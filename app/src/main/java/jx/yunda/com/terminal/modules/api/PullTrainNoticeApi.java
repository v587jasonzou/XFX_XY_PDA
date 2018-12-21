package jx.yunda.com.terminal.modules.api;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface PullTrainNoticeApi {
    /**点击添加页面点击车型车号  查询在修机车的牵车通知集合
     *trainNo通过车号模糊查询
     * @param params
     * @return
     */
    @GET("pullTrainNotice!getPullTrainNotice.action")
    Observable<String> getPullTrainNotice(@QueryMap Map<String, Object> params);

    /**选择车型车号后查询没有确认的牵车命令或者查询车型车号对应的牵车历史
     * trainPlanIdx	String		是	计划主键
     * type    	    String		是	0查询未确认的，1查询全部的
     * @param params
     * @return
     */
    @GET("pullTrainNotice!getPullTrainDetails.action")
    Observable<String> getPullTrainDetails(@QueryMap Map<String, Object> params);

    /** 新增或者修改牵车通知单(确认通知单亦实用)
     * pullTrainNotice	JsonString		是	Json格式的牵车通知单实体对象
     * groupId	String		是	29.2中选择的数据带有
     * @param params
     * @return
     */
    @GET("pullTrainNotice!saveOrUpdateNotice.action")
    Observable<String> saveOrUpdateNotice(@QueryMap Map<String, Object> params);

    /** 删除牵车通知单
     * idx	JsonString		是	需要删除的牵车通知单idx
     * @param params
     * @return
     */
    @GET("pullTrainNotice!deletePullNotice.action")
    Observable<String> deletePullNotice(@QueryMap Map<String, Object> params);

    /** 查询所有的未确认的牵车通知
     * @return
     */
    @GET("pullTrainNotice!getAllNoConfirm.action")
    Observable<String> getAllNoConfirm();
}
