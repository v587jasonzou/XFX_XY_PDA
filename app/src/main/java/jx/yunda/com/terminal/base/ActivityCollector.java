package jx.yunda.com.terminal.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * <li>标题: ActivityCollector
 * <li>说明: Description
 * <li>创建人：朱宝石
 * <li>创建日期：2018/4/17 16:48
 * <li>修改人: 朱宝石
 * <li>修改日期：2018/4/17 16:48
 * <li>版权: Copyright (c) 2015 运达科技公司
 * <li>版本:1.0
 */
public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static  void finishAll(){

        for(Activity activity : activities){
            activity.finish();
        }
    }
}
