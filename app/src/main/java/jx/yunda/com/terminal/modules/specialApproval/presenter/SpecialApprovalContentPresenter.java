package jx.yunda.com.terminal.modules.specialApproval.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

public class SpecialApprovalContentPresenter extends BasePresenter<ISpecialApprovalContent> {
    public SpecialApprovalContentPresenter(ISpecialApprovalContent view) {
        super(view);
    }

    public void submitApproval(SpecialApproval specialApproval, boolean isAgreement, String opinions) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idx", specialApproval.getIdx());
            jsonObject.put("processInstId", specialApproval.getProcessInstId());
            jsonObject.put("workId", specialApproval.getWorkId());
            jsonObject.put("workName", specialApproval.getWorkName());
            jsonObject.put("taskKey", specialApproval.getTaskKey());
            jsonObject.put("opinions", opinions);
            jsonObject.put("opinionsResult", isAgreement ? "01" : "02");
            Map<String, Object> params = new HashMap<>();
            params.put("whereSnakerJson", JSON.toJSONString(jsonObject));
            req(RequestFactory.getInstance().createApi(SpecialApprovalApi.class).approvalDischarge(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity rst = JSON.parseObject(result, new TypeReference<BaseResultEntity>() {
                    });
                    if (rst != null && rst.getSuccess()) {
                        ToastUtil.toastShort("审批成功！");
                        getViewRef().closeFragmentRefreshMainPage();
                    } else
                        ToastUtil.toastShort("审批失败！");
                }

                @Override
                public void onError(ApiException e) {
                    ToastUtil.toastShort("审批失败！");
                    LogUtil.e("机车例外放行审批", e.toString());
                }
            });
        } catch (Exception ex) {
            LogUtil.e("机车例外放行审批", ex.toString());
        }
    }
}
