package com.wzgiceman.rxretrofitlibrary.retrofit_rx.http;

import android.util.Log;

import com.trello.rxlifecycle.LifecycleProvider;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.api.BaseResultEntity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.FactoryException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.HttpTimeException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.RetryWhenNetworkException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.cookie.CookieInterceptor;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.cookie.ReadCookiesInterceptor;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.cookie.SaveCookiesInterceptor;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.GsonHttpOnNextListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.subscribers.GsonProgressSubscriber;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hed on 2017-10-19
 */
@Deprecated
public class GsonRequestFactory {

    private volatile static GsonRequestFactory INSTANCE;
    private volatile static Retrofit RETROFIT;

    // 构造方法私有
    private GsonRequestFactory() {}

    //获取单例
    public static GsonRequestFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (GsonRequestFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GsonRequestFactory();

                    BaseApi basePar = new BaseApi();

                    //手动创建一个OkHttpClient并设置超时时间缓存等设置
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(basePar.getConnectionTime(), TimeUnit.SECONDS);
                    builder.addInterceptor(new SaveCookiesInterceptor(RxRetrofitApp.getApplication()));
                    builder.addInterceptor(new ReadCookiesInterceptor(RxRetrofitApp.getApplication()));
                    builder.addInterceptor(new CookieInterceptor(basePar.isCache(), basePar.getUrl()));
                    if (RxRetrofitApp.isDebug()) {
                        builder.addInterceptor(getHttpLoggingInterceptor());
                    }

                    /*创建retrofit对象*/
                    RETROFIT = new Retrofit.Builder()
                            .client(builder.build())
                            //.addConverterFactory(GsonConverterFactory.create())
                            .addConverterFactory(JacksonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .baseUrl(RxRetrofitApp.getBaseUrl())
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    public <T> T createApi(Class<T> clazz) {
        return RETROFIT.create(clazz);
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
    public <T, R> void doHttpDeal(Observable<T> observable, BaseApi basePar, GsonHttpOnNextListener onNextListener, LifecycleProvider<R> lifecycleProvider) {

        GsonProgressSubscriber<T> subscriber = new GsonProgressSubscriber<>(basePar, onNextListener);

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

        // 是否检查返回值（当服务器端各方法返回值被BaseResultEntity包装，验证服务器明确的返回和打印服务器消息）
        if (basePar.isCheckSuccess()) {
            observable.map(new Func1<T, T>() {

                @Override
                public T call(T t) {
                    if (t instanceof BaseResultEntity) {
                        BaseResultEntity httpResult = (BaseResultEntity)t;
                        if (httpResult.getSuccess()) {
                            throw new HttpTimeException(httpResult.getErrMsg());
                        }
                    }

                    return t;
                }
            });
        }

        /*链接式对象返回*/
        if (onNextListener != null) {
            onNextListener.onNext(observable);
        }

        observable.subscribe(subscriber);
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
    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("RxRetrofit", "Retrofit====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }
}
