package jx.yunda.com.terminal.modules.schedule.presenter;

import com.alibaba.fastjson.JSON;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.schedule.ScheduleApi;
import jx.yunda.com.terminal.modules.schedule.entity.OrgBean;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;
import jx.yunda.com.terminal.modules.schedule.entity.StationBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleBookPresenter extends BasePresenter<IScheduleBook> {
    public ScheduleBookPresenter(IScheduleBook view) {
        super(view);
    }

    public void getStationList(String idx) {
        RequestFactory.getInstance().createApi(ScheduleApi.class).getStationList(idx)
                .enqueue(new Callback<BaseBean<List<StationBean>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<StationBean>>> call, Response<BaseBean<List<StationBean>>> response) {
                        if (response != null && response.body() != null) {
                            if (response.body().getRoot() != null) {
                                getViewRef().OnloadStationSuccess(response.body().getRoot());
                            }else {
                                getViewRef().OnLoadStationFaild("获取台位失败");
                            }
                        }else {
                            getViewRef().OnLoadStationFaild("获取台位失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<StationBean>>> call, Throwable t) {
                        getViewRef().OnLoadStationFaild("获取台位失败"+t.getMessage());
                    }
                });
    }

    public void getTeamList(String idx) {
        RequestFactory.getInstance().createApi(ScheduleApi.class).getTeamList(idx).enqueue(new Callback<BaseBean<List<OrgBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<OrgBean>>> call, Response<BaseBean<List<OrgBean>>> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getRoot() != null) {
                        getViewRef().OnLoadOrgSuccess(response.body().getRoot());
                    }else {
                        getViewRef().OnLoadStationFaild("获取人员/班组信息失败，请重试");
                    }

                }else {
                    getViewRef().OnLoadStationFaild("获取人员/班组信息失败，请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<List<OrgBean>>> call, Throwable t) {
                getViewRef().OnLoadStationFaild("获取人员/班组信息失败，请重试");
            }
        });
    }
    public void getOrgList() {
        RequestFactory.getInstance().createApi(ScheduleApi.class).getTeamList().enqueue(new Callback<BaseBean<List<OrgBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<OrgBean>>> call, Response<BaseBean<List<OrgBean>>> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getRoot() != null) {
                        getViewRef().OnLoadOrgSuccess(response.body().getRoot());
                    }else {
                        getViewRef().OnLoadStationFaild("获取人员/班组信息失败，请重试");
                    }

                }else {
                    getViewRef().OnLoadStationFaild("获取人员/班组信息失败，请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<List<OrgBean>>> call, Throwable t) {
                getViewRef().OnLoadStationFaild("获取人员/班组信息失败，请重试");
            }
        });
    }
    public void Book(String data){
        RequestFactory.getInstance().createApi(ScheduleApi.class).Book(data)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().isSuccess()){
                                getViewRef().submitStationSuccess();
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().submitStationFaild("点名失败，请重试"+response.body().getErrMsg());
                                }else {
                                    getViewRef().submitStationFaild("点名失败，请重试");
                                }
                            }
                        }else {
                            getViewRef().submitStationFaild("点名失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().submitStationFaild("点名失败，请重试"+t.getMessage());
                    }
                });
    }
    public void getTarinList(){
        Map<String,Object> map = new HashMap<>();
        map.put("workPlanStatus","INITIALIZE,ONGOING");
        RequestFactory.getInstance().createApi(ScheduleApi.class).getTrainList(0,1000, JSON.toJSONString(map))
                .enqueue(new Callback<BaseBean<List<ScheduleTrainBean>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<ScheduleTrainBean>>> call, Response<BaseBean<List<ScheduleTrainBean>>> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().getRoot()!=null){
                                getViewRef().OnLoadTrainSuccess(response.body().getRoot());

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

    public void ScduleBook(String data){
        RequestFactory.getInstance().createApi(ScheduleApi.class).ScheduleBook(data)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().isSuccess()){
                                getViewRef().submitBookSuccess();
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().submitStationFaild("点名失败，请重试"+response.body().getErrMsg());
                                }else {
                                    getViewRef().submitStationFaild("点名失败，请重试");
                                }
                            }
                        }else {
                            getViewRef().submitStationFaild("点名失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().submitStationFaild("点名失败，请重试"+t.getMessage());
                    }
                });
    }

    public void CancleScduleBook(String idx,String orgid,String orgName){
        RequestFactory.getInstance().createApi(ScheduleApi.class).CancelScheduleBook(idx,orgid,orgName)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().isSuccess()){
                                getViewRef().submitStationSuccess();
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().submitStationFaild("取消点名失败，请重试"+response.body().getErrMsg());
                                }else {
                                    getViewRef().submitStationFaild("取消点名失败，请重试");
                                }
                            }
                        }else {
                            getViewRef().submitStationFaild("取消点名失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().submitStationFaild("点名失败，请重试"+t.getMessage());
                    }
                });
    }
}
