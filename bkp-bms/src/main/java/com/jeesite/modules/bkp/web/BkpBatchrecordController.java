/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.bkp.entity.BkpFixedprofit;
import com.jeesite.modules.bkp.entity.BkpUserbets;
import com.jeesite.modules.bkp.service.BkpFixedprofitService;
import com.jeesite.modules.bkp.service.BkpUserbetsService;
import com.jeesite.modules.odds.web.TestController002;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.utils.TimeUpdate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bkp.entity.BkpBatchrecord;
import com.jeesite.modules.bkp.service.BkpBatchrecordService;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 批次记录表Controller
 *
 * @author Lucifer
 * @version 2020-09-03
 */
@Controller
@RequestMapping(value = "${adminPath}/bkp/bkpBatchrecord")
public class BkpBatchrecordController extends BaseController {

    @Autowired
    private BkpBatchrecordService bkpBatchrecordService;
    @Autowired
    private BkpUserbetsService bkpUserbetsService;
    @Autowired
    private TestController002 testController002;
    @Autowired
    private BkpFixedprofitService bkpFixedprofitService;
    @Autowired
    private TimeUpdate timeUpdate;

    /**
     * 获取数据
     */
    @ModelAttribute
    public BkpBatchrecord get(String id, boolean isNewRecord) {
        return bkpBatchrecordService.get(id, isNewRecord);
    }

    /**
     * 查询列表
     */
//	@RequiresPermissions("bkp:bkpBatchrecord:view")
    @RequestMapping(value = {"list", ""})
    public String list(BkpBatchrecord bkpBatchrecord, Model model) {
        model.addAttribute("bkpBatchrecord", bkpBatchrecord);

        return "modules/bkp/bkpBatchrecordList";
    }

    /**
     * 查询列表数据
     */
//	@RequiresPermissions("bkp:bkpBatchrecord:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<BkpBatchrecord> listData(BkpBatchrecord bkpBatchrecord, HttpServletRequest request, HttpServletResponse response) throws IOException {
        bkpBatchrecord.setPage(new Page<>(request, response));
        bkpBatchrecord.setOrderBy("starttime DESC");
        User user = UserUtils.getUser();
        bkpBatchrecord.setUserCode(user.getUserCode());
        Page<BkpBatchrecord> page = new Page<>(request, response);
        List<BkpBatchrecord> list = bkpBatchrecordService.findList1(bkpBatchrecord);
        page.setList(list);
        page.setCount(list.size());
        return page;
    }

    public double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    //查看盈利模式的亏损金额
    @RequestMapping(value ="amountOfLoss")
    @ResponseBody
    public Object amountOfLoss(@RequestParam String id){
        BkpFixedprofit bkpFixedprofit = bkpFixedprofitService.getBypcId(id);
        return bkpFixedprofit.getProfitamount();
    }

    /**
     * 查看编辑表单
     */
//	@RequiresPermissions("bkp:bkpBatchrecord:view")
    @RequestMapping(value = "form")
    public String form(BkpBatchrecord bkpBatchrecord, Model model) {
        model.addAttribute("bkpBatchrecord", bkpBatchrecord);
        return "modules/bkp/bkpBatchrecordForm";
    }

    //	@RequiresPermissions("bkp:bkpBatchrecord:view")
    @RequestMapping(value = "form1")
    public String form1(BkpBatchrecord bkpBatchrecord, Model model) {
        model.addAttribute("bkpBatchrecord", bkpBatchrecord);
        return "modules/bkp/bkpBatchrecordList";
    }

    /**
     * 保存数据
     */
