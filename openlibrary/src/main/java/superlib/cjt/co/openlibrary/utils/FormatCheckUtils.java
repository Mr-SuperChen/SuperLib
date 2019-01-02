package superlib.cjt.co.openlibrary.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 作用：判断是否为手机号
 * --------- 日期 ------- 维护人 ------------ 变更内容 --------
 * 2017/4/17		 chenjiantong
 */
public class FormatCheckUtils {
    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((1[0-9][0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 验证邮箱
     *
     * @param
     * @return 如果是符合的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isEmail(String str) {
        String regex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return match(regex, str);
    }

    /**
     * 验证输入身份证号
     *
     * @param
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isIDcard(String str) {
        String regex = "(^\\d{18}$)|(^\\d{15}$)";
        return match(regex, str);
    }


    /**
     * 验证车牌号
     */
    public static boolean isCarNumbre(String str) {
        if (str.length() == 7) {
            String regex = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Za-z]{1}[A-Za-z]{1}[A-Za-z0-9]{4}[A-Za-z0-9挂学警港澳]{1}$";
            return match(regex, str);
        } else {
            String regex = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Za-z]{1}[A-Za-z]{1}[A-Za-z0-9]{5}[A-Za-z0-9挂学警港澳]{1}$";
            return match(regex, str);
        }

    }

    /**
     * 验证车架号
     */
    public static boolean isCarFrameNumber(String str) {
        String regex = "/^[a-z\\d]{17}$/";
        return match(regex, str);
    }

    /**
     * 验证发动机号
     */
    public static boolean isCarEngineNumber(String str) {
        String regex = "/^[a-z\\d]{6,16}$/";
        return match(regex, str);
    }


    public static void main(String[] args) {
        System.out.print(isCarEngineNumber("g4ga-5b257630"));
    }

    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    /***
     * 验证身份证格式是否正确
     *
     * @param idNumber
     * @return
     */
    public static boolean isIdNumber(String idNumber) {
        if (!idNumber.matches("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$")) {
            return false;
        }
        return true;
    }

    /***
     * 密码格式不正确，登陆密码只包含数字、字母，不区分大小写，6-12位字符
     * 验证密码
     */
    public static boolean isPw(String pw) {
        if (!pw.matches("[0-9a-zA-Z]{6,16}")) {
            return false;
        }
        return true;
    }

    /***
     * 验证码格式是否正确
     *
     * @param code
     * @return
     */
    public static boolean isAuthCode(String code) {
        if (!code.matches("[0-9]{6,6}")) {
            return false;
        }
        return true;
    }

    /***
     * 返回手机号  3 4 4 格式
     * @param phone
     * @return
     */
    public static String getFormatPhoneNumber(String phone) {

        StringBuffer stringBuffer = new StringBuffer();
        if (phone.length() == 11) {
            stringBuffer.append(phone.substring(0, 3));
            stringBuffer.append(" ");
            stringBuffer.append(phone.substring(3, 7));
            stringBuffer.append(" ");
            stringBuffer.append(phone.substring(7, phone.length()));
        }
        return stringBuffer.toString();
    }

    /***
     * 返回手机号  3 4 4 格式
     * @param phone
     * @return
     */
    public static String getFormatPhoneNumberLine(String phone) {

        StringBuffer stringBuffer = new StringBuffer();
        if (phone.length() == 11) {
            stringBuffer.append(phone.substring(0, 3));
            stringBuffer.append("-");
            stringBuffer.append(phone.substring(3, 7));
            stringBuffer.append("-");
            stringBuffer.append(phone.substring(7, phone.length()));
        }
        return stringBuffer.toString();
    }
}
