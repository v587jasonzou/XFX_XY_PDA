package jx.yunda.com.terminal.modules.ORGBookNew.act;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.ORGBook.model.BookCalenderBean;
import jx.yunda.com.terminal.modules.ORGBook.model.BookLastUserBean;
import jx.yunda.com.terminal.modules.ORGBook.model.BookUserBean;
import jx.yunda.com.terminal.modules.ORGBookNew.adapter.BookUserAdapterNew;
import jx.yunda.com.terminal.modules.ORGBookNew.adapter.UserPlanAdapter;
import jx.yunda.com.terminal.modules.ORGBookNew.adapter.UserUnPlanAdapter;
import jx.yunda.com.terminal.modules.ORGBookNew.model.BookUserBeanNew;
import jx.yunda.com.terminal.modules.ORGBookNew.model.Nodebean;
import jx.yunda.com.terminal.modules.ORGBookNew.presenter.BookPresenterNew;
import jx.yunda.com.terminal.modules.ORGBookNew.presenter.IBookNew;
import jx.yunda.com.terminal.utils.ToastUtil;

public class BookNewActivity extends BaseActivity<BookPresenterNew> implements IBookNew, View.OnClickListener {
    List<BookUserBeanNew> beans = new ArrayList<>();
    BookUserAdapterNew adapter;
    AlertDialog dialog;
    String nowData;
    String nowDatashow;
    List<BookLastUserBean> lastPlans = new ArrayList<>();
    @BindView(R.id.rlUsers)
    RecyclerView rlUsers;
    @BindView(R.id.btBook)
    Button btBook;
    @BindView(R.id.rlPlans)
    RecyclerView rlPlans;
    @BindView(R.id.tvAdd)
    TextView tvAdd;
    @BindView(R.id.ivRefresh)
    ImageView ivRefresh;
    int UserPosition = 0;
    int bookPlanPosition;
    int unBookPlanPosition;
    List<Nodebean> mPlans = new ArrayList<>();
    List<Nodebean> mUnBookPlans = new ArrayList<>();
    List<Nodebean> mAllUnBookPlans = new ArrayList<>();
    UserPlanAdapter userPlanAdapter;
    UserUnPlanAdapter userUnPlanAdapter;
    @BindView(R.id.menu_tp)
    Toolbar menuTp;

    LinearLayout llEmpty;
    @Override
    protected BookPresenterNew getPresenter() {
        return new BookPresenterNew(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_book_user_new;
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
        setData();
    }

    @OnClick(R.id.ivRefresh)
    void Refresh() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(ivRefresh, "rotation", 0f, 360f);
        animator.setDuration(650);
        animator.start();
        getUser();
    }