//	@RequiresPermissions("bkp:bkpBatchrecord:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated BkpBatchrecord bkpBatchrecord) {
        User user = UserUtils.getUser();
        bkpBatchrecord.setUserCode(user.getUserCode());
        bkpBatchrecord.setDqzt("0");
        bkpBatchrecordService.save(bkpBatchrecord);
        return renderResult(Global.TRUE, text("保存批次记录表成功！"));
    }

    //	@RequiresPermissions("bkp:bkpBatchrecord:edit")
    @PostMapping(value = "save1")
    @ResponseBody
    public BkpBatchrecord save1(@Validated BkpBatchrecord bkpBatchrecord) {
        User user = UserUtils.getUser();
        BkpFixedprofit bkpFixedprofit = new BkpFixedprofit();
        bkpFixedprofit.setId(String.valueOf(bkpBatchrecord.getModeid()));
        BkpFixedprofit bkpFixedprofit1 = bkpFixedprofitService.get(bkpFixedprofit);
        System.out.println("批次批次" + bkpBatchrecord.getBatch());
        System.out.println("固定模式批次" + bkpFixedprofit1.getBatch());
        int a = bkpBatchrecord.getBatch();
        long b = bkpFixedprofit1.getBatch();
        if (a == b) {
            float cycle01 = Float.valueOf(bkpFixedprofit1.getCycle());//周期
            Long batch = bkpFixedprofit1.getBatch();//批次
            int pct = (int) (cycle01 / batch);//如30/5=6 每个批次6天
            int bbb = (int) (cycle01 % batch);
            if (bbb != 0) {
                DecimalFormat df2 = new DecimalFormat("#.00");
                float preprofit = Float.valueOf(bkpBatchrecord.getPreprofit());
                float fpyl = preprofit / (pct + bbb);
                fpyl = Float.valueOf(df2.format(fpyl));
                bkpBatchrecord.setFpyl(String.valueOf(fpyl));
            }
        }
        DecimalFormat df2 = new DecimalFormat("#.00");
        float preprofit = Float.valueOf(bkpBatchrecord.getPreprofit());
        preprofit = Float.valueOf(df2.format(preprofit));
        bkpBatchrecord.setPreprofit(String.valueOf(preprofit));
        bkpBatchrecord.setUserCode(user.getUserCode());
        bkpBatchrecord.setDqzt("0");
        bkpBatchrecord.setProfitamount("0");
        bkpBatchrecordService.save(bkpBatchrecord);
        BkpBatchrecord bkpBatchrecord1 = bkpBatchrecordService.get(bkpBatchrecord);
        return bkpBatchrecord1;
    }

    @PostMapping(value = "queryrecording")
    @ResponseBody
    public Object queryrecording(@RequestParam Map params) throws ParseException {
        Map map = new HashMap();
        BkpBatchrecord bkpBatchrecord = new BkpBatchrecord();
        bkpBatchrecord.setModeid(Long.valueOf((String) params.get("modeid")));
        //固定盈利模式
        BkpFixedprofit bkpFixedprofit = bkpBatchrecordService.selectById(Long.valueOf((String) params.get("modeid")));
        timeUpdate.changeTheTimeStatus(bkpFixedprofit);
        timeUpdate.modifyBatchStatus(bkpFixedprofit);
        //固定盈利模式下的批次
        BkpBatchrecord bkpBatchrecord1 = bkpBatchrecordService.selectMin(Long.valueOf((String) params.get("modeid")));
        //查询当前所在的批次id
        map.put("id", bkpBatchrecord1.getId());
        map.put("fpdt", bkpBatchrecord1.getFpyl());
        //所在的序号
        Object pcxhobj = bkpBatchrecordService.selectPcxh(bkpBatchrecord1.getId());//最大的批次序号出来了
        if (null == pcxhobj) {
            pcxhobj = 1;
        }
        List<String> createTime = bkpBatchrecordService.selectCreateTime((Integer) pcxhobj, bkpBatchrecord1.getId());
        if (createTime.size() == 0) {
            map.put("pcxh", 1);
        } else {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(date);
            String time = createTime.get(0);
            if (!format.equals(time)) {
                map.put("pcxh", (Integer) pcxhobj + 1);
            } else {
                map.put("pcxh", (Integer) pcxhobj);
            }
        }
        return map;
    }


    public void timing(BkpFixedprofit bkpFixedprofit) throws ParseException {
        bkpFixedprofit.getCreattime();//创建时间
        String cycle = bkpFixedprofit.getCycle();//周期
        Long batch = bkpFixedprofit.getBatch();//批次
        int pct = 0;
        //
        if ((Long.valueOf(cycle)) % batch == 0) {
            pct = (int) ((Long.valueOf(cycle)) / batch);//每个批次的具体多天  如1批次6天
            //查询startTime不为null的 看是否需要修改状态
            List<BkpBatchrecord> list = bkpBatchrecordService.selectBystartTime(bkpFixedprofit.getId());
            SimpleDateFormat mf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();//获取当前时间
            Calendar cal = Calendar.getInstance();
            // 设置开始时间
            String ff = "yyyy-MM-dd";
            String format = mf.format(date);//当前时间
            //cal.add(Calendar.DATE, -10);//减10天
            for (BkpBatchrecord s : list) {
                cal.setTime(mf.parse(s.getStarttime()));
                cal.add(Calendar.DATE, pct - 1);
                String format1 = mf.format(cal.getTime());////加了6天之后的时间
                //时间比较大小
                Integer min1 = getMin1(format, format1, ff);
                if (min1 > 0) {
                    s.setDqzt("2");
                    s.setEndtime(format);
                    //这个模式结束了后  改变其他批次的
                    BkpBatchrecord bkpBatchrecord2 = new BkpBatchrecord();
                    bkpBatchrecord2.setDqzt("0");
                    bkpBatchrecord2.setModeid(Long.valueOf(s.getModeid()));
                    List<BkpBatchrecord> list001 = bkpBatchrecordService.findList(bkpBatchrecord2);//其他还未开始的
                    BkpFixedprofit bkpFixedprofit01 = new BkpFixedprofit();
                    bkpFixedprofit01.setId(String.valueOf(s.getModeid()));
                    BkpFixedprofit bkpFixedprofit1 = bkpFixedprofitService.get(bkpFixedprofit01);
                    float totalloss = Float.valueOf(bkpFixedprofit1.getTotalloss()); //历史亏损金额
                    float monthamount = Float.valueOf(bkpFixedprofit1.getMonthamount());
                    float cycle01 = Float.valueOf(bkpFixedprofit1.getCycle());//周期  30
                    Long batch01 = bkpFixedprofit1.getBatch();//批次
                    float sum = Float.valueOf(s.getProfitamount());
                    float zje = totalloss + monthamount;
                    for (BkpBatchrecord s1 : list001) {
                        float bb = ((zje - sum) / list001.size()) / (cycle01 / batch01);
                        DecimalFormat df12 = new DecimalFormat("#.00");
                        bb = Float.valueOf(df12.format(bb));
                        s1.setFpyl(String.valueOf(bb));
                        float c = (zje - sum) / list001.size();
                        c = Float.valueOf(df12.format(c));
                        s1.setPreprofit(String.valueOf(c));
                        bkpBatchrecordService.update(s1);
                    }
                    bkpBatchrecordService.update(s);
                }
            }
            cal.setTime(mf.parse(bkpFixedprofit.getStarttime()));
            cal.add(Calendar.DATE, Integer.valueOf(cycle) - 1);//加了30天后的时间
            String str = mf.format(cal.getTime());
            int aa = getMin1(format, str, ff);
            if (aa > 0) {
                bkpFixedprofit.setDeletelogo(0L);
                bkpFixedprofit.setEndtime(format);
                bkpFixedprofitService.update(bkpFixedprofit);
            }

        } else {
            int a = (int) ((Long.valueOf(cycle)) / batch);//每个批次的具体多天  如1批次6天
            int b = (int) ((Long.valueOf(cycle)) % batch);
            //查询startTime不为null的 看是否需要修改状态
            List<BkpBatchrecord> list = bkpBatchrecordService.selectBystartTime(bkpFixedprofit.getId());
            SimpleDateFormat mf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();//获取当前时间
            Calendar cal = Calendar.getInstance();
            // 设置开始时间
            String ff = "yyyy-MM-dd";
            String format = mf.format(date);//当前时间
            //cal.add(Calendar.DATE, -10);//减10天
            for (BkpBatchrecord s : list) {
                cal.setTime(mf.parse(s.getStarttime()));
                if (s.getBatch().equals(batch.toString())) {
                    cal.add(Calendar.DATE, a + b - 1);
                } else {
                    cal.add(Calendar.DATE, a - 1);
                }
                String format1 = mf.format(cal.getTime());////加了n天之后的时间
                //时间比较大小
                Integer min1 = getMin1(format, format1, ff);
                if (min1 > 0) {
                    s.setDqzt("2");
                    s.setEndtime(format);
                    //这个模式结束了后  改变其他批次的
                    BkpBatchrecord bkpBatchrecord2 = new BkpBatchrecord();
                    bkpBatchrecord2.setDqzt("0");
                    bkpBatchrecord2.setModeid(Long.valueOf(s.getModeid()));
                    List<BkpBatchrecord> list001 = bkpBatchrecordService.findList(bkpBatchrecord2);//其他还未开始的
                    BkpFixedprofit bkpFixedprofit01 = new BkpFixedprofit();
                    bkpFixedprofit01.setId(String.valueOf(s.getModeid()));
                    BkpFixedprofit bkpFixedprofit1 = bkpFixedprofitService.get(bkpFixedprofit01);
                    float totalloss = Float.valueOf(bkpFixedprofit1.getTotalloss()); //历史亏损金额
                    float monthamount = Float.valueOf(bkpFixedprofit1.getMonthamount());
                    float cycle01 = Float.valueOf(bkpFixedprofit1.getCycle());//周期  30
                    Long batch01 = bkpFixedprofit1.getBatch();//批次
                    float sum = Float.valueOf(s.getProfitamount());
                    float zje = totalloss + monthamount;
                    for (BkpBatchrecord s1 : list001) {
                        float bb = ((zje - sum) / list001.size()) / (cycle01 / batch01);
                        DecimalFormat df12 = new DecimalFormat("#.00");
                        bb = Float.valueOf(df12.format(bb));
                        s1.setFpyl(String.valueOf(bb));
                        float c = (zje - sum) / list001.size();
                        c = Float.valueOf(df12.format(c));
                        s1.setPreprofit(String.valueOf(c));
                        bkpBatchrecordService.update(s1);
                    }
                    bkpBatchrecordService.update(s);
                }
            }
            cal.setTime(mf.parse(bkpFixedprofit.getStarttime()));
            cal.add(Calendar.DATE, Integer.valueOf(cycle) - 1);//加了30天后的时间
            String str = mf.format(cal.getTime());
            int aa = getMin1(format, str, ff);
            if (aa > 0) {
                bkpFixedprofit.setDeletelogo(0L);
                bkpFixedprofit.setEndtime(format);
                bkpFixedprofitService.update(bkpFixedprofit);
            }
        }

    }

    public static Integer getMin1(String date, String date1, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date dateTime1 = dateFormat.parse(date);
        Date dateTime2 = dateFormat.parse(date1);
        int i = dateTime1.compareTo(dateTime2); //返回-1 则是比当前时间小 返回0相等  返回>1则是比当前时间大
        return i;
    }

    /**
     * 删除数据
     */
    @RequiresPermissions("bkp:bkpBatchrecord:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(BkpBatchrecord bkpBatchrecord) {
        bkpBatchrecordService.delete(bkpBatchrecord);
        return renderResult(Global.TRUE, text("删除批次记录表成功！"));
    }

}