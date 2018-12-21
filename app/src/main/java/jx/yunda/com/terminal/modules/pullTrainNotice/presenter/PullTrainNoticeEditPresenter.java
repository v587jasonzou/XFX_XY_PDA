package jx.yunda.com.terminal.modules.pullTrainNotice.presenter;

import android.text.TextUtils;
import android.widget.TextView;

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
import jx.yunda.com.terminal.modules.pullTrainNotice.model.PullTrainNotice;
import jx.yunda.com.terminal.modules.pullTrainNotice.model.PullTrainNoticeItem;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class PullTrainNoticeEditPresenter extends BasePresenter<IPullTrainNoticeEdit> {
    public PullTrainNoticeEditPresenter(IPullTrainNoticeEdit view) {
        super(view);
    }

    public void saveOrUpdateNotice(String groupId, PullTrainNoticeItem item) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("groupId", groupId);
            params.put("pullTrainNotice", JSON.toJSONString(item));
            req(RequestFactory.getInstance().createApi(PullTrainNoticeApi.class).saveOrUpdateNotice(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity rst = JSON.parseObject(result, new TypeReference<BaseResultEntity>() {
                    });
                    if (rst != null && rst.getSuccess() != null && rst.getSuccess()) {
                        ToastUtil.toastShort("保存成功！");
                        getAct().initData();
                    } else
                        ToastUtil.toastShort(rst.getErrMsg());
                }

                @Override
                public void onError(ApiException e) {
                    getViewRef().pageNotifyNewDataSetChangedForSelectPersonDialog(null);
                    ToastUtil.toastShort(e.getMessage());
                    LogUtil.e("牵车通知编辑页面，保存/修改牵车通知Item", e.toString());
                }
            });
        } catch (Exception ex) {
            getViewRef().pageNotifyNewDataSetChangedForSelectPersonDialog(null);
            LogUtil.e("牵车通知编辑页面，保存/修改牵车通知Item", ex.toString());
        }
    }

    public void getGroupPersonsByPostName(String postName) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("postName", postName);
            req(RequestFactory.getInstance().createApi(TransNoticeApi.class).getNoticeAcceptPerson(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity<MessageGroupPerson> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<MessageGroupPerson>>() {
                    });
                    if (rst != null && rst.getSuccess() != null && rst.getSuccess()) {
                        getViewRef().pageNotifyNewDataSetChangedForSelectPersonDialog(rst.getRoot());
                    } else
                        getViewRef().pageNotifyNewDataSetChangedForSelectPersonDialog(null);

                }

                @Override
                public void onError(ApiException e) {
                    getViewRef().pageNotifyNewDataSetChangedForSelectPersonDialog(null);
                    ToastUtil.toastShort(e.getMessage());
                    LogUtil.e("牵车通知编辑页面，获取可选择机车", e.toString());
                }
            });
        } catch (Exception ex) {
            getViewRef().pageNotifyNewDataSetChangedForSelectPersonDialog(null);
            LogUtil.e("牵车通知编辑页面，获取可选择机车", ex.toString());
        }
    }

    public void getPullTrainPlan(String trainNo) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("trainNo", trainNo);
            req(RequestFactory.getInstance().createApi(PullTrainNoticeApi.class).getPullTrainNotice(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity<PullTrainNotice> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<PullTrainNotice>>() {
                    });
                    if (rst != null && rst.getSuccess() != null && rst.getSuccess()) {
                        getViewRef().pageNotifyNewDataSetChangedForSelectTrainDialog(rst.getRoot());
                    } else
                        getViewRef().pageNotifyNewDataSetChangedForSelectTrainDialog(null);
                }

                @Override
                public void onError(ApiException e) {
                    getViewRef().pageNotifyNewDataSetChangedForSelectTrainDialog(null);
                    ToastUtil.toastShort(e.getMessage());
                    LogUtil.e("牵车通知编辑页面，获取可选择机车", e.toString());
                }
            });
        } catch (Exception ex) {
            getViewRef().pageNotifyNewDataSetChangedForSelectTrainDialog(null);
            LogUtil.e("牵车通知编辑页面，获取可选择机车", ex.toString());
        }
    }

    public void getNoConfirmPullTrainDetailItemByTrainPlanIdx(String trainPlanIdx) {
        try {
            if (TextUtils.isEmpty(trainPlanIdx)) return;
            Map<String, Object> params = new HashMap<>();
            params.put("trainPlanIdx", trainPlanIdx);
            params.put("type", "0");
            req(RequestFactory.getInstance().createApi(PullTrainNoticeApi.class).getPullTrainDetails(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity<PullTrainNoticeItem> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<PullTrainNoticeItem>>() {
                    });
                    if (rst != null && rst.getSuccess() != null && rst.getSuccess() && rst.getRoot() != null && rst.getRoot().size() > 0) {
                        getViewRef().setPageByNoConfirmPullTrainDetailItem(rst.getRoot().get(0));
                    } else
                        getViewRef().setPageByNoConfirmPullTrainDetailItem(null);
                }

                @Override
                public void onError(ApiException e) {
                    getViewRef().setPageByNoConfirmPullTrainDetailItem(null);
                    ToastUtil.toastShort(e.getMessage());
                    LogUtil.e("牵车通知编辑页面，获取详情", e.toString());
                }
            });
        } catch (Exception ex) {
            getViewRef().setPageByNoConfirmPullTrainDetailItem(null);
            LogUtil.e("牵车通知编辑页面，获取详情", ex.toString());
        }
    }
}
