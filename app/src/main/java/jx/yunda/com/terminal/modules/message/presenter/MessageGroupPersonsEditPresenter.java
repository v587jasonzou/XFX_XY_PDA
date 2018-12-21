package jx.yunda.com.terminal.modules.message.presenter;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.promeg.pinyinhelper.Pinyin;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.api.MessageApi;
import jx.yunda.com.terminal.modules.message.model.MessageGroupPerson;
import jx.yunda.com.terminal.modules.message.model.MessageResultModel;
import jx.yunda.com.terminal.modules.message.model.Receiver;
import jx.yunda.com.terminal.modules.message.okHttp.OkHttpUtil;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MessageGroupPersonsEditPresenter extends BasePresenter<IMessageGroupPersonsEdit> {
    public MessageGroupPersonsEditPresenter(IMessageGroupPersonsEdit view) {
        super(view);
    }

    private Handler handler = new Handler(Looper.getMainLooper());
    List<MessageGroupPerson> msgGroupPersons;

    public void getGroupPersons(final String groupId) {
        showProgressDialog();
        msgGroupPersons = new ArrayList<>();
        Observable<List<MessageGroupPerson>> observable = Observable.create(new Observable.OnSubscribe<List<MessageGroupPerson>>() {
            @Override
            public void call(Subscriber<? super List<MessageGroupPerson>> subscriber) {
                try {
                    RequestBody body = new FormBody.Builder().add("params", groupId).build();
                    String result = null;
                    result = OkHttpUtil.dealHttp(SysInfo.msgWebURL + "msgGroupUser/getAllGroupUser.do", body);
                    MessageResultModel rst = JSON.parseObject(result, MessageResultModel.class);
                    if ("success".equals(rst.getMsg())) {
                        msgGroupPersons = JSON.parseObject(rst.getData(), new TypeReference<List<MessageGroupPerson>>() {
                        });
                    } else {
                        LogUtil.e("消息组人员获取失败", rst.getMsg());
                        ToastUtil.toastShort(rst.getMsg());
                    }
                    subscriber.onNext(msgGroupPersons);
                } catch (IOException e) {
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

    public void removeGroupPerson(final String groupId, final String removeEmpId, final int position) {
        showProgressDialog();
        Observable<Boolean> observable = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    boolean rstObservable = false;
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("empId", removeEmpId);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.add(jsonObject);
                    JSONObject params = new JSONObject();
                    params.put("groupId", groupId);
                    params.put("empIds", jsonArray);
                    RequestBody body = new FormBody.Builder().add("params", params.toJSONString()).build();
                    String result = OkHttpUtil.dealHttp(SysInfo.msgWebURL + "msgGroupUser/removeMsgGroupUser.do", body);
                    MessageResultModel rst = JSON.parseObject(result, MessageResultModel.class);
                    rstObservable = "success".equals(rst.getMsg());
                    if (rstObservable) {
                        msgGroupPersons.remove(position);
                    } else {
                        LogUtil.e("消息组人员移除失败", rst.getMsg());
                        ToastUtil.toastShort(rst.getMsg());
                    }
                    subscriber.onNext(rstObservable);
                } catch (Exception ex) {
                    dismissProgressDialog();
                    LogUtil.e("消息组人员移除失败", ex.getMessage());
                }
            }
        });
        Observer<Boolean> observer = new Observer<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                dismissProgressDialog();
                LogUtil.e("消息组人员移除失败", e.getMessage());
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean)
                    getViewRef().pageNotifyDataSetChanged(msgGroupPersons);
                dismissProgressDialog();
            }
        };
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void addGroupPerson(final String groupId, List<Receiver> receivers) {
        showProgressDialog();
        BaseApi baseApi = new BaseApi();
        baseApi.setShowProgress(false);
        baseApi.setCheckSuccess(false);
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("emps", JSON.toJSONString(receivers));
        req(RequestFactory.getInstance().createApi(MessageApi.class).savePerson(params), baseApi, new HttpOnNextListener() {
            @Override
            public void onNext(String result, String mothead) {
                try {
                    JSONObject jsonObject = JSON.parseObject(result, JSONObject.class);
                    if ("true".equals(jsonObject.get("success").toString())) {
                        ToastUtil.toastSuccess();
                        getGroupPersons(groupId);
                        getViewRef().dismissAddPersonDialog();
                    } else
                        ToastUtil.toastFail();
                    dismissProgressDialog();
                } catch (Exception e) {
                    dismissProgressDialog();
                    LogUtil.e("消息页面", "添加组人员保存失败" + e.getMessage());
                }
            }

            @Override
            public void onError(ApiException e) {
                dismissProgressDialog();
                LogUtil.e("消息页面", "添加组人员保存失败" + e.getMessage());
            }
        });
    }

    public void searchAllPerson() {
        showProgressDialog();
        BaseApi baseApi = new BaseApi();
        baseApi.setShowProgress(false);
        baseApi.setCheckSuccess(false);
        req(RequestFactory.getInstance().createApi(MessageApi.class).getAllPerson(), baseApi, new HttpOnNextListener() {
            @Override
            public void onNext(String result, String mothead) {
                try {
                    JSONObject jsonObject = JSON.parseObject(result, JSONObject.class);
                    List<Receiver> receivers = JSON.parseObject(jsonObject.get("root").toString(), new TypeReference<List<Receiver>>() {
                    });
                    List<MessageGroupPerson> allPersons = new ArrayList<>();
                    MessageGroupPerson p = null;
                    for (Receiver r : receivers) {
                        if (!msgGroupPersonLisIsContainByEmpId(msgGroupPersons, r.getEmpId())) {
                            p = new MessageGroupPerson();
                            p.setEmpId(r.getEmpId());
                            p.setEmpName(r.getEmpName());
                            p.setEmpPost(r.getEmpPost());
                            p.isChecked = false;
                            p.setNamePinyIn(Pinyin.toPinyin(r.getEmpName(), "").toUpperCase());
                            allPersons.add(p);
                        }
                    }
                    //allPersons = allPersons.subList(0, 10);
                    getViewRef().pageNotifyAllPersonDataSetChanged(allPersons);
                    getViewRef().showAddPersonDialog();
                    dismissProgressDialog();
                } catch (Exception e) {
                    LogUtil.e("消息页面", "获取所有可添加人员失败" + e.getMessage());
                    dismissProgressDialog();
                }
            }

            @Override
            public void onError(ApiException e) {
                dismissProgressDialog();
                LogUtil.e("消息页面", "获取所有可添加人员失败" + e.getMessage());
            }
        });
    }

    private boolean msgGroupPersonLisIsContainByEmpId(List<MessageGroupPerson> persons, String empId) {
        if (persons == null || TextUtils.isEmpty(empId)) return false;
        boolean isContain = false;
        for (MessageGroupPerson p : persons) {
            if (empId.equals(p.getEmpId()))
                isContain = true;
        }
        return isContain;
    }
}
