package jx.yunda.com.terminal.modules.partsrecandition.act;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.TrainRecandition.adapter.EquipListAdapter;
import jx.yunda.com.terminal.modules.TrainRecandition.model.EquipBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.ModulesBean;
import jx.yunda.com.terminal.modules.TrainRecandition.presenter.IUpEquip;
import jx.yunda.com.terminal.modules.TrainRecandition.presenter.UpEquipPresenter;
import jx.yunda.com.terminal.modules.partsrecandition.adapter.DownPartsListAdapter;
import jx.yunda.com.terminal.modules.partsrecandition.adapter.UpPartsAdapter;
import jx.yunda.com.terminal.modules.partsrecandition.model.ChildUpEquipBean;
import jx.yunda.com.terminal.modules.partsrecandition.presenter.IUpParts;
import jx.yunda.com.terminal.modules.partsrecandition.presenter.UpPartsPresenter;
import jx.yunda.com.terminal.utils.ToastUtil;

public class UpPartsActivity extends BaseActivity<UpPartsPresenter> implements IUpParts, BaseActivity.OnScanCodeCallBack, UpPartsAdapter.OnUpClickListner {


    List<ChildUpEquipBean> list = new ArrayList<>();
    UpPartsAdapter adapter;
    Dialog dialog;
    ImageView ivDelete;
    TextView tvBJName, tvEquipName;
    EditText etCode, etScanCode;
    Button btFinish;
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvTicketName)
    TextView tvTicketName;
    @BindView(R.id.tvPlanName)
    TextView tvPlanName;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tb_title)
    TabLayout tbTitle;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.lvEquip)
    ListView lvEquip;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    int Status = 0;
    int dialogStatus = 0;
    String RdpIDX, nodeCaseIDX, trainTypeIDX, ShortName, TrainNo;
    PopupMenu popupMenu, popupMenu2;
    List<ModulesBean> modulesBeans = new ArrayList<>();
    List<String> codes = new ArrayList<>();

    @Override
    protected UpPartsPresenter getPresenter() {
        return new UpPartsPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_up_equip;
    }

    @Override
    public void initSubViews(View view) {

    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvPlanName.setText(bundle.getString("name"));
            tvTime.setText(bundle.getString("startTime") + "——" + bundle.getString("endTime"));
            RdpIDX = bundle.getString("RdpIDX");
            nodeCaseIDX = bundle.getString("nodeCaseIDX");
            trainTypeIDX = bundle.getString("trainType");
            TrainNo = bundle.getString("TrainNo");
            tvTicketName.setText(trainTypeIDX + " " + TrainNo);
        }
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setData();
        adapter = new UpPartsAdapter(this, list, "上配件");
        adapter.setOnUpClickListner(this);
        srRefresh.setRefreshHeader(new ClassicsHeader(this));
        srRefresh.setRefreshFooter(new ClassicsFooter(this));
        srRefresh.setNoMoreData(true);
        lvEquip.setAdapter(adapter);
        setOnScanCodeCallBack(this);
        tbTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Status = 0;
                        adapter.setStatus(0);
                        setData();
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        Status = 1;
                        adapter.setStatus(1);
                        setData();
                        adapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public int EquipPosition;

    private void addEquip() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.dialog);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    dialogStatus = 0;
                }
            });
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    dialogStatus = 1;
                }
            });
            LayoutInflater inflater = LayoutInflater.from(this);
            View viewDialog = inflater.inflate(R.layout.dialog_add_equip, null);
            Window window = dialog.getWindow();
            window.setWindowAnimations(R.style.mystyle);  //添加动画
            dialog.setContentView(viewDialog);
            ivDelete = (ImageView) viewDialog.findViewById(R.id.ivDelete);
            tvBJName = (TextView) viewDialog.findViewById(R.id.tvBJName);
            etCode = (EditText) viewDialog.findViewById(R.id.etCode);
            btFinish = (Button) viewDialog.findViewById(R.id.btFinish);
            tvEquipName = (TextView) viewDialog.findViewById(R.id.tvEquipName);
            etScanCode = (EditText) viewDialog.findViewById(R.id.etScanCode);
            etScanCode.setVisibility(View.GONE);
            viewDialog.findViewById(R.id.tvScanCode).setVisibility(View.GONE);
            tvBJName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> map = new HashMap<>();
//                    if(list.get(EquipPosition).getSpecificationModel()!=null){
//                        map.put("specificationModel",list.get(EquipPosition).getSpecificationModel());
//                    }
                    if (list.get(EquipPosition).getJcpjbm() != null) {
                        map.put("jcpjbm", list.get(EquipPosition).getJcpjbm());
                    }
                    getProgressDialog().show();
                    mPresenter.getEquipTypes(JSON.toJSONString(map));
                }
            });
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tvBJName.getTag() == null) {
                        ToastUtil.toastShort("规格型号还未选择");
                        return;
                    }
                    if (etCode.getText() == null || etCode.getText().toString().equals("")) {
                        ToastUtil.toastShort("配件编码还未输入");
                        return;
                    }
