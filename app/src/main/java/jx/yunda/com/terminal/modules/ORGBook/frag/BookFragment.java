package jx.yunda.com.terminal.modules.ORGBook.frag;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.entity.UserSelectBean;
import jx.yunda.com.terminal.modules.ORGBook.act.ORGBookActivity;
import jx.yunda.com.terminal.modules.ORGBook.adapter.BookUserAdapter;
import jx.yunda.com.terminal.modules.ORGBook.model.BookCalenderBean;
import jx.yunda.com.terminal.modules.ORGBook.model.BookLastUserBean;
import jx.yunda.com.terminal.modules.ORGBook.model.BookUserBean;
import jx.yunda.com.terminal.modules.ORGBook.presenter.BookPresenter;
import jx.yunda.com.terminal.modules.ORGBook.presenter.IBook;
import jx.yunda.com.terminal.utils.ToastUtil;

public class BookFragment extends BaseFragment<BookPresenter> implements IBook,View.OnClickListener {
    @BindView(R.id.tvstartTime)
    TextView tvstartTime;
    @BindView(R.id.tvendTime)
    TextView tvendTime;
    @BindView(R.id.llSelectTime)
    LinearLayout llSelectTime;
    @BindView(R.id.rlUsers)
    RecyclerView rlUsers;
    @BindView(R.id.btBook)
    Button btBook;
    Unbinder unbinder;
    List<BookUserBean> beans = new ArrayList<>();
    BookUserAdapter adapter ;
    AlertDialog dialog;
    String nowData;
    String nowDatashow;
    List<BookLastUserBean> lastPlans = new ArrayList<>();
    @Override
    protected BookPresenter getPresenter() {
        return new BookPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_book_user;
    }

    @Override
    public void initSubViews(View view) {

    }

    ORGBookActivity mainact;
    @Override
    public void initData() {
        mainact = (ORGBookActivity) getActivity();
        setData();
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rlUsers.setFocusable(true);
        rlUsers.setLayoutManager(layoutManager);
        rlUsers.setHasFixedSize(true);
        adapter = new BookUserAdapter(getContext(),beans);
        rlUsers.setAdapter(adapter);
        llSelectTime.setOnClickListener(this);
        btBook.setOnClickListener(this);
        adapter.setOnItemClickListner(new BookUserAdapter.OnItemClickListner() {
            @Override
            public void OnItemClick(int position, int status) {
                if(status==0){
                    beans.get(position).setPlan("");
                    beans.get(position).setPlanid("");
                }else {
                    beans.get(position).setPlan(beans.get(position).getEmpname());
                    beans.get(position).setPlanid(beans.get(position).getEmpid()+"");
                }
                adapter.notifyDataSetChanged();
                int bookNum = 0;
                for(BookUserBean bean: beans){
                    if(bean.getPlan()!=null&&!bean.getPlan().equals("")){
                        bookNum++;
                    }
                }
                if(bookNum>0){
                    btBook.setText("点名"+"("+bookNum+")");
                }else {
                    btBook.setText("点名");
                }
            }
        });
    }

    private void getUser() {
        mainact.getProgressDialog().show();
        mPresenter.getBookUsers(SysInfo.userInfo.org.getOrgid()+"","");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        nowData = sf2.format(new Date());

    }

    @Override
    public void BookSuccess() {
        List<HashMap<String,Object>> temps = new ArrayList<>();
        for(BookUserBean bean: beans){
            if(bean.getStatus()==1){
              HashMap<String,Object> map = new HashMap<>();
              map.put("empid",bean.getEmpid()+"");
              map.put("empname",bean.getEmpname());
              map.put("empids",bean.getPlanid());
              map.put("empnames",bean.getPlan());
              temps.add(map);
            }
        }
        mPresenter.MasterDipatch(JSON.toJSONString(temps));
    }

