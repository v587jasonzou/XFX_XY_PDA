package jx.yunda.com.terminal.modules.foremandispatch.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.ArrayList;
import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.api.ForemanDispatchApi;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.api.WorkshopTaskDispatchApi;
import jx.yunda.com.terminal.modules.foremandispatch.model.EmpForForemanDispatch;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.OrgForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter.IWorkshopTaskDispatchTicket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForemanDispatchTicketPresenter extends BasePresenter<IForemanDispatchTicket> {
    public ForemanDispatchTicketPresenter(IForemanDispatchTicket view) {
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

    public void getImages(String attachmentKeyIDX, String node){
        RequestFactory.getInstance().createApi(TicketApi.class).getImages(attachmentKeyIDX,node).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean bean = response.body();
                List<String> images = new ArrayList<>();
                if(bean.getFileUrlList()!=null){
                    images.addAll(bean.getFileUrlList());
                }
                getViewRef().onGetImagesSuccess(images);
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().onLoadFail("获取故障现象联想失败"+t.getMessage());
            }
        });

    }
}
