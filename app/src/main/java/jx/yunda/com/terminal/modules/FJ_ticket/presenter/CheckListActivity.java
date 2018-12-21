package jx.yunda.com.terminal.modules.FJ_ticket.presenter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
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
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.FJ_ticket.RecheckInfoActivity;
import jx.yunda.com.terminal.modules.FJ_ticket.SubmitRecheckActivity;
import jx.yunda.com.terminal.modules.FJ_ticket.adapter.CheckListAdapter;
import jx.yunda.com.terminal.modules.FJ_ticket.model.RecheckBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketInfoBean;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

public class CheckListActivity extends BaseActivity<RecheckListPresenter> implements IRecheckList {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvTicketName)
    TextView tvTicketName;
    @BindView(R.id.tvTicketNum)
    TextView tvTicketNum;
    @BindView(R.id.lvRecheck)
    SwipeMenuListView lvRecheck;
    @BindView(R.id.srlRefresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.btAdd)
    Button btAdd;
    List<RecheckBean> list = new ArrayList<>();
    CheckListAdapter adapter;
    String ticketTrainIDX, trainTypeShortName, trainNo, trainTypeIDX;

    @Override
    protected RecheckListPresenter getPresenter() {
        return new RecheckListPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_recheck_list;
    }

    @Override
    public void initSubViews(View view) {

    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        ticketTrainIDX = bundle.getString("ticketTrainIDX");
        trainTypeShortName = bundle.getString("trainTypeShortName");
        trainNo = bundle.getString("trainNo");
        trainTypeIDX = bundle.getString("trainTypeIDX");
        tvTicketName.setText(trainTypeShortName + " " + trainNo);
        Refreshed();
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new CheckListAdapter(this, list);
        lvRecheck.setAdapter(adapter);
        lvRecheck.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("TrainName", trainTypeShortName+" "+trainNo);
                bundle.putString("idx", list.get(position).getIdxx());
                bundle.putString("lb",list.get(position).getLb());
                bundle.putString("fixPlaceFullName",list.get(position).getFixPlaceFullName());
                bundle.putString("blzt",list.get(position).getBlzt());
                ActivityUtil.startActivityWithDelayed(CheckListActivity.this, RecheckInfoActivity.class, bundle);
            }
        });
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x00, 0x00)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("删除");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

            }
        };
        lvRecheck.setMenuCreator(creator);
        lvRecheck.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        String[] ids = new String[1];
                        String mgs = ids.toString();
                        ids[0] = list.get(position).getIdxx();
                        getProgressDialog().show();
                        mPresenter.DeleteRecheck(JSON.toJSONString(ids));
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        lvRecheck.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        srlRefresh.setRefreshHeader(new ClassicsHeader(this));
        srlRefresh.setRefreshFooter(new ClassicsFooter(this));
        srlRefresh.setNoMoreData(true);
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                Refreshed();
            }
        });
    }

    private void Refreshed() {
        Map<String, String> map = new HashMap<>();
//        map.put("data1","");
//        map.put("idx","");
        map.put("ticketTrainIDX", ticketTrainIDX);
        map.put("empid", SysInfo.userInfo.emp.getEmpid() + "");
        map.put("trainTypeShortName", trainTypeShortName);
//        map.put("shortName",trainTypeShortName);
        map.put("trainNo", trainNo);
        map.put("trainTypeIDX", trainTypeIDX);
        map.put("ticketSource", "机车复检复验");
        map.put("recheckStatus", "10");
        map.put("recheckType", "10");
        map.put("placeName", "");
        map.put("updator", SysInfo.userInfo.emp.getEmpid() + "");
        getProgressDialog().show();
        mPresenter.getRecheckList(JSON.toJSONString(map));
    }

    @OnClick(R.id.btAdd)
    void AddRecheck() {
        Bundle bundle = new Bundle();
        bundle.putString("ShortName", trainTypeShortName);
        bundle.putString("trainTypeIDX", trainTypeIDX);
        bundle.putString("TrainNo", trainNo);
        bundle.putString("ticketTrainIDX", ticketTrainIDX);
        ActivityUtil.startActivityResultWithDelayed(CheckListActivity.this, SubmitRecheckActivity.class, bundle, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==300){
            Refreshed();
        }
    }
    @Override
    public void getRecheckListSuccess(List<RecheckBean> RecheckList) {
        list.clear();
        if (RecheckList != null && RecheckList.size() > 0) {
            list.addAll(RecheckList);
        }
        tvTicketNum.setText("当前提票数 "+list.size()+"张");
        srlRefresh.finishRefresh();
        adapter.notifyDataSetChanged();
        getProgressDialog().dismiss();
    }

    @Override
    public void getReCheckListFaild(String msg) {
        ToastUtil.toastShort(msg);
        getProgressDialog().dismiss();
        srlRefresh.finishRefresh();
    }

    @Override
    public void DeleteRecheckSuccess() {
        ToastUtil.toastShort("删除成功");
        Refreshed();
    }

    @Override
    public void DeleteRecheckFaild(String msg) {
        ToastUtil.toastShort(msg);
        getProgressDialog().dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
