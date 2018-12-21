package jx.yunda.com.terminal.modules.tpApproval.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.entity.BaseResultEntity;
import jx.yunda.com.terminal.modules.api.TPApprovalApi;
import jx.yunda.com.terminal.modules.tpApproval.model.TPApproval;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TPApprovalPresenter extends BasePresenter<ITPApproval> {
    public TPApprovalPresenter(ITPApproval view) {
        super(view);
    }

//    public void setMainPageData(String searchCondition,String professionTypeIdx) {
//        try {
//            Map<String, Object> params = new HashMap<>();
//            params.put("trainNo", searchCondition);
//            params.put("professionTypeIdx",professionTypeIdx);
//            req(RequestFactory.getInstance().createApi(TPApprovalApi.class).getFaultTicketDischargeList(params), new HttpOnNextListener() {
//                @Override
//                public void onNext(String result, String mothead) {
//                    BaseResultEntity<TPApproval> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<TPApproval>>() {
//                    });
//                    if (rst != null && rst.getSuccess())
//                        getViewRef().pageRecyclerNotifyDataSetChanged(rst.getRoot());
//                    else{
//                        getViewRef().pageRecyclerNotifyDataSetChanged(null);
//                        ToastUtil.toastShort("无机车提票数据！");
//                    }
//                }
//
//                @Override
//                public void onError(ApiException e) {
//                    ToastUtil.toastShort(e.getMessage());
//                    getViewRef().pageRecyclerNotifyDataSetChanged(null);
//                    LogUtil.e("提票审批主页面数据获取", e.toString());
//                }
//            });
//        } catch (Exception ex) {
//            getViewRef().pageRecyclerNotifyDataSetChanged(null);
//            LogUtil.e("提票审批主页面数据获取", ex.toString());
//        }
//    }

    public void setMainPageData(String searchCondition,String professionTypeIdx,String name) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("trainNo", searchCondition);
            params.put("professionTypeIdx",professionTypeIdx);
            params.put("trainTypeShortName",name);
            params.put("isApproval","1");
            RequestFactory.getInstance().createApi(TPApprovalApi.class).getFaultTicket(params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String str = response.body();
                    BaseBean<List<FaultTicket>> rst = JSON.parseObject(str, new TypeReference< BaseBean<List<FaultTicket>>>() {
                    });
                    if (rst != null)
                        getViewRef().pageRecyclerNotifyDataSetChanged(rst.getRoot());
                    else{
                        getViewRef().pageRecyclerNotifyDataSetChanged(null);
                        ToastUtil.toastShort("无机车提票数据！");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    ToastUtil.toastShort("无机车提票数据！"+t.getMessage());
                }
            });
        } catch (Exception ex) {
            getViewRef().pageRecyclerNotifyDataSetChanged(null);
            LogUtil.e("提票审批主页面数据获取", ex.toString());
        }
    }
}
