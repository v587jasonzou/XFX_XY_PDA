package jx.yunda.com.terminal.modules.TrainRecandition.act;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import jx.yunda.com.terminal.modules.TrainRecandition.adapter.RecanditionListAdapter;
import jx.yunda.com.terminal.modules.TrainRecandition.model.RecanditionBean;
import jx.yunda.com.terminal.modules.TrainRecandition.presenter.IRecanditionList;
import jx.yunda.com.terminal.modules.TrainRecandition.presenter.RecanditionListPresenter;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

public class RecanditionListActivity extends BaseActivity<RecanditionListPresenter> implements IRecanditionList, RecanditionListAdapter.OnBtnClickLisnter {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.lvList)
    SwipeMenuListView lvList;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    @BindView(R.id.llEmpty)
    LinearLayout llEmpty;
    List<RecanditionBean> list = new ArrayList<>();
    RecanditionListAdapter adapter;
    String trainTypeIDX, ShortName, TrainNo;
    String nodeName = "";
    public static String STATUS_COMPLETED = "COMPLETED";
    public static String STATUS_NOTSTARTED = "NOTSTARTED";
    public static String STATUS_RUNNING = "RUNNING";
    int start = 0;
    int limit = 8;
    boolean isLoadMore = false;
    AlertDialog dialog;
    TextView tvTitle;
    LinearLayout llDownEquip;
    LinearLayout llUpEquip;
    int EquipPosition;
    String function="";
    @Override
    protected RecanditionListPresenter getPresenter() {
        return new RecanditionListPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_recandition_list;
    }

    @Override
    public void initSubViews(View view) {

    }

    private void setDialog() {
        if (dialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_recandition_select, null);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            llDownEquip = (LinearLayout) view.findViewById(R.id.llDownEquip);
            llDownEquip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Trainname", menuTp.getTitle().toString());
                    bundle.putString("name", list.get(EquipPosition).getNodeName());
                    bundle.putString("startTime", Utils.formatTime(list.get(EquipPosition).getPlanBeginTime(), "MM-dd HH:mm"));
                    bundle.putString("endTime", Utils.formatTime(list.get(EquipPosition).getPlanEndTime(), "MM-dd HH:mm"));
                    bundle.putString("workPlanId", list.get(EquipPosition).getWorkPlanIDX());
                    bundle.putString("nodeCaseIDX", list.get(EquipPosition).getIdx());
                    bundle.putString("trainTypeIDX", list.get(EquipPosition).getTrainTypeIdx());
                    bundle.putString("ShortName", ShortName);
                    bundle.putString("TrainNo", TrainNo);
                    dialog.dismiss();
                    ActivityUtil.startActivityResultWithDelayed(RecanditionListActivity.this, DownEquipActivity.class, bundle, 200);
                }
            });
            llUpEquip = (LinearLayout) view.findViewById(R.id.llUpEquip);
            llUpEquip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Trainname", menuTp.getTitle().toString());
                    bundle.putString("name", list.get(EquipPosition).getNodeName());
                    bundle.putString("startTime", Utils.formatTime(list.get(EquipPosition).getPlanBeginTime(), "MM-dd HH:mm"));
                    bundle.putString("endTime", Utils.formatTime(list.get(EquipPosition).getPlanEndTime(), "MM-dd HH:mm"));
                    bundle.putString("workPlanId", list.get(EquipPosition).getWorkPlanIDX());
                    bundle.putString("nodeCaseIDX", list.get(EquipPosition).getIdx());
                    bundle.putString("trainTypeIDX", list.get(EquipPosition).getTrainTypeIdx());
                    bundle.putString("ShortName", ShortName);
                    bundle.putString("TrainNo", TrainNo);
                    dialog.dismiss();
                    ActivityUtil.startActivityResultWithDelayed(RecanditionListActivity.this, UpEquipActivity.class, bundle, 200);
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
                            mPresenter.CompletePlan(list.get(EquipPosition).getIdx(), on, off, SysInfo.userInfo.emp.getOperatorid() + "", EquipPosition);
                        }
                    })
                    .setNegativeButton("取消", null).create();
        }
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        trainTypeIDX = bundle.getString("trainTypeIDX");
        ShortName = bundle.getString("ShortName");
        TrainNo = bundle.getString("TrainNo");
        if(bundle.getString("function")!=null){
            function = bundle.getString("function");
        }
        menuTp.setTitle(bundle.getString("ShortName") + " " + bundle.getString("TrainNo"));
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
                setData();
            }
        });
        setData();
        setDialog();
        adapter = new RecanditionListAdapter(this, list);
        lvList.setAdapter(adapter);
        lvList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                EquipPosition = position;
                if (list != null && list.size() > 0) {
                    if (menu.getViewType() == 0) {
                        new AlertDialog.Builder(RecanditionListActivity.this).setTitle("确认开工").setMessage("确认开工吗？")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getProgressDialog().show();
                                        mPresenter.startWork(list.get(EquipPosition).getIdx(), EquipPosition);
                                    }
                                }).show();
                    } else {
                        if (list.get(EquipPosition).getPartsOffCount() == null) {
                            llDownEquip.setVisibility(View.GONE);
                        } else {
                            llDownEquip.setVisibility(View.VISIBLE);
                        }
                        if (list.get(EquipPosition).getPartsOnCount() == null) {
                            llUpEquip.setVisibility(View.GONE);
                        } else {
                            llUpEquip.setVisibility(View.VISIBLE);
                        }
                        mPresenter.getDownEquips(list.get(EquipPosition).getWorkPlanIDX(), list.get(EquipPosition).getIdx()
                                , "1", EquipPosition);
                    }
                }
                return false;
            }
        });
        adapter.setOnBtnClickLisnter(this);
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
    }

    int a = 0;

    private void setSwipMenu() {
        if (list != null && list.size() > 0) {
            a = 0;
            SwipeMenuCreator creator = new SwipeMenuCreator() {
                @Override
                public void create(SwipeMenu menu) {
                    SwipeMenuItem openItem2 = new SwipeMenuItem(
                            RecanditionListActivity.this);
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
                    openItem2.setTitleColor(0xFFFFFFFF);
                    menu.setViewType(1);
                    menu.addMenuItem(openItem2);
//                    if (list.size() > a) {
//                        if (list.get(a).getStatus().equals(RecanditionListActivity.STATUS_NOTSTARTED)) {
//                            SwipeMenuItem openItem2 = new SwipeMenuItem(
//                                    RecanditionListActivity.this);
//                            // set item background
//                            openItem2.setBackground(new ColorDrawable(Color.rgb(0xFF,
//                                    0xFF, 0xFF)));
//                            // set item width
//                            openItem2.setWidth(dp2px(90));
//                            // set item title
//                            openItem2.setTitle("开工");
//                            // set item title fontsize
//                            openItem2.setTitleSize(18);
//                            // set item title font color
//                            openItem2.setTitleColor(0xFF3D5AFE);
//                            menu.setViewType(0);
//                            menu.addMenuItem(openItem2);
//                        } else {
//                            SwipeMenuItem openItem2 = new SwipeMenuItem(
//                                    RecanditionListActivity.this);
//                            // set item background
//                            openItem2.setBackground(new ColorDrawable(Color.rgb(0x3D,
//                                    0x5A, 0xFE)));
//                            // set item width
//                            openItem2.setWidth(dp2px(90));
//                            // set item title
//                            openItem2.setTitle("完工");
//                            // set item title fontsize
//                            openItem2.setTitleSize(18);
//                            // set item title font color
//                            openItem2.setTitleColor(0xFFFFFFFF);
//                            menu.setViewType(1);
//                            menu.addMenuItem(openItem2);
//                        }
//                        a++;
//                    }


                }
            };
            lvList.setMenuCreator(creator);
        }
    }

    private void setData() {
        getProgressDialog().show();
        srRefresh.setNoMoreData(false);
        Map<String, String> map = new HashMap<>();
        map.put("workPlanIDX", trainTypeIDX);
        map.put("status", "1");
        map.put("nodeName", nodeName);
        mPresenter.GetRecanditionPlanList(JSON.toJSONString(map), start + "", limit + "", "false"
                , SysInfo.userInfo.emp.getEmpid() + "",function, isLoadMore);
    }

    @Override
    public void OnLoadPlanSuccess(List<RecanditionBean> recanditionBeans) {
        getProgressDialog().dismiss();
        list.clear();
        srRefresh.finishRefresh();
        if (recanditionBeans != null) {
            list.addAll(recanditionBeans);
            if (recanditionBeans.size() < limit) {
                srRefresh.setNoMoreData(true);
            }
        }
        if (list.size() == 0) {
            llEmpty.setVisibility(View.VISIBLE);
        } else {
            llEmpty.setVisibility(View.GONE);
        }
//        adapter = new RecanditionListAdapter(this,list);
//        lvList.setAdapter(adapter);
//        adapter.setOnBtnClickLisnter(this);
//        setSwipMenu();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void OnLoadMoreOlanSuccess(List<RecanditionBean> recanditionBeans) {
        getProgressDialog().dismiss();
        srRefresh.finishLoadMore();
        if (recanditionBeans != null) {
            list.addAll(recanditionBeans);
            if (recanditionBeans.size() < limit) {
                srRefresh.setNoMoreData(true);
            }
        }
//        adapter = new RecanditionListAdapter(this,list);
//        lvList.setAdapter(adapter);
//        adapter.setOnBtnClickLisnter(this);
//        setSwipMenu();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void OnLoadPlanFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void OnStartPlanSuccess(int position) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(list.get(EquipPosition).getNodeName() + "已开工");
        Bundle bundle = new Bundle();
        bundle.putString("nodeidx", list.get(EquipPosition).getIdx());
        bundle.putString("type", list.get(EquipPosition).getTrainTypeShortName());
        bundle.putString("trainNo", list.get(EquipPosition).getTrainNo());
        bundle.putString("nodeName", list.get(EquipPosition).getNodeName());
        bundle.putString("typeIdx",list.get(EquipPosition).getTrainTypeIdx());
        if (list.get(position).getDictid() != null)
            bundle.putString("dictid", list.get(EquipPosition).getDictid());
        if (list.get(position).getDictname() != null)
            bundle.putString("dictname", list.get(EquipPosition).getDictname());
        if (list.get(position).getFilter2() != null)
            bundle.putString("filter2", list.get(EquipPosition).getFilter2());
        if(list.get(position).getProfessionTypeIdx()!=null){
            bundle.putString("professionTypeIdx",list.get(position).getProfessionTypeIdx());
        }
        ActivityUtil.startActivityResultWithDelayed(RecanditionListActivity.this, WorkCardActivity.class, bundle, 200);
//        start = 0;
//        list.clear();
//        setData();
    }

    @Override
    public void OnStartPlanFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void OnCompletPlanSuccess() {
        getProgressDialog().dismiss();
        ToastUtil.toastShort("完工成功");
        start = 0;
        list.clear();
        setData();
    }

    @Override
    public void OnCompletPlanFaild(String msg, int position) {
        if (msg.contains("参数错误")) {
            mPresenter.getDownEquips(list.get(position).getWorkPlanIDX(), list.get(position).getIdx()
                    , "1", position);
        } else {
            getProgressDialog().dismiss();
            ToastUtil.toastShort(msg);
        }
    }

    @Override
    public void getUpPlanSuccess(int size, final int position) {
        getProgressDialog().dismiss();
        if (size > 0) {
            tvTitle.setText("有配件未上车登记，不能完工！");
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.GONE);
        } else {
//            dialog.setTitle("提示,确定完工？");
            tvTitle.setText("确定完工？");

            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getUpPlanFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void getDownPlanSuccess(int size, final int position) {
        if (size > 0) {
            tvTitle.setText("有配件未下车，不能完工！");
            dialog.show();
            getProgressDialog().dismiss();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.GONE);
        } else {
            mPresenter.getUpEquips(list.get(position).getWorkPlanIDX(), list.get(position).getIdx()
                    , "1", position);
        }
    }

    @Override
    public void getDownPlanFaild(String masg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(masg);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 300 || resultCode == 203) {
            setData();
        }
    }

    @Override
    public void OnBtnClick(int position, String status) {
        EquipPosition = position;
        Bundle bundle = new Bundle();
        bundle.putString("nodeidx", list.get(position).getIdx());
        bundle.putString("type", list.get(position).getTrainTypeShortName());
        bundle.putString("trainNo", list.get(position).getTrainNo());
        bundle.putString("nodeName", list.get(position).getNodeName());
        bundle.putString("typeIdx",list.get(position).getTrainTypeIdx());
        if (list.get(position).getDictid() != null)
            bundle.putString("dictid", list.get(position).getDictid());
        if (list.get(position).getDictname() != null)
            bundle.putString("dictname", list.get(position).getDictname());
        if (list.get(position).getFilter2() != null)
            bundle.putString("filter2", list.get(position).getFilter2());
        if(list.get(position).getProfessionTypeIdx()!=null){
            bundle.putString("professionTypeIdx",list.get(position).getProfessionTypeIdx());
        }

        ActivityUtil.startActivityResultWithDelayed(RecanditionListActivity.this, WorkCardActivity.class, bundle, 200);
//        if (STATUS_NOTSTARTED.equals(status)) {
//            new AlertDialog.Builder(this).setTitle("确认开工").setMessage("确认开工吗？")
//                    .setNegativeButton("取消", null)
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            getProgressDialog().show();
//                            mPresenter.startWork(list.get(EquipPosition).getIdx(), EquipPosition);
//                        }
//                    }).show();
//
//        } else if (STATUS_RUNNING.equals(status)) {
////            if(list.get(EquipPosition).getPartsOffCount()==null){
////                llDownEquip.setVisibility(View.GONE);
////            }else {
////                llDownEquip.setVisibility(View.VISIBLE);
////            }
////            if(list.get(EquipPosition).getPartsOnCount()==null){
////                llUpEquip.setVisibility(View.GONE);
////            }else {
////                llUpEquip.setVisibility(View.VISIBLE);
////            }
////            mPresenter.getDownEquips(list.get(position).getWorkPlanIDX(), list.get(position).getIdx()
////                    , "1", position);
//            Bundle bundle = new Bundle();
//            bundle.putString("nodeidx", list.get(position).getIdx());
//            bundle.putString("type", list.get(position).getTrainTypeShortName());
//            bundle.putString("trainNo", list.get(position).getTrainNo());
//            bundle.putString("nodeName", list.get(position).getNodeName());
//            bundle.putString("typeIdx",list.get(position).getTrainTypeIdx());
//            if (list.get(position).getDictid() != null)
//                bundle.putString("dictid", list.get(position).getDictid());
//            if (list.get(position).getDictname() != null)
//                bundle.putString("dictname", list.get(position).getDictname());
//            if (list.get(position).getFilter2() != null)
//                bundle.putString("filter2", list.get(position).getFilter2());
//            if(list.get(position).getProfessionTypeIdx()!=null){
//                bundle.putString("professionTypeIdx",list.get(position).getProfessionTypeIdx());
//            }
//
//            ActivityUtil.startActivityResultWithDelayed(RecanditionListActivity.this, WorkCardActivity.class, bundle, 200);
//        }
    }

    @Override
    public void OnDownClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("Trainname", menuTp.getTitle().toString());
        bundle.putString("name", list.get(position).getNodeName());
        bundle.putString("startTime", Utils.formatTime(list.get(position).getPlanBeginTime(), "MM-dd HH:mm"));
        bundle.putString("endTime", Utils.formatTime(list.get(position).getPlanEndTime(), "MM-dd HH:mm"));
        bundle.putString("workPlanId", list.get(position).getWorkPlanIDX());
        bundle.putString("nodeCaseIDX", list.get(position).getIdx());
        bundle.putString("trainTypeIDX", list.get(position).getTrainTypeIdx());
        bundle.putString("ShortName", ShortName);
        bundle.putString("TrainNo", TrainNo);
        dialog.dismiss();
        ActivityUtil.startActivityResultWithDelayed(RecanditionListActivity.this, DownEquipActivity.class, bundle, 200);
    }

    @Override
    public void OnUpClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("Trainname", menuTp.getTitle().toString());
        bundle.putString("name", list.get(position).getNodeName());
        bundle.putString("startTime", Utils.formatTime(list.get(position).getPlanBeginTime(), "MM-dd HH:mm"));
        bundle.putString("endTime", Utils.formatTime(list.get(position).getPlanEndTime(), "MM-dd HH:mm"));
        bundle.putString("workPlanId", list.get(position).getWorkPlanIDX());
        bundle.putString("nodeCaseIDX", list.get(position).getIdx());
        bundle.putString("trainTypeIDX", list.get(position).getTrainTypeIdx());
        bundle.putString("ShortName", ShortName);
        bundle.putString("TrainNo", TrainNo);
        dialog.dismiss();
        ActivityUtil.startActivityResultWithDelayed(RecanditionListActivity.this, UpEquipActivity.class, bundle, 200);
    }

    @Override
    protected void onResume() {
        super.onResume();
        start = 0;
        setData();
    }
}
