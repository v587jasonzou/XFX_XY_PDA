package jx.yunda.com.terminal.modules.workshoptaskdispatch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.base.adapter.UserListAdapter;
import jx.yunda.com.terminal.modules.foremandispatch.model.EmpForForemanDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.adapter.WorkShopUserListAdapter;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.EmpForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FwhBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.OrgForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.WorkstationForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter.IWorkshopTaskDispatchFwh;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter.WorkshopTaskDispatchFwhPresenter;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;

public class WorkshopTaskDispatchFwhActivity extends BaseActivity<WorkshopTaskDispatchFwhPresenter>
        implements View.OnClickListener, IWorkshopTaskDispatchFwh {


    @BindView(R.id.menu_tp)
    Toolbar menuTp;

    @BindView(R.id.nodeInfo)
    TextView nodeInfo;

    @BindView(R.id.tvOperateClass)
    TextView tvOperateClass;
    @BindView(R.id.tvOperator)
    TextView tvOperator;
    @BindView(R.id.tvWorkstation)
    TextView tvWorkstation;

    @BindView(R.id.btn_dispatch)
    Button btnDispatch;
    @BindView(R.id.ll_btn)
    RelativeLayout llBtn;

    FwhBean fwhBean;

    String stutes;
    @Override
    protected WorkshopTaskDispatchFwhPresenter getPresenter() {
        return new WorkshopTaskDispatchFwhPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_workshoptaskdispatch_fwh;
    }

    @Override
    public void initSubViews(View view) {
        //设置toolbar
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnDispatch.setOnClickListener(this);
        tvOperateClass.setOnClickListener(this);
        tvOperator.setOnClickListener(this);
        tvWorkstation.setOnClickListener(this);
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            fwhBean = (FwhBean) getIntent().getSerializableExtra("fwhBean");
            stutes = getIntent().getExtras().getString("status","已完成");
            if (fwhBean != null) {
                nodeInfo.setText(fwhBean.getNodeName());
                if (!TextUtils.isEmpty(fwhBean.getWorkStationBelongTeamName())) {
                    tvOperateClass.setText(fwhBean.getWorkStationBelongTeamName());
                    tvOperateClass.setTag(fwhBean.getWorkStationBelongTeam());
                }

                if (!TextUtils.isEmpty(fwhBean.getWorkerNameStr())) {
                    tvOperator.setText(fwhBean.getWorkerNameStr());
                    tvOperator.setTag(fwhBean.getWorkerIdsStr());
                }

                if (!TextUtils.isEmpty(fwhBean.getWorkStationName())) {
                    if(stutes.equals("已完成")){
                        tvWorkstation.setText(fwhBean.getWorkStationName());
                        tvWorkstation.setTag(fwhBean.getWorkStationIDX());
                    }
                }
                if(fwhBean.getNodeStatus().equals(FwhBean.STATUS_COMPLETE)||
                        fwhBean.getNodeStatus().equals(FwhBean.STATUS_STOP)){
                    tvWorkstation.setEnabled(false);
                }
                if (FwhBean.NODERDPTYPE_CLASS.equals(fwhBean.getNodeRdpType())) {
                    tvOperateClass.setVisibility(View.VISIBLE);
                    tvOperator.setVisibility(View.GONE);
                } else if (FwhBean.NODERDPTYPE_EMP.equals(fwhBean.getNodeRdpType())) {
                    tvOperateClass.setVisibility(View.GONE);
                    tvOperator.setVisibility(View.VISIBLE);
                }

                // 判断是否填写数据
                if (((FwhBean.NODERDPTYPE_CLASS.equals(fwhBean.getNodeRdpType()) && Utils.isEmpty(tvOperateClass.getTag()))
                        || (FwhBean.NODERDPTYPE_EMP.equals(fwhBean.getNodeRdpType()) && Utils.isEmpty(tvOperator.getTag())))
                        ) {
                    btnDispatch.setEnabled(false);
                    btnDispatch.setBackgroundResource(R.drawable.button_selector_gray);
                } else {
                    btnDispatch.setEnabled(true);
                    btnDispatch.setBackgroundResource(R.drawable.button_selector);
                }

                if (FwhBean.ISSENDNODE_N == fwhBean.getIsSendNode() ||
                        (FwhBean.ISSENDNODE_Y == fwhBean.getIsSendNode() && FwhBean.STATUS_UNSTART.equals(fwhBean.getNodeStatus()))) {
                    llBtn.setVisibility(View.VISIBLE);
                } else {
                    tvOperator.setEnabled(false);
                    llBtn.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        if (fwhBean == null) return;

        switch (v.getId()) {
            case R.id.tvOperateClass:
                mPresenter.getClassList(fwhBean.getProcessNodeIdx());
                break;
            case R.id.tvOperator:
                Map<String, String> p = new HashMap<>();
                p.put("processNodeIdx", fwhBean.getProcessNodeIdx());
                mPresenter.getEmpList(JSON.toJSONString(p), "omEmployeeSelect");
                break;
            case R.id.tvWorkstation:
                Map<String, String> param = new HashMap<>();
                param.put("twTypeIDX", fwhBean.getWorkStationTypeIDX());
                mPresenter.getWorkstationList(JSON.toJSONString(param), "workStation");
                break;
            case R.id.btn_dispatch:
                // 判断是否填写数据
                if (((FwhBean.NODERDPTYPE_CLASS.equals(fwhBean.getNodeRdpType()) && TextUtils.isEmpty(tvOperateClass.getTag().toString()))
                        || (FwhBean.NODERDPTYPE_EMP.equals(fwhBean.getNodeRdpType()) && TextUtils.isEmpty(tvOperator.getTag().toString())))
                        ) {
                    ToastUtil.toastShort("请选择作业班组或人员");
                    return;
                }

                fwhBean.setWorkStationBelongTeamName(tvOperateClass.getText().toString());
                fwhBean.setWorkStationBelongTeam((Long)tvOperateClass.getTag());
                if(tvOperator.getTag()!=null){
                    fwhBean.setWorkerNameStr(tvOperator.getText().toString());
                    fwhBean.setWorkerIdsStr(tvOperator.getTag().toString());
                }
                if(tvWorkstation.getTag()!=null){
                    fwhBean.setWorkStationName(tvWorkstation.getText().toString());
                    fwhBean.setWorkStationIDX(tvWorkstation.getTag().toString());
                }
                mPresenter.dispatchFwh(JSON.toJSONString(new FwhBean[]{fwhBean}));

                break;
        }
    }

    @Override
    public void onClassListLoadSuccess(final List<OrgForWorkshopTaskDispatch> list) {
        OptionsPickerView options = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvOperateClass.setText(list.get(options1).getOrgname());
                try {
                    tvOperateClass.setTag(Long.valueOf(list.get(options1).getId()));
                } catch (Exception e) {
                    LogUtil.e("onClassListLoadSuccess", e.getMessage());
                }

                // 判断是否填写数据
                if (((FwhBean.NODERDPTYPE_CLASS.equals(fwhBean.getNodeRdpType()) && TextUtils.isEmpty(tvOperateClass.getTag().toString()))
                        || (FwhBean.NODERDPTYPE_EMP.equals(fwhBean.getNodeRdpType()) && TextUtils.isEmpty(tvOperator.getTag().toString())))
                        ) {
                    btnDispatch.setEnabled(false);
                    btnDispatch.setBackgroundResource(R.drawable.button_selector_gray);
                } else {
                    btnDispatch.setEnabled(true);
                    btnDispatch.setBackgroundResource(R.drawable.button_selector);
                }
            }
        }).setTitleText("选择作业班组").setSubmitText("确定").build();
        List<String> list1 = new ArrayList<>();
        for (OrgForWorkshopTaskDispatch bean : list) {
            list1.add(bean.getOrgname());
        }
        options.setPicker(list1);
        options.show();
    }
    AlertDialog userDialog;
    WorkShopUserListAdapter UserAdapter;
    List<EmpForWorkshopTaskDispatch> userlist = new ArrayList<>();
    List<EmpForWorkshopTaskDispatch> userlistTemp = new ArrayList<>();
    ListView lvUser;
    Button btCancle;
    Button btSubmit;
    Button btClear;
    TextView tvUsers;
    EditText etSearch;
    @Override
    public void onEmpListLoadSuccess(final List<EmpForWorkshopTaskDispatch> list) {
        if (userDialog == null) {
            View view = LinearLayout.inflate(this, R.layout.dialog_select_user, null);
            lvUser = (ListView)view.findViewById(R.id.lvUser);
            btCancle = (Button) view.findViewById(R.id.btCancle);
            btSubmit = (Button)view.findViewById(R.id.btSubmit);
            btClear = (Button)view.findViewById(R.id.btClear);
            tvUsers = (TextView)view.findViewById(R.id.tvUsers);
            etSearch = (EditText)view.findViewById(R.id.etSearch);
            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.toString().equals("请输入人员姓名或拼音")||"".equals(s.toString())){
                        userlist.clear();
                        userlist.addAll(userlistTemp);
                    }else {
                        List<EmpForWorkshopTaskDispatch> temps = new ArrayList<>();
                        String Search = s.toString().toUpperCase();
                        if(userlistTemp.size()>0){
                            for(int i = 0;i<userlistTemp.size();i++){
                                if(Pinyin.toPinyin(userlistTemp.get(i).getText(),"").toUpperCase().contains(Search)||
                                        userlistTemp.get(i).getText().contains(Search)){
                                    temps.add(userlistTemp.get(i));
                                }
                            }
                        }
                        userlist.clear();
                        userlist.addAll(temps);
                    }
                    UserAdapter.notifyDataSetChanged();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            UserAdapter =new WorkShopUserListAdapter(this, userlist);
            lvUser.setAdapter(UserAdapter);
            lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(userlist.get(position).getChecked()==null||!userlist.get(position).getChecked()){
                        userlist.get(position).setChecked(true);
                    }else {
                        userlist.get(position).setChecked(false);
                    }
                    UserAdapter.notifyDataSetChanged();
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
                    List<EmpForWorkshopTaskDispatch> temps = new ArrayList<>();
                    List<String> reChecks = new ArrayList<>();
                    for(EmpForWorkshopTaskDispatch bean:userlist){
                        if(tvOperator.getTag()==null){
                            if(bean.getChecked()){
                                temps.add(bean);
                            }
                        }else {
                            if(bean.getChecked()){
                                if(tvOperator.getText().toString().contains(bean.getText())){
                                    reChecks.add(bean.getText());
                                }else {
                                    temps.add(bean);
                                }
                            }
                        }
                    }
                    if(temps.size()>0){
                        for(int i = 0;i<temps.size();i++){
                            if(tvOperator.getTag()==null){
                                tvOperator.setText(temps.get(i).getText());
                                tvOperator.setTag(temps.get(i).getId()+"");
                            }else {
                                tvOperator.setText(tvOperator.getText().toString()+","+temps.get(i).getText());
                                tvOperator.setTag(tvOperator.getTag().toString()+","+temps.get(i).getId()+"");
                            }
                        }
                    }
                    if(reChecks.size()>0){
                        String reUsers = "";
                        for(String str:reChecks){
                            if(reUsers.equals("")){
                                reUsers = reUsers+str;
                            }else {
                                reUsers = reUsers+","+str;
                            }
                        }
                        ToastUtil.toastShort(reUsers+"  已添加，不能重复添加");
                    }
                    userDialog.dismiss();
                }
            });
            btClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvOperator.setText("");
                    tvOperator.setTag(null);
                    userDialog.dismiss();
                }
            });
            userDialog = new AlertDialog.Builder(this).setView(view).create();
            userDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    etSearch.setText("");
                }
            });
        }
        userlist.clear();
        userlistTemp.clear();
        if(list!=null&&list.size()>0){
            userlist.addAll(list);
            userlistTemp.addAll(list);
            for(int i = 0;i<userlist.size();i++){
                userlist.get(i).setChecked(false);
                userlistTemp.get(i).setChecked(false);
            }
            UserAdapter.notifyDataSetChanged();
        }
        userDialog.show();
        userDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    @Override
    public void onWorkstationListLoadSuccess(final List<WorkstationForWorkshopTaskDispatch> list) {
        if(list==null||list.size()==0){
            ToastUtil.toastShort("无台位相关信息");
            return;
        }
        OptionsPickerView options = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvWorkstation.setText(list.get(options1).getWorkStationName());
                tvWorkstation.setTag(list.get(options1).getIdx());

                // 判断是否填写数据
                if (((FwhBean.NODERDPTYPE_CLASS.equals(fwhBean.getNodeRdpType()) && Utils.isEmpty(tvOperateClass.getTag()))
                        || (FwhBean.NODERDPTYPE_EMP.equals(fwhBean.getNodeRdpType()) && Utils.isEmpty(tvOperator.getTag())))
                        || Utils.isEmpty(tvWorkstation.getTag())) {
                    btnDispatch.setEnabled(false);
                    btnDispatch.setBackgroundResource(R.drawable.button_selector_gray);
                } else {
                    btnDispatch.setEnabled(true);
                    btnDispatch.setBackgroundResource(R.drawable.button_selector);
                }
            }
        }).setTitleText("选择作业台位").setSubmitText("确定").build();
        List<String> list1 = new ArrayList<>();
        for (WorkstationForWorkshopTaskDispatch bean : list) {
            list1.add(bean.getWorkStationName());
        }
        options.setPicker(list1);
        options.show();
    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void onDispatchSuccess(String msg) {
        ToastUtil.toastSuccess();

        Intent intent = new Intent();
        intent.putExtra("dispatchSuccess", true);
        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    public void onDispatchFail(String msg) {
        ToastUtil.toastShort(msg);
    }
}
