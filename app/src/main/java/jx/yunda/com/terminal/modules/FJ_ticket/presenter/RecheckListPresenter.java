package jx.yunda.com.terminal.modules.FJ_ticket.presenter;

import android.util.Log;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.FJ_ticket.model.RecheckBean;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultTreeBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecheckListPresenter extends BasePresenter<IRecheckList> {
    public RecheckListPresenter(IRecheckList view) {
        super(view);
    }
    public void getRecheckList(String str){
        RequestFactory.getInstance().createApi(TicketApi.class).getRecheckList(str).enqueue(new Callback<BaseBean<List<RecheckBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<RecheckBean>>> call, Response<BaseBean<List<RecheckBean>>> response) {
                if(response.body()!=null){
                    if(response.body().getRoot()!=null&&response.body().getRoot().size()>0){
                        getViewRef().getRecheckListSuccess(response.body().getRoot());
                    }else {
                        getViewRef().getRecheckListSuccess(null);
                    }
                }else {
                    getViewRef().getReCheckListFaild("连接服务器失败，请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<List<RecheckBean>>> call, Throwable t) {
                getViewRef().getReCheckListFaild("获取部件信息失败:"+t.getMessage());
            }
        });
    }

    public void DeleteRecheck(String ids){
        RequestFactory.getInstance().createApi(TicketApi.class).DeleteRecheck(ids).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean bean = response.body();
                if(bean!=null){
                    if (bean.isSuccess()){
                        getViewRef().DeleteRecheckSuccess();
                    }else {
                        getViewRef().DeleteRecheckFaild("删除复检提票单失败");
                    }
                }else {
                    getViewRef().DeleteRecheckFaild("删除复检提票单失败");
                }
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().DeleteRecheckFaild("删除复检提票单失败:"+t.getMessage());
//                getViewRef().OnLoacationLoadFaild("获取部件信息失败:"+t.getMessage());
            }
        });
    }
}
