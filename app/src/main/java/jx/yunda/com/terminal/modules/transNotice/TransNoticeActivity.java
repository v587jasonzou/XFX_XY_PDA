package jx.yunda.com.terminal.modules.transNotice;

import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.entity.BaseResultEntity;
import jx.yunda.com.terminal.modules.transNotice.adapter.TransNoticeSwipeMenuAdapter;
import jx.yunda.com.terminal.modules.transNotice.fragment.TransNoticeEditFragment;
import jx.yunda.com.terminal.modules.transNotice.model.TransNotice;
import jx.yunda.com.terminal.modules.transNotice.presenter.ITransNotice;
import jx.yunda.com.terminal.modules.transNotice.presenter.TransNoticePresenter;
import jx.yunda.com.terminal.utils.LogUtil;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

public class TransNoticeActivity extends BaseActivity<TransNoticePresenter> implements ITransNotice {

    TransNoticeSwipeMenuAdapter adapter;
    @BindView(R.id.trans_notice_smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.trans_notice_list)
    SwipeMenuListView transNoticeList;

    public TransNotice selectTransNotice;

    public String msgGroupId;

    @Override
    protected TransNoticePresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new TransNoticePresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.trans_notice_act;
    }

    @Override
    public void initSubViews(View view) {
        initTransNoticeList();
        smartRefresh.setRefreshHeader(new ClassicsHeader(this));
        smartRefresh.setRefreshFooter(new ClassicsFooter(this));
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                setMainPageList(0);
            }
        });
        smartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                setMainPageList(adapter.getCount());
            }
        });
        //显示操作页面
        showEditFragment();
    }

    private void initTransNoticeList() {
        adapter = new TransNoticeSwipeMenuAdapter(this);
        transNoticeList.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                TransNotice model = adapter.getData().get(menu.getViewType());
                if ("0".equals(model.getSubmitStatus())) {
                    SwipeMenuItem submitItem = new SwipeMenuItem(TransNoticeActivity.this);
                    submitItem.setBackground(R.color.acivity_color);
                    submitItem.setWidth(dp2px(90));
                    submitItem.setTitle("提交");
                    submitItem.setTitleSize(18);
                    submitItem.setTitleColor(Color.WHITE);
                    menu.addMenuItem(submitItem);
                    SwipeMenuItem removeItem = new SwipeMenuItem(TransNoticeActivity.this);
                    removeItem.setBackground(R.color.btn_remove);
                    removeItem.setWidth(dp2px(90));
                    removeItem.setTitle("移除");
                    removeItem.setTitleSize(18);
                    removeItem.setTitleColor(Color.WHITE);
                    menu.addMenuItem(removeItem);
                }
            }
        };
        transNoticeList.setMenuCreator(creator);
        transNoticeList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                boolean rst = true;
                try {
                    switch (index) {
                        case 0:
                            getPresenter().submitNotice(adapter.getItem(position), position);
                            break;
                        case 1:
                            getPresenter().deleteNotice(adapter.getItem(position).getIdx(), position);
                            break;
                        default:
                            break;
                    }
                } catch (Exception ex) {

                }
                return rst;
            }
        });
    }

    @Override
    public void initData() {
        //setMainPageList(0);
    }

    public void setMainPageList(int start) {
        //TODO
        selectTransNotice = null;
        if (start == 0) {
            initTransNoticeList();
        }
        getPresenter().getNotice(start);
    }

    @OnClick({R.id.trans_notice_back, R.id.trans_notice_add})
    void viewClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.trans_notice_back:
                    showEditFragment();
                    break;
                case R.id.trans_notice_add:
                    showEditFragment();
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            LogUtil.e("调车通知单主页面列表刷新", ex.toString());
        }
    }

    private void showEditFragment() {
        selectTransNotice = null;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        TransNoticeEditFragment snAddFragment = new TransNoticeEditFragment();
        snAddFragment.initPage(msgGroupId, null);
        transaction.add(R.id.trans_notice_panel, snAddFragment);
        transaction.commit();
    }

    @Override
    public void pageListNotifyDataSetChanged(String msgGroupId, List<TransNotice> datas, int total) {
        try {
            this.msgGroupId = msgGroupId;
            boolean isLoadAll = total == adapter.getCount();
            if (isLoadAll) {
                smartRefresh.setNoMoreData(isLoadAll);
            } else {
                adapter.getData().addAll(datas);
                adapter.notifyDataSetChanged();
            }
            smartRefresh.finishLoadMore();
            smartRefresh.finishRefresh();
        } catch (Exception ex) {
            LogUtil.e("调车通知单主页面列表刷新", ex.toString());
        }
    }

    @Override
    public void pageListRemoveItem(int position) {
        try {
            List<TransNotice> tempList = adapter.getData();
            tempList.remove(position);
            initTransNoticeList();
            adapter.setData(tempList);
            adapter.notifyDataSetChanged();
        } catch (Exception ex) {
            LogUtil.e("调车通知单主页面移除后刷新页面", ex.toString());
        }
    }

    @Override
    public void pageListUpdateItem(TransNotice transNotice, int position) {
        try {
            TransNotice tn = adapter.getData().get(position);
            tn.setSubmitStatus(transNotice.getSubmitStatus());
            adapter.notifyDataSetChanged();
        } catch (Exception ex) {
            LogUtil.e("调车通知单主页面移除后刷新页面", ex.toString());
        }
    }
}
