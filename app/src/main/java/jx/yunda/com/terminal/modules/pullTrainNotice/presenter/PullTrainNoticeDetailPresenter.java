package jx.yunda.com.terminal.modules.pullTrainNotice.presenter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.Map;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.entity.BaseResultEntity;
import jx.yunda.com.terminal.modules.api.PullTrainNoticeApi;
import jx.yunda.com.terminal.modules.api.TransNoticeApi;
import jx.yunda.com.terminal.modules.message.model.MessageGroupPerson;
import jx.yunda.com.terminal.modules.pullTrainNotice.model.PullTrainNoticeItem;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class PullTrainNoticeDetailPresenter extends BasePresenter<IPullTrainNoticeDetail> {
    public PullTrainNoticeDetailPresenter(IPullTrainNoticeDetail view) {
        super(view);
    }

    public void getPullTrainDetails(String trainPlanIdx) {
        try {
            if (TextUtils.isEmpty(trainPlanIdx)) return;
            Map<String, Object> params = new HashMap<>();
            params.put("trainPlanIdx", trainPlanIdx);
            params.put("type", "1");
            req(RequestFactory.getInstance().createApi(PullTrainNoticeApi.class).getPullTrainDetails(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity<PullTrainNoticeItem> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<PullTrainNoticeItem>>() {
                    });
                    if (rst != null && rst.getSuccess() != null && rst.getSuccess() && rst.getRoot() != null && rst.getRoot().size() > 0) {
                        getViewRef().pageNotifyNewDataSetChangedForPullTrainDetails(rst.getRoot());
                    } else
                        getViewRef().pageNotifyNewDataSetChangedForPullTrainDetails(null);
                }

                @Override
                public void onError(ApiException e) {
                    getViewRef().pageNotifyNewDataSetChangedForPullTrainDetails(null);
                    ToastUtil.toastShort(e.getMessage());
                    LogUtil.e("牵车通知详情页面，获取详情", e.toString());
                }
            });
        } catch (Exception ex) {
            getViewRef().pageNotifyNewDataSetChangedForPullTrainDetails(null);
            LogUtil.e("牵车通知详情页面，获取详情", ex.toString());
        }
    }

    public void deletePullNotice(String idx, HttpOnNextListener listener) {
        try {
            if (TextUtils.isEmpty(idx)) return;
            Map<String, Object> params = new HashMap<>();
            params.put("idx", idx);
            req(RequestFactory.getInstance().createApi(PullTrainNoticeApi.class).deletePullNotice(params), listener);
        } catch (Exception ex) {
            LogUtil.e("牵车通知详情页面，删除未确认的详情", ex.toString());
        }
    }
}
