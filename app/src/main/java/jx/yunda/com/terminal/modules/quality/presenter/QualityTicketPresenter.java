package jx.yunda.com.terminal.modules.quality.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.quality.model.QualityTIcketPlanBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QualityTicketPresenter extends BasePresenter<IQualityTicket> {
    public QualityTicketPresenter(IQualityTicket view) {
        super(view);
    }
    public void getTrains(String query,String start,String limit, String mode,final boolean isLoad){
        RequestFactory.getInstance().createApi(TicketApi.class).getTicketTrains(query,start,limit,mode,"1")
                .enqueue(new Callback<BaseBean<List<QualityTIcketPlanBean>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<QualityTIcketPlanBean>>> call, Response<BaseBean<List<QualityTIcketPlanBean>>> response) {
                        if(response!=null&&response.body()!=null){
                            if(isLoad){
                                getViewRef().OnLoadMorePlansSuccess(response.body().getRoot());
                            }else {
                                getViewRef().OnLoadPlansSuccess(response.body().getRoot());
                            }

                        }else {
                            getViewRef().OnLoadPlansFaild("获取检查列表失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<QualityTIcketPlanBean>>> call, Throwable t) {
                        getViewRef().OnLoadPlansFaild("获取检查列表失败："+t.getMessage());
                    }
                });
    }
    public void PassQuality(String entityJson){
        RequestFactory.getInstance().createApi(TicketApi.class).QualityTicketPass(entityJson).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if(response!=null&&response.body()!=null){
                    if(response.body().isSuccess()){
                        getViewRef().OnSubmitSuccess();
                    }else {
                        getViewRef().OnSubmitFaild("通过失败,"+response.body().getErrMsg());
                    }
                }else {
                    getViewRef().OnSubmitFaild("连接服务器失败，请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().OnSubmitFaild("通过失败"+t.getMessage());
            }
        });
    }
}
