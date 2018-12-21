package jx.yunda.com.terminal.modules.api;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface JCApprovalApi {
        /*票活退回 入口参数：params{'idx':idx,'processInstId':processInstId,'workId':workId,'workName':workName,'taskKey':taskKey,'opinions':opinions,'opinionsResult':opinionsResult}*/
    @GET("deliverTrain!deliverTrainApporval.action")
    Observable<String> deliverTrainApporval(@QueryMap Map<String, Object> params);
}
