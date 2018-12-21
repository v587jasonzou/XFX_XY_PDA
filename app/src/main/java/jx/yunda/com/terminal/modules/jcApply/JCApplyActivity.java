package jx.yunda.com.terminal.modules.jcApply;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
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
import jx.yunda.com.terminal.modules.jcApply.adapter.JCApplyAdapter;
import jx.yunda.com.terminal.modules.jcApply.model.JCApply;
import jx.yunda.com.terminal.modules.jcApply.presenter.IJCApply;
import jx.yunda.com.terminal.modules.jcApply.presenter.JCApplyPresenter;
import jx.yunda.com.terminal.utils.LogUtil;

public class JCApplyActivity extends BaseActivity<JCApplyPresenter> implements IJCApply {
    @BindView(R.id.jc_apply_tab)
    TabLayout tab;
    @BindView(R.id.jc_apply_smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.jc_apply_recycler)
    RecyclerView recycler;
    JCApplyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected JCApplyPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new JCApplyPresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.jc_apply_act;
    }

    @Override
    public void initSubViews(View view) {
        adapter = new JCApplyAdapter(this);
        adapter.setVisibleSubmitBtn(tab.getSelectedTabPosition() == 0);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                try {
                    setPageData();
                } catch (Exception ex) {
                    LogUtil.e("交车申请主页面Tag切换", ex.toString());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
        adapter.setVisibleSubmitBtn(tab.getSelectedTabPosition() == 0);
        getPresenter().setPageData(tab.getSelectedTabPosition() == 0);
    }

    @OnClick({R.id.jc_apply_back})
    void viewClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.jc_apply_back:
                    this.finish();
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            LogUtil.e("交车申请主页面", ex.toString());
        }
    }

    @Override
    public void pageRecyclerNotifyDataSetChanged(List<JCApply> datas) {
        try {
            smartRefresh.finishRefresh();
            adapter.setData(datas);
            adapter.notifyDataSetChanged();
        } catch (Exception ex) {
            LogUtil.e("交车申请主页面列表刷新", ex.toString());
        }
    }
}
