package jx.yunda.com.terminal.modules.tpprocess;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.List;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTrainBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTypeBean;

public class MakeTicketPresenter extends BasePresenter<IMakeTicket> {
    public MakeTicketPresenter(IMakeTicket view) {
        super(view);
    }
    public void getTicketTrains(){
        req(RequestFactory.getInstance().createApi(TicketApi.class).getCarList(""), new HttpOnNextListener() {
            @Override
            public void onNext(String result, String mothead) {
//                Log.e("json",result);
                BaseBean<List<TicketTrainBean>> bean = JSON.parseObject(result,new TypeReference<BaseBean<List<TicketTrainBean>>>(){});
                if(bean!=null){
                    if(bean.isSuccess()){
                        getViewRef().onLoadTicktSuccess(bean.getRoot());
                    }else {
                        getViewRef().onLoadTicktFail("获取在修机车数据失败，请重试");
                    }
                }else {
                    getViewRef().onLoadTicktFail("连接服务器失败，请重试");
                }

            }

            @Override
            public void onError(ApiException e) {
                getViewRef().onLoadTicktFail(e.getMessage());
            }
        });

    }

    public void getTicketType(){
        req(RequestFactory.getInstance().createApi(TicketApi.class).getTicketType("JCZL_FAULT_TYPE"), new HttpOnNextListener() {
            @Override
            public void onNext(String result, String mothead) {
//                Log.e("json",result);
                BaseBean<List<TicketTypeBean>> bean = JSON.parseObject(result,new TypeReference<BaseBean<List<TicketTypeBean>>>(){});
                if(bean!=null){
                    if(bean.isSuccess()){
                        getViewRef().onLoadTicktTypeSuccess(bean.getRoot());
                    }else {
                        getViewRef().onLoadTicktFail("获取提票类型数据失败，请重试");
                    }
                }else {
                    getViewRef().onLoadTicktFail("连接服务器失败，请重试");
                }

            }

            @Override
            public void onError(ApiException e) {
                getViewRef().onLoadTicktFail(e.getMessage());
            }
        });

    }
}
