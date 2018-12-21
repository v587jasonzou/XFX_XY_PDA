package jx.yunda.com.terminal.modules.confirmNotify;

import java.util.List;

import jx.yunda.com.terminal.modules.confirmNotify.entity.NotifyDetailBean;
import jx.yunda.com.terminal.modules.confirmNotify.entity.NotifyListBean;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NotifyApi {
    //获取调车通知单列表
    @FormUrlEncoded
    @POST("notice!getNoticeConfirm.action")
    Call<BaseBean<List<NotifyListBean>>> getNotifyList(@Field("start") int start,
                                                       @Field("limit") int limit,
                                                       @Field("timeStart") String timeStart,
                                                       @Field("timeEnd") String timeEnd);

    //获取调车通知单详情
    @FormUrlEncoded
    @POST("transTrainNotice!getDetailsTrainDetails.action")
    Call<BaseBean<List<NotifyDetailBean>>> getNotifyDetailList(@Field("start") int start,
                                                               @Field("limit") int limit,
                                                               @Field("noticeIDX") String noticeIDX);
    //更新位置信息
    @FormUrlEncoded
    @POST("transTrainNotice!savePosition.action")
    Call<BaseBean> ChangeLocation(@Field("noticeDetail") String noticeDetail);

    //确认通知单详情
    @FormUrlEncoded
    @POST("transTrainNotice!transTrainNoticeConfirm.action")
    Call<BaseBean> ConfirmNotify(@Field("noticeDetailsIDX") String noticeDetailsIDX,
                                 @Field("confirmPersonId") long confirmPersonId,
                                 @Field("confirmPersonName") String confirmPersonName);

}
