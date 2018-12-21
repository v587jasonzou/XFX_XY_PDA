package jx.yunda.com.terminal.modules.TrainRecandition.presenter;

import com.lzy.imagepicker.bean.ImageItem;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.ArrayList;
import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.entity.FileBaseBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.BaseWorkCardBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.InspectorOrderBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.ItemResultBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.ItemResultRespons;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.tpprocess.model.ImageResponsBean;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkOrderPresenter extends BasePresenter<IWorkOrder> {
    public WorkOrderPresenter(IWorkOrder view) {
        super(view);
    }
    public void getWorkOrders(String idx){
        RequestFactory.getInstance().createApi(TicketApi.class).getWorkorders(idx).enqueue(new Callback<BaseWorkCardBean<InspectorOrderBean>>() {
            @Override
            public void onResponse(Call<BaseWorkCardBean<InspectorOrderBean>> call, Response<BaseWorkCardBean<InspectorOrderBean>> response) {
                if(response!=null&&response.body()!=null){
                    getViewRef().LoaddataSuccess(response.body().getWorkTaskBeanList());
                }else {
                    getViewRef().LoaddataFaild("获取数据项失败，请重试");
                }
            }

            @Override
            public void onFailure(Call<BaseWorkCardBean<InspectorOrderBean>> call, Throwable t) {
                getViewRef().LoaddataFaild("获取数据项失败，请重试"+t.getMessage());
            }
        });
    }

    public void getItems(String idx){
        RequestFactory.getInstance().createApi(TicketApi.class).getItems(idx).enqueue(new Callback<ItemResultRespons>() {
            @Override
            public void onResponse(Call<ItemResultRespons> call, Response<ItemResultRespons> response) {
                if(response!=null&&response.body()!=null){
                    if(response.body().getList()!=null){
                        List<String> list = new ArrayList<>();
                        for(ItemResultBean bean:response.body().getList()){
                            list.add(bean.getResultName());
                        }
                        if (list.size()>0){
                            getViewRef().getItemsSuccess(list);
                        }
                    }else {
                        getViewRef().getItemsFaild("无更多数据");
                    }

//                    getViewRef().LoaddataSuccess(response.body().getWorkTaskBeanList());
                }else {
                    getViewRef().getItemsFaild("无更多数据");
                }
            }

            @Override
            public void onFailure(Call<ItemResultRespons> call, Throwable t) {
                getViewRef().getItemsFaild("无更多数据"+t.getMessage());
            }
        });
    }
    public void SubmitEduit(Long opratedId,String jsonData){
        RequestFactory.getInstance().createApi(TicketApi.class).SubmitEditItem(opratedId,jsonData)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().isSuccess()){
                                getViewRef().SubmitEditSuccess();
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().SubmitEditFaild(response.body().getErrMsg());
                                }else {
                                    getViewRef().SubmitEditFaild("提交失败");
                                }

                            }
                        }else {
                            getViewRef().SubmitEditFaild("提交失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().SubmitEditFaild("提交失败");
                    }
                });
    }
    public void SubmitEduitNew(Long opratedId,String jsonData,String faultPhotos,final String type){
        RequestFactory.getInstance().createApi(TicketApi.class).SubmitEditItemNew(opratedId,jsonData,faultPhotos)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().isSuccess()){
                                getViewRef().submitOrderSuccess(type);
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().submitOrderFaild(response.body().getErrMsg(),type);
                                }else {
                                    getViewRef().submitOrderFaild("提交失败",type);
                                }

                            }
                        }else {
                            getViewRef().submitOrderFaild("提交失败",type);
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().submitOrderFaild("提交失败"+t.getMessage(),type);
                    }
                });
    }
    public void UpLoadImage(ImageItem image,String idx){
        String str64 = Utils.getBase64StringFromImg(image.path);
        RequestFactory.getInstance().createApi(TicketApi.class).UpLoadImagesNew(str64,".jpg","workTaskImgAtt",idx)
                .enqueue(new Callback<ImageResponsBean>() {
                    @Override
                    public void onResponse(Call<ImageResponsBean> call, Response<ImageResponsBean> response) {
                        if(response!=null&&response.body()!=null){
                            getViewRef().upLoadImagesSuccess(response.body().getFilePath(),response.body().getFileIdx());
                        }else {
                            getViewRef().upLoadImagesFaild("上传照片失败");
                        }

                    }

                    @Override
                    public void onFailure(Call<ImageResponsBean> call, Throwable t) {
                        getViewRef().upLoadImagesFaild("上传照片失败"+t.getMessage());
                    }
                });
    }
    public void getImages(String attachmentKeyIDX, final String node){
        RequestFactory.getInstance().createApi(TicketApi.class).getImages(attachmentKeyIDX,node,"workTaskImgAtt").enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseBean bean = response.body();
                List<FileBaseBean> images = new ArrayList<>();
                if(bean.getFileUrlList()!=null){
                    images.addAll(bean.getFileUrlList());
                    getViewRef().OnGetImagesSuccess(images);
                }else {
                    getViewRef().OnGetImagesFaild("获取照片失败");
                }

            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                getViewRef().OnGetImagesFaild("获取照片失败"+t.getMessage());
            }
        });

    }
}
