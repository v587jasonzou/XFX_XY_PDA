package jx.yunda.com.terminal.modules.confirmNotify.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.confirmNotify.NotifyApi;
import jx.yunda.com.terminal.modules.confirmNotify.entity.NotifyListBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetNotifyPresenter extends BasePresenter<IGetNotify> {
    public GetNotifyPresenter(IGetNotify view) {
        super(view);
    }
    public void getNotifyList(int start, int limit, String satrttime, String endTime, final boolean isLoadmore){
        RequestFactory.getInstance().createApi(NotifyApi.class).getNotifyList(start,limit,satrttime,endTime)
                .enqueue(new Callback<BaseBean<List<NotifyListBean>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<NotifyListBean>>> call, Response<BaseBean<List<NotifyListBean>>> response) {
                        if(response!=null&&response.body()!=null){
                            if (response.body().getRoot()!=null){
                                if(isLoadmore){
                                    getViewRef().OnLoadMoreNotifySuccess(response.body().getRoot());
                                }else {
                                    getViewRef().OnLoadNotifySuccess(response.body().getRoot());
                                }
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().OnLoadNotifyFaild("获取调车通知单失败"+response.body().getErrMsg());
                                }else {
                                    getViewRef().OnLoadNotifyFaild("获取调车通知单失败");
                                }
                            }
                        }else {
                            getViewRef().OnLoadNotifyFaild("获取调车通知单失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<NotifyListBean>>> call, Throwable t) {
                        getViewRef().OnLoadNotifyFaild("获取调车通知单失败"+t.getMessage());
                    }
                });
    }
}
