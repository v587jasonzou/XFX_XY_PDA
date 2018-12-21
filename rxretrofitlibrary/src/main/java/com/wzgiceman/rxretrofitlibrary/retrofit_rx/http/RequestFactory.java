package com.wzgiceman.rxretrofitlibrary.retrofit_rx.http;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trello.rxlifecycle.LifecycleProvider;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.api.BaseResultEntity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.FactoryException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.HttpTimeException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.RetryWhenNetworkException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.GsonHttpOnNextListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.subscribers.GsonProgressSubscriber;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.subscribers.ProgressSubscriber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hed on 2017-10-19
 */
public class RequestFactory {

    private volatile static RequestFactory INSTANCE;
    private Retrofit retrofit;

    // 构造方法私有
    private RequestFactory() {}

    //获取单例
    public static RequestFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (RequestFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RequestFactory();
                }
            }
        }

        return INSTANCE;
    }

    /**
     * 创建retrofit对象
     */
    public void createRetrofit() {
        BaseApi basePar = new BaseApi();

        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(basePar.getConnectionTime(), TimeUnit.SECONDS);
        // 以下两个拦截器是为实现多个OkHttpClient共享cookie（退出登录可清空SharedPreferences中的cookie值）
        //builder.addInterceptor(new SaveCookiesInterceptor(RxRetrofitApp.getApplication()));
        //builder.addInterceptor(new ReadCookiesInterceptor(RxRetrofitApp.getApplication()));
        // 文件上传关闭流
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Connection", "close");
                return chain.proceed(builder.build());
            }
        });
        // session失效验证
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);

                ResponseBody body = response.body();
                MediaType contentType = body.contentType();
                if (contentType != null && contentType.subtype().equals("html")) {
                    Context context = RxRetrofitApp.getApplication();

                    Intent intent = new Intent();
                    intent.setAction("com.yunda.jx.TIMEOUT_TO_LOGIN");
                    context.sendBroadcast(intent);

                    return null;
                }

                return response;
            }
        });
        builder.cookieJar(new CookieJar() {

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                RxRetrofitApp.cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = RxRetrofitApp.cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });
        builder.addInterceptor(getHttpLoggingInterceptor());
//        if (RxRetrofitApp.isDebug()) {
//            builder.addInterceptor(getHttpLoggingInterceptor());
//        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    /*创建retrofit对象*/
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(RxRetrofitApp.getBaseUrl())
                .build();
    }

    public <T> T createApi(Class<T> clazz) {
        return retrofit.create(clazz);
    }

    /**
     * 请求执行后对缓存、异常、变换、结果判定等的处理
     * @param observable 请求api中方法执行后返回的RxJava对象
     * @param basePar 基础参数设置对象
     * @param onNextListener 结果回调对象
     * @param lifecycleProvider Activity或Fragment生命周期监控，以取消网络请求
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public <T, R> Subscription doHttpDeal(Observable<T> observable, final BaseApi basePar, HttpOnNextListener onNextListener, LifecycleProvider<R> lifecycleProvider) {

        ProgressSubscriber<T> subscriber = new ProgressSubscriber<>(basePar, onNextListener);

        if (lifecycleProvider != null) {
            /*失败后的retry配置*/
            observable = observable.retryWhen(new RetryWhenNetworkException())
                    /*异常处理*/
                    .onErrorResumeNext(funcException)
                    /*生命周期管理*/
                    .compose(lifecycleProvider.bindToLifecycle())
                    /*http请求线程*/
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    /*回调线程*/
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            /*失败后的retry配置*/
            observable = observable.retryWhen(new RetryWhenNetworkException())
                    /*异常处理*/
                    .onErrorResumeNext(funcException)
                    /*http请求线程*/
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    /*回调线程*/
                    .observeOn(AndroidSchedulers.mainThread());
        }



        // 检查返回值（当服务器端各方法返回值被BaseResultEntity包装，验证服务器明确的返回和打印服务器消息）
        return observable.map(new Func1<T, T>() {
            @Override
            public T call(T t) {
                if (basePar.isCheckSuccess() && t instanceof String) {
                    BaseResultEntity<T> httpResult = JSONObject.parseObject((String) t, new TypeReference<BaseResultEntity<T>>(){});
                    if (httpResult != null && !httpResult.getSuccess()) {
                        throw new HttpTimeException(httpResult.getErrMsg());
                    }
                }

                return t;
            }
        }).subscribe(subscriber);
    }

    /**
     * 请求执行后对缓存、异常、变换、结果判定等的处理
     * @param observable 请求api中方法执行后返回的RxJava对象
     * @param basePar 基础参数设置对象
     * @param onNextListener 结果回调对象
     * @param lifecycleProvider Activity或Fragment生命周期监控，以取消网络请求
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public <T, R> Subscription doHttpDeal(Observable<T> observable, final BaseApi basePar, GsonHttpOnNextListener onNextListener, LifecycleProvider<R> lifecycleProvider) {

        GsonProgressSubscriber<T> subscriber = new GsonProgressSubscriber<>(basePar, onNextListener);

        if (lifecycleProvider != null) {
            /*失败后的retry配置*/
            observable = observable.retryWhen(new RetryWhenNetworkException())
                    /*异常处理*/
                    .onErrorResumeNext(funcException)
                    /*生命周期管理*/
                    .compose(lifecycleProvider.bindToLifecycle())
                    //.compose(appCompatActivity.bindUntilEvent(ActivityEvent.PAUSE))
                    /*http请求线程*/
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    /*回调线程*/
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            /*失败后的retry配置*/
            observable = observable.retryWhen(new RetryWhenNetworkException())
                    /*异常处理*/
                    .onErrorResumeNext(funcException)
                    /*http请求线程*/
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    /*回调线程*/
                    .observeOn(AndroidSchedulers.mainThread());
        }


        /*链接式对象返回*/
        if (onNextListener != null) {
            onNextListener.onNext(observable);
        }

        // 检查返回值（当服务器端各方法返回值被BaseResultEntity包装，验证服务器明确的返回和打印服务器消息）
        return observable.map(new Func1<T, T>() {

            @Override
            public T call(T t) {
                if (basePar.isCheckSuccess() && t instanceof BaseResultEntity) {
                    BaseResultEntity httpResult = (BaseResultEntity)t;
                    if (httpResult.getSuccess() != null && !httpResult.getSuccess()) {
                        throw new HttpTimeException(httpResult.getErrMsg());
                    }
                }

                return t;
            }
        }).subscribe(subscriber);
    }

    /**
     * 异常处理
     */
    Func1 funcException = new Func1<Throwable, Observable>() {
        @Override
        public Observable call(Throwable throwable) {
            return Observable.error(FactoryException.analysisExcetpion(throwable));
        }
    };

    /**
     * 日志输出
     * 自行判定是否添加
     *
     * @return
     */
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
//                new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//                Log.d("RxRetrofit", "Retrofit====Message:" + message);
//            }
//        }
        );
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }
}
