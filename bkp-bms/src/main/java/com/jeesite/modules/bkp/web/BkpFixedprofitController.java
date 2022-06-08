/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.bkp.entity.BkpBatchrecord;
import com.jeesite.modules.bkp.entity.BkpUserbets;
import com.jeesite.modules.bkp.entity.BkpUserbetsVO;
import com.jeesite.modules.bkp.service.BkpBatchrecordService;
import com.jeesite.modules.bkp.service.BkpUserbetsService;
import com.jeesite.modules.odds.web.TestController002;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.utils.TimeUpdate;
import io.swagger.annotations.ApiOperation;
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
import com.jeesite.modules.bkp.entity.BkpFixedprofit;
import com.jeesite.modules.bkp.service.BkpFixedprofitService;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 固定盈利模式Controller
 *
 * @author Lucifer
 * @version 2020-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/bkp/bkpFixedprofit")
public class BkpFixedprofitController extends BaseController {

    @Autowired
    private BkpFixedprofitService bkpFixedprofitService;
    @Autowired
    private BkpUserbetsService bkpUserbetsService;

    @Autowired
    private BkpBatchrecordService bkpBatchrecordService;
    @Autowired
    private TimeUpdate timeUpdate;
    @Autowired
    private TestController002 testController002;
    @Autowired
    private BkpBatchrecordController bkpBatchrecordController;


