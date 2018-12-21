package jx.yunda.com.terminal.modules.confirmNotify.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.confirmNotify.NotifyApi;
import jx.yunda.com.terminal.modules.confirmNotify.entity.NotifyDetailBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmNotifyPresenter extends BasePresenter<IConfirmNotify> {
    public ConfirmNotifyPresenter(IConfirmNotify view) {
        super(view);
    }
    public void getNotyFyInfo(int start,int limit ,String idx){
        RequestFactory.getInstance().createApi(NotifyApi.class).getNotifyDetailList(start,limit,idx)
                .enqueue(new Callback<BaseBean<List<NotifyDetailBean>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<NotifyDetailBean>>> call, Response<BaseBean<List<NotifyDetailBean>>> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().getRoot()!=null){
                                getViewRef().GetNotifySuccess(response.body().getRoot());
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().GetNotifyFaild("获取通知单详情失败"+response.body().getErrMsg());
                                }else {
                                    getViewRef().GetNotifyFaild("获取通知单详情失败,请重试");
                                }
                            }
                        }else {
                            getViewRef().GetNotifyFaild("获取通知单详情失败,请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<NotifyDetailBean>>> call, Throwable t) {
                        getViewRef().GetNotifyFaild("获取通知单详情失败,请重试"+t.getMessage());
                    }
                });
    }
    public void UploadLocation(String detail){
        RequestFactory.getInstance().createApi(NotifyApi.class).ChangeLocation(detail)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().isSuccess()){
                                getViewRef().OnConfirmLocationSuccess();
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().OnConfirmLoacationFaild("更新位置信息失败"+response.body().getErrMsg()+"，请重试");
                                }else {
                                    getViewRef().OnConfirmLoacationFaild("更新位置信息失败，请重试");
                                }
                            }
                        }else {
                            getViewRef().OnConfirmLoacationFaild("更新位置信息失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().OnConfirmLoacationFaild("更新位置信息失败，请重试"+t.getMessage());
                    }
                });


    }
    public void ConfirmNotify(String idx,long userid,String user){
        RequestFactory.getInstance().createApi(NotifyApi.class).ConfirmNotify(idx,userid,user)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().isSuccess()){
                                getViewRef().ConfirmSuccess();
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().ConfirmFaild("提交失败，"+response.body().getErrMsg());
                                }else {
                                    getViewRef().ConfirmFaild("提交失败，请重试");
                                }
                            }
                        }else {
                            getViewRef().ConfirmFaild("提交失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().ConfirmFaild("提交失败，请重试"+t.getMessage());
                    }
                });
    }
}
