package com.ziroom.common.utils;

import com.ziroom.common.exception.ServiceException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * @program: zhumo
 * @description: 数字处理的工具类
 * @author: GuanTao
 * @create: 2019-03-11 17:43
 **/
public class NumberDealUtils {

    public static final String PERCENT_0 = "0%";
    public static final String PERCENT_2 = "0.00%";
    public static final String PATTERN_A_AAA_AA = "#,##0.00";
    public static final String PATTERN_A_AAA = "#,##0";


    /**
     * @Description: 每三位分隔，并且保留三位小数
     * @Param: [number]
     * @return: java.lang.String
     * @Author: GuanTao
     * @Date: 2019/3/11
    **/
    public static String retainThreeDecimals(Object number){
        String format = null;
        if(number != null){
            DecimalFormat df = new DecimalFormat(PATTERN_A_AAA_AA);
            format = df.format(number);
        }
        return format;
    }
    /**
     * @Description: 一个数值，根据选择格式匹配展示
     * @Param: [number]
     * @return: java.lang.String
     * @Author: GuanTao
     * @Date: 2019/3/11
     **/
    public static String retainThreeDecimals(Object number, String pattern){
        String format = "0";
        if(number != null){
            DecimalFormat df = new DecimalFormat(pattern);
            format = df.format(number);
        }
        return format;
    }
    /**
     * @Description: 数字格式转换double
     * @Param: [number]
     * @return: java.lang.String
     * @Author: GuanTao
     * @Date: 2019/3/11
     **/
    public static Double DecimalsStrTransNumber(String number, String pattern){
        DecimalFormat df = new DecimalFormat(pattern);
        try {
            Number parse = df.parse(number);
            return parse.doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ServiceException("字符串转换成Number失败！");
        }
    }
    /**
     * @Description: 求百分比，选择保留小数位
     * @Param: [num1, num2, significand]
     * @return: java.lang.Double
     * @Author:Mr.Guan
     * @Date: 2019/3/21
     * @Time: 10:37
    **/
    public static Double getPercentCount(String num1, String num2, int significand, int roundingMode) {

        BigDecimal rentNumDaysDec = new BigDecimal(num1);
        BigDecimal holdNumDaysDec = new BigDecimal(num2);
        Double RentalRates = rentNumDaysDec.divide(holdNumDaysDec, significand, roundingMode).doubleValue();
        return RentalRates;
    }
    /**
     * @Description: 百分数转换成double
     * @Param: [num1, num2, significand]
     * @return: java.lang.Double
     * @Author:Mr.Guan
     * @Date: 2019/3/21
     * @Time: 10:37
     **/
    public static Double percentCountStrTransNumber(String percentStr) {
        //NumberFormat是一个工厂，可以直接getXXX创建，而getPercentInstance() 是返回当前默认语言环境的百分比格式。
        NumberFormat nf = NumberFormat.getPercentInstance();
         try {
             if("0".equals(percentStr)){
                 return 0d;
             }
             Number m=nf.parse(percentStr);
             return m.doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ServiceException("百分比字符串转换成Number对象失败！");
        }

    }
    /**
     * @Description: 求百分比,返回百分数字符串
     * @Param: [num1, num2, significand]
     * @return: java.lang.Double
     * @Author:Mr.Guan
     * @Date: 2019/3/21
     * @Time: 10:37
     **/
    public static String getPercentCountStr(String num1, String num2, int significand, int roundingMode, String percent) {
        //创建百分数格式对象
        DecimalFormat df = new DecimalFormat(percent);
        BigDecimal rentNumDaysDec = new BigDecimal(num1);
        BigDecimal holdNumDaysDec = new BigDecimal(num2);
        Double RentalRates = rentNumDaysDec.divide(holdNumDaysDec, significand, roundingMode).doubleValue();
        return df.format(RentalRates);

    }
    /**
     * @Description: 两个数乘法
     * @Param: [num1, num2, significand]
     * @return: java.lang.Double
     * @Author:Mr.Guan
     * @Date: 2019/3/21
     * @Time: 10:37
     **/
    public static BigDecimal getMultiplicationStr(String num1, String num2) {
        BigDecimal rentNumDaysDec = new BigDecimal(num1);
        BigDecimal holdNumDaysDec = new BigDecimal(num2);
        BigDecimal result = rentNumDaysDec.multiply(holdNumDaysDec);
        return result;

    }
    /**
     * @Description: 两个数加法
     * @Param: [num1, num2, significand]
     * @return: java.lang.Double
     * @Author:Mr.Guan
     * @Date: 2019/3/21
     * @Time: 10:37
     **/
    public static BigDecimal getAddBigDecimal(String num1, String num2) {
        BigDecimal rentNumDaysDec = new BigDecimal(num1);
        BigDecimal holdNumDaysDec = new BigDecimal(num2);
        BigDecimal result = rentNumDaysDec.add(holdNumDaysDec);
        return result;

    }
    /**
     * @Description: 两个数减法
     * @Param: [num1, num2, significand]
     * @return: java.lang.Double
     * @Author:Mr.Guan
     * @Date: 2019/3/21
     * @Time: 10:37
     **/
    public static BigDecimal getSubtractBigDecimal(String num1, String num2) {
        BigDecimal rentNumDaysDec = new BigDecimal(num1);
        BigDecimal holdNumDaysDec = new BigDecimal(num2);
        BigDecimal result = rentNumDaysDec.subtract(holdNumDaysDec);
        return result;

    }
    /**
     * 浮点数是否小于n位小数点
     *
     * @param param         入参
     * @param targetOfRange 期望范围
     * @return
     */
    public static boolean isLessThanDecimalPlaces(Double param, int targetOfRange) {
        // 浮点数转换为字符串
        String paramStr = param.toString();
        return isLessThanDecimalPlaces(paramStr, targetOfRange);
    }

