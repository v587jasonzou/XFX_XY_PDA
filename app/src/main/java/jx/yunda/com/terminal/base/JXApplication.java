package jx.yunda.com.terminal.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.lzy.imagepicker.ImagePicker;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.common.IntConstants;
import jx.yunda.com.terminal.common.StringConstants;
import jx.yunda.com.terminal.common.crash.AndroidCrash;
import jx.yunda.com.terminal.utils.LoggerMaster;
import jx.yunda.com.terminal.widget.GlideImageLoader;
import rx.Observable;
import rx.Subscriber;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

/**
 * <li>类名: MyApplication
 * <li>说明: 用于获取当前活动的Context
 * <li>创建人：朱宝石
 * <li>创建日期：2018/4/17 17:12
 * <li>修改人: 朱宝石
 * <li>修改日期：2018/4/17 17:12
 * <li>版权: Copyright (c) 2015 运达科技公司
 * <li>版本:1.0
 */
public class JXApplication extends Application {
    private static Context context;
    private static ExecutorService executor;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Realm.init(getApplicationContext());
                RealmConfiguration configq = new RealmConfiguration.Builder()
                        .name("jxxt.realm") //文件名
                        .schemaVersion(0)//版本号
//                        .migration(new CustomerMigration())
                        .build();
                Realm.setDefaultConfiguration(configq);
                Log.e("当前线程",Thread.currentThread().getName()+"--"+Thread.currentThread().getId()+"");
            }
        }).subscribeOn(Schedulers.from(getExecutor())).subscribe();
        SysInfo.baseURL = getSharedPreferences(StringConstants.SYSTEM_BASE_DATA, Context.MODE_PRIVATE).getString(
                StringConstants.SYSTEM_BASE_URL_KEY, StringConstants.DEFAULT_BASE_URL_VALUE);
        SysInfo.msgSocketAdress = getSharedPreferences(StringConstants.SYSTEM_BASE_DATA, Context.MODE_PRIVATE).getString(
                StringConstants.SYSTEM_MESSAGE_SOCKET_ADDRESS_KEY, StringConstants.DEFAULT_MESSAGE_SOCKET_ADDRESS);
        SysInfo.msgSocketPort = getSharedPreferences(StringConstants.SYSTEM_BASE_DATA, Context.MODE_PRIVATE).getInt(
                StringConstants.SYSTEM_MESSAGE_SOCKET_PORT_KEY, IntConstants.DEFAULT_MESSAGE_SOCKET_PORT);
        SysInfo.msgWebURL = getSharedPreferences(StringConstants.SYSTEM_BASE_DATA, Context.MODE_PRIVATE).getString(
                StringConstants.SYSTEM_MESSAGE_WEB_URL_KEY, StringConstants.DEFAULT_MESSAGE_WEB_URL);
        RxRetrofitApp.init(this, SysInfo.baseURL);
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(false); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
//        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
//        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
//        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        AndroidCrash.getInstance().init(this);
        // Logger日志库
        LoggerMaster.initLogger();

    }

    public static Context getContext() {
        return context;
    }
    public static ExecutorService getExecutor(){
        if (executor==null){
            executor = Executors.newFixedThreadPool(1);
            Thread thread = new Thread();//创建一个单线程
            RxJavaSchedulersHook hook = RxJavaPlugins.getInstance().getSchedulersHook();
            executor.execute(thread);
        }
        return executor;
    }

}
