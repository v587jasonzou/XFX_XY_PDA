package jx.yunda.com.terminal.modules.foremandispatch.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.api.ForemanDispatchApi;
import jx.yunda.com.terminal.modules.foremandispatch.model.TrainForForemanDispatch;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForemanDispatchPresenter extends BasePresenter<IForemanDispatch> {
    public ForemanDispatchPresenter(IForemanDispatch view) {
        super(view);
    }
    public void getDispatchTrains(String operatorId, String query){
        RequestFactory.getInstance().createApi(ForemanDispatchApi.class).getForemanDispatchTrainList(operatorId, query).enqueue(new Callback<BaseBean<List<TrainForForemanDispatch>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<TrainForForemanDispatch>>> call, Response<BaseBean<List<TrainForForemanDispatch>>> response) {
                BaseBean<List<TrainForForemanDispatch>> bean = response.body();
                if(bean != null && bean.getRoot() != null){
                    getViewRef().LoadTrainListSuccess(bean.getRoot());
                }else {
                    getViewRef().OnLoadFaild("连接服务器失败，请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<List<TrainForForemanDispatch>>> call, Throwable t) {
                getViewRef().OnLoadFaild(t.getMessage());
            }
        });
    }

}
