package jx.yunda.com.terminal.modules.partsrecandition.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.ArrayList;
import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.TrainRecandition.model.RecanditionBean;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.partsrecandition.model.ChildEquipBean;
import jx.yunda.com.terminal.modules.partsrecandition.model.ChildUpEquipBean;
import jx.yunda.com.terminal.modules.partsrecandition.model.PartsPlanBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartsPlanPresenter extends BasePresenter<IPartsPlans> {
    public PartsPlanPresenter(IPartsPlans view) {
        super(view);
    }
    public void GetRecanditionPlanList(String entityJson, String start, String limit ,final boolean isLoadmore){
        RequestFactory.getInstance().createApi(TicketApi.class).getPartsPlans(entityJson,start,limit)
                .enqueue(new Callback<BaseBean<List<PartsPlanBean>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<PartsPlanBean>>> call, Response<BaseBean<List<PartsPlanBean>>> response) {
                        if(response!=null&&response.body()!=null){
                            if(isLoadmore){
                                getViewRef().getMorePartSPlanSuccess(response.body().getRoot());
                            }else {
                                getViewRef().getPartSPlanSuccess(response.body().getRoot());
                            }
                        }else {
                            getViewRef().getPartsPlanFaild("获取巡检任务失败，请重试");
                        }


                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<PartsPlanBean>>> call, Throwable t) {
                        getViewRef().getPartsPlanFaild("获取检修任务失败："+t.getMessage());
                    }
                });
    }
    public void CompletPlan(String idx,String partsNo,String specificationModel,String partsOffCount,String partsOnCount){
        RequestFactory.getInstance().createApi(TicketApi.class).CompletePart(idx,partsNo,specificationModel,partsOffCount,partsOnCount)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().isSuccess()){
                                getViewRef().StartWorkSuccess();
                            }else {
                                getViewRef().StartWorkFaild("完工失败"+response.body().getMsg());
                            }
                        }else {
                            getViewRef().StartWorkFaild("完工失败,请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().StartWorkFaild("完工失败"+t.getMessage());
                    }
                });
    }
    public void getPartsDown(String rdpIDX,String nodeCaseIDX,String status,final int position){
        RequestFactory.getInstance().createApi(TicketApi.class).getDownParts(rdpIDX,nodeCaseIDX,status).enqueue(new Callback<BaseBean<ArrayList<ChildEquipBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<ArrayList<ChildEquipBean>>> call, Response<BaseBean<ArrayList<ChildEquipBean>>> response) {
                if(response!=null&&response.body()!=null){
                    if(response.body().getRoot()!=null){
                        getViewRef().getDownEquipSuccess(position,response.body().getRoot().size());
                    }else {
                        getViewRef().getDownEquipSuccess(position,0);
                    }
                }else {
                    getViewRef().getUpEquipFaild("获取下配件列表失败");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<ArrayList<ChildEquipBean>>> call, Throwable t) {
                getViewRef().getUpEquipFaild("获取下配件列表失败"+t.getMessage());
            }
        });
    }

    public void getPartsUp(String rdpIDX,String nodeCaseIDX,String status,final int position){
        RequestFactory.getInstance().createApi(TicketApi.class).getUpParts(rdpIDX,nodeCaseIDX,status).enqueue(new Callback<BaseBean<ArrayList<ChildUpEquipBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<ArrayList<ChildUpEquipBean>>> call, Response<BaseBean<ArrayList<ChildUpEquipBean>>> response) {
                if(response!=null&&response.body()!=null){
                    if(response.body().getRoot()!=null){
                        getViewRef().getUpEquipSuccess(position,response.body().getRoot().size());
                    }else {
                        getViewRef().getUpEquipSuccess(position,0);
                    }
                }else {
                    getViewRef().getUpEquipFaild("获取下配件列表失败");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<ArrayList<ChildUpEquipBean>>> call, Throwable t) {
                getViewRef().getUpEquipFaild("获取下配件列表失败"+t.getMessage());
            }
        });
    }
}
