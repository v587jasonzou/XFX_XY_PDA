package jx.yunda.com.terminal.modules.takeCarConfirm.act;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.base.baseDictData.model.DicDataItem;
import jx.yunda.com.terminal.modules.takeCarConfirm.adapter.TakeCarAdapter;
import jx.yunda.com.terminal.modules.takeCarConfirm.entry.TakeCarBean;
import jx.yunda.com.terminal.modules.takeCarConfirm.entry.TakeReponse;
import jx.yunda.com.terminal.modules.takeCarConfirm.presenter.ITakeCarConfirm;
import jx.yunda.com.terminal.modules.takeCarConfirm.presenter.TakeCarConfirmPresenter;
import jx.yunda.com.terminal.utils.DateUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class TakeCarConfirmActivity extends BaseActivity<TakeCarConfirmPresenter> implements ITakeCarConfirm {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.lvNotifyList)
    ListView lvNotifyList;
    @BindView(R.id.svList)
    SmartRefreshLayout svList;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.llEmpty)
    LinearLayout llEmpty;
    List<TakeCarBean> mlist = new ArrayList<>();
    TakeCarAdapter adapter ;
    AlertDialog dialog ;
    int EquipPosition = 0;
    @Override
    protected TakeCarConfirmPresenter getPresenter() {
        return new TakeCarConfirmPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_take_car_confirm;
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
        getProgressDialog().show();
        mPresenter.getUnConfirmCarList();
        adapter = new TakeCarAdapter(this,mlist);
        lvNotifyList.setAdapter(adapter);
        adapter.setOnConfirmClickListner(new TakeCarAdapter.OnConfirmClickListner() {
            @Override
            public void OnConfirmClick(int position) {
                EquipPosition = position;
                if(dialog!=null){
                    if(mlist.get(position)!=null&&mlist.get(position).getDestination()!=null){
                        tvEndLocaton.setText(mlist.get(position).getDestination());
                    }else {
                        tvEndLocaton.setText("请选择");
                    }
                    dialog.show();
                }
            }
        });
        svList.setRefreshHeader(new ClassicsHeader(this));
        svList.setRefreshFooter(new ClassicsFooter(this));
        svList.setNoMoreData(true);
        svList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getProgressDialog().show();
                mPresenter.getUnConfirmCarList();
            }
        });
        llEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProgressDialog().show();
                mPresenter.getUnConfirmCarList();
            }
        });
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_takecar,null);
        tvEndLocaton = (TextView)view.findViewById(R.id.tvEndLocaton);
        dialog = new AlertDialog.Builder(this).setTitle("确定提交当前牵车通知单？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TakeCarBean bean = mlist.get(EquipPosition);
                        TakeReponse reponse = new TakeReponse();
                        if(bean!=null){
                            reponse.setConfirmStatus(1);
                            reponse.setConfirmPersonName(SysInfo.userInfo.emp.getEmpname());
                            reponse.setConfirmPersonId(SysInfo.userInfo.emp.getEmpid()+"");
                            reponse.setConfirmTime(DateUtil.date2String(new Date(),"yyyy-MM-dd HH:mm"));
                            reponse.setDestination(bean.getDestination());
                            reponse.setHomePosition(bean.getHomePosition());
                            reponse.setIdx(bean.getIdx());
                            reponse.setJsonAcceptPerson(bean.getJsonAcceptPerson());
                            reponse.setLastTime(bean.getLastTime());
                            reponse.setNoticeTime(bean.getNoticeTime());
                            reponse.setRemark(bean.getRemark());
                            reponse.setTrainPlanIdx(bean.getTrainPlanIdx());
                            reponse.setUpdateTime(bean.getUpdateTime());
                            reponse.setUpdator(bean.getUpdator());
                            reponse.setUpdatorName(bean.getUpdatorName());
                            mPresenter.SubmitTakeCar(bean.getGroupId(), JSON.toJSONString(reponse));
                            getProgressDialog().show();
                        }
                    }
                }).setNegativeButton("取消",null).setView(view)
                .create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                tvEndLocaton.setText("请选择");
                adapter.notifyDataSetChanged();
            }
        });
        tvEndLocaton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(positions.size()==0){
                    getProgressDialog().show();
                    mPresenter.getPositions();
                }else {
                    popupMenu.show();
                }
            }
        });
    }
    TextView tvEndLocaton;
    @Override
    public void OnLoadConfirmListSuccess(List<TakeCarBean> list) {
        svList.finishRefresh();
        getProgressDialog().dismiss();
        mlist.clear();
        if(list!=null&&list.size()>0){
            mlist.addAll(list);
            llEmpty.setVisibility(View.GONE);
        }else {
            ToastUtil.toastShort("无相关牵车通知单信息");
            llEmpty.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnLoadFaild(String msg) {
        if(mlist.size()==0){
            llEmpty.setVisibility(View.GONE);
        }
        svList.finishRefresh();
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }
    List<DicDataItem> positions = new ArrayList<>();
    PopupMenu popupMenu;
    @Override
    public void OnLoadStationsSuccess(List<DicDataItem> list) {
        positions.clear();
        if(list!=null&&list.size()>0){
            positions.addAll(list);
            if(popupMenu==null){
                popupMenu = new PopupMenu(this, tvEndLocaton);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(tvEndLocaton.getWindowToken(), 0);
                        }
                        tvEndLocaton.setText(item.getTitle().toString());
                        mlist.get(EquipPosition).setDestination(item.getTitle().toString());
                        popupMenu.dismiss();
                        return false;
                    }
                });
                android.view.Menu menu_more = popupMenu.getMenu();
                menu_more.clear();
                int size = positions.size();
                for (int i = 0; i < size; i++) {
                    menu_more.add(android.view.Menu.NONE,  i, i, positions.get(i).getDictname());
                }
            }
            popupMenu.show();
        }

        getProgressDialog().dismiss();
    }

    @Override
    public void ConfirmSuccess() {
        ToastUtil.toastShort("确认成功");
        mPresenter.getUnConfirmCarList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
