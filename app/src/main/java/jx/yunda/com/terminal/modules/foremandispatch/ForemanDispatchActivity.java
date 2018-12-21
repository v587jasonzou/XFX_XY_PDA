package jx.yunda.com.terminal.modules.foremandispatch;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.FJ_ticket.presenter.CheckListActivity;
import jx.yunda.com.terminal.modules.foremandispatch.adapter.ForemanDispatchTrainAdapter;
import jx.yunda.com.terminal.modules.foremandispatch.model.TrainForForemanDispatch;
import jx.yunda.com.terminal.modules.foremandispatch.presenter.ForemanDispatchPresenter;
import jx.yunda.com.terminal.modules.foremandispatch.presenter.IForemanDispatch;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class ForemanDispatchActivity extends BaseActivity<ForemanDispatchPresenter> implements AdapterView.OnItemClickListener,
        View.OnClickListener, IForemanDispatch {


    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.gvCar)
    GridView gvCar;
    @BindView(R.id.sv_trains)
    SmartRefreshLayout svTrains;
    @BindView(R.id.btNext)
    Button btNext;
    ForemanDispatchTrainAdapter adapter;
    List<TrainForForemanDispatch> mList = new ArrayList<>();
    List<TrainForForemanDispatch> mListSearch = new ArrayList<>();
    int trainPosition;

    @Override
    protected ForemanDispatchPresenter getPresenter() {
        return new ForemanDispatchPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_foremandispatch;
    }

    @Override
    public void initSubViews(View view) {

    }

    @Override
    public void initData() {
        //设置toolbar
        adapter = new ForemanDispatchTrainAdapter(this, mList);
        gvCar.setAdapter(adapter);
        gvCar.setOnItemClickListener(this);
//        gvCar.getBackground().setAlpha(100);
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshTrain();
        btNext.setOnClickListener(this);
        svTrains.setRefreshHeader(new ClassicsHeader(this));
        svTrains.setRefreshFooter(new ClassicsFooter(this));
        svTrains.setNoMoreData(true);
        svTrains.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshTrain();
            }
        });
        svTrains.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                refreshTrain();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btNext.setBackground(ContextCompat.getDrawable(ForemanDispatchActivity.this,R.drawable.button_selector_gray));
                btNext.setEnabled(false);
                if(mListSearch.size()>0){
                    if("车型、车号".equals(s.toString())||"".equals(s.toString())){
                        mList.clear();
                        for(TrainForForemanDispatch bean :mListSearch){
                            if(bean.getState()==1){
                                bean.setState(0);
                            }
                        }
                        mList.addAll(mListSearch);
                        adapter.notifyDataSetChanged();
                    }else {
                        mList.clear();
                        for(TrainForForemanDispatch bean :mListSearch){
                            if(bean.getState()==1){
                                bean.setState(0);
                            }
                        }
                        for(int i = 0;i<mListSearch.size();i++){
                            if(mListSearch.get(i).getTrainNo().contains(s.toString())||
                                    mListSearch.get(i).getTrainTypeShortName().contains(s.toString())){
                                mList.add(mListSearch.get(i));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void refreshTrain() {
        getProgressDialog().show();
        if(mPresenter!=null)
        mPresenter.getDispatchTrains(SysInfo.userInfo.emp.getOperatorid().toString(), "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btNext:
                Bundle bundle = new Bundle();
                bundle.putString("trainTypeShortName", mList.get(trainPosition).getTrainTypeShortName());
                bundle.putString("trainTypeIDX", mList.get(trainPosition).getTrainTypeIDX());
                bundle.putString("trainNo", mList.get(trainPosition).getTrainNo());
                bundle.putString("workPlanIdx", mList.get(trainPosition).getWorkPlanIDX());
                ActivityUtil.startActivityResultWithDelayed(this, ForemanDispatchListActivity.class, bundle, 201);
                break;
        }

    }

    @Override
    public void LoadTrainListSuccess(List<TrainForForemanDispatch> beans) {
        getProgressDialog().dismiss();
        svTrains.finishRefresh();
        svTrains.finishLoadMore();
        if (beans != null && beans.size() > 0) {
            mList.clear();
            mListSearch.clear();
            mListSearch.addAll(beans);
            mList.addAll(beans);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "无在修机车数据", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void LoadMoreTrainListSuccess(List<TrainForForemanDispatch> beans) {
        getProgressDialog().dismiss();
        svTrains.finishRefresh();
        svTrains.finishLoadMore();
        mList.clear();
        mListSearch.clear();
        if (beans != null && beans.size() > 0) {
            mListSearch.addAll(beans);
            mList.addAll(beans);
        } else {
            Toast.makeText(this, "无在修机车数据", Toast.LENGTH_SHORT).show();
        }
        mListSearch.clear();
    }

    @Override
    protected void onResume() {
        refreshTrain();
        super.onResume();
    }

    @Override
    public void OnLoadFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort("获取在修机车列表失败:" + msg);
    }

    @Override
    public void OnSubmitSuccess() {
        getProgressDialog().dismiss();
        refreshTrain();
    }

    @Override
    public void OnSubmitFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mList.size() > 0) {
            for (int i = 0; i < mList.size(); i++) {
                if (i == position) {
                    mList.get(i).setState(1);
                } else {
                    mList.get(i).setState(0);
                }
            }
            trainPosition = position;
        }
        adapter.notifyDataSetChanged();
        btNext.setBackground(ContextCompat.getDrawable(this, R.drawable.button_selector));
        btNext.setEnabled(true);
    }

}
