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
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.TrainRecandition.adapter.DownEquipListAdapter;
import jx.yunda.com.terminal.modules.TrainRecandition.model.DownEquipBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.ModulesBean;
import jx.yunda.com.terminal.modules.partsrecandition.adapter.DownPartsListAdapter;
import jx.yunda.com.terminal.modules.partsrecandition.model.ChildEquipBean;
import jx.yunda.com.terminal.modules.partsrecandition.presenter.DownPartsPresenter;
import jx.yunda.com.terminal.modules.partsrecandition.presenter.IDownParts;
import jx.yunda.com.terminal.utils.ToastUtil;

public class DownPartsActivity extends BaseActivity<DownPartsPresenter> implements IDownParts, BaseActivity.OnScanCodeCallBack
        , DownPartsListAdapter.OnDownClickListner {

    List<ChildEquipBean> list = new ArrayList<>();
    List<ModulesBean> modulesBeans = new ArrayList<>();
    DownPartsListAdapter adapter;
    Dialog dialog;
    ImageView ivDelete;
    TextView tvBJName, tvScanTitle, tvEquipName;
    EditText etCode, etScanCode;
    Button btFinish;
    PopupMenu popupMenu, popupMenu2;
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
    @BindView(R.id.lvEquip)
    ListView lvEquip;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    List<String> codes = new ArrayList<>();

    @Override
    protected DownPartsPresenter getPresenter() {
        return new DownPartsPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_down_equip;
    }

    @Override
    public void initSubViews(View view) {

    }

    String RdpIDX, nodeCaseIDX, trainTypeIDX, ShortName, TrainNo, unloadPlace, specificationModel;
    int Status = 0;

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
            specificationModel = bundle.getString("specificationModel");
        }
        setData();
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new DownPartsListAdapter(this, list, "下配件");
        adapter.setOnDownClickListner(this);
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

    //    @OnClick(R.id.tvAdd)
    public int EquipPosition;

    public void addEquip() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.dialog);
            LayoutInflater inflater = LayoutInflater.from(this);
            View viewDialog = inflater.inflate(R.layout.dialog_add_equip, null);
            Window window = dialog.getWindow();
            window.setWindowAnimations(R.style.mystyle);  //添加动画
            dialog.setContentView(viewDialog);
            ivDelete = (ImageView) viewDialog.findViewById(R.id.ivDelete);
            tvBJName = (TextView) viewDialog.findViewById(R.id.tvBJName);
            tvScanTitle = (TextView) viewDialog.findViewById(R.id.tvScanTitle);
            tvEquipName = (TextView) viewDialog.findViewById(R.id.tvEquipName);
            tvScanTitle.setVisibility(View.VISIBLE);
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
                    codes.clear();
                    mPresenter.getEquipTypes(JSON.toJSONString(map));
                }
            });
            etCode = (EditText) viewDialog.findViewById(R.id.etCode);
            etScanCode = (EditText) viewDialog.findViewById(R.id.etScanCode);
            btFinish = (Button) viewDialog.findViewById(R.id.btFinish);
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
//                    if(etCode.getText()==null||etCode.getText().toString().equals("")){
//                        ToastUtil.toastShort("配件编码还未输入");
//                        return;
//                    }
//                    if(etScanCode.getText()==null||etScanCode.getText().toString().equals("")){
//                        ToastUtil.toastShort("配件二维码还未扫描");
//                        return;
//                    }
                    Map<String, Object> map = new HashMap<>();
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("specificationModel", specificationModel);
                    map1.put("rdpIdx", RdpIDX);

                    map.put("parentPartsAccountIDX", list.get(EquipPosition).getPartsAccountIDX());
                    map.put("idx", list.get(EquipPosition).getIdx());
//                    map.put("rdpIdx",RdpIDX);
                    map.put("partsTypeIDX", tvBJName.getTag().toString());
                    map.put("specificationModel", tvBJName.getText().toString());
                    map.put("partsName", list.get(EquipPosition).getPartsName());
                    if (etCode.getText() != null && !etCode.getText().toString().equals(""))
                        map.put("partsNo", etCode.getText().toString());
                    if (etScanCode.getText() != null && !etScanCode.getText().toString().equals(""))
                        map.put("identificationCode", etScanCode.getText().toString());
                    map.put("isInRange", list.get(EquipPosition).getIsInRange());
                    map.put("unloadPlace", list.get(EquipPosition).getUnloadPlace());
//                    map.put("unloadPlaceCode",list.get(EquipPosition).getPartsAccountIDX());
                    ArrayList<Map<String, Object>> temp = new ArrayList<>();
                    temp.add(map);
                    getProgressDialog().show();
//                    map1.put("registerArray",JSON.toJSONString(temp));
                    mPresenter.DownEquip(JSON.toJSONString(map1), JSON.toJSONString(temp));
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
        if (list.get(EquipPosition).getUnloadPlace() != null) {
            tvEquipName.setText(list.get(EquipPosition).getPartsName() + " " + list.get(EquipPosition).getUnloadPlace());
        } else {
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
            mPresenter.getDownEquips(RdpIDX, nodeCaseIDX, "1");
        } else {
            mPresenter.getDownEquips(RdpIDX, nodeCaseIDX, "2");
        }
    }


    @Override
    public void OnDownSubmitSuccess() {
        dialog.dismiss();
        getProgressDialog().dismiss();
        setData();
        ToastUtil.toastShort("配件下车成功");
    }

    @Override
    public void OnDownsubmitFaild(String msg) {
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
    public void getTypeSuccess(final List<ModulesBean> modulesBeans) {
        getProgressDialog().dismiss();
        if (modulesBeans != null && modulesBeans.size() > 0) {
            this.modulesBeans.clear();
            this.modulesBeans.addAll(modulesBeans);
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
        ToastUtil.toastShort(msg);
        getProgressDialog().dismiss();
    }

    @Override
    public void getDowmEquipPlansSuccess(ArrayList<ChildEquipBean> beans) {
        getProgressDialog().dismiss();
        if (beans != null && beans.size() > 0) {
            this.list.addAll(beans);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getDowmEquipPlansFaild(String msg) {
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
    public void ScanCodeSuccess(String code) {
        if (etScanCode != null) {
            ToastUtil.toastShort("扫码成功");
            etScanCode.setText(code);
        }
    }

    @Override
    public void OnDwonClick(int position) {
        EquipPosition = position;
        addEquip();
    }

    @Override
    public void OnBackClick(final int position) {
        new AlertDialog.Builder(this).setTitle("提示").setMessage("是否撤销当前配件下车记录")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getProgressDialog().show();
                        mPresenter.DownEquipBack(list.get(position).getIdx());
                    }
                }).show();
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
}
