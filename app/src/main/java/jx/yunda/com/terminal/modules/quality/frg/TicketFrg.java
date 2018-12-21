package jx.yunda.com.terminal.modules.quality.frg;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
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
import butterknife.Unbinder;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.modules.quality.act.QualityActivity;
import jx.yunda.com.terminal.modules.quality.adapter.TicketQualityAdapter;
import jx.yunda.com.terminal.modules.quality.model.NodeBean;
import jx.yunda.com.terminal.modules.quality.model.QualityTIcketPlanBean;
import jx.yunda.com.terminal.modules.quality.presenter.IQualityTicket;
import jx.yunda.com.terminal.modules.quality.presenter.QualityTicketPresenter;
import jx.yunda.com.terminal.utils.ToastUtil;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

public class TicketFrg extends BaseFragment<QualityTicketPresenter> implements IQualityTicket {
    @BindView(R.id.lvRecheck)
    SwipeMenuListView lvRecheck;
    @BindView(R.id.srlRefresh)
    SmartRefreshLayout srlRefresh;
    Unbinder unbinder;
    int start = 0;
    int limit = 20;
    boolean isLoadMore = false;
    QualityActivity mac;
    TicketQualityAdapter adapter;
    List<QualityTIcketPlanBean> mList = new ArrayList<>();
    int EquipPosition;
    @BindView(R.id.llEmpty)
    LinearLayout llEmpty;

    @Override
    protected QualityTicketPresenter getPresenter() {
        return new QualityTicketPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_ticket_quality;
    }

    @Override
    public void initSubViews(View view) {

    }

    public void setData() {
        srlRefresh.setNoMoreData(false);
        mac.getProgressDialog().show();
        Map<String, Object> map = new HashMap<>();
        mPresenter.getTrains(JSON.toJSONString(map), start + "", limit + "", "3", isLoadMore);
    }
    AlertDialog dialog;
    EditText etsuggestion;
    private void setDialog() {
        View view  = LayoutInflater.from(getContext()).inflate(R.layout.quality_dialog_suggestion,null);
        etsuggestion = (EditText)view.findViewById(R.id.etsuggestion);
        dialog = new AlertDialog.Builder(getContext()).setView(view).setNegativeButton("取消",null)
                .setPositiveButton("确定",null).create();

    }
    @Override
    public void initData() {
        mac = (QualityActivity) getActivity();
        adapter = new TicketQualityAdapter(mac, mList);
        lvRecheck.setAdapter(adapter);
        setData();
        srlRefresh.setRefreshFooter(new ClassicsFooter(mac));
        srlRefresh.setRefreshHeader(new ClassicsHeader(mac));
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                start = 0;
                isLoadMore = false;
                setData();
            }
        });
        srlRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                start = start + limit;
                isLoadMore = true;
                setData();
            }
        });
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem openItem2 = new SwipeMenuItem(
                        getContext());
                // set item background
                openItem2.setBackground(new ColorDrawable(Color.rgb(0x00,
                        0xC8, 0x53)));
                // set item width
                openItem2.setWidth(dp2px(90));
                // set item title
                openItem2.setTitle("通过");
                // set item title fontsize
                openItem2.setTitleSize(18);
                // set item title font color
                openItem2.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem2);

            }
        };
        setDialog();
        lvRecheck.setMenuCreator(creator);
        lvRecheck.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                dialog.setTitle("请输入备注");
                dialog.show();
                EquipPosition = position;
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(etsuggestion.getText()==null&&etsuggestion.getText().toString().equals("")){
                            ToastUtil.toastShort("还未输入备注");
                            return;
                        }else {
                            dialog.dismiss();
                            mac.getProgressDialog().show();
                            Map<String, Object> map = new HashMap<>();
                            map.put("idx", mList.get(EquipPosition).getIdx());
                            map.put("qcEmpID", SysInfo.userInfo.emp.getEmpid());
                            map.put("qcEmpName", SysInfo.userInfo.emp.getEmpname());
                            map.put("remarks",etsuggestion.getText().toString());
                            ArrayList<Map<String, Object>> maps = new ArrayList<>();
                            maps.add(map);
                            mPresenter.PassQuality(JSON.toJSONString(maps));
                        }
                    }
                });
                return false;
            }
        });
        lvRecheck.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        llEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = 0;
                isLoadMore = false;
                setData();
            }
        });
    }

    @Override
    public void OnLoadPlansSuccess(List<QualityTIcketPlanBean> list) {
        mList.clear();
        mac.getProgressDialog().dismiss();
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        if(list==null||list.size()==0){
            llEmpty.setVisibility(View.VISIBLE);
        }else {
            llEmpty.setVisibility(View.GONE);
        }
        adapter = new TicketQualityAdapter(mac,mList);
        lvRecheck.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (list == null || list.size() < limit) {
            srlRefresh.setNoMoreData(true);
        }
        srlRefresh.finishLoadMore();
        srlRefresh.finishRefresh();
    }

    @Override
    public void OnLoadPlansFaild(String msg) {
        srlRefresh.finishLoadMore();
        srlRefresh.finishRefresh();
        mac.getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void OnLoadMorePlansSuccess(List<QualityTIcketPlanBean> list) {
        srlRefresh.finishLoadMore();
        srlRefresh.finishRefresh();
        mac.getProgressDialog().dismiss();
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        adapter.notifyDataSetChanged();
//        adapter.refresh(list);
        if (list == null || list.size() < limit) {
            srlRefresh.setNoMoreData(true);
        }
    }

    @Override
    public void OnSubmitSuccess() {
        mac.getProgressDialog().dismiss();
        ToastUtil.toastShort("通过成功");
        start = 0;
        isLoadMore = false;
        setData();
        mac.setData();
    }

    @Override
    public void OnSubmitFaild(String msg) {
        mac.getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
        start = 0;
        isLoadMore = false;
        setData();
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

    @Override
    public void onResume() {
        super.onResume();
    }
}
