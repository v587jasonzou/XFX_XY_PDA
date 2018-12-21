package com.wzgiceman.rxretrofitlibrary.retrofit_rx;

import android.app.Application;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;

/**
 * 全局app
 * Created by WZG on 2016/12/12.
 */

public class RxRetrofitApp  {
    private static Application application;
    /*基础url*/
    private static String baseUrl="";

    private static boolean debug;

    // cookie
    public static final Map<String, List<Cookie>> cookieStore = new HashMap<>();

    /**
     * 初始化rx_retrofit数据
     * @param app app
     * @param baseUrl 基础url
     */
    public static void init(Application app,String baseUrl){
        setApplication(app);
        setBaseUrl(baseUrl);
        RequestFactory.getInstance().createRetrofit();
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        RxRetrofitApp.baseUrl = baseUrl;
        RequestFactory.getInstance().createRetrofit();
    }

    public static Application getApplication() {
        return application;
    }

    private static void setApplication(Application application) {
        RxRetrofitApp.application = application;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        RxRetrofitApp.debug = debug;
    }
}