//                    if (etScanCode.getText() == null || etScanCode.getText().toString().equals("")) {
//                        ToastUtil.toastShort("配件二维码还未扫描");
//                        return;
//                    }
//                    Map<String, String> map = new HashMap<>();
//                    map.put("rdpIdx", list.get(EquipPosition).getRdpIdx());
//                    map.put("rdpType", "配件");
//                    map.put("fixEmpId", SysInfo.userInfo.emp.getEmpid() + "");
//                    map.put("fixEmp", SysInfo.userInfo.emp.getEmpname());
//                    map.put("aboardTrainTypeIdx", trainTypeIDX);
////                    map.put("aboardTrainType", ShortName);
//                    map.put("aboardTrainNo", TrainNo);
//                    map.put("idx", list.get(EquipPosition).getIdx());
//                    map.put("partsTypeIDX", tvBJName.getTag().toString());
//                    map.put("specificationModel", tvBJName.getText().toString());
//                    map.put("partsName", list.get(EquipPosition).getPartsName());
//                    map.put("partsNo", etCode.getText().toString());
//                    map.put("identificationCode", etScanCode.getText().toString());
//                    map.put("isInRange", list.get(EquipPosition).getIsInRange());
//                    map.put("aboardPlace",list.get(EquipPosition).getUnloadPlace());
//                    ArrayList<Map<String, String>> temp = new ArrayList<>();
//                    temp.add(map);
//                    getProgressDialog().show();
//                    mPresenter.UpEquipNew(JSON.toJSONString(temp));
                    Map<String, String> map = new HashMap<>();
                    map.put("rdpIdx", list.get(EquipPosition).getRdpIdx());
                    map.put("rdpType", "配件");
                    map.put("fixEmpId", SysInfo.userInfo.emp.getEmpid() + "");
                    map.put("fixEmp", SysInfo.userInfo.emp.getEmpname());
                    map.put("aboardTrainTypeIdx", trainTypeIDX);
                    map.put("aboardTrainType", ShortName);
                    map.put("aboardTrainNo", TrainNo);
                    map.put("idx", list.get(EquipPosition).getIdx());
                    map.put("partsTypeIDX", tvBJName.getTag().toString());
                    map.put("specificationModel", tvBJName.getText().toString());
                    map.put("partsName", list.get(EquipPosition).getPartsName());
                    map.put("partsNo", etCode.getText().toString());
