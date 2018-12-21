package jx.yunda.com.terminal.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import jx.yunda.com.terminal.R;

/**
 * <li>标题: 机车整备管理信息系统
 * <li>说明: 系统对话框工具
 * <li>创建人：何东
 * <li>创建日期：2017-10-27
 * <li>修改人: 
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @version 1.0
 */
public final class AlertDialogUtil {

    /**
     * <li>说明：禁止实例化该类
     * <li>创建人：何东
     * <li>创建日期：2017-10-27
     * <li>修改人：
     * <li>修改日期：
     */
    private AlertDialogUtil() {}

    /**
     * <li>说明：确认对话框
     * <li>创建人：何东
     * <li>创建日期：2017年10月30日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     * @param msg 消息内容
     */
    public static void confirm(Context context, String msg, DialogInterface.OnClickListener positiveListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog dialog = builder.setMessage(msg)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setPositiveButton("确定", positiveListener)
                .create();

        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.gray));
    }

    /**
     * <li>说明：确认对话框
     * <li>创建人：何东
     * <li>创建日期：2017年10月30日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     * @param msg 消息内容
     */
    public static void confirm(Context context, String msg, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog dialog = builder.setMessage(msg)
                .setNegativeButton("取消", negativeListener)
                .setPositiveButton("确定", positiveListener)
                .create();

        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.gray));
    }
}
