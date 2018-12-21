package jx.yunda.com.terminal.modules.tpmanage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.tpmanage.presenter.ITicketManage;
import jx.yunda.com.terminal.modules.tpmanage.presenter.TicketManagePresenter;
import jx.yunda.com.terminal.modules.tpprocess.TicketInfoReadActivity;
import jx.yunda.com.terminal.modules.tpprocess.adapter.TicketListAdapter;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketInfoBean;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;

public class TicketManagerActivity extends BaseActivity<TicketManagePresenter> implements ITicketManage,TicketListAdapter.OnInfoClickListner {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvMesaageNo)
    TextView tvMesaageNo;
    @BindView(R.id.lvTicketList)
    ListView lvTicketList;
    @BindView(R.id.svList)
    SmartRefreshLayout svList;
    @BindView(R.id.llEmpty)
    LinearLayout llEmpty;
    int start = 0 ;
    int limit = 8;
    TicketListAdapter adapter;
    List<TicketInfoBean> mlist = new ArrayList<>();
    @Override
    protected TicketManagePresenter getPresenter() {
        return new TicketManagePresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_ticket_manage;
    }

    @Override
    public void initSubViews(View view) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toobar跟menu
        getMenuInflater().inflate(R.menu.tp_menu, menu);
        MenuItem item = menu.findItem(R.id.tv_tprecord);
        item.setTitle("处理记录");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.tv_tprecord) {
//            Bundle bundle = new Bundle();
//            bundle.putString("type",typeList.get(typePosition).getDictname());
//            ActivityUtil.startActivityWithDelayed(this, TicketListActivity.class,bundle);
            ActivityUtil.startActivityWithDelayed(this, TicketManageListActivity.class);
        }
        return super.onOptionsItemSelected(item);
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
        llEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = 0;
                Map<String, Object> map = new HashMap<>();
                map.put("status", 20);
                map.put("dispatchEmpID", SysInfo.userInfo.emp.getEmpid());
                mPresenter.getTicketList(start,limit, JSON.toJSONString(map),0);
            }
        });
        svList.setRefreshHeader(new ClassicsHeader(this));
        svList.setRefreshFooter(new ClassicsFooter(this));
        svList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                start = 0;
                Map<String, Object> map = new HashMap<>();
                map.put("status", 20);
                map.put("dispatchEmpID", SysInfo.userInfo.emp.getEmpid());
                mPresenter.getTicketList(start,limit, JSON.toJSONString(map),0);
            }
        });
        svList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                start = start+limit;
                Map<String, Object> map = new HashMap<>();
                map.put("status", 20);
                map.put("dispatchEmpID", SysInfo.userInfo.emp.getEmpid());
                mPresenter.getTicketList(start,limit, JSON.toJSONString(map),1);
            }
        });
        adapter = new TicketListAdapter(this, mlist, 0);
        adapter.setOnInfoClickListner(this);
        lvTicketList.setAdapter(adapter);
        if(SysInfo.userInfo!=null){
            Map<String, Object> map = new HashMap<>();
            map.put("status", 20);
            map.put("dispatchEmpID", SysInfo.userInfo.emp.getEmpid());
            mPresenter.getTicketList(start,limit, JSON.toJSONString(map),0);
        }

    }

    @Override
    public void OnTicketListLoadSuccess(List<TicketInfoBean> list) {
        mlist.clear();
        svList.finishRefresh();
        if(list!=null&&list.size()>0){
            llEmpty.setVisibility(View.GONE);
            if(list.size()<8){
                svList.setNoMoreData(true);
            }else {
                svList.setNoMoreData(false);
            }
            mlist.addAll(list);
        }else {
            llEmpty.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnTicketListLoadMoreSuccess(List<TicketInfoBean> list) {
        svList.finishLoadMore();
        if(list!=null&&list.size()>0){
            if(list.size()<8){
                svList.setNoMoreData(true);
            }else {
                svList.setNoMoreData(false);
            }
            mlist.addAll(list);
            adapter.notifyDataSetChanged();
        }else {
            ToastUtil.toastShort("加载数据失败");
        }
    }

    @Override
    public void OnTicketListLoadFail(String msg) {
        ToastUtil.toastShort("加载数据失败："+ msg);
    }

    @Override
    public void OnTicketListLoadMoreFail(String msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void OnInfoClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("type", mlist.get(position).getType());
        bundle.putString("ShortName", mlist.get(position).getTrainTypeShortName());
        bundle.putString("trainTypeIDX", mlist.get(position).getTrainTypeIDX());
        bundle.putString("TrainNo", mlist.get(position).getTrainNo());
        bundle.putString("typeId", mlist.get(position).getTrainTypeIDX());
        bundle.putString("idx", mlist.get(position).getIdx());
        bundle.putString("faultFixFullName", mlist.get(position).getFixPlaceFullName());
        String[] proId = null;
        String[] pro = null;
        if (mlist.get(position).getReasonAnalysisID() != null)
            proId = mlist.get(position).getReasonAnalysisID().split(";");
        if (mlist.get(position).getReasonAnalysis() != null)
            pro = mlist.get(position).getReasonAnalysis().split(";");
        String zhuanye = "";
        String kaohe = "";
        String zhuanyeId = "";
        String kaoheId = "";
        if (proId != null&&pro!=null) {
            for (int i = 0; i < proId.length; i++) {
                if (proId[i].substring(0, 2).equals("10")) {
                    if (!"".equals(zhuanye)) {
                        zhuanye = zhuanye + "," + pro[i];
                        zhuanyeId = zhuanyeId+","+proId[i];
                    } else {
                        zhuanye = zhuanye + pro[i];
                        zhuanyeId = zhuanyeId+proId[i];
                    }

                } else {
                    if (!"".equals(kaohe)) {
                        kaohe = kaohe + "," + pro[i];
                        kaoheId = kaoheId + "," + proId[i];
                    } else {
                        kaohe = kaohe + pro[i];
                        kaoheId = kaoheId + proId[i];
                    }
                }
            }
        }
        bundle.putString("zhuanye", zhuanye);
        bundle.putString("kaohe", kaohe);
        bundle.putString("zhuanyeId", zhuanyeId);
        bundle.putString("kaoheId", kaoheId);
        bundle.putString("faultDesc", mlist.get(position).getFaultDesc());
        if(mlist.get(position).getResolutionContent()!=null)
        bundle.putString("resolutionContent", mlist.get(position).getResolutionContent());
        bundle.putString("state", "4");
        bundle.putString("isture","ture");
        if(mlist.get(position).getOverRangeStatus()!=null)
        bundle.putInt("overRangeStatus",mlist.get(position).getOverRangeStatus());
        if(mlist.get(position).getTicketEmp()!=null)
        bundle.putString("ticketEmp",mlist.get(position).getTicketEmp());
        bundle.putString("ticketTime", Utils.formatTime(mlist.get(position).getTicketTime(),"yyyy/MM/dd HH:mm"));
        bundle.putString("faultFixPlaceIDX",mlist.get(position).getFaultFixPlaceIDX());
        bundle.putString("fixPlaceFullCode",mlist.get(position).getFixPlaceFullCode()+"");
        bundle.putLong("faultOccurDate",mlist.get(position).getFaultOccurDate());
        //  添加提票[专业，类型...]
        bundle.putString("reasonAnalysisID",mlist.get(position).getReasonAnalysisID());
        bundle.putString("reasonAnalysis",mlist.get(position).getReasonAnalysis());
        ActivityUtil.startActivityResultWithDelayed(TicketManagerActivity.this, TicketInfoReadActivity.class, bundle,201);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==200){
            start = 0;
            Map<String, Object> map = new HashMap<>();
            map.put("status", 20);
            map.put("dispatchEmpID", SysInfo.userInfo.emp.getEmpid());
            mPresenter.getTicketList(start,limit, JSON.toJSONString(map),0);
        }
    }
}
