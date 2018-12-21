package jx.yunda.com.terminal.modules.takeCarConfirm;

import java.util.List;

import jx.yunda.com.terminal.modules.confirmNotify.entity.NotifyDetailBean;
import jx.yunda.com.terminal.modules.takeCarConfirm.entry.TakeCarBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ITakeCarApi {
    @GET("pullTrainNotice!getAllNoConfirm.action")
    Call<BaseBean<List<TakeCarBean>>> getTakeCarConfirmList();

    @FormUrlEncoded
    @POST("pullTrainNotice!saveOrUpdateNotice.action")
    Call<BaseBean> SubmitTakeCar(@Field("groupId") String groupId,
                                 @Field("pullTrainNotice") String pullTrainNotice);
}
