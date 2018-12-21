package com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.cookie;

import android.content.Context;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hed on 2017/10/31.
 */

public class ReadCookiesInterceptor implements Interceptor {

    Context context;

    public ReadCookiesInterceptor() {}
    public ReadCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = (HashSet) PreferenceManager.getDefaultSharedPreferences(context).getStringSet(SaveCookiesInterceptor.COMMON_COOKIE_KEY, new HashSet<String>());
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
        }

        return chain.proceed(builder.build());
    }
}
