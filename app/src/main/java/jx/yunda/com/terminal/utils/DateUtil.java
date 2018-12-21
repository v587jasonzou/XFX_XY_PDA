package jx.yunda.com.terminal.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <li>标题: 机车整备管理信息系统
 * <li>说明: 日期时间工具类，针对日期的一些常用的处理方法。
 * <li>创建人：何东
 * <li>创建日期：2017-10-27
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @version 1.0
 */
public final class DateUtil {
    /**
     * 日期格式“年-月-日”，yyyy-MM-dd（如2012-12-31）
     */
    private static final SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    /**
     * <li>说明：禁止实例化该类
     * <li>创建人：何东
     * <li>创建日期：2017-10-27
     * <li>修改人：
     * <li>修改日期：
     */
    private DateUtil() {
    }

    /**
     * <li>说明：将日期类型转换成字符串 默认格式yyyy-MM-dd
     * <li>创建人：何东
     * <li>创建日期：2016年6月16日
     * <li>修改人：
     * <li>修改日期：需要转换的日期
     * <li>修改内容：
     *
     * @param date 日期类型入参
     * @return 日期字符串
     */
    public static String date2String(Date date) {
        return date2String(date, null);
    }

    /**
     * <li>说明：将日期类型转换成字符串
     * <li>创建人：何东
     * <li>创建日期：2016年6月16日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     *
     * @param date   需要转换的日期
     * @param format 转换格式
     * @return 日期字符串
     */
    public static String date2String(Date date, String format) {
        String dateStr = "";
        SimpleDateFormat sdf = yyyy_MM_dd;
        if (null != format && !TextUtils.isEmpty(format)) {
            sdf = new SimpleDateFormat(format, Locale.CHINA);
        }

        if (null == date) return dateStr;

        try {
            dateStr = sdf.format(date);
        } catch (Exception e) {
            throw new IllegalArgumentException("参数无效");
        }

        return dateStr;
    }

    /**
     * <li>说明：使用指定日期格式解析日期字符串
     * <li>创建人：何东
     * <li>创建日期：2016-09-14
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     *
     * @param date：日期字符串
     * @param format:日期格式
     * @return 解析成功返回的日期对象
     */
    public static Date string2Date(String date, String format) {
        Date d = null;

        SimpleDateFormat sdf = yyyy_MM_dd;
        if (null != format && !TextUtils.isEmpty(format)) {
            sdf = new SimpleDateFormat(format, Locale.CHINA);
        }

        if (!TextUtils.isEmpty(date)) {
            try {
                d = sdf.parse(date);
            } catch (ParseException e) {
                throw new IllegalArgumentException("参数无效");
            }
        }

        return d;
    }

    public static String millisecondStr2DateStr(String date, String format) {
        String d = "";

        SimpleDateFormat sdf = yyyy_MM_dd;
        if (null != format && !TextUtils.isEmpty(format)) {
            sdf = new SimpleDateFormat(format);
        }
        if (!TextUtils.isEmpty(date)) {
            try {
                Long temp =  Long.parseLong(date);

                Date da = new Date(temp);
                d = sdf.format(da);
            } catch (Exception e) {
                throw new IllegalArgumentException("参数无效");
            }
        }

        return d;
    }

    /**
     * <li>说明：获取时间到毫秒
     * <li>创建人：何东
     * <li>创建日期：2016年10月10日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     *
     * @return 日期字符串（yyyyMMddHHmmss.SSS）
     */
    public static String getTimeWithMillisecond() {
        return date2String(new Date(), "yyyyMMddHHmmss.SSS");
    }
}
