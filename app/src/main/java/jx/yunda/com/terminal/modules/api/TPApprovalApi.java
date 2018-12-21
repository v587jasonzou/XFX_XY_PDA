package jx.yunda.com.terminal.modules.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface TPApprovalApi {
    /* 获取待审批提票列表 */
    @GET("discharge!getFaultTicketDischargeList.action")
    Observable<String> getFaultTicketDischargeList(@QueryMap Map<String, Object> params);
    /* 提交审批票活 */
    @GET("faultTicket!approvalByPda.action")
    Observable<String> approvalByPda(@QueryMap Map<String, Object> params);
    /* 提交审批票活 */
    @GET("faultTicket!findTPByTrain.action")
    Call<String> getFaultTicket(@QueryMap Map<String, Object> params);
}
