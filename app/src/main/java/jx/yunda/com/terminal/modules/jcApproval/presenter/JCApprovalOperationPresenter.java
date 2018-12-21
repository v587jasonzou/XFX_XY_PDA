package jx.yunda.com.terminal.modules.jcApproval.presenter;

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
import jx.yunda.com.terminal.modules.api.JCApprovalApi;
import jx.yunda.com.terminal.modules.jcApproval.model.JCApproval;
import jx.yunda.com.terminal.modules.jcApproval.model.JCApprovalOperationWorkContent;
import jx.yunda.com.terminal.modules.jcApproval.model.JCApprovalOperationWorkType;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class JCApprovalOperationPresenter extends BasePresenter<IJCApprovalOperation> {
    public JCApprovalOperationPresenter(IJCApprovalOperation view) {
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
                    BaseResultEntity<JCApprovalOperationWorkType> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<JCApprovalOperationWorkType>>() {
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
                    getViewRef().workTypeRecyclerNotifyDataSetChanged(null);
                    ToastUtil.toastShort(e.getMessage());
                    LogUtil.e("交车审批机车活项类型数据获取", e.toString());
                }
            });
        } catch (Exception ex) {
            getViewRef().workTypeRecyclerNotifyDataSetChanged(null);
            LogUtil.e("交车审批机车活项类型数据获取", ex.toString());
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
                    BaseResultEntity<JCApprovalOperationWorkContent> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<JCApprovalOperationWorkContent>>() {
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
                    getViewRef().workContentRecyclerNotifyDataSetChanged(null);
                    ToastUtil.toastShort(e.getMessage());
                    LogUtil.e("交车审批机车活项类型数据获取", e.toString());
                }
            });
        } catch (Exception ex) {
            getViewRef().workContentRecyclerNotifyDataSetChanged(null);
            LogUtil.e("交车审批机车活项类型数据获取", ex.toString());
        }
    }

    public void workContentBack(String typeName, final int position, final JCApprovalOperationWorkContent workContent) {
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
                    LogUtil.e("交车审批机车活项类型数据获取", e.toString());
                }
            });
        } catch (Exception ex) {
            LogUtil.e("交车审批机车活项类型数据获取", ex.toString());
        }
    }

    public void submitApproval(JCApproval jcApproval, boolean isAgreement, String opinions) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idx", jcApproval.getIdx());
            jsonObject.put("processInstId", jcApproval.getProcessInstId());
            jsonObject.put("workId", jcApproval.getWorkId());
            jsonObject.put("workName", jcApproval.getWorkName());
            jsonObject.put("taskKey", jcApproval.getTaskKey());
            jsonObject.put("opinions", opinions);
            jsonObject.put("opinionsResult", isAgreement ? "01" : "02");
            Map<String, Object> params = new HashMap<>();
            params.put("params", JSON.toJSONString(jsonObject));
            req(RequestFactory.getInstance().createApi(JCApprovalApi.class).deliverTrainApporval(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    BaseResultEntity rst = JSON.parseObject(result, new TypeReference<BaseResultEntity>() {
                    });
                    if (rst != null && rst.getSuccess()) {
                        ToastUtil.toastShort("审批成功！");
                        getViewRef().closeJCApplyOperationFragment();
                    } else
                        ToastUtil.toastShort("审批失败！");
                }

                @Override
                public void onError(ApiException e) {
                    ToastUtil.toastShort("审批失败！");
                    LogUtil.e("交车审批", e.toString());
                }
            });
        } catch (Exception ex) {
            LogUtil.e("交车审批", ex.toString());
        }
    }
}