    private void setData() {
//        for(int i = 0;i<10;i++){
//            BookUserBean bean = new BookUserBean();
//            bean.setName("张伟"+i);
//            bean.setStatus(0);
////            bean.setPlan("张伟"+i+"的任务");
//            beans.add(bean);
//        }
        getUser();
//        Calendar calendar = Calendar.getInstance();
//        tvstartTime.setText((calendar.get(Calendar.MONTH)+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日 "
//        +"08:30");
//        tvendTime.setText((calendar.get(Calendar.MONTH)+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日 "
//        +"17:30");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rlUsers.setFocusable(true);
        rlUsers.setLayoutManager(layoutManager);
        rlUsers.setHasFixedSize(true);
        adapter = new BookUserAdapterNew(this, beans);
        rlUsers.setAdapter(adapter);
        btBook.setOnClickListener(this);
        adapter.setOnItemClickListner(new BookUserAdapterNew.OnItemClickListner() {
            @Override
            public void OnItemClick(int position) {
                UserPosition = position;
                for (int i = 0; i < beans.size(); i++) {
                    if (i == position) {
                        beans.get(i).setStates(1);
                    } else {
                        beans.get(i).setStates(0);
                    }
                }
                adapter.notifyDataSetChanged();
                getProgressDialog().show();
                mPresenter.getBookNodes(beans.get(position).getEmpId() + "");
            }
        });
        userPlanAdapter = new UserPlanAdapter(this, mPlans);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        rlPlans.setFocusable(true);
        rlPlans.setLayoutManager(layoutManager1);
        rlPlans.setHasFixedSize(true);
        rlPlans.setAdapter(userPlanAdapter);
        userPlanAdapter.setOnItemClickListner(new UserPlanAdapter.OnItemClickListner() {
            @Override
            public void OnItemClick(final int position) {
                new AlertDialog.Builder(BookNewActivity.this).setTitle("提示！")
                        .setMessage("确定取消当前指派任务？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getProgressDialog().show();
                                mPresenter.CanclePlan(beans.get(UserPosition).getEmpId() + "", beans.get(UserPosition).getEmpName(),
                                        mPlans.get(position).getIdx());
                            }
                        }).show();
            }
        });
    }

    private void getUser() {
        getProgressDialog().show();
//        mPresenter.getBookUsers(SysInfo.userInfo.org.getOrgid() + "", "");
//        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
//        nowData = sf2.format(new Date());
        mPresenter.getBookUsers();

    }

    @Override
    public void BookSuccess() {
        List<HashMap<String, Object>> temps = new ArrayList<>();
        for (BookUserBeanNew bean : beans) {
//            if (bean.getStatus() == 1) {
//                HashMap<String, Object> map = new HashMap<>();
//                map.put("empid", bean.getEmpid() + "");
//                map.put("empname", bean.getEmpname());
//                map.put("empids", bean.getPlanid());
//                map.put("empnames", bean.getPlan());
//                temps.add(map);
//            }
        }
        mPresenter.MasterDipatch(JSON.toJSONString(temps));
    }

    @Override
    public void BookFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void PostPlanSuccess() {
        getProgressDialog().dismiss();
        ToastUtil.toastShort("点名成功");
    }

    @Override
    public void PostPlanFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort("点名失败：" + msg);
    }

    @Override
    public void getUsersSuccess(List<BookUserBean> list) {
//        beans.clear();
//        if (list != null && list.size() > 0) {
//            beans.addAll(list);
//            List<UserSelectBean> listuser = new ArrayList<>();
//            for (BookUserBean bean : list) {
//                UserSelectBean user = new UserSelectBean();
//                user.setUserName(bean.getEmpname());
//                user.setIdx(bean.getEmpid() + "");
//                user.setSelected(false);
//                listuser.add(user);
//            }
//            if (listuser.size() > 0) {
//                adapter.setSelectUsers(listuser);
//            }
//            adapter.notifyDataSetChanged();
//        }
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        String data = sf2.format(new Date());
        mPresenter.getBookCalender(data, "ff8080815b096999015b096e5a070002");

    }

    @Override
    public void getUsersFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }
    TabLayout tbAssign;
    int tabposition = 0;
    @OnClick(R.id.tvAdd)
    void AddPlans() {
        if (dialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_select_plan, null);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            etSearch = (EditText) view.findViewById(R.id.etSearch);
            lvUnBookPlans = (ListView) view.findViewById(R.id.lvUnBookPlans);
            btSubmit = (Button) view.findViewById(R.id.btSubmit);
            llEmpty = (LinearLayout)view.findViewById(R.id.llEmpty);
            tbAssign = (TabLayout)view.findViewById(R.id.tbAssign);
            llEmpty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getProgressDialog().show();
                    if(tabposition==0){
                        mPresenter.getUnBookNodes(beans.get(UserPosition).getEmpId() + "");
                    }else {
                        mPresenter.getOtherUnBookNodes("");
                    }
                }
            });
            tbAssign.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if(tab.getPosition()==0){
                        tabposition = 0;
                        getProgressDialog().show();
                        mPresenter.getUnBookNodes(beans.get(UserPosition).getEmpId() + "");
                    }else {
                        tabposition = 1;
                        getProgressDialog().show();
                        mPresenter.getOtherUnBookNodes("");
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            dialog = new AlertDialog.Builder(this).setView(view).create();
            userUnPlanAdapter = new UserUnPlanAdapter(this, mUnBookPlans);
            lvUnBookPlans.setAdapter(userUnPlanAdapter);
            userUnPlanAdapter.setOnItemClickListner(new UserUnPlanAdapter.OnItemClickListner() {
                @Override
                public void OnItemClick(int position) {
                    getProgressDialog().show();
                    mPresenter.AddPlan(beans.get(UserPosition).getEmpId() + "", beans.get(UserPosition).getEmpName(),
                            mUnBookPlans.get(position).getIdx());

                }
            });
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mUnBookPlans.clear();
                    userUnPlanAdapter.notifyDataSetChanged();
                    getUser();
                }
            });
            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String str = s.toString();
                    List<Nodebean> list = new ArrayList<>();
                    mUnBookPlans.clear();
                    if (str == null || str.equals("")) {
                        mUnBookPlans.addAll(mAllUnBookPlans);
                    } else {
                        for (Nodebean bean : mAllUnBookPlans) {
                            if (bean.getTrainNo() != null) {
                                if (bean.getTrainNo().contains(str)) {
                                    mUnBookPlans.add(bean);
                                }
                            }
                        }
                    }
                    userUnPlanAdapter.notifyDataSetChanged();
                }
            });
        }
        dialog.show();
        getProgressDialog().show();
        if(tabposition==0){
            mPresenter.getUnBookNodes(beans.get(UserPosition).getEmpId() + "");
        }else {
            mPresenter.getOtherUnBookNodes("");
        }
    }

    @Override
    public void getUserPlanSuccess(List<BookLastUserBean> list) {
        getProgressDialog().dismiss();
        lastPlans.clear();
//        for (BookUserBean bean : beans) {
//            bean.setStatus(0);
//            bean.setPlan("");
//            bean.setPlanid("");
//        }
//        if (list != null && list.size() > 0) {
//            lastPlans.addAll(list);
//            for (int i = 0; i < list.size(); i++) {
//                for (int j = 0; j < beans.size(); j++) {
//                    if (lastPlans.get(i).getEmpId().equals(beans.get(j).getEmpcode())) {
//                        beans.get(j).setStatus(1);
//                        if (lastPlans.get(i).getEmpIds() != null) {
//                            beans.get(j).setPlanid(lastPlans.get(i).getEmpIds());
//                            beans.get(j).setPlan(lastPlans.get(i).getEmpNames());
//                        } else {
//                            beans.get(j).setPlanid(lastPlans.get(i).getEmpId());
//                            beans.get(j).setPlan(lastPlans.get(i).getEmpName());
//                        }
//                    }
//                }
//            }
//        }
//        int bookNum = 0;
//        for (BookUserBean bean : beans) {
//            if (bean.getPlan() != null && !bean.getPlan().equals("")) {
//                bookNum++;
//            }
//        }
//        if (bookNum > 0) {
//            btBook.setText("点名" + "(" + bookNum + ")");
//        } else {
//            btBook.setText("点名");
//        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getUserPlanFaild(String msg) {
        getProgressDialog().dismiss();
//        ToastUtil.toastShort(msg);
//        for (BookUserBean bean : beans) {
//            bean.setStatus(0);
//            bean.setPlanid("");
//            bean.setPlan("");
//        }
//        int bookNum = 0;
//        for (BookUserBean bean : beans) {
//            if (bean.getPlan() != null && !bean.getPlan().equals("")) {
//                bookNum++;
//            }
//        }
//        if (bookNum > 0) {
//            btBook.setText("点名" + "(" + bookNum + ")");
//        } else {
//            btBook.setText("点名");
//        }
//        adapter.notifyDataSetChanged();
    }

    String starttime = "08:00";
    String endtime = "17:20";

    @Override
    public void getBookCalenderSuccess(BookCalenderBean calenderBean) {
        SimpleDateFormat st = new SimpleDateFormat("MM月dd日");
        nowDatashow = st.format(new Date());
        starttime = calenderBean.getDefInfo().getPeriod1Begin().substring(0, 5);
        endtime = calenderBean.getDefInfo().getPeriod2End().substring(0, 5);
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        String data = sf2.format(new Date());
        mPresenter.getLastMater(data + " " + starttime, data + " " + endtime);
    }

    @Override
    public void getBookCalenderFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void getUsersSuccessNew(List<BookUserBeanNew> list) {
        getProgressDialog().dismiss();
        beans.clear();
        if (list != null && list.size() > 0) {
            beans.addAll(list);
//            List<UserSelectBean> listuser = new ArrayList<>();
//            for (BookUserBeanNew bean : list) {
//                UserSelectBean user = new UserSelectBean();
//                user.setUserName(bean.getEmpName());
//                user.setIdx(bean.getEmpId() + "");
//                user.setSelected(false);
//                listuser.add(user);
//            }
            beans.get(UserPosition).setStates(1);
            getProgressDialog().show();
            mPresenter.getBookNodes(beans.get(UserPosition).getEmpId() + "");
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getBookPlanSuccess(List<Nodebean> list) {
        getProgressDialog().dismiss();
        mPlans.clear();
        if (list != null && list.size() > 0) {
            mPlans.addAll(list);
        } else {
            ToastUtil.toastShort("当前人员无相关任务信息");
        }
        userPlanAdapter.notifyDataSetChanged();
    }

    @Override
    public void getUnBookPlanSuccess(List<Nodebean> list) {
        getProgressDialog().dismiss();
        mUnBookPlans.clear();
        mAllUnBookPlans.clear();
        if (list != null && list.size() > 0) {
            mUnBookPlans.addAll(list);
            mAllUnBookPlans.addAll(list);
            if(llEmpty!=null)
                llEmpty.setVisibility(View.GONE);
        }else {
            if(llEmpty!=null)
            llEmpty.setVisibility(View.VISIBLE);
        }
        if (etSearch != null && dialog.isShowing()) {
            if (etSearch.getText() != null && !etSearch.getText().toString().equals("")) {
                String str = etSearch.getText().toString().toString();
                mUnBookPlans.clear();
                if (str == null || str.equals("")) {
                    mUnBookPlans.addAll(mAllUnBookPlans);
                } else {
                    for (Nodebean bean : mAllUnBookPlans) {
                        if (bean.getTrainNo() != null) {
                            if (bean.getTrainNo().contains(str)) {
                                mUnBookPlans.add(bean);
                            }
                        }
                    }
                }
            }
        }

        userUnPlanAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnAddSuccess() {
        ToastUtil.toastShort("添加任务成功");
        getProgressDialog().dismiss();
        if(tabposition==0){
            mPresenter.getUnBookNodes(beans.get(UserPosition).getEmpId() + "");
        }else {
            mPresenter.getOtherUnBookNodes("");
        }

    }

    @Override
    public void OnDeleteSuccess() {
        ToastUtil.toastShort("取消任务成功");
        getUser();
    }


    private void setAnimate1() {
//        String emp = "";
//        String empid = "";
//        for (BookUserBean bean : beans) {
//            if (bean.getStatus() == 1) {
//                if (emp.equals("")) {
//                    emp = emp + bean.getEmpname();
//                    empid = empid + bean.getEmpid();
//                } else {
//                    emp = emp + "," + bean.getEmpname();
//                    empid = empid + "," + bean.getEmpid();
//                }
//            }
//        }
//        if (!emp.equals("")) {
//            mPresenter.BookUsers(empid, emp, nowData + " " + starttime, nowData + " " + endtime);
//        }
    }


    TextView tvStartData, tvEndData;
    Long startTime, EndTime;

    TextView tvTitle;
    EditText etSearch;
    ListView lvUnBookPlans;
    Button btSubmit;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llSelectTime:
                break;
            case R.id.btBook:
                getProgressDialog().show();
                setAnimate1();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
