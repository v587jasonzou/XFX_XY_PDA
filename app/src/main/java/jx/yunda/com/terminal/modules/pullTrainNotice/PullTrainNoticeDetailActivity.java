package jx.yunda.com.terminal.modules.pullTrainNotice;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.pullTrainNotice.adapter.PullTrainNoticeDetailAdapter;
import jx.yunda.com.terminal.modules.pullTrainNotice.model.PullTrainNotice;
import jx.yunda.com.terminal.modules.pullTrainNotice.model.PullTrainNoticeItem;
import jx.yunda.com.terminal.modules.pullTrainNotice.presenter.IPullTrainNoticeDetail;
import jx.yunda.com.terminal.modules.pullTrainNotice.presenter.PullTrainNoticeDetailPresenter;
import jx.yunda.com.terminal.utils.LogUtil;

public class PullTrainNoticeDetailActivity extends BaseActivity<PullTrainNoticeDetailPresenter> implements IPullTrainNoticeDetail {

    PullTrainNoticeDetailAdapter adapter;
    @BindView(R.id.pull_train_notice_detail_train)
    TextView train;
    @BindView(R.id.pull_train_notice_detail_smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.pull_train_notice_detail_list)
    RecyclerView transNoticeList;
    PullTrainNotice selectPullTrainNotice;

    @Override
    protected PullTrainNoticeDetailPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new PullTrainNoticeDetailPresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.pull_train_notice_detail_act;
    }

    @Override
    public void initSubViews(View view) {
        selectPullTrainNotice = (PullTrainNotice) getIntent().getExtras().getSerializable("selectPullTrainNotice");
        if (selectPullTrainNotice != null)
            train.setText(selectPullTrainNotice.getTrainTypeShortName() + " " + selectPullTrainNotice.getTrainNo());
        adapter = new PullTrainNoticeDetailAdapter(mPresenter);
        transNoticeList.setAdapter(adapter);
        transNoticeList.setLayoutManager(new LinearLayoutManager(this));
        smartRefresh.setRefreshHeader(new ClassicsHeader(this));
        smartRefresh.setRefreshFooter(new ClassicsFooter(this));
        smartRefresh.setEnableLoadMore(false);
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
    }

    @Override
    public void initData() {
        try {
            if (selectPullTrainNotice == null || TextUtils.isEmpty(selectPullTrainNotice.getIdx()))
                return;
            mPresenter.getPullTrainDetails(selectPullTrainNotice.getIdx());
        } catch (Exception ex) {
            LogUtil.e("牵车通知单详情面列表获取，错误：", ex.toString());
        }
    }

    @Override
    public void pageNotifyNewDataSetChangedForPullTrainDetails(List<PullTrainNoticeItem> items) {
        try {
            adapter.setData(items);
            adapter.notifyDataSetChanged();
            smartRefresh.finishRefresh();
        } catch (Exception ex) {
            LogUtil.e("牵车通知单详情面列表刷新，错误：", ex.toString());
        }
    }

    @OnClick({R.id.pull_train_notice_detail_back})
    void viewClick(View view) {
        switch (view.getId()) {
            case R.id.pull_train_notice_detail_back:
                finish();
                break;
            default:
                break;
        }
    }
}
