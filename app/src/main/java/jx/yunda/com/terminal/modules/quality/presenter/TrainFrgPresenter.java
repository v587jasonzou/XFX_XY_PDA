package jx.yunda.com.terminal.modules.quality.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.quality.model.TrainBoby;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainFrgPresenter extends BasePresenter<ITrainFrg> {
    public TrainFrgPresenter(ITrainFrg view) {
        super(view);
    }
    public void getTrainList(String operatorId,String query){
        RequestFactory.getInstance().createApi(TicketApi.class).getQualityTrain(operatorId,query)
                .enqueue(new Callback<TrainBoby>() {
                    @Override
                    public void onResponse(Call<TrainBoby> call, Response<TrainBoby> response) {
                        if(response!=null&&response.body()!=null){
                            getViewRef().OnLoadTrainSuccess(response.body().getEntityList());
                        }else {
                            getViewRef().OnLoadTrainFaild("获取机车列表失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<TrainBoby> call, Throwable t) {
                        getViewRef().OnLoadTrainFaild("获取机车列表失败"+t.getMessage());
                    }
                });
    }
}
