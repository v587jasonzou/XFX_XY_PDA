package jx.yunda.com.terminal.modules.receiveTrain.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.receiveTrain.ReceiveTrainActivity;
import jx.yunda.com.terminal.modules.receiveTrain.adapter.ReceivedTrainNoticeAdapter;
import jx.yunda.com.terminal.modules.receiveTrain.model.ReceiveTrainNotice;
import jx.yunda.com.terminal.modules.receiveTrain.presenter.IReceiveTrainNotice;
import jx.yunda.com.terminal.modules.receiveTrain.presenter.ReceiveTrainNoticePresenter;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.ToastUtil;


public class ReceiveTrainNoticeActivity extends BaseActivity<ReceiveTrainNoticePresenter> implements IReceiveTrainNotice {
    ReceivedTrainNoticeAdapter adapter;
    @BindView(R.id.receive_train_notice_recycler)
    RecyclerView recycler;
    List<ReceiveTrainNotice> mList = new ArrayList<>();
    @BindView(R.id.receive_train_notice_back)
    ImageButton receiveTrainNoticeBack;
    @BindView(R.id.receive_train_notice_smart_refresh)
    SmartRefreshLayout receiveTrainNoticeSmartRefresh;
    @BindView(R.id.tvHistory)
    TextView tvHistory;
    @BindView(R.id.ivAdd)
    TextView ivAdd;
    String idx = null;
    @Override
    protected ReceiveTrainNoticePresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new ReceiveTrainNoticePresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.receive_train_notice_fm;
    }

    @Override
    public void initSubViews(View view) {
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        Bundle bundle = null;
        if(intent!=null){
            bundle = getIntent().getExtras();
        }
        if(bundle!=null){
            idx = bundle.getString("idx");
        }
        adapter = new ReceivedTrainNoticeAdapter(this, mList);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        receiveTrainNoticeSmartRefresh.setNoMoreData(true);
        receiveTrainNoticeSmartRefresh.setRefreshHeader(new ClassicsHeader(this));
        receiveTrainNoticeSmartRefresh.setRefreshFooter(new ClassicsFooter(this));
        receiveTrainNoticeSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getProgressDialog().show();
                Map<String, Object> map = new HashMap<>();
                map.put("acceptStatus", 0);
                mPresenter.getReceiveTrainList("");
            }
        });
        adapter.setOnOnReceivedClickListner(new ReceivedTrainNoticeAdapter.OnReceivedClickListner() {
            @Override
            public void OnReceiveClick(int position) {
                ReceiveTrainNotice bean = mList.get(position);
                Bundle bundle = new Bundle();
                if (bean != null) {
                    bundle.putSerializable("TrainNotice", bean);
                }
                ActivityUtil.startActivity(ReceiveTrainNoticeActivity.this,
                        ReceiveTrainOperationActivity.class, bundle);
            }
        });
        getProgressDialog().show();
        Map<String, Object> map = new HashMap<>();
        map.put("acceptStatus", 0);
        mPresenter.getReceiveTrainList("");
    }

    @OnClick({R.id.receive_train_notice_back, R.id.tvHistory, R.id.ivAdd})
    void viewClick(View view) {
        switch (view.getId()) {
            case R.id.receive_train_notice_back:
                finish();
                break;
            case R.id.ivAdd:
                ActivityUtil.startActivity(ReceiveTrainNoticeActivity.this,
                        ReceiveTrainOperationActivity.class);
                break;
            case R.id.tvHistory:
                ActivityUtil.startActivity(ReceiveTrainNoticeActivity.this,
                        ReceiveTrainActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void OnLoadTrainListSuccess(List<ReceiveTrainNotice> list) {
        receiveTrainNoticeSmartRefresh.finishRefresh();
        getProgressDialog().dismiss();
        mList.clear();
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        adapter.notifyDataSetChanged();
        if(idx!=null&&list!=null&&list.size()>0){
            ReceiveTrainNotice bean = null;
            for(ReceiveTrainNotice notice:list){
                if(notice.getIdx().equals(idx)){
                    bean = notice;
                    break;
                }
            }
            if(bean!=null){
                Bundle bundle = new Bundle();
                if (bean != null) {
                    bundle.putSerializable("TrainNotice", bean);
                }
                ActivityUtil.startActivity(ReceiveTrainNoticeActivity.this,
                        ReceiveTrainOperationActivity.class, bundle);
            }

        }
    }

    @Override
    public void OnLoadTrainListFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }


    @Override
    public void onResume() {
        super.onResume();
        getProgressDialog().show();
        Map<String, Object> map = new HashMap<>();
        map.put("acceptStatus", 0);
        mPresenter.getReceiveTrainList("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
