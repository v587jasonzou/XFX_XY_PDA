package jx.yunda.com.terminal.modules.api;

import java.util.List;

import jx.yunda.com.terminal.modules.foremandispatch.model.EmpForForemanDispatch;
import jx.yunda.com.terminal.modules.foremandispatch.model.FwFormenBean;
import jx.yunda.com.terminal.modules.foremandispatch.model.TrainForForemanDispatch;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.EmpForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FwhBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ForemanDispatchApi {

    //复检机车列表
    @FormUrlEncoded
    @POST("trainWorkPlanQuery!queryTrainListGzpg.action")
    Call<BaseBean<List<TrainForForemanDispatch>>> getForemanDispatchTrainList(
              @Field("operatorId") String operatorId,
              @Field("query") String query);

    // 范围活列表 - 未派工
    @FormUrlEncoded
    @POST("nodeWorkerManager!getUnDispatchNode.action")
    Call<BaseBean<List<FwFormenBean>>> getUnDispatchFwhList(
            @Field("start") String start,
            @Field("limit") String limit,
            @Field("workPlanIdx") String workPlanIdx);

    // 范围活列表 - 已派工
    @FormUrlEncoded
    @POST("nodeWorkerManager!getDoneDispatchNode.action")
    Call<BaseBean<List<FwFormenBean>>> getDispatedchFwhList(
            @Field("start") String start,
            @Field("limit") String limit,
            @Field("workPlanIdx") String workPlanIdx);

    // 提票活列表
    @FormUrlEncoded
    @POST("faultTicket!queryGzpgList.action")
    Call<BaseBean<List<FaultTicket>>> getTicketList(
            @Field("start") String start,
            @Field("limit") String limit,
            @Field("operatorid") String operatorId,
            @Field("isDispatch") String isDispatch,
            @Field("entityJson") String entityJson);

    // 作业人员列表
    @FormUrlEncoded
    @POST("omEmployeeSelect!pageList.action")
    Call<BaseBean<List<EmpForForemanDispatch>>> getEmpList(
            @Field("orgid") String orgid,
            @Field("emp") String emp);

    // 范围活派工
    @FormUrlEncoded
    @POST("nodeWorkerManager!dispatchNode.action")
    Call<BaseBean> dispatchFwh(
            @Field("nodeIdxs") String nodeIdxs,
            @Field("empids") String empids,
            @Field("empnames") String empnames);

    // 提票活派工
    @FormUrlEncoded
    @POST("faultTicket!updateForGzpgMobile.action")
    Call<BaseBean> dispatchTicket(@Field("ids") String ids, @Field("empids") String empids);
}
