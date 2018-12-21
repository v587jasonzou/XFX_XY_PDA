package jx.yunda.com.terminal.modules.TrainRecandition.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.TrainRecandition.model.DownEquipBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.ModulesBean;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownEquipPresenter extends BasePresenter<IDownEquip> {
    public DownEquipPresenter(IDownEquip view) {
        super(view);
    }
    public void getDownEquips(String workPlanId , String nodeCaseIDX , String status){
        RequestFactory.getInstance().createApi(TicketApi.class).getDownEquips(workPlanId,nodeCaseIDX,status).enqueue(new Callback<BaseBean<ArrayList<DownEquipBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<ArrayList<DownEquipBean>>> call, Response<BaseBean<ArrayList<DownEquipBean>>> response) {
                if(response!=null&&response.body()!=null){
                    if(response.body().getRoot()!=null){
                        getViewRef().getDowmEquipPlansSuccess(response.body().getRoot());
                    }else {
                        getViewRef().getDowmEquipPlansFaild("获取上车配件失败请重试");
                    }
                }else {
                    getViewRef().getDowmEquipPlansFaild("获取上车配件失败请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<ArrayList<DownEquipBean>>> call, Throwable t) {
                getViewRef().getDowmEquipPlansFaild("获取上车配件失败:"+t.getMessage());
            }
        });
    }
    public void getEquipCode(String specificationModel,String partsStatus){
        RequestFactory.getInstance().createApi(TicketApi.class).getEquipCodes(specificationModel,partsStatus)
                .enqueue(new Callback<List<Map<String,String>>>() {
                    @Override
                    public void onResponse(Call<List<Map<String,String>>> call, Response<List<Map<String,String>>> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().size()>0){
                                List<String> list = new ArrayList<>();
                                for(Map<String,String>map:response.body()){
                                    list.add(map.get("partsNo"));
                                }
                                getViewRef().OnPartsNoLoadSuccess(list);
                            }
                        }else {
                            getViewRef().OnPartsNoLoadFaild("获取编号失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Map<String,String>>> call, Throwable t) {
                        getViewRef().OnPartsNoLoadFaild("获取编号失败，请重试");
                    }
                });
    }
    public void getEquipTypes(String entityJson){
        RequestFactory.getInstance().createApi(TicketApi.class).getEquipType(entityJson).enqueue(new Callback<BaseBean<List<ModulesBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<ModulesBean>>> call, Response<BaseBean<List<ModulesBean>>> response) {
                if(response!=null&&response.body()!=null){
                    if(response.body().getRoot()!=null){
                        getViewRef().getTypeSuccess(response.body().getRoot());
                    }else {
                        getViewRef().getTypeFaild("为查询到相关规格型号，请重试");
                    }
                }else {
                    getViewRef().getTypeFaild("连接服务器失败，请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<List<ModulesBean>>> call, Throwable t) {
                getViewRef().getTypeFaild("连接服务器失败，请重试:"+t.getMessage());
            }
        });
    }
    public void DownEquip(String str,String str2){
        RequestFactory.getInstance().createApi(TicketApi.class).DownEquip(str,str2).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if(response!=null&&response.body()!=null){
                    if(response.body().isSuccess()){
                        getViewRef().OnDownSubmitSuccess();
                    }else {
                        getViewRef().OnDownsubmitFaild("下车登记失败"+response.body().getErrMsg());
                    }
                }else {
                    getViewRef().OnDownsubmitFaild("连接服务器失败，请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().OnDownsubmitFaild("下车登记失败"+t.getMessage());
            }
        });
    }

    public void DownEquipBack(String str){
        RequestFactory.getInstance().createApi(TicketApi.class).DownEquipBack(str).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if(response!=null&&response.body()!=null){
                    if(response.body().isSuccess()){
                        getViewRef().OnDeletSuccess();
                    }else {
                        getViewRef().OnDeletFaild("撤销失败"+response.body().getErrMsg());
                    }
                }else {
                    getViewRef().OnDeletFaild("连接服务器失败，请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().OnDeletFaild("撤销失败"+t.getMessage());
            }
        });
    }
}
