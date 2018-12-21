package jx.yunda.com.terminal.base;

import android.app.ProgressDialog;

import com.alibaba.fastjson.JSON;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import jx.yunda.com.terminal.entity.MessageSend;
import jx.yunda.com.terminal.message.okhttp.client.listener.WsOnOpenHanleListener;
import jx.yunda.com.terminal.message.okhttp.client.manager.WebSocketManager;
import rx.Observable;

public class BasePresenter<T> {
    protected Reference<T> mViewRef;

    public BasePresenter(T view) {
        this.mViewRef = new WeakReference<>(view);
    }

    //获取当前活动或碎片
    protected T getViewRef() {
        if (null != mViewRef)
            return mViewRef.get();
        return null;
    }

    //获取当前活动
    public BaseActivity getAct() {
        T v = getViewRef();
        if (v instanceof BaseActivity) {
            return (BaseActivity) v;
        } else if (v instanceof BaseFragment) {
            return (BaseActivity) (((BaseFragment) v).getActivity());
        }
        return null;
    }

    /**
     * 若需要对网络请求进行个性化设置，调用该方法得到BaseApi后进行参数设置，然后再调用req的包含BaseApi参数的重载方法即可
     * 注意，同一个Activity（包括有多个Fragment的情况）中共享BaseApi，因此不同请求需要个性设置请new BaseApi()，且需要
     * 手动设置进度提示框（可调用getProgressDialog()）
     *
     * @return
     */
    public BaseApi getBaseApi() {
        T v = getViewRef();
        if (v instanceof BaseActivity) {
            return ((BaseActivity) v).getBaseApi();
        } else if (v instanceof BaseFragment) {
            return ((BaseActivity) (((BaseFragment) v).getActivity())).getBaseApi();
        } else {
            return null;
        }
    }

    /**
     * 该方法用于自己new BaseApi或使用HttpManager方式时设置进度提示框（因为ProgressDialog只能在Activity中创建）
     *
     * @return
     */
    public ProgressDialog getProgressDialog() {
        T v = getViewRef();
        if (v instanceof BaseActivity) {
            return ((BaseActivity) v).getProgressDialog();
        } else if (v instanceof BaseFragment) {
            return ((BaseActivity) (((BaseFragment) v).getActivity())).getProgressDialog();
        } else {
            return null;
        }
    }

    /*显示加载框*/
    public void showProgressDialog() {
        ProgressDialog pd = getProgressDialog();
        if (pd != null && !pd.isShowing())
            pd.show();
    }

    /*隐藏加载框*/
    public void dismissProgressDialog() {
        ProgressDialog pd = getProgressDialog();
        if (pd != null && pd.isShowing())
            pd.dismiss();
    }

    public <R> void req(Observable<R> observable, HttpOnNextListener onNextListener) {
        T v = getViewRef();
        RequestFactory.getInstance().doHttpDeal(observable, getBaseApi(), onNextListener,
                v instanceof RxAppCompatActivity ? (LifecycleProvider<ActivityEvent>) v : (LifecycleProvider<FragmentEvent>) v);
    }

    public <R> void req(Observable<R> observable, boolean isShowProgress, HttpOnNextListener onNextListener) {
        T v = getViewRef();
        BaseApi baseApi = getBaseApi();
        baseApi.setShowProgress(isShowProgress);
        RequestFactory.getInstance().doHttpDeal(observable, baseApi, onNextListener,
                v instanceof RxAppCompatActivity ? (LifecycleProvider<ActivityEvent>) v : (LifecycleProvider<FragmentEvent>) v);
    }

    public <R> void req(Observable<R> observable, BaseApi baseApi, HttpOnNextListener onNextListener) {
        if (baseApi == null) return;
        T v = getViewRef();
        RequestFactory.getInstance().doHttpDeal(observable, baseApi, onNextListener,
                v instanceof RxAppCompatActivity ? (LifecycleProvider<ActivityEvent>) v : (LifecycleProvider<FragmentEvent>) v);
    }

    public void webSocketConnnect() {
        if (!WebSocketManager.getInstance().isWsConnected())
            WebSocketManager.getInstance().startConnect();
    }

    public void webSocketConnnect(WsOnOpenHanleListener listener) {
        if (!WebSocketManager.getInstance().isWsConnected())
            WebSocketManager.getInstance().startConnect(listener);
    }
    public void setWebSocketCallBack(WsOnOpenHanleListener listener){
        WebSocketManager.getInstance().setWsOnOpenHanleListener(listener);
    }

    public void webSocketDisConnnect() {
        WebSocketManager.getInstance().stopConnect();
    }

    public boolean sendMessage(MessageSend msgSend) {
        if (WebSocketManager.getInstance().isWsConnected())
            return WebSocketManager.getInstance().sendMessage(JSON.toJSONString(msgSend));
        return false;
    }
}
