package jx.yunda.com.terminal.modules.api;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface MessageApi {
    /* 获取所有可添加人员 */
    @GET("omEmployeeSelect!getEmployeeByEmp.action")
    Observable<String> getAllPerson();
    @GET("messageGroup!addMsgGroupUser.action")
    Observable<String> savePerson(@QueryMap Map<String, Object> params);
}
