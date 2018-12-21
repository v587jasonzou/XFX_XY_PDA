package jx.yunda.com.terminal.modules.quality.frg;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import butterknife.Unbinder;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.modules.quality.act.QualityActivity;
import jx.yunda.com.terminal.modules.quality.adapter.TrainEquipadapter;
import jx.yunda.com.terminal.modules.quality.model.NodeBean;
import jx.yunda.com.terminal.modules.quality.model.QualityEquipPlanBean;
import jx.yunda.com.terminal.modules.quality.presenter.EquipQualityPresenter;
import jx.yunda.com.terminal.modules.quality.presenter.IEquipQuality;
import jx.yunda.com.terminal.utils.ToastUtil;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

public class Equipfrg extends BaseFragment<EquipQualityPresenter> implements IEquipQuality,BaseActivity.OnScanCodeCallBack {
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.lvRecheck)
    SwipeMenuListView lvRecheck;
    @BindView(R.id.srlRefresh)
    SmartRefreshLayout srlRefresh;
    Unbinder unbinder;
    int start = 0;
    int limit = 20;
    boolean isLoadMore = false;
    TrainEquipadapter adapter;
    QualityActivity act;
    List<QualityEquipPlanBean> mList = new ArrayList<>();
    String specificationModel = "";
    String identificationCode = "";
    int EquipPosition;
    @BindView(R.id.llEmpty)
    LinearLayout llEmpty;

    @Override
    protected EquipQualityPresenter getPresenter() {
        return new EquipQualityPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_equip_quality_list;
    }

    @Override
    public void initSubViews(View view) {

    }

    public void setData(String specificationModel, String identificationCode) {
        srlRefresh.setNoMoreData(false);
        act.getProgressDialog().show();
        Map<String, Object> map = new HashMap<>();
        map.put("specificationModel", specificationModel);
        map.put("identificationCode", identificationCode);
        mPresenter.getPlans(start + "", limit + "", "2", JSON.toJSONString(map), SysInfo.userInfo.emp.getOperatorid() + "", "1"
                , isLoadMore);
    }

    @Override
    public void initData() {
        act = ((QualityActivity) getActivity());
        adapter = new TrainEquipadapter(getContext(), mList);
        lvRecheck.setAdapter(adapter);
        act.setOnScanCodeCallBack(this);
        setData("", "");
        srlRefresh.setRefreshHeader(new ClassicsHeader(getContext()));
        srlRefresh.setRefreshFooter(new ClassicsFooter(getContext()));
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                start = 0;
                isLoadMore = false;
                setData(specificationModel, identificationCode);
            }
        });
        srlRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                start = start + limit;
                isLoadMore = true;
                setData(specificationModel, identificationCode);
            }
        });
        setDialog();
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getContext());
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
                menu.addMenuItem(openItem);
                menu.addMenuItem(openItem2);

            }
        };
        lvRecheck.setMenuCreator(creator);
        lvRecheck.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                EquipPosition = position;
                switch (index) {
                    case 0:
                        act.getProgressDialog().show();
                        mPresenter.getNodes(mList.get(position).getRdpIdx());
                        break;
                    case 1:
                        act.getProgressDialog().show();
                        Map<String, Object> map = new HashMap<>();
                        map.put("rdpIdx", mList.get(position).getRdpIdx());
                        map.put("qcItemNo", mList.get(position).getQcItemNo());
                        List<Map<String, Object>> temp = new ArrayList<>();
                        temp.add(map);
                        mPresenter.PassQuality(JSON.toJSONString(temp));
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        lvRecheck.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        setEditText();
        llEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData("","");
            }
        });
    }
    AlertDialog dialog;
    EditText etsuggestion;
    private void setDialog() {
        View view  = LayoutInflater.from(getContext()).inflate(R.layout.quality_dialog_suggestion,null);
        etsuggestion = (EditText)view.findViewById(R.id.etsuggestion);
        dialog = new AlertDialog.Builder(getContext()).setView(view).setNegativeButton("取消",null)
                .setPositiveButton("确定",null).create();

    }
    private void setEditText() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“GO”键*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(
                                v.getApplicationWindowToken(), 0);
                    }
                    if (etSearch.getText() != null) {
                        isLoadMore = false;
                        start = 0;
                        setData(etSearch.getText().toString(), "");
                    } else {
                        isLoadMore = false;
                        start = 0;
                        setData("", "");
                    }

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void OnLoadPlansSuccess(List<QualityEquipPlanBean> list) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadMore();
        act.getProgressDialog().dismiss();
        mList.clear();
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        adapter = new TrainEquipadapter(act,mList);
        lvRecheck.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (list == null || list.size() < limit) {
            srlRefresh.setNoMoreData(true);
        }
        if(list==null||list.size()==0){
            llEmpty.setVisibility(View.VISIBLE);
        }else {
            llEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnLoadPlansFaild(String msg) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadMore();
        act.getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void OnLoadMorePlansSuccess(List<QualityEquipPlanBean> list) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadMore();
        act.getProgressDialog().dismiss();
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        adapter.notifyDataSetChanged();
        if (list == null || list.size() < limit) {
            srlRefresh.setNoMoreData(true);
        }
    }

    @Override
    public void OnSubmitSuccess() {
        act.getProgressDialog().dismiss();
        ToastUtil.toastShort("通过成功");
        start = 0;
        isLoadMore = false;
        setData("", "");
        act.setData();
    }

    @Override
    public void OnSubmitFaild(String msg) {
        act.getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void OnDoBackSuccess() {
        act.getProgressDialog().dismiss();
        ToastUtil.toastShort("返修成功");
        start = 0;
        isLoadMore = false;
        setData("", "");
        act.setData();
    }

    @Override
    public void OnDoBackFaild(String msg) {
        act.getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void getNodesSuccess(final List<NodeBean> list) {
        act.getProgressDialog().dismiss();
        dialog.setTitle("请输入返修内容");
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etsuggestion.getText()==null&&etsuggestion.getText().toString().equals("")){
                    ToastUtil.toastShort("还未输入返修内容");
                    return;
                }else {
                    dialog.dismiss();
                    act.getProgressDialog().show();
                    if (list != null && list.size() > 0) {
                        ArrayList<String> arr = new ArrayList<>();
                        for (NodeBean nodeBean : list) {
                            arr.add(nodeBean.getIdx());
                        }
                        mPresenter.BackQuality(JSON.toJSONString(arr), mList.get(EquipPosition).getQcItemNo(), "返修");
                    } else {
                        mPresenter.BackQuality("", mList.get(EquipPosition).getQcItemNo(), "返修");
                    }
                }
            }
        });

    }

    @Override
    public void getNodesFaild(String msg) {
        act.getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
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
    public void ScanCodeSuccess(String code) {
        isLoadMore = false;
        start = 0;
        setData("",code);
    }
}
