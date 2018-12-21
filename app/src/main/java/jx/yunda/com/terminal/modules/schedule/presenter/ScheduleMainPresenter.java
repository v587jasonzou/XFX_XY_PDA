package jx.yunda.com.terminal.modules.schedule.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.schedule.ScheduleApi;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleMainPresenter extends BasePresenter<IScheduleMain> {
    public ScheduleMainPresenter(IScheduleMain view) {
        super(view);
    }
    public void getTarinList(int start, int limit, String entityjson, final boolean isLoadmore){
        RequestFactory.getInstance().createApi(ScheduleApi.class).getTrainList(start,limit,entityjson)
                .enqueue(new Callback<BaseBean<List<ScheduleTrainBean>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<ScheduleTrainBean>>> call, Response<BaseBean<List<ScheduleTrainBean>>> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().getRoot()!=null){
                                if(isLoadmore){
                                    getViewRef().OnLoadMoreTrainSuccess(response.body().getRoot());
                                }else {
                                    getViewRef().OnLoadTrainSuccess(response.body().getRoot());
                                }

                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().OnLoadTrainFaild("未获取到在修车数据，请重试"+response.body().getErrMsg());
                                }else {
                                    getViewRef().OnLoadTrainFaild("未获取到在修车数据，请重试");
                                }
                            }
                        }else {
                            getViewRef().OnLoadTrainFaild("未获取到在修车数据，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<ScheduleTrainBean>>> call, Throwable t) {
                        getViewRef().OnLoadTrainFaild("未获取到在修车数据，请重试"+t.getMessage());
                    }
                });
    }
    public void UpStation(String data){
        RequestFactory.getInstance().createApi(ScheduleApi.class).UpStation(data)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().isSuccess()){
                                getViewRef().OnUpStationSuccess();
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().OnUpStationFaild("机车上台失败"+response.body().getErrMsg());
                                }else {
                                    getViewRef().OnUpStationFaild("机车上台失败，请重试");
                                }
                            }
                        }else {
                            getViewRef().OnUpStationFaild("机车上台失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().OnUpStationFaild("机车上台失败，请重试"+t.getMessage());
                    }
                });
    }
}
