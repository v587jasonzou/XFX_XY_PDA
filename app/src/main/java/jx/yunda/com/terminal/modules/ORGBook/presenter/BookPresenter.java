package jx.yunda.com.terminal.modules.ORGBook.presenter;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.ORGBook.model.BookCalenderBean;
import jx.yunda.com.terminal.modules.ORGBook.model.BookLastUserBean;
import jx.yunda.com.terminal.modules.ORGBook.model.BookUserBean;
import jx.yunda.com.terminal.modules.api.ORGBookApi;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookPresenter extends BasePresenter<IBook> {
    public BookPresenter(IBook view) {
        super(view);
    }
    public void getBookUsers(String orgId,String emp){
        RequestFactory.getInstance().createApi(ORGBookApi.class).getBookUsers(orgId,emp)
                .enqueue(new Callback<BaseBean<List<BookUserBean>>>() {
                    @Override
                    public void onResponse(Call<BaseBean<List<BookUserBean>>> call, Response<BaseBean<List<BookUserBean>>> response) {
                        if(response!=null&&response.body()!=null){
                            getViewRef().getUsersSuccess(response.body().getRoot());
                        }else {
                            getViewRef().getUsersFaild("获取班组人员信息失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean<List<BookUserBean>>> call, Throwable t) {
                        getViewRef().getUsersFaild("获取班组人员信息失败，请重试"+t.getMessage());
                    }
                });
    }
    public void getBookCalender(String calData,String msg){
        RequestFactory.getInstance().createApi(ORGBookApi.class).getWorkCalendarDetail(calData,msg)
                .enqueue(new Callback<BookCalenderBean>() {
                    @Override
                    public void onResponse(Call<BookCalenderBean> call, Response<BookCalenderBean> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().isSuccess()){
                                getViewRef().getBookCalenderSuccess(response.body());
                            }else {
                                getViewRef().getBookCalenderFaild("获取日历失败，请重试");
                            }
                        }else {
                            getViewRef().getBookCalenderFaild("获取日历失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BookCalenderBean> call, Throwable t) {
                        getViewRef().getBookCalenderFaild("获取日历失败，请重试"+t.getMessage());
                    }
                });
    }
    //获取上次点名人员
    public void getLastMater(String start,String end){
        RequestFactory.getInstance().createApi(ORGBookApi.class).getLastMaster(start,end).enqueue(new Callback<BaseBean<List<BookLastUserBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<BookLastUserBean>>> call, Response<BaseBean<List<BookLastUserBean>>> response) {
                if(response!=null&&response.body()!=null){
                    getViewRef().getUserPlanSuccess(response.body().getRoot());
                }else {
                    getViewRef().getUserPlanFaild("获取上次点名计划失败");
                }
            }

            @Override
            public void onFailure(Call<BaseBean<List<BookLastUserBean>>> call, Throwable t) {
                getViewRef().getUserPlanFaild("获取上次点名计划失败"+t.getMessage());
            }
        });
    }
    public void BookUsers(String empids,String empNames,String starttime,String endtime){
        RequestFactory.getInstance().createApi(ORGBookApi.class).BookUser(empids,empNames,starttime,endtime)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().isSuccess()){
                                getViewRef().BookSuccess();
                            }else {
                                getViewRef().BookFaild("点名失败，请重试");
                            }
                        }else {
                            getViewRef().BookFaild("点名失败，请重试");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().BookFaild("点名失败，请重试"+t.getMessage());
                    }
                });
    }
    public void MasterDipatch(String data){
        RequestFactory.getInstance().createApi(ORGBookApi.class).MasterDipatch(data)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        if(response!=null&&response.body()!=null){
                            if(response.body().isSuccess()){
                                getViewRef().PostPlanSuccess();
                            }else {
                                if(response.body().getErrMsg()!=null){
                                    getViewRef().PostPlanFaild(response.body().getErrMsg());
                                }else {
                                    getViewRef().PostPlanFaild("派工失败,请重试");
                                }
                            }

                        }else {
                            getViewRef().PostPlanFaild("派工失败,请重试");
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        getViewRef().PostPlanFaild("派工失败,请重试"+t.getMessage());
                    }
                });
    }
}
