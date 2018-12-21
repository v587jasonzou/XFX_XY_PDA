package jx.yunda.com.terminal.modules.fixflow;

import java.util.List;

import jx.yunda.com.terminal.modules.fixflow.entry.FlowNoBean;
import jx.yunda.com.terminal.modules.fixflow.entry.FlowTrainBean;
import jx.yunda.com.terminal.modules.fixflow.entry.FwhInfoBean;
import jx.yunda.com.terminal.modules.fixflow.entry.TicketInfoBean;
import jx.yunda.com.terminal.modules.receiveTrain.model.NodeEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.OrgEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.ReceivedTrain;
import jx.yunda.com.terminal.modules.receiveTrain.model.RepairEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.TrainEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.TrainNoEntity;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FlowInfoApi {

    //获取接车机车列表
    @FormUrlEncoded
    @POST("trainWorkType!getTrainWorkType.action")
    Call<BaseBean<List<FlowTrainBean>>> getTrainList(@Field("trainNameOrNo") String entityJson);

    //获取范围活列表
    @FormUrlEncoded
    @POST("trainWorkType!getTrainFwDetail.action")
    Call<BaseBean<List<FwhInfoBean>>> getFwhList(@Field("workPlanIdx") String workPlanIdx,
                                                      @Field("orgId") String orgId,
                                                      @Field("type") String type,
                                                      @Field("start") int limit,
                                                      @Field("limit") int start);

    //获取提票活列表
    @FormUrlEncoded
    @POST("trainWorkType!getTrainTpDetail.action")
    Call<BaseBean<List<TicketInfoBean>>> getTpList(@Field("workPlanIdx") String workPlanIdx,
                                                   @Field("orgId") String orgId,
                                                   @Field("type") String type,
                                                   @Field("start") int limit,
                                                   @Field("limit") int start);
    //获取范围活列表
    @FormUrlEncoded
    @POST("trainWorkType!getTrainWorkNo.action")
    Call<BaseBean<FlowNoBean>> getWorkNo(@Field("workPlanIdx") String workPlanIdx,
                                         @Field("orgId") String orgId);
}
