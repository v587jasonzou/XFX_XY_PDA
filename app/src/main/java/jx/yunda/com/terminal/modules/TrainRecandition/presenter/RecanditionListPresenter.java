package jx.yunda.com.terminal.modules.TrainRecandition.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.ArrayList;
import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.TrainRecandition.model.DownEquipBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.EquipBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.RecanditionBean;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecanditionListPresenter extends BasePresenter<IRecanditionList> {
    public RecanditionListPresenter(IRecanditionList view) {
        super(view);
    }

    public void GetRecanditionPlanList(String entityJson, String start, String limit, String isAll, String empid,String inVokDataType, final boolean isLoadmore){
        RequestFactory.getInstance().createApi(TicketApi.class).getTrainWorkPlan(entityJson,start,limit,isAll,empid,inVokDataType)
                .enqueue(new Callback<BaseBean<List<RecanditionBean>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<RecanditionBean>>> call, Response<BaseBean<List<RecanditionBean>>> response) {
                        if(response!=null&&response.body()!=null){
                            if(isLoadmore){
                                getViewRef().OnLoadMoreOlanSuccess(response.body().getRoot());
                            }else {
                                getViewRef().OnLoadPlanSuccess(response.body().getRoot());
                            }
                        }else {
                            getViewRef().OnLoadPlanFaild("获取巡检任务失败，请重试");
                        }


                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<RecanditionBean>>> call, Throwable t) {
                        getViewRef().OnLoadPlanFaild("获取巡检任务失败："+t.getMessage());
                    }
                });
    }
    public void startWork(String id, final int position){
        RequestFactory.getInstance().createApi(TicketApi.class).StartPlan(id).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if(response!=null&&response.body()!=null){
                    if(response.body().isSuccess()){
                        getViewRef().OnStartPlanSuccess(position);
                    }else {
                        getViewRef().OnStartPlanFaild("开工失败:"+response.body().getErrMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().OnStartPlanFaild("开工失败"+t.getMessage());
            }
        });
    }
    public void CompletePlan(String id , int up , int off,String operatorId, final int position){
        RequestFactory.getInstance().createApi(TicketApi.class).CompletePlan(id,up+"",off+"",operatorId).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if(response!=null&&response.body()!=null){
                    if(response.body().isSuccess()){
                        getViewRef().OnCompletPlanSuccess();
                    }else {
                        getViewRef().OnCompletPlanFaild("完工失败"+response.body().getErrMsg(),position);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().OnCompletPlanFaild("完工失败:"+t.getMessage(),position);
            }
        });
    }

    public void getUpEquips(String workPlanId , String nodeCaseIDX , String status, final int position){
        RequestFactory.getInstance().createApi(TicketApi.class).getUpEquips(workPlanId,nodeCaseIDX,status).enqueue(new Callback<BaseBean<ArrayList<EquipBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<ArrayList<EquipBean>>> call, Response<BaseBean<ArrayList<EquipBean>>> response) {
                if(response!=null&&response.body()!=null){
                    if(response.body().getRoot()!=null){
                        getViewRef().getUpPlanSuccess(response.body().getRoot().size(),position);
                    }else {
                        getViewRef().getUpPlanSuccess(0,position);
                    }
                }else {
                    getViewRef().getUpPlanFaild("获取上车配件失败请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<ArrayList<EquipBean>>> call, Throwable t) {
                getViewRef().getUpPlanFaild("获取上车配件失败:"+t.getMessage());
            }
        });
    }

    //获取下车配件列表
    public void getDownEquips(String workPlanId , String nodeCaseIDX , String status, final int position){
        RequestFactory.getInstance().createApi(TicketApi.class).getDownEquips(workPlanId,nodeCaseIDX,status).enqueue(new Callback<BaseBean<ArrayList<DownEquipBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<ArrayList<DownEquipBean>>> call, Response<BaseBean<ArrayList<DownEquipBean>>> response) {
                if(response!=null&&response.body()!=null){
                    if(response.body().getRoot()!=null){
                        getViewRef().getDownPlanSuccess(response.body().getRoot().size(),position);
                    }else {
                        getViewRef().getDownPlanSuccess(0,position);
                    }
                }else {
                    getViewRef().getDownPlanFaild("获取上车配件失败请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<ArrayList<DownEquipBean>>> call, Throwable t) {
                getViewRef().getDownPlanFaild("获取上车配件失败:"+t.getMessage());
            }
        });
    }


}
