package jx.yunda.com.terminal.modules.TrainRecandition.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.TrainRecandition.model.JXTrainBean;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecanditionTrainPresenter extends BasePresenter<IRecanditionTrain> {
    public RecanditionTrainPresenter(IRecanditionTrain view) {
        super(view);
    }
    public void getTicketTrains(String msg,int start,int limit,String invokDataType){
        RequestFactory.getInstance().createApi(TicketApi.class).getJXTrainList(msg,start,limit,invokDataType).enqueue(new Callback<BaseBean<List<JXTrainBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<JXTrainBean>>> call, Response<BaseBean<List<JXTrainBean>>> response) {
                if(response!=null&&response.body()!=null){
                    getViewRef().LoadTrainListSuccess(response.body().getRoot());
                }else {
                    getViewRef().OnLoadFaild("获取再修机车列表失败，请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<List<JXTrainBean>>> call, Throwable t) {
                getViewRef().OnLoadFaild("连接服务器失败，请重试"+t.getMessage());
            }
        });

    }
}
