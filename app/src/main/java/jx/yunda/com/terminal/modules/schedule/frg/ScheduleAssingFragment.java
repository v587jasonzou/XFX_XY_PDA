package jx.yunda.com.terminal.modules.schedule.frg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.base.adapter.SelectUserAdapter;
import jx.yunda.com.terminal.base.adapter.SelectUserNewAdapter;
import jx.yunda.com.terminal.entity.UserSelectBean;
import jx.yunda.com.terminal.modules.ORGBook.act.ORGBookActivity;
import jx.yunda.com.terminal.modules.ORGBook.adapter.DispatchFwhListAdapter;
import jx.yunda.com.terminal.modules.ORGBook.model.BookUserBean;
import jx.yunda.com.terminal.modules.ORGBook.presenter.AssignDispatchPresenter;
import jx.yunda.com.terminal.modules.ORGBook.presenter.IAssignDispatch;
import jx.yunda.com.terminal.modules.foremandispatch.model.FwFormenBean;
import jx.yunda.com.terminal.modules.foremandispatch.model.TrainForForemanDispatch;
import jx.yunda.com.terminal.modules.schedule.act.ScheduleBookNewActivity;
import jx.yunda.com.terminal.modules.schedule.adapter.ScheduleDispatchFwhListAdapter;
import jx.yunda.com.terminal.modules.schedule.entity.OrgBean;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;
import jx.yunda.com.terminal.modules.schedule.presenter.IScheduleAssignDispatch;
import jx.yunda.com.terminal.modules.schedule.presenter.ScheduleAssignDispatchPresenter;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.WorkshopTaskDispatchFwhActivity;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FwhBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.OrgForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter.OnViewClickInItemListner;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.SelectUserTextView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

//范围活fragment
public class ScheduleAssingFragment extends BaseFragment<ScheduleAssignDispatchPresenter> implements IScheduleAssignDispatch {
    @BindView(R.id.tabTrain)
    VerticalTabLayout tabTrain;
    @BindView(R.id.tbAssign)
    TabLayout tbAssign;
    @BindView(R.id.lvPlan)
    ListView lvPlan;
    Unbinder unbinder;
    int start = 0;
    int limit = 8;
    List<ScheduleTrainBean> ticketTrainBeans = new ArrayList<>();
    ScheduleBookNewActivity mainAc;
    public static List<FwhBean> fwhList = new ArrayList<>();
    ScheduleDispatchFwhListAdapter adapter ;
    int EquipPosition = 0;
    int tabStatus = 0;

    //人员选择相关控件
    AlertDialog userDialog;
    AlertDialog orgDialog;
    List<UserSelectBean> users = new ArrayList<>();
    List<UserSelectBean> userlistTemp = new ArrayList<>();
    List<BookUserBean> musers = new ArrayList<>();
    ListView lvUser;
    Button btCancle;
    Button btSubmit;
    Button btClear;
    TextView tvUsers;
    EditText etSearch;
    Context mContext;
    SelectUserNewAdapter userAdapter;
    SelectUserTextView.OnSelectCompleteLisnter onSelectCompleteLisnter;
    int userPosition;
    @Override
    protected ScheduleAssignDispatchPresenter getPresenter() {
        return new ScheduleAssignDispatchPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_assign;
    }

