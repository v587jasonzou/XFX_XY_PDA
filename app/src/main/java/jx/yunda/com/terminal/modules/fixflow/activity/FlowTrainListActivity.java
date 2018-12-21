package jx.yunda.com.terminal.modules.fixflow.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.fixflow.adapter.FlowTrainAdapter;
import jx.yunda.com.terminal.modules.fixflow.entry.FlowTrainBean;
import jx.yunda.com.terminal.modules.fixflow.presenter.TrainListPresenter;
import jx.yunda.com.terminal.modules.fixflow.presenter.iTrainList;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class FlowTrainListActivity extends BaseActivity<TrainListPresenter> implements iTrainList {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.lvTrain)
    ListView lvTrain;
    FlowTrainAdapter adapter;
    @Override
    protected TrainListPresenter getPresenter() {
        return new TrainListPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_fix_flow_train;
    }

    @Override
    public void initSubViews(View view) {

    }
    List<FlowTrainBean> list = new ArrayList<>();
    @Override
    public void initData() {
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new FlowTrainAdapter(this,list);
        lvTrain.setAdapter(adapter);
        getProgressDialog().show();
        mPresenter.getTrainList("");
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etSearch.getText()!=null&&!"".equals(etSearch.getText().toString())){
                    getProgressDialog().show();
                    mPresenter.getTrainList(etSearch.getText().toString());
                }else {
                    getProgressDialog().show();
                    mPresenter.getTrainList("");
                }
            }
        });
        lvTrain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FlowTrainBean entry = list.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("idx",entry.getIdx());
                bundle.putString("fwhUndoNo",entry.getFwUnfinishedNo()+"");
                bundle.putString("fwhDoNo",entry.getFwFinishedNo()+"");
                bundle.putString("TpUndoNo",entry.getTpUnfinishedNo()+"");
                bundle.putString("TpdoNo",entry.getTpFinishedNo()+"");
                bundle.putString("shortName",entry.getTrainTypeShortName());
                bundle.putString("TrainNo",entry.getTrainNo());
                ActivityUtil.startActivity(FlowTrainListActivity.this,FlowInfoMainActivity.class,bundle);
            }
        });
    }

    @Override
    public void OnLoadSuccess(List<FlowTrainBean> list) {
        this.list.clear();
        getProgressDialog().dismiss();
        if(list!=null&&list.size()>0){
            this.list.addAll(list);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnLoadMoreSuccess() {

    }

    @Override
    public void OnLoadFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
