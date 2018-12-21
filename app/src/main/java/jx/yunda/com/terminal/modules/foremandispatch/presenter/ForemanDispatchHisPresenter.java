package jx.yunda.com.terminal.modules.foremandispatch.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.api.ForemanDispatchApi;
import jx.yunda.com.terminal.modules.foremandispatch.model.FwFormenBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FwhBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForemanDispatchHisPresenter extends BasePresenter<IForemanDispatchList> {
    public ForemanDispatchHisPresenter(IForemanDispatchList view) {
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
                    getViewRef().onLoadFail("获取提票记录失败，请重新访问");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<List<FaultTicket>>> call, Throwable t) {
                getViewRef().onLoadFail("获取提票记录失败，请重新访问" + t.getMessage());
            }
        });
    }

    public void getFwhList(int start, int limit, String workPlanIdx, final int type) {
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
                    getViewRef().onLoadFail("获取范围活记录失败，请重新访问");
                }

            }

            @Override
            public void onFailure(Call<BaseBean<List<FwFormenBean>>> call, Throwable t) {
                getViewRef().onLoadFail("获取范围活记录失败，请重新访问" + t.getMessage());
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
                    getViewRef().onLoadFail("范围活派工发生错误");
                }

            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().onLoadFail("范围活派工发生错误" + t.getMessage());
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
