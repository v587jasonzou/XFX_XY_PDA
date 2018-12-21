package jx.yunda.com.terminal.modules.tpApproval;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jakewharton.rxbinding.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.base.JXApplication;
import jx.yunda.com.terminal.base.baseDictData.model.DicDataItem;
import jx.yunda.com.terminal.base.baseDictData.presenter.BaseDictPresenter;
import jx.yunda.com.terminal.modules.tpApproval.adapter.TPApprovalAdapter;
import jx.yunda.com.terminal.modules.tpApproval.model.TPApproval;
import jx.yunda.com.terminal.modules.tpApproval.presenter.ITPApproval;
import jx.yunda.com.terminal.modules.tpApproval.presenter.TPApprovalPresenter;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTypeBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;
import jx.yunda.com.terminal.utils.LogUtil;
import rx.functions.Action1;

public class TPApprovalActivity extends BaseActivity<TPApprovalPresenter> implements ITPApproval {

    TPApprovalAdapter tpApprovalAdapter;
    @BindView(R.id.tp_approval_recycler)
    RecyclerView recycler;
    @BindView(R.id.tp_approval_smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.tp_approval_search_condition)
    EditText searchCondition;

    @Override
    public TPApprovalPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new TPApprovalPresenter(this);
        return mPresenter;
    }
    String professionTypeIdx = "";
    String trainNo = "";
    String name="";
    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.tp_approval_act;
    }

    @Override
    public void initSubViews(View view) {

        tpApprovalAdapter = new TPApprovalAdapter(this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(tpApprovalAdapter);

        smartRefresh.setRefreshHeader(new ClassicsHeader(this));
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                //TODO
                initData();
            }
        });

    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            trainNo = bundle.getString("trainNo");
            professionTypeIdx = bundle.getString("professionTypeIdx");
            name = bundle.getString("name");
        }
        if(professionTypeIdx!=null){
            getPresenter().setMainPageData(trainNo,professionTypeIdx,name);
        }else {
            getPresenter().setMainPageData(searchCondition.getText().toString(),"","");
        }

    }

    @OnClick({R.id.tp_approval_back, R.id.tp_approval_search_btn})
    void viewClick(View view) {
        switch (view.getId()) {
            case R.id.tp_approval_back:
                finish();
                break;
            case R.id.tp_approval_search_btn:
                initData();
                break;
            default:
                break;
        }
    }

    @Override
    public void pageRecyclerNotifyDataSetChanged(List<FaultTicket> datas) {
        try {
            smartRefresh.finishRefresh();
            tpApprovalAdapter.setData(datas);
            tpApprovalAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            LogUtil.e("提票审批主页面列表刷新", ex.toString());
        }
    }
}