package jx.yunda.com.terminal.modules.schedule.act;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.netty.util.internal.StringUtil;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.TrainRecandition.model.OrderBean;
import jx.yunda.com.terminal.modules.schedule.adapter.OrgAdapter;
import jx.yunda.com.terminal.modules.schedule.adapter.StationAdapter;
import jx.yunda.com.terminal.modules.schedule.entity.OrgBean;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;
import jx.yunda.com.terminal.modules.schedule.entity.StationBean;
import jx.yunda.com.terminal.modules.schedule.presenter.IScheduleBook;
import jx.yunda.com.terminal.modules.schedule.presenter.ScheduleBookPresenter;
import jx.yunda.com.terminal.utils.ToastUtil;

public class ScheduleBookActivity extends BaseActivity<ScheduleBookPresenter> implements IScheduleBook {
    String planIdx;
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.lvOrg)
    ListView lvOrg;
    @BindView(R.id.lvStation)
    ListView lvStation;
    @BindView(R.id.tvBook)
    TextView tvBook;
    OrgAdapter orgAdapter;
    StationAdapter stationAdapter;
    List<StationBean> stationBeanList = new ArrayList<>();
    List<OrgBean> orgList = new ArrayList<>();
    @Override
    protected ScheduleBookPresenter getPresenter() {
        return new ScheduleBookPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_schedule_book;
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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            planIdx = bundle.getString("idx");
        }
        orgAdapter = new OrgAdapter(this,orgList);
        lvOrg.setAdapter(orgAdapter);
        stationAdapter = new StationAdapter(this,stationBeanList);
        lvStation.setAdapter(stationAdapter);
        getProgressDialog().show();
        if (planIdx != null) {
            mPresenter.getTeamList(planIdx);
            mPresenter.getStationList(planIdx);
        }
    }

    @Override
    public void OnLoadOrgSuccess(List<OrgBean> list) {
        getProgressDialog().dismiss();
        orgList.clear();
        if(list!=null&&list.size()>0){
            orgList.addAll(list);
        }
        orgAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnLoadOrgFaild(String msg) {
        ToastUtil.toastShort(msg);
        getProgressDialog().dismiss();
    }

    @Override
    public void OnloadStationSuccess(List<StationBean> list) {
        getProgressDialog().dismiss();
        stationBeanList.clear();
        if(list!=null&&list.size()>0){
            stationBeanList.addAll(list);
        }
        stationAdapter.notifyDataSetChanged();
    }
    @OnClick(R.id.tvBook)
    void Book(){
        StringBuffer str = new StringBuffer();
        StringBuffer strid = new StringBuffer();
        for(OrgBean bean:orgList){
            if (bean.getStatus()==1){
                if(StringUtil.isNullOrEmpty(str.toString())){
                    str.append(bean.getHandeOrgName());
                    strid.append(bean.getHandeOrgID());
                }else {
                    str.append(","+bean.getHandeOrgName());
                    strid.append(","+bean.getHandeOrgID());
                }

            }
        }
        for(StationBean station:stationBeanList){
            station.setTeamOrgName(str.toString());
            station.setTeamOrgIds(strid.toString());
        }
        getProgressDialog().show();
        mPresenter.Book(JSON.toJSONString(stationBeanList));
    }
    @Override
    public void OnLoadStationFaild(String msg) {
        ToastUtil.toastShort(msg);
        getProgressDialog().dismiss();
    }

    @Override
    public void submitStationSuccess() {
        getProgressDialog().dismiss();
        ToastUtil.toastShort("点名成功");
        finish();
    }

    @Override
    public void submitStationFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void OnLoadTrainSuccess(List<ScheduleTrainBean> list) {

    }

    @Override
    public void OnLoadTrainFaild(String msg) {

    }

    @Override
    public void submitBookSuccess() {

    }

    @Override
    public void submitBookFaild(String msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
