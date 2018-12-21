package jx.yunda.com.terminal.modules.partsrecandition.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.TrainRecandition.model.RecanditionBean;
import jx.yunda.com.terminal.modules.TrainRecandition.presenter.IRecanditionHistory;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.partsrecandition.model.PartsPlanBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartsHistoryPresenter extends BasePresenter<IPartsHistory> {
    public PartsHistoryPresenter(IPartsHistory view) {
        super(view);
    }

    public void GetRecanditionPlanList(String entityJson, String start, String limit, final boolean isLoadmore){
        RequestFactory.getInstance().createApi(TicketApi.class).getPartsPlans(entityJson,start,limit)
                .enqueue(new Callback<BaseBean<List<PartsPlanBean>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<PartsPlanBean>>> call, Response<BaseBean<List<PartsPlanBean>>> response) {
                        if(response!=null&&response.body()!=null){
                            if(isLoadmore){
                                getViewRef().LoadMoreSuccess(response.body().getRoot());
                            }else {
                                getViewRef().getPlansSuccess(response.body().getRoot());
                            }
                        }else {
                            getViewRef().getPlansFaild("获取检修任务失败，请重试");
                        }


                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<PartsPlanBean>>> call, Throwable t) {
                        getViewRef().getPlansFaild("获取检修任务失败："+t.getMessage());
                    }
                });
    }
}
