package jx.yunda.com.terminal.modules.TrainRecandition.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.TrainRecandition.model.RecanditionBean;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecanditionHistoryPresenter extends BasePresenter<IRecanditionHistory> {
    public RecanditionHistoryPresenter(IRecanditionHistory view) {
        super(view);
    }

    public void GetRecanditionPlanList(String entityJson, String start, String limit, String isAll, String empid, final boolean isLoadmore){
        RequestFactory.getInstance().createApi(TicketApi.class).getTrainWorkPlan(entityJson,start,limit,isAll,empid,"")
                .enqueue(new Callback<BaseBean<List<RecanditionBean>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<RecanditionBean>>> call, Response<BaseBean<List<RecanditionBean>>> response) {
                        if(response!=null&&response.body()!=null){
                            if(isLoadmore){
                                getViewRef().LoadMoreSuccess(response.body().getRoot());
                            }else {
                                getViewRef().getPlansSuccess(response.body().getRoot());
                            }
                        }else {
                            getViewRef().getPlansFaild("获取巡检任务失败，请重试");
                        }


                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<RecanditionBean>>> call, Throwable t) {
                        getViewRef().getPlansFaild("获取巡检任务失败："+t.getMessage());
                    }
                });
    }
}
