package jx.yunda.com.terminal.modules.schedule.frg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.modules.schedule.act.ScheduleBookNewActivity;
import jx.yunda.com.terminal.modules.schedule.adapter.ScheduleBookAdapter;
import jx.yunda.com.terminal.modules.schedule.adapter.SelectTrainAdapter;
import jx.yunda.com.terminal.modules.schedule.entity.OrgBean;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;
import jx.yunda.com.terminal.modules.schedule.entity.StationBean;
import jx.yunda.com.terminal.modules.schedule.presenter.IScheduleBook;
import jx.yunda.com.terminal.modules.schedule.presenter.ScheduleBookPresenter;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;

public class ScheduleBookFragment extends BaseFragment<ScheduleBookPresenter> implements IScheduleBook {
    Unbinder unbinder;
    ScheduleBookAdapter adapter;
    AlertDialog dialog;
    String nowData;
    @BindView(R.id.rlUsers)
    RecyclerView rlUsers;
    @BindView(R.id.btBook)
    Button btBook;
    GridView gvTrains;
    SelectTrainAdapter trainAdapter;
    int EquipPosition = 0;
    int TrainPosition = 0;
    @Override
    protected ScheduleBookPresenter getPresenter() {
        return new ScheduleBookPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_schedule_book;
    }

    @Override
    public void initSubViews(View view) {

    }

    ScheduleBookNewActivity mainact;
    @Override
    public void initData() {
        mainact = (ScheduleBookNewActivity) getActivity();
        setData();
    }
    boolean isDialogFirstShow = false;
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
        adapter = new ScheduleBookAdapter(getContext(), orgList);
        rlUsers.setAdapter(adapter);
        adapter.setOnItemClickListner(new ScheduleBookAdapter.OnItemClickListner() {
            @Override
            public void OnItemClick(int position) {
                EquipPosition = position;
                if(trainBeanList.size()>0){
                    View view = LayoutInflater.from(mainact).inflate(R.layout.dialog_schedule_book,null);
                    gvTrains = (GridView)view.findViewById(R.id.gvTrains);
                    trainAdapter = new SelectTrainAdapter(mainact,orgList.get(position).getTrainList());
                    gvTrains.setAdapter(trainAdapter);
                    dialog = new AlertDialog.Builder(mainact).setView(view)
                            .setTitle("点名").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            adapter.notifyDataSetChanged();
                        }
                    });
                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialog) {
                            isDialogFirstShow = true;
                        }
                    });
                    trainAdapter.setOnSelectedChangedListner(new SelectTrainAdapter.OnSelectedChangedListner() {
                        @Override
                        public void OnselectedChanged(int position, boolean isChecked) {
                            if(isChecked){
                                if(orgList.get(EquipPosition).getTrainList().get(position).isBook()){
                                    return;
                                }
                                mainact.getProgressDialog().show();
                                TrainPosition = position;
                                Map<String,Object> map = new HashMap<>();
                                map.put("handeOrgName",orgList.get(EquipPosition).getHandeOrgName());
                                map.put("handeOrgID",orgList.get(EquipPosition).getHandeOrgID());
                                map.put("trainWorkPlanIdx",orgList.get(EquipPosition).getTrainList().get(position)
                                .getIdx());
                                //返回值修改：由JSON改至JSONArray
                                mPresenter.ScduleBook("[" + JSON.toJSONString(map) + "]");
                            }else {
                                if(!orgList.get(EquipPosition).getTrainList().get(position).isBook()){
                                    return;
                                }
                                mainact.getProgressDialog().show();
                                TrainPosition = position;
                                mPresenter.CancleScduleBook(orgList.get(EquipPosition).getTrainList().get(position).getIdx(),orgList.get(EquipPosition).getHandeOrgID()+"",orgList.get(EquipPosition).getHandeOrgName());
                            }
                        }
                    });
                }
            }
        });
    }

    private void getUser() {
        mainact.getProgressDialog().show();
        mPresenter.getOrgList();
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        nowData = sf2.format(new Date());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (btBook != null)
            btBook.setText("点名");
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

    List<OrgBean> orgList = new ArrayList<>();
    List<ScheduleTrainBean> trainBeanList = new ArrayList<>();
    @Override
    public void OnLoadOrgSuccess(List<OrgBean> list) {
        mainact.getProgressDialog().dismiss();
        orgList.clear();
        if (list != null && list.size() > 0) {
            orgList.addAll(list);
        }
        adapter.notifyDataSetChanged();
        mainact.getProgressDialog().show();
        mPresenter.getTarinList();
    }

    @Override
    public void OnLoadOrgFaild(String msg) {
        mainact.getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void OnloadStationSuccess(List<StationBean> list) {

    }

    @Override
    public void OnLoadStationFaild(String msg) {
    }

    @Override
    public void submitStationSuccess() {
        mainact.getProgressDialog().dismiss();
        ToastUtil.toastShort("取消点名成功");
        orgList.get(EquipPosition).getTrainList().get(TrainPosition).setBook(false);
    }

    @Override
    public void submitStationFaild(String msg) {
        mainact.getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
        if(orgList.get(EquipPosition).getTrainList().get(TrainPosition).getStatus()==0){
            orgList.get(EquipPosition).getTrainList().get(TrainPosition).setStatus(1);
        }else {
            orgList.get(EquipPosition).getTrainList().get(TrainPosition).setStatus(0);
        }
        trainAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnLoadTrainSuccess(List<ScheduleTrainBean> list) {
        mainact.getProgressDialog().dismiss();
        if(list!=null&&list.size()>0){
            trainBeanList.clear();
            trainBeanList.addAll(list);
            for(ScheduleTrainBean bean: trainBeanList){
                bean.setStatus(0);
            }
            for(OrgBean org : orgList){
                List<ScheduleTrainBean> templist = null;
                try {
                    templist = Utils.deepCopy(trainBeanList);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                org.setTrainList(templist);
            }
            for(OrgBean bean:orgList){
                if(bean.getTrainTypeShortName()!=null&&!"".equals(bean.getTrainTypeShortName())){
                    String names[] = bean.getTrainTypeShortName().split(",");
                    List<ScheduleTrainBean> trains = bean.getTrainList();
                    if(names.length>0&&trains!=null&&trains.size()>0){
                        String nums[] = bean.getTrainNo().split(",");
                        String idx[] = bean.getWorkPlanIdx().split(",");
                        for(int i = 0;i<names.length;i++){
                            for(ScheduleTrainBean train:trains){
                                if(train.getTrainTypeShortName().equals(names[i])&&
                                        train.getTrainNo().equals(nums[i])){
                                    train.setStatus(1);
                                    train.setBook(true);
                                    break;
                                }
                            }
                        }
                    }else {
                        for(ScheduleTrainBean train:trains){
                            if(train.getTrainTypeShortName().equals(bean.getTrainTypeShortName())&&
                                    train.getTrainNo().equals(bean.getTrainNo())){
                                train.setStatus(1);
                                train.setBook(true);
                                break;
                            }
                        }
                    }
                    bean.setTrainList(trains);
                }
            }
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void OnLoadTrainFaild(String msg) {
        mainact.getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void submitBookSuccess() {
        mainact.getProgressDialog().dismiss();
        ToastUtil.toastShort("点名成功");
        orgList.get(EquipPosition).getTrainList().get(TrainPosition).setBook(true);
    }

    @Override
    public void submitBookFaild(String msg) {

    }
}
