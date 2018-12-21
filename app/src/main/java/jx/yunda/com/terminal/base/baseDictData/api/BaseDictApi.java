package jx.yunda.com.terminal.base.baseDictData.api;

import java.util.List;
import java.util.Map;

import jx.yunda.com.terminal.modules.tpprocess.model.DictBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface BaseDictApi {
    //标签信息
    @GET("eosDictEntrySelect!queryDictList.action")
    Observable<String> queryDictList(@Query("dictTypeID") String dictTypeID);

    /*标签信息
     * status	String
     * dicttypeid	String	key
     * queryWhere  String
     * hasEmpty  boolean
     * */
    @GET("eosDictEntrySelect!combolist.action")
    Observable<String> queryDictCombolist(@QueryMap Map<String, Object> params);

    //标签信息
    @FormUrlEncoded
    @POST("eosDictEntrySelect!queryChildTree.action")
    Observable<String> getDictForBQ(@Field("parentIDX") String parentIDX,
                                 @Field("queryParams") String queryParams);
}
