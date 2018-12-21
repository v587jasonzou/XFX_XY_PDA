package jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.ArrayList;
import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.entity.FileBaseBean;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.api.WorkshopTaskDispatchApi;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.OrgForWorkshopTaskDispatch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkshopTaskDispatchTicketPresenter extends BasePresenter<IWorkshopTaskDispatchTicket> {
    public WorkshopTaskDispatchTicketPresenter(IWorkshopTaskDispatchTicket view) {
        super(view);
    }

    public void getTicketClassList(String parentIDX) {
        RequestFactory.getInstance().createApi(WorkshopTaskDispatchApi.class).getTicketClassList(parentIDX).enqueue(new Callback<List<OrgForWorkshopTaskDispatch>>() {
            @Override
            public void onResponse(Call<List<OrgForWorkshopTaskDispatch>> call, Response<List<OrgForWorkshopTaskDispatch>> response) {
                List<OrgForWorkshopTaskDispatch> list = response.body();
                if (list != null) {
                    getViewRef().onTicketClassListLoadSuccess(list);
                } else {
                    getViewRef().onLoadFail("获取提票活处理班组记录失败，请重新访问");
                }
            }

            @Override
            public void onFailure(Call<List<OrgForWorkshopTaskDispatch>> call, Throwable t) {
                getViewRef().onLoadFail("获取提票活处理班组记录失败，请重新访问" + t.getMessage());
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

    public void getImages(String attachmentKeyIDX, String node){
        RequestFactory.getInstance().createApi(TicketApi.class).getImages(attachmentKeyIDX,node).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean bean = response.body();
                List<FileBaseBean> images = new ArrayList<>();
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
