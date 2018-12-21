package jx.yunda.com.terminal.modules.foremandispatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.common.IntConstants;
import jx.yunda.com.terminal.modules.foremandispatch.adapter.ForemanDispatchHisFwhListAdapter;
import jx.yunda.com.terminal.modules.foremandispatch.adapter.ForemanDispatchHisTicketListAdapter;
import jx.yunda.com.terminal.modules.foremandispatch.model.FwFormenBean;
import jx.yunda.com.terminal.modules.foremandispatch.presenter.ForemanDispatchHisPresenter;
import jx.yunda.com.terminal.modules.foremandispatch.presenter.IForemanDispatchList;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.WorkshopTaskDispatchFwhActivity;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.WorkshopTaskDispatchTicketActivity;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.adapter.WorkshopTaskDispatchHisFwhListAdapter;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.adapter.WorkshopTaskDispatchHisTicketListAdapter;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FwhBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter.IWorkshopTaskDispatchList;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter.OnViewClickInItemListner;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter.WorkshopTaskDispatchListPresenter;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.SizeConvert;
import jx.yunda.com.terminal.utils.ToastUtil;

public class ForemanDispatchHisActivity extends BaseActivity<ForemanDispatchHisPresenter>
        implements OnViewClickInItemListner, IForemanDispatchList {

    private static int ACT_REQUEST_CODE_FWH = 4001;
    private static int ACT_REQUEST_CODE_TICKET = 4002;

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tb_title)
    TabLayout tbTitle;
    @BindView(R.id.trainInfo)
    TextView trainInfo;
    @BindView(R.id.ti0_unComplet)
    TextView ti0UnComplet;
    @BindView(R.id.ti1_unComplet)
    TextView ti1UnComplet;
    @BindView(R.id.lvTicketList)
    ListView lvTicketList;
    @BindView(R.id.svList)
    SmartRefreshLayout svList;
    @BindView(R.id.llEmpty)
    LinearLayout llEmpty;

    ForemanDispatchHisTicketListAdapter ticketAdapter;
    ForemanDispatchHisFwhListAdapter fwhAdapter;
    List<FaultTicket> mList = new ArrayList<>();
    List<FwFormenBean> fwhList = new ArrayList<>();

    String workPlanIdx;
    String trainNo;
    String trainType;
    int start = 0;

    int tabPosition = 0;

    @Override
    protected ForemanDispatchHisPresenter getPresenter() {
        return new ForemanDispatchHisPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_foremandispatch_his;
    }

    @Override
    public void initSubViews(View view) {

    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            workPlanIdx = getIntent().getStringExtra("workPlanIdx");
            trainNo = getIntent().getStringExtra("trainNo");
            trainType = getIntent().getStringExtra("trainTypeShortName");
        }

        //设置toolbar
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tabPosition = 0;
        tbTitle.getTabAt(0).select();

        trainInfo.setText(trainType + " " + trainNo);

        ticketAdapter = new ForemanDispatchHisTicketListAdapter(this, mList);
        ticketAdapter.setOnViewClickListner(this);

        fwhAdapter = new ForemanDispatchHisFwhListAdapter(this, fwhList);
        fwhAdapter.setOnViewClickListner(this);

        lvTicketList.setAdapter(fwhAdapter);


        //设置 Header 为 贝塞尔雷达 样式
        svList.setRefreshHeader(new ClassicsHeader(this));
        //设置 Footer 为 球脉冲 样式
        svList.setRefreshFooter(new ClassicsFooter(this));
        svList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshListData(true);
            }
        });
        svList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                start = start + IntConstants.PAGE_SIZE;
                refreshListData(false);
            }
        });
        tbTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tabPosition = 0;
                        refreshListData(true);

                        break;
                    case 1:
                        tabPosition = 1;
                        refreshListData(true);

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

        llEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llEmpty.setVisibility(View.GONE);
                refreshListData(true);
            }
        });

        firstLoad();
    }

    private void refreshListData(boolean refresh) {
        int type = 1;

        if (refresh) {
            start = 0;
            type = 0;
            getProgressDialog().show();
        }

        if (0 == tabPosition) {
            lvTicketList.setAdapter(fwhAdapter);

            mPresenter.getFwhList(start, IntConstants.PAGE_SIZE, workPlanIdx, type);

            ti0UnComplet.setBackgroundResource(R.drawable.circle_blue_bg);
            ti1UnComplet.setBackgroundResource(R.drawable.circle_grey_bg);
            ti0UnComplet.setPadding(SizeConvert.dip2px(3f), 0, SizeConvert.dip2px(3f), 0);
            ti1UnComplet.setPadding(SizeConvert.dip2px(3f), 0, SizeConvert.dip2px(3f), 0);
        } else if (1 == tabPosition) {
            lvTicketList.setAdapter(ticketAdapter);

            Map<String, String> param = new HashMap<>();
            param.put("workPlanIDX", workPlanIdx);
            mPresenter.getTicketList(start, IntConstants.PAGE_SIZE,
                    SysInfo.userInfo.emp.getOperatorid().toString(), "true", JSON.toJSONString(param), type);

            ti0UnComplet.setBackgroundResource(R.drawable.circle_grey_bg);
            ti1UnComplet.setBackgroundResource(R.drawable.circle_blue_bg);
            ti0UnComplet.setPadding(SizeConvert.dip2px(3f), 0, SizeConvert.dip2px(3f), 0);
            ti1UnComplet.setPadding(SizeConvert.dip2px(3f), 0, SizeConvert.dip2px(3f), 0);
        }
    }

    private void firstLoad() {
        ti0UnComplet.setBackgroundResource(R.drawable.circle_blue_bg);
        ti1UnComplet.setBackgroundResource(R.drawable.circle_grey_bg);
        ti0UnComplet.setPadding(SizeConvert.dip2px(3f), 0, SizeConvert.dip2px(3f), 0);
        ti1UnComplet.setPadding(SizeConvert.dip2px(3f), 0, SizeConvert.dip2px(3f), 0);

        getProgressDialog().show();
        start = 0;

        lvTicketList.setAdapter(fwhAdapter);
        mPresenter.getFwhList(start, IntConstants.PAGE_SIZE, workPlanIdx, 1);

        Map<String, String> param = new HashMap<>();
        param.put("workPlanIDX", workPlanIdx);
        mPresenter.getTicketList(start, IntConstants.PAGE_SIZE,
                SysInfo.userInfo.emp.getOperatorid().toString(), "true", JSON.toJSONString(param), 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onTicketListLoadSuccess(List<FaultTicket> list, int total) {
        getProgressDialog().dismiss();

        mList.clear();
        mList.addAll(list);

        svList.finishRefresh();
        svList.setNoMoreData(total <= start + list.size());
        ticketAdapter.notifyDataSetChanged();

        if (1 == tabPosition) {
            if (mList.size() <= 0) {
                llEmpty.setVisibility(View.VISIBLE);
                svList.setVisibility(View.GONE);
            } else {
                llEmpty.setVisibility(View.GONE);
                svList.setVisibility(View.VISIBLE);
            }
        }

        ti1UnComplet.setText(total + "");
    }

    @Override
    public void onTicketListLoadMoreSuccess(List<FaultTicket> list, int total) {
        getProgressDialog().dismiss();

        mList.addAll(list);

        svList.finishLoadMore();
        svList.setNoMoreData(total <= start + list.size());
        ticketAdapter.notifyDataSetChanged();

        ti1UnComplet.setText(total + "");
    }

    @Override
    public void onFwhListLoadSuccess(List<FwFormenBean> list, int total) {
        getProgressDialog().dismiss();

        fwhList.clear();
        fwhList.addAll(list);

        svList.finishRefresh();
        svList.setNoMoreData(total <= start + list.size());
        fwhAdapter.notifyDataSetChanged();

        if (0 == tabPosition) {
            if (fwhList.size() <= 0) {
                llEmpty.setVisibility(View.VISIBLE);
                svList.setVisibility(View.GONE);
            } else {
                llEmpty.setVisibility(View.GONE);
                svList.setVisibility(View.VISIBLE);
            }
        }

        ti0UnComplet.setText(total + "");
    }

    @Override
    public void onFwhListLoadMoreSuccess(List<FwFormenBean> list, int total) {
        getProgressDialog().dismiss();

        fwhList.addAll(list);

        svList.finishLoadMore();
        svList.setNoMoreData(total <= start + list.size());
        fwhAdapter.notifyDataSetChanged();

        ti0UnComplet.setText(total + "");
    }

    @Override
    public void onLoadFail(String msg) {
        getProgressDialog().dismiss();

        svList.finishRefresh();
        svList.finishLoadMore();
        svList.setNoMoreData(true);

        ToastUtil.toastShort(msg);
    }

    /**
     * 提票活列表点击事件响应接口
     * @param position
     */
    @Override
    public void onViewClick(View view, int position) {
        switch (view.getId()){
            case R.id.llInfo_fwh:
                goToDispatch(position);

                break;
            case R.id.ll_redispatch_fwh:
                goToDispatch(position);

                break;
            case R.id.llInfo:
                goToDispatch(position);

                break;
            case R.id.ll_redispatch:
                goToDispatch(position);

                break;
        }
    }

    private void goToDispatch(int position) {
        if (0 == tabPosition) {
            Bundle params = new Bundle();
            params.putSerializable("fwhBean", fwhList.get(position));
            ActivityUtil.startActivityForResult(this, ForemanDispatchFwhActivity.class, ACT_REQUEST_CODE_FWH, params);
        } else if (1 == tabPosition) {
            Bundle params = new Bundle();
            params.putSerializable("ticketBean", mList.get(position));
            ActivityUtil.startActivityForResult(this, ForemanDispatchTicketActivity.class, ACT_REQUEST_CODE_TICKET, params);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null && data.getBooleanExtra("dispatchSuccess", false)) {
            refreshListData(true);
        }
        /*
        if (resultCode == Activity.RESULT_OK && requestCode == ACT_REQUEST_CODE_FWH) {
            refreshListData(true);
        } else if (resultCode == Activity.RESULT_OK && requestCode == ACT_REQUEST_CODE_TICKET) {
            refreshListData(true);
        }
        */
    }

    @Override
    public void onDispatchSuccess(String msg) {
        ToastUtil.toastSuccess();
        refreshListData(true);
    }

    @Override
    public void onDispatchFail(String msg) {
        ToastUtil.toastShort(msg);
    }
}