    /**
     * 浮点数的小数位是否小于n位
     *
     * @param param         入参
     * @param targetOfRange 期望范围
     * @return
     */
    public static boolean isLessThanDecimalPlaces(String param, int targetOfRange) {
        // 校验入参
        if (targetOfRange == 0) {
            throw new ServiceException("判断浮点数的小数位是否小于n位,n不能为0,请确认!");
        }
        // 默认小于n位小数点,返回true
        boolean result = true;
        // 数值长度
        int length = param.length();
        // 小数点位置
        int index = param.indexOf(".");
        // 大于期望的小数点范围
        if (index > -1) {
            // 存在小数点,不存在小数点满足期望范围,故忽略
            boolean flag = (length - index) > targetOfRange;
            if (flag) {
                result = false;
            }
        }
        return result;
    }

    /**
     * 数据千元化
     * @param d
     * @return
     */
    public static BigDecimal thousandChange(Double d){
        BigDecimal result = null;
        if(d != null){
             result =  new BigDecimal(d.toString()).divide(new BigDecimal("1000"), 0, 4);
        }
        return result;
    }

    public static void main(String[] args) {
//        Double param = 0.231;
//        System.out.println(isLessThanDecimalPlaces(param, 3));
//        Double param1 = 0.23;
//        System.out.println(isLessThanDecimalPlaces(param1, 3));
//        Double param2 = -0.231;
//        System.out.println(isLessThanDecimalPlaces(param2, 3));
//        String param3 = "100";
//        System.out.println(isLessThanDecimalPlaces(param3, 3));
//        System.out.println(NumberUtils.isDigits("123"));
////        System.out.println(NumberUtils.isDigits("123a"));
////        System.out.println(NumberUtils.isDigits("123.12"));
////        System.out.println(NumberUtils.isDigits("-123"));
        //String aDouble = retainThreeDecimals(762986, PERCENT_0);
        //String percentCountStr = getMultiplicationStr("88654.44", "100", 0, 5, PATTERN_A_AAA);
        Double aDouble1 = NumberDealUtils.percentCountStrTransNumber("12.55%");
        System.out.println(aDouble1);
    }

 }
