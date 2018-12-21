package jx.yunda.com.terminal.modules.foremandispatch.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.api.ForemanDispatchApi;
import jx.yunda.com.terminal.modules.foremandispatch.model.EmpForForemanDispatch;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.EmpForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.OrgForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.WorkstationForWorkshopTaskDispatch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForemanDispatchFwhPresenter extends BasePresenter<IForemanDispatchFwh> {
    public ForemanDispatchFwhPresenter(IForemanDispatchFwh view) {
        super(view);
    }

    public void getEmpList(String orgid, String emp) {
        RequestFactory.getInstance().createApi(ForemanDispatchApi.class).getEmpList(orgid, emp).enqueue(new Callback<BaseBean<List<EmpForForemanDispatch>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<EmpForForemanDispatch>>> call, Response<BaseBean<List<EmpForForemanDispatch>>> response) {
                BaseBean<List<EmpForForemanDispatch>> bean = response.body();
                if (bean != null && bean.getRoot() != null) {
                    getViewRef().onEmpListLoadSuccess(bean.getRoot());
                } else {
                    getViewRef().onLoadFail("获取作业人员记录失败，请重新访问");
                }

            }

            @Override
            public void onFailure(Call<BaseBean<List<EmpForForemanDispatch>>> call, Throwable t) {
                getViewRef().onLoadFail("获取作业人员记录失败，请重新访问" + t.getMessage());
            }
        });
    }

    public void dispatchFwh(String ids, String empIds, String empNames) {
        RequestFactory.getInstance().createApi(ForemanDispatchApi.class).dispatchFwh(ids, empIds, empNames).enqueue(new Callback<BaseBean>() {
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
