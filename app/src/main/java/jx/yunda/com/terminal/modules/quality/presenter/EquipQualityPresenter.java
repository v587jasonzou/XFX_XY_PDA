package jx.yunda.com.terminal.modules.quality.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.quality.model.QualityEquipPlanBean;
import jx.yunda.com.terminal.modules.quality.model.TrainQualityBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquipQualityPresenter extends BasePresenter<IEquipQuality> {
    public EquipQualityPresenter(IEquipQuality view) {
        super(view);
    }
    public void getPlans(String start,String limit,String checkWay,String entityJson,String operatorId,String qcstatus,final boolean isLoadmore){
        RequestFactory.getInstance().createApi(TicketApi.class).getQualityEquipPlan(start,limit,checkWay
        ,entityJson,operatorId,qcstatus).enqueue(new Callback<BaseBean<List<QualityEquipPlanBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<QualityEquipPlanBean>>> call, Response<BaseBean<List<QualityEquipPlanBean>>> response) {
                if(response!=null&&response.body()!=null){
                    if(isLoadmore){
                        getViewRef().OnLoadMorePlansSuccess(response.body().getRoot());
                    }else {
                        getViewRef().OnLoadPlansSuccess(response.body().getRoot());
                    }

                }else {
                    getViewRef().OnLoadPlansFaild("获取质量检查任务失败，请重试！");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<List<QualityEquipPlanBean>>> call, Throwable t) {
                getViewRef().OnLoadPlansFaild("获取质量检查任务失败:"+t.getMessage());
            }
        });
    }

    public void getNodes(String rdpIdx){
        RequestFactory.getInstance().createApi(TicketApi.class).getNodes(rdpIdx).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if(response!=null&&response.body()!=null){
                    getViewRef().getNodesSuccess(response.body().getNodeList());
                }else {
                    getViewRef().getNodesFaild("获取流程节点失败");
                }
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().getNodesFaild("获取流程节点失败");
            }
        });
    }
    public void BackQuality(String nodeIdxs,String qcItemNo,String backContent){
        RequestFactory.getInstance().createApi(TicketApi.class).QualityEquipBack(nodeIdxs,qcItemNo,backContent).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if(response!=null&&response.body()!=null){
                    if(response.body().isSuccess()){
                        getViewRef().OnDoBackSuccess();
                    }else {
                        getViewRef().OnDoBackFaild("返修失败,"+response.body().getErrMsg());
                    }
                }else {
                    getViewRef().OnDoBackFaild("连接服务器失败，请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().OnDoBackFaild("返修失败"+t.getMessage());
            }
        });
    }
    public void PassQuality(String entityJson){
        RequestFactory.getInstance().createApi(TicketApi.class).QualityEquipPass(entityJson).enqueue(new Callback<BaseBean>() {
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
