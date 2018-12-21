package jx.yunda.com.terminal.modules.FJ_ticket.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.entity.FileBaseBean;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.tpprocess.ITicketInfo;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultBean;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultTreeBean;
import jx.yunda.com.terminal.modules.tpprocess.model.ImageResponsBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTypeBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitCheckPresenter extends BasePresenter<ISubmitRecheck> {
    public SubmitCheckPresenter(ISubmitRecheck view) {
        super(view);
    }
    public static int TYPE_MAJOR = 1;//专业
    public static int TYPE_EMPLOYEE = 2;//考核
    //获取部件位置信息
    public void getFalutInfo(final int position ,String str){
        RequestFactory.getInstance().createApi(TicketApi.class).getFaultInfos(str).enqueue(new Callback<List<FaultTreeBean>>() {
            @Override
            public void onResponse(Call<List<FaultTreeBean>> call, Response<List<FaultTreeBean>> response) {
                Log.e("name",response.body().get(0).getName());
                List<FaultTreeBean> faultBeans = response.body();
                if(faultBeans!=null&&faultBeans.size()>0){
                    getViewRef().OnLoacationLoadsuccess(position,response.body());
                }else {
                    getViewRef().OnLoacationLoadFaild("未获取到部件位置信息");
                }

            }

            @Override
            public void onFailure(Call<List<FaultTreeBean>> call, Throwable t) {
                getViewRef().OnLoacationLoadFaild("获取部件信息失败:"+t.getMessage());
            }
        });
    }
    //获取类别
    public void getLable(final int type){
        req(RequestFactory.getInstance().createApi(TicketApi.class).getTicketType("CATEGORY_2"), new HttpOnNextListener() {
            @Override
            public void onNext(String result, String mothead) {
                Log.e("json",result);
                BaseBean<List<TicketTypeBean>> bean = JSON.parseObject(result,new TypeReference<BaseBean<List<TicketTypeBean>>>(){});
                if(bean!=null){
                    if(bean.isSuccess()){
                        if(bean.getRoot()!=null&&bean.getRoot().size()>0){
                            getViewRef().OnTypeLoadSuccess(bean.getRoot());
                        }else {
                            getViewRef().OnTypeLoadFaild("无数据请重试");
                        }
                    }else {
                        getViewRef().OnTypeLoadFaild("获取类别数据失败，请重试");
                    }
                }else {
                    getViewRef().OnTypeLoadFaild("连接服务器失败，请重试");
                }

            }

            @Override
            public void onError(ApiException e) {
                Log.e("错误",e.getMessage());
                getViewRef().OnTypeLoadFaild("连接服务器失败，请重试"+e.getMessage());
//                getViewRef().onLoadTicktFail(e.getMessage());
            }
        });
    }
    public void getFaultList(String entityJson){
        RequestFactory.getInstance().createApi(TicketApi.class).getFaultList(entityJson).enqueue(new Callback<List<FaultBean>>() {
            @Override
            public void onResponse(Call<List<FaultBean>> call, Response<List<FaultBean>> response) {
                if(response.body()!=null&&response.body().size()>0){
                    getViewRef().OnLoadListSuccess(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<FaultBean>> call, Throwable t) {
                getViewRef().OnLoadListFaild("获取故障现象联想失败"+t.getMessage());
            }
        });

    }
    public int position1,imageNo1;
    public void UpLoadImages(String Base64,String extname,int position, int imageNo){
        this.position1 = position;
        this.imageNo1 = imageNo;
        RequestFactory.getInstance().createApi(TicketApi.class).upLoadImage(Base64,extname).enqueue(new Callback<ImageResponsBean>() {
            @Override
            public void onResponse(Call<ImageResponsBean> call, Response<ImageResponsBean> response) {
                ImageResponsBean bean = response.body();
                if(bean!=null&&bean.isSuccess()){
                    getViewRef().OnUpLoadImagesSuccess(bean.getFilePath(),position1,imageNo1);
                }else {
                    getViewRef().OnUpLoadImagesFaild("上传图片失败",position1,imageNo1);
                }
            }

            @Override
            public void onFailure(Call<ImageResponsBean> call, Throwable t) {
                getViewRef().OnUpLoadImagesFaild("上传图片失败"+t.getMessage(),position1,imageNo1);
            }
        });
    }

    public void SaveTicket(String data,String data1,String status,String idx,String ticketTrainIDX,String empid,String faultPhotos){
        RequestFactory.getInstance().createApi(TicketApi.class).SavaRecheck(data,data1,status,idx,ticketTrainIDX,empid,faultPhotos).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean bean = response.body();
                if(bean!=null){
                    if(bean.getMsg().equals("添加成功")){
                        getViewRef().OnSubmitSuccess();
                    }else {
                        getViewRef().OnSubmitFaild(bean.getErrMsg());
                    }
                }else {
                    getViewRef().OnSubmitFaild("提交失败");
                }

            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().OnSubmitFaild("提交失败"+t.getMessage());
            }
        });
    }

    public void getImages(String attachmentKeyIDX){
        RequestFactory.getInstance().createApi(TicketApi.class).getImages(attachmentKeyIDX,"nodeRecheckAtt").enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean bean = response.body();
                List<FileBaseBean> images = new ArrayList<>();
                if(bean.getFileUrlList()!=null){
                    images.addAll(bean.getFileUrlList());
                }
                getViewRef().OnGetImagesSuccess(images);
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().OnGetImagesFaild("获取故障现象联想失败"+t.getMessage());
            }
        });

    }
}
