package jx.yunda.com.terminal.modules.TrainRecandition.act;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.TrainRecandition.adapter.RecanditionHisAdapter;
import jx.yunda.com.terminal.modules.TrainRecandition.adapter.RecanditionListAdapter;
import jx.yunda.com.terminal.modules.TrainRecandition.model.RecanditionBean;
import jx.yunda.com.terminal.modules.TrainRecandition.presenter.IRecanditionHistory;
import jx.yunda.com.terminal.modules.TrainRecandition.presenter.RecanditionHistoryPresenter;
import jx.yunda.com.terminal.modules.tpprocess.TicketListActivity;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketInfoBean;
import jx.yunda.com.terminal.utils.NetWorkUtils;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;

public class RecanditionHistoryActivity extends BaseActivity<RecanditionHistoryPresenter> implements IRecanditionHistory,View.OnClickListener {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.tvData)
    TextView tvData;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.lvList)
    ListView lvList;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    @BindView(R.id.llEmpty)
    LinearLayout llEmpty;
    List<RecanditionBean> list = new ArrayList<>();
    List<RecanditionBean> mUnCompletlistTemp = new ArrayList<>();
    RecanditionHisAdapter adapter;
    String nodeName = "";
    AlertDialog dialog;
    TextView tvStartData, tvEndData;
    long startTime = 0;
    long EndTime = 0;
    public static String STATUS_COMPLETED = "COMPLETED";
    public static String STATUS_NOTSTARTED = "NOTSTARTED";
    public static String STATUS_RUNNING = "RUNNING";
    int start = 0;
    int limit = 8;
    boolean isLoadMore = false;
    @Override
    protected RecanditionHistoryPresenter getPresenter() {
        return new RecanditionHistoryPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_recandition_history;
    }

    @Override
    public void initSubViews(View view) {

    }

    @Override
    public void initData() {
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        llEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }
        });
        setData();
        adapter = new RecanditionHisAdapter(this, list);
        lvList.setAdapter(adapter);
        srRefresh.setRefreshHeader(new ClassicsHeader(this));
        srRefresh.setRefreshFooter(new ClassicsFooter(this));
        srRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                start = 0;
                isLoadMore = false;
                setData();
            }
        });
        srRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                start = start + limit;
                if (start < mUnCompletlistTemp.size()) {
                    if (start + limit < mUnCompletlistTemp.size()) {
                        for (int i = start; i < start + limit; i++) {
                            list.add(mUnCompletlistTemp.get(i));
                        }
                    } else {
                        for (int i = start; i < mUnCompletlistTemp.size(); i++) {
                            list.add(mUnCompletlistTemp.get(i));
                        }
                    }
                } else {
                    refreshLayout.setNoMoreData(true);
                }
                refreshLayout.finishLoadMore();
                adapter.notifyDataSetChanged();
            }
        });
        setEditText();
        tvData.setOnClickListener(this);
    }
    private void setData() {
        if(!NetWorkUtils.isWifiConnected(this)){
            ToastUtil.toastShort("当前网络连接差，请检查网络");
            return;
        }
        getProgressDialog().show();
        srRefresh.setNoMoreData(false);
        Map<String, String> map = new HashMap<>();
//        map.put("workPlanIDX", trainTypeIDX);
        map.put("status", "2");
        map.put("nodeName", nodeName);
        mPresenter.GetRecanditionPlanList(JSON.toJSONString(map), start + "", 1000 + "", "false"
                , SysInfo.userInfo.emp.getEmpid() + "", isLoadMore);
    }
    @Override
    public void getPlansSuccess(List<RecanditionBean> recanditionBeans) {
        getProgressDialog().dismiss();
        list.clear();
        mUnCompletlistTemp.clear();
        if(recanditionBeans!=null){
            mUnCompletlistTemp.addAll(recanditionBeans);
            if (mUnCompletlistTemp.size() > limit) {
                for (int i = 0; i < limit; i++) {
                    list.add(mUnCompletlistTemp.get(i));
                }
            } else {
                list.addAll(mUnCompletlistTemp);
                srRefresh.setNoMoreData(true);
            }
//            adapter.notifyDataSetChanged();
            if (startTime != 0 && EndTime != 0) {
                list.clear();
                for (RecanditionBean bean : mUnCompletlistTemp) {
                    if (bean.getRealEndTime() != null) {
                        if (bean.getRealEndTime() >= startTime && bean.getRealEndTime() <= EndTime) {
                            list.add(bean);
                        }
                    }

                }
                mUnCompletlistTemp.clear();
                mUnCompletlistTemp.addAll(list);
            }
        }

        srRefresh.finishRefresh();
//        if (recanditionBeans != null) {
//            list.addAll(recanditionBeans);
//            if (recanditionBeans.size() < limit) {
//                srRefresh.setNoMoreData(true);
//            }
//        }
        if(list.size()==0){
            llEmpty.setVisibility(View.VISIBLE);
        }else {
            llEmpty.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getPlansFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void LoadMoreSuccess(List<RecanditionBean> recanditionBeans) {
        getProgressDialog().dismiss();
        srRefresh.finishLoadMore();
        if (recanditionBeans != null) {
            list.addAll(recanditionBeans);
            if (recanditionBeans.size() < limit) {
                srRefresh.setNoMoreData(true);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void LoadMoreFaild(String msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvData:
                if (dialog == null) {
                    View view = LayoutInflater.from(this).inflate(R.layout.dialog_choose_data, null);
                    tvStartData = (TextView) view.findViewById(R.id.tvStartData);
                    tvStartData.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar selectedDate = Calendar.getInstance();
                            Calendar startDate = Calendar.getInstance();
                            startDate.set(selectedDate.get(Calendar.YEAR) - 1, 0, 1);
                            Calendar endDate = Calendar.getInstance();
                            endDate.set(selectedDate.get(Calendar.YEAR) + 1, 11, 31);

                            TimePickerView pvTime = new TimePickerBuilder(RecanditionHistoryActivity.this, new OnTimeSelectListener() {
                                @Override
                                public void onTimeSelect(Date date, View v) {//选中事件回调
                                    tvStartData.setText(Utils.formatTime(date, "yyyy年MM月dd日"));
                                    tvStartData.setTag(date);
                                    startTime = date.getTime();
                                }
                            })
                                    .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                                    .setCancelText("取消")//取消按钮文字
                                    .setSubmitText("确定")//确认按钮文字
                                    .setContentTextSize(18)//滚轮文字大小
                                    .setTitleSize(20)//标题文字大小
                                    .setTitleText("选择日期")//标题文字
                                    .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                                    .isCyclic(false)//是否循环滚动
                                    .setTitleColor(Color.BLACK)//标题文字颜色
                                    .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                                    .setCancelColor(Color.BLUE)//取消按钮文字颜色
                                    .setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
                                    .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
//                                    .setRange(selectedDate.get(Calendar.YEAR) - 20, selectedDate.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                                    .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                                    .setRangDate(startDate, endDate)//起始终止年月日设定
                                    .setLabel("年", "月", "日", "时", "分", "秒")
                                    .isDialog(true)//是否显示为对话框样式
                                    .build();
                            pvTime.show();
                        }
                    });

                    tvEndData = (TextView) view.findViewById(R.id.tvEndData);
                    tvEndData.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (tvStartData.getTag() == null) {
                                ToastUtil.toastShort("请先选择开始时间");
                                return;
                            }
                            Calendar selectedDate = Calendar.getInstance();
                            Calendar startDate = Calendar.getInstance();
                            startDate.set(selectedDate.get(Calendar.YEAR) - 1, 0, 1);
                            Calendar endDate = Calendar.getInstance();
                            endDate.set(selectedDate.get(Calendar.YEAR) + 1, 11, 31);

                            TimePickerView pvTime = new TimePickerBuilder(RecanditionHistoryActivity.this, new OnTimeSelectListener() {
                                @Override
                                public void onTimeSelect(Date date, View v) {//选中事件回调
                                    tvEndData.setText(Utils.formatTime(date, "yyyy年MM月dd日"));
                                    tvEndData.setTag(date);
                                    EndTime = date.getTime();
                                }
                            })
                                    .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                                    .setCancelText("取消")//取消按钮文字
                                    .setSubmitText("确定")//确认按钮文字
                                    .setContentTextSize(18)//滚轮文字大小
                                    .setTitleSize(20)//标题文字大小
                                    .setTitleText("选择日期")//标题文字
                                    .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                                    .isCyclic(false)//是否循环滚动
                                    .setTitleColor(Color.BLACK)//标题文字颜色
                                    .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                                    .setCancelColor(Color.BLUE)//取消按钮文字颜色
                                    .setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
                                    .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
//                                    .setRange(selectedDate.get(Calendar.YEAR) - 20, selectedDate.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                                    .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                                    .setRangDate(startDate, endDate)//起始终止年月日设定
                                    .setLabel("年", "月", "日", "时", "分", "秒")
                                    .isDialog(true)//是否显示为对话框样式
                                    .build();
                            pvTime.show();
                        }
                    });
                    dialog = new AlertDialog.Builder(this).setTitle("请选择查询日期").setView(view)
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {//下面三句控制弹框的关闭
                                        Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                        field.setAccessible(true);
                                        field.set(dialog, false);//true表示要关闭
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (tvStartData.getTag() == null) {
                                        ToastUtil.toastShort("还未选择开始时间");
                                        return;
                                    }
                                    if (tvEndData.getTag() == null) {
                                        ToastUtil.toastShort("还未选择结束时间");
                                        return;
                                    }
                                    if (((Date) tvStartData.getTag()).getTime() > ((Date) tvEndData.getTag()).getTime()) {
                                        ToastUtil.toastShort("开始时间不能大于结束时间，请重新选择");
                                        return;
                                    }
                                    try {//下面三句控制弹框的关闭
                                        Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                        field.setAccessible(true);
                                        field.set(dialog, true);//true表示要关闭
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    tvData.setText(tvStartData.getText().toString().split("年")[1] + "至" +
                                            tvEndData.getText().toString().split("年")[1]);
                                    if (startTime != 0 && EndTime != 0) {
                                        list.clear();
                                        for (RecanditionBean bean : mUnCompletlistTemp) {
                                            if (bean.getRealEndTime() != null) {
                                                if (bean.getRealEndTime() >= startTime && bean.getRealEndTime() <= EndTime) {
                                                    list.add(bean);
                                                }
                                            }

                                        }
                                        adapter.notifyDataSetChanged();
                                        if (list.size() == 0) {
                                            llEmpty.setVisibility(View.VISIBLE);
                                        } else {
                                            llEmpty.setVisibility(View.GONE);
                                        }
                                        srRefresh.setNoMoreData(true);
                                    }
                                }
                            }).create();
                }
                dialog.show();
                break;
        }
    }
    private void setEditText() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“GO”键*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(
                                v.getApplicationWindowToken(), 0);
                    }
                    if (etSearch.getText() != null && !etSearch.getText().equals("")) {
                        list.clear();
                        for (RecanditionBean bean : mUnCompletlistTemp) {
                            if (bean.getTrainTypeShortName().contains(etSearch.getText().toString())
                                    || bean.getTrainNo().contains(etSearch.getText().toString())) {
                                list.add(bean);
                            }
                        }
                        if (startTime != 0 && EndTime != 0) {
                            List<RecanditionBean> temp = new ArrayList<>();
                            for (RecanditionBean bean : list) {
                                if (bean.getRealEndTime() != null) {
                                    if (bean.getRealEndTime() >= startTime && bean.getRealEndTime() <= EndTime) {
                                        temp.add(bean);
                                    }
                                }
                            }
                            list.clear();
                            if (temp.size() != 0) {
                                list.addAll(temp);
                            }
                        }
                        if (list.size() == 0) {
                            llEmpty.setVisibility(View.VISIBLE);
                        } else {
                            llEmpty.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();
                        srRefresh.setNoMoreData(true);
                    } else {
                        ToastUtil.toastShort("未查询到相关结果");
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
