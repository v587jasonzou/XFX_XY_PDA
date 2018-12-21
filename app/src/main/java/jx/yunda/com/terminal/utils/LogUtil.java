package jx.yunda.com.terminal.utils;

import android.util.Log;

/**
 * <li>类名: BaseLog
 * <li>说明: 系统日志打印
 * <li>创建人：朱宝石
 * <li>创建日期：2018/4/17 17:18
 * <li>修改人: 朱宝石
 * <li>修改日期：2018/4/17 17:18
 * <li>版权: Copyright (c) 2015 运达科技公司
 * <li>版本:1.0
 */
public class LogUtil {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static int level = ERROR;

    public static void v(String tag, String msg) {
        if (level <= VERBOSE)
            Log.v(tag, msg + " ");
    }

    public static void d(String tag, String msg) {
        if (level <= DEBUG)
            Log.d(tag, msg + " ");
    }

    public static void i(String tag, String msg) {
        if (level <= INFO)
            Log.i(tag, msg + " ");
    }

    public static void w(String tag, String msg) {
        if (level <= WARN)
            Log.w(tag, msg + " ");
    }

    public static void e(String tag, String msg) {
        if (level <= ERROR)
            Log.e(tag, msg + " ");
    }
}
