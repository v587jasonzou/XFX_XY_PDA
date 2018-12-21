package jx.yunda.com.terminal.utils;

import android.widget.Toast;

import jx.yunda.com.terminal.common.StringConstants;
import jx.yunda.com.terminal.base.JXApplication;

public final class ToastUtil {
    public static void toastShort(String text) {
        Toast.makeText(JXApplication.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(String text) {
        Toast.makeText(JXApplication.getContext(), text, Toast.LENGTH_LONG).show();
    }

    public static void toastSuccess() {
        Toast.makeText(JXApplication.getContext(), StringConstants.OPERATE_SUCCESS, Toast.LENGTH_SHORT).show();
    }
    public static void toastFail() {
        Toast.makeText(JXApplication.getContext(), StringConstants.OPERATE_FAIL, Toast.LENGTH_SHORT).show();
    }
}
