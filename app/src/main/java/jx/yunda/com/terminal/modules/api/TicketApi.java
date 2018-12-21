package jx.yunda.com.terminal.modules.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jx.yunda.com.terminal.modules.FJ_ticket.model.RecheckBean;
import jx.yunda.com.terminal.modules.FJ_ticket.model.RecheckTrainBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.BaseWorkCardBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.DownEquipBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.EquipBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.InspectorOrderBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.ItemResultRespons;
import jx.yunda.com.terminal.modules.TrainRecandition.model.JXTrainBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.ModulesBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.RecanditionBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.WorkCardBean;
import jx.yunda.com.terminal.modules.partsrecandition.model.ChildEquipBean;
import jx.yunda.com.terminal.modules.partsrecandition.model.ChildUpEquipBean;
import jx.yunda.com.terminal.modules.partsrecandition.model.PartsPlanBean;
import jx.yunda.com.terminal.modules.quality.model.QualityEquipPlanBean;
import jx.yunda.com.terminal.modules.quality.model.QualityTIcketPlanBean;
import jx.yunda.com.terminal.modules.quality.model.TrainBoby;
import jx.yunda.com.terminal.modules.quality.model.TrainQualityBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.tpprocess.model.DictBean;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultBean;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultTreeBean;
import jx.yunda.com.terminal.modules.tpprocess.model.ImageResponsBean;
import jx.yunda.com.terminal.modules.tpprocess.model.StandardBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketInfoBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface TicketApi {
    //在修机车列表
    @GET("faultTiketMobile!queryZXJCList.action")
    Observable<String> getCarList(@Query("entityJson") String entityJson);

    //提票机车类型
    @GET("sysEosDictEntry!tree")
    Observable<String> getLables(@Query("dicttypeid") String dicttypeid,
                                 @Query("dictid") String dictid,
                                 @Query("isAll") boolean isAll);

    //标签信息
    @GET("eosDictEntrySelect!queryDictList.action")
    Observable<String> getTicketType(@Query("dictTypeID") String dictTypeID);

    //标签信息
    @FormUrlEncoded
    @POST("eosDictEntrySelect!queryChildTree.action")
    Call<List<DictBean>> getDict(@Field("parentIDX") String parentIDX,
                                 @Field("queryParams") String queryParams);

    //获取部件位置
    @GET("jcgxBuildQuery!getJcgxBuildTree.action")
    Call<List<FaultTreeBean>> getFaultInfos(@Query("entityJson") String entityJson);

    //获取故障现象信息
    @GET("jcgxBuildQuery!getFlbmFault.action")
    Call<List<FaultBean>> getFaultList(@Query("entityJson") String entityJson);

    //获取过程提票列表
    @GET("faultTicket!getFaultTicketDetailByType.action")
    Call<BaseBean<List<TicketInfoBean>>> getTicketList(@Query("entityJson") String entityJson,
                                                       @Query("start") String start,
                                                       @Query("limit") String limit);

    //图片上传
    @FormUrlEncoded
    @POST("faultTiketMobile!uploadFaultImg.action")
    Call<ImageResponsBean> upLoadImage(@Field("photoBase64Str") String photoBase64Str,
                                       @Field("extname") String extname);

    //图片上传
    @FormUrlEncoded
    @POST("trainWorkPlanEditNew!uploadTrainPhoto.action")
    Call<ImageResponsBean> upLoadImageNew(@Field("photoBase64Str") String photoBase64Str,
                                          @Field("extname") String extname);

    //图片上传
    @FormUrlEncoded
    @POST("workCard!uploadFWJCPhoto.action")
    Call<ImageResponsBean> upLoadWOrkCardImage(@Field("photoBase64Str") String photoBase64Str,
                                               @Field("extname") String extname);

    //图片上传
    @FormUrlEncoded
    @POST("workCard!uploadJDPhoto.action")
    Call<ImageResponsBean> upLoadNodeImage(@Field("photoBase64Str") String photoBase64Str,
                                           @Field("extname") String extname);

    //过程提票提交
    @FormUrlEncoded
    @POST("faultTiketMobile!saveFaultTicket.action")
    Call<BaseBean> saveTicket(@Field("operatorid") long operatorid,
                              @Field("jsonData") String jsonData,
                              @Field("faultPhotos") String faultPhotos);


    //下载图片
    @FormUrlEncoded
    @POST("faultTiketMobile!downloadFaultImg.action")
    Call<BaseBean> getImages(@Field("attachmentKeyIDX") String attachmentKeyIDX,
                             @Field("node") String node);

    //获取检修标准
    @FormUrlEncoded
    @POST("faultTicket!getTpSkillStandard.action")
    Call<BaseBean<List<StandardBean>>> getStandard(@Field("idx") String idx);
    //下载图片-公用
    @FormUrlEncoded
    @POST("mobile!downloadImg.action")
    Call<BaseBean> getImages(@Field("attachmentKeyIDX") String attachmentKeyIDX,
                             @Field("node") String node,
                             @Field("upLoadPathType") String upLoadPathType);

    //删除图片-公用
    @FormUrlEncoded
    @POST("mobile!deleteAttachmentList.action")
    Call<BaseBean> DeleteImages(@Field("attachmentKeyIDX") String attachmentKeyIDX,
                                @Field("fileIdx") String fileIdx);

    //提票处理接口
    @FormUrlEncoded
    @POST("faultTiketMobile!updateTicketHandleMobile.action")
    Call<BaseBean> SubmitTicketManage(@Field("operatorid") long operatorid,
                                      @Field("jsonData") String jsonData,
                                      @Field("faultPhotos") String faultPhotos,
                                      @Field("skillList") String skillList);

    //提票处理接口
    @FormUrlEncoded
    @POST("faultTiketMobile!sendApprovalMobile.action")
    Call<BaseBean> SubmitTicketManageOther(@Field("operatorid") long operatorid,
                                           @Field("jsonData") String jsonData,
                                           @Field("faultPhotos") String faultPhotos);

    //复检机车列表
    @FormUrlEncoded
    @POST("trainRecheckInfo!queryRecheckTrain.action")
    Call<BaseBean<List<RecheckTrainBean>>> getRecheckTrainList(@Field("entityJson") String entityJson
            , @Field("start") String start
            , @Field("limit") String limit
            , @Field("status") String status
            , @Field("recheckType") String recheckType
            , @Field("type") String type
            , @Field("empid") String empid);

    //复检提票保存
    @FormUrlEncoded
    @POST("faultTiketMobile!saveTrainWarning.action")
    Call<BaseBean> SavaRecheck(@Field("data") String data
            , @Field("data1") String data1
            , @Field("status") String status
            , @Field("idx") String idx
            , @Field("ticketTrainIDX") String ticketTrainIDX
            , @Field("operatorid") String empid
            , @Field("faultPhotos") String faultPhotos);

    @FormUrlEncoded
    @POST("faultTicket!levelUp.action")
    Call<BaseBean> LevelUpTicket(@Field("idx") String idx
            , @Field("target") String target
            , @Field("orginal") String orginal);

    //复检提票提交
    @FormUrlEncoded
    @POST("trainRecheckInfo!submitRecheckInfo.action")
    Call<BaseBean> SubmitRecheck(@Field("entityJson") String entityJson
            , @Field("recheckType") String recheckType
            , @Field("empid") String empid);

    //复检提票信息列表
    @FormUrlEncoded
    @POST("trainRecheckInfo!queryRecheckInfo.action")
    Call<BaseBean<List<RecheckBean>>> getRecheckList(@Field("whereListJson") String whereListJson);

    //复检提票信息删除
    @FormUrlEncoded
    @POST("trainEarlyWarning!delTrainEarlyWarningNew.action")
    Call<BaseBean> DeleteRecheck(@Field("ids") String ids);

    //机车检修在修机车列表
    @FormUrlEncoded
    @POST("trainWorkRdp!queryZXJCListJCJX.action")
    Call<BaseBean<List<JXTrainBean>>> getJXTrainList(@Field("entityJson") String entityJson,
                                                     @Field("start") int start,
                                                     @Field("limit") int limit,
                                                     @Field("invokDataType") String invokDataType);

    //获取机车检修任务
    @FormUrlEncoded
    @POST("trainWorkRdp!queryNodeListByTeam.action")
    Call<BaseBean<List<RecanditionBean>>> getTrainWorkPlan(@Field("entityJson") String entityJson
            , @Field("start") String start
            , @Field("limit") String limit
            , @Field("isAll") String isAll
            , @Field("empid") String empid
            , @Field("invokDataType") String inVokDataType);

    //机车检修_开工
    @FormUrlEncoded
    @POST("jobProcessNodeNew!startNode.action")
    Call<BaseBean> StartPlan(@Field("id") String id);

    //机车检修_完工
    @FormUrlEncoded
    @POST("jobProcessNodeNew!finishNode.action")
    Call<BaseBean> CompletePlan(@Field("idx") String id,
                                @Field("partsOnCount") String partsOnCount,
                                @Field("partsOffCount") String partsOffCount,
                                @Field("operatorId") String operatorId);

    //机车检修_获取上车配件任务
    @FormUrlEncoded
    @POST("partsFixRegister!findPartsAboardRegisterAll.action")
    Call<BaseBean<ArrayList<EquipBean>>> getUpEquips(@Field("workPlanId") String workPlanId,
                                                     @Field("nodeCaseIDX") String nodeCaseIDX,
                                                     @Field("status") String partsOffCount);

    //机车检修_获取下车配件任务
    @FormUrlEncoded
    @POST("partsUnloadRegister!findPartsUnloadRegisterAll.action")
    Call<BaseBean<ArrayList<DownEquipBean>>> getDownEquips(@Field("workPlanId") String workPlanId,
                                                           @Field("nodeCaseIDX") String nodeCaseIDX,
                                                           @Field("status") String partsOffCount);

    //获取配件规格型号
    @FormUrlEncoded
    @POST("partsType!findPartsTypeList.action")
    Call<BaseBean<List<ModulesBean>>> getEquipType(@Field("entityJson") String entityJson);

    //机车检修_下车登记
    @FormUrlEncoded
    @POST("partsUnloadRegister!savePartsUnloadRegister.action")
    Call<BaseBean> DownEquip(@Field("registerData") String entityJson,
                             @Field("registerArray") String registerArray);

    //机车检修_下车登记_撤销
    @FormUrlEncoded
    @POST("partsUnloadRegister!updateUnloadRegisterForCancel.action")
    Call<BaseBean> DownEquipBack(@Field("id") String id);

    //机车检修_上车登记_撤销
    @FormUrlEncoded
    @POST("partsFixRegister!updateFixRegisterForCancel.action")
    Call<BaseBean> UpEquipBack(@Field("id") String id);

    //机车检修_上车登记
    @FormUrlEncoded
    @POST("partsFixRegister!savePartsFixRegister.action")
    Call<BaseBean> UpEquip(@Field("registerData") String registerData,
                           @Field("isScan") String isScan);

    //配件检修_检修任务列表
    @FormUrlEncoded
    @POST("partsRdpNodeQuery!queryNodeListByHandler.action")
    Call<BaseBean<List<PartsPlanBean>>> getPartsPlans(@Field("entityJson") String entityJson,
                                                      @Field("start") String start,
                                                      @Field("limit") String limit);

    //机车检修_上车配件登记
    @FormUrlEncoded
    @POST("partsFixRegister!savePartsFixRegisterNew.action")
    Call<BaseBean> UpEquipNew(@Field("registerData") String registerData);

    //配件检修_检修任务列表
    @FormUrlEncoded
    @POST("partsRdpNode!finishPartsNode.action")
    Call<BaseBean> CompletePart(@Field("idx") String idx,
                                @Field("partsNo") String partsNo,
                                @Field("specificationModel") String lspecificationModelimit,
                                @Field("partsOffCount") String partsOffCount,
                                @Field("partsOnCount") String partsOnCount);

    //配件检修_下配件列表
    @FormUrlEncoded
    @POST("partsUnloadRegisterForParts!findUnloadRegisterForParts.action")
    Call<BaseBean<ArrayList<ChildEquipBean>>> getDownParts(@Field("rdpIDX") String rdpIDX,
                                                           @Field("nodeCaseIDX") String nodeCaseIDX,
                                                           @Field("status") String status);

    //配件检修_上配件列表
    @FormUrlEncoded
    @POST("partsFixRegisterForParts!findFixRegisterForParts.action")
    Call<BaseBean<ArrayList<ChildUpEquipBean>>> getUpParts(@Field("rdpIDX") String rdpIDX,
                                                           @Field("nodeCaseIDX") String nodeCaseIDX,
                                                           @Field("status") String status);

    //配件检修_下配件登记
    @FormUrlEncoded
    @POST("partsUnloadRegisterForParts!saveUnloadRegisterForParts.action")
    Call<BaseBean> DownParts(@Field("regData") String registerData,
                             @Field("regArray") String regArray);

    //配件检修_上车配件登记
    @FormUrlEncoded
    @POST("partsFixRegisterForParts!savePartsFixRegister.action")
    Call<BaseBean> UpPartsNew(@Field("registerArray") String registerData);

    //配件检修_上车配件登记
    @FormUrlEncoded
    @POST("partsFixRegisterForParts!saveFixRegisterForParts.action")
    Call<BaseBean> UpParts(@Field("registerData") String registerData);

    //质量检查_在修机车列表
    @FormUrlEncoded
    @POST("qCResultQuery!getWorkPlanQcList.action")
    Call<TrainBoby> getQualityTrain(@Field("operatorId") String operatorId,
                                    @Field("query") String query);

    //质量检查_机车任务列表
    @FormUrlEncoded
    @POST("qCResultQuery!getNodeQcList.action")
    Call<BaseBean<List<TrainQualityBean>>> getQualityTrainPlan(@Field("start") String start,
                                                               @Field("limit") String limit,
                                                               @Field("checkWay") String checkWay,
                                                               @Field("workPlanIdx") String workPlanIdx,
                                                               @Field("operatorId") String operatorId,
                                                               @Field("qcstatus") String qcstatus);

    //质量检查_机车质量检查_返修
    @FormUrlEncoded
    @POST("qCResult!bacthUpdateToBack.action")
    Call<BaseBean> QualityTrainBack(@Field("entityJson") String entityJson,
                                    @Field("backReason") String backReason);

    //质量检查_机车质量检查_通过
    @FormUrlEncoded
    @POST("qCResult!bacthUpdateQCstatus.action")
    Call<BaseBean> QualityTrainPass(@Field("checkWay") String checkWay,
                                    @Field("qrResult") String qrResult,
                                    @Field("entityJson") String entityJson);

    //质量检查_机车任务列表
    @FormUrlEncoded
    @POST("partsRdpQR!findQcItemQR.action")
    Call<BaseBean<List<QualityEquipPlanBean>>> getQualityEquipPlan(@Field("start") String start,
                                                                   @Field("limit") String limit,
                                                                   @Field("checkWay") String checkWay,
                                                                   @Field("entityJson") String entityJson,
                                                                   @Field("operatorId") String operatorId,
                                                                   @Field("qcstatus") String qcstatus);

    @FormUrlEncoded
    @POST("partsRdpQR!findNodeList.action")
    Call<BaseBean> getNodes(@Field("rdpIDX") String rdpIDX);

    //质量检查_机车质量检查_返修
    @FormUrlEncoded
    @POST("partsRdpQR!updateToBackNew.action")
    Call<BaseBean> QualityEquipBack(@Field("nodeIdxs") String entityJson,
                                    @Field("qcItemNo") String backReason,
                                    @Field("backContent") String backContent);

    //质量检查_配件质量检查_通过
    @FormUrlEncoded
    @POST("partsRdpQR!bacthUpdateQCstatus.action")
    Call<BaseBean> QualityEquipPass(@Field("entityJson") String entityJson);

    //质量检查_提票任务列表
    @FormUrlEncoded
    @POST("faultQCResultQuery!getQCPageList.action")
    Call<BaseBean<List<QualityTIcketPlanBean>>> getTicketTrains(@Field("query") String query,
                                                                @Field("start") String limit,
                                                                @Field("limit") String checkWay,
                                                                @Field("mode") String entityJson,
                                                                @Field("qcstatus") String qcstatus);

    //质量检查_提票质量检查_通过
    @FormUrlEncoded
    @POST("faultQCResult!updateFinishQCResultPDA.action")
    Call<BaseBean> QualityTicketPass(@Field("entityJson") String entityJson);

    //获取配件编号
    @FormUrlEncoded
    @POST("partsAccount!getPartsNoByPartsType.action")
    Call<List<Map<String, String>>> getEquipCodes(@Field("specificationModel") String specificationModel,
                                                  @Field("partsStatus") String partsStatus);

    //机车检修完工
    @FormUrlEncoded
    @POST("workCard!findOneCardTaskByNodeIdx.action")
    Call<BaseBean> CompleteTrainRecandition(@Field("nodeIdx") String nodeIdx,
                                            @Field("seqNo") int seqNo,
                                            @Field("workCardIdx") String workCardIdx);

    //机车检修完工
    @FormUrlEncoded
    @POST("workCard!findWorkCardInfoByNodeIdx.action")
    Call<BaseWorkCardBean<WorkCardBean>> getWorkCardSort(@Field("nodeIdx") String nodeIdx);

    //获取数据项
    @FormUrlEncoded
    @POST("workTask!getWorkTaskByIdx.action")
    Call<BaseWorkCardBean<InspectorOrderBean>> getWorkorders(@Field("idx") String Idx);

    //机车检修_获取下拉选项
    @FormUrlEncoded
    @POST("detectItemResult!getValueList.action")
    Call<ItemResultRespons> getItems(@Field("detectItemCode") String detectItemCode);

    //机车检修_获取下拉选项
    @FormUrlEncoded
    @POST("detectResult!saveDetectResult.action")
    Call<BaseBean> SubmitEditItem(@Field("operatorid") long detectItemCode,
                                  @Field("jsonData") String jsonData);

    //机车检修_获取下拉选项
    @FormUrlEncoded
    @POST("workTask!saveWorkTask.action")
    Call<BaseBean> SubmitEditItemNew(@Field("operatorid") long operatorid,
                                     @Field("jsonData") String jsonData,
                                     @Field("faultPhotos") String faultPhotos);

    //机车检修_作业卡提交
    @FormUrlEncoded
    @POST("workCard!finishWorkCardOnly.action")
    Call<BaseBean> SubmitWorkCard(@Field("operatorid") long operatorid,
                                  @Field("idx") String idx,
                                  @Field("fwhclPhoto") String fwhclPhoto);
    //机车检修_作业卡放弃
    @FormUrlEncoded
    @POST("workCard!giveUpWorkCard.action")
    Call<BaseBean> PassWorkCard(@Field("workCardIdx") String workCardIdx);

    //机车检修_作业卡提交
    @FormUrlEncoded
    @POST("workCard!finishNodeByHande.action")
    Call<BaseBean> SubmitNode(@Field("idx") String idx,
                              @Field("nodePhoto") String nodePhoto);

    //机车检修_上传照片
    @FormUrlEncoded
    @POST("mobile!uploadFaultImg.action")
    Call<ImageResponsBean> UpLoadImagesNew(@Field("photoBase64Str") String photoBase64Str,
                                           @Field("extname") String extname,
                                           @Field("upLoadPathType") String upLoadPathType,
                                           @Field("attachmentKeyIDX") String attachmentKeyIDX);
    //获取提票选择业务字典
//    @FormUrlEncoded
//    @POST("")
//    Call<BaseBean> getEosDiction(@Field(""));
}
