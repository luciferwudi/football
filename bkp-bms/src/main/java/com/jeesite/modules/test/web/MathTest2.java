package com.jeesite.modules.test.web;

/**
 * 从n个数里取出m个数的排列或组合算法实现
 * @author chengesheng
 * @date 2016年9月28日 下午3:18:34
 */

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MathTest2 {

    public static void main(String[] args) {

        String str = "20066|3001|[2.04#3.05#3.16]/20067|3002|[2.04#3.05#3.16]/20068|3003|[2.04#3.05#3.16]/20069|3004|[2.04#3.05#3.16]/20065|3005|[3.04#3.05#3.16]";
         str = "20066|3001|[2.04#3.05]/20067|3002|[2.04#3.05]/20068|3003|[2.04#3.05]/20069|3004|[2.04#3.05#3.16]/20065|3005|[3.04#3.05#3.16]";
        Set set=new HashSet();

        int numb=touzhuSum(5,2,str,1);
        System.out.println(numb);

        /*String[] team1=new String[]{"1","2","3"};
        String[] team2=new String[]{"4","5","6"};
        String[] team3=new String[]{"7","8","9","10"};
        String[] team4=new String[]{"7"};
        String[] team5=new String[]{"7","9"};*/


     /*   // N 串 1
        int n=3;

        int[] teams={team1.length,team2.length,team3.length,team4.length,team5.length};

        //球队数量m，m串n
            combinationSelect(set, new String[]{
                    "0", "1", "2", "3", "4"
            }, n);

        //投注总数
        int touzhuSum=0;
        //球队组合列表
        Iterator<String> it=set.iterator();
        while(it.hasNext()){
            String  s=it.next();
            //球队id
            System.out.println("球队ID---------------------------"+s);
            String[] ss = s.split(",");
            System.out.println(Arrays.toString(ss));
            //选中球队的赔率种类数量
            int[] dd=new int[ss.length];
            for (int i=0;i<ss.length;i++){
                dd[i]=teams[Integer.valueOf(ss[i])];
            }
            //球队排列组合
            int nRet = 1;
            for(int i = 0; i<dd.length;i++)
            {
                System.out.println(i+"==================="+dd[i]);
                nRet *= dd[i];
            }
            touzhuSum+=nRet;
            System.out.println("nRet==="+nRet+" ===sum==="+touzhuSum);
        }*/
    }

    /**
     *  投注总数
     * @param teamSum  球队数量
     * @param ggtype  过关方式
     * @param ggtype   球队选择赔率数据  20066|3001|[2.04#3.05#3.16]/20067|3002|[2.04#3.05#3.16]/20068|3003|[2.04#3.05#3.16]/20069|3004|[2.04#3.05#3.16]/20065|3005|[3.04#3.05#3.16]
     * @param beishu  倍数
     * @return
     */
    public static int touzhuSum(int teamSum,int ggtype,String datas,int beishu) {
        Set set=new HashSet();
      /*  String[] team1=new String[]{"1","2","3"};
        String[] team2=new String[]{"4","5","6"};
        String[] team3=new String[]{"7","8","9","10"};
        String[] team4=new String[]{"7"};
        String[] team5=new String[]{"7","9"};*/
        String[] rates=datas.split("/");
        String[] t0=null,t1=null,t2=null,t3=null,t4=null,t5=null,t6=null,t7=null,t8=null,t9=null,t10=null,t11=null,t12=null,t13=null,t14=null;
        for (int i=0;i<rates.length;i++){
            String strs=rates[i];
            System.out.println("strs==="+strs);
            String[] data=strs.split("\\|");
            System.out.println("data==="+Arrays.toString(data));
            String rate= data[2];
            System.out.println("rate===="+rate);
            rate=rate.replace("[","");
            rate=rate.replace("]","");
            // String ratenew = rate.replace("#",",");
            System.out.println(rate);
            String[] teamratas = rate.split("#");

            if(i==0) t0 =teamratas;
            if(i==1) t1 =teamratas;
            if(i==2) t2 =teamratas;
            if(i==3) t3 =teamratas;
            if(i==4) t4 =teamratas;
            if(i==5) t5 =teamratas;
            if(i==6) t6 =teamratas;
            if(i==7) t7 =teamratas;
            if(i==8) t8 =teamratas;
            if(i==9) t9 =teamratas;
            if(i==10) t10 =teamratas;
            if(i==11) t11 =teamratas;
            if(i==12) t12 =teamratas;
            if(i==13) t13 =teamratas;
            if(i==14) t14 =teamratas;
        }

        int[] teams=new int[teamSum];
        for (int i=0;i<teamSum;i++){
            if(i==0)teams[i]=t0.length;
            if(i==1)teams[i]=t1.length;
            if(i==2)teams[i]=t2.length;
            if(i==3)teams[i]=t3.length;
            if(i==4)teams[i]=t4.length;
            if(i==5)teams[i]=t5.length;
            if(i==6)teams[i]=t6.length;
            if(i==7)teams[i]=t7.length;
            if(i==8)teams[i]=t8.length;
            if(i==9)teams[i]=t9.length;
            if(i==10)teams[i]=t10.length;
            if(i==11)teams[i]=t11.length;
            if(i==12)teams[i]=t12.length;
            if(i==13)teams[i]=t13.length;
            if(i==14)teams[i]=t14.length;
        }

       // int[] teams={team1.length,team2.length,team3.length,team4.length,team5.length};

        //球队定义别名 从0.开始
        String[] teamNum=new String[teamSum];
        for (int i= 0 ;i<teamSum;i++){
            teamNum[i]=""+i;
        }
        //球队数量m，m串n
        combinationSelect(set, teamNum, ggtype);

        //投注总数
        int touzhuSum=0;
        //球队组合列表
        Iterator<String> it=set.iterator();
        while(it.hasNext()){
            String  s=it.next();
            //球队id
            System.out.println("球队ID---------------------------"+s);
            String[] ss = s.split(",");
            System.out.println(Arrays.toString(ss));
            //选中球队的赔率种类数量
            int[] dd=new int[ss.length];
            for (int i=0;i<ss.length;i++){
                dd[i]=teams[Integer.valueOf(ss[i])];
            }
            //球队排列组合
            int nRet = 1;
            for(int i = 0; i<dd.length;i++)
            {
                System.out.println(i+"==================="+dd[i]);
                nRet *= dd[i];
            }
            touzhuSum+=nRet;
            System.out.println("nRet==="+nRet+" ===sum==="+touzhuSum);
        }
    return touzhuSum*beishu;
    }


    /**
     * 排列选择（从列表中选择n个排列）
     * @param dataList 待选列表
     * @param n 选择个数
     */
    public static void arrangementSelect(String[] dataList, int n) {
        System.out.println(String.format("A(%d, %d) = %d", dataList.length, n, arrangement(dataList.length, n)));
        arrangementSelect(dataList, new String[n], 0);
    }

    /**
     * 排列选择
     * @param dataList 待选列表
     * @param resultList 前面（resultIndex-1）个的排列结果
     * @param resultIndex 选择索引，从0开始
     */
    private static void arrangementSelect(String[] dataList, String[] resultList, int resultIndex) {
        int resultLen = resultList.length;
        if (resultIndex >= resultLen) { // 全部选择完时，输出排列结果
            System.out.println(Arrays.asList(resultList));
            return;
        }

        // 递归选择下一个
        for (int i = 0; i < dataList.length; i++) {
            // 判断待选项是否存在于排列结果中
            boolean exists = false;
            for (int j = 0; j < resultIndex; j++) {
                if (dataList[i].equals(resultList[j])) {
                    exists = true;
                    break;
                }
            }
            if (!exists) { // 排列结果不存在该项，才可选择
                resultList[resultIndex] = dataList[i];
                arrangementSelect(dataList, resultList, resultIndex + 1);
            }
        }
    }

    /**
     * 组合选择（从列表中选择n个组合）
     * @param dataList 待选列表
     * @param n 选择个数
     */
    public static void combinationSelect(Set set,String[] dataList, int n) {
        System.out.println(String.format("C(%d, %d) = %d", dataList.length, n, combination(dataList.length, n)));

        combinationSelect(set,dataList, 0, new String[n], 0);

    }

    /**
     * 组合选择
     * @param dataList 待选列表
     * @param dataIndex 待选开始索引
     * @param resultList 前面（resultIndex-1）个的组合结果
     * @param resultIndex 选择索引，从0开始
     */
    private static void combinationSelect(Set set, String[] dataList, int dataIndex, String[] resultList, int resultIndex) {
        int resultLen = resultList.length;
        int resultCount = resultIndex + 1;
        if (resultCount > resultLen) { // 全部选择完时，输出组合结果
            //System.out.println(Arrays.asList(resultList));
            //set.add(Arrays.toString(resultList));
            //数组转字符串 org.apache.commons.lang3.StringUtils
            String str4 = StringUtils.join(resultList, ","); // 数组转字符串(逗号分隔)(推荐)
            set.add(str4);

            return;
        }

        // 递归选择下一个
        for (int i = dataIndex; i < dataList.length + resultCount - resultLen; i++) {
            resultList[resultIndex] = dataList[i];
            combinationSelect(set,dataList, i + 1, resultList, resultIndex + 1);
        }
    }

    /**
     * 计算阶乘数，即n! = n * (n-1) * ... * 2 * 1
     * @param n
     * @return
     */
    public static long factorial(int n) {
        return (n > 1) ? n * factorial(n - 1) : 1;
    }

    /**
     * 计算排列数，即A(n, m) = n!/(n-m)!
     * @param n
     * @param m
     * @return
     */
    public static long arrangement(int n, int m) {
        return (n >= m) ? factorial(n) / factorial(n - m) : 0;
    }

    /**
     * 计算组合数，即C(n, m) = n!/((n-m)! * m!)
     * @param n
     * @param m
     * @return
     */
    public static long combination(int n, int m) {
        return (n >= m) ? factorial(n) / factorial(n - m) / factorial(m) : 0;
    }
}
