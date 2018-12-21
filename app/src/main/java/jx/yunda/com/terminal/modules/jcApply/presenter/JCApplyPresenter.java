package jx.yunda.com.terminal.modules.jcApply.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.entity.BaseResultEntity;
import jx.yunda.com.terminal.modules.api.JCApplyApi;
import jx.yunda.com.terminal.modules.jcApply.model.JCApply;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import rx.Observable;

public class JCApplyPresenter extends BasePresenter<IJCApply> {
    public JCApplyPresenter(IJCApply view) {
        super(view);
    }

    public void setPageData(boolean isJC) {
        try {
            Observable observable = isJC ? RequestFactory.getInstance().createApi(JCApplyApi.class).findDeliverTrainList() : RequestFactory.getInstance().createApi(JCApplyApi.class).findApprovalTrainList();
            req(observable, new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity<JCApply> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<JCApply>>() {
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
                    LogUtil.e("交车申请主页数据获取", e.toString());
                }
            });
        } catch (Exception ex) {
            getViewRef().pageRecyclerNotifyDataSetChanged(null);
            LogUtil.e("交车申请主页数据获取", ex.toString());
        }
    }
}
