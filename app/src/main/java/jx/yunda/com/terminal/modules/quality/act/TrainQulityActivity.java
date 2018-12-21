package jx.yunda.com.terminal.modules.quality.act;

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
import android.widget.EditText;

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
import jx.yunda.com.terminal.modules.quality.adapter.TrainAdapter;
import jx.yunda.com.terminal.modules.quality.adapter.TrainQualityadapter;
import jx.yunda.com.terminal.modules.quality.model.TrainQualityBean;
import jx.yunda.com.terminal.modules.quality.presenter.ITrainQuality;
import jx.yunda.com.terminal.modules.quality.presenter.TrainQualityPresenter;
import jx.yunda.com.terminal.utils.ToastUtil;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

public class TrainQulityActivity extends BaseActivity<TrainQualityPresenter> implements ITrainQuality {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.lvRecheck)
    SwipeMenuListView lvRecheck;
    @BindView(R.id.srlRefresh)
    SmartRefreshLayout srlRefresh;
    int start = 0;
    int limit = 8;
    boolean isLoadMore = false;
    int state =0;
    List<TrainQualityBean> mList = new ArrayList<>();
    TrainQualityadapter adapter;
    @Override
    protected TrainQualityPresenter getPresenter() {
        return new TrainQualityPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_train_quality_list;
    }

    @Override
    public void initSubViews(View view) {

    }

    AlertDialog dialog;
    EditText etsuggestion;
    String workPlanId;
    int EuipPosition;
    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            menuTp.setTitle(bundle.getString("planName"));
            workPlanId = bundle.getString("workPlanId");
        }
        setDialog();
        adapter = new TrainQualityadapter(this,mList);
        lvRecheck.setAdapter(adapter);
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        srlRefresh.setRefreshHeader(new ClassicsHeader(this));
        srlRefresh.setRefreshFooter(new ClassicsFooter(this));
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
                start = start+limit;
                isLoadMore = true;
                setData();
            }
        });
        setData();
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xFF,
                        0x57, 0x22)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("返修");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu

                SwipeMenuItem openItem2 = new SwipeMenuItem(
                        getApplicationContext());
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
                menu.addMenuItem(openItem);
                menu.addMenuItem(openItem2);

            }
        };
        lvRecheck.setMenuCreator(creator);
        lvRecheck.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        state = 0;
                        EuipPosition = position;
                        dialog.setTitle("请填写质量检查结果");
                        dialog.show();
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(etsuggestion.getText()==null&&etsuggestion.getText().toString().equals("")){
                                    ToastUtil.toastShort("还未输入处理结果");
                                    return;
                                }else {
                                    getProgressDialog().show();
                                    Map<String,Object> map = new HashMap<>();
                                    map.put("nodeIdx",mList.get(EuipPosition).getNodeIdx());
                                    map.put("checkItemCode",mList.get(EuipPosition).getCheckItemCode());
                                    map.put("checkItemName",mList.get(EuipPosition).getCheckItemName());
                                    Map[] maps = new Map[1];
                                    maps[0] = map;
                                    mPresenter.BackQuality(JSON.toJSONString(maps),etsuggestion.getText().toString());
                                    dialog.dismiss();
                                }
                            }
                        });
                        break;
                    case 1:
                        state = 1;
                        EuipPosition = position;
                        dialog.setTitle("请填写质量检查结果");
                        dialog.show();
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(etsuggestion.getText()==null&&etsuggestion.getText().toString().equals("")){
                                    ToastUtil.toastShort("还未输入处理结果");
                                    return;
                                }else {
                                    getProgressDialog().show();
                                    Map<String,Object> map1 = new HashMap<>();
                                    String checkWay = String.valueOf(mList.get(EuipPosition).getCheckWay());
                                    map1.put("nodeIdx",mList.get(EuipPosition).getNodeIdx());
                                    map1.put("checkItemCode",mList.get(EuipPosition).getCheckItemCode());
                                    map1.put("checkItemName",mList.get(EuipPosition).getCheckItemName());
                                    Map[] maps1 = new Map[1];
                                    maps1[0] = map1;
                                    mPresenter.PassQuality(checkWay,etsuggestion.getText().toString(),JSON.toJSONString(maps1));
                                    dialog.dismiss();
                                }
                            }
                        });
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        lvRecheck.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
    }

    private void setDialog() {
        View view  = LayoutInflater.from(this).inflate(R.layout.quality_dialog_suggestion,null);
        etsuggestion = (EditText)view.findViewById(R.id.etsuggestion);
        dialog = new AlertDialog.Builder(this).setView(view).setNegativeButton("取消",null)
               .setPositiveButton("确定",null).create();

    }
    private void setData() {
        srlRefresh.setNoMoreData(false);
        getProgressDialog().show();
        mPresenter.getPlans(start+"",limit+"","",workPlanId, SysInfo.userInfo.emp.getOperatorid()+"","1",isLoadMore);
    }

    @Override
    public void OnLoadPlansSuccess(List<TrainQualityBean> list) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadMore();
        getProgressDialog().dismiss();
        mList.clear();
        if(list!=null&&list.size()>0){
            mList.addAll(list);
            if (list.size()<limit){
                srlRefresh.setNoMoreData(true);
            }
        }else {
            srlRefresh.setNoMoreData(true);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnLoadPlansFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadMore();
    }

    @Override
    public void OnLoadMorePlansSuccess(List<TrainQualityBean> list) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadMore();
        getProgressDialog().dismiss();
        if(list!=null&&list.size()>0){
            mList.addAll(list);
            if(list.size()<limit){
                srlRefresh.setNoMoreData(true);
            }
        }else {
            srlRefresh.setNoMoreData(true);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnSubmitSuccess() {
        getProgressDialog().dismiss();
        ToastUtil.toastShort("提交成功");
        isLoadMore = false;
        start = 0;
        setData();
    }

    @Override
    public void OnSubmitFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort("提交失败:"+msg);
    }

    @Override
    public void OnDoBackSuccess() {
        getProgressDialog().dismiss();
        ToastUtil.toastShort("返修成功");
        isLoadMore = false;
        start = 0;
        setData();
    }

    @Override
    public void OnDoBackFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort("返修失败:"+msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
