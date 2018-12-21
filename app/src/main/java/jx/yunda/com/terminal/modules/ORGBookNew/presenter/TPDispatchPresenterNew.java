package jx.yunda.com.terminal.modules.ORGBookNew.presenter;

import com.alibaba.fastjson.JSON;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.ORGBook.model.BookUserBean;
import jx.yunda.com.terminal.modules.api.ForemanDispatchApi;
import jx.yunda.com.terminal.modules.api.ORGBookApi;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.foremandispatch.model.TrainForForemanDispatch;
import jx.yunda.com.terminal.modules.schedule.ScheduleApi;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TPDispatchPresenterNew extends BasePresenter<ITPWorkNew> {
    public TPDispatchPresenterNew(ITPWorkNew view) {
        super(view);
    }
    public void getTicketList(int start, int limit, String operatorId, String isDispatch, String entityJson, final int type) {
        RequestFactory.getInstance().createApi(ForemanDispatchApi.class).getTicketList(start + "", limit + "", operatorId, isDispatch, entityJson).enqueue(new Callback<BaseBean<List<FaultTicket>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<FaultTicket>>> call, Response<BaseBean<List<FaultTicket>>> response) {
                BaseBean<List<FaultTicket>> bean = response.body();
                if (bean.isSuccess()) {
                    List<FaultTicket> list = bean.getRoot();
                    if (type == 0) {
                        getViewRef().onTicketListLoadSuccess(list, bean.getTotalProperty());
                    } else {
                        getViewRef().onTicketListLoadMoreSuccess(list, bean.getTotalProperty());
                    }
                } else {
                    getViewRef().OnLoadFaid("获取提票记录失败，请重新访问");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<List<FaultTicket>>> call, Throwable t) {
                getViewRef().OnLoadFaid("获取提票记录失败，请重新访问" + t.getMessage());
            }
        });
    }
    public void dispatchTicket(String ids, String empids) {
        RequestFactory.getInstance().createApi(ForemanDispatchApi.class).dispatchTicket(ids, empids).enqueue(new Callback<BaseBean>() {
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
                    getViewRef().OnLoadFaid("提票活派工发生错误");
                }

            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().OnLoadFaid("提票活派工发生错误" + t.getMessage());
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
    public void UpLevel(String idx,String target,String orgnial){
        RequestFactory.getInstance().createApi(TicketApi.class).LevelUpTicket(idx,target,orgnial).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if(response!=null&&response.body()!=null){
                    if (response.body().isSuccess()){
                        getViewRef().OnBackSuccess();
                    }else {
                        getViewRef().OnLoadFaid("退回失败，请重试");
                    }
                }else {
                    getViewRef().OnLoadFaid("退回失败，请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().OnLoadFaid("退回失败，请重试"+t.getMessage());
            }
        });
    }
}
