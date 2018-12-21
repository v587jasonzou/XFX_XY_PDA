package jx.yunda.com.terminal.modules.jcApply.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.entity.BaseResultEntity;
import jx.yunda.com.terminal.modules.api.JCApplyApi;
import jx.yunda.com.terminal.modules.jcApply.model.JCApplyProcess;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class JCApplyProcessPresenter extends BasePresenter<IJCApplyProcess> {
    public JCApplyProcessPresenter(IJCApplyProcess view) {
        super(view);
    }

    public void getProcessByProcessInstID(String processInstID) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("processInstID", processInstID);
            Map<String, Object> params = new HashMap<>();
            params.put("params", JSON.toJSONString(jsonObject));
            req(RequestFactory.getInstance().createApi(JCApplyApi.class).findApprovalTrainListPda(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity<JCApplyProcess> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<JCApplyProcess>>() {
                    });
                    if (rst != null && rst.getSuccess()) {
                        List<JCApplyProcess> list = rst.getRoot();
                        Collections.reverse(list);
                        getViewRef().recyclerNotifyDataSetChanged(list);
                    } else {
                        ToastUtil.toastShort("无数据！");
                        getViewRef().recyclerNotifyDataSetChanged(null);
                    }
                }

                @Override
                public void onError(ApiException e) {
                    ToastUtil.toastShort(e.getMessage());
                    getViewRef().recyclerNotifyDataSetChanged(null);
                    LogUtil.e("交车进度数据获取", e.toString());
                }
            });
        } catch (Exception ex) {
            getViewRef().recyclerNotifyDataSetChanged(null);
            LogUtil.e("交车进度数据获取", ex.toString());
        }
    }
}
