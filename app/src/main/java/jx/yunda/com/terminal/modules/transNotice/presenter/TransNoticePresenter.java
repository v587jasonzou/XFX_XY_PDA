package jx.yunda.com.terminal.modules.transNotice.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.Map;

import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.entity.BaseResultEntity;
import jx.yunda.com.terminal.modules.api.TransNoticeApi;
import jx.yunda.com.terminal.modules.transNotice.model.TransNotice;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class TransNoticePresenter extends BasePresenter<ITransNotice> {
    public TransNoticePresenter(ITransNotice view) {
        super(view);
    }

    public void getNotice(int start) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("start", start);
            params.put("limit", 40);
            params.put("timeStart", "");
            params.put("timeEnd", "");
            params.put("type", "-1");
            req(RequestFactory.getInstance().createApi(TransNoticeApi.class).getNotice(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity<TransNotice> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<TransNotice>>() {
                    });
                    if (rst != null && rst.getSuccess())
                        getViewRef().pageListNotifyDataSetChanged(rst.getId(), rst.getRoot(), rst.getTotalProperty());
                    else {
                        ToastUtil.toastShort("本次查询无知单数据！");
                        getViewRef().pageListNotifyDataSetChanged("", null, 0);
                    }
                }

                @Override
                public void onError(ApiException e) {
                    ToastUtil.toastShort(e.getMessage());
                    getViewRef().pageListNotifyDataSetChanged("", null, 0);
                    LogUtil.e("调车通知主页数据获取", e.toString());
                }
            });
        } catch (Exception ex) {
            getViewRef().pageListNotifyDataSetChanged("", null, 0);
            LogUtil.e("调车通知主页数据获取", ex.toString());
        }
    }

    public void deleteNotice(String noticeIDX, final int position) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("noticeIDX", noticeIDX);
            req(RequestFactory.getInstance().createApi(TransNoticeApi.class).deleteNotice(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity<TransNotice> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<TransNotice>>() {
                    });
                    if (rst != null && rst.getSuccess()) {
                        ToastUtil.toastShort("删除成功！");
                        getViewRef().pageListRemoveItem(position);
                    } else
                        ToastUtil.toastShort("删除失败！");
                }

                @Override
                public void onError(ApiException e) {
                    ToastUtil.toastShort(e.getMessage());
                    LogUtil.e("调车通知主页删除通知单", e.toString());
                }
            });
        } catch (Exception ex) {
            LogUtil.e("调车通知主页删除通知单", ex.toString());
        }
    }

    public void submitNotice(final TransNotice transNotice, final int position) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("noticeIDX", transNotice.getIdx());
            req(RequestFactory.getInstance().createApi(TransNoticeApi.class).submitNotice(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity<TransNotice> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<TransNotice>>() {
                    });
                    if (rst != null && rst.getSuccess()) {
                        ToastUtil.toastShort("提交成功！");
                        transNotice.setSubmitStatus("1");
                        getViewRef().pageListUpdateItem(transNotice, position);
                    } else
                        ToastUtil.toastShort("提交失败！");
                }

                @Override
                public void onError(ApiException e) {
                    ToastUtil.toastShort(e.getMessage());
                    LogUtil.e("调车通知主页提交通知单", e.toString());
                }
            });
        } catch (Exception ex) {
            LogUtil.e("调车通知主页提交通知单", ex.toString());
        }
    }
}
