package jx.yunda.com.terminal.modules.api;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface HomeApi {
    /* 获取权限菜单 */
    @GET("roleProc!queryPDARoleFuncList.action")
    Observable<String> getPurviewMenu();
    /* 获取权限菜单 */
    @FormUrlEncoded
    @POST("roleProc!queryPDARoleFuncListNew.action")
    Observable<String> getPurviewMenu(@Field("clientFlag") String clientFlag);
    /* 查询待办数量 */
    @GET("todoJob!getTodoJobList.action")
    Observable<String> getTodoJobCount();
}
