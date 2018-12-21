package jx.yunda.com.terminal.modules.api;

import java.util.List;

import jx.yunda.com.terminal.modules.ORGBook.model.BookCalenderBean;
import jx.yunda.com.terminal.modules.ORGBook.model.BookLastUserBean;
import jx.yunda.com.terminal.modules.ORGBook.model.BookUserBean;
import jx.yunda.com.terminal.modules.ORGBookNew.model.BookNodeResponse;
import jx.yunda.com.terminal.modules.ORGBookNew.model.BookUserBeanNew;
import jx.yunda.com.terminal.modules.ORGBookNew.model.Nodebean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ORGBookApi {
    //获取班组人员信息
    @FormUrlEncoded
    @POST("omEmployeeSelect!pageList.action")
    Call<BaseBean<List<BookUserBean>>> getBookUsers(@Field("orgid") String orgid,
                                                    @Field("emp") String emp);

    @GET("foreman!getPersonAndWorkNum.action")
    Call<BaseBean<List<BookUserBeanNew>>> getBookUserNew();

    @FormUrlEncoded
    @POST("nodeWorkerManager!getDisNodeForPDAByEmpId.action")
    Call<BaseBean<BookNodeResponse>> getNodesNew(@Field("empId") String empId);

    //已指派任务
    @FormUrlEncoded
    @POST("nodeWorkerManager!getDoneDisNodeByEmpId.action")
    Call<BaseBean<List<Nodebean>>> getBookNodes(@Field("empId") String empId);

    //未指派任务
    @FormUrlEncoded
    @POST("nodeWorkerManager!getUnDispatchNodeNotSelf.action")
    Call<BaseBean<List<Nodebean>>> getUnBookNodes(@Field("empId") String empId);

    @FormUrlEncoded
    @POST("nodeWorkerManager!getOtherTeamUnDispatch.action")
    Call<BaseBean<List<Nodebean>>> getOtherUnBookNodes(@Field("queryParam") String queryParam);
    //获取工作日历
    @FormUrlEncoded
    @POST("workCalendarDetail!getWorkCalendarDetail.action")
    Call<BookCalenderBean> getWorkCalendarDetail(@Field("calDate") String calDate,
                                                 @Field("infoIdx") String infoIdx);

    //获取当前班组的上一次点名的人员任务对应列表
    @FormUrlEncoded
    @POST("foreman!getCurrentMasterNameList.action")
    Call<BaseBean<List<BookLastUserBean>>> getLastMaster(@Field("startTime") String startTime,
                                                         @Field("endTime") String endTime);

    //点名
    @FormUrlEncoded
    @POST("foreman!masterName.action")
    Call<BaseBean> BookUser(@Field("empids") String empids,
                            @Field("empnames") String empnames,
                            @Field("startTime") String startTime,
                            @Field("endTime") String endTime);

    //点名派工
    @FormUrlEncoded
    @POST("foreman!masterDispatchTaskByPda.action")
    Call<BaseBean> MasterDipatch(@Field("data") String data);

    //取消指派任务
    @FormUrlEncoded
    @POST("nodeWorkerManager!backNodeForPerson.action")
    Call<BaseBean> CanclePlans(@Field("empId") String empId,
                               @Field("empName") String empName,
                               @Field("nodeIdx") String nodeIdx);

    //指派任务
    @FormUrlEncoded
    @POST("nodeWorkerManager!setNodeForPerson.action")
    Call<BaseBean> AddPlans(@Field("empId") String empId,
                            @Field("empName") String empName,
                            @Field("nodeIdx") String nodeIdx);
}
