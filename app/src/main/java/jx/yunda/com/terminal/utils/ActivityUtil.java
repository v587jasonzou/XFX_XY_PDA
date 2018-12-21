package jx.yunda.com.terminal.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;

import java.util.Map;

import jx.yunda.com.terminal.R;

/**
 * Created by hed on 2017-10-12.
 * 实现了活动切换动画，因此，打开活动请调用该工具类中的方法(控件调用除外)，BaseActivity中已添加：
 *
     @Override
     public void finish() {
         finish();
         overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
     }
 */
public class ActivityUtil {

    public static Bundle getActivityOptions(Context context) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(context,
                R.anim.in_from_right, R.anim.blank);

        return compat.toBundle();
    }

    public static void startActivityNotInActivity(Context context, Class targetActivity, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityCompat.startActivity(context, intent, getActivityOptions(context));
    }

    public static void startActivity(final Activity activity, final Class targetActivity) {
        startActivity(activity, targetActivity, null);
    }

    public static void startActivityWithDelayed(final Activity activity, final Class targetActivity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(activity, targetActivity, null);
            }
        }, 500);
    }

    public static void startActivity(Activity activity, Class targetActivity, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(activity, targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        ActivityCompat.startActivity(activity, intent, getActivityOptions(activity));
    }

    public static void startActivityWithDelayed(final Activity activity, final Class targetActivity, final Bundle bundle) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(activity, targetActivity, bundle);
            }
        }, 500);
    }
    public static void startActivityResultWithDelayed(final Activity activity, final Class targetActivity, final Bundle bundle,final int result) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(activity, targetActivity,result,bundle);
            }
        }, 500);
    }
    public static void startActivity(Activity activity, Class targetActivity, Map<String, String> map, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(activity, targetActivity);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        ActivityCompat.startActivity(activity, intent, getActivityOptions(activity));
    }

    public static void startActivity(Activity activity, Uri uri, Bundle bundle) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        ActivityCompat.startActivity(activity, intent, getActivityOptions(activity));
    }

    public static void startActivity(Activity activity, String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        ActivityCompat.startActivity(activity, intent, getActivityOptions(activity));
    }

    /****  有返回值 ****/

    public static void startActivityForResult(Activity activity, String action, Bundle bundle, int result) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        ActivityCompat.startActivityForResult(activity, intent, result, getActivityOptions(activity));
    }

    public static void startActivityForResult(Activity activity, Class targetActivity, int result) {
        startActivityForResult(activity, targetActivity, result, null);
    }


    public static void startActivityForResult(Activity activity, String action, Uri uri, int result) {
        startActivityForResult(activity, action, uri, result, null);
    }

    public static void startActivityForResult(Activity activity, String action, Uri uri, int result, Bundle bundle) {
        Intent intent = new Intent(action, uri);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        ActivityCompat.startActivityForResult(activity, intent, result, getActivityOptions(activity));
    }

    public static void startActivityForResult(Activity activity, Class targetActivity, int result, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(activity, targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        ActivityCompat.startActivityForResult(activity, intent, result, getActivityOptions(activity));
    }

    public static void startActivityForResult(Fragment fragment, Class targetActivity, int result, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(fragment.getActivity(), targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        // fragment.startActivityForResult(intent, result);

        ActivityCompat.startActivityForResult(fragment.getActivity(), intent, result, getActivityOptions(fragment.getActivity()));
    }

    public static void startActivityForResult(Activity activity, Intent intent, int result, Bundle bundle) {
        if (intent == null || !!DoubleClickUtil.isFastDoubleClick())
            return;

        if (bundle != null)
            intent.putExtras(bundle);

        ActivityCompat.startActivityForResult(activity, intent, result, getActivityOptions(activity));
    }
}