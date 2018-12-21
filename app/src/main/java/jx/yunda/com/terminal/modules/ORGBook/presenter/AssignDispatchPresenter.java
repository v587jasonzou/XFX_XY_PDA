package jx.yunda.com.terminal.modules.ORGBook.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.ORGBook.model.BookUserBean;
import jx.yunda.com.terminal.modules.api.ForemanDispatchApi;
import jx.yunda.com.terminal.modules.api.ORGBookApi;
import jx.yunda.com.terminal.modules.foremandispatch.model.FwFormenBean;
import jx.yunda.com.terminal.modules.foremandispatch.model.TrainForForemanDispatch;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignDispatchPresenter extends BasePresenter<IAssignDispatch> {
    public AssignDispatchPresenter(IAssignDispatch view) {
        super(view);
    }
    public void getFwhList(int start, int limit, String workPlanIdx, final int type) {
        RequestFactory.getInstance().createApi(ForemanDispatchApi.class).getUnDispatchFwhList(start + "", limit + "", workPlanIdx).enqueue(new Callback<BaseBean<List<FwFormenBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<FwFormenBean>>> call, Response<BaseBean<List<FwFormenBean>>> response) {
                BaseBean<List<FwFormenBean>> bean = response.body();
                if (bean != null) {
                    List<FwFormenBean> list = bean.getRoot();
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
            public void onFailure(Call<BaseBean<List<FwFormenBean>>> call, Throwable t) {
                getViewRef().OnLoadFaid("获取范围活记录失败，请重新访问" + t.getMessage());
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
        RequestFactory.getInstance().createApi(ForemanDispatchApi.class).getDispatedchFwhList(start + "", limit + "", workPlanIdx).enqueue(new Callback<BaseBean<List<FwFormenBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<FwFormenBean>>> call, Response<BaseBean<List<FwFormenBean>>> response) {
                BaseBean<List<FwFormenBean>> bean = response.body();
                if (bean != null) {
                    List<FwFormenBean> list = bean.getRoot();
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
            public void onFailure(Call<BaseBean<List<FwFormenBean>>> call, Throwable t) {
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