    @Override
    public void BookFaild(String msg) {
        mainact.getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void PostPlanSuccess() {
        mainact.getProgressDialog().dismiss();
        ToastUtil.toastShort("点名成功");
    }

    @Override
    public void PostPlanFaild(String msg) {
        mainact.getProgressDialog().dismiss();
        ToastUtil.toastShort("点名失败："+msg);
    }

    @Override
    public void getUsersSuccess(List<BookUserBean> list) {
        beans.clear();
        if(list!=null&&list.size()>0){
            beans.addAll(list);
            List<UserSelectBean> listuser = new ArrayList<>();
            for(BookUserBean bean:list){
                UserSelectBean user = new UserSelectBean();
                user.setUserName(bean.getEmpname());
                user.setIdx(bean.getEmpid()+"");
                user.setSelected(false);
                listuser.add(user);
            }
            if(listuser.size()>0){
                adapter.setSelectUsers(listuser);
            }
            adapter.notifyDataSetChanged();
        }
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        String data = sf2.format(new Date());
        mPresenter.getBookCalender(data,"ff8080815b096999015b096e5a070002");

    }

    @Override
    public void getUsersFaild(String msg) {
        mainact.getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void getUserPlanSuccess(List<BookLastUserBean> list) {
        mainact.getProgressDialog().dismiss();
        lastPlans.clear();
        for(BookUserBean bean:beans){
            bean.setStatus(0);
            bean.setPlan("");
            bean.setPlanid("");
        }
        if(list!=null&&list.size()>0){
            lastPlans.addAll(list);
            for(int i = 0;i< list.size();i++){
                for(int j = 0;j<beans.size();j++){
                    if(lastPlans.get(i).getEmpId().equals(beans.get(j).getEmpcode())){
                        beans.get(j).setStatus(1);
                        if(lastPlans.get(i).getEmpIds()!=null){
                            beans.get(j).setPlanid(lastPlans.get(i).getEmpIds());
                            beans.get(j).setPlan(lastPlans.get(i).getEmpNames());
                        }else {
                            beans.get(j).setPlanid(lastPlans.get(i).getEmpId());
                            beans.get(j).setPlan(lastPlans.get(i).getEmpName());
                        }
                    }
                }
            }
        }
        int bookNum = 0;
        for(BookUserBean bean: beans){
            if(bean.getPlan()!=null&&!bean.getPlan().equals("")){
                bookNum++;
            }
        }
        if(bookNum>0){
            btBook.setText("点名"+"("+bookNum+")");
        }else {
            btBook.setText("点名");
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getUserPlanFaild(String msg) {
        mainact.getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
        for(BookUserBean bean:beans){
            bean.setStatus(0);
            bean.setPlanid("");
            bean.setPlan("");
        }
        int bookNum = 0;
        for(BookUserBean bean: beans){
            if(bean.getPlan()!=null&&!bean.getPlan().equals("")){
                bookNum++;
            }
        }
        if(bookNum>0){
            btBook.setText("点名"+"("+bookNum+")");
        }else {
            btBook.setText("点名");
        }
        adapter.notifyDataSetChanged();
    }
    String starttime = "08:00";
    String endtime = "17:20";
    @Override
    public void getBookCalenderSuccess(BookCalenderBean calenderBean) {
        SimpleDateFormat st = new SimpleDateFormat("MM月dd日");
        nowDatashow = st.format(new Date());
        starttime = calenderBean.getDefInfo().getPeriod1Begin().substring(0,5);
        endtime = calenderBean.getDefInfo().getPeriod2End().substring(0,5);
        if(tvstartTime!=null)
        tvstartTime.setText(nowDatashow+" "+starttime);
        if(tvendTime!=null)
        tvendTime.setText(nowDatashow+" "+endtime);
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        String data = sf2.format(new Date());
        mPresenter.getLastMater(data+" "+starttime,data+" "+endtime);
    }

    @Override
    public void getBookCalenderFaild(String msg) {
        mainact.getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(btBook!=null)
        btBook.setText("点名");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void setAnimate1() {
        String emp = "";
        String empid = "";
        for(BookUserBean bean: beans){
            if(bean.getStatus()==1){
                if(emp.equals("")){
                    emp = emp+bean.getEmpname();
                    empid = empid + bean.getEmpid();
                }else {
                    emp = emp+","+bean.getEmpname();
                    empid = empid + ","+ bean.getEmpid();
                }
            }
        }
        if(!emp.equals("")){
            mPresenter.BookUsers(empid,emp,nowData+" "+starttime,nowData+" "+endtime);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    TextView tvStartData,tvEndData;
    Long startTime,EndTime;
    CalendarView clender;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llSelectTime:
                if (dialog == null) {
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.calendar_view, null);
                    clender = (CalendarView)view.findViewById(R.id.clender);
                    clender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                        @Override
                        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                            String mon = "";
                            String day = "";
                            if((month+1)<10){
                                mon = "0"+(month+1);
                            }else {
                                mon = (month+1)+"";
                            }
                           if((dayOfMonth)<10){
                                day = "0"+(dayOfMonth);
                           }else {
                               day = ""+(dayOfMonth);
                           }
                            nowDatashow = mon+"月"+day+"日";
                            nowData = year+"-"+mon+"-"+day;
                        }
                    });

                    dialog = new AlertDialog.Builder(getContext()).setTitle("请选择日期").setView(view)
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(tvstartTime!=null)
                                        tvstartTime.setText(nowDatashow+" "+starttime);
                                    if(tvendTime!=null)
                                        tvendTime.setText(nowDatashow+" "+endtime);
                                    mPresenter.getLastMater(nowData+" "+ startTime,nowData+" "+endtime);
                                }
                            }).create();
                }
                dialog.show();
                break;
            case R.id.btBook:
                mainact.getProgressDialog().show();
                setAnimate1();
                break;
        }
    }
}
