package jx.yunda.com.terminal.modules.api;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;


public interface LoginApi {
    /* 登陆 */
    @GET("login!terminalLogin.action")
    Observable<String> login(@QueryMap Map<String, Object> params);

}
