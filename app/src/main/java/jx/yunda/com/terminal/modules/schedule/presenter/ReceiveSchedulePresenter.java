package jx.yunda.com.terminal.modules.schedule.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.schedule.ScheduleApi;
import jx.yunda.com.terminal.modules.schedule.entity.ReceiveTrainBean;
import jx.yunda.com.terminal.modules.schedule.entity.ReceiveUserBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiveSchedulePresenter extends BasePresenter<IReceiveSchedule> {
    public ReceiveSchedulePresenter(IReceiveSchedule view) {
        super(view);
    }
    public void getTrainList(){
        RequestFactory.getInstance().createApi(ScheduleApi.class).getReceiveTrains()
                .enqueue(new Callback<BaseBean<List<ReceiveTrainBean>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<ReceiveTrainBean>>> call, Response<BaseBean<List<ReceiveTrainBean>>> response) {
                        if(response.body()!=null){
                            if(response.body().getRoot()!=null&&response.body().getRoot().size()>0){
                                getViewRef().getTrainsSuccess(response.body().getRoot());
                            }else {
                                getViewRef().onLoadFaild("获取机车数据失败，请重试");
                            }
                        }else{
                            getViewRef().onLoadFaild("获取机车数据失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<ReceiveTrainBean>>> call, Throwable t) {
                        getViewRef().onLoadFaild("获取机车数据失败，请重试"+t.getMessage());
                    }
                });
    }
    public void getUserList(){
        RequestFactory.getInstance().createApi(ScheduleApi.class).getReceiveUsers().enqueue(new Callback<BaseBean<List<ReceiveUserBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<ReceiveUserBean>>> call, Response<BaseBean<List<ReceiveUserBean>>> response) {
                if(response.body()!=null){
                    if(response.body().getRoot()!=null&&response.body().getRoot().size()>0){
                        getViewRef().getUsersSuccess(response.body().getRoot());
                    }else {
                        getViewRef().onLoadFaild("人员信息失败，请重试");
                    }
                }else{
                    getViewRef().onLoadFaild("人员信息失败，请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<List<ReceiveUserBean>>> call, Throwable t) {
                getViewRef().onLoadFaild("人员信息失败，请重试"+t.getMessage());
            }
        });
    }
    public void deliverDispatch(String idx,String empId,String empName){
        RequestFactory.getInstance().createApi(ScheduleApi.class).deliverDispatch(idx,empId,empName)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response.body()!=null){
                            if(response.body().isSuccess()){
                                getViewRef().BookSuccess();
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().onLoadFaild("点名失败"+response.body().getErrMsg());
                                }else {
                                    getViewRef().onLoadFaild("点名失败,请重试");
                                }
                            }
                        }else {
                            getViewRef().onLoadFaild("点名失败,请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().onLoadFaild("点名失败,请重试"+t.getMessage());
                    }
                });
    }
}
