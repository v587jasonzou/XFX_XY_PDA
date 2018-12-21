package jx.yunda.com.terminal.modules.receiveTrain;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.jakewharton.rxbinding.view.RxView;
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
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.receiveTrain.adapter.ReceivedTrainAdapter;

import jx.yunda.com.terminal.modules.receiveTrain.model.ReceiveTrainNotice;
import jx.yunda.com.terminal.modules.receiveTrain.model.ReceivedTrain;
import jx.yunda.com.terminal.modules.receiveTrain.presenter.IReceiveTrain;
import jx.yunda.com.terminal.modules.receiveTrain.presenter.ReceiveTrainPresenter;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ReceiveTrainActivity extends BaseActivity<ReceiveTrainPresenter> implements IReceiveTrain {
    ReceivedTrainAdapter adapter;
    @BindView(R.id.receive_train_recycler)
    RecyclerView recycler;
    @BindView(R.id.receive_train_back)
    ImageView receiveTrainBack;
    @BindView(R.id.receive_train_search_condition)
    EditText receiveTrainSearchCondition;
    @BindView(R.id.tp_approval_search_btn)
    Button tpApprovalSearchBtn;
    @BindView(R.id.receive_train_smart_refresh)
    SmartRefreshLayout receiveTrainSmartRefresh;
    @BindView(R.id.receive_train_panel)
    FrameLayout receiveTrainPanel;
    int start = 0;
    int limit = 10;
    boolean isLoadMore = false;
    List<ReceivedTrain> mList = new ArrayList<>();
    @Override
    protected ReceiveTrainPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new ReceiveTrainPresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.receive_train_act;
    }

    @Override
    public void initSubViews(View view) {
        adapter = new ReceivedTrainAdapter(this,mList);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        receiveTrainSmartRefresh.setRefreshFooter(new ClassicsFooter(this));
        receiveTrainSmartRefresh.setRefreshHeader(new ClassicsHeader(this));
        receiveTrainSmartRefresh.setEnableAutoLoadMore(false);
        receiveTrainSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                start = 0;
                isLoadMore = false;
                loadData();
            }
        });
        receiveTrainSmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                start = start + limit;
                isLoadMore = true;
                loadData();
            }
        });

    }

    String idx ;
    @Override
    public void initData() {
        loadData();
    }
    private void loadData(){
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            boolean ismessage = bundle.getBoolean("ismessge");
            if(ismessage){
                idx = bundle.getString("idx");
                getProgressDialog().show();
                Map<String, Object> map = new HashMap<>();
                map.put("acceptStatus", 0);
                mPresenter.getReceiveTrainList(JSON.toJSONString(map));
            }
        }

        Map<String,Object> map = new HashMap<>();
        map.put("workPlanStatus","INITIALIZE,ONGOING");
        mPresenter.GetTrainList(start,limit, JSON.toJSONString(map),isLoadMore);
    }
    @Override
    public void pageRecyclerNotifyDataSetChanged(List<ReceivedTrain> datas) {

    }

    @Override
    public void OnLoadTrainsSuccess(List<ReceivedTrain> list) {
        receiveTrainSmartRefresh.finishRefresh();
        mList.clear();
        if(list!=null&&list.size()>0){
            mList.addAll(list);
        }
        adapter.notifyDataSetChanged();
        if (list.size() < limit) {
            receiveTrainSmartRefresh.setNoMoreData(true);
        } else {
            receiveTrainSmartRefresh.setNoMoreData(false);
        }
    }

    @Override
    public void OnLoadMoreTrainsSuccess(final List<ReceivedTrain> list) {
        receiveTrainSmartRefresh.finishLoadMore();
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
                    receiveTrainSmartRefresh.setNoMoreData(true);
                } else {
                    receiveTrainSmartRefresh.setNoMoreData(false);
                }
            }
        });
    }

    @Override
    public void OnLoadFaild(String msg) {
        ToastUtil.toastShort(msg);
        getProgressDialog().dismiss();
    }

    @Override
    public void OnLoadTrainListSuccess(List<ReceiveTrainNotice> list) {
        if(list!=null&&list.size()>0){
            for(ReceiveTrainNotice bean:list){
                if(bean.getIdx().equals(idx)){
                    getProgressDialog().dismiss();
                    break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    @OnClick(R.id.receive_train_back)
    void back(){
        finish();
    }

}