//                    map.put("identificationCode", etCode.getText().toString());
                    map.put("isInRange", list.get(EquipPosition).getIsInRange());
                    map.put("aboardPlace", list.get(EquipPosition).getUnloadPlace());
                    ArrayList<Map<String, String>> temp = new ArrayList<>();
                    temp.add(map);
                    getProgressDialog().show();
                    mPresenter.UpEquipNew(JSON.toJSONString(temp));
                    dialog.dismiss();
                }
            });

            popupMenu = new PopupMenu(this, tvBJName);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    tvBJName.setText(item.getTitle());
                    tvBJName.setTag(modulesBeans.get(item.getOrder()).getIdx());
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(tvBJName.getWindowToken(), 0);
                    }
                    codes.clear();
                    mPresenter.getEquipCode(item.getTitle().toString(), "0103");
                    popupMenu.dismiss();
                    return false;
                }
            });
            popupMenu2 = new PopupMenu(this, etCode);
            popupMenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    etCode.setText(item.getTitle());
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(etCode.getWindowToken(), 0);
                    }
                    popupMenu2.dismiss();
                    return false;
                }
            });
            etCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = "";
                    if (etCode.getText() != null)
                        text = etCode.getText().toString();
                    List<String> temps = new ArrayList<>();
                    if (codes.size() > 0) {
                        if (text.equals("如果出现非常见现象，请输入") || text.equals("")) {
                            temps.addAll(codes);
                        } else {
                            for (String bean : codes) {
                                if (bean.contains(text)) {
                                    temps.add(bean);
                                }
                            }
                        }
                    }

                    if (temps.size() > 0) {
                        android.view.Menu menu_more = popupMenu2.getMenu();
                        menu_more.clear();
                        int size = temps.size();
                        for (int i = 0; i < size; i++) {
                            menu_more.add(android.view.Menu.NONE, android.view.Menu.FIRST + i, i, temps.get(i));
                        }
                        popupMenu2.show();
                    }
                }
            });
        }
        if(list.get(EquipPosition).getUnloadPlace()!=null){
            tvEquipName.setText(list.get(EquipPosition).getPartsName()+" "+list.get(EquipPosition).getUnloadPlace());
        }else {
            tvEquipName.setText(list.get(EquipPosition).getPartsName());
        }
        tvBJName.setText("规格型号");
        tvBJName.setTag(null);
        etCode.setText("");
        etScanCode.setText("");
        dialog.show();
    }


    private void setData() {
        list.clear();
        getProgressDialog().show();
        if (Status == 0) {
            mPresenter.getUpEquips(RdpIDX, nodeCaseIDX, "1");
        } else {
            mPresenter.getUpEquips(RdpIDX, nodeCaseIDX, "2");
        }
    }

    @Override
    public void OnSearchSuccess() {

    }

    @Override
    public void OnSearchFaild() {

    }

    @Override
    public void OnSubmitSuccess() {
        getProgressDialog().dismiss();
        setData();
    }

    @Override
    public void OnsubmitFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void OnDeletSuccess() {
        getProgressDialog().dismiss();
        setData();
        ToastUtil.toastShort("撤销成功");
    }

    @Override
    public void OnDeletFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void OnPartsNoLoadSuccess(List<String> list) {
        getProgressDialog().dismiss();
        if (list != null && list.size() > 0) {
            codes.clear();
            codes.addAll(list);
//            android.view.Menu menu_more = popupMenu2.getMenu();
//            menu_more.clear();
//            for (int i = 0; i < this.codes.size(); i++) {
//                menu_more.add(android.view.Menu.NONE, android.view.Menu.FIRST + i, i, codes.get(i));
//            }
//            popupMenu2.show();
        }
    }

    @Override
    public void OnPartsNoLoadFaild(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void getUpEquipSuccess(ArrayList<ChildUpEquipBean> list) {
        getProgressDialog().dismiss();
        if (list != null && list.size() > 0) {
            this.list.addAll(list);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getUnEquipFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void getTypeSuccess(List<ModulesBean> list) {
        getProgressDialog().dismiss();
        if (list != null && list.size() > 0) {
            this.modulesBeans.clear();
            this.modulesBeans.addAll(list);
            android.view.Menu menu_more = popupMenu.getMenu();
            menu_more.clear();
            for (int i = 0; i < this.modulesBeans.size(); i++) {
                menu_more.add(android.view.Menu.NONE, android.view.Menu.FIRST + i, i, this.modulesBeans.get(i).getSpecificationModel());
            }
            popupMenu.show();
        }
    }

    @Override
    public void getTypeFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void ScanCodeSuccess(final String code) {
        ToastUtil.toastShort("扫码成功");
        if (dialogStatus == 0) {
            new AlertDialog.Builder(this).setTitle("提示").setMessage("是否登记识别码为"+
                    code+"的配件?").setNegativeButton("取消",null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Map<String, String> map = new HashMap<>();
//            map.put("rdpIdx", list.get(EquipPosition).getRdpIdx());
                            map.put("rdpType", "配件");
                            map.put("fixEmpId", SysInfo.userInfo.emp.getEmpid() + "");
                            map.put("fixEmp", SysInfo.userInfo.emp.getEmpname());
//            map.put("aboardTrainTypeIdx", trainTypeIDX);
//            map.put("aboardTrainType", ShortName);
//            map.put("aboardTrainNo", TrainNo);
//            map.put("idx", list.get(EquipPosition).getIdx());
//        map.put("partsTypeIDX",tvBJName.getTag().toString());
//        map.put("specificationModel",tvBJName.getText().toString());
//        map.put("partsName",list.get(EquipPosition).getPartsName());
//        map.put("partsNo",etCode.getText().toString());
                            map.put("identificationCode", code);
//            map.put("isInRange", list.get(EquipPosition).getIsInRange());
//            map.put("aboardPlace",list.get(EquipPosition).getUnloadPlace());
                            ArrayList<Map<String, String>> temp = new ArrayList<>();
                            temp.add(map);
                            getProgressDialog().show();
                            mPresenter.UpEquip(JSON.toJSONString(temp));
                        }
                    }).show();
            etSearch.setText(code);

        }
//        else {
//            Map<String, String> map = new HashMap<>();
////            map.put("rdpIdx", list.get(EquipPosition).getRdpIdx());
//            map.put("rdpType", "配件");
//            map.put("fixEmpId", SysInfo.userInfo.emp.getEmpid() + "");
//            map.put("fixEmp", SysInfo.userInfo.emp.getEmpname());
////            map.put("aboardTrainTypeIdx", trainTypeIDX);
////            map.put("aboardTrainType", ShortName);
////            map.put("aboardTrainNo", TrainNo);
////            map.put("idx", list.get(EquipPosition).getIdx());
////        map.put("partsTypeIDX",tvBJName.getTag().toString());
////        map.put("specificationModel",tvBJName.getText().toString());
////        map.put("partsName",list.get(EquipPosition).getPartsName());
////        map.put("partsNo",etCode.getText().toString());
//            map.put("identificationCode", code);
////            map.put("isInRange", list.get(EquipPosition).getIsInRange());
////            map.put("aboardPlace",list.get(EquipPosition).getUnloadPlace());
//            ArrayList<Map<String, String>> temp = new ArrayList<>();
//            temp.add(map);
//            getProgressDialog().show();
//            mPresenter.UpEquip(JSON.toJSONString(map));
//        }

    }

    @Override
    public void OnUpClick(int position) {
        EquipPosition = position;
        addEquip();
    }

    @Override
    public void OnBackUp(final int position) {
        new AlertDialog.Builder(this).setTitle("提示").setMessage("是否撤销当前配件下车记录")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getProgressDialog().show();
                        mPresenter.UpEquipBack(list.get(position).getIdx());
                    }
                }).show();
    }
}
