package jx.yunda.com.terminal.modules.jcApply.presenter;

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
import jx.yunda.com.terminal.modules.api.JCApplyApi;
import jx.yunda.com.terminal.modules.jcApply.model.JCApply;
import jx.yunda.com.terminal.modules.jcApply.model.JCApplyOperationWorkContent;
import jx.yunda.com.terminal.modules.jcApply.model.JCApplyOperationWorkType;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class JCApplyOperationPresenter extends BasePresenter<IJCApplyOperation> {
    public JCApplyOperationPresenter(IJCApplyOperation view) {
        super(view);
    }

    public void getWorkTypes(String workPlanIdx) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("workPlanIdx", workPlanIdx);
            Map<String, Object> params = new HashMap<>();
            params.put("params", JSON.toJSONString(jsonObject));
            req(RequestFactory.getInstance().createApi(JCApplyApi.class).findSixTicketClassCounts(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity<JCApplyOperationWorkType> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<JCApplyOperationWorkType>>() {
                    });
                    if (rst != null && rst.getSuccess())
                        getViewRef().workTypeRecyclerNotifyDataSetChanged(rst.getRoot());
                    else {
                        ToastUtil.toastShort("无数据！");
                        getViewRef().workTypeRecyclerNotifyDataSetChanged(null);
                    }
                }

                @Override
                public void onError(ApiException e) {
                    ToastUtil.toastShort(e.getMessage());
                    getViewRef().workTypeRecyclerNotifyDataSetChanged(null);
                    LogUtil.e("交车申请机车活项类型数据获取", e.toString());
                }
            });
        } catch (Exception ex) {
            getViewRef().workTypeRecyclerNotifyDataSetChanged(null);
            LogUtil.e("交车申请机车活项类型数据获取", ex.toString());
        }
    }

    public void getWorkContents(String workPlanIdx, String typeName) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("workPlanIdx", workPlanIdx);
            jsonObject.put("type", typeName);
            Map<String, Object> params = new HashMap<>();
            params.put("params", JSON.toJSONString(jsonObject));
            req(RequestFactory.getInstance().createApi(JCApplyApi.class).findEveryTicketList(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity<JCApplyOperationWorkContent> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<JCApplyOperationWorkContent>>() {
                    });
                    if (rst != null && rst.getSuccess())
                        getViewRef().workContentRecyclerNotifyDataSetChanged(rst.getRoot());
                    else {
                        ToastUtil.toastShort("无数据！");
                        getViewRef().workContentRecyclerNotifyDataSetChanged(null);
                    }
                }

                @Override
                public void onError(ApiException e) {
                    ToastUtil.toastShort(e.getMessage());
                    getViewRef().workContentRecyclerNotifyDataSetChanged(null);
                    LogUtil.e("交车申请机车活项类型数据获取", e.toString());
                }
            });
        } catch (Exception ex) {
            getViewRef().workContentRecyclerNotifyDataSetChanged(null);
            LogUtil.e("交车申请机车活项类型数据获取", ex.toString());
        }
    }

    public void workContentBack(String typeName, final int position, final JCApplyOperationWorkContent workContent) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("workPlanIdx", workContent.getWorkPlanIdx());
            jsonObject.put("ticketIdx", workContent.getIdx());
            jsonObject.put("type", typeName);
            Map<String, Object> params = new HashMap<>();
            params.put("params", JSON.toJSONString(jsonObject));
            req(RequestFactory.getInstance().createApi(JCApplyApi.class).rollBackTicket(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity rst = JSON.parseObject(result, new TypeReference<BaseResultEntity>() {
                    });
                    if (rst != null && rst.getSuccess()) {
                        ToastUtil.toastShort("退回成功！");
                        workContent.isShowBackBtn = false;
                        getViewRef().workContentRecyclerNotifyItemChanged(position, workContent);
                    } else
                        ToastUtil.toastShort("退回失败！");
                }

                @Override
                public void onError(ApiException e) {
                    ToastUtil.toastShort("退回失败！");
                    LogUtil.e("交车申请机车活项类型数据获取", e.toString());
                }
            });
        } catch (Exception ex) {
            LogUtil.e("交车申请机车活项类型数据获取", ex.toString());
        }
    }

    public void submitApply(JCApply jcApply) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("workPlanIdx", jcApply.getWorkPlanIdx());
            Map<String, Object> params = new HashMap<>();
            params.put("params", JSON.toJSONString(jsonObject));
            req(RequestFactory.getInstance().createApi(JCApplyApi.class).applyDeliverTrain(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity rst = JSON.parseObject(result, new TypeReference<BaseResultEntity>() {
                    });
                    if (rst != null && rst.getSuccess()) {
                        ToastUtil.toastShort("申请成功！");
                        getViewRef().closeJCApplyOperationFragment();
                    } else
                        ToastUtil.toastShort("申请失败！");
                }

                @Override
                public void onError(ApiException e) {
                    ToastUtil.toastShort("申请失败！");
                    LogUtil.e("交车申请机车活项类型数据获取", e.toString());
                }
            });
        } catch (Exception ex) {
            LogUtil.e("交车申请机车活项类型数据获取", ex.toString());
        }
    }
}
