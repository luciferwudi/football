/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.shiro.realms.B;
import com.jeesite.modules.bkp.entity.*;
import com.jeesite.modules.bkp.service.BkpBatchrecordService;
import com.jeesite.modules.bkp.service.BkpDetailService;
import com.jeesite.modules.bkp.service.BkpFixedprofitService;
import com.jeesite.modules.odds.web.TestController002;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.utils.ListUtils;
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
import com.jeesite.modules.bkp.service.BkpUserbetsService;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户下注表Controller
 *
 * @author Lucifer
 * @version 2020-09-02
 */
@Controller
@RequestMapping(value = "${adminPath}/bkp/bkpUserbets")
public class BkpUserbetsController extends BaseController {

    @Autowired
    private BkpUserbetsService bkpUserbetsService;
    @Autowired
    private TestController002 testController002;
    @Autowired
    private BkpBatchrecordService bkpBatchrecordService;
    @Autowired
    private BkpFixedprofitService bkpFixedprofitService;
    @Autowired
    private TimeUpdate timeUpdate;
    @Autowired
    private ListUtils listUtils;
    @Autowired
    private BkpDetailService bkpDetailService;

    /**
     * 获取数据
     */
    @ModelAttribute
    public BkpUserbets get(String id, boolean isNewRecord) {
        return bkpUserbetsService.get(id, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("bkp:bkpUserbets:view")
    @RequestMapping(value = {"list", ""})
    public String list(BkpUserbets bkpUserbets, Model model) {
        model.addAttribute("bkpUserbets", bkpUserbets);
        User user = UserUtils.getUser();
        if (user.getUserCode().equals("poor_ztt7")) {
            return "modules/bkp/bkpUserbetsList1";
        } else {
            return "modules/bkp/bkpUserbetsList";
        }

    }

    @GetMapping(value = "generateRandom")
    @ResponseBody
    public Object generateRandom() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return machineId + String.format("%015d", hashCodeV);
    }

    @PostMapping(value = "saveInformation")
    @ResponseBody
    public Object saveInformation(BkpUserbets bkpUserbets) {
        try {
            User user = UserUtils.getUser();
            bkpUserbets.setUsercode(user.getUserCode());
            SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            bkpUserbets.setCreatetime(smf.format(date));
            Long pcjlid = bkpUserbets.getPcjlid();
            //查询是否已有数据
            //测试室生死时速
            System.out.println("pcjlid" + pcjlid);
            List<BkpUserbets> list = bkpUserbetsService.getById(pcjlid);
            System.out.println(list.size() + "list.size");
            if (list.size() == 0) {
                BkpBatchrecord bkpBatchrecord = new BkpBatchrecord();
                bkpBatchrecord.setId(String.valueOf(pcjlid));
                BkpBatchrecord bkpBatchrecord1 = bkpBatchrecordService.get(bkpBatchrecord);
                bkpBatchrecord1.setStarttime(smf.format(date));
                bkpBatchrecord1.setDqzt("1");
                bkpBatchrecordService.update(bkpBatchrecord1);
            }
            bkpUserbetsService.save(bkpUserbets);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping(value = "saveInformation1")
    @ResponseBody
    public Object saveInformation1(BkpUserbets bkpUserbets) {
        try {
            User user = UserUtils.getUser();
            bkpUserbets.setUsercode(user.getUserCode());
            SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            bkpUserbets.setPcxh("0");
            bkpUserbets.setCreatetime(smf.format(date));
            bkpUserbetsService.save(bkpUserbets);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping(value = "updatedqzt")
    public void updatedqzt() {
        User user = UserUtils.getUser();
        //根据 usercode查询
        List<BkpBatchrecord> list = bkpBatchrecordService.selectByUserCode(user.getUserCode());
        for (BkpBatchrecord s : list) {
            s.setDqzt("1");
            bkpBatchrecordService.update(s);
        }
    }

    //    public void timedTask() throws IOException {
//        List<BkpUserbets> list=bkpUserbetsService.selectAllNullMatchResults();
//        for (BkpUserbets s:list){
//			Map<String,Object> result= (Map<String, Object>) testController002.getGameData(s.getBdate(), s.getNum());
//			if (null==result.get("result")){
//				continue;
//			}else {
//				s.setMatchresults((String) result.get("result"));
//				bkpUserbetsService.update(s);
//			}
//        }
//    }
    /**
     * 查询列表数据
     */
    @RequiresPermissions("bkp:bkpUserbets:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<BkpUserbetsVO> listData(BkpUserbets bkpUserbets, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long a = System.currentTimeMillis();
        User user = UserUtils.getUser();
        List<Map> list = new ArrayList<>();
        Page<BkpUserbetsVO> page1 = new Page<>(request, response);
        int pageNo = page1.getPageNo();
        int pageSize = page1.getPageSize();
        if (bkpUserbets.getPcjlid() != null) {
            list = bkpUserbetsService.selectCombinationAndPcjlid(user.getUserCode(), bkpUserbets.getPcjlid());
        } else {
            list = bkpUserbetsService.selectCombination(user.getUserCode());
        }
		pageSize = 10;
		List<Map> list01=listUtils.Pager(pageSize, pageNo, list);
        List<BkpUserbetsVO> pagelist = new ArrayList<>();
        Long b = System.currentTimeMillis();
        System.out.println(b - a + "第一阶段花费时间");
        Long a1 = System.currentTimeMillis();
        for (Map s : list01) {
            BkpUserbetsVO bkpUserbetsVO = new BkpUserbetsVO();
            List<BkpUserbets> list1 = bkpUserbetsService.selectByCombination(Long.valueOf((String) s.get("combination")));
            if (list1.size() == 0) {
                continue;
            }
            Map map = new HashMap();
            String matchResults = "";
            String yhxzjg = "";
            boolean flag = true;
            for (BkpUserbets f : list1) {
                map.put(f.getNum(), f.getMatchresults());
//                yhxzjg+=f.getBdate()+f.getNum()+f.getHometeam()+"VS"+f.getAwayteam()+"选择:"+f.getPick()+";";
                yhxzjg += f.getNum() + f.getHometeam() + "VS" + f.getAwayteam() + "选择:" + f.getPick() + ";";
                if (f.getMatchresults().equals("待开奖")) {
                    matchResults += "待开奖";
                } else {
                    int i = f.getPick().indexOf(f.getMatchresults());
                    if (i < 0) {
                        flag = false;
                    }
                    matchResults += f.getMatchresults();
                }
                matchResults += ";";
            }
            if (user.getUserCode().equals("poor_ztt7")) {
                bkpUserbetsVO.setXzsm(list1.get(0).getXzsm());
            }
            bkpUserbetsVO.setId(list1.get(0).getId());
            bkpUserbetsVO.setGdmsmc(bkpUserbetsService.selectByGdylmsId(list1.get(0).getGdmsid()));
            bkpUserbetsVO.setDqpc(bkpUserbetsService.selectByPcjlId(list1.get(0).getPcjlid()));
            bkpUserbetsVO.setDqpcxh(list1.get(0).getPcxh());
            bkpUserbetsVO.setBetamount(list1.get(0).getBetamount());
            bkpUserbetsVO.setCombination(Long.valueOf((String) s.get("combination")));
            bkpUserbetsVO.setCreatetime(list1.get(0).getCreatetime());
            bkpUserbetsVO.setMultiple(list1.get(0).getMultiple());
            bkpUserbetsVO.setBettingmethod(list1.get(0).getBettingmethod());
            bkpUserbetsVO.setLlzg(list1.get(0).getLlzg());
            bkpUserbetsVO.setPctyj(list1.get(0).getPctyj());
            bkpUserbetsVO.setPctylbl(list1.get(0).getPctylbl());
			if (flag){ // 两个情况  待开奖,
				if (matchResults.indexOf("待开奖")>=0){ //说明都是待开奖
					bkpUserbetsVO.setTzjg("待开奖");
					bkpUserbetsVO.setDqykje("0");//当前批次盈利金额都没有出  所以暂为0
				}else { //中注
					bkpUserbetsVO.setTzjg("中注");
					bkpUserbetsVO.setDqykje(list1.get(0).getCurrentprofitamount());
				}
			}else { //没有每场比赛都中   存在三种情况  里面有待开奖的, 一场比赛都没中的,  中了单场的
				if (matchResults.indexOf("待开奖")>=0) {  //还没出完整的结果
					bkpUserbetsVO.setTzjg("待开奖");
					bkpUserbetsVO.setDqykje("0");
				}else {
					//查看是否该下面某个组合中注了
					BkpDetail bkpDetail = new BkpDetail();
					bkpDetail.setCombination(String.valueOf(s));
					List<BkpDetail> list2 = bkpDetailService.findList(bkpDetail);
					int sum01 = 0;
					for (BkpDetail s3 : list2) {
						String combinedresult = s3.getCombinedresult(); //周四022(胜)x周四023(胜)x
						String[] arr = combinedresult.split("x"); //周四022(胜)  周四023(胜)
						boolean flag1 = true;
						for (String f : arr) {
							String num = f.substring(0, 5);//周四022
							String selectresults = f.substring(6, 7);//选择结果
							String Matchresults = (String) map.get(num);//比赛结果
							if (!selectresults.equals(Matchresults)) {
								flag1 = false;
							}
						}
						if (flag1) {
							sum01++;
						}
					}
					if (sum01 > 0) {
						bkpUserbetsVO.setTzjg("中注");
						bkpUserbetsVO.setDqykje(list1.get(0).getCurrentprofitamount());
					} else {
						bkpUserbetsVO.setTzjg("未中注");
						bkpUserbetsVO.setDqykje(list1.get(0).getCurrentprofitamount());
					}
				}
			}
            bkpUserbetsVO.setMatchresults(matchResults);
            bkpUserbetsVO.setPick(yhxzjg);
            pagelist.add(bkpUserbetsVO);
        }
        Long b1 = System.currentTimeMillis();
        System.out.println(b1 - a1 + "第二阶段花费时间");
        Long a2 = System.currentTimeMillis();
        List<BkpUserbetsVO> pager = listUtils.Pager(pageSize, pageNo, pagelist);  //这种方式数据量太大的情况下不要使用
        page1.setCount(list.size());
        page1.setList(pager);
        page1.setPageSize(pageSize);
        Long b2 = System.currentTimeMillis();
        System.out.println(b2 - a2 + "第三阶段花费时间");
        return page1;
    }

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    @PostMapping(value = "queryBets")
    @ResponseBody
    public Object queryBets(BkpUserbets bkpUserbets) {
        BkpUserbets bkpUserbets1 = bkpUserbetsService.get(bkpUserbets);
        BkpUserbets bkpUserbets2 = new BkpUserbets();
        bkpUserbets2.setCombination(bkpUserbets1.getCombination());
        return bkpUserbetsService.findList(bkpUserbets2);
    }

    public double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("bkp:bkpUserbets:view")
    @RequestMapping(value = "form")
    public String form(BkpUserbets bkpUserbets, Model model) {
        model.addAttribute("bkpUserbets", bkpUserbets);
        return "modules/bkp/bkpUserbetsForm";
    }

    /**
     * 保存数据
     */
    @RequiresPermissions("bkp:bkpUserbets:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated BkpUserbets bkpUserbets) {
        bkpUserbetsService.save(bkpUserbets);
        return renderResult(Global.TRUE, text("保存用户下注表成功！"));
    }

    /**
     * 删除数据
     */
    @RequiresPermissions("bkp:bkpUserbets:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(BkpUserbets bkpUserbets) {
        List<BkpUserbets> list = bkpUserbetsService.findList(bkpUserbets);
        if (null == list.get(0).getCombination() || list.get(0).getCombination().equals("")) {
            return "没有组合编号";
        } else {
            BkpUserbets bkpUserbets1 = new BkpUserbets();
            bkpUserbets1.setCombination(list.get(0).getCombination());
            List<BkpUserbets> list1 = bkpUserbetsService.findList(bkpUserbets1);
            if (null == list.get(0).getGdmsid()) {
                for (BkpUserbets s : list1) {
                    bkpUserbetsService.delete(s);
                }
                //辅助表也得全部删除
                BkpDetail bkpDetail = new BkpDetail();
                bkpDetail.setCombination(list.get(0).getCombination());
                List<BkpDetail> list2 = bkpDetailService.findList(bkpDetail);
                for (BkpDetail s : list2) {
                    bkpDetailService.delete(s);
                }
            } else {
                BkpFixedprofit bkpFixedprofit = bkpFixedprofitService.findByid(String.valueOf(list.get(0).getGdmsid()));
                int num = Integer.valueOf(bkpFixedprofit.getYtzje());
                for (BkpUserbets s : list1) {
                    bkpUserbetsService.delete(s);
                }
                num -= list1.get(0).getBetamount();
                bkpFixedprofit.setYtzje(String.valueOf(num));
                bkpFixedprofitService.update(bkpFixedprofit);//固定盈利模式删除了已经投注金额
                //辅助表也得全部删除
                BkpDetail bkpDetail = new BkpDetail();
                bkpDetail.setCombination(list.get(0).getCombination());
                List<BkpDetail> list2 = bkpDetailService.findList(bkpDetail);
                for (BkpDetail s : list2) {
                    bkpDetailService.delete(s);
                }
            }
            return renderResult(Global.TRUE, text("删除用户下注表成功！"));
        }
    }

}