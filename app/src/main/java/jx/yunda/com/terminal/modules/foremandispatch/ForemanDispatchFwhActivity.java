package jx.yunda.com.terminal.modules.foremandispatch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.base.adapter.UserListAdapter;
import jx.yunda.com.terminal.modules.foremandispatch.model.EmpForForemanDispatch;
import jx.yunda.com.terminal.modules.foremandispatch.model.FwFormenBean;
import jx.yunda.com.terminal.modules.foremandispatch.presenter.ForemanDispatchFwhPresenter;
import jx.yunda.com.terminal.modules.foremandispatch.presenter.IForemanDispatchFwh;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FwhBean;
import jx.yunda.com.terminal.utils.NetWorkUtils;
import jx.yunda.com.terminal.utils.ToastUtil;

public class ForemanDispatchFwhActivity extends BaseActivity<ForemanDispatchFwhPresenter>
        implements View.OnClickListener, IForemanDispatchFwh {


    @BindView(R.id.menu_tp)
    Toolbar menuTp;

    @BindView(R.id.nodeInfo)
    TextView nodeInfo;

    @BindView(R.id.tvOperator)
    TextView tvOperator;

    @BindView(R.id.btn_dispatch)
    Button btnDispatch;
    @BindView(R.id.ll_btn)
    RelativeLayout llBtn;

    FwFormenBean fwhBean;
    ListView lvUser;
    Button btCancle;
    Button btSubmit;
    Button btClear;
    TextView tvUsers;
    EditText etSearch;
    boolean isRePost = false;
    @Override
    protected ForemanDispatchFwhPresenter getPresenter() {
        return new ForemanDispatchFwhPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_foremandispatch_fwh;
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
        tvOperator.setOnClickListener(this);
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            fwhBean = (FwFormenBean) getIntent().getSerializableExtra("fwhBean");
            if (fwhBean != null) {
                nodeInfo.setText(fwhBean.getNodeName());
                if (!TextUtils.isEmpty(fwhBean.getWorkerNameStr())) {
                    tvOperator.setText(fwhBean.getWorkerNameStr());
                    tvOperator.setTag(fwhBean.getWorkerIdxStr());
                    isRePost = true;
                }else {
                    isRePost = false;
                }

                // 判断是否填写数据
                if (tvOperator.getTag() != null && TextUtils.isEmpty(tvOperator.getTag().toString())) {
                    btnDispatch.setEnabled(false);
                    btnDispatch.setBackgroundResource(R.drawable.button_selector_gray);
                } else {
                    btnDispatch.setEnabled(true);
                    btnDispatch.setBackgroundResource(R.drawable.button_selector);
                }

                if (FwhBean.STATUS_UNSTART.equals(fwhBean.getStatus())) {
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
            case R.id.tvOperator:
                mPresenter.getEmpList(SysInfo.userInfo.org.getOrgid().toString(), "");
                break;
            case R.id.btn_dispatch:
                // 判断是否填写数据
                if (tvOperator.getTag() == null || TextUtils.isEmpty(tvOperator.getTag().toString())) {
                    ToastUtil.toastShort("请选择作业人员！");
                    return;
                }

                fwhBean.setWorkerNameStr(tvOperator.getText().toString());
                fwhBean.setWorkerIdxStr(tvOperator.getTag().toString());
                mPresenter.dispatchFwh(fwhBean.getIdx(),
                        tvOperator.getTag().toString(),
                        tvOperator.getText().toString());

                break;
        }
    }

    AlertDialog userDialog;
    UserListAdapter UserAdapter;
    List<EmpForForemanDispatch> userlist = new ArrayList<>();
    List<EmpForForemanDispatch> userlistTemp = new ArrayList<>();
    int showNumber = 0;
    @Override
    public void onEmpListLoadSuccess(final List<EmpForForemanDispatch> list) {
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
                        List<EmpForForemanDispatch> temps = new ArrayList<>();
                        String Search = s.toString().toUpperCase();
                        if(userlistTemp.size()>0){
                            for(int i = 0;i<userlistTemp.size();i++){
                                if(Pinyin.toPinyin(userlistTemp.get(i).getEmpname(),"").toUpperCase().contains(Search)||
                                        userlistTemp.get(i).getEmpname().contains(Search)){
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
            UserAdapter =new UserListAdapter(this, userlist);
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
                    List<EmpForForemanDispatch> temps = new ArrayList<>();
                    List<String> reChecks = new ArrayList<>();
                    for(EmpForForemanDispatch bean:userlist){
                        if(tvOperator.getTag()==null){
                            if(bean.getChecked()){
                                temps.add(bean);
                            }
                        }else {
                            if(bean.getChecked()){
                                if(tvOperator.getText().toString().contains(bean.getEmpname())&&showNumber>0){
                                    reChecks.add(bean.getEmpname());
                                }else {
                                    temps.add(bean);
                                }
                            }
                        }
                    }
                    if(temps.size()>0){
                        if(showNumber==0){
                            tvOperator.setTag(null);
                            tvOperator.setText("");
                        }
                        for(int i = 0;i<temps.size();i++){
                            if(tvOperator.getTag()==null){
                                tvOperator.setText(temps.get(i).getEmpname());
                                tvOperator.setTag(temps.get(i).getEmpid()+"");
                            }else {
                                tvOperator.setText(tvOperator.getText().toString()+","+temps.get(i).getEmpname());
                                tvOperator.setTag(tvOperator.getTag().toString()+","+temps.get(i).getEmpid()+"");
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
                    showNumber++;
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
