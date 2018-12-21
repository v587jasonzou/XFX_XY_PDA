package jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.api.ForemanDispatchApi;
import jx.yunda.com.terminal.modules.api.WorkshopTaskDispatchApi;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.TrainForWorkshopTaskDispatch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkshopTaskDispatchPresenter extends BasePresenter<IWorkshopTaskDispatch> {
    public WorkshopTaskDispatchPresenter(IWorkshopTaskDispatch view) {
        super(view);
    }
    public void getDispatchTrains(String trainNo, String isSendNode, String entityJson){
        RequestFactory.getInstance().createApi(WorkshopTaskDispatchApi.class).getWorkshopTaskDispatchTrainList(trainNo, isSendNode, entityJson).enqueue(new Callback<BaseBean<List<TrainForWorkshopTaskDispatch>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<TrainForWorkshopTaskDispatch>>> call, Response<BaseBean<List<TrainForWorkshopTaskDispatch>>> response) {
                BaseBean<List<TrainForWorkshopTaskDispatch>> bean = response.body();
                if(bean!=null&&bean.getRoot()!=null){
                    if(bean.getRoot().size()>0){
                        getViewRef().LoadTrainListSuccess(bean.getRoot());
                    }else {
                        getViewRef().LoadTrainListSuccess(null);
                    }
                }else {
                    getViewRef().OnLoadFaild("连接服务器失败，请重试");
                }

            }

            @Override
            public void onFailure(Call<BaseBean<List<TrainForWorkshopTaskDispatch>>> call, Throwable t) {
                getViewRef().OnLoadFaild(t.getMessage());
            }
        });
    }

}