    @Override
    public void initSubViews(View view) {

    }
    VerticalTabLayout.OnTabSelectedListener onTabSelectedListener;
    @Override
    public void initData() {
        mainAc = (ScheduleBookNewActivity) getActivity();
        mContext = getContext();
        mainAc.getProgressDialog().show();
        adapter = new ScheduleDispatchFwhListAdapter(getContext(),fwhList);
        lvPlan.setAdapter(adapter);
        mPresenter.getTarinList();
        onTabSelectedListener = new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                EquipPosition = position;
                if(ticketTrainBeans.size()>0){
                    mainAc.getProgressDialog().show();
                    if(tabStatus==0){
                        mPresenter.getFwhListNew(ticketTrainBeans.get(EquipPosition).getIdx(),"no");
                    }else {
                        mPresenter.getFwhListNew(ticketTrainBeans.get(EquipPosition).getIdx(),"yes");
                    }

                }
            }
            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        };

        tabTrain.addOnTabSelectedListener(onTabSelectedListener);
        tbAssign.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(ticketTrainBeans.size()>0){
                    mainAc.getProgressDialog().show();
                    if(tab.getPosition()==0){
                        tabStatus = 0;
                        adapter.setStatus(0);
                        mPresenter.getFwhListNew(ticketTrainBeans.get(EquipPosition).getIdx(),"no");
                    } else {
                        tabStatus = 1;
                        adapter.setStatus(1);
                        mPresenter.getFwhListNew(ticketTrainBeans.get(EquipPosition).getIdx(),"yes");
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        adapter.setOnViewClickListner(new OnViewClickInItemListner() {
            @Override
            public void onViewClick(View view, int position) {
                userPosition = position;
//                if(tabStatus==0){
//                    Bundle params = new Bundle();
//                    params.putSerializable("fwhBean", fwhList.get(position));
//                    params.putString("status","未完成");
//                    ActivityUtil.startActivity(mainAc, WorkshopTaskDispatchFwhActivity.class, params);
//                }else {
//                    Bundle params = new Bundle();
//                    params.putSerializable("fwhBean", fwhList.get(position));
//                    params.putString("status","已完成");
//                    ActivityUtil.startActivity(mainAc, WorkshopTaskDispatchFwhActivity.class, params);
//                }

                if(users.size()==0){
                    mainAc.getProgressDialog().show();
                    mPresenter.getOrgList();
                }else {
                    String works = fwhList.get(position).getWorkStationBelongTeamName();
                    if(works!=null){
                        String[] workers = works.split(",");
                        if (workers!=null&&workers.length>0){
                            for(int i = 0;i<users.size();i++){
                                for(int j = 0;j<workers.length;j++){
                                    if(workers[j].equals(users.get(i).getIdx())){
                                        users.get(i).setSelected(true);
                                        break;
                                    }
                                }
                            }
                            userDialog.show();
                            userAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
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
        if(tabTrain!=null){
            tabTrain.removeOnTabSelectedListener(onTabSelectedListener);
            tabTrain.removeAllTabs();
        }
        unbinder.unbind();
        ticketTrainBeans.clear();

    }

    @Override
    public void LoadTrainListSuccess(List<TrainForForemanDispatch> beans) {
        if(beans!=null&&beans.size()>0){
//            ticketTrainBeans.addAll(beans);
//            mPresenter.getFwhList(start,1000,ticketTrainBeans.get(0).getWorkPlanIDX(),0);
        }
        tabTrain.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return ticketTrainBeans.size();
            }

            @Override
            public ITabView.TabBadge getBadge(int position) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                return new QTabView.TabTitle.Builder()
                        .setContent((ticketTrainBeans.get(position).getTrainTypeShortName()+"\n"+
                                ticketTrainBeans.get(position).getTrainNo()))
                        .setTextColor(0xff3E5FE5,0xff616161 )
                        .setTextSize(18)
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return R.drawable.verticaltab_bg;
            }
        });

    }

    @Override
    public void LoadTrainListFaild(String msg) {
        mainAc.getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void onEmpListLoadSuccess(List<BookUserBean> list) {
        if(list!=null&&list.size()>0){
            musers.addAll(list);
            for(BookUserBean bean:list){
                UserSelectBean user = new UserSelectBean();
                user.setSelected(false);
                user.setIdx(bean.getEmpid()+"");
                user.setUserName(bean.getEmpname());
                users.add(user);
            }
            String works = fwhList.get(userPosition).getWorkerIdxStr();
            if(works!=null){
                String[] workers = works.split(",");
                if (workers!=null&&workers.length>0){
                    for(int i = 0;i<users.size();i++){
                        for(int j = 0;j<workers.length;j++){
                            if(workers[j].equals(users.get(i).getIdx())){
                                users.get(i).setSelected(true);
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(list==null||list.size()==0){
            ToastUtil.toastShort("没有班组可以选择");
        }else {
            if(userDialog==null){
                View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_select_user, null);
                lvUser = (ListView)view.findViewById(R.id.lvUser);
                btCancle = (Button) view.findViewById(R.id.btCancle);
                btSubmit = (Button)view.findViewById(R.id.btSubmit);
                btClear = (Button)view.findViewById(R.id.btClear);
                tvUsers = (TextView)view.findViewById(R.id.tvUsers);
                etSearch = (EditText)view.findViewById(R.id.etSearch);
                btClear.setVisibility(View.GONE);
                etSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.toString().equals("请输入班组名称或拼音")||"".equals(s.toString())){
                            users.clear();
                            for(BookUserBean bean:musers){
                                UserSelectBean user = new UserSelectBean();
                                user.setSelected(false);
                                user.setIdx(bean.getEmpid()+"");
                                user.setUserName(bean.getEmpname());
                                users.add(user);
                            }
//                            users.addAll(userlistTemp);
                        }else {
                            List<UserSelectBean> temps = new ArrayList<>();
                            String Search = s.toString().toUpperCase();
                            List<UserSelectBean> Searchs = new ArrayList<>();
                            for(BookUserBean bean:musers){
                                UserSelectBean user = new UserSelectBean();
                                user.setSelected(false);
                                user.setIdx(bean.getEmpid()+"");
                                user.setUserName(bean.getEmpname());
                                Searchs.add(user);
                            }
                            if(Searchs.size()>0){
                                for(int i = 0;i<Searchs.size();i++){
                                    if(Pinyin.toPinyin(Searchs.get(i).getUserName(),"").toUpperCase().contains(Search)||
                                            Searchs.get(i).getUserName().contains(Search)){
                                        temps.add(Searchs.get(i));
                                    }
                                }
                            }
                            users.clear();
                            users.addAll(temps);
                        }
                        userAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                btCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userDialog.dismiss();
                    }
                });
                btSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FwhBean fwhBean = fwhList.get(userPosition);
                        for(int i = 0;i<users.size();i++){
                            if(users.get(i).getSelected()){
                                fwhBean.setWorkStationBelongTeamName(users.get(i).getUserName());
                                fwhBean.setWorkStationBelongTeam(Long.parseLong(users.get(i).getIdx()));
                                mainAc.getProgressDialog().show();
                                mPresenter.dispatchFwh(JSON.toJSONString(new FwhBean[]{fwhBean}));
                                userDialog.dismiss();
                                return;
                            }
                        }
                        ToastUtil.toastShort("还未选择班组");
                        userDialog.dismiss();
                    }
                });

                userDialog = new AlertDialog.Builder(mContext).setView(view).create();
                userDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        etSearch.setText("");
                        users.clear();
                        users.addAll(userlistTemp);
                        adapter.notifyDataSetChanged();
                    }
                });
                userDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {

                    }
                });
                userAdapter =new SelectUserNewAdapter(mContext, users);
                lvUser.setAdapter(userAdapter);

            }
            userDialog.show();
            userDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        }
    }

    @Override
    public void onLoadEmpListLoadFail(String msg) {
        mainAc.getProgressDialog().dismiss();
        ToastUtil.toastLong(msg);
    }

    @Override
    public void onFwhListLoadSuccess(List<FwhBean> list, int total) {
        mainAc.getProgressDialog().dismiss();
        fwhList.clear();
        if(list!=null&&list.size()>0){
            fwhList.addAll(list);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFwhListLoadMoreSuccess(List<FwhBean> list, int total) {

    }

    @Override
    public void OnLoadFaid(String msg) {
        mainAc.getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void onDispatchSuccess(String msg) {
//        mainAc.getProgressDialog().dismiss();
        ToastUtil.toastShort("派工成功");
        mainAc.getProgressDialog().show();
        if(tabStatus==0){
            mPresenter.getFwhListNew(ticketTrainBeans.get(EquipPosition).getIdx(),"no");
        }else {
            mPresenter.getFwhListNew(ticketTrainBeans.get(EquipPosition).getIdx(),"yes");
    }
    }

    @Override
    public void onDispatchFail(String msg) {
        mainAc.getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }
    List<OrgBean> orgs = new ArrayList<>();
    @Override
    public void onClassListLoadSuccess(List<OrgForWorkshopTaskDispatch> list) {

    }

    @Override
    public void OnLoadOrgSuccess(List<OrgBean> list) {
        mainAc.getProgressDialog().dismiss();
        if(list!=null&&list.size()>0){
            orgs.addAll(list);
            for(OrgBean bean:list){
                UserSelectBean user = new UserSelectBean();
                user.setSelected(false);
                user.setIdx(bean.getHandeOrgID()+"");
                user.setUserName(bean.getHandeOrgName());
                users.add(user);
            }
            String works = fwhList.get(userPosition).getWorkerIdxStr();
            if(works!=null){
                String[] workers = works.split(",");
                if (workers!=null&&workers.length>0){
                    for(int i = 0;i<users.size();i++){
                        for(int j = 0;j<workers.length;j++){
                            if(workers[j].equals(users.get(i).getIdx())){
                                users.get(i).setSelected(true);
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(list==null||list.size()==0){
            ToastUtil.toastShort("没有班组可以选择");
        }else {
            if(userDialog==null){
                View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_select_user, null);
                TextView tvTitle = (TextView)view.findViewById(R.id.tvTitle);
                tvTitle.setText("请选择班组");
                lvUser = (ListView)view.findViewById(R.id.lvUser);
                btCancle = (Button) view.findViewById(R.id.btCancle);
                btSubmit = (Button)view.findViewById(R.id.btSubmit);
                btClear = (Button)view.findViewById(R.id.btClear);
                tvUsers = (TextView)view.findViewById(R.id.tvUsers);
                etSearch = (EditText)view.findViewById(R.id.etSearch);
                btClear.setVisibility(View.GONE);
                etSearch.setHint("请输入班组名称或拼音");
                etSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.toString().equals("请输入班组名称或拼音")||"".equals(s.toString())){
                            users.clear();
                            for(OrgBean bean:orgs){
                                UserSelectBean user = new UserSelectBean();
                                user.setSelected(false);
                                user.setIdx(bean.getHandeOrgID()+"");
                                user.setUserName(bean.getHandeOrgName());
                                users.add(user);
                            }
//                            users.addAll(userlistTemp);
                        }else {
                            List<UserSelectBean> temps = new ArrayList<>();
                            String Search = s.toString().toUpperCase();
                            List<UserSelectBean> Searchs = new ArrayList<>();
                            for(OrgBean bean:orgs){
                                UserSelectBean user = new UserSelectBean();
                                user.setSelected(false);
                                user.setIdx(bean.getHandeOrgID()+"");
                                user.setUserName(bean.getHandeOrgName());
                                Searchs.add(user);
                            }
                            if(Searchs.size()>0){
                                for(int i = 0;i<Searchs.size();i++){
                                    if(Pinyin.toPinyin(Searchs.get(i).getUserName(),"").toUpperCase().contains(Search)||
                                            Searchs.get(i).getUserName().contains(Search)){
                                        temps.add(Searchs.get(i));
                                    }
                                }
                            }
                            users.clear();
                            users.addAll(temps);
                        }
                        userAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                btCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userDialog.dismiss();
                    }
                });
                btSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FwhBean fwhBean = fwhList.get(userPosition);
                        for(int i = 0;i<users.size();i++){
                            if(users.get(i).getSelected()){
                                fwhBean.setWorkStationBelongTeamName(users.get(i).getUserName());
                                fwhBean.setWorkStationBelongTeam(Long.parseLong(users.get(i).getIdx()));
                                mainAc.getProgressDialog().show();
                                mPresenter.dispatchFwh(JSON.toJSONString(new FwhBean[]{fwhBean}));
                                userDialog.dismiss();
                                return;
                            }
                        }
                        ToastUtil.toastShort("还未选择班组");
                        userDialog.dismiss();
                    }
                });
                userDialog = new AlertDialog.Builder(mContext).setView(view).create();
                userDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        etSearch.setText("");
                        users.clear();
                        users.addAll(userlistTemp);
                        adapter.notifyDataSetChanged();
                    }
                });
                userDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {

                    }
                });
                userAdapter =new SelectUserNewAdapter(mContext, users);
                lvUser.setAdapter(userAdapter);
            }
            userDialog.show();
            userDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        }
    }

    @Override
    public void OnLoadTrainSuccess(List<ScheduleTrainBean> list) {
        if(list!=null&&list.size()>0){
            ticketTrainBeans.addAll(list);
            mPresenter.getFwhListNew(ticketTrainBeans.get(0).getIdx(),"no");
        }else {
            mainAc.getProgressDialog().dismiss();
            ToastUtil.toastShort("在修机车列表数据为空");
        }
        tabTrain.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return ticketTrainBeans.size();
            }

            @Override
            public ITabView.TabBadge getBadge(int position) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                return new QTabView.TabTitle.Builder()
                        .setContent((ticketTrainBeans.get(position).getTrainTypeShortName()+"\n"+
                                ticketTrainBeans.get(position).getTrainNo()))
                        .setTextColor(0xff3E5FE5,0xff616161 )
                        .setTextSize(18)
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return R.drawable.verticaltab_bg;
            }
        });
    }
}
