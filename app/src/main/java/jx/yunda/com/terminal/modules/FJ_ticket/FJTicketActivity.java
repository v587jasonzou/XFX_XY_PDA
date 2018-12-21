package jx.yunda.com.terminal.modules.FJ_ticket;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

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
import jx.yunda.com.terminal.modules.FJ_ticket.adapter.RecheckTrainAdapter;
import jx.yunda.com.terminal.modules.FJ_ticket.model.RecheckTrainBean;
import jx.yunda.com.terminal.modules.FJ_ticket.presenter.CheckListActivity;
import jx.yunda.com.terminal.modules.FJ_ticket.presenter.IMakeTicketRecheck;
import jx.yunda.com.terminal.modules.FJ_ticket.presenter.MakeTicketRecheckPresenter;
import jx.yunda.com.terminal.modules.TrainRecandition.model.JXTrainBean;
import jx.yunda.com.terminal.modules.tpprocess.TP_ProcessActivity;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTrainBean;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class FJTicketActivity extends BaseActivity<MakeTicketRecheckPresenter> implements AdapterView.OnItemClickListener, View.OnClickListener
        , IMakeTicketRecheck {


    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvMesaageNo)
    TextView tvMesaageNo;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.gvCar)
    GridView gvCar;
    @BindView(R.id.sv_ticketTrain)
    SmartRefreshLayout svTicketTrain;
    @BindView(R.id.btNext)
    Button btNext;
    RecheckTrainAdapter adapter;
    List<RecheckTrainBean> mList = new ArrayList<>();
    List<RecheckTrainBean> mListSearch = new ArrayList<>();
    int trainPosition;
    @BindView(R.id.tb_title)
    TabLayout tbTitle;
    @BindView(R.id.btSubmit)
    Button btSubmit;
    String status = "未提交";

    @Override
    protected MakeTicketRecheckPresenter getPresenter() {
        return new MakeTicketRecheckPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_fj_ticket;
    }

    @Override
    public void initSubViews(View view) {

    }

    @Override
    public void initData() {
        //设置toolbar
        adapter = new RecheckTrainAdapter(this, mList);
        gvCar.setAdapter(adapter);
        gvCar.setOnItemClickListener(this);
//        gvCar.getBackground().setAlpha(100);
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshTrain();
        btNext.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
        tvMesaageNo.setOnClickListener(this);
        svTicketTrain.setRefreshHeader(new ClassicsHeader(this));
        svTicketTrain.setRefreshFooter(new ClassicsFooter(this));
        svTicketTrain.setNoMoreData(true);
        svTicketTrain.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshTrain();
            }
        });
        svTicketTrain.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                refreshTrain();
            }
        });
        tbTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        status = "未提交";
                        getProgressDialog().show();
                        refreshTrain();
                        btNext.setBackground(ContextCompat.getDrawable(FJTicketActivity.this,R.drawable.button_selector_gray));
                        btNext.setEnabled(false);
                        btSubmit.setBackground(ContextCompat.getDrawable(FJTicketActivity.this,R.drawable.button_selector_gray));
                        btSubmit.setEnabled(false);
                        btSubmit.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        status = "已提交";
                        refreshTrain();
                        btNext.setBackground(ContextCompat.getDrawable(FJTicketActivity.this,R.drawable.button_selector_gray));
                        btNext.setEnabled(false);
                        btSubmit.setBackground(ContextCompat.getDrawable(FJTicketActivity.this,R.drawable.button_selector_gray));
                        btSubmit.setEnabled(false);
                        btSubmit.setVisibility(View.GONE);
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
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btNext.setBackground(ContextCompat.getDrawable(FJTicketActivity.this,R.drawable.button_selector_gray));
                btNext.setEnabled(false);
                btSubmit.setBackground(ContextCompat.getDrawable(FJTicketActivity.this,R.drawable.button_selector_gray));
                btSubmit.setEnabled(false);
                s = s.toString().toUpperCase();
                if (mListSearch.size() > 0) {
                    if ("车型、车号".equals(s.toString()) || "".equals(s.toString())) {
                        mList.clear();
                        for (RecheckTrainBean bean : mListSearch) {
                            if (bean.getState() == 1) {
                                bean.setState(0);
                            }
                        }
                        mList.addAll(mListSearch);
                        adapter.notifyDataSetChanged();
                    } else {
                        mList.clear();
                        for (RecheckTrainBean bean : mListSearch) {
                            if (bean.getState() == 1) {
                                bean.setState(0);
                            }
                        }
                        for (int i = 0; i < mListSearch.size(); i++) {
                            if (mListSearch.get(i).getTrainNo().contains(s.toString()) ||
                                    mListSearch.get(i).getTrainTypeShortName().contains(s.toString())) {
                                mList.add(mListSearch.get(i));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void refreshTrain() {
        getProgressDialog().show();
        mPresenter.getTicketTrains("", "0", "1000", status, "10", "",
                SysInfo.userInfo.emp.getEmpid() + "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btNext:
                Bundle bundle = new Bundle();
                bundle.putString("trainTypeShortName", mList.get(trainPosition).getTrainTypeShortName());
                bundle.putString("trainTypeIDX", mList.get(trainPosition).getTrainIDX());
                bundle.putString("trainNo", mList.get(trainPosition).getTrainNo());
                bundle.putString("ticketTrainIDX",mList.get(trainPosition).getIdx());
                ActivityUtil.startActivityResultWithDelayed(this, CheckListActivity.class, bundle, 201);
                break;
            case R.id.btSubmit:
                getProgressDialog().show();
                Map<String,String> map = new HashMap<>();
                map.put("trainTypeShortName",mList.get(trainPosition).getTrainTypeShortName());
                map.put("trainNo",mList.get(trainPosition).getTrainNo());
                map.put("trainIDX",mList.get(trainPosition).getTrainIDX());
                map.put("trainRepaire",mList.get(trainPosition).getTrainRepaire());
                map.put("recheckStatus","10");
                map.put("idx",mList.get(trainPosition).getIdx());
                mPresenter.Submit(JSON.toJSONString(map),"10",SysInfo.userInfo.emp.getEmpid()+"");
                break;
        }

    }

    @Override
    public void LoadTrainListSuccess(List<RecheckTrainBean> beans) {
        getProgressDialog().dismiss();
        svTicketTrain.finishRefresh();
        svTicketTrain.finishLoadMore();
        mList.clear();
        mListSearch.clear();
        if (beans != null && beans.size() > 0) {
            mListSearch.addAll(beans);
            mList.addAll(beans);
        } else {
            Toast.makeText(this, "无在修机车数据", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void LoadMoreTrainListSuccess(List<RecheckTrainBean> beans) {
        getProgressDialog().dismiss();
        svTicketTrain.finishRefresh();
        svTicketTrain.finishLoadMore();
        mList.clear();
        mListSearch.clear();
        if (beans != null && beans.size() > 0) {
            mListSearch.addAll(beans);
            mList.addAll(beans);
        } else {
            Toast.makeText(this, "无在修机车数据", Toast.LENGTH_SHORT).show();
        }
        mListSearch.clear();
    }

    @Override
    public void OnLoadFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort("获取在修机车列表失败:" + msg);
    }

    @Override
    public void OnSubmitSuccess() {
        getProgressDialog().dismiss();
        refreshTrain();
    }

    @Override
    public void OnSubmitFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mList.size() > 0) {
            for (int i = 0; i < mList.size(); i++) {
                if (i == position) {
                    mList.get(i).setState(1);
                } else {
                    mList.get(i).setState(0);
                }
            }
            trainPosition = position;
        }
        adapter.notifyDataSetChanged();
        btNext.setBackground(ContextCompat.getDrawable(this, R.drawable.button_selector));
        btNext.setEnabled(true);
        btSubmit.setBackground(ContextCompat.getDrawable(this, R.drawable.button_selector));
        btSubmit.setEnabled(true);
    }

}
