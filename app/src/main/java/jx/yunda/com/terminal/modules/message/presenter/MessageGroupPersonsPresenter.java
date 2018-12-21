package jx.yunda.com.terminal.modules.message.presenter;

import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.api.MessageApi;
import jx.yunda.com.terminal.modules.message.model.MessageGroupPerson;
import jx.yunda.com.terminal.modules.message.model.MessageResultModel;
import jx.yunda.com.terminal.modules.message.okHttp.OkHttpUtil;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MessageGroupPersonsPresenter extends BasePresenter<IMessageGroupPersons> {
    public MessageGroupPersonsPresenter(IMessageGroupPersons view) {
        super(view);
    }

    private String groupId;
    List<MessageGroupPerson> msgGroupPersons;

    public void getGroupPersons(final String gId) {
        showProgressDialog();
        groupId = gId;
        msgGroupPersons = new ArrayList<>();
        rx.Observable<List<MessageGroupPerson>> observable = rx.Observable.create(new rx.Observable.OnSubscribe<List<MessageGroupPerson>>() {
            @Override
            public void call(Subscriber<? super List<MessageGroupPerson>> subscriber) {
                try {
                    msgGroupPersons = MessageMethods.getGroupPersonsByGroudId(gId);
                    subscriber.onNext(msgGroupPersons);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e("消息组人员获取失败", e.getMessage());
                    dismissProgressDialog();
                }
            }
        });
        Observer<List<MessageGroupPerson>> observer = new Observer<List<MessageGroupPerson>>() {
            @Override
            public void onCompleted() {
                //dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                dismissProgressDialog();
                LogUtil.e("消息组人员获取失败", e.getMessage());
            }

            @Override
            public void onNext(List<MessageGroupPerson> persons) {
                if (persons == null) return;
                getViewRef().pageNotifyDataSetChanged(persons);
                dismissProgressDialog();
            }
        };
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
