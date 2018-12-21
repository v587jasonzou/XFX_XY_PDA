package jx.yunda.com.terminal.modules.specialApproval;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.specialApproval.adapter.SpecialApprovalAdapter;
import jx.yunda.com.terminal.modules.specialApproval.model.SpecialApproval;
import jx.yunda.com.terminal.modules.specialApproval.presenter.ISpecialApproval;
import jx.yunda.com.terminal.modules.specialApproval.presenter.SpecialApprovalPresenter;
import jx.yunda.com.terminal.utils.LogUtil;

public class SpecialApprovalActivity extends BaseActivity<SpecialApprovalPresenter> implements ISpecialApproval {

    SpecialApprovalAdapter specialApprovalAdapter;
    @BindView(R.id.special_approval_recycler)
    RecyclerView recycler;
    @BindView(R.id.special_approval_smart_refresh)
    SmartRefreshLayout smartRefresh;

    @BindView(R.id.special_approval_search_condition)
    EditText searchCondition;

    @Override
    protected SpecialApprovalPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new SpecialApprovalPresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.special_approval_act;
    }

    @Override
    public void initSubViews(View view) {
        specialApprovalAdapter = new SpecialApprovalAdapter(this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(specialApprovalAdapter);
        smartRefresh.setRefreshHeader(new ClassicsHeader(this));
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
    }

    @Override
    public void initData() {
        getPresenter().getApprovalPageData(searchCondition.getText().toString());
    }

    @OnClick({R.id.special_approval_back, R.id.special_approval_search_btn})
    void viewClick(View view) {
        switch (view.getId()) {
            case R.id.special_approval_back:
                finish();
                break;
            case R.id.special_approval_search_btn:
                initData();
                break;
            default:
                break;
        }
    }

    @Override
    public void pageRecyclerNotifyDataSetChanged(List<SpecialApproval> datas) {
        try {
            smartRefresh.finishRefresh();
            specialApprovalAdapter.setData(datas);
            specialApprovalAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            LogUtil.e("例外放行审批主页面列表刷新", ex.toString());
        }
    }
}
