package com.jeesite.modules.utils;

import com.jeesite.modules.bkp.entity.*;
import com.jeesite.modules.bkp.service.BkpBatchrecordService;
import com.jeesite.modules.bkp.service.BkpDetailService;
import com.jeesite.modules.bkp.service.BkpFixedprofitService;
import com.jeesite.modules.bkp.service.BkpUserbetsService;
import com.jeesite.modules.bkp.web.BkpUserbetsController;
import com.jeesite.modules.odds.web.TestController002;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class TimeUpdate {
    @Autowired
    private BkpBatchrecordService bkpBatchrecordService;
    @Autowired
    private BkpFixedprofitService bkpFixedprofitService;
    @Autowired
    private BkpUserbetsService bkpUserbetsService;
    @Autowired
    private TestController002 testController002;
    @Autowired
    private BkpDetailService bkpDetailService;

    public void updateStatus() throws IOException, ParseException {
        Long aa=System.currentTimeMillis();
//       timedTask();
        //查询所有的固定盈利模式信息
        BkpFixedprofit bkpFixedprofit=new BkpFixedprofit();
        User user= UserUtils.getUser();
        bkpFixedprofit.setUsercode(user.getUserCode());
        List<BkpFixedprofit> list=bkpFixedprofitService.findList(bkpFixedprofit);
        //根据他们的id进行查询该id下的所有批次
        for (BkpFixedprofit s:list){
            timing(s);
            BkpBatchrecord bkpBatchrecord=new BkpBatchrecord();
            bkpBatchrecord.setModeid(Long.valueOf(s.getId()));
            //该固定盈利模式下所有批次
            List<BkpBatchrecord> bkpBatchrecordList=bkpBatchrecordService.findList(bkpBatchrecord);
            for (BkpBatchrecord f:bkpBatchrecordList){
                BkpUserbets bkpUserbets=new BkpUserbets();
                bkpUserbets.setPcjlid(Long.valueOf(f.getId()));
                List<BkpUserbets> bkpUserbetsList=bkpUserbetsService.findList(bkpUserbets); //该批次下所有的下注信息
                if (bkpUserbetsList.size()==0){
                    continue;
                }
                List<Long> comm=bkpUserbetsService.selectById(bkpUserbetsList.get(0).getPcjlid());
                for (int i=0;i<comm.size();i++){
                    refresh(bkpUserbetsList); //此方法走完 每个批次下的盈亏都有了
                }
            }
            float sum=0;
            for (BkpBatchrecord f:bkpBatchrecordList){ //固定盈利模式加入
                sum+=Float.valueOf(f.getProfitamount());
            }
//            sum=(float)(Math.round(sum*1000)/1000);
            DecimalFormat df1 = new DecimalFormat("#.00");
            sum = Float.valueOf(df1.format(sum));
            s.setProfitamount(String.valueOf(sum));
            if (Float.valueOf(s.getProfitamount())>=(Float.valueOf(s.getMonthamount())+Float.valueOf(s.getTotalloss()))){
                s.setDeletelogo(0L);
                SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
                s.setEndtime(sim.format(new Date()));
            }
            bkpFixedprofitService.update(s);
        }
        Long bb=System.currentTimeMillis();
        System.out.println(bb-aa+"定时任务耗时");
    }

    public void refresh(List<BkpUserbets> bkpUserbetsList) throws IOException { //当前批次 下所有的 下注信息
        //分组查询到所有组合
        List<Long> comm=bkpUserbetsService.selectById(bkpUserbetsList.get(0).getPcjlid());
        //获取到所有的组合
        List<BkpUserbetsVO> bkpUserbetsVOList=new ArrayList<>();
        for (Long s:comm){
            BkpUserbetsVO bkpUserbetsVO=new BkpUserbetsVO();
            List<BkpUserbets> list1=bkpUserbetsService.selectByCombination(s);//该组合编号下的所有下注信息
            if (list1.size()==0){
                continue;
            }
            String matchResults="";
            String yhxzjg="";
            boolean flag=true;
            Boolean flag1=true;
            Boolean flag2=true;
            for (BkpUserbets f:list1){
                f.getMatchresults();//比赛结果
                f.getPick();//用户选择结果
                if (f.getMatchresults().equals("待开奖")){
                    matchResults+="待开奖";
                }else {
                    int i = f.getPick().indexOf(f.getMatchresults());//看选择里面是否有比赛结果
                    if (i<0){//选择里面没有比赛结果
                        flag=false;
                    }
                }
            }
            //如果flag为false 则此次下注没有中注
            if (flag){//有两种情况  1都是待开奖   2 中注
                if (matchResults.indexOf("待开奖")>=0){ //说明都是待开奖
                    bkpUserbetsVO.setDqykje("0");//当前批次盈利金额都没有出  所以暂为0
                }else { //中注
                    //计算中奖金额
                    float num1=1;
                    float ss=1;
                    //就去查询倍率
                    Long multiple = list1.get(0).getMultiple();//倍数
                    int jk=0;
                    for (BkpUserbets j:list1){
                        //根据b_date num 查询
                        Map<String,Object> result= (Map<String, Object>) testController002.getGameData(j.getBdate(), j.getNum());
                        float v2=Float.valueOf((String) result.get("jj"));//查询到的奖金赔率
                        //奖金x2
                        num1=num1*v2;
                    }
                    //结束后就为奖金 num1x倍数*2
                    num1=multiple*num1*2;//此为下注后的可获取的金额
                    //投注奖金
                    float aa=Float.valueOf(list1.get(0).getBetamount());
                    num1-=aa;
                    DecimalFormat df1 = new DecimalFormat("#.00");
                    num1 = Float.valueOf(df1.format(num1));
//                    num1=(float)(Math.round(num1*1000)/1000);
                    System.out.println("num1"+num1);
                    bkpUserbetsVO.setDqykje(String.valueOf(num1));//当前组合盈利金额
                    BkpUserbets bkpUserbets=new BkpUserbets();
                    bkpUserbets.setCombination(String.valueOf(s));
                    List<BkpUserbets> bkpUserbetsList1=bkpUserbetsService.findList(bkpUserbets);
                    for (BkpUserbets k:bkpUserbetsList1){
                        k.setCurrentprofitamount(String.valueOf(num1));
                        bkpUserbetsService.update(k);
                    }
                }
            }else { //flag为false  未中注
                BkpUserbets bkpUserbets=new BkpUserbets();
                bkpUserbets.setCombination(String.valueOf(s));
                List<BkpUserbets> bkpUserbetsList1=bkpUserbetsService.findList(bkpUserbets);
                for (BkpUserbets k:bkpUserbetsList1){
                    k.setCurrentprofitamount(String.valueOf("-"+list1.get(0).getBetamount()));
                    bkpUserbetsService.update(k);
                }
                bkpUserbetsVO.setDqykje("-"+list1.get(0).getBetamount());
            }
            bkpUserbetsVOList.add(bkpUserbetsVO);
        }
        if (bkpUserbetsVOList.size()==0){
            return;
        }
        ///修改批次盈利金额
        float sum=0;
        for (BkpUserbetsVO s:bkpUserbetsVOList){
            //把组合的金额全部加起来
            sum+=Float.valueOf(s.getDqykje());
        }
        BkpBatchrecord bkpBatchrecord=new BkpBatchrecord();
        bkpBatchrecord.setId(String.valueOf(bkpUserbetsList.get(0).getPcjlid()));
        BkpBatchrecord bkpBatchrecord1 = bkpBatchrecordService.get(bkpBatchrecord);
        DecimalFormat df1 = new DecimalFormat("#.00");
        sum = Float.valueOf(df1.format(sum));
//        sum=(float)(Math.round(sum*1000)/1000);//盈利金额
        float v1 =Float.valueOf(bkpBatchrecord1.getPreprofit());//这批的预盈利金额
        if (sum>=v1){
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            bkpBatchrecord1.setEndtime(simpleDateFormat.format(new Date()));
            bkpBatchrecord1.setDqzt("2");//结束;
            //这个模式结束了后  改变其他批次的
            BkpBatchrecord bkpBatchrecord2=new BkpBatchrecord();
            bkpBatchrecord2.setDqzt("0");
            bkpBatchrecord2.setModeid(Long.valueOf(bkpUserbetsList.get(0).getGdmsid()));
            List<BkpBatchrecord> list = bkpBatchrecordService.findList(bkpBatchrecord2);//其他还未开始的
            BkpFixedprofit bkpFixedprofit=new BkpFixedprofit();
            bkpFixedprofit.setId(String.valueOf(bkpBatchrecord1.getModeid()));
            BkpFixedprofit bkpFixedprofit1 = bkpFixedprofitService.get(bkpFixedprofit);
            float totalloss = Float.valueOf(bkpFixedprofit1.getTotalloss()); //历史亏损金额
            float monthamount = Float.valueOf(bkpFixedprofit1.getMonthamount());
            float cycle = Float.valueOf(bkpFixedprofit1.getCycle());//周期  30
            Long batch = bkpFixedprofit1.getBatch();//批次
            float zje=totalloss+monthamount;
            for (BkpBatchrecord s1:list){
                float b =((zje-sum)/list.size())/(cycle/batch);
                DecimalFormat df12 = new DecimalFormat("#.00");
                b = Float.valueOf(df12.format(b));
                s1.setFpyl(String.valueOf(b));
                float c=(zje-sum)/list.size();
                c=Float.valueOf(df12.format(c));
                s1.setPreprofit(String.valueOf( c));
                bkpBatchrecordService.update(s1);
            }
        }
        //根据每天盈利
        bkpBatchrecord1.setProfitamount(String.valueOf(sum));
        bkpBatchrecordService.update(bkpBatchrecord1);
    }
    //当天结束后
    //计算当天批次
//    public void todayUpdate(){
//        User user= UserUtils.getUser();
//        BkpBatchrecord bkpBatchrecord=new BkpBatchrecord();
//        bkpBatchrecord.setDqzt("1");//进行中的批次
//        bkpBatchrecord.setUserCode(user.getUserCode());
//        BkpBatchrecord bkpBatchrecord1 = bkpBatchrecordService.get(bkpBatchrecord);//查询正在进行的批次
//        if (null==bkpBatchrecord1){
//            return;
//        }
//        //查询该批次下所有的下注信息
//        List<Long> comm=bkpUserbetsService.selectById(Long.valueOf(bkpBatchrecord1.getId()));
//        if (comm.size()==0){
//            return;
//        }
//        float num=0;
//        int fpxh=0;
//        for (Long s:comm){
//            BkpUserbetsVO bkpUserbetsVO=new BkpUserbetsVO();
//            List<BkpUserbets> list1=bkpUserbetsService.selectByCombination(s);//该组合编号下的所有下注信息
//           float currentprofitamount =Float.valueOf(list1.get(0).getCurrentprofitamount()); //这个批次的盈亏的金额;
//            num+=currentprofitamount;
//            fpxh=Integer.valueOf(list1.get(0).getPcxh());
//        }
//        DecimalFormat df1 = new DecimalFormat("#.00");
//        num = Float.valueOf(df1.format(num));
//        //获得固定盈利模式的每天预盈利金额
//        BkpFixedprofit bkpFixedprofit=new BkpFixedprofit();
//        bkpFixedprofit.setId(String.valueOf(bkpBatchrecord1.getModeid()));
//        BkpFixedprofit bkpFixedprofit1 = bkpFixedprofitService.get(bkpFixedprofit);
//        float totalloss = Float.valueOf(bkpFixedprofit1.getTotalloss()); //历史亏损金额
//        float monthamount = Float.valueOf(bkpFixedprofit1.getMonthamount());
//        Long batch = bkpFixedprofit1.getBatch();//批次
//        float cycle = Float.valueOf(bkpFixedprofit1.getCycle());//周期  30
//        float zje=totalloss+monthamount; //3000
//        float mpcyl=Float.valueOf(bkpBatchrecord2.getPreprofit());
//        float fpylje=zje/cycle;//每天预盈利金额
//        float at=cycle/batch;//每个批次多少天
//        if (num<fpylje){ //当天批次盈利 小于当天分批预盈利
//            float a=(mpcyl-num)/(at-Float.valueOf(fpxh));
//            bkpBatchrecord1.setFpyl(String.valueOf(a));
//        }else if (num>fpylje){
//            float a=(mpcyl-num)/(at-Float.valueOf(fpxh));
//            bkpBatchrecord1.setFpyl(String.valueOf(a));
//        }
//        bkpBatchrecordService.update(bkpBatchrecord1);
//    }
    public void timing(BkpFixedprofit bkpFixedprofit) throws ParseException {
        bkpFixedprofit.getCreattime();//创建时间
        String cycle = bkpFixedprofit.getCycle();//周期
        Long batch = bkpFixedprofit.getBatch();//批次
        int pct=0;
        //
        if ((Long.valueOf(cycle))%batch==0){
            pct= (int) ((Long.valueOf(cycle))/batch);//每个批次的具体多天  如1批次6天
            //查询startTime不为null的 看是否需要修改状态
            List<BkpBatchrecord> list=bkpBatchrecordService.selectBystartTime(bkpFixedprofit.getId());
            SimpleDateFormat mf=new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();//获取当前时间
            Calendar cal = Calendar.getInstance();
            // 设置开始时间
            String ff="yyyy-MM-dd";
            String format = mf.format(date);//当前时间
            //cal.add(Calendar.DATE, -10);//减10天
            for (BkpBatchrecord s:list){
                cal.setTime(mf.parse(s.getStarttime()));
                cal.add(Calendar.DATE, pct-1);
                String format1 = mf.format(cal.getTime());////加了6天之后的时间
                //时间比较大小
                Integer min1 = getMin1(format, format1,ff);
                if (min1>0){
                    s.setDqzt("2");
                    s.setEndtime(format);
                    //这个模式结束了后  改变其他批次的
                    BkpBatchrecord bkpBatchrecord2=new BkpBatchrecord();
                    bkpBatchrecord2.setDqzt("0");
                    bkpBatchrecord2.setModeid(Long.valueOf(s.getModeid()));
                    List<BkpBatchrecord> list001 = bkpBatchrecordService.findList(bkpBatchrecord2);//其他还未开始的
                    BkpFixedprofit bkpFixedprofit01=new BkpFixedprofit();
                    bkpFixedprofit01.setId(String.valueOf(s.getModeid()));
                    BkpFixedprofit bkpFixedprofit1 = bkpFixedprofitService.get(bkpFixedprofit01);
                    float totalloss = Float.valueOf(bkpFixedprofit1.getTotalloss()); //历史亏损金额
                    float monthamount = Float.valueOf(bkpFixedprofit1.getMonthamount());
                    float cycle01 = Float.valueOf(bkpFixedprofit1.getCycle());//周期  30
                    Long batch01 = bkpFixedprofit1.getBatch();//批次
                    float sum=Float.valueOf(s.getProfitamount());
                    float zje=totalloss+monthamount;
                    for (BkpBatchrecord s1:list001){
                        float bb =((zje-sum)/list001.size())/(cycle01/batch01);
                        DecimalFormat df12 = new DecimalFormat("#.00");
                        bb = Float.valueOf(df12.format(bb));
                        s1.setFpyl(String.valueOf(bb));
                        float c=(zje-sum)/list001.size();
                        c=Float.valueOf(df12.format(c));
                        s1.setPreprofit(String.valueOf( c));
                        bkpBatchrecordService.update(s1);
                    }
                    bkpBatchrecordService.update(s);
                }
            }
            cal.setTime(mf.parse(bkpFixedprofit.getStarttime()));
            cal.add(Calendar.DATE, Integer.valueOf(cycle)-1);//加了30天后的时间
            String str=mf.format(cal.getTime());
            int aa = getMin1(format, str,ff);
            if (aa>0){
                bkpFixedprofit.setDeletelogo(0L);
                bkpFixedprofit.setEndtime(format);
                bkpFixedprofitService.update(bkpFixedprofit);
            }

        }else {
            int a=(int) ((Long.valueOf(cycle))/batch);//每个批次的具体多天  如1批次6天
            int b= (int) ((Long.valueOf(cycle))%batch);
            //查询startTime不为null的 看是否需要修改状态
            List<BkpBatchrecord> list=bkpBatchrecordService.selectBystartTime(bkpFixedprofit.getId());
            SimpleDateFormat mf=new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();//获取当前时间
            Calendar cal = Calendar.getInstance();
            // 设置开始时间
            String ff="yyyy-MM-dd";
            String format = mf.format(date);//当前时间
            //cal.add(Calendar.DATE, -10);//减10天
            for (BkpBatchrecord s:list){
                cal.setTime(mf.parse(s.getStarttime()));
                if (s.getBatch().equals(batch.toString())){
                    cal.add(Calendar.DATE, a+b-1);
                }else {
                    cal.add(Calendar.DATE, a-1);
                }
                String format1 = mf.format(cal.getTime());////加了n天之后的时间
                //时间比较大小
                Integer min1 = getMin1(format, format1,ff);
                if (min1>0){
                    s.setDqzt("2");
                    s.setEndtime(format);
                    //这个模式结束了后  改变其他批次的
                    BkpBatchrecord bkpBatchrecord2=new BkpBatchrecord();
                    bkpBatchrecord2.setDqzt("0");
                    bkpBatchrecord2.setModeid(Long.valueOf(s.getModeid()));
                    List<BkpBatchrecord> list001 = bkpBatchrecordService.findList(bkpBatchrecord2);//其他还未开始的
                    BkpFixedprofit bkpFixedprofit01=new BkpFixedprofit();
                    bkpFixedprofit01.setId(String.valueOf(s.getModeid()));
                    BkpFixedprofit bkpFixedprofit1 = bkpFixedprofitService.get(bkpFixedprofit01);
                    float totalloss = Float.valueOf(bkpFixedprofit1.getTotalloss()); //历史亏损金额
                    float monthamount = Float.valueOf(bkpFixedprofit1.getMonthamount());
                    float cycle01 = Float.valueOf(bkpFixedprofit1.getCycle());//周期  30
                    Long batch01 = bkpFixedprofit1.getBatch();//批次
                    float profitamount1 =  Float.valueOf(bkpFixedprofit1.getProfitamount());//已经盈利金额
                    float sum=Float.valueOf(s.getProfitamount());
                    float zje=totalloss+monthamount;
                    for (BkpBatchrecord s1:list001){
                        float bb =((zje-sum)/list001.size())/(cycle01/batch01);
                        DecimalFormat df12 = new DecimalFormat("#.00");
                        bb = Float.valueOf(df12.format(bb));
                        s1.setFpyl(String.valueOf(bb));
                        float c=(zje-sum)/list001.size();
                        c=Float.valueOf(df12.format(c));
                        s1.setPreprofit(String.valueOf( c));
                        bkpBatchrecordService.update(s1);
                    }
                    bkpBatchrecordService.update(s);
                }
            }
            cal.setTime(mf.parse(bkpFixedprofit.getStarttime()));
            cal.add(Calendar.DATE, Integer.valueOf(cycle)-1);//加了30天后的时间
            String str=mf.format(cal.getTime());
            int aa = getMin1(format, str,ff);
            if (aa>0){
                bkpFixedprofit.setDeletelogo(0L);
                bkpFixedprofit.setEndtime(format);
                bkpFixedprofitService.update(bkpFixedprofit);
            }
        }
//        cc();
    }
    public void cc(){ //修改
        User user= UserUtils.getUser();
        //根据 usercode查询
        List<BkpBatchrecord> list=bkpBatchrecordService.selectByUserCode(user.getUserCode());
        for (BkpBatchrecord s:list){
            s.setDqzt("1");
            bkpBatchrecordService.update(s);
        }
    }
    public static Integer getMin1(String date,String date1,String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date dateTime1 = dateFormat.parse(date);
        Date dateTime2 = dateFormat.parse(date1);
        int i = dateTime1.compareTo(dateTime2); //返回-1 则是比当前时间小 返回0相等  返回>1则是比当前时间大
        return i;
    }
    /*
    固定盈利模式时间过期了修改状态
     */
    public void changeTheTimeStatus(BkpFixedprofit bkpFixedprofit) throws ParseException {
        bkpFixedprofit.getStarttime();//开始时间
        int cycle = Integer.valueOf(bkpFixedprofit.getCycle());//周期  30
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
        String time=sim.format(new Date());//当前时间
        //如果开始时间加了周期超过当前时间  就是超时了
        Calendar cal = Calendar.getInstance();
        cal.setTime(sim.parse(bkpFixedprofit.getStarttime()));
        cal.add(Calendar.DATE, cycle-1);
        String format1 = sim.format(cal.getTime());////加了30天之后的时间
        //然后和当天时间进行对比  如果超过当前时间那么就修改状态
        String ff="yyyy-MM-dd";
        //时间比较大小
        Integer min1 = getMin1(time, format1,ff);
        if (min1>0){//超时了  修改固定模式的状态
            bkpFixedprofit.setDeletelogo(0L);
            bkpFixedprofit.setEndtime(time);
            bkpFixedprofitService.update(bkpFixedprofit);
            //还得修改批次的状态
            BkpBatchrecord bkpBatchrecord=new BkpBatchrecord();
            bkpBatchrecord.setModeid(Long.valueOf(bkpFixedprofit.getId()));
            bkpBatchrecord.setDqzt("1");
            List<BkpBatchrecord> list=bkpBatchrecordService.findList(bkpBatchrecord);
            for (BkpBatchrecord s:list){
                s.setDqzt("2");
                s.setEndtime(time);
                bkpBatchrecordService.update(s);
            }
        }
    }

    /*
    批次超时了修改状态.以及修改其他的批次的金额
     */
    public void modifyBatchStatus(BkpFixedprofit bkpFixedprofit) throws ParseException {
        float profitamount1 =  Float.valueOf(bkpFixedprofit.getProfitamount());//已经盈利金额
        float totalloss1 = Float.valueOf(bkpFixedprofit.getTotalloss()); //历史亏损金额
        float monthamount1 = Float.valueOf(bkpFixedprofit.getMonthamount());
        float zje=totalloss1+monthamount1;//当前固定盈利模式的总盈亏金额
        bkpFixedprofit.getStarttime();//开始时间
        int cycle = Integer.valueOf(bkpFixedprofit.getCycle());//周期  30
        Long batch = bkpFixedprofit.getBatch();//固定模式的批次
        int a= (int) (cycle/batch);//30/4=7
        int b= (int) (cycle%batch);//30%4=2
        String ff="yyyy-MM-dd";
        int pct= (int) (cycle/batch);
        boolean flag=true;
        if (cycle%batch!=0){
            flag=false;
        }
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
        String time=sim.format(new Date());//当前时间
        //查询下面的有开始时间  没有结束时间的批次
        List<BkpBatchrecord> list=bkpBatchrecordService.selectByModeId(bkpFixedprofit.getId());
        //查询是否超过了当前时间
        for (BkpBatchrecord s:list){
            s.getStarttime();//批次的开始时间
            Calendar cal = Calendar.getInstance();
            cal.setTime(sim.parse(s.getStarttime()));
            int batch1 = s.getBatch();//当前批次的  如  4
            if(batch1==batch){//最后一批
                if (flag==false){//如  30天分成4个批次   30/4=7.5
                    cal.add(Calendar.DATE, a+b-1);
                    String format1 = sim.format(cal.getTime());////加了批次天之后的时间
                    //时间比较大小
                    Integer min1 = getMin1(time, format1,ff);
                    if (min1>0){//超时了  修改批次的状态以及其他的批次的预盈利金额
                        s.setDqzt("2");
                        s.setEndtime(time);
                        bkpBatchrecordService.update(s);
                    }
                }else {
                    cal.add(Calendar.DATE, pct-1);
                    String format1 = sim.format(cal.getTime());////加了批次天之后的时间
                    //时间比较大小
                    Integer min1 = getMin1(time, format1,ff);
                    if (min1>0){//超时了  修改批次的状态以及其他的批次的预盈利金额
                        s.setDqzt("2");
                        s.setEndtime(time);
                        bkpBatchrecordService.update(s);
                    }
                }
            }else {
                cal.add(Calendar.DATE, pct-1);
                String format1 = sim.format(cal.getTime());////加了批次天之后的时间
                //时间比较大小
                Integer min1 = getMin1(time, format1,ff);
                if (min1>0){//超时了  修改批次的状态以及其他的批次的预盈利金额
                    s.setDqzt("2");
                    s.setEndtime(time);
                    bkpBatchrecordService.update(s);
                    //同时修改其他的为未开始的状态的批次预计盈利,以及批次天预计盈利
                    BkpBatchrecord bkpBatchrecord2=new BkpBatchrecord();
                    bkpBatchrecord2.setDqzt("0");
                    bkpBatchrecord2.setModeid(Long.valueOf(s.getModeid()));
                    List<BkpBatchrecord> list1 = bkpBatchrecordService.findList(bkpBatchrecord2);//当前固定模式下的所有的未开始批次
                    for (BkpBatchrecord s1:list1){
                        DecimalFormat df2 = new DecimalFormat("#.00");
                        float aa=(zje-profitamount1)/list1.size();
                        aa=Float.valueOf(df2.format(aa));
                        s1.setPreprofit(String.valueOf(aa));//预计盈利每个批次  当前固定模式剩余的盈利除以剩下的批次
                        float bb=(zje-profitamount1)/(list1.size()*pct);
                        if (Long.valueOf(s1.getBatch()).equals(batch)){ //如果是最后一批次
                            if (flag==false){
                                bb=(zje-profitamount1)/(list1.size()*(a+b));
                            }
                        }
                        bb=Float.valueOf(df2.format(bb));
                        s1.setFpyl(String.valueOf(bb));//预计盈利天
                        bkpBatchrecordService.update(s1);
                    }
                }
            }
        }
    }
    /*
    玩法自由化
     */
    public void freedomOfPlay(BkpUserbets bkpUserbets) throws Exception{
        //根据
        BkpUserbets bkpUserbets1=new BkpUserbets();
        bkpUserbets1.setCombination(bkpUserbets.getCombination());
        List<BkpUserbets> list1 = bkpUserbetsService.findList(bkpUserbets1);//该组合下所有的下注信息
        Map map=new HashMap();
        for (BkpUserbets s:list1){
            map.put(s.getNum(),s.getMatchresults());//比赛结果
            map.put(s.getNum()+"date",s.getBdate());//时间
        }
        //投注奖金
        float aa=Float.valueOf(list1.get(0).getBetamount());
        Long multiple = list1.get(0).getMultiple();//倍数



        BkpDetail bkpDetail=new BkpDetail();
        bkpDetail.setCombination(bkpUserbets.getCombination());
        float zje=0;
        //查询出下面所有的组合
        List<BkpDetail> list=bkpDetailService.findList(bkpDetail); //所有的组合
        for (BkpDetail s:list){ //所有的组合
            String combinedresult = s.getCombinedresult(); //周四022(胜)x周四023(胜)x
            String[] arr=combinedresult.split("x"); //周四022(胜)  周四023(胜)
            boolean flag=true;
            for (String f:arr){
                String num=f.substring(0,5);//周四022
                String selectresults=f.substring(6,7);//比赛结果
                String Matchresults = (String) map.get(num);
                if (!selectresults.equals(Matchresults)){//未中注
                    flag=false;
                }
            }
            if (flag){  //当前组合中注   //把他们的赔率查询出来
                float v=1;
                for (String s1:arr){
                    String num=s1.substring(0,5);//周四022
                    String time= (String) map.get(num+"date");
                    //查询奖金赔率
                    Map<String,Object> result= (Map<String, Object>) testController002.getGameData(time,num);
                    float v2=Float.valueOf((String) result.get("jj"));//查询到的奖金赔率
                    v=v*v2;
                }
                v=multiple*2*v;//此为下注后可以获取的金额
                zje+=v;
            }
        }
        //所有 组合的盈利金额为  zje;
        zje=zje-aa;//总金额减去投注的金额  计算盈亏
        DecimalFormat df1 = new DecimalFormat("#.00");
        zje = Float.valueOf(df1.format(zje));//此为盈利金额
        for (BkpUserbets s:list1){
            s.setCurrentprofitamount(String.valueOf(zje));
            bkpUserbetsService.update(s);
        }
        if (null==bkpUserbets.getPcjlid()||null==bkpUserbets.getGdmsid()){

        }else {
            updateBkpBatchrecord(bkpUserbets);//批次修改更新;
            updatebkpFixedprofit(bkpUserbets);//固定模式修改更新
            updateBatchDays(bkpUserbets);//修改批次天
            endBatch(bkpUserbets);//修改状态
            toBeCalculated(bkpUserbets);//如果该批次下已经结束了 但是还有待开奖的  重新进行计算其他批次的预盈利金额
        }

    }


    public void updateykje(BkpUserbets bkpUserbets) throws Exception {
        Long bb01=System.currentTimeMillis();
        //根据
        BkpUserbets bkpUserbets1=new BkpUserbets();
        bkpUserbets1.setCombination(bkpUserbets.getCombination());
        List<BkpUserbets> list = bkpUserbetsService.findList(bkpUserbets1);//该组合下所有的下注信息
        boolean flag=true;
        for (BkpUserbets s:list){ //查看是否中注
            int i = s.getPick().indexOf(s.getMatchresults());//看选择里面是否有比赛结果
            if (i<0){
                flag=false;
            }
        }
        float num002=0;
        if (flag){//中注
            float num1=1;
            //中奖情况下才计算
            for (BkpUserbets s:list){
                Map<String,Object> result= (Map<String, Object>) testController002.getGameData(s.getBdate(), s.getNum());
                float v2=Float.valueOf((String) result.get("jj"));//查询到的奖金赔率
                num1=num1*v2;
            }
            Long multiple = list.get(0).getMultiple();//倍数
            //结束后就为奖金 num1x倍数*2
            num1=multiple*num1*2;//此为下注后的可获取的金额
            //投注奖金
            float aa=Float.valueOf(list.get(0).getBetamount());
            num1-=aa;
            DecimalFormat df1 = new DecimalFormat("#.00");
            num1 = Float.valueOf(df1.format(num1));//此为盈利金额
//            num002=num1;
            for (BkpUserbets s:list){
                s.setCurrentprofitamount(String.valueOf(num1));
                bkpUserbetsService.update(s);
            }
        }else { //未中注
            float betamout=Float.valueOf(list.get(0).getBetamount());//投注金额
            for (BkpUserbets s:list){
                s.setCurrentprofitamount("-"+betamout);
//                num002=Float.valueOf("-"+betamout);
                bkpUserbetsService.update(s);
            }
        }
        updateBkpBatchrecord(bkpUserbets);//批次修改更新;
        updatebkpFixedprofit(bkpUserbets);//固定模式修改更新
        updateBatchDays(bkpUserbets);//修改批次天
        endBatch(bkpUserbets);//修改状态

    }

    public void toBeCalculated(BkpUserbets bkpUserbets){
        BkpBatchrecord bkpBatchrecord=new BkpBatchrecord();
        bkpBatchrecord.setId(String.valueOf(bkpUserbets.getPcjlid()));
        BkpBatchrecord bkpBatchrecord1 = bkpBatchrecordService.get(bkpBatchrecord);//当前所在的批次
        if (bkpBatchrecord1.getDqzt().equals("2")) {//如果当前批次已经结束了
            List<BkpBatchrecord> batchrecordList = bkpBatchrecordService.selectByDqzt(bkpBatchrecord1.getModeid());
            //获取当前的进行的固定盈利模式数据
            BkpFixedprofit bkpFixedprofit = new BkpFixedprofit();
            bkpFixedprofit.setId(String.valueOf(bkpUserbets.getGdmsid()));
            BkpFixedprofit bkpFixedprofit1 = bkpFixedprofitService.get(bkpFixedprofit);
            float profitamount1 = Float.valueOf(bkpFixedprofit1.getProfitamount());//已经盈利金额
            float cycle01 = Float.valueOf(bkpFixedprofit1.getCycle());//周期
            float totalloss1 = Float.valueOf(bkpFixedprofit1.getTotalloss()); //历史亏损金额
            float monthamount1 = Float.valueOf(bkpFixedprofit1.getMonthamount());
            float zje = totalloss1 + monthamount1;
            Long batch = bkpFixedprofit1.getBatch();//批次
            int pct = (int) (cycle01 / batch);//如30/5=6 每个批次6天
            int bbb = (int) (cycle01 % batch);

            //获取刚才中注未中注的 金额
            BkpUserbets bkpUserbets1=new BkpUserbets();
            bkpUserbets1.setCombination(bkpUserbets.getCombination());
            List<BkpUserbets> list2 = bkpUserbetsService.findList(bkpUserbets1);//该组合下所有的下注信息
            //当前批次的批次
            int batch01 = bkpBatchrecord1.getBatch();//当前批次的批次
            long l = batch - batch01;
            if (l == 0) {
            } else {
                float zhyyl = Float.valueOf(list2.get(0).getCurrentprofitamount());//这个组合的盈利 / l
                float fk=zhyyl/l;
                for (BkpBatchrecord s : batchrecordList) {
                    if ((s.getBatch().toString()).equals(batch.toString())) {//最后一批次
                        if (bbb != 0) { //比如  30 天分了4批  最后一天就为9天
                            float profitamount = Float.valueOf(s.getPreprofit());//预盈利金额
                            //当前批次的预盈利金额再减去当前出来的拆分
                            float aa=profitamount-fk;
                            DecimalFormat df1 = new DecimalFormat("#.00");
                            aa = Float.valueOf(df1.format(aa));//此为盈利金额  //小数点两位
                            s.setPreprofit(String.valueOf(aa));//修改了预盈利
                            float fpyl=fk/(pct+bbb);
                            float fpyl1 = Float.valueOf(s.getFpyl());//原本的盈利
                            fpyl=fpyl1-fpyl;
                            fpyl= Float.valueOf(df1.format(fpyl));
                            s.setFpyl(String.valueOf(fpyl));
                            bkpBatchrecordService.update(s);
                        } else {
                            float profitamount = Float.valueOf(s.getPreprofit());//预盈利金额
                            //当前批次的预盈利金额再减去当前出来的拆分
                            float aa=profitamount-fk;
                            DecimalFormat df1 = new DecimalFormat("#.00");
                            aa = Float.valueOf(df1.format(aa));//此为盈利金额  //小数点两位
                            s.setPreprofit(String.valueOf(aa));//修改了预盈利
                            float fpyl=fk/pct;
                            float fpyl1 = Float.valueOf(s.getFpyl());//原本的盈利
                            fpyl=fpyl1-fpyl;
                            fpyl= Float.valueOf(df1.format(fpyl));
                            s.setFpyl(String.valueOf(fpyl));
                            bkpBatchrecordService.update(s);
                        }
                    } else {
                        float profitamount = Float.valueOf(s.getPreprofit());//预盈利金额
                        //当前批次的预盈利金额再减去当前出来的拆分
                        float aa=profitamount-fk;
                        DecimalFormat df1 = new DecimalFormat("#.00");
                        aa = Float.valueOf(df1.format(aa));//此为盈利金额  //小数点两位
                        s.setPreprofit(String.valueOf(aa));//修改了预盈利
                        float fpyl=fk/pct;
                        float fpyl1 = Float.valueOf(s.getFpyl());//原本的盈利
                        fpyl=fpyl1-fpyl;
                        fpyl= Float.valueOf(df1.format(fpyl));
                        s.setFpyl(String.valueOf(fpyl));
                        bkpBatchrecordService.update(s);
                    }

                }
            }
        }
    }


    /*
    更新当前组合出来的批次
     */
    public void updateBkpBatchrecord(BkpUserbets bkpUserbets){
        //获取刚才中注未中注的 金额
        BkpUserbets bkpUserbets1=new BkpUserbets();
        bkpUserbets1.setCombination(bkpUserbets.getCombination());
        List<BkpUserbets> list2 = bkpUserbetsService.findList(bkpUserbets1);//该组合下所有的下注信息
        //下注解决后 获取当前组合所在的批次
        BkpBatchrecord bkpBatchrecord=new BkpBatchrecord();
        bkpBatchrecord.setId(String.valueOf(bkpUserbets.getPcjlid()));
        BkpBatchrecord bkpBatchrecord1 = bkpBatchrecordService.get(bkpBatchrecord);//当前所在的批次
        float profitamount =Float.valueOf(bkpBatchrecord1.getProfitamount());//当前批次的盈亏金额
        float zhje=Float.valueOf(list2.get(0).getCurrentprofitamount());//组合的盈亏金额
        float pcykje=profitamount+zhje;//该批次的现在的总盈亏
        DecimalFormat df1 = new DecimalFormat("#.00");
        pcykje = Float.valueOf(df1.format(pcykje));//此为盈利金额  //小数点两位
        bkpBatchrecord1.setProfitamount(String.valueOf(pcykje));//修改总的盈亏数
        float preprofit = Float.valueOf(bkpBatchrecord1.getPreprofit());//当前批次预盈利金额
        SimpleDateFormat mf=new SimpleDateFormat("yyyy-MM-dd");
        boolean fff=false;
        //修改批次信息
        bkpBatchrecordService.update(bkpBatchrecord1);
    }

    /*
    固定模式修改盈亏金额
     */
    public void updatebkpFixedprofit(BkpUserbets bkpUserbets){
        //获取刚才中注未中注的 金额
        BkpUserbets bkpUserbets1=new BkpUserbets();
        bkpUserbets1.setCombination(bkpUserbets.getCombination());
        List<BkpUserbets> list2 = bkpUserbetsService.findList(bkpUserbets1);//该组合下所有的下注信息
        //获得固定盈利模式的每天预盈利金额
        BkpFixedprofit bkpFixedprofit=new BkpFixedprofit();
        bkpFixedprofit.setId(String.valueOf(list2.get(0).getGdmsid()));
        //获取该组合上的固定模式
        BkpFixedprofit bkpFixedprofit1 = bkpFixedprofitService.get(bkpFixedprofit);
        float mpcyl=Float.valueOf(bkpFixedprofit1.getProfitamount());//这个固定模式的现在的盈亏金额
        float mpcyl01=Float.valueOf(list2.get(0).getCurrentprofitamount());// 这个组合的盈亏金额
        float a1=mpcyl+mpcyl01;//模式盈亏金额加上组合盈亏金额
        DecimalFormat df1 = new DecimalFormat("#.00");
        a1 = Float.valueOf(df1.format(a1));//此为盈利金额  //小数点两位
        bkpFixedprofit1.setProfitamount(String.valueOf(a1));
        bkpFixedprofitService.update(bkpFixedprofit1);
    }
    /*
    批次更新状态,更新当前批次天预盈利金额()
     */
     public void updateBatchDays(BkpUserbets bkpUserbets){
         //获取当前的进行的固定盈利模式数据
         BkpFixedprofit bkpFixedprofit=new BkpFixedprofit();
         bkpFixedprofit.setId(String.valueOf(bkpUserbets.getGdmsid()));
         BkpFixedprofit bkpFixedprofit1 = bkpFixedprofitService.get(bkpFixedprofit);
         float profitamount1 =  Float.valueOf(bkpFixedprofit1.getProfitamount());//已经盈利金额
         float cycle01 = Float.valueOf(bkpFixedprofit1.getCycle());//周期
         float totalloss1 = Float.valueOf(bkpFixedprofit1.getTotalloss()); //历史亏损金额
         float monthamount1 = Float.valueOf(bkpFixedprofit1.getMonthamount());
         float zje=totalloss1+monthamount1;
         Long batch = bkpFixedprofit1.getBatch();//批次
         int pct= (int) (cycle01/batch);//如30/5=6 每个批次6天
         int bbb= (int) (cycle01%batch);

         //获取当前进行的批次数据
         BkpBatchrecord bkpBatchrecord=new BkpBatchrecord();
         bkpBatchrecord.setId(String.valueOf(bkpUserbets.getPcjlid()));
         BkpBatchrecord bkpBatchrecord1 = bkpBatchrecordService.get(bkpBatchrecord);
         float preprofit = Float.valueOf(bkpBatchrecord1.getPreprofit());//预盈利数据
         float profitamount = Float.valueOf(bkpBatchrecord1.getProfitamount());///当前批次的盈亏
         float fpyl = Float.valueOf(bkpBatchrecord1.getFpyl());//当前批次的分批天预计盈利()
         if (profitamount>=preprofit){//该批次盈利大于预盈利数据时//  修改该批次状态为已经结束
             bkpBatchrecord1.setDqzt("2");//已经结束
             SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
             bkpBatchrecord1.setEndtime(sim.format(new Date()));//结束时间
             bkpBatchrecordService.update(bkpBatchrecord1);//修改当前批次
             //同时修改其他的为未开始的状态的批次预计盈利,以及批次天预计盈利
             BkpBatchrecord bkpBatchrecord2=new BkpBatchrecord();
             bkpBatchrecord2.setDqzt("0");
             bkpBatchrecord2.setModeid(Long.valueOf(bkpUserbets.getGdmsid()));
             List<BkpBatchrecord> list = bkpBatchrecordService.findList(bkpBatchrecord2);//当前固定模式下的所有的未开始批次
             for (BkpBatchrecord s:list){
                 DecimalFormat df2 = new DecimalFormat("#.00");
                 float aa=(zje-profitamount1)/list.size();
                 aa=Float.valueOf(df2.format(aa));
                 s.setPreprofit(String.valueOf(aa));//预计盈利每个批次  当前固定模式剩余的盈利除以剩下的批次
                 float bb=(zje-profitamount1)/(list.size()*pct);
                 long vb=s.getBatch();
                 if (vb==batch){//最后一批
                     if (bbb!=0){
                         bb=(zje-profitamount1)/(list.size()*(pct+bbb));
                     }
                 }
                 bb=Float.valueOf(df2.format(bb));
                 s.setFpyl(String.valueOf(bb));//预计盈利天
                 bkpBatchrecordService.update(s);
             }
         }else {//该批次盈利小于预盈利数据时,修改当天批次盈利
             float pcxh = Float.valueOf(bkpUserbets.getPcxh());//批次序号
             if (pct==pcxh){//就是最后一批次天了
             }else {
                 long vb=bkpBatchrecord1.getBatch();
                 if (vb==batch){//最后一个批次
                     if (bbb!=0){
                         pct=pct+bbb;
                     }
                 }
                 DecimalFormat df2 = new DecimalFormat("#.00");
                 String format = df2.format((preprofit-profitamount)/(pct-pcxh));   // /1
                 bkpBatchrecord1.setFpyl(format);
                bkpBatchrecordService.update(bkpBatchrecord1);
             }
         }
     }

     /*
     如果盈利金额大于当前的预期结束当前的批次或者固定盈利模式
      */
     public void endBatch(BkpUserbets bkpUserbets){
         //固定模式
         BkpFixedprofit bkpFixedprofit=new BkpFixedprofit();
         bkpFixedprofit.setId(String.valueOf(bkpUserbets.getGdmsid()));
         BkpFixedprofit bkpFixedprofit1 = bkpFixedprofitService.get(bkpFixedprofit);
         float profitamount1 =  Float.valueOf(bkpFixedprofit1.getProfitamount());//已经盈利金额
         float totalloss1 = Float.valueOf(bkpFixedprofit1.getTotalloss()); //历史亏损金额
         float monthamount1 = Float.valueOf(bkpFixedprofit1.getMonthamount());
         float zje=totalloss1+monthamount1;
         if (profitamount1>=zje){//  查看是否还有待开奖的  然后判断是否修改状态
             int count=bkpFixedprofitService.selectBets(bkpFixedprofit1.getId());
             if (count==0){
                 bkpFixedprofit1.setDeletelogo(0L);//结束了
                 SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
                 bkpFixedprofit1.setEndtime(sim.format(new Date()));
                 bkpFixedprofitService.update(bkpFixedprofit1);
                 BkpBatchrecord bkpBatchrecord005=new BkpBatchrecord();
                 bkpBatchrecord005.setModeid(Long.valueOf(bkpFixedprofit1.getId()));
                 bkpBatchrecord005.setDqzt("1");
                 List<BkpBatchrecord> bkpBatchrecordList=bkpBatchrecordService.findList(bkpBatchrecord005);
                 for (BkpBatchrecord kk:bkpBatchrecordList){
                     kk.setDqzt("2");
                     kk.setEndtime(sim.format(new Date()));
                     bkpBatchrecordService.update(kk);
                 }
             }
         }
         BkpBatchrecord bkpBatchrecord=new BkpBatchrecord();
         bkpBatchrecord.setId(String.valueOf(bkpUserbets.getPcjlid()));
         BkpBatchrecord bkpBatchrecord1 = bkpBatchrecordService.get(bkpBatchrecord);
         float profitamount = Float.valueOf(bkpBatchrecord1.getProfitamount());//当前盈亏的金额
         float preprofit = Float.valueOf(bkpBatchrecord1.getPreprofit());//预盈利的金额
         if(profitamount>=preprofit){
             bkpBatchrecord1.setDqzt("2");//结束
             SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
             bkpBatchrecord1.setEndtime(sim.format(new Date()));
             bkpBatchrecordService.update(bkpBatchrecord1);
         }
     }


}
