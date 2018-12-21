package jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.api.WorkshopTaskDispatchApi;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FwhBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkshopTaskDispatchListPresenter extends BasePresenter<IWorkshopTaskDispatchList> {
    public WorkshopTaskDispatchListPresenter(IWorkshopTaskDispatchList view) {
        super(view);
    }

    public void getTicketList(int start, int limit, String isDispatch, String entityJson, final int type) {
        RequestFactory.getInstance().createApi(WorkshopTaskDispatchApi.class).getTicketList(start + "", limit + "", isDispatch, entityJson).enqueue(new Callback<BaseBean<List<FaultTicket>>>() {
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
                    getViewRef().onLoadFail("获取提票记录失败，请重新访问");
                }

            }

            @Override
            public void onFailure(Call<BaseBean<List<FaultTicket>>> call, Throwable t) {
                getViewRef().onLoadFail("获取提票记录失败，请重新访问" + t.getMessage());
            }
        });
    }

    public void getFwhList(int start, int limit, String isSendNode, String workPlanIdx, final int type) {
        RequestFactory.getInstance().createApi(WorkshopTaskDispatchApi.class).getFwhList(start + "", limit + "", isSendNode, workPlanIdx).enqueue(new Callback<BaseBean<List<FwhBean>>>() {
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
                    getViewRef().onLoadFail("获取范围活记录失败，请重新访问");
                }

            }

            @Override
            public void onFailure(Call<BaseBean<List<FwhBean>>> call, Throwable t) {
                getViewRef().onLoadFail("获取范围活记录失败，请重新访问" + t.getMessage());
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
                    getViewRef().onLoadFail("范围活派工发生错误");
                }

            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().onLoadFail("范围活派工发生错误" + t.getMessage());
            }
        });
    }

    public void dispatchTicket(String ids, String entityJson) {
        RequestFactory.getInstance().createApi(WorkshopTaskDispatchApi.class).dispatchTicket(ids, entityJson).enqueue(new Callback<BaseBean>() {
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
                    getViewRef().onLoadFail("提票活派工发生错误");
                }

            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().onLoadFail("提票活派工发生错误" + t.getMessage());
            }
        });
    }
}
