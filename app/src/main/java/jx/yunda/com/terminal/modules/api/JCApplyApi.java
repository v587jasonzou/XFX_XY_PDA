package jx.yunda.com.terminal.modules.api;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface JCApplyApi {
    /* 获取所有可交机车 */
    @GET("deliverTrain!findDeliverTrainList.action")
    Observable<String> findDeliverTrainList();
    /* 获取所有审批中的机车 */
    @GET("deliverTrain!findApprovalTrainList.action")
    Observable<String> findApprovalTrainList();

    /*六种提票类型数量统计接口 入口参数：params:{'workPlanIdx':workPlanIdx}*/
    @GET("deliverTrain!findSixTicketClassCounts.action")
    Observable<String> findSixTicketClassCounts(@QueryMap Map<String, Object> params);

    /*每种提票类型的提票列表接口 入口参数：params{‘workPlanIdx’:workPlanIdx,’type’:type} type传递每种类型的汉字即可*/
    @GET("deliverTrain!findEveryTicketList.action")
    Observable<String> findEveryTicketList(@QueryMap Map<String, Object> params);

    /*票活退回 入口参数：params{‘workPlanidx’:workPlanidx,’ ticketIdx’:ticketIdx’,'type':typeName}*/
    @GET("deliverTrain!rollBackTicket.action")
    Observable<String> rollBackTicket(@QueryMap Map<String, Object> params);

    /*交车申请接口 入口参数：params{‘workPlanidx’:workPlanidx}*/
    @GET("deliverTrain!applyDeliverTrain.action")
    Observable<String> applyDeliverTrain(@QueryMap Map<String, Object> params);

    /*PDA 端流程查看 入口参数：{‘params’:params} Params结构如下：{‘processInstID’: processInstID }*/
    @GET("deliverTrain!findApprovalTrainListPda.action")
    Observable<String> findApprovalTrainListPda(@QueryMap Map<String, Object> params);
}
