package jx.yunda.com.terminal.modules.specialApproval.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.Map;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.entity.BaseResultEntity;
import jx.yunda.com.terminal.modules.api.SpecialApprovalApi;
import jx.yunda.com.terminal.modules.specialApproval.model.SpecialApproval;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class SpecialApprovalPresenter extends BasePresenter<ISpecialApproval> {
    public SpecialApprovalPresenter(ISpecialApproval view) {
        super(view);
    }

    public void getApprovalPageData(String trainNo) {
        try {
            Map<String, Object> mapcondition = new HashMap<>();
            mapcondition.put("trainNo", trainNo);
            req(RequestFactory.getInstance().createApi(SpecialApprovalApi.class).getDischargeListByPda(mapcondition), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity<SpecialApproval> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<SpecialApproval>>() {
                    });
                    if (rst != null && rst.getSuccess())
                        getViewRef().pageRecyclerNotifyDataSetChanged(rst.getRoot());
                    else {
                        ToastUtil.toastShort("无机车数据！");
                        getViewRef().pageRecyclerNotifyDataSetChanged(null);
                    }
                }

                @Override
                public void onError(ApiException e) {
                    ToastUtil.toastShort(e.getMessage());
                    getViewRef().pageRecyclerNotifyDataSetChanged(null);
                    LogUtil.e("例外放行审批主页数据获取", e.toString());
                }
            });
        } catch (Exception ex) {
            getViewRef().pageRecyclerNotifyDataSetChanged(null);
            LogUtil.e("例外放行审批主页数据获取", ex.toString());
        }
    }
}
