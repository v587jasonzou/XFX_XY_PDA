package jx.yunda.com.terminal.modules.api;

import java.util.List;

import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.EmpForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FwhBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.OrgForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.TrainForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.WorkstationForWorkshopTaskDispatch;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WorkshopTaskDispatchApi {

    // 机车列表
    @FormUrlEncoded
    @POST("trainWorkPlan!pageQueryData.action")
    Call<BaseBean<List<TrainForWorkshopTaskDispatch>>> getWorkshopTaskDispatchTrainList(
            @Field("trainNo") String trainNo,
            @Field("isSendNode") String isSendNode,
            @Field("entityJson") String entityJson);

    // 范围活列表
    @FormUrlEncoded
    @POST("jobProcessNodeNew!findDoneSendNodes.action")
    Call<BaseBean<List<FwhBean>>> getFwhList(
            @Field("start") String start,
            @Field("limit") String limit,
            @Field("isSendNode") String isSendNode,
            @Field("workPlanIdx") String workPlanIdx);

    // 提票活列表
    @FormUrlEncoded
    @POST("faultTicket!queryDdpgList.action")
    Call<BaseBean<List<FaultTicket>>> getTicketList(
            @Field("start") String start,
            @Field("limit") String limit,
            @Field("isDispatch") String isDispatch,
            @Field("entityJson") String entityJson);

    // 作业班组列表
    @FormUrlEncoded
    @POST("omOrganizationSelect!customOrg.action")
    Call<List<OrgForWorkshopTaskDispatch>> getClassList(@Field("processNodeIdx") String processNodeIdx);

    // 作业人员列表
    @FormUrlEncoded
    @POST("baseCombo!getBaseComboTree.action")
    Call<List<EmpForWorkshopTaskDispatch>> getEmpList(
            @Field("queryParams") String queryParams,
            @Field("manager") String manager);

    // 台位列表
    @FormUrlEncoded
    @POST("baseCombo!pageList.action")
    Call<BaseBean<List<WorkstationForWorkshopTaskDispatch>>> getWorkstationList(
            @Field("queryParams") String queryParams,
            @Field("manager") String manager);

    // 提票活处理班组列表
    @FormUrlEncoded
    @POST("omOrganizationSelect!findOrg.action")
    Call<List<OrgForWorkshopTaskDispatch>> getTicketClassList(@Field("parentIDX") String parentIDX);

    // 范围活派工
    @FormUrlEncoded
    @POST("jobProcessNodeNew!batchSendNodeMobil.action")
    Call<BaseBean> dispatchFwh(@Field("nodes") String nodes);

    // 提票活派工
    @FormUrlEncoded
    @POST("faultTicket!updateForDdpgMobile.action")
    Call<BaseBean> dispatchTicket(@Field("ids") String ids, @Field("entityJson") String entityJson);
}
