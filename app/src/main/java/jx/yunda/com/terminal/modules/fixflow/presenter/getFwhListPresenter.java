package jx.yunda.com.terminal.modules.fixflow.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.fixflow.FlowInfoApi;
import jx.yunda.com.terminal.modules.fixflow.entry.FwhInfoBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class getFwhListPresenter extends BasePresenter<iGetFwhList> {
    public getFwhListPresenter(iGetFwhList view) {
        super(view);
    }
    public void getFwhList(String workPlanIdx,String type,String orgid,int start,int limit,final boolean isLoadmore){
        RequestFactory.getInstance().createApi(FlowInfoApi.class).getFwhList(workPlanIdx
        , orgid,type,start,limit).enqueue(new Callback<BaseBean<List<FwhInfoBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<FwhInfoBean>>> call, Response<BaseBean<List<FwhInfoBean>>> response) {
                if(response!=null&&response.body()!=null){
                    if(response.body().getRoot()!=null){
                        if(!isLoadmore){
                            getViewRef().LoadFwhListSuccess(response.body().getRoot());
                        }else {
                            getViewRef().LoadMoreFwhListSuccess(response.body().getRoot());
                        }

                    }else {
                        getViewRef().LoadFaild("未获取到相关数据，请重试");
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseBean<List<FwhInfoBean>>> call, Throwable t) {
                getViewRef().LoadFaild("未获取到相关数据，请重试"+t.getMessage());
            }
        });
    }


}
