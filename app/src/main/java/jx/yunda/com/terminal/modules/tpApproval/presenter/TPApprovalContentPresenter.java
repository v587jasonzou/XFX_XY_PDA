package jx.yunda.com.terminal.modules.tpApproval.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.Map;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.entity.BaseResultEntity;
import jx.yunda.com.terminal.modules.api.TPApprovalApi;
import jx.yunda.com.terminal.modules.tpApproval.model.TPApproval;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class TPApprovalContentPresenter extends BasePresenter<ITPApprovalContent> {
    public TPApprovalContentPresenter(ITPApprovalContent view) {
        super(view);
    }

    public void submitTpApproval(FaultTicket data) {
        try {
            data.setIsAllow("1");
            Map<String, Object> params = new HashMap<>();
            params.put("data", JSON.toJSONString(data));
            req(RequestFactory.getInstance().createApi(TPApprovalApi.class).approvalByPda(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity rst = JSON.parseObject(result, new TypeReference<BaseResultEntity>() {
                    });
                    if (rst != null && rst.getSuccess()) {
                        ToastUtil.toastShort("审批成功！");
                        getViewRef().submitAfter();
                    } else
                        ToastUtil.toastShort(rst.getErrMsg());
                }

                @Override
                public void onError(ApiException e) {
                    ToastUtil.toastShort(e.getMessage());
                    LogUtil.e("提票审批提交", e.toString());
                }
            });
        } catch (Exception ex) {
            LogUtil.e("提票审批提交", ex.toString());
        }
    }
}
