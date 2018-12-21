package jx.yunda.com.terminal.modules.receiveTrain.presenter;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;


import java.util.ArrayList;
import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.receiveTrain.ReceiveApi;
import jx.yunda.com.terminal.modules.receiveTrain.model.ReceiveTrainNotice;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiveTrainNoticePresenter extends BasePresenter<IReceiveTrainNotice> {
    public ReceiveTrainNoticePresenter(IReceiveTrainNotice view) {
        super(view);
    }
    public void getReceiveTrainList(String entityJson){
        RequestFactory.getInstance().createApi(ReceiveApi.class).getTrainReceiveList(entityJson)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response!=null&&response.body()!=null){
                            String result = response.body();
                            BaseBean<ArrayList<ReceiveTrainNotice>> bean = JSONObject.parseObject(result, new TypeReference<BaseBean<ArrayList<ReceiveTrainNotice>>>() {});
                            if(bean.getRoot()!=null){
                                getViewRef().OnLoadTrainListSuccess(bean.getRoot());
                            }else {
                                if(bean.getErrMsg()!=null){
                                    getViewRef().OnLoadTrainListFaild("获取机车列表失败"+bean.getErrMsg());
                                }else {
                                    getViewRef().OnLoadTrainListFaild("获取机车列表失败,请重试");
                                }
                            }
                        }else {
                            getViewRef().OnLoadTrainListFaild("获取机车列表失败,请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        getViewRef().OnLoadTrainListFaild("获取机车列表失败,请重试"+t.getMessage());
                    }
                });
    }
}
