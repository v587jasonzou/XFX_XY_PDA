package jx.yunda.com.terminal.modules.api;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface TransNoticeApi {
    /**查询通知单
    *start	Integer	否	查询起始页
    *limit	Integer	否	每页条数
    *timeStart	String	否	查询通知单的起始时间
    *timeEnd	String	否	查询通知单的截止时间
    *type	Integer	是	默认为-1，表示查询所有数据，1表示查询已提交的数据
    * */
    @GET("notice!getNotice.action")
    Observable<String> getNotice(@QueryMap Map<String, Object> params);

    /**查询通知单
     *noticeIDX	String	是	通知单的id
     * */
    @GET("notice!deleteNotice.action")
    Observable<String> deleteNotice(@QueryMap Map<String, Object> params);
    /*查询通知单
     *start	Integer	否	查询起始页
     *limit	Integer	否	每页条数
     *noticeIDX	String	是	通知单的id
     * */
    @GET("transTrainNotice!getDetailsTrainDetails.action")
    Observable<String> getTransNoticeTrains(@QueryMap Map<String, Object> params);

    /**提交通知单
     *noticeIDX	String	是	通知单的id
     * */
    @GET("notice!submitNotice.action")
    Observable<String> submitNotice(@QueryMap Map<String, Object> params);

    /**新建通知单是获取默认名称
     *noticeIDX	String	是	通知单的id
     * */
    @GET("notice!getNoticeName.action")
    Observable<String> getNoticeName();

    /**新建通知单是获取默认名称
     * start	Integer	否	查询起始页
     * limit	Integer	否	每页条数
     * trainNo	String	否	模糊查询车号
     * */
    @GET("transTrainNotice!getSelectTrain.action")
    Observable<String> getSelectTrain(@QueryMap Map<String, Object> params);


    /**新建通知单是获取默认名称
     * transNotice	TransNotice	通知单信息
     * transNoticeTrains	List<TransNoticeTrain>	通知单内调车列表
     * */
    @GET("notice!applyNoticeForPDA.action")
    Observable<String> saveTransNotice(@QueryMap Map<String, Object> params);

    /**提交通知单
     *noticeDetailsIDX	String	是	通知单的id
     * */
    @GET("transTrainNotice!deleteNoticeDetails.action")
    Observable<String> deleteNoticeTrainDetails(@QueryMap Map<String, Object> params);

    /**获取可通知人列表
     *noticeDetailsIDX	String	是	通知单的id
     * */
    @GET("notice!getNoticeAcceptPerson.action")
    Observable<String> getNoticeAcceptPerson(@QueryMap Map<String, Object> params);
}
