package jx.yunda.com.terminal.modules.schedule;

import java.util.List;

import jx.yunda.com.terminal.modules.schedule.entity.OrgBean;
import jx.yunda.com.terminal.modules.schedule.entity.ReceiveTrainBean;
import jx.yunda.com.terminal.modules.schedule.entity.ReceiveUserBean;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;
import jx.yunda.com.terminal.modules.schedule.entity.StationBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FwhBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ScheduleApi {
    //获取调度机车列表
    @FormUrlEncoded
    @POST("trainWorkPlanFlowSheet!queryTrainWorkPlan.action")
    Call<BaseBean<List<ScheduleTrainBean>>> getTrainList(@Field("start") int start,
                                                         @Field("limit") int limit,
                                                         @Field("entityJson") String entityJson);

    //获取调度机车列表
    @GET("trainWorkPlanFlowSheet!queryTrainWorkPlan.action")
    Call<BaseBean<List<ScheduleTrainBean>>> getTrainList();

    //获取台位信息
    @FormUrlEncoded
    @POST("jobProcessNodeNew!getCallNameList.action")
    Call<BaseBean<List<StationBean>>> getStationList(@Field("workPlanIdx") String workPlanIdx);

    //获取班组信息
    @FormUrlEncoded
    @POST("jobProcessNodeNew!getCallTeamList.action")
    Call<BaseBean<List<OrgBean>>> getTeamList(@Field("workPlanIdx") String workPlanIdx);

    //获取班组信息
    @GET("trainWorkPlanFlowSheet!getGroupListWithMasterNameInfo.action")
    Call<BaseBean<List<OrgBean>>> getTeamList();

    //获取班组信息
    @GET("deliverDispatch!getTrainDispatch.action")
    Call<BaseBean<List<ReceiveTrainBean>>> getReceiveTrains();

    //获取班组信息
    @GET("deliverDispatch!getDeliverEmp.action")
    Call<BaseBean<List<ReceiveUserBean>>> getReceiveUsers();

    @FormUrlEncoded
    @POST("deliverDispatch!setDeliverDispatch.action")
    Call<BaseBean> deliverDispatch(@Field("idx") String idx,
                                   @Field("deliverEmpId") String deliverEmpId,
                                   @Field("deliverEmpName") String deliverEmpName);

    @FormUrlEncoded
    @POST("jobProcessNodeNew!getGroupList.action")
    Call<BaseBean<List<OrgBean>>> getTeamListNew(@Field("DICT") String Dict);

    //点名
    @FormUrlEncoded
    @POST("jobProcessNodeNew!saveCallNameTableByPda.action")
    Call<BaseBean> Book(@Field("data") String data);

    //上台
    @FormUrlEncoded
    @POST("trainWorkPlanEditNew!generateWorkPlanByPda.action")
    Call<BaseBean> UpStation(@Field("data") String data);

    //点名
    @FormUrlEncoded
    @POST("trainWorkPlanFlowSheet!scheduCallName.action")
    Call<BaseBean> ScheduleBook(@Field("data") String data);

    //取消点名
    @FormUrlEncoded
    @POST("trainWorkPlanFlowSheet!backOfScheduCallName.action")
    Call<BaseBean> CancelScheduleBook(@Field("idx") String idx,
                                      @Field("handeOrgID") String handeOrgID,
                                      @Field("handeOrgName") String handeOrgName);

    //取消点名
    @FormUrlEncoded
    @POST("trainWorkPlanFlowSheet!getTrainNodes.action")
    Call<BaseBean<List<FwhBean>>> getScheduleFwh(@Field("trainWorkPlanIdx") String trainWorkPlanIdx,
                                                 @Field("isDispatche") String isDispatche);

    //提票活列表
    @FormUrlEncoded
    @POST("faultTicket!getTrainJT28ListByPda.action")
    Call<BaseBean<List<FaultTicket>>> getScheduleJT28List(@Field("workPlanIdx") String trainWorkPlanIdx,
                                                          @Field("isDispatche") String isDispatche);
}
