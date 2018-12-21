package jx.yunda.com.terminal.modules.FJ_ticket.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.ArrayList;
import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.entity.FileBaseBean;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RechecjInfoPresenter extends BasePresenter<IRecheckInfo> {
    public RechecjInfoPresenter(IRecheckInfo view) {
        super(view);
    }
    public void getImages(String attachmentKeyIDX){
        RequestFactory.getInstance().createApi(TicketApi.class).getImages(attachmentKeyIDX,"nodeRecheckAtt").enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean bean = response.body();
                List<FileBaseBean> images = new ArrayList<>();
                if(bean.getFileUrlList()!=null){
                    images.addAll(bean.getFileUrlList());
                }
                getViewRef().GetImagesSuccess(images);
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().GetImagesFaild("获取图片失败："+t.getMessage());
            }
        });

    }
}
