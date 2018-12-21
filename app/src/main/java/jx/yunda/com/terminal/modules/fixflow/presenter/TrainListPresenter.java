package jx.yunda.com.terminal.modules.fixflow.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.fixflow.FlowInfoApi;
import jx.yunda.com.terminal.modules.fixflow.entry.FlowTrainBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainListPresenter extends BasePresenter<iTrainList> {
    public TrainListPresenter(iTrainList view) {
        super(view);
    }
    public void getTrainList(String entityJson){
        RequestFactory.getInstance().createApi(FlowInfoApi.class).getTrainList(entityJson)
                .enqueue(new Callback<BaseBean<List<FlowTrainBean>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<FlowTrainBean>>> call, Response<BaseBean<List<FlowTrainBean>>> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().getRoot()!=null){
                                getViewRef().OnLoadSuccess(response.body().getRoot());
                            }else {
                                getViewRef().OnLoadFaild("未获取到相关机车信息");
                            }
                        }else {
                            getViewRef().OnLoadFaild("连接服务器失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<FlowTrainBean>>> call, Throwable t) {
                        getViewRef().OnLoadFaild("连接服务器失败，请重试"+t.getMessage());
                    }
                });
    }
}
