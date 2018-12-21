package jx.yunda.com.terminal.modules.tpmanage.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import io.realm.Realm;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.base.JXApplication;
import jx.yunda.com.terminal.modules.tpprocess.ITicketList;
import jx.yunda.com.terminal.modules.tpprocess.RealmDb.TicketSubmitDao;
import jx.yunda.com.terminal.modules.tpprocess.TicketInfoReadActivity;
import jx.yunda.com.terminal.modules.tpprocess.TicketListPresenter;
import jx.yunda.com.terminal.modules.tpprocess.adapter.TicketListAdapter;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketInfoBean;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 提票处理列表 - 处理记录(检索)
 * {
 * 已处理:[tab:0,state:0] -> bundle{state:3}
 * 已保存:[tab:1,state:2] -> bundle{state:2}
 * }
 */
public class TicketManageListActivity extends BaseActivity<TicketListPresenter> implements ITicketList, TicketListAdapter.OnInfoClickListner, View.OnClickListener {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tb_title)
    TabLayout tbTitle;
    //    @BindView(R.id.tv_unComplet)
    //    TextView tvUnComplet;
    @BindView(R.id.tv_unUpload)
    TextView tvUnUpload;
    @BindView(R.id.lvTicketList)
    ListView lvTicketList;
    @BindView(R.id.svList)
    SmartRefreshLayout svList;
    @BindView(R.id.btSubmit)
    Button btSubmit;
    @BindView(R.id.btAllSubmit)
    Button btAllSubmit;
    @BindView(R.id.rvFooter)
    RelativeLayout rvFooter;
    @BindView(R.id.llEmpty)
    LinearLayout llEmpty;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.tvData)
    TextView tvData;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    TextView tvStartData, tvEndData;
    TicketListAdapter adapter;
    List<TicketInfoBean> mlist = new ArrayList<>();
    List<TicketInfoBean> mUnupLoadTemp = new ArrayList<>();
    List<TicketInfoBean> mUnCompletlistTemp = new ArrayList<>();
    List<TicketInfoBean> mCompletlistTemp = new ArrayList<>();
    int state = 0;
    int limit = 8;
    int start = 0;
    String type = "";
    AlertDialog dialog;
    long startTime = 0;
    long EndTime = 0;
    List<TicketSubmitDao> daos = new ArrayList<>();
    ArrayList<TicketInfoBean> uploads = new ArrayList<>();

    @Override
    protected TicketListPresenter getPresenter() {
        return new TicketListPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_ticket_manage_list;
    }

    @Override
    public void initSubViews(View view) {

    }

    @Override
    public void initData() {
        //        init();
        //        Intent intent = getIntent();
        //        Bundle bundle = intent.getExtras();
        //        type = bundle.getString("type","3");
        //        state = bundle.getInt("state", 0);
        rvFooter.setVisibility(View.GONE);
        if (state == 1) {
            tbTitle.getTabAt(1).select();
            rvFooter.setVisibility(View.VISIBLE);
        }
        adapter = new TicketListAdapter(this, mlist, 0);
        lvTicketList.setAdapter(adapter);
        adapter.setOnInfoClickListner(this);
        tvData.setOnClickListener(this);
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                daos = Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance().where(TicketSubmitDao.class).equalTo("dispatchEmpID", SysInfo.userInfo.emp.getEmpid()).equalTo("type", type).findAll());
                if (daos != null && daos.size() > 0) {
                    for (TicketSubmitDao bean : daos) {
                        TicketInfoBean tBean = new TicketInfoBean();
                        tBean.setResolutionContent(bean.getResolutionContent());
                        tBean.setFaultDesc(bean.getFaultDesc());
                        tBean.setFixPlaceFullName(bean.getFixPlaceFullName());
                        tBean.setTrainNo(bean.getTrainNo());
                        tBean.setTrainTypeShortName(bean.getTrainTypeShortName());
                        tBean.setFixPlaceFullCode(bean.getFixPlaceFullCode());
                        tBean.setReasonAnalysisID(bean.getReasonAnalysisKaoHeId() + "," + bean.getReasonAnalysisZhuanYeID());
                        tBean.setReasonAnalysis(bean.getReasonAnalysisKaoHe() + "," + bean.getReasonAnalysisZhuanYe());
                        tBean.setType(bean.getType());
                        tBean.setTrainTypeIDX(bean.getTrainTypeIDX());
                        tBean.setFaultOccurDate(bean.getFaultOccurDate());
                        tBean.setIdx(bean.getIdx());
                        mUnupLoadTemp.add(tBean);
                    }
                }
                subscriber.onNext(0);
            }
        }).subscribeOn(Schedulers.from(JXApplication.getExecutor())).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                if (integer > 0) {
                    tvUnUpload.setVisibility(View.VISIBLE);
                    tvUnUpload.setText(integer + "");
                } else {
                    tvUnUpload.setVisibility(View.GONE);
                }
                if (state == 1) {
                    mlist.clear();
                    mlist.addAll(mUnupLoadTemp);
                    adapter.setState(2);
                    adapter.notifyDataSetChanged();
                }
            }
        });
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
                llEmpty.setVisibility(View.GONE);
                Map<String, Object> map = new HashMap<>();
                map.put("dispatchEmpID", SysInfo.userInfo.emp.getEmpid());
                map.put("status", 30);
                //                map.put("type", type);
                start = 0;
                mPresenter.getTicketList(0, 1000, JSON.toJSONString(map), 0);
            }
        });
        //设置 Header 为 贝塞尔雷达 样式
        svList.setRefreshHeader(new ClassicsHeader(this));
        //设置 Footer 为 球脉冲 样式
        svList.setRefreshFooter(new ClassicsFooter(this));
        svList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                svList.setNoMoreData(false);
                Map<String, Object> map = new HashMap<>();
                map.put("dispatchEmpID", SysInfo.userInfo.emp.getEmpid());
                //                map.put("type", type);
                map.put("status", 30);
                start = 0;
                mPresenter.getTicketList(0, 1000, JSON.toJSONString(map), 0);
            }
        });
        svList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (state == 0) {
                    start = start + limit;
                    if (start < mCompletlistTemp.size()) {
                        if (start + limit < mCompletlistTemp.size()) {
                            for (int i = start; i < start + limit; i++) {
                                mlist.add(mCompletlistTemp.get(i));
                            }
                        } else {
                            for (int i = start; i < mCompletlistTemp.size(); i++) {
                                mlist.add(mCompletlistTemp.get(i));
                            }
                        }
                    } else {
                        refreshLayout.setNoMoreData(true);
                    }
                }
                refreshLayout.finishLoadMore();
                adapter.notifyDataSetChanged();
            }
        });
        //        svList.setFooter(new DefaultFooter(this));
        //        svList.setHeader(new DefaultHeader(this));
        tbTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0://已处理
                        llSearch.setVisibility(View.VISIBLE);
                        svList.setNoMoreData(false);
                        start = 0;
                        Map<String, Object> map1 = new HashMap<>();
                        map1.put("dispatchEmpID", SysInfo.userInfo.emp.getEmpid());
                        map1.put("status", 30);
                        //                        map1.put("type", type);
                        mPresenter.getTicketList(0, 1000, JSON.toJSONString(map1), 0);
                        state = 0;
                        rvFooter.setVisibility(View.GONE);
                        adapter.setState(1);
                        adapter.notifyDataSetChanged();
                        break;
                    case 1://已保存
                        llSearch.setVisibility(View.GONE);
                        state = 2;
                        mlist.clear();
                        mlist.addAll(mUnupLoadTemp);
                        rvFooter.setVisibility(View.VISIBLE);
                        if (mlist.size() == 0) {
                            llEmpty.setVisibility(View.VISIBLE);
                        } else {
                            llEmpty.setVisibility(View.GONE);
                        }
                        adapter.setState(2);
                        adapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        btSubmit.setOnClickListener(this);
        btAllSubmit.setOnClickListener(this);
        Map<String, Object> map = new HashMap<>();
        map.put("dispatchEmpID", SysInfo.userInfo.emp.getEmpid());
        map.put("status", 30);
        //        map.put("type", type);
        mPresenter.getTicketList(0, 1000, JSON.toJSONString(map), 0);
        setEditText();
    }

    @Override
    public void OnTicketListLoadSuccess(List<TicketInfoBean> list) {
        mlist.clear();
        mUnCompletlistTemp.clear();
        mCompletlistTemp.clear();
        if (list != null && list.size() > 0) {
            mCompletlistTemp.addAll(list);
        }

        if (state == 0) {
            if (mCompletlistTemp.size() > limit) {
                for (int i = 0; i < limit; i++) {
                    mlist.add(mCompletlistTemp.get(i));
                }
            } else {
                mlist.addAll(mCompletlistTemp);
                svList.setNoMoreData(true);
            }
            adapter.notifyDataSetChanged();
            if (mlist.size() == 0) {
                llEmpty.setVisibility(View.VISIBLE);
            } else {
                llEmpty.setVisibility(View.GONE);
            }
            if (startTime != 0 && EndTime != 0) {
                mlist.clear();
                for (TicketInfoBean bean : mCompletlistTemp) {
                    if (bean.getCreateTime() != null) {
                        if (bean.getCreateTime() >= startTime && bean.getCreateTime() <= EndTime) {
                            mlist.add(bean);
                        }
                    }

                }
                mCompletlistTemp.clear();
                mCompletlistTemp.addAll(mlist);
            }
        } else {
            if (mUnupLoadTemp.size() > 0)
                mlist.addAll(mUnupLoadTemp);
        }
        svList.finishRefresh();
        svList.finishLoadMore();
        adapter.notifyDataSetChanged();
        if (mlist.size() == 0) {
            llEmpty.setVisibility(View.VISIBLE);
        } else {
            llEmpty.setVisibility(View.GONE);
        }
        //        if (mUnCompletlistTemp.size() > 0) {
        //            tvUnComplet.setVisibility(View.VISIBLE);
        //            tvUnComplet.setText(mUnCompletlistTemp.size() + "");
        ////            tvUnUpload.setText(mlist.size()+"");
        //        } else {
        //            tvUnComplet.setVisibility(View.GONE);
        //        }

    }

    @Override
    public void OnTicketListLoadMoreSuccess(List<TicketInfoBean> list) {
        mlist.addAll(list);
    }

    @Override
    public void OnLoadFail(String msg) {
        svList.finishRefresh();
        svList.finishLoadMore();
        if (mlist != null && mlist.size() > 0) {
            //            tvUnComplet.setVisibility(View.VISIBLE);
            tvUnUpload.setVisibility(View.VISIBLE);
            //            tvUnComplet.setText(mlist.size() + "");
            tvUnUpload.setText(mlist.size() + "");
        } else {
            //            tvUnComplet.setVisibility(View.GONE);
            tvUnUpload.setVisibility(View.GONE);
        }
        svList.setNoMoreData(true);
    }

    @Override
    public void OnImageUploadSuccess(int position) {

    }

    @Override
    public void OnImageUploadFail(int position, String msg) {

    }

    @Override
    public void OnTicketSubmitSuccess(int position) {
        getProgressDialog().dismiss();
        Toast toast = new Toast(this);
        ImageView image = new ImageView(this);
        image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        image.setImageResource(R.mipmap.submit_success);
        toast.setView(image);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
        Observable.create(new Observable.OnSubscribe<List<TicketInfoBean>>() {
            @Override
            public void call(Subscriber<? super List<TicketInfoBean>> subscriber) {
                List<TicketSubmitDao> list = Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance().where(TicketSubmitDao.class).equalTo("operatorid", SysInfo.userInfo.emp.getOperatorid()).equalTo("type", type).findAll());
                List<TicketInfoBean> beans = new ArrayList<>();
                for (TicketSubmitDao dao : list) {
                    TicketInfoBean tBean = new TicketInfoBean();
                    tBean.setResolutionContent(dao.getResolutionContent());
                    tBean.setFaultDesc(dao.getFaultDesc());
                    tBean.setFixPlaceFullName(dao.getFixPlaceFullName());
                    tBean.setTrainNo(dao.getTrainNo());
                    tBean.setTrainTypeShortName(dao.getTrainTypeShortName());
                    tBean.setFixPlaceFullCode(dao.getFixPlaceFullCode());
                    tBean.setReasonAnalysisID(dao.getReasonAnalysisKaoHeId() + "," + dao.getReasonAnalysisZhuanYeID());
                    tBean.setReasonAnalysis(dao.getReasonAnalysisKaoHe() + "," + dao.getReasonAnalysisZhuanYe());
                    tBean.setType(dao.getType());
                    tBean.setTrainTypeIDX(dao.getTrainTypeIDX());
                    tBean.setFaultOccurDate(dao.getFaultOccurDate());
                    beans.add(tBean);
                }
                subscriber.onNext(beans);
            }
        }).subscribeOn(Schedulers.from(JXApplication.getExecutor())).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<TicketInfoBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<TicketInfoBean> ticketInfoBeans) {
                mlist.clear();
                mUnupLoadTemp.clear();
                if (ticketInfoBeans.size() > 0) {
                    mlist.addAll(ticketInfoBeans);
                    mUnupLoadTemp.addAll(ticketInfoBeans);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void OnTicketSubmitFail(int position, String msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    /**
     * 检索提票处理记录-点击进入详情页
     *
     * @param position
     */
    @Override
    public void OnInfoClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("type", mlist.get(position).getType());
        bundle.putString("ShortName", mlist.get(position).getTrainTypeShortName());
        bundle.putString("trainTypeIDX", mlist.get(position).getTrainTypeIDX());
        bundle.putString("TrainNo", mlist.get(position).getTrainNo());
        bundle.putString("typeId", mlist.get(position).getTrainTypeIDX());
        bundle.putString("idx", mlist.get(position).getIdx());
        bundle.putString("faultFixFullName", mlist.get(position).getFixPlaceFullName());
        if (mlist.get(position).getOverRangeStatus() != null) {
            bundle.putInt("overRangeStatus", mlist.get(position).getOverRangeStatus());
        }
        String[] proId = null;
        String[] pro = null;
        if (mlist.get(position).getReasonAnalysisID() != null)
            proId = mlist.get(position).getReasonAnalysisID().split(";");
        if (mlist.get(position).getReasonAnalysis() != null)
            pro = mlist.get(position).getReasonAnalysis().split(";");
        String zhuanye = "";
        String kaohe = "";
        String zhuanyeId = "";
        String kaoheId = "";
        if (proId != null && proId.length > 0 && pro != null && pro.length > 0) {
            for (int i = 0; i < proId.length && i < pro.length; i++) {
                if (proId[i].substring(0, 2).equals("10")) {
                    if (!"".equals(zhuanye)) {
                        zhuanye = zhuanye + "," + pro[i];
                        zhuanyeId = zhuanyeId + "," + proId[i];
                    } else {
                        zhuanye = zhuanye + pro[i];
                        zhuanyeId = zhuanyeId + proId[i];
                    }

                } else {
                    if (!"".equals(kaohe)) {
                        kaohe = kaohe + "," + pro[i];
                        kaoheId = kaoheId + "," + proId[i];
                    } else {
                        kaohe = kaohe + pro[i];
                        kaoheId = kaoheId + proId[i];
                    }
                }
            }
        }
        bundle.putString("zhuanye", mlist.get(position).getReasonAnalysis());
        bundle.putString("kaohe", kaohe);
        bundle.putString("zhuanyeId", zhuanyeId);
        bundle.putString("kaoheId", kaoheId);
        bundle.putString("faultDesc", mlist.get(position).getFaultDesc());
        bundle.putString("resolutionContent", mlist.get(position).getResolutionContent());
        if (state == 2) {//当前页面Tab切换到"已保存"
            /*已保存*/
            bundle.putString("state", "2");
            bundle.putString("UUId", daos.get(position).getIdx());
            bundle.putString("faultFixPlaceIDX", mlist.get(position).getFaultFixPlaceIDX());
            bundle.putString("fixPlaceFullCode", mlist.get(position).getFixPlaceFullCode() + "");
            bundle.putLong("faultOccurDate", mlist.get(position).getFaultOccurDate());
        } else {//默认是该分支:Tab"已处理"
            /* !已保存 */
            bundle.putString("state", "3");
        }
        if (mlist.get(position).getCompleteTime() != null)
            bundle.putString("completeTime", Utils.formatTime(mlist.get(position).getCompleteTime(), "yyyy/MM/dd HH:mm"));
        if (mlist.get(position).getDispatchEmp() != null)
            bundle.putString("ManageInfoUser", mlist.get(position).getDispatchEmp());
        if (mlist.get(position).getTicketEmp() != null)
            bundle.putString("ticketEmp", mlist.get(position).getTicketEmp());
        if (mlist.get(position).getTicketTime() != null)
            bundle.putString("ticketTime", Utils.formatTime(mlist.get(position).getTicketTime(), "yyyy/MM/dd HH:mm"));
        if (mlist.get(position).getRepairResult()!=null)
            bundle.putString("repairResult", mlist.get(position).getRepairResult());

        ActivityUtil.startActivityWithDelayed(TicketManageListActivity.this, TicketInfoReadActivity.class, bundle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSubmit:
                if (mlist.size() > 0) {
                    uploads.clear();
                    for (int i = 0; i < mlist.size(); i++) {
                        if (mlist.get(i).isCheck()) {
                            uploads.add(mlist.get(i));
                        }
                    }
                    if (uploads.size() > 0) {
                        getProgressDialog().show();
                        Observable.create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {
                                mPresenter.UploadTickets(uploads);
                            }
                        }).subscribeOn(Schedulers.io()).subscribe();

                    } else {
                        ToastUtil.toastShort("请先选择需要提交的提票");
                    }
                } else {
                    ToastUtil.toastShort("没有可提交的提票");
                }

                break;
            case R.id.btAllSubmit:
                if (mlist.size() > 0) {
                    getProgressDialog().show();
                    Observable.create(new Observable.OnSubscribe<Integer>() {
                        @Override
                        public void call(Subscriber<? super Integer> subscriber) {
                            mPresenter.UploadTickets(mlist);
                        }
                    }).subscribeOn(Schedulers.io()).subscribe();
                } else {
                    ToastUtil.toastShort("没有可提交的提票");
                }
                break;
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

                            TimePickerView pvTime = new TimePickerBuilder(TicketManageListActivity.this, new OnTimeSelectListener() {
                                @Override
                                public void onTimeSelect(Date date, View v) {//选中事件回调
                                    tvStartData.setText(Utils.formatTime(date, "yyyy年MM月dd日"));
                                    tvStartData.setTag(date);
                                    startTime = date.getTime();
                                }
                            }).setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
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
                                    .setLabel("年", "月", "日", "时", "分", "秒").isDialog(true)//是否显示为对话框样式
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

                            TimePickerView pvTime = new TimePickerBuilder(TicketManageListActivity.this, new OnTimeSelectListener() {
                                @Override
                                public void onTimeSelect(Date date, View v) {//选中事件回调
                                    tvEndData.setText(Utils.formatTime(date, "yyyy年MM月dd日"));
                                    tvEndData.setTag(date);
                                    EndTime = date.getTime();
                                }
                            }).setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
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
                                    .setLabel("年", "月", "日", "时", "分", "秒").isDialog(true)//是否显示为对话框样式
                                    .build();
                            pvTime.show();
                        }
                    });
                    dialog = new AlertDialog.Builder(this).setTitle("请选择查询日期").setView(view).setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                            tvData.setText(tvStartData.getText().toString().split("年")[1] + "至" + tvEndData.getText().toString().split("年")[1]);
                            if (startTime != 0 && EndTime != 0) {
                                mlist.clear();
                                for (TicketInfoBean bean : mCompletlistTemp) {
                                    if (bean.getCreateTime() != null) {
                                        if (bean.getCreateTime() >= startTime && bean.getCreateTime() <= EndTime) {
                                            mlist.add(bean);
                                        }
                                    }

                                }
                                adapter.notifyDataSetChanged();
                                if (mlist.size() == 0) {
                                    llEmpty.setVisibility(View.VISIBLE);
                                } else {
                                    llEmpty.setVisibility(View.GONE);
                                }
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
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    if (etSearch.getText() != null && !etSearch.getText().equals("")) {
                        mlist.clear();
                        for (TicketInfoBean bean : mCompletlistTemp) {
                            if (bean.getTrainTypeShortName().contains(etSearch.getText().toString()) || bean.getTrainNo().contains(etSearch.getText().toString())) {
                                mlist.add(bean);
                            }
                        }
                        if (startTime != 0 && EndTime != 0) {
                            List<TicketInfoBean> temp = new ArrayList<>();
                            for (TicketInfoBean bean : mlist) {
                                if (bean.getCreateTime() != null) {
                                    if (bean.getCreateTime() >= startTime && bean.getCreateTime() <= EndTime) {
                                        temp.add(bean);
                                    }
                                }
                            }
                            mlist.clear();
                            if (temp.size() != 0) {
                                mlist.addAll(temp);
                            }
                        }
                        if (mlist.size() == 0) {
                            llEmpty.setVisibility(View.VISIBLE);
                        } else {
                            if (mlist.size() < limit) {
                                svList.setNoMoreData(true);
                            }
                            llEmpty.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();
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
