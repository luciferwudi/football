package com.jeesite.modules.config;

import com.jeesite.modules.bkp.entity.BkpFixedprofit;
import com.jeesite.modules.bkp.service.BkpFixedprofitService;
import com.jeesite.modules.bkp.web.BkpFixedprofitController;
import com.jeesite.modules.bkp.web.BkpInjuriesController;
import com.jeesite.modules.bkp.web.BkpLbplController;
import com.jeesite.modules.bkp.web.BkpLeisController;
import com.jeesite.modules.odds.web.TestController002;
import com.jeesite.modules.teamdata.web.TeamdataController;
import com.jeesite.modules.utils.RecommendJsoup;
import com.jeesite.modules.utils.TimeUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class timer {

      @Autowired
    private TestController002 testController002;
      @Autowired
      private TeamdataController teamdataController;
      @Autowired
      private BkpFixedprofitController bkpFixedprofitController;
      @Autowired
      private TimeUpdate timeUpdate;
    @Autowired
    private BkpFixedprofitService bkpFixedprofitService;
    @Autowired
    private BkpInjuriesController bkpInjuriesController;
    @Autowired
    private RecommendJsoup recommendJsoup;
    @Autowired
    private BkpLeisController bkpLeisController;
    @Autowired
    private BkpLbplController bkpLbplController;

    //@Scheduled(cron="0 0/5 * * * ?" )//5分钟一次  测试通过  @Scheduled(cron="0 0 1 * * ?")//凌晨一点
    @Scheduled(cron="0 0 0/3 * * ?")//每2小时爬取一次
    public void  timer()  {
        try {
            System.out.println("定时器开始爬取数据");
            testController002.ceshi();
//            recommendJsoup.insertpbkleis(); //雷速设置了反爬机制
//            bkpLeisController.checkTheMatchResult();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Scheduled(cron = "0 0 0/1 * * ?") //每小时执行一次
    public void timer1() {
        try {
            System.out.println("定时器002开始");
            BkpFixedprofit bkpFixedprofit=new BkpFixedprofit();
            bkpFixedprofit.setDeletelogo(1L);//进行中的
            bkpFixedprofit.setStatus("0");//有效的
            List<BkpFixedprofit> list = bkpFixedprofitService.findList(bkpFixedprofit);
            for (BkpFixedprofit s:list){
                timeUpdate.changeTheTimeStatus(s);
                timeUpdate.modifyBatchStatus(s);
            }
            String userCode=null;//查询全部
            bkpFixedprofitController.timedTask(userCode);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Scheduled(cron ="0 0 23 * * ?")
    public  void lb(){
        try {
            DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String ss=sdf.format(new Date());
            bkpLbplController.autoUpdate(ss);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    @Scheduled(cron="0 0/5 * * * ?" )//5分钟一次  测试通过
//    public void  timer1() throws Exception {
//        try {
//            bkpFixedprofitController.timedTask();
//            System.out.println("定时器开始修改状态了");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
    @Scheduled(cron="0 0 1 * * ?")//凌晨一点
    public void getdata(){
        try {
            teamdataController.task();
            bkpInjuriesController.insertData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
