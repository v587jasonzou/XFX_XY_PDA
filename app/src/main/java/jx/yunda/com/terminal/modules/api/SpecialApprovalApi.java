package jx.yunda.com.terminal.modules.api;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface SpecialApprovalApi {
    /* 获取例外放行列表 {trainNo:""} */
    @GET("discharge!getDischargeListByPda.action")
    Observable<String> getDischargeListByPda(@QueryMap Map<String, Object> params);

    /* 例外放行审批票活 {whereSnakerJson:"objcet"} objcet如下
     *idx ：提票主键
     *processInstId ：流程主键
     *workId ： 流程workId
     *workName ： 流程workName
     *taskKey : 流程taskKey
     *opinions ：备注
     *opinions ：审批结果 （“01”：同意  “02”：拒绝）
     * */
    @GET("discharge!approvalDischarge.action")
    Observable<String> approvalDischarge(@QueryMap Map<String, Object> params);
}
