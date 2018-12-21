package jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.api.WorkshopTaskDispatchApi;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.EmpForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.OrgForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.WorkstationForWorkshopTaskDispatch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkshopTaskDispatchFwhPresenter extends BasePresenter<IWorkshopTaskDispatchFwh> {
    public WorkshopTaskDispatchFwhPresenter(IWorkshopTaskDispatchFwh view) {
        super(view);
    }

    public void getClassList(String processNodeIdx) {
        RequestFactory.getInstance().createApi(WorkshopTaskDispatchApi.class).getClassList(processNodeIdx).enqueue(new Callback<List<OrgForWorkshopTaskDispatch>>() {
            @Override
            public void onResponse(Call<List<OrgForWorkshopTaskDispatch>> call, Response<List<OrgForWorkshopTaskDispatch>> response) {
                List<OrgForWorkshopTaskDispatch> list = response.body();
                if (list != null) {
                    getViewRef().onClassListLoadSuccess(list);
                } else {
                    getViewRef().onLoadFail("获取作业班组记录失败，请重新访问");
                }
            }

            @Override
            public void onFailure(Call<List<OrgForWorkshopTaskDispatch>> call, Throwable t) {
                getViewRef().onLoadFail("获取作业班组记录失败，请重新访问" + t.getMessage());
            }
        });
    }

    public void getEmpList(String queryParams, String manager) {
        RequestFactory.getInstance().createApi(WorkshopTaskDispatchApi.class).getEmpList(queryParams, manager).enqueue(new Callback<List<EmpForWorkshopTaskDispatch>>() {
            @Override
            public void onResponse(Call<List<EmpForWorkshopTaskDispatch>> call, Response<List<EmpForWorkshopTaskDispatch>> response) {
                List<EmpForWorkshopTaskDispatch> list = response.body();
                if (list != null) {
                    getViewRef().onEmpListLoadSuccess(list);
                } else {
                    getViewRef().onLoadFail("获取作业人员记录失败，请重新访问");
                }

            }

            @Override
            public void onFailure(Call<List<EmpForWorkshopTaskDispatch>> call, Throwable t) {
                getViewRef().onLoadFail("获取作业人员记录失败，请重新访问" + t.getMessage());
            }
        });
    }

    public void getWorkstationList(String queryParams, String manager) {
        RequestFactory.getInstance().createApi(WorkshopTaskDispatchApi.class).getWorkstationList(queryParams, manager).enqueue(new Callback<BaseBean<List<WorkstationForWorkshopTaskDispatch>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<WorkstationForWorkshopTaskDispatch>>> call, Response<BaseBean<List<WorkstationForWorkshopTaskDispatch>>> response) {
                BaseBean<List<WorkstationForWorkshopTaskDispatch>> bean = response.body();
                if (bean != null && bean.getRoot() != null) {
                    getViewRef().onWorkstationListLoadSuccess(bean.getRoot());
                } else {
                    getViewRef().onLoadFail("获取作业台位记录失败，请重新访问");
                }

            }

            @Override
            public void onFailure(Call<BaseBean<List<WorkstationForWorkshopTaskDispatch>>> call, Throwable t) {
                getViewRef().onLoadFail("获取作业台位记录失败，请重新访问" + t.getMessage());
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
}
