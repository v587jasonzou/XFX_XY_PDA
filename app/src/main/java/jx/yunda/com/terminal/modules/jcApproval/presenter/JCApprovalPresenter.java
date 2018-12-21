package jx.yunda.com.terminal.modules.jcApproval.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.entity.BaseResultEntity;
import jx.yunda.com.terminal.modules.api.JCApplyApi;
import jx.yunda.com.terminal.modules.jcApproval.model.JCApproval;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import rx.Observable;

public class JCApprovalPresenter extends BasePresenter<IJCApproval> {
    public JCApprovalPresenter(IJCApproval view) {
        super(view);
    }

    public void setPageData(boolean isJC) {
        try {
            Observable observable = isJC ? RequestFactory.getInstance().createApi(JCApplyApi.class).findDeliverTrainList() : RequestFactory.getInstance().createApi(JCApplyApi.class).findApprovalTrainList();
            req(observable, new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity<JCApproval> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<JCApproval>>() {
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
                    LogUtil.e("交车审批主页数据获取", e.toString());
                }
            });
        } catch (Exception ex) {
            getViewRef().pageRecyclerNotifyDataSetChanged(null);
            LogUtil.e("交车审批主页数据获取", ex.toString());
        }
    }
}
