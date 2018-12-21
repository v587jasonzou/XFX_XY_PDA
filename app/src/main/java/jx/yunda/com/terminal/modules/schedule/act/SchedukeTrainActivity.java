package jx.yunda.com.terminal.modules.schedule.act;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.schedule.adapter.BookOrgAdapter;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;
import jx.yunda.com.terminal.modules.schedule.presenter.IScheduleMain;
import jx.yunda.com.terminal.modules.schedule.presenter.ScheduleMainPresenter;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.WorkshopTaskDispatchListActivity;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class SchedukeTrainActivity extends BaseActivity<ScheduleMainPresenter> implements IScheduleMain {


    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.lvTicketList)
    ListView lvTicketList;
    @BindView(R.id.svList)
    SmartRefreshLayout svList;
    @BindView(R.id.llEmpty)
    LinearLayout llEmpty;
    BookOrgAdapter adapter;
    List<ScheduleTrainBean> mList = new ArrayList<>();
    int start = 0;
    int limit = 10;
    boolean isLoadmore = false;
    @Override
    public void OnLoadTrainSuccess(List<ScheduleTrainBean> list) {
        getProgressDialog().dismiss();
        svList.finishRefresh();
        ToastUtil.toastShort("加载成功");
        mList.clear();
        if(list!=null&&list.size()>0){
            mList.addAll(list);
            getProgressDialog().dismiss();
        }
        adapter.notifyDataSetChanged();
        if (list.size() < limit) {
            svList.setNoMoreData(true);
        } else {
            svList.setNoMoreData(false);
        }
    }

    @Override
    public void OnLoadTrainFaild(String msg) {
        svList.finishRefresh();
        svList.finishLoadMore();
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void OnLoadMoreTrainSuccess(final List<ScheduleTrainBean> list) {
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
    public void OnUpStationSuccess() {
        ToastUtil.toastShort("上台成功");
        getProgressDialog().dismiss();
        isLoadmore = false;
        start = 0;
        LoadData();
    }

    @Override
    public void OnUpStationFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    protected ScheduleMainPresenter getPresenter() {
        return new ScheduleMainPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_schedule_main;
    }

    @Override
    public void initSubViews(View view) {

    }
    @OnClick(R.id.tvSearch)
    void search(){
        isLoadmore = false;
        start = 0;
        LoadData();
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
                isLoadmore = false;
                start = 0;
                LoadData();
            }
        });
        svList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                start = start+limit;
                isLoadmore = true;
                LoadData();
            }
        });
        adapter = new BookOrgAdapter(this,mList);
        lvTicketList.setAdapter(adapter);
        adapter.setOnBtnClickLisnter(new BookOrgAdapter.OnBtnClickLisnter() {
            @Override
            public void OnBtnClick(int position, String status) {
                if(status.equals("点名")){
                    Bundle bundle = new Bundle();
                    bundle.putString("idx",mList.get(position).getIdx());
                    ActivityUtil.startActivity(SchedukeTrainActivity.this,ScheduleBookActivity.class,bundle);
                }else if(status.equals("调度")){
                    Bundle bundle = new Bundle();
                    bundle.putString("trainTypeShortName", mList.get(position).getTrainTypeShortName());
                    bundle.putString("trainTypeIDX", mList.get(position).getTrainTypeIDX());
                    bundle.putString("trainNo", mList.get(position).getTrainNo());
                    bundle.putString("workPlanIdx", mList.get(position).getIdx());
                    ActivityUtil.startActivity(SchedukeTrainActivity.this, WorkshopTaskDispatchListActivity.class, bundle);
                }else {
                    final ScheduleTrainBean entity = mList.get(position);
                    dialog.show();
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(etsuggestion.getText()==null||etsuggestion.getText().toString().equals("")){
                                ToastUtil.toastShort("还未输入备注！");
                                return;
                            }
                            Map<String,Object> map = new HashMap<>();
                            map.put("idx",entity.getIdx());
                            map.put("planBeginTime", Utils.formatTime(new Date(),"yyyy-MM-dd mm:ss"));
                            map.put("remarks",etsuggestion.getText().toString());
                            map.put("workCalendarIDX",entity.getWorkCalendarIDX());
                            dialog.dismiss();
                            getProgressDialog().show();
                            mPresenter.UpStation(JSON.toJSONString(map));
                        }
                    });
                }
            }
        });
        LoadData();
        setDialog();
    }
    AlertDialog dialog;
    EditText etsuggestion;
    private void setDialog() {
        View view  = LayoutInflater.from(this).inflate(R.layout.quality_dialog_suggestion,null);
        etsuggestion = (EditText)view.findViewById(R.id.etsuggestion);
        dialog = new AlertDialog.Builder(this).setView(view).setTitle("机车上台备注").setNegativeButton("取消",null)
                .setPositiveButton("确定",null).create();

    }
    private void LoadData(){
        Map<String,Object> map = new HashMap<>();
        map.put("workPlanStatus","INITIALIZE,ONGOING");
        if(etSearch.getText()!=null&&!etSearch.getText().toString().equals("")){
            map.put("trainNo",etSearch.getText().toString());
        }
        getProgressDialog().show();
        mPresenter.getTarinList(start,limit, JSON.toJSONString(map),isLoadmore);
    }
}
