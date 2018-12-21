package jx.yunda.com.terminal.modules.TrainRecandition.act;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.TrainRecandition.adapter.TrainAdapter;
import jx.yunda.com.terminal.modules.TrainRecandition.model.JXTrainBean;
import jx.yunda.com.terminal.modules.TrainRecandition.presenter.IRecanditionTrain;
import jx.yunda.com.terminal.modules.TrainRecandition.presenter.RecanditionTrainPresenter;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class RecanditionTrainActivity extends BaseActivity<RecanditionTrainPresenter> implements AdapterView.OnItemClickListener, View.OnClickListener
        , IRecanditionTrain {
    TrainAdapter adapter;
    List<JXTrainBean> mList = new ArrayList<>();
    List<JXTrainBean> mListSearch = new ArrayList<>();
    int trainPosition;
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvMesaageNo)
    TextView tvMesaageNo;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.gvCar)
    GridView gvCar;
    @BindView(R.id.sv_ticketTrain)
    SmartRefreshLayout svTicketTrain;
    @BindView(R.id.btNext)
    Button btNext;

    @Override
    protected RecanditionTrainPresenter getPresenter() {
        return new RecanditionTrainPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_train_recandition;
    }

    @Override
    public void initSubViews(View view) {

    }
    String function;
    @Override
    public void initData() {
        //设置toolbar
        adapter = new TrainAdapter(this, mList);
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
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            function = bundle.getString("function");
        }
        btNext.setOnClickListener(this);
        tvMesaageNo.setOnClickListener(this);
        svTicketTrain.setRefreshHeader(new ClassicsHeader(this));
        svTicketTrain.setRefreshFooter(new ClassicsFooter(this));
        svTicketTrain.setNoMoreData(true);
        svTicketTrain.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshTrain();
            }
        });
        svTicketTrain.setOnLoadMoreListener(new OnLoadMoreListener() {
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
                btNext.setBackground(ContextCompat.getDrawable(RecanditionTrainActivity.this, R.drawable.button_selector_gray));
                btNext.setEnabled(false);
                s = s.toString().toUpperCase();
                if (mListSearch.size() > 0) {
                    if ("车型、车号".equals(s.toString()) || "".equals(s.toString())) {
                        mList.clear();
                        for (JXTrainBean bean : mListSearch) {
                            if (bean.getState() == 1) {
                                bean.setState(0);
                            }
                        }
                        mList.addAll(mListSearch);
                        adapter.notifyDataSetChanged();
                    } else {
                        mList.clear();
                        for (JXTrainBean bean : mListSearch) {
                            if (bean.getState() == 1) {
                                bean.setState(0);
                            }
                        }
                        for (int i = 0; i < mListSearch.size(); i++) {
                            if (mListSearch.get(i).getTrainNo().contains(s.toString()) ||
                                    mListSearch.get(i).getTrainTypeShortName().contains(s.toString())) {
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
        Map<String, String> map = new HashMap<>();
        map.put("empid", SysInfo.userInfo.emp.getEmpid() + "");
        mPresenter.getTicketTrains(JSON.toJSONString(map),0,1000,function);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mPresenter!=null&&SysInfo.userInfo!=null&& SysInfo.userInfo.emp!=null){
            getProgressDialog().show();
            Map<String, String> map = new HashMap<>();
            map.put("empid", SysInfo.userInfo.emp.getEmpid() + "");
            mPresenter.getTicketTrains(JSON.toJSONString(map),0,1000,function);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btNext:
                Bundle bundle = new Bundle();
                bundle.putString("ShortName", mList.get(trainPosition).getTrainTypeShortName());
                bundle.putString("trainTypeIDX", mList.get(trainPosition).getWorkPlanIDX());
                bundle.putString("TrainNo", mList.get(trainPosition).getTrainNo());
                bundle.putString("state", "0");
                bundle.putString("unloadTrainType","");
                if(function!=null){
                    bundle.putString("function",function);
                }
                ActivityUtil.startActivityResultWithDelayed(this, RecanditionListActivity.class, bundle, 201);
                break;
        }

    }

    @Override
    public void LoadTrainListSuccess(List<JXTrainBean> beans) {
        getProgressDialog().dismiss();
        svTicketTrain.finishRefresh();
        svTicketTrain.finishLoadMore();
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
    public void LoadMoreTrainListSuccess(List<JXTrainBean> beans) {
        getProgressDialog().dismiss();
        svTicketTrain.finishRefresh();
        svTicketTrain.finishLoadMore();
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
    public void OnLoadFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort("获取在修机车列表失败:" + msg);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toobar跟menu
        getMenuInflater().inflate(R.menu.tp_menu, menu);
        menu.getItem(0).setTitle("处理记录");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.tv_tprecord) {

            ActivityUtil.startActivityWithDelayed(this, RecanditionHistoryActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }
}
