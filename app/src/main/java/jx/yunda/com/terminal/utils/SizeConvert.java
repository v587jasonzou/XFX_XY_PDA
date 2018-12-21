package jx.yunda.com.terminal.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import jx.yunda.com.terminal.base.JXApplication;

/**
 * <li>标题: 机车设备管理信息系统
 * <li>说明: 单位转换工具类
 * <li>创建人：何东
 * <li>创建日期：2017-10-27
 * <li>修改人: 
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2015 运达科技公司
 * @version 1.0
 */
public class SizeConvert {
    private static DisplayMetrics dm;

    static {
        dm = JXApplication.getContext().getResources().getDisplayMetrics();
    }

    /**
     * 将dp转换为sp
     */
    public static int dip2px(float dipValue){
        return (int)(dipValue * getDensity() + 0.5f);
    }

    /**
     * sp转dp
     */
    public static int px2dip(float pxValue){
        return (int)(pxValue / getDensity() + 0.5f);
    }

    /**
     * 获取屏幕像素密度
     */
    public static float getDensity(){
        return dm.density;
    }

    /**
     * 获取屏幕像素密度(DPI)
     */
    public static int getDensityDpi(){
        return dm.densityDpi;
    }

    /**
     * 获取屏幕宽(PX)
     */
    public static int getScreenWidthPx(){
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高(PX)
     */
    public static int getScreenHeightPx(){
        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽(DIP)
     */
    public static int getScreenWidthDip(){
        return px2dip(dm.widthPixels);
    }

    /**
     * 获取屏幕高(DIP)
     */
    public static int getScreenHeightDip(){
        return px2dip(dm.heightPixels);
    }

    /**
     * dip转pix
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获得资源
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }
}
