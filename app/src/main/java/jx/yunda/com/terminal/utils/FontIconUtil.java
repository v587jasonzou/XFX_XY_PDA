package jx.yunda.com.terminal.utils;

import android.graphics.Typeface;
import android.text.TextUtils;
import android.widget.TextView;

import jx.yunda.com.terminal.base.JXApplication;

/**
 * <li>标题: 机车整备管理信息系统
 * <li>说明: 字体icon工具
 * <li>创建人：何东
 * <li>创建日期：2017-10-27
 * <li>修改人: 
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @version 1.0
 */
public final class FontIconUtil {

    private static Typeface fontawesome = Typeface.createFromAsset(JXApplication.getContext().getAssets(), "fontawesome-webfont.ttf");

    /**
     * <li>说明：禁止实例化该类
     * <li>创建人：何东
     * <li>创建日期：2017-10-27
     * <li>修改人：
     * <li>修改日期：
     */
    private FontIconUtil() {}
    
    /**
     * <li>说明：设置文本控件（一般情况是TextView）所显示文字的字体
     * <li>创建人：何东
     * <li>创建日期：2017年10月30日
     * <li>修改人： 
     * <li>修改日期：
     * <li>修改内容：
     * @param text 需要指定字体的控件
     * @param iconStr 字体编号
     * @return 日期字符串
     */
    public static void fontReference(TextView text, String iconStr) {
        if (text == null) return;

        if (!TextUtils.isEmpty(iconStr)) {
            text.setText(iconStr);
        }

        text.setTypeface(fontawesome);
    }
}
