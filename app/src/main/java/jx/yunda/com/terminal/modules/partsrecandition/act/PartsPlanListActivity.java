package jx.yunda.com.terminal.modules.partsrecandition.act;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import jx.yunda.com.terminal.modules.TrainRecandition.act.DownEquipActivity;
import jx.yunda.com.terminal.modules.TrainRecandition.act.RecanditionHistoryActivity;
import jx.yunda.com.terminal.modules.TrainRecandition.act.RecanditionListActivity;
import jx.yunda.com.terminal.modules.TrainRecandition.act.UpEquipActivity;
import jx.yunda.com.terminal.modules.partsrecandition.adapter.PartsPlanListAdapter;
import jx.yunda.com.terminal.modules.partsrecandition.model.PartsPlanBean;
import jx.yunda.com.terminal.modules.partsrecandition.presenter.IPartsPlans;
import jx.yunda.com.terminal.modules.partsrecandition.presenter.PartsPlanPresenter;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

public class PartsPlanListActivity extends BaseActivity<PartsPlanPresenter> implements IPartsPlans, PartsPlanListAdapter.OnBtnClickLisnter, BaseActivity.OnScanCodeCallBack {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.lvList)
    SwipeMenuListView lvList;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    @BindView(R.id.llEmpty)
    LinearLayout llEmpty;
    List<PartsPlanBean> list = new ArrayList<>();
    PartsPlanListAdapter adapter;
    String trainTypeIDX, ShortName, TrainNo;
    String nodeName = "";
    public static String STATUS_COMPLETED = "COMPLETED";
    public static String STATUS_NOTSTARTED = "NOTSTARTED";
    public static String STATUS_RUNNING = "RUNNING";
    int start = 0;
    int limit = 8;
    boolean isLoadMore = false;
    @BindView(R.id.etSearch)
    EditText etSearch;
    AlertDialog dialog;
    int EquipPosition;
    EditText etCode;
    TextView tvType;
    LinearLayout llDownEquip;
    LinearLayout llUpEquip;
    boolean isScan = true;

    @Override
    protected PartsPlanPresenter getPresenter() {
        return new PartsPlanPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_parts_plan_list;
    }

    @Override
    public void initSubViews(View view) {

    }

    private void setDialog() {
        if (dialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_parts_equip_select, null);
            etCode = (EditText) view.findViewById(R.id.etCode);
            tvType = (TextView) view.findViewById(R.id.tvType);
            llDownEquip = (LinearLayout) view.findViewById(R.id.llDownEquip);
            llDownEquip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    String starttime = list.get(EquipPosition).getPlanStartTime();
                    String endTime = list.get(EquipPosition).getPlanEndTime();
                    if (starttime != null && endTime != null) {
                        starttime = Utils.formatTime(Utils.stringToLong(starttime, "yyyy-MM-dd HH:mm:ss"), "MM-dd HH:mm");
                        endTime = Utils.formatTime(Utils.stringToLong(endTime, "yyyy-MM-dd HH:mm:ss"), "MM-dd HH:mm");
                    }
                    bundle.putString("name", list.get(EquipPosition).getWpNodeName());
                    bundle.putString("startTime", starttime);
                    bundle.putString("endTime", endTime);
                    bundle.putString("RdpIDX", list.get(EquipPosition).getRdpIDX());
                    bundle.putString("nodeCaseIDX", list.get(EquipPosition).getIdx());
                    bundle.putString("trainType", list.get(EquipPosition).getPartsName());
                    bundle.putString("TrainNo", list.get(EquipPosition).getUnloadTrainNo());
                    bundle.putString("specificationModel", list.get(EquipPosition).getSpecificationModel());
                    dialog.dismiss();
                    ActivityUtil.startActivityResultWithDelayed(PartsPlanListActivity.this, DownPartsActivity.class, bundle, 200);
                }
            });
            llUpEquip = (LinearLayout) view.findViewById(R.id.llUpEquip);
            llUpEquip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    String starttime = list.get(EquipPosition).getPlanStartTime();
                    String endTime = list.get(EquipPosition).getPlanEndTime();
                    if (starttime != null && endTime != null) {
                        starttime = Utils.formatTime(Utils.stringToLong(starttime, "yyyy-MM-dd HH:mm:ss"), "MM-dd HH:mm");
                        endTime = Utils.formatTime(Utils.stringToLong(endTime, "yyyy-MM-dd HH:mm:ss"), "MM-dd HH:mm");
                    }
                    bundle.putString("name", list.get(EquipPosition).getWpNodeName());
                    bundle.putString("startTime", starttime);
                    bundle.putString("endTime", endTime);
                    bundle.putString("RdpIDX", list.get(EquipPosition).getRdpIDX());
                    bundle.putString("nodeCaseIDX", list.get(EquipPosition).getIdx());
                    bundle.putString("trainType", list.get(EquipPosition).getPartsName());
                    bundle.putString("TrainNo", list.get(EquipPosition).getUnloadTrainNo());
                    dialog.dismiss();
                    ActivityUtil.startActivityResultWithDelayed(PartsPlanListActivity.this, UpPartsActivity.class, bundle, 200);
                }
            });

            dialog = new AlertDialog.Builder(this).setView(view)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getProgressDialog().show();
                            int on, off;
                            if (list.get(EquipPosition).getPartsOnCount() == null) {
                                on = 0;
                            } else {
                                on = Integer.parseInt(list.get(EquipPosition).getPartsOnCount());
                            }
                            if (list.get(EquipPosition).getPartsOffCount() == null) {
                                off = 0;
                            } else {
                                off = Integer.parseInt(list.get(EquipPosition).getPartsOffCount());
                            }
                            mPresenter.CompletPlan(list.get(EquipPosition).getIdx(), list.get(EquipPosition).getPartsNo(), list.get(EquipPosition).getSpecificationModel(), off + "", on + "");
                        }
                    })
                    .setNegativeButton("取消", null).create();
        }

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
        setData();
        setDialog();
        adapter = new PartsPlanListAdapter(this, list);
        lvList.setAdapter(adapter);
        adapter.setOnBtnClickLisnter(this);
        setMenu();
        srRefresh.setRefreshHeader(new ClassicsHeader(this));
        srRefresh.setRefreshFooter(new ClassicsFooter(this));
        srRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                start = 0;
                isLoadMore = false;
                setData();
            }
        });
        srRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                start = start + limit;
                isLoadMore = true;
                setData();
            }
        });
        setOnScanCodeCallBack(this);
    }

    private void setMenu() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // add to menu

                SwipeMenuItem openItem2 = new SwipeMenuItem(
                        PartsPlanListActivity.this);
                // set item background
                openItem2.setBackground(new ColorDrawable(Color.rgb(0x3D,
                        0x5A, 0xFE)));
                // set item width
                openItem2.setWidth(dp2px(90));
                // set item title
                openItem2.setTitle("完工");
                // set item title fontsize
                openItem2.setTitleSize(18);
                // set item title font color
                openItem2.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem2);

            }
        };
        lvList.setMenuCreator(creator);
        lvList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                EquipPosition = position;
                switch (index) {
                    case 0:
                        getProgressDialog().show();
                        EquipPosition = position;
                        mPresenter.getPartsDown(list.get(position).getRdpIDX(), list.get(position).getIdx(), "1", position);
                        break;
                    case 1:
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        lvList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
    }

    String identificationCode = "";
    String partsNo = "";

    private void setData() {
        getProgressDialog().show();
        srRefresh.setNoMoreData(false);
        Map<String, String> map = new HashMap<>();
//        map.put("workPlanIDX", trainTypeIDX);
        map.put("status", "1");
        map.put("partsNo", partsNo);
        map.put("identificationCode", identificationCode);
//        map.put("nodeName", nodeName);
        mPresenter.GetRecanditionPlanList(JSON.toJSONString(map), start + "", limit + "", isLoadMore);
        identificationCode = "";
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
            ActivityUtil.startActivityWithDelayed(this, PartsHistoryActivity.class);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void OnBtnClick(final int position, String status) {
        EquipPosition = position;
        if (list.get(EquipPosition).getPartsNo() != null && !list.get(EquipPosition).getPartsNo().equals(""))
            etCode.setText(list.get(EquipPosition).getPartsNo());
//        dialog.show();
        mPresenter.getPartsDown(list.get(position).getRdpIDX(), list.get(position).getIdx(), "1", position);
    }

    @Override
    public void OnDownClick(int position) {
        Bundle bundle = new Bundle();
        String starttime = list.get(position).getPlanStartTime();
        String endTime = list.get(position).getPlanEndTime();
        if (starttime != null && endTime != null) {
            starttime = Utils.formatTime(Utils.stringToLong(starttime, "yyyy-MM-dd HH:mm:ss"), "MM-dd HH:mm");
            endTime = Utils.formatTime(Utils.stringToLong(endTime, "yyyy-MM-dd HH:mm:ss"), "MM-dd HH:mm");
        }
        bundle.putString("name", list.get(position).getWpNodeName());
        bundle.putString("startTime", starttime);
        bundle.putString("endTime", endTime);
        bundle.putString("RdpIDX", list.get(position).getRdpIDX());
        bundle.putString("nodeCaseIDX", list.get(position).getIdx());
        bundle.putString("trainType", list.get(position).getPartsName());
        bundle.putString("TrainNo", list.get(position).getUnloadPlace());
        bundle.putString("specificationModel", list.get(position).getSpecificationModel());
        ActivityUtil.startActivityResultWithDelayed(PartsPlanListActivity.this, DownPartsActivity.class, bundle, 200);
    }

    @Override
    public void OnUpClick(int position) {
        Bundle bundle = new Bundle();
        String starttime = list.get(EquipPosition).getPlanStartTime();
        String endTime = list.get(EquipPosition).getPlanEndTime();
        if (starttime != null && endTime != null) {
            starttime = Utils.formatTime(Utils.stringToLong(starttime, "yyyy-MM-dd HH:mm:ss"), "MM-dd HH:mm");
            endTime = Utils.formatTime(Utils.stringToLong(endTime, "yyyy-MM-dd HH:mm:ss"), "MM-dd HH:mm");
        }
        bundle.putString("name", list.get(EquipPosition).getWpNodeName());
        bundle.putString("startTime", starttime);
        bundle.putString("endTime", endTime);
        bundle.putString("RdpIDX", list.get(EquipPosition).getRdpIDX());
        bundle.putString("nodeCaseIDX", list.get(EquipPosition).getIdx());
        bundle.putString("trainType", list.get(EquipPosition).getPartsName());
        bundle.putString("TrainNo", list.get(EquipPosition).getUnloadPlace());
        ActivityUtil.startActivityResultWithDelayed(PartsPlanListActivity.this, UpPartsActivity.class, bundle, 200);
    }

    @Override
    public void getPartSPlanSuccess(List<PartsPlanBean> beans) {
        srRefresh.finishRefresh();
        getProgressDialog().dismiss();
        this.list.clear();
        if (beans != null) {
            list.addAll(beans);
            if (beans.size() < limit) {
                srRefresh.setNoMoreData(true);
            }
        }
        if (list.size() == 0) {
            llEmpty.setVisibility(View.VISIBLE);
        } else {
//            if(isScan){
//
//            }
            llEmpty.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getPartsPlanFaild(String msg) {
        srRefresh.finishLoadMore();
        srRefresh.finishRefresh();
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void getMorePartSPlanSuccess(List<PartsPlanBean> beans) {
        getProgressDialog().dismiss();
        srRefresh.finishLoadMore();
        if (beans != null) {
            list.addAll(beans);
            if (beans.size() < limit) {
                srRefresh.setNoMoreData(true);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void StartWorkSuccess() {
        dialog.dismiss();
        getProgressDialog().dismiss();
        setData();
        ToastUtil.toastShort("完工成功");
    }

    @Override
    public void StartWorkFaild(String msg) {
        dialog.dismiss();
        getProgressDialog().dismiss();
        setData();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void getUpEquipSuccess(int position, int size) {
        getProgressDialog().dismiss();
        if (size > 0) {
            if (dialog != null) {
                if (!Utils.isEmpty(list.get(EquipPosition).getSpecificationModel())) {
                    tvType.setText(list.get(EquipPosition).getSpecificationModel());
                }
                if (!Utils.isEmpty(list.get(EquipPosition).getPartsNo())) {
                    etCode.setText(list.get(EquipPosition).getPartsNo());
                    etCode.setEnabled(false);
                }
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.GONE);
                ToastUtil.toastShort("有配件未上车，不能完工！");
            }
        } else {
            if (!Utils.isEmpty(list.get(EquipPosition).getSpecificationModel())) {
                tvType.setText(list.get(EquipPosition).getSpecificationModel());
            }
            if (!Utils.isEmpty(list.get(EquipPosition).getPartsNo())) {
                etCode.setText(list.get(EquipPosition).getPartsNo());
                etCode.setEnabled(false);
            }
            if (list.get(EquipPosition).getPartsOffCount() == null) {
                llDownEquip.setVisibility(View.GONE);
            }
            if (list.get(EquipPosition).getPartsOnCount() == null) {
                llUpEquip.setVisibility(View.GONE);
            }
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getUpEquipFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void getDownEquipSuccess(int position, int size) {
        getProgressDialog().dismiss();
        if (size > 0) {
            if (dialog != null) {
                if (!Utils.isEmpty(list.get(EquipPosition).getSpecificationModel())) {
                    tvType.setText(list.get(EquipPosition).getSpecificationModel());
                }
                if (!Utils.isEmpty(list.get(EquipPosition).getPartsNo())) {
                    etCode.setText(list.get(EquipPosition).getPartsNo());
                    etCode.setEnabled(false);
                }
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.GONE);
                ToastUtil.toastShort("有配件未下车，不能完工！");
            }
        } else {
            if (!Utils.isEmpty(list.get(EquipPosition).getSpecificationModel())) {
                tvType.setText(list.get(EquipPosition).getSpecificationModel());
            }
            if (!Utils.isEmpty(list.get(EquipPosition).getPartsNo())) {
                etCode.setText(list.get(EquipPosition).getPartsNo());
                etCode.setEnabled(false);
            }
            dialog.show();
            mPresenter.getPartsUp(list.get(EquipPosition).getRdpIDX(), list.get(EquipPosition).getIdx(), "1", EquipPosition);
        }
    }

    @Override
    public void getDownEquipFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void ScanCodeSuccess(String code) {
        identificationCode = code;
        isScan = true;
        setData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }
}
