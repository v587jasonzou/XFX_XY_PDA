package jx.yunda.com.terminal.modules.quality.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.quality.model.QualityEquipPlanBean;
import jx.yunda.com.terminal.modules.quality.model.QualityTIcketPlanBean;
import jx.yunda.com.terminal.modules.quality.model.QualityTrainBean;
import jx.yunda.com.terminal.modules.quality.model.TrainBoby;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QualityPresenter extends BasePresenter<IQuality> {
    public QualityPresenter(IQuality view) {
        super(view);
    }

    public void getTrainList(String operatorId,String query){
        RequestFactory.getInstance().createApi(TicketApi.class).getQualityTrain(operatorId,query)
                .enqueue(new Callback<TrainBoby>() {
                    @Override
                    public void onResponse(Call<TrainBoby> call, Response<TrainBoby> response) {
                        int size = 0;
                        if(response!=null&&response.body()!=null){
                            if(response.body().getEntityList()!=null&&response.body().getEntityList().size()>0){
                                for(QualityTrainBean body:response.body().getEntityList()){
                                    size = body.getUndoCount()+size;
                                }
                                getViewRef().getTrainQualitySuccess(size);
                            }
                        }else {
                            getViewRef().getTrainQualityFaild("获取机车列表失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<TrainBoby> call, Throwable t) {
                        getViewRef().getTrainQualityFaild("获取机车列表失败");
                    }
                });
    }

    public void getPlans(String start,String limit,String checkWay,String entityJson,String operatorId,String qcstatus){
        RequestFactory.getInstance().createApi(TicketApi.class).getQualityEquipPlan(start,limit,checkWay
                ,entityJson,operatorId,qcstatus).enqueue(new Callback<BaseBean<List<QualityEquipPlanBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<QualityEquipPlanBean>>> call, Response<BaseBean<List<QualityEquipPlanBean>>> response) {
                if(response!=null&&response.body()!=null){
                    if(response.body().getRoot()!=null){
                        getViewRef().getEquipQualitySuccess(response.body().getRoot().size());
                    }else {
                        getViewRef().getEquipQualitySuccess(0);
                    }

                }else {
                    getViewRef().getEquipQualityFaild("获取质量检查任务失败");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<List<QualityEquipPlanBean>>> call, Throwable t) {
                getViewRef().getEquipQualityFaild("获取质量检查任务失败:"+t.getMessage());
            }
        });
    }

    public void getTIckets(String query,String start,String limit, String mode){
        RequestFactory.getInstance().createApi(TicketApi.class).getTicketTrains(query,start,limit,mode,"1")
                .enqueue(new Callback<BaseBean<List<QualityTIcketPlanBean>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<QualityTIcketPlanBean>>> call, Response<BaseBean<List<QualityTIcketPlanBean>>> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().getRoot()!=null){
                                getViewRef().getTicketQualitySuccess(response.body().getRoot().size());
                            }else {
                                getViewRef().getTicketQualitySuccess(0);
                            }
                        }else {
                            getViewRef().getTicketQualityFaild("获取检查列表失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<QualityTIcketPlanBean>>> call, Throwable t) {
                        getViewRef().getTicketQualityFaild("获取检查列表失败："+t.getMessage());
                    }
                });
    }
}
