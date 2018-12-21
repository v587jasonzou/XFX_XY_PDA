package com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.cookie;

import android.content.Context;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by hed on 2017/10/31.
 */

public class SaveCookiesInterceptor implements Interceptor {
    public static final String COMMON_COOKIE_KEY = "COMMON_COOKIE_KEY";

    Context context;

    public SaveCookiesInterceptor() {}
    public SaveCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }

            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putStringSet(COMMON_COOKIE_KEY, cookies)
                    .commit();
        }

        return originalResponse;
    }
}
