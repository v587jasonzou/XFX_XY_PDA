package jx.yunda.com.terminal.modules.fixflow.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.fixflow.FlowInfoApi;
import jx.yunda.com.terminal.modules.fixflow.entry.FwhInfoBean;
import jx.yunda.com.terminal.modules.fixflow.entry.TicketInfoBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class getTphListPresenter extends BasePresenter<iGetTpList> {
    public getTphListPresenter(iGetTpList view) {
        super(view);
    }

    public void getTpList(String workPlanIdx,String type,String orgid,int start,int limit,final boolean isLoadmore){
        RequestFactory.getInstance().createApi(FlowInfoApi.class).getTpList(workPlanIdx
                , orgid,type,start,limit).enqueue(new Callback<BaseBean<List<TicketInfoBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<TicketInfoBean>>> call, Response<BaseBean<List<TicketInfoBean>>> response) {
                if(response!=null&&response.body()!=null){
                    if(response.body().getRoot()!=null){
                        if(!isLoadmore){
                            getViewRef().LoadTpListSuccess(response.body().getRoot());
                        }else {
                            getViewRef().LoadMoreTpListSuccess(response.body().getRoot());
                        }

                    }else {
                        getViewRef().LoadFaild("未获取到相关数据，请重试");
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseBean<List<TicketInfoBean>>> call, Throwable t) {
                getViewRef().LoadFaild("未获取到相关数据，请重试"+t.getMessage());
            }
        });
    }
}
