package jx.yunda.com.terminal.modules.FJ_ticket.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.FJ_ticket.model.RecheckTrainBean;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTrainBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeTicketRecheckPresenter extends BasePresenter<IMakeTicketRecheck> {
    public MakeTicketRecheckPresenter(IMakeTicketRecheck view) {
        super(view);
    }
    public void getTicketTrains(String entityJson,String start,String limit,String status,String recheckType,
                                String type,String empid){
        RequestFactory.getInstance().createApi(TicketApi.class).getRecheckTrainList(entityJson,start,limit,status,recheckType
        ,type,empid).enqueue(new Callback<BaseBean<List<RecheckTrainBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<RecheckTrainBean>>> call, Response<BaseBean<List<RecheckTrainBean>>> response) {
                BaseBean<List<RecheckTrainBean>> bean = response.body();
                if(bean!=null&&bean.getRoot()!=null){
                    if(bean.getRoot().size()>0){
                        getViewRef().LoadTrainListSuccess(bean.getRoot());
                    }else {
                        getViewRef().LoadTrainListSuccess(null);
                    }
                }else {
                    getViewRef().OnLoadFaild("连接服务器失败，请重试");
                }

            }

            @Override
            public void onFailure(Call<BaseBean<List<RecheckTrainBean>>> call, Throwable t) {
                getViewRef().OnLoadFaild(t.getMessage());
            }
        });
    }

    //复检提票提交
    public void Submit(String entityJson,String recheckType,String empid){
        RequestFactory.getInstance().createApi(TicketApi.class).SubmitRecheck(entityJson,recheckType,empid).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean bean = response.body();
                if(bean!=null&&bean.getMsg()!=null){
                  if(bean.getMsg().equals("success")){
                      getViewRef().OnSubmitSuccess();
                  }
                }else {
                    getViewRef().OnSubmitFaild("连接服务器失败，请重试");
                }

            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().OnSubmitFaild(t.getMessage());
            }
        });
    }
}
