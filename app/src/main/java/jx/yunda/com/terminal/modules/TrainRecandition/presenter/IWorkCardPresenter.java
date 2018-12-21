package jx.yunda.com.terminal.modules.TrainRecandition.presenter;

import android.text.TextUtils;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.TrainRecandition.model.BaseWorkCardBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.WorkCardBean;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.tpprocess.model.ImageResponsBean;
import jx.yunda.com.terminal.utils.ToastUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IWorkCardPresenter extends BasePresenter<IworkCard> {
    public IWorkCardPresenter(IworkCard view) {
        super(view);
    }

    public void CompletePlan(String nodeIdx,int seqNo,String WorkCardIdx){
        RequestFactory.getInstance().createApi(TicketApi.class).CompleteTrainRecandition(nodeIdx,seqNo,WorkCardIdx)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                           getViewRef().LoadDataSuccess(response.body().getWorkCardBeanList());
                        }else {
                            getViewRef().LoadDataFaild("获取检修记录卡数据失败，请重试");
                        }


                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().LoadDataFaild("获取检修记录卡数据失败"+t.getMessage());
                    }
                });
    }

    public void getWorkCardSort(String nodeIdx){
        RequestFactory.getInstance().createApi(TicketApi.class).getWorkCardSort(nodeIdx)
                .enqueue(new Callback<BaseWorkCardBean<WorkCardBean>>() {
                    @Override
                    public void onResponse(Call<BaseWorkCardBean<WorkCardBean>> call, Response<BaseWorkCardBean<WorkCardBean>> response) {
                        if(response!=null&&response.body()!=null){
                            getViewRef().getCardsSortSuccess(response.body().getWorkCardBeanList());
                        }else {
                            getViewRef().getCardsSortFaild("获取检修记录卡顺序失败，请重试");
                        }


                    }

                    @Override
                    public void onFailure(Call<BaseWorkCardBean<WorkCardBean>> call, Throwable t) {
                        getViewRef().getCardsSortFaild("获取检修记录卡顺序失败"+t.getMessage());
                    }
                });
    }
    public void SubmitWorkCard(long operatedid,String idx){
        RequestFactory.getInstance().createApi(TicketApi.class).SubmitWorkCard(operatedid,idx,"")
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                            if (response.body().isSuccess()){
                                getViewRef().SubmitSuccess();
                            }else {
                                getViewRef().SubmitFaild("提交失败，请重试");
                            }
                        }else {
                            getViewRef().SubmitFaild("提交失败，请重试");
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().SubmitFaild("提交失败，请重试"+t.getMessage());
                    }
                });
    }
    public void PassWorkCard(String idx){
        RequestFactory.getInstance().createApi(TicketApi.class).PassWorkCard(idx)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                            if (response.body().isSuccess()){
                                ToastUtil.toastShort("放弃成功");
                                getViewRef().SubmitSuccess();
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().SubmitFaild(response.body().getErrMsg());
                                }else {
                                    getViewRef().SubmitFaild("放弃失败，请重试");
                                }

                            }
                        }else {
                            getViewRef().SubmitFaild("放弃失败，请重试");
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().SubmitFaild("放弃失败，请重试"+t.getMessage());
                    }
                });
    }

    public void SubmitWorkCard(long operatedid,String idx,String photos){
        RequestFactory.getInstance().createApi(TicketApi.class).SubmitWorkCard(operatedid,idx,photos)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                            if (response.body().isSuccess()){
                                getViewRef().SubmitSuccess();
                            }else {
                                getViewRef().SubmitFaild("提交失败，请重试");
                            }
                        }else {
                            getViewRef().SubmitFaild("提交失败，请重试");
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().SubmitFaild("提交失败，请重试"+t.getMessage());
                    }
                });
    }

    public int position1,imageNo1;
    public void UpLoadImages(String Base64,String extname,int position, int imageNo){
        this.position1 = position;
        this.imageNo1 = imageNo;
        RequestFactory.getInstance().createApi(TicketApi.class).upLoadWOrkCardImage(Base64,extname).enqueue(new Callback<ImageResponsBean>() {
            @Override
            public void onResponse(Call<ImageResponsBean> call, Response<ImageResponsBean> response) {
                ImageResponsBean bean = response.body();
                if(bean!=null&&bean.isSuccess()){
                    getViewRef().OnUpLoadImagesSuccess(bean.getFilePath(),position1,imageNo1);
                }else {
                    getViewRef().OnLoadFail("上传图片失败",position1,imageNo1);
                }
            }

            @Override
            public void onFailure(Call<ImageResponsBean> call, Throwable t) {
                getViewRef().OnLoadFail("上传图片失败"+t.getMessage(),position1,imageNo1);
            }
        });
    }

    public void UpLoadNodeImages(String Base64,String extname,int position, int imageNo){
        this.position1 = position;
        this.imageNo1 = imageNo;
        RequestFactory.getInstance().createApi(TicketApi.class).upLoadNodeImage(Base64,extname).enqueue(new Callback<ImageResponsBean>() {
            @Override
            public void onResponse(Call<ImageResponsBean> call, Response<ImageResponsBean> response) {
                ImageResponsBean bean = response.body();
                if(bean!=null&&bean.isSuccess()){
                    getViewRef().OnUpLoadImagesSuccess(bean.getFilePath(),position1,imageNo1);
                }else {
                    getViewRef().OnLoadFail("上传图片失败",position1,imageNo1);
                }
            }

            @Override
            public void onFailure(Call<ImageResponsBean> call, Throwable t) {
                getViewRef().OnLoadFail("上传图片失败"+t.getMessage(),position1,imageNo1);
            }
        });
    }
    public void SubmitNode(String idx,String photos){
        RequestFactory.getInstance().createApi(TicketApi.class).SubmitNode(idx,photos)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                            if (response.body().isSuccess()){
                                getViewRef().SubmitSuccess();
                            }else {
                                getViewRef().SubmitFaild("提交失败，请重试");
                            }
                        }else {
                            getViewRef().SubmitFaild("提交失败，请重试");
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().SubmitFaild("提交失败，请重试"+t.getMessage());
                    }
                });
    }
}
