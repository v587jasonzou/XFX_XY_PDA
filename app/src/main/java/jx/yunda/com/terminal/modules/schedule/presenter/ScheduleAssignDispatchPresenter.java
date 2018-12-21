package jx.yunda.com.terminal.modules.schedule.presenter;

import com.alibaba.fastjson.JSON;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.ORGBook.model.BookUserBean;
import jx.yunda.com.terminal.modules.ORGBook.presenter.IAssignDispatch;
import jx.yunda.com.terminal.modules.api.ForemanDispatchApi;
import jx.yunda.com.terminal.modules.api.ORGBookApi;
import jx.yunda.com.terminal.modules.api.WorkshopTaskDispatchApi;
import jx.yunda.com.terminal.modules.foremandispatch.model.FwFormenBean;
import jx.yunda.com.terminal.modules.foremandispatch.model.TrainForForemanDispatch;
import jx.yunda.com.terminal.modules.schedule.ScheduleApi;
import jx.yunda.com.terminal.modules.schedule.entity.OrgBean;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FwhBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.OrgForWorkshopTaskDispatch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleAssignDispatchPresenter extends BasePresenter<IScheduleAssignDispatch> {
    public ScheduleAssignDispatchPresenter(IScheduleAssignDispatch view) {
        super(view);
    }
    public void getFwhList(int start, int limit, String workPlanIdx, final int type) {
        RequestFactory.getInstance().createApi(WorkshopTaskDispatchApi.class).getFwhList(start + "", limit + "", "0", workPlanIdx).enqueue(new Callback<BaseBean<List<FwhBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<FwhBean>>> call, Response<BaseBean<List<FwhBean>>> response) {
                BaseBean<List<FwhBean>> bean = response.body();
                if (bean != null) {
                    List<FwhBean> list = bean.getRoot();
                    if (type == 0) {
                        getViewRef().onFwhListLoadSuccess(list, bean.getTotalProperty());
                    } else {
                        getViewRef().onFwhListLoadMoreSuccess(list, bean.getTotalProperty());
                    }
                } else {
                    getViewRef().OnLoadFaid("获取范围活记录失败，请重新访问");
                }

            }

            @Override
            public void onFailure(Call<BaseBean<List<FwhBean>>> call, Throwable t) {
                getViewRef().OnLoadFaid("获取范围活记录失败，请重新访问" + t.getMessage());
            }
        });
    }

    public void getFwhListNew(String idx,String isDispatche) {
        RequestFactory.getInstance().createApi(ScheduleApi.class).getScheduleFwh(idx, isDispatche).enqueue(new Callback<BaseBean<List<FwhBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<FwhBean>>> call, Response<BaseBean<List<FwhBean>>> response) {
                BaseBean<List<FwhBean>> bean = response.body();
                if (bean != null) {
                    List<FwhBean> list = bean.getRoot();
                    getViewRef().onFwhListLoadSuccess(list, bean.getTotalProperty());
                } else {
                    getViewRef().OnLoadFaid("获取范围活记录失败，请重新访问");
                }

            }

            @Override
            public void onFailure(Call<BaseBean<List<FwhBean>>> call, Throwable t) {
                getViewRef().OnLoadFaid("获取范围活记录失败，请重新访问" + t.getMessage());
            }
        });
    }
    public void dispatchFwh(String nodes) {
        RequestFactory.getInstance().createApi(WorkshopTaskDispatchApi.class).dispatchFwh(nodes).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean bean = response.body();
                if (bean != null) {
                    if (bean.isSuccess()) {
                        getViewRef().onDispatchSuccess(bean.getMsg());
                    } else {
                        getViewRef().onDispatchFail(bean.getErrMsg());
                    }
                } else {
                    getViewRef().onDispatchFail("范围活派工发生错误");
                }

            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().onDispatchFail("范围活派工发生错误" + t.getMessage());
            }
        });
    }
    public void dispatchFwh(String ids, String empids, String empnames) {
        RequestFactory.getInstance().createApi(ForemanDispatchApi.class).dispatchFwh(ids, empids, empnames).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean bean = response.body();
                if (bean != null) {
                    if (bean.isSuccess()) {
                        getViewRef().onDispatchSuccess(bean.getMsg());
                    } else {
                        getViewRef().onDispatchFail(bean.getErrMsg());
                    }
                } else {
                    getViewRef().onDispatchFail("范围活派工发生错误");
                }

            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().onDispatchFail("范围活派工发生错误" + t.getMessage());
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
                        getViewRef().OnLoadFaid("获取人员/班组信息失败，请重试");
                    }

                }else {
                    getViewRef().OnLoadFaid("获取人员/班组信息失败，请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<List<OrgBean>>> call, Throwable t) {
                getViewRef().OnLoadFaid("获取人员/班组信息失败，请重试");
            }
        });
    }
    public void getClassList(String processNodeIdx) {
        RequestFactory.getInstance().createApi(WorkshopTaskDispatchApi.class).getClassList(processNodeIdx).enqueue(new Callback<List<OrgForWorkshopTaskDispatch>>() {
            @Override
            public void onResponse(Call<List<OrgForWorkshopTaskDispatch>> call, Response<List<OrgForWorkshopTaskDispatch>> response) {
                List<OrgForWorkshopTaskDispatch> list = response.body();
                if (list != null) {
                    getViewRef().onClassListLoadSuccess(list);
                } else {
                    getViewRef().OnLoadFaid("获取作业班组记录失败，请重新访问");
                }
            }

            @Override
            public void onFailure(Call<List<OrgForWorkshopTaskDispatch>> call, Throwable t) {
                getViewRef().OnLoadFaid("获取作业班组记录失败，请重新访问" + t.getMessage());
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
                                    getViewRef().OnLoadFaid("未获取到在修车数据，请重试"+response.body().getErrMsg());
                                }else {
                                    getViewRef().OnLoadFaid("未获取到在修车数据，请重试");
                                }
                            }
                        }else {
                            getViewRef().OnLoadFaid("未获取到在修车数据，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<ScheduleTrainBean>>> call, Throwable t) {
                        getViewRef().OnLoadFaid("未获取到在修车数据，请重试"+t.getMessage());
                    }
                });
    }
    public void getDispatchTrains(String operatorId, String query){
        RequestFactory.getInstance().createApi(ForemanDispatchApi.class).getForemanDispatchTrainList(operatorId, query).enqueue(new Callback<BaseBean<List<TrainForForemanDispatch>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<TrainForForemanDispatch>>> call, Response<BaseBean<List<TrainForForemanDispatch>>> response) {
                BaseBean<List<TrainForForemanDispatch>> bean = response.body();
                if(bean != null && bean.getRoot() != null){
                    getViewRef().LoadTrainListSuccess(bean.getRoot());
                }else {
                    getViewRef().OnLoadFaid("连接服务器失败，请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<List<TrainForForemanDispatch>>> call, Throwable t) {
                getViewRef().OnLoadFaid(t.getMessage());
            }
        });
    }
    public void getCompleteFwhList(int start, int limit, String workPlanIdx, final int type) {
        RequestFactory.getInstance().createApi(WorkshopTaskDispatchApi.class).getFwhList(start + "", limit + "", "1", workPlanIdx).enqueue(new Callback<BaseBean<List<FwhBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<FwhBean>>> call, Response<BaseBean<List<FwhBean>>> response) {
                BaseBean<List<FwhBean>> bean = response.body();
                if (bean != null) {
                    List<FwhBean> list = bean.getRoot();
                    if (type == 0) {
                        getViewRef().onFwhListLoadSuccess(list, bean.getTotalProperty());
                    } else {
                        getViewRef().onFwhListLoadMoreSuccess(list, bean.getTotalProperty());
                    }
                } else {
                    getViewRef().OnLoadFaid("获取范围活记录失败，请重新访问");
                }

            }

            @Override
            public void onFailure(Call<BaseBean<List<FwhBean>>> call, Throwable t) {
                getViewRef().OnLoadFaid("获取范围活记录失败，请重新访问" + t.getMessage());
            }
        });
    }
    public void getUsers(String orgId,String emp){
        RequestFactory.getInstance().createApi(ORGBookApi.class).getBookUsers(orgId,emp)
                .enqueue(new Callback<BaseBean<List<BookUserBean>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<BookUserBean>>> call, Response<BaseBean<List<BookUserBean>>> response) {
                        if(response!=null&&response.body()!=null){
                            getViewRef().onEmpListLoadSuccess(response.body().getRoot());
                        }else {
                            getViewRef().onLoadEmpListLoadFail("获取班组人员信息失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<BookUserBean>>> call, Throwable t) {
                        getViewRef().onLoadEmpListLoadFail("获取班组人员信息失败，请重试"+t.getMessage());
                    }
                });
    }
}
