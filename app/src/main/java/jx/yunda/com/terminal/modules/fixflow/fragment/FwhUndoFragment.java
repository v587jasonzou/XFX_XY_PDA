package jx.yunda.com.terminal.modules.fixflow.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.modules.fixflow.activity.FlowInfoMainActivity;
import jx.yunda.com.terminal.modules.fixflow.adapter.FwhListAdapter;
import jx.yunda.com.terminal.modules.fixflow.entry.FwhInfoBean;
import jx.yunda.com.terminal.modules.fixflow.presenter.getFwhListPresenter;
import jx.yunda.com.terminal.modules.fixflow.presenter.iGetFwhList;
import jx.yunda.com.terminal.utils.ToastUtil;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class FwhUndoFragment extends BaseFragment<getFwhListPresenter> implements iGetFwhList {
    int start = 0;
    int limit = 8;
    String orgId = "";
    @BindView(R.id.lvfwhUndo)
    ListView lvfwhUndo;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    Unbinder unbinder;
    FwhListAdapter adapter;
    public static List<FwhInfoBean> mList = new ArrayList<>();
    @Override
    protected getFwhListPresenter getPresenter() {
        return new getFwhListPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_fwh_undo_detail;
    }

    @Override
    public void initSubViews(View view) {

    }

    boolean isLoadMore = false;
    FlowInfoMainActivity activity;
    @Override
    public void initData() {
        activity = (FlowInfoMainActivity)getActivity();
        srRefresh.setRefreshHeader(new ClassicsHeader(activity));
        srRefresh.setRefreshFooter(new ClassicsFooter(activity));
        srRefresh.setEnableAutoLoadMore(false);
        srRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                activity.getProgressDialog().show();
                start = 0;
                isLoadMore = false;
                srRefresh.setNoMoreData(false);
                mPresenter.getFwhList(FlowInfoMainActivity.idx, "0",FlowInfoMainActivity.orgId, start, limit, isLoadMore);
            }
        });
        srRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                activity.getProgressDialog().show();
                start = start+limit;
                isLoadMore = true;
                mPresenter.getFwhList(FlowInfoMainActivity.idx, "0",FlowInfoMainActivity.orgId, start, limit, isLoadMore);
            }
        });
        adapter = new FwhListAdapter(activity,mList);
        lvfwhUndo.setAdapter(adapter);
        activity.getProgressDialog().show();
        mPresenter.getFwhList(FlowInfoMainActivity.idx, "0",FlowInfoMainActivity.orgId, start, limit, isLoadMore);
    }

    @Override
    public void LoadFwhListSuccess(List<FwhInfoBean> list) {
        activity.getProgressDialog().dismiss();
        mList.clear();
        if(list!=null&&list.size()>0){
            mList.addAll(list);
        }
        srRefresh.finishRefresh();
        if(list==null||list.size()<limit){
            srRefresh.setNoMoreData(true);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void LoadMoreFwhListSuccess(final List<FwhInfoBean> list) {
        srRefresh.finishLoadMore();
        activity.getProgressDialog().dismiss();
        if(list!=null&&list.size()>0){
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
                    srRefresh.setNoMoreData(true);
                } else {
                    srRefresh.setNoMoreData(false);
                }
            }
        });
    }

    @Override
    public void LoadFaild(String msg) {
        ToastUtil.toastShort(msg);
        activity.getProgressDialog().dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    public void Refresh(){
        activity.getProgressDialog().show();
        start = 0;
        isLoadMore = false;
        srRefresh.setNoMoreData(false);
        mPresenter.getFwhList(FlowInfoMainActivity.idx, "0",FlowInfoMainActivity.orgId, start, limit, isLoadMore);
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
