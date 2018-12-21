package jx.yunda.com.terminal.modules.takeCarConfirm.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.ArrayList;
import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.base.baseDictData.api.BaseDictApi;
import jx.yunda.com.terminal.base.baseDictData.model.DicDataItem;
import jx.yunda.com.terminal.modules.confirmNotify.NotifyApi;
import jx.yunda.com.terminal.modules.confirmNotify.entity.NotifyListBean;
import jx.yunda.com.terminal.modules.confirmNotify.presenter.IGetNotify;
import jx.yunda.com.terminal.modules.takeCarConfirm.ITakeCarApi;
import jx.yunda.com.terminal.modules.takeCarConfirm.entry.AcceptPerson;
import jx.yunda.com.terminal.modules.takeCarConfirm.entry.TakeCarBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TakeCarConfirmPresenter extends BasePresenter<ITakeCarConfirm> {
    public TakeCarConfirmPresenter(ITakeCarConfirm view) {
        super(view);
    }
    public void getUnConfirmCarList(){
        RequestFactory.getInstance().createApi(ITakeCarApi.class).getTakeCarConfirmList()
                .enqueue(new Callback<BaseBean<List<TakeCarBean>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<TakeCarBean>>> call, Response<BaseBean<List<TakeCarBean>>> response) {
                        if(response!=null&&response.body()!=null){
                            Gson gson = new Gson();
                            if(response.body().getRoot()!=null&&response.body().getRoot().size()>0){
                                for(TakeCarBean takecar:response.body().getRoot()){
                                    if(takecar.getJsonAcceptPerson()!=null&&!"".equals(takecar.getJsonAcceptPerson())){
                                        try {
                                            List<AcceptPerson> person = gson.fromJson(takecar.getJsonAcceptPerson(), new TypeToken<List<AcceptPerson>>() {}.getType());
                                            if(person!=null&&person.size()>0){
                                                takecar.setPersonList(person);
                                            }
                                        }catch (Exception e){
                                            getViewRef().OnLoadFaild("无相关人员信息");
                                        }finally {
                                            getViewRef().OnLoadConfirmListSuccess(response.body().getRoot());
                                        }
                                    }
                                }

                            }else {
                                getViewRef().OnLoadConfirmListSuccess(null);
                            }

                        }else {
                            getViewRef().OnLoadFaild("获取牵车通知单数据失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<TakeCarBean>>> call, Throwable t) {
                        getViewRef().OnLoadFaild("获取牵车通知单数据失败，请重试"+t.getMessage());
                    }
                });
    }
    public void getPositions(){
        RequestFactory.getInstance().createApi(BaseDictApi.class).queryDictList("DESTINATION_DICT")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getProgressDialog().show();
                        getViewRef().OnLoadFaild("获取到达台位信息错误"+e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        BaseBean<List<DicDataItem>> bean = JSON.parseObject(s, new TypeReference<BaseBean<List<DicDataItem>>>() {
                        });
                        if(bean!=null&&bean.getRoot()!=null){
                            getViewRef().OnLoadStationsSuccess(bean.getRoot());
                        }else {
                            getProgressDialog().show();
                            getViewRef().OnLoadFaild("无相关台位信息");
                        }
                    }
                });

    }
    public void SubmitTakeCar(String idx,String entityJson){
        RequestFactory.getInstance().createApi(ITakeCarApi.class).SubmitTakeCar(idx,entityJson)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().isSuccess()){
                                getViewRef().ConfirmSuccess();
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().OnLoadFaild("确认失败"+response.body().getErrMsg());
                                }else {
                                    getViewRef().OnLoadFaild("确认失败,请重试");
                                }
                            }
                        }else {
                            getViewRef().OnLoadFaild("确认失败,请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().OnLoadFaild("确认失败,请重试"+t.getMessage());
                    }
                });
    }
}
