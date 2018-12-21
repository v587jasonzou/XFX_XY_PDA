package jx.yunda.com.terminal.modules.transNotice.presenter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.entity.BaseResultEntity;
import jx.yunda.com.terminal.modules.api.TransNoticeApi;
import jx.yunda.com.terminal.modules.message.model.MessageGroupPerson;
import jx.yunda.com.terminal.modules.message.presenter.MessageMethods;
import jx.yunda.com.terminal.modules.transNotice.model.TransNotice;
import jx.yunda.com.terminal.modules.transNotice.model.Train;
import jx.yunda.com.terminal.modules.transNotice.model.TransNoticeTrain;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TransNoticeEditPresenter extends BasePresenter<ITransNoticeEdit> {
    public TransNoticeEditPresenter(ITransNoticeEdit view) {
        super(view);
    }

    public void getGroupPersonsByPostName(String postName) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("postName", postName);
            req(RequestFactory.getInstance().createApi(TransNoticeApi.class).getNoticeAcceptPerson(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity<MessageGroupPerson> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<MessageGroupPerson>>() {
                    });
                    if (rst != null && rst.getSuccess() != null && rst.getSuccess()) {
                        getViewRef().pageNotifyNewDataSetChangedForSelectPersonDialog(rst.getRoot());
                    } else
                        getViewRef().pageNotifyNewDataSetChangedForSelectPersonDialog(null);

                }

                @Override
                public void onError(ApiException e) {
                    getViewRef().pageNotifyNewDataSetChangedForSelectPersonDialog(null);
                    ToastUtil.toastShort(e.getMessage());
                    LogUtil.e("调车通知编辑页面，获取可选择机车", e.toString());
                }
            });
        } catch (Exception ex) {
            getViewRef().pageNotifyNewDataSetChangedForSelectPersonDialog(null);
            LogUtil.e("调车通知编辑页面，获取可选择机车", ex.toString());
        }
    }

    public void getGroupPersons(final String gId) {
        try {
            showProgressDialog();
            rx.Observable<List<MessageGroupPerson>> observable = rx.Observable.create(new rx.Observable.OnSubscribe<List<MessageGroupPerson>>() {
                @Override
                public void call(Subscriber<? super List<MessageGroupPerson>> subscriber) {
                    try {
                        List<MessageGroupPerson> msgGroupPersons = MessageMethods.getGroupPersonsByGroudId(gId);
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
                    getViewRef().pageNotifyNewDataSetChangedForSelectPersonDialog(persons);
                    dismissProgressDialog();
                }
            };
            observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } catch (Exception ex) {
            LogUtil.e("调车通知编辑页面，通知人选择", ex.toString());
        }
    }

    public void getTransNoticeTrains(String noticeIDX) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("start", "0");
            params.put("limit", "999");
            params.put("noticeIDX", noticeIDX);
            req(RequestFactory.getInstance().createApi(TransNoticeApi.class).getTransNoticeTrains(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity<TransNoticeTrain> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<TransNoticeTrain>>() {
                    });
                    if (rst != null && rst.getSuccess() != null && rst.getSuccess()) {
                        getViewRef().pageNotifyNewDataSetChangedForNoticeTrain(rst.getRoot());
                    } else
                        getViewRef().pageNotifyNewDataSetChangedForNoticeTrain(null);

                }

                @Override
                public void onError(ApiException e) {
                    getViewRef().pageNotifyNewDataSetChangedForNoticeTrain(null);
                    ToastUtil.toastShort(e.getMessage());
                    LogUtil.e("调车通知编辑页面，获取可选择机车", e.toString());
                }
            });
        } catch (Exception ex) {
            getViewRef().pageNotifyNewDataSetChangedForNoticeTrain(null);
            LogUtil.e("调车通知编辑页面，获取可选择机车", ex.toString());
        }
    }

    public void getSelectTrains() {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("start", "0");
            params.put("limit", "999");
            params.put("trainNo", "");
            req(RequestFactory.getInstance().createApi(TransNoticeApi.class).getSelectTrain(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity<Train> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<Train>>() {
                    });
                    if (rst != null && rst.getSuccess() != null && rst.getSuccess()) {
                        getViewRef().pageNotifyNewDataSetChangedForSelectTrainDialog(rst.getRoot());
                    }
                }

                @Override
                public void onError(ApiException e) {
                    ToastUtil.toastShort(e.getMessage());
                    LogUtil.e("调车通知编辑页面，获取可选择机车", e.toString());
                }
            });
        } catch (Exception ex) {
            LogUtil.e("调车通知编辑页面，获取可选择机车", ex.toString());
        }
    }

    public void getDefaultNoticeName() {
        try {
            req(RequestFactory.getInstance().createApi(TransNoticeApi.class).getNoticeName(), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity<TransNotice> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<TransNotice>>() {
                    });
                    if (rst != null && rst.getSuccess()) {
                        getViewRef().setNoticeDefaultName(rst.getRoot().get(0).getNoticeName());
                    }
                }

                @Override
                public void onError(ApiException e) {
                    ToastUtil.toastShort(e.getMessage());
                    LogUtil.e("调车通知编辑页面，获取默认名称", e.toString());
                }
            });
        } catch (Exception ex) {
            LogUtil.e("调车通知编辑页面，获取默认名称", ex.toString());
        }
    }

    public void saveTransNotice(TransNotice transNotice, List<TransNoticeTrain> transNoticeTrains) {
        try {
            //final boolean isNew = TextUtils.isEmpty(transNotice.getIdx());
            Map<String, Object> params = new HashMap<>();
            params.put("empId", SysInfo.userInfo.emp.getEmpid());
            params.put("empName", SysInfo.userInfo.emp.getEmpname());
            params.put("transNotice", JSON.toJSONString(transNotice, SerializerFeature.WriteNullStringAsEmpty));
            params.put("transNoticeTrains", JSON.toJSONString(transNoticeTrains, SerializerFeature.WriteNullStringAsEmpty));
            req(RequestFactory.getInstance().createApi(TransNoticeApi.class).saveTransNotice(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity rst = JSON.parseObject(result, new TypeReference<BaseResultEntity>() {
                    });
                    if (rst != null && rst.getSuccess() != null && rst.getSuccess()) {
                        ToastUtil.toastShort("保存成功！");
                        getViewRef().closeFragment(true);
                    } else
                        ToastUtil.toastShort("保存失败！" + rst.getErrMsg());
                }

                @Override
                public void onError(ApiException e) {
                    ToastUtil.toastShort(e.getMessage());
                    LogUtil.e("调车通知编辑页面，保存", e.toString());
                }
            });
        } catch (Exception ex) {
            LogUtil.e("调车通知编辑页面，保存", ex.toString());
        }
    }

    public void submitTransNotice(TransNotice transNotice, List<TransNoticeTrain> transNoticeTrains) {
        try {
            //final boolean isNew = TextUtils.isEmpty(transNotice.getIdx());
            Map<String, Object> params = new HashMap<>();
            params.put("empId", SysInfo.userInfo.emp.getEmpid());
            params.put("empName", SysInfo.userInfo.emp.getEmpname());
            params.put("transNotice", JSON.toJSONString(transNotice, SerializerFeature.WriteNullStringAsEmpty));
            params.put("transNoticeTrains", JSON.toJSONString(transNoticeTrains, SerializerFeature.WriteNullStringAsEmpty));
            req(RequestFactory.getInstance().createApi(TransNoticeApi.class).saveTransNotice(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity rst = JSON.parseObject(result, new TypeReference<BaseResultEntity>() {
                    });
                    if (rst != null && rst.getSuccess() != null && rst.getSuccess()) {
                        //保存成功后进行提交操作，返回的id为保存成功后的通知单noticeIDX
                        Map<String, Object> params = new HashMap<>();
                        params.put("noticeIDX", rst.getId());
                        req(RequestFactory.getInstance().createApi(TransNoticeApi.class).submitNotice(params), new HttpOnNextListener() {
                            @Override
                            public void onNext(String result, String mothead) {
                                BaseResultEntity<TransNotice> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<TransNotice>>() {
                                });
                                if (rst != null && rst.getSuccess()) {
                                    getViewRef().closeFragment(true);
                                    ToastUtil.toastShort("提交成功！");
                                } else
                                    ToastUtil.toastShort("提交失败！");
                            }

                            @Override
                            public void onError(ApiException e) {
                                ToastUtil.toastShort(e.getMessage());
                                LogUtil.e("调车通知编辑页面，提交", e.toString());
                            }
                        });

                    } else
                        ToastUtil.toastShort("提交失败！" + rst.getErrMsg());
                }

                @Override
                public void onError(ApiException e) {
                    ToastUtil.toastShort(e.getMessage());
                    LogUtil.e("调车通知编辑页面，提交", e.toString());
                }
            });
        } catch (Exception ex) {
            LogUtil.e("调车通知编辑页面，提交", ex.toString());
        }
    }

    public void deleteNoticeTrainDetails(String noticeDetailsIDX, HttpOnNextListener listener) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("noticeDetailsIDX", noticeDetailsIDX);
            req(RequestFactory.getInstance().createApi(TransNoticeApi.class).deleteNoticeTrainDetails(params), listener);
        } catch (Exception ex) {
            getViewRef().pageNotifyNewDataSetChangedForNoticeTrain(null);
            LogUtil.e("调车通知编辑页面，删除机车机车", ex.toString());
        }
    }
}
