package jx.yunda.com.terminal.modules.receiveTrain;

import java.util.List;

import jx.yunda.com.terminal.modules.receiveTrain.model.NodeEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.OrgEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.ReceiveTrainNotice;
import jx.yunda.com.terminal.modules.receiveTrain.model.ReceivedTrain;
import jx.yunda.com.terminal.modules.receiveTrain.model.RepairEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.TrainEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.TrainNoEntity;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ReceiveApi {

    //获取接车机车列表
    @FormUrlEncoded
    @POST("trainWorkPlanFlowSheet!queryTrainWorkPlan.action")
    Call<BaseBean<List<ReceivedTrain>>> getTrainList(@Field("start") int start,
                                                     @Field("limit") int limit,
                                                     @Field("entityJson") String entityJson);

    //获取接车机车列表
    @FormUrlEncoded
    @POST("trainEnforcePlanDetail!getAllIssuePlan.action")
    Call<String> getTrainReceiveList(@Field("trainNo") String trainNo);

    //获取车型列表
    @FormUrlEncoded
    @POST("baseCombo!pageList.action")
    Call<BaseBean<List<TrainEntity>>> getTrainTypeList(@Field("entity") String entity,
                                                       @Field("manager") String manager,
                                                       @Field("queryParams") String queryParams);

    //获取车型列表
    @FormUrlEncoded
    @POST("baseCombo!pageList.action")
    Call<BaseBean<List<TrainNoEntity>>> getTrainNoList(@Field("entity") String entity,
                                                       @Field("manager") String manager,
                                                       @Field("queryParams") String queryParams,
                                                       @Field("isAll") String isAll,
                                                       @Field("start") int start,
                                                       @Field("limit") int limit);

    //获取修程
    @FormUrlEncoded
    @POST("baseCombo!pageList.action")
    Call<BaseBean<List<RepairEntity>>> getTrainRepairName(@Field("manager") String manager,
                                                          @Field("queryParams") String queryParams);
    //获取修程
    @FormUrlEncoded
    @POST("undertakeRcCode!selectRcCode.action")
    Call<BaseBean<List<RepairEntity>>> getTrainRepairName(@Field("TrainTypeIdx") String TrainTypeIdx);
    //作业流程
    @FormUrlEncoded
    @POST("jobProcessDef!getModelsByTrainTypeIDXAndRcIDX.action")
    Call<BaseBean<List<NodeEntity>>> getTrainNode(@Field("start") int start,
                                                  @Field("limit") int limit,
                                                  @Field("trainTypeIDX") String trainTypeIDX,
                                                  @Field("rcIDX") String rcIDX);
    //获取配属断
    @FormUrlEncoded
    @POST("omOrganizationSelect!deportTree2.action")
    Call<List<OrgEntity>> getOrgs(@Field("parentIDX") String parentIDX);

    //提交
    @FormUrlEncoded
    @POST("trainWorkPlanEditNew!generateWorkPlanByPda.action")
    Call<BaseBean> Confirm(@Field("data") String data);
}
