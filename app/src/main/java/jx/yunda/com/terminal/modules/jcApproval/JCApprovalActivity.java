package jx.yunda.com.terminal.modules.jcApproval;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.jcApply.model.JCApply;
import jx.yunda.com.terminal.modules.jcApply.presenter.JCApplyPresenter;
import jx.yunda.com.terminal.modules.jcApproval.adapter.JCApprovalAdapter;
import jx.yunda.com.terminal.modules.jcApproval.model.JCApproval;
import jx.yunda.com.terminal.modules.jcApproval.presenter.IJCApproval;
import jx.yunda.com.terminal.modules.jcApproval.presenter.JCApprovalPresenter;
import jx.yunda.com.terminal.utils.LogUtil;

public class JCApprovalActivity extends BaseActivity<JCApprovalPresenter> implements IJCApproval {

    JCApprovalAdapter jcApprovalAdapter;
    @BindView(R.id.jc_approval_recycler)
    RecyclerView recycler;
    @BindView(R.id.jc_approval_smart_refresh)
    SmartRefreshLayout smartRefresh;

    @Override
    protected JCApprovalPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new JCApprovalPresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.jc_approval_act;
    }

    @Override
    public void initSubViews(View view) {
        jcApprovalAdapter = new JCApprovalAdapter(this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(jcApprovalAdapter);

        smartRefresh.setRefreshHeader(new ClassicsHeader(this));
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                setPageData();
            }
        });
    }

    @Override
    public void initData() {
        setPageData();
    }

    public void setPageData() {
        jcApprovalAdapter.setVisibleSubmitBtn(true);
        getPresenter().setPageData(false);
    }

    @OnClick({R.id.jc_approval_back})
    void viewClick(View view) {
        switch (view.getId()) {
            case R.id.jc_approval_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void pageRecyclerNotifyDataSetChanged(List<JCApproval> datas) {
        try {
            smartRefresh.finishRefresh();
            jcApprovalAdapter.setData(datas);
            jcApprovalAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            LogUtil.e("交车审批主页面列表刷新", ex.toString());
        }
    }
}