    /**
     * 获取数据
     */
    @ModelAttribute
    public BkpFixedprofit get(String id, boolean isNewRecord) {
        return bkpFixedprofitService.get(id, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("bkp:bkpFixedprofit:view")
    @RequestMapping(value = {"list", ""})
    public String list(BkpFixedprofit bkpFixedprofit, Model model) throws IOException {
        model.addAttribute("bkpFixedprofit", bkpFixedprofit);

        return "modules/bkp/bkpFixedprofitList";
    }
    @RequestMapping(value = "lb")
    public String web01() {
        return "modules/user/lb";
    }

    @ApiOperation("主客场赔率分析区间")
    @PostMapping("/analysisInterval")
    public Object analysisInterval(@RequestParam Map param){
        return bkpFixedprofitService.analysisInterval(Double.valueOf(param.get("inputV1").toString()),Double.valueOf(param.get("inputV2").toString()),param.get("value").toString());
    }
    @GetMapping(value = "userQuery")
    @ResponseBody
    public Object userQuery() throws ParseException {
        User user = UserUtils.getUser();//获取当前登录用户
        BkpFixedprofit bkpFixedprofit = bkpFixedprofitService.userQuery(user.getUserCode());
        Map map = new HashMap();
        if (null == bkpFixedprofit) {
            return "";
        }
        map.put("modeid", bkpFixedprofit.getId());
        Map queryrecording = (Map) bkpBatchrecordController.queryrecording(map);
        bkpFixedprofit.setFpdt(String.valueOf(queryrecording.get("fpdt")));
        return bkpFixedprofit;
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("bkp:bkpFixedprofit:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<BkpFixedprofit> listData(BkpFixedprofit bkpFixedprofit, HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = UserUtils.getUser();
        bkpFixedprofit.setUsercode(user.getUserCode());
        bkpFixedprofit.setPage(new Page<>(request, response));
//        List<BkpFixedprofit> list = bkpFixedprofitService.findList(bkpFixedprofit);

//        timedTask(user.getUserCode()); //暂时先注释
        Page<BkpFixedprofit> page = bkpFixedprofitService.findPage(bkpFixedprofit);
        return page;
    }

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public void timedTask(String userCode) throws Exception {
        List<BkpUserbets> list = bkpUserbetsService.selectAllNullMatchResults(userCode);
        for (BkpUserbets s : list) {
            boolean flag = true;
            Map<String, Object> result = (Map<String, Object>) testController002.getGameData(s.getBdate(), s.getNum());
            if (null == result.get("result")) {
                continue;
            } else {
                s.setMatchresults((String) result.get("result"));
                bkpUserbetsService.update(s);
                BkpUserbets bkpUserbets = new BkpUserbets();
                bkpUserbets.setCombination(s.getCombination());
                List<BkpUserbets> list1 = bkpUserbetsService.findList(bkpUserbets);
                for (BkpUserbets f : list1) {  //查看这个是否都有比赛结果了  如果有了  那就开始往批次上加盈亏金额
                    if ("待开奖".equals(f.getMatchresults())) {
                        flag = false;
                    }
                }
                if (flag) {
                    //执行方法
//                    timeUpdate.updateStatus();
//                    timeUpdate.updateykje(s);
                    timeUpdate.freedomOfPlay(s);
                }
            }
        }

    }


//    public Page<BkpUserbetsVO> fh(){
//        User user= UserUtils.getUser();
//        List<Long> list=bkpUserbetsService.selectCombination(user.getUserCode());
//        List<BkpUserbetsVO> pagelist=new ArrayList<>();
//        for (Long s:list){
//            BkpUserbetsVO bkpUserbetsVO=new BkpUserbetsVO();
//            List<BkpUserbets> list1=bkpUserbetsService.selectByCombination(s);
//            String matchResults="";
//            String yhxzjg="";
//            for (BkpUserbets f:list1){
//                bkpUserbetsVO.setId(f.getId());
//                bkpUserbetsVO.setBetamount(f.getBetamount());
//                bkpUserbetsVO.setCombination(s);
//                bkpUserbetsVO.setCreatetime(f.getCreatetime());
//                bkpUserbetsVO.setMultiple(f.getMultiple());
//                bkpUserbetsVO.setBettingmethod(f.getBettingmethod());
//                yhxzjg+=f.getBdate()+f.getNum()+f.getHometeam()+"VS"+f.getAwayteam()+"选择:"+f.getPick()+";";
//                if(null==f.getMatchresults()){
//                    matchResults+="未开赛";
//                }else{
//                    matchResults+=f.getMatchresults();
//                }
//            }
//            matchResults+=";";
//
//            bkpUserbetsVO.setMatchresults(matchResults);
//            bkpUserbetsVO.setTzjg("待开奖");
//            bkpUserbetsVO.setPick(yhxzjg);
//            pagelist.add(bkpUserbetsVO);
//        }
//        Page<BkpUserbetsVO> page1 =new Page<>();
//        page1.setCount(list.size());
//        page1.setList(pagelist);
//        return page1;
//    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("bkp:bkpFixedprofit:view")
    @RequestMapping(value = "form")
    public String form(BkpFixedprofit bkpFixedprofit, Model model) {
        model.addAttribute("bkpFixedprofit", bkpFixedprofit);
        return "modules/bkp/bkpFixedprofitForm";
    }


    /**
     * 保存数据
     */
    @RequiresPermissions("bkp:bkpFixedprofit:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated BkpFixedprofit bkpFixedprofit) {
        if (null != bkpFixedprofit.getId()) {//修改
            float profitamount1 = Float.valueOf(bkpFixedprofit.getProfitamount());//已经盈利的
            float totalloss1 = Float.valueOf(bkpFixedprofit.getTotalloss());//修改后的亏损金额
            float monthamount1 = Float.valueOf(bkpFixedprofit.getMonthamount());//修改后的月预盈利金额
            if ((totalloss1 + monthamount1) < profitamount1) {
                bkpFixedprofit.setDeletelogo(0L); //结束
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                bkpFixedprofit.setEndtime(sim.format(new Date()));
            }
            bkpFixedprofitService.save(bkpFixedprofit);
            //其余的dqzt为0的预盈利金额以及批次(天) 都得改
            BkpFixedprofit bkpFixedprofit1 = new BkpFixedprofit();
            bkpFixedprofit1.setId(bkpFixedprofit.getId());
            BkpFixedprofit bkpFixedprofit2 = bkpFixedprofitService.get(bkpFixedprofit1);//修改之后的固定盈利模式
            float profitamount = Float.valueOf(bkpFixedprofit2.getProfitamount());//已经盈利的
            float totalloss = Float.valueOf(bkpFixedprofit2.getTotalloss());//修改后的亏损金额
            float monthamount = Float.valueOf(bkpFixedprofit2.getMonthamount());//修改后的月预盈利金额
            float cycle01 = Float.valueOf(bkpFixedprofit2.getCycle());//周期
            Long batch = bkpFixedprofit2.getBatch();//批次
            int pct = (int) (cycle01 / batch);//如30/5=6 每个批次6天
            int bbb = (int) (cycle01 % batch);
            float zje = totalloss + monthamount;
            BkpBatchrecord bkpBatchrecord = new BkpBatchrecord();
            bkpBatchrecord.setDqzt("0");
            bkpBatchrecord.setModeid(Long.valueOf(bkpFixedprofit.getId()));
            List<BkpBatchrecord> list = bkpBatchrecordService.findList(bkpBatchrecord);//拿到了该id下的未开始的数据 进行修改
            for (BkpBatchrecord s : list) {
                DecimalFormat df2 = new DecimalFormat("#.00");
                float aa = (zje - profitamount) / list.size();
                aa = Float.valueOf(df2.format(aa));
                s.setPreprofit(String.valueOf(aa));
                float bb = (zje - profitamount) / (list.size() * pct);
                long vb = s.getBatch();
                if (vb == batch) {//最后一批
                    if (bbb != 0) {
                        bb = (zje - profitamount) / (list.size() * (pct + bbb));
                    }
                }
                bb = Float.valueOf(df2.format(bb));
                s.setFpyl(String.valueOf(bb));//预计盈利天
                bkpBatchrecordService.update(s);
            }
            return renderResult(Global.TRUE, text("保存固定盈利模式成功！"));
        } else {
            User user = UserUtils.getUser();
            SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            bkpFixedprofit.setRetrieveinbatches(bkpFixedprofit.getBatch());
            bkpFixedprofit.setCreattime(myFmt2.format(date));
            bkpFixedprofit.setStarttime(myFmt2.format(date));
            bkpFixedprofit.setUsercode(user.getUserCode());
            bkpFixedprofit.setYtzje("0");
            bkpFixedprofit.setDeletelogo(1L);
            bkpFixedprofitService.save(bkpFixedprofit);
        }

        return renderResult(Global.TRUE, text("保存固定盈利模式成功！"));
    }

    @RequiresPermissions("bkp:bkpFixedprofit:edit")
    @PostMapping(value = "save1")
    @ResponseBody
    public BkpFixedprofit save1(@Validated BkpFixedprofit bkpFixedprofit) {
        User user = UserUtils.getUser();
        SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        bkpFixedprofit.setRetrieveinbatches(bkpFixedprofit.getBatch());
        bkpFixedprofit.setCreattime(myFmt2.format(date));
        bkpFixedprofit.setStarttime(myFmt2.format(date));
        bkpFixedprofit.setUsercode(user.getUserCode());
        bkpFixedprofit.setYtzje("0");
        bkpFixedprofit.setProfitamount("0");
        bkpFixedprofit.setDeletelogo(1L);
        bkpFixedprofitService.save(bkpFixedprofit);
        BkpFixedprofit bkpFixedprofit1 = bkpFixedprofitService.get(bkpFixedprofit);
        return bkpFixedprofit1;
    }

    @PostMapping(value = "updateTzje")
    public void updateTzje(@RequestParam String id, @RequestParam String betamount) {
        BkpFixedprofit bkpFixedprofit1 = bkpFixedprofitService.findByid(id);
        Integer integer = Integer.valueOf(betamount);
        Integer integer1 = Integer.valueOf(bkpFixedprofit1.getYtzje());
        bkpFixedprofit1.setYtzje(String.valueOf(integer + integer1));
        bkpFixedprofitService.update(bkpFixedprofit1);
    }

    @PostMapping(value = "queryGame")
    @ResponseBody
    public Object queryGame(@RequestParam List<String> id) {

        return bkpFixedprofitService.queryGame(id);
    }

    @PostMapping(value = "saveInformation")
    @ResponseBody
    public Object saveInformation(@RequestParam String result) {

        return result;
    }

    /**
     * 删除数据
     */
    @RequiresPermissions("bkp:bkpFixedprofit:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(BkpFixedprofit bkpFixedprofit) {
        bkpFixedprofitService.delete(bkpFixedprofit);
        return renderResult(Global.TRUE, text("删除固定盈利模式成功！"));
    }

}