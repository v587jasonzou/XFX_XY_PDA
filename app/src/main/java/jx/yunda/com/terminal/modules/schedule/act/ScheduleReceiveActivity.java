package jx.yunda.com.terminal.modules.schedule.act;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.schedule.adapter.BookUserAdapter;
import jx.yunda.com.terminal.modules.schedule.adapter.ReceiveSchedulAdapter;
import jx.yunda.com.terminal.modules.schedule.entity.ReceiveTrainBean;
import jx.yunda.com.terminal.modules.schedule.entity.ReceiveUserBean;
import jx.yunda.com.terminal.modules.schedule.presenter.IReceiveSchedule;
import jx.yunda.com.terminal.modules.schedule.presenter.ReceiveSchedulePresenter;
import jx.yunda.com.terminal.utils.ToastUtil;

public class ScheduleReceiveActivity extends BaseActivity<ReceiveSchedulePresenter> implements IReceiveSchedule {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.rlUsers)
    ListView rlUsers;
    BookUserAdapter userAdapter;
    ReceiveSchedulAdapter adapter;
    List<ReceiveTrainBean> mList = new ArrayList<>();
    List<ReceiveUserBean> mUsers = new ArrayList<>();
    AlertDialog dialog ;
    ListView lvUsers;
    int EquipPosition = 0;
    @Override
    protected ReceiveSchedulePresenter getPresenter() {
        return new ReceiveSchedulePresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_schedule_receive;
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
        adapter = new ReceiveSchedulAdapter(this,mList);
        rlUsers.setAdapter(adapter);
        adapter.setOnBookClickLisnter(new ReceiveSchedulAdapter.OnBookClickListner() {
            @Override
            public void OnBookClick(int position) {
                if(mUsers.size()==0){
                    ToastUtil.toastShort("无相关调度人员可以选择");
                }else {
                    EquipPosition = position;
                    dialog.show();
                }
            }
        });
        getProgressDialog().show();
        mPresenter.getTrainList();
    }

    @Override
    public void getTrainsSuccess(List<ReceiveTrainBean> list) {
        getProgressDialog().dismiss();
        mList.clear();
        if(list!=null&&list.size()>0){
            mList.addAll(list);
        }
        adapter.notifyDataSetChanged();
        getProgressDialog().show();
        mPresenter.getUserList();
    }

    @Override
    public void getUsersSuccess(List<ReceiveUserBean> list) {
        getProgressDialog().dismiss();
        mUsers.clear();
        if(list!=null&&list.size()>0){
            mUsers.addAll(list);
        }
        if(dialog == null){
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_base_user,null);
            lvUsers = (ListView)view.findViewById(R.id.lvUsers);
            dialog = new AlertDialog.Builder(this).setTitle("请选择").setView(view).create();
            userAdapter = new BookUserAdapter(this,mUsers);
            lvUsers.setAdapter(userAdapter);
            lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ReceiveUserBean user = mUsers.get(position);
                    if(user!=null){
                        getProgressDialog().show();
                        mPresenter.deliverDispatch(mList.get(EquipPosition).getIdx(),user.getEmpId(),user.getEmpName());
                    }

                }
            });
        }

    }

    @Override
    public void BookSuccess() {
        getProgressDialog().dismiss();
        ToastUtil.toastShort("操作成功");
        dialog.dismiss();
        getProgressDialog().show();
        mPresenter.getTrainList();
    }

    @Override
    public void onLoadFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
