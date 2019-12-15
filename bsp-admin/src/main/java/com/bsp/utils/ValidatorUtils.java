package com.bsp.utils;


/**
 * hibernate-validator校验工具类
 *
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 */
public class ValidatorUtils {

    /**
     * 判断是否为合法的密码（合法密码：6-20位英文字母、数字或下划线的组合）
     * @param pwd
     * @return true 合法；false 不合法
     */
    public static boolean isLegalPassword(String pwd) {
        return pwd != null && pwd.matches("[a-zA-Z0-9\\_]{6,20}");
    }

    /**
     * 非空,非空字符串
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }
    /**
     * 验证Email地址格式
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        return email != null && email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+");
    }
    /**
     * 验证多个Email地址
     * @param emails
     * @param seperator
     * @return
     */
    public static boolean isEmail(String emails, String seperator) {
        if(isBlank(emails) || isBlank(seperator)) return false;
        for(String e : emails.split(seperator)) {
            if(!isEmail(e)) return false;
        }
        return true;
    }
    /**
     * 验证手机号
     * @param phoneNum
     * @return
     */
    public static boolean isPhone(String phoneNum){
        return phoneNum !=null && phoneNum.matches("^(13|14|15|17|18)[0-9]{9}$");
    }

    /**
     * 判断IP地址的合法性，这里采用了正则表达式的方法来判断
     * return true，合法
     * */
    public static boolean isIP(String text) {
        if (text != null && !text.isEmpty()) {
            String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
            String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }

    /**
     * 验证网址Url
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean IsUrl(String param) {
        if (param != null && !param.isEmpty()) {
            String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
            // 判断url地址是否与正则表达式匹配
            if (param.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }

    /**
     * 验证固定电话号码
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean IsTelephone(String param) {
        if (param != null && !param.isEmpty()) {
            String regex = "^(\\d{3,4}-)?\\d{6,8}$";
            // 判断参数是否与正则表达式匹配
            if (param.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }

    /**
     * 验证输入邮政编号
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean IsPostalcode(String param) {
        if (param != null && !param.isEmpty()) {
            String regex = "^\\d{6}$";
            // 判断参数是否与正则表达式匹配
            if (param.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }

    /**
     * 验证输入手机号码
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean IsMobilePhone(String param) {
        if (param != null && !param.isEmpty()) {
            String regex = "^[1]+[3,5]+\\d{9}$";
            // 判断参数是否与正则表达式匹配
            if (param.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }

    /**
     * 验证输入身份证号
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean IsIDcard(String param) {
        if (param != null && !param.isEmpty()) {
            String regex = "(^\\d{18}$)|(^\\d{15}$)";
            // 判断参数是否与正则表达式匹配
            if (param.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }
}
