package jx.yunda.com.terminal.modules.confirmNotify.act;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.confirmNotify.adapter.NotifyListAdapter;
import jx.yunda.com.terminal.modules.confirmNotify.entity.NotifyListBean;
import jx.yunda.com.terminal.modules.confirmNotify.presenter.GetNotifyPresenter;
import jx.yunda.com.terminal.modules.confirmNotify.presenter.IGetNotify;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.DateText;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class NotifyActivity extends BaseActivity<GetNotifyPresenter> implements IGetNotify {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.lvNotifyList)
    ListView lvNotifyList;
    @BindView(R.id.svList)
    SmartRefreshLayout svList;
    @BindView(R.id.llEmpty)
    LinearLayout llEmpty;
    boolean isLoadmore = false;
    List<NotifyListBean> mList = new ArrayList<>();
    int start = 0;
    int limit = 8;
    String starttime = "";
    String endtime = "";
    NotifyListAdapter adapter;
    @BindView(R.id.tvStatrtTime)
    DateText tvStatrtTime;
    @BindView(R.id.tvEndTime)
    DateText tvEndTime;

    @Override
    protected GetNotifyPresenter getPresenter() {
        return new GetNotifyPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_get_notify_main;
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
        svList.setRefreshHeader(new ClassicsHeader(this));
        svList.setRefreshFooter(new ClassicsFooter(this));
        svList.setEnableAutoLoadMore(false);
        svList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                start = 0;
                isLoadmore = false;
                LoadData();
            }
        });
        svList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                start = start + limit;
                isLoadmore = true;
                LoadData();
            }
        });
        adapter = new NotifyListAdapter(this, mList);
        lvNotifyList.setAdapter(adapter);
        adapter.setOnItemclickListner(new NotifyListAdapter.OnItemclickListner() {
            @Override
            public void OnItemClick(int position) {
                Bundle bundle  = new Bundle();
                NotifyListBean bean = mList.get(position);
                if(bean!=null){
                    bundle.putSerializable("notify",bean);
                }
                ActivityUtil.startActivity(NotifyActivity.this,CofirmNotifyActivity.class,bundle);
            }
        });
        LoadData();
    }

    @Override
    public void OnLoadNotifySuccess(List<NotifyListBean> list) {
        getProgressDialog().dismiss();
        svList.finishRefresh();
        ToastUtil.toastShort("加载成功");
        mList.clear();
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        adapter.notifyDataSetChanged();
        if (list.size() < limit) {
            svList.setNoMoreData(true);
        } else {
            svList.setNoMoreData(false);
        }
    }

    @Override
    public void OnLoadNotifyFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void OnLoadMoreNotifySuccess(final List<NotifyListBean> list) {
        getProgressDialog().dismiss();
        svList.finishLoadMore();
        if (list != null) {
            mList.addAll(list);
        }
        adapter.notifyDataSetChanged();
        Observable.timer(1500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
                if (list.size() < limit) {
                    svList.setNoMoreData(true);
                } else {
                    svList.setNoMoreData(false);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void LoadData() {
        getProgressDialog().show();
        mPresenter.getNotifyList(start, limit, starttime, endtime, isLoadmore);
    }
}
