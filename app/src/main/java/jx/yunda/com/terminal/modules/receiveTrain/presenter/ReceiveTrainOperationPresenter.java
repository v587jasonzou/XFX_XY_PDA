package jx.yunda.com.terminal.modules.receiveTrain.presenter;

import com.alibaba.fastjson.JSON;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.ORGBook.model.BookCalenderBean;
import jx.yunda.com.terminal.modules.api.ORGBookApi;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.receiveTrain.ReceiveApi;
import jx.yunda.com.terminal.modules.receiveTrain.model.NodeEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.OrgEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.RepairEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.TrainEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.TrainNoEntity;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.tpprocess.model.ImageResponsBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiveTrainOperationPresenter extends BasePresenter<IReceiveTrainOperation> {
    public ReceiveTrainOperationPresenter(IReceiveTrainOperation view) {
        super(view);
    }
    public void getTrainType(){
        Map<String,Object> map = new HashMap<>();
        map.put("isCx","yes");
        RequestFactory.getInstance().createApi(ReceiveApi.class).getTrainTypeList("com.yunda.jx.base.jcgy.entity.TrainType",
                "trainType", JSON.toJSONString(map))
                .enqueue(new Callback<BaseBean<List<TrainEntity>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<TrainEntity>>> call, Response<BaseBean<List<TrainEntity>>> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().getRoot()!=null){
                                getViewRef().getTrainNameSuccess(response.body().getRoot());
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().getDataFaild("获取车型信息失败"+response.body().getErrMsg(),"车型");
                                }else {
                                    getViewRef().getDataFaild("获取车型信息失败,请重试","车型");
                                }
                            }
                        }else {
                            getViewRef().getDataFaild("获取车型信息失败,请重试","车型");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<TrainEntity>>> call, Throwable t) {
                        getViewRef().getDataFaild("获取车型信息失败,请重试"+t.getMessage(),"车型");
                    }
                });
    }

    public void getTrainNo(String trainType){
        Map<String,Object> map = new HashMap<>();
        map.put("isCx","yes");
        map.put("isIn","false");
        map.put("isRemoveRun","true");
        map.put("trainTypeIDX",trainType);
        RequestFactory.getInstance().createApi(ReceiveApi.class).getTrainNoList("com.yunda.jx.jczl.attachmanage.entity.jczlTrain",
                "trainNoSelect", JSON.toJSONString(map),"yes",0,1000)
                .enqueue(new Callback<BaseBean<List<TrainNoEntity>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<TrainNoEntity>>> call, Response<BaseBean<List<TrainNoEntity>>> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().getRoot()!=null){
                                getViewRef().getTrainNoSuccess(response.body().getRoot());
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().getDataFaild("获取车号信息失败"+response.body().getErrMsg(),"车号");
                                }else {
                                    getViewRef().getDataFaild("获取车号信息失败,请重试","车号");
                                }
                            }
                        }else {
                            getViewRef().getDataFaild("获取车号信息失败,请重试","车号");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<TrainNoEntity>>> call, Throwable t) {
                        getViewRef().getDataFaild("获取车号信息失败,请重试"+t.getMessage(),"车号 ");
                    }
                });
    }

    public void getRepair(String trainType){
        Map<String,Object> map = new HashMap<>();
        map.put("TrainTypeIdx",trainType);
        RequestFactory.getInstance().createApi(ReceiveApi.class).getTrainRepairName(
                trainType)
                .enqueue(new Callback<BaseBean<List<RepairEntity>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<RepairEntity>>> call, Response<BaseBean<List<RepairEntity>>> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().getRoot()!=null){
                                getViewRef().getRepairSuccess(response.body().getRoot());
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().getDataFaild("获取修程信息失败"+response.body().getErrMsg(),"修程");
                                }else {
                                    getViewRef().getDataFaild("获取修程信息失败,请重试","修程");
                                }
                            }
                        }else {
                            getViewRef().getDataFaild("获取修程信息失败,请重试","修程");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<RepairEntity>>> call, Throwable t) {
                        getViewRef().getDataFaild("获取修程信息失败,请重试"+t.getMessage(),"修程 ");
                    }
                });
    }

    public void getNode(String where,String rcIDx){
        RequestFactory.getInstance().createApi(ReceiveApi.class).getTrainNode(0,1000,where,rcIDx)
                .enqueue(new Callback<BaseBean<List<NodeEntity>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<NodeEntity>>> call, Response<BaseBean<List<NodeEntity>>> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().getRoot()!=null){
                                getViewRef().getNodeSuccess(response.body().getRoot());
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().getDataFaild("获取流程信息失败"+response.body().getErrMsg(),"流程");
                                }else {
                                    getViewRef().getDataFaild("获取流程信息失败,请重试","流程");
                                }
                            }
                        }else {
                            getViewRef().getDataFaild("获取流程信息失败,请重试","流程");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<NodeEntity>>> call, Throwable t) {
                        getViewRef().getDataFaild("获取流程信息失败,请重试"+t.getMessage(),"流程 ");
                    }
                });
    }

    public void getBookCalender(){
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        String data = sf2.format(new Date());
        RequestFactory.getInstance().createApi(ORGBookApi.class).getWorkCalendarDetail(data,"ff8080815b096999015b096e5a070002")
                .enqueue(new Callback<BookCalenderBean>() {
                    @Override
                    public void onResponse(Call<BookCalenderBean> call, Response<BookCalenderBean> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().isSuccess()){
                                getViewRef().getCalenderSuccess(response.body());
                            }else {
                                getViewRef().getDataFaild("获取日历信息失败","日历");
                            }
                        }else {
                            getViewRef().getDataFaild("获取日历信息失败","日历");
                        }
                    }

                    @Override
                    public void onFailure(Call<BookCalenderBean> call, Throwable t) {
                        getViewRef().getDataFaild("获取日历信息失败"+t.getMessage(),"日历");
                    }
                });
    }

    public void getOrgs(final int position, String idx){
        RequestFactory.getInstance().createApi(ReceiveApi.class).getOrgs(idx)
                .enqueue(new Callback<List<OrgEntity>>() {
                    @Override
                    public void onResponse(Call<List<OrgEntity>> call, Response<List<OrgEntity>> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().size()>0){
                                getViewRef().getOrgsSuccess(response.body(),position);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<OrgEntity>> call, Throwable t) {

                    }
                });
    }
    public void Confirm(String data){
        RequestFactory.getInstance().createApi(ReceiveApi.class).Confirm(data)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().isSuccess()){
                                getViewRef().ConfirmSuccess();
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().getDataFaild("提交失败"+response.body().getErrMsg(),"提交");
                                }else {
                                    getViewRef().getDataFaild("提交失败,请重试","提交");
                                }
                            }
                        }else {
                            getViewRef().getDataFaild("提交失败,请重试","提交");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().getDataFaild("提交失败,请重试"+t.getMessage(),"提交");
                    }
                });
    }
    public int position1,imageNo1;
    public void UpLoadImages(String Base64,String extname,int position, int imageNo){
        this.position1 = position;
        this.imageNo1 = imageNo;
        RequestFactory.getInstance().createApi(TicketApi.class).upLoadImageNew(Base64,extname).enqueue(new Callback<ImageResponsBean>() {
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

}
