package jx.yunda.com.terminal.modules.tpprocess;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.base.JXApplication;
import jx.yunda.com.terminal.entity.FileBaseBean;
import jx.yunda.com.terminal.modules.tpprocess.RealmDb.ImageDao;
import jx.yunda.com.terminal.modules.tpprocess.RealmDb.TicketSubmitDao;
import jx.yunda.com.terminal.modules.tpprocess.adapter.SelectDictAdapter;
import jx.yunda.com.terminal.modules.tpprocess.adapter.TicketDictAdapter;
import jx.yunda.com.terminal.modules.tpprocess.adapter.TreeViewAdapter;
import jx.yunda.com.terminal.modules.tpprocess.model.DictBean;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultBean;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultTreeBean;
import jx.yunda.com.terminal.modules.tpprocess.model.StandardBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTypeBean;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.NetWorkUtils;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.MyListView;
import jx.yunda.com.terminal.widget.PhotoReadActivity;
import jx.yunda.com.terminal.widget.SodukuGridView;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 新增提票
 */
public class TicketInfoActivity extends BaseActivity<ITicketInfoPresenter> implements ITicketInfo, View.OnClickListener, TreeViewAdapter.OnImageClickLisnter {


    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvTicketName)
    TextView tvTicketName;
    @BindView(R.id.tvBJName)
    TextView tvBJName;
    @BindView(R.id.tvZYName)
    TextView tvZYName;
    @BindView(R.id.tvKHName)
    TextView tvKHName;
    @BindView(R.id.etGZXXInfo)
    EditText etGZXXInfo;
    @BindView(R.id.gvImages)
    SodukuGridView gvImages;
    @BindView(R.id.etSXFNInfo)
    EditText etSXFNInfo;
    @BindView(R.id.btNext)
    Button btNext;
    @BindView(R.id.cbIsTodo)
    CheckBox cbIsTodo;
    /**
     *[专业、类型...]列表
     */
    @BindView(R.id.lvEos)
    MyListView lvEos;
    @BindView(R.id.llBJName)
    LinearLayout llBJName;
    ArrayList<String> images = new ArrayList<>();
    ArrayList<ImageItem> imagesTemp = new ArrayList<>();
    ImagesAdapter adapter;
    List<FaultTreeBean> Faults = new LinkedList<>();
    TreeViewAdapter treeViewAdapter;
    AlertDialog dialog;
    AlertDialog Dicdialog;
    ListView faultsListView;
    public String type, ShortName, TrainNo, typeId, trainTypeIDX, faultFixFullName, zhuanye, kaohe, faultDesc, resolutionContent, idx, UUID, zhuanyeId, kaoheId, faultFixPlaceIDX, fixPlaceFullCode;
    public String state;
    long faultOccurDate = 0;
    String filter = "";
    public static int IMAGE_PICKER = 666;
    public static final String JPG_EXTNAME = ".jpg";
    int ImagesTimes = 0;
    int AllImagesTimes = 0;
    int Restimes = 0;
    List<FaultBean> nowFaults = new ArrayList<>();
    PopupMenu popupMenu;
    LinkedList<FaultTreeBean> childMenu = new LinkedList<>();
    TicketDictAdapter dictAdapter;
    List<DictBean> dictBeanList = new ArrayList<>();
    SelectDictAdapter selectDictAdapter;
    GridView gvTrains;
    int EquipPosition = 0;
    int TrainPosition = 0;
    @BindView(R.id.cbDispatch)
    CheckBox cbDispatch;
    @BindView(R.id.cbOrg)
    CheckBox cbOrg;
    String dictid;
    String dictname;
    String flbm;
    String coid;
    String wzqm;
    @Override
    protected ITicketInfoPresenter getPresenter() {
        return new ITicketInfoPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_ticket_info;
    }

    @Override
    public void initSubViews(View view) {

    }

    //初始化控件及数据
    private void setBaseViewData() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");
        ShortName = bundle.getString("ShortName");
        TrainNo = bundle.getString("TrainNo");
        typeId = bundle.getString("typeId");
        trainTypeIDX = bundle.getString("trainTypeIDX");
        state = bundle.getString("state");
        UUID = Utils.getUUID();
        filter = bundle.getString("filter");
        flbm = bundle.getString("flbm");
        coid = bundle.getString("coid");
        wzqm = bundle.getString("wzqm");
        if(!TextUtils.isEmpty(coid)){
            llBJName.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(flbm)) {
                getProgressDialog().show();
                Map<String,Object> map = new HashMap<>();
                map.put("flbm",flbm);
                mPresenter.getFaultList(JSON.toJSONString(map));
            }
        }

        if (!"0".equals(state)) {
            cbIsTodo.setVisibility(View.GONE);
            cbDispatch.setVisibility(View.GONE);
            cbOrg.setVisibility(View.GONE);
            tvBJName.setEnabled(false);
            tvKHName.setEnabled(false);
            tvZYName.setEnabled(false);
            etGZXXInfo.setEnabled(false);
            etSXFNInfo.setEnabled(false);
//            gvImages.setEnabled(false);
            faultFixFullName = bundle.getString("faultFixFullName");
            zhuanye = bundle.getString("zhuanye");
            kaohe = bundle.getString("kaohe");
            idx = bundle.getString("idx");
            faultDesc = bundle.getString("faultDesc");
            resolutionContent = bundle.getString("resolutionContent");
            if (faultFixFullName != null) {
                tvBJName.setText(faultFixFullName);
            }
            if (zhuanye != null) {
                if ("".equals(zhuanye)) {
                    tvZYName.setText("无");
                } else {
                    tvZYName.setText(zhuanye);
                }

            }
            if (kaohe != null) {
                if ("".equals(kaohe)) {
                    tvKHName.setText("无");
                } else {
                    tvKHName.setText(kaohe);
                }
            }
            if (faultDesc != null) {
                etGZXXInfo.setText(faultDesc);
            }
            if (resolutionContent != null) {
                etSXFNInfo.setText(resolutionContent);
            }
            if (!"2".equals(state)) {
                mPresenter.getImages(idx, "nodeTpAtt");
                btNext.setVisibility(View.GONE);
            } else {
                UUID = bundle.getString("UUId");
                zhuanyeId = bundle.getString("zhuanyeId");
                kaoheId = bundle.getString("kaoheId");
                faultFixPlaceIDX = bundle.getString("faultFixPlaceIDX");
                fixPlaceFullCode = bundle.getString("fixPlaceFullCode");
                faultOccurDate = bundle.getLong("faultOccurDate");
                FaultTreeBean bean = new FaultTreeBean();
                bean.setGxwzbm(fixPlaceFullCode);
                bean.setFlbm(faultFixPlaceIDX);
                tvBJName.setTag(bean);
                tvKHName.setTag(kaoheId);
                tvZYName.setTag(zhuanyeId);
                Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        List<ImageDao> list = Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance()
                                .where(ImageDao.class).equalTo("planIdx", UUID).equalTo("isUpLoad", false).findAll());
                        if (list != null && list.size() > 0) {
                            imagesTemp.clear();
                            images.clear();
                            for (ImageDao dao : list) {
                                ImageItem item = new ImageItem();
                                item.path = dao.getFilePath();
                                imagesTemp.add(item);
                                images.add(dao.getFilePath());
                            }
                            subscriber.onNext(1);
                        }
                    }
                }).subscribeOn(Schedulers.from(JXApplication.getExecutor())).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Integer integer) {
                                if (integer == 1) {
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
            }

        } else {
            cbIsTodo.setVisibility(View.VISIBLE);
            cbDispatch.setVisibility(View.VISIBLE);
            cbOrg.setVisibility(View.VISIBLE);
            if(!TextUtils.isEmpty(filter)){
                if("1".equals(filter)){
                    cbDispatch.setVisibility(View.GONE);
                    cbOrg.setVisibility(View.GONE);
                }else if("2".equals(filter)){
                    cbOrg.setVisibility(View.GONE);
                }
            }
            images.add("0");
        }
        tvTicketName.setText(ShortName + " " + TrainNo);
        tvBJName.setOnClickListener(this);
        tvZYName.setOnClickListener(this);
        tvKHName.setOnClickListener(this);
        btNext.setOnClickListener(this);
        popupMenu = new PopupMenu(this, etGZXXInfo);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                etGZXXInfo.setText(item.getTitle());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(etGZXXInfo.getWindowToken(), 0);
                }
                popupMenu.dismiss();
                return false;
            }
        });
        adapter = new ImagesAdapter(this, images);
        adapter.setState(state);
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagesTemp != null && imagesTemp.size() > 0 && state.equals("0")) {
                    new AlertDialog.Builder(TicketInfoActivity.this).setTitle("提示！").setMessage("当前有照片还未上传，是否确定退出")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();
                } else {
                    finish();
                }
            }
        });
        menuTp.setTitle(ShortName+" "+TrainNo+"("+type+")");
        gvImages.setAdapter(adapter);
        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (state.equals("0")) {
                    if (position == images.size() - 1) {

//                    Intent intent = new Intent(TicketInfoActivity.this, ImageGridActivity.class);
//                    if(imagesTemp!=null&&imagesTemp.size()>0){
//                        intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imagesTemp);
//                        intent.putExtra(ImagePreviewActivity.ISORIGIN, false);
//                        intent.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
//                    }
//                    startActivityForResult(intent, IMAGE_PICKER);
                        Intent intent = new Intent(TicketInfoActivity.this, ImageGridActivity.class);
                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                        startActivityForResult(intent, IMAGE_PICKER);
                    } else {
                        Bundle bundle1 = new Bundle();
                        Intent intent = new Intent(TicketInfoActivity.this, PhotoReadActivity.class);
                        bundle1.putSerializable("images", imagesTemp);
                        bundle1.putInt("position", position);
                        bundle1.putString("state", state);
                        intent.putExtras(bundle1);
                        startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
                    }
                } else {
                    Bundle bundle1 = new Bundle();
                    Intent intent = new Intent(TicketInfoActivity.this, PhotoReadActivity.class);
                    bundle1.putInt("position", position);
                    bundle1.putString("state", state);
                    bundle1.putSerializable("images", imagesTemp);
                    intent.putExtras(bundle1);
                    startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);

                }

            }
        });
    }

    @Override
    public void initData() {
        setBaseViewData();
        setEditText();
        initDialog();
        getProgressDialog().show();
        Map<String, Object> map = new HashMap<>();
        map.put("dicttypeid", "JXGC_Fault_Ticket_YYFX");
        /*获取父节点列表*/
        mPresenter.getDict("ROOT_0", JSON.toJSONString(map), -1);
        /*动态的操作选项-列表 :【专业、类型...】*/
        dictAdapter = new TicketDictAdapter(this, dictBeanList);
        lvEos.setAdapter(dictAdapter);
        /*动态操作项 - listview item点击*/
        dictAdapter.setOnDicClickListner(new TicketDictAdapter.OnDicClickListner() {
            @Override
            public void OnDicClick(int position) {
                EquipPosition = position;
                if (dictBeanList.size() > 0) {
                    if (dictBeanList.get(position).isGetChildSuccess()) {
                        if (dictBeanList.get(position).getChildList() != null && dictBeanList.get(position).getChildList().size() > 0) {
                            View view = LayoutInflater.from(TicketInfoActivity.this).inflate(R.layout.dialog_schedule_book, null);
                            gvTrains = (GridView) view.findViewById(R.id.gvTrains);
                            selectDictAdapter = new SelectDictAdapter(TicketInfoActivity.this, dictBeanList.get(position).getChildList());
                            gvTrains.setAdapter(selectDictAdapter);
                            Dicdialog = new AlertDialog.Builder(TicketInfoActivity.this).setView(view)
                                    .setTitle("选择").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                            Dicdialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    Utils.setListViewHeightBasedOnChildren(lvEos);
                                    dictAdapter.notifyDataSetChanged();
                                }
                            });
//                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//                            @Override
//                            public void onShow(DialogInterface dialog) {
//                                isDialogFirstShow = true;
//                            }
//                        });
//                        selectDictAdapter.setOnSelectedChangedListner(new SelectDictAdapter.OnSelectedChangedListner() {
//                            @Override
//                            public void OnselectedChanged(int position, boolean isChecked) {
//                                if(isChecked){
//                                    if(dictBeanList.get(EquipPosition).getChildList().get(position).isChecked()){
//                                        return;
//                                    }
//                                    getProgressDialog().show();
//                                    TrainPosition = position;
//                                    Map<String,Object> map = new HashMap<>();
//                                    map.put("handeOrgName",orgList.get(EquipPosition).getHandeOrgName());
//                                    map.put("handeOrgID",orgList.get(EquipPosition).getHandeOrgId());
//                                    map.put("trainWorkPlanIdx",orgList.get(EquipPosition).getTrainList().get(position)
//                                            .getIdx());
//                                    mPresenter.ScduleBook(JSON.toJSONString(map));
//                                }else {
//                                    if(!orgList.get(EquipPosition).getTrainList().get(position).isBook()){
//                                        return;
//                                    }
//                                    mainact.getProgressDialog().show();
//                                    TrainPosition = position;
//                                    mPresenter.CancleScduleBook(orgList.get(EquipPosition).getTrainList().get(position).getIdx());
//                                }
//                            }
//                        });
                        } else {
                            ToastUtil.toastShort("当前标签无可选择项");
                        }

                    } else {
                        ToastUtil.toastShort("当前标签数据还在加载请稍等");
                    }

                }
            }
        });
        cbIsTodo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkState = 1;
                    cbDispatch.setChecked(false);
                    cbOrg.setChecked(false);

                } else {
                    checkState = 0;
                }
            }
        });
        cbDispatch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkState = 2;
                    cbIsTodo.setChecked(false);
                    cbOrg.setChecked(false);

                } else {
                    checkState = 0;
                }
            }
        });
        cbOrg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkState = 3;
                    cbIsTodo.setChecked(false);
                    cbDispatch.setChecked(false);

                } else {
                    checkState = 0;
                }
            }
        });
    }

    //设置输入框监听事件
    private void setEditText() {

        etGZXXInfo.setOnFocusChangeListener(new View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String text = "";
                    if (etGZXXInfo.getText() != null)
                        text = etGZXXInfo.getText().toString();
                    List<FaultBean> temps = new ArrayList<>();
                    if (nowFaults.size() > 0) {
                        if (text.equals("如果出现非常见现象，请输入") || text.equals("")) {
                            temps.addAll(nowFaults);
                        } else {
                            for (FaultBean bean : nowFaults) {
                                if (bean.getFaultName().contains(text)) {
                                    temps.add(bean);
                                }
                            }
                        }
                    }

                    if (temps.size() > 0) {
                        Menu menu_more = popupMenu.getMenu();
                        menu_more.clear();
                        int size = temps.size();
                        for (int i = 0; i < size; i++) {
                            menu_more.add(Menu.NONE, Menu.FIRST + i, i, temps.get(i).getFaultName());
                        }
                        popupMenu.show();
                    }

                } else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(etGZXXInfo.getWindowToken(), 0);
                    }
                    popupMenu.dismiss();
                }
            }
        });
        etGZXXInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<FaultBean> temps = new ArrayList<>();
                if (nowFaults.size() > 0) {
                    if (s.equals("如果出现非常见现象，请输入")) {
                        temps.addAll(nowFaults);
                    } else {
                        for (FaultBean bean : nowFaults) {
                            if (bean.getFaultName().contains(s)) {
                                temps.add(bean);
                            }
                        }
                    }
                }

                if (temps.size() > 0) {
                    Menu menu_more = popupMenu.getMenu();
                    menu_more.clear();
                    int size = temps.size();
                    for (int i = 0; i < size; i++) {
                        menu_more.add(Menu.NONE, Menu.FIRST + i, i, temps.get(i).getFaultName());
                    }
                    popupMenu.show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    //设置设备故障选择弹出框
    private void initDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.treeview_dialog, null);
        faultsListView = (ListView) view.findViewById(R.id.lvTree);
        treeViewAdapter = new TreeViewAdapter(this, Faults);
        treeViewAdapter.setOnImageClickLisnter(this);
        faultsListView.setAdapter(treeViewAdapter);
        dialog = new AlertDialog.Builder(this).setTitle("请选择部件位置").setView(view)
                .setPositiveButton("确定", null).create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Faults.clear();
                treeViewAdapter.notifyDataSetChanged();
            }
        });
    }

    //提交前判断是否全部数据全部填入
    private boolean isAllselecyesd() {
//        if (tvBJName.getTag() == null || "".equals(tvBJName.getTag().toString())) {
//            Toast.makeText(this, "不能提交，还未选择部件位置！", Toast.LENGTH_SHORT).show();
//            getProgressDialog().dismiss();
//            return false;
//        }
        for (DictBean dictBean : dictBeanList) {
            if ("1".equals(dictBean.getFilter1())&&dictBean.getChildList() != null && dictBean.getChildList().size() > 0) {
                int num = 0;
                for (DictBean child : dictBean.getChildList()) {
                    if (child.isChecked()) {
                        num++;
                    }
                }
                if (num == 0) {
                    ToastUtil.toastShort("标签：" + dictBean.getName() + "还未选择");
                    getProgressDialog().dismiss();
                    return false;
                }
            }
        }
        if (etGZXXInfo.getText() == null || "".equals(etGZXXInfo.getText().toString())) {
            Toast.makeText(this, "不能提交，还未填写故障现象内容！", Toast.LENGTH_SHORT).show();
            getProgressDialog().dismiss();
            return false;
        }
//        if (etSXFNInfo.getText() == null || "".equals(etSXFNInfo.getText().toString())) {
//            Toast.makeText(this, "不能提交，还未选填写施修方案内容！", Toast.LENGTH_SHORT).show();
//            getProgressDialog().dismiss();
//            return false;
//        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0) {
                    imagesTemp.addAll(images);
//                    ImagePicker.getInstance().getSelectedImages().addAll(images);
//                    ImagePicker.getInstance().getSelectImageCount();
                    this.images.remove(this.images.size() - 1);
                    for (int i = 0; i < images.size(); i++) {
                        this.images.add(images.get(i).path);
                    }
                    if (state.equals("0")) {
                        this.images.add("0");
                    }
                    adapter.notifyDataSetChanged();
                }
            } else if (requestCode == ImagePicker.REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra("images");
                imagesTemp.clear();
                if (images != null && images.size() > 0) {
                    imagesTemp.addAll(images);
                    this.images.clear();
                    for (int i = 0; i < images.size(); i++) {
                        this.images.add(images.get(i).path);
                    }
                    if (state.equals("0")) {
                        this.images.add("0");
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    this.images.clear();
                    if (state.equals("0")) {
                        this.images.add("0");
                    }
                    adapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void OnLoadListSuccess(List<FaultBean> Faults) {
        getProgressDialog().dismiss();
        nowFaults.clear();
        if (Faults.size() > 0) {
            nowFaults.addAll(Faults);
        }
    }

    @Override
    public void OnLoadEquipSuccess(int position, List<FaultTreeBean> faultTreeBeanList) {
        if (Faults.size() == 0) {
            if (faultTreeBeanList != null) {
                for (int i = 0; i < faultTreeBeanList.size(); i++) {
                    faultTreeBeanList.get(i).setMyLevel(0);
                }
            }
            Faults.addAll(faultTreeBeanList);
            dialog.show();
            treeViewAdapter.notifyDataSetChanged();
        } else {
            dialog.show();
            if (faultTreeBeanList != null && faultTreeBeanList.size() > 0) {
                Faults.get(position).setOpen(true);
                int level = Faults.get(position).getMyLevel();
                Faults.get(position).setOpen(true);
                for (int i = 0; i < faultTreeBeanList.size(); i++) {
                    faultTreeBeanList.get(i).setMyLevel(level + 1);
                }
            }
            Faults.addAll(position + 1, faultTreeBeanList);
            treeViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnLoadMajorSuccess(final List<TicketTypeBean> list) {
        OptionsPickerView options = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvZYName.setText(list.get(options1).getDictname());
                tvZYName.setTag(list.get(options1).getDictid());
            }
        }).setTitleText("选择专业").setSubmitText("确定").build();
        List<String> list1 = new ArrayList<>();
        for (TicketTypeBean bean : list) {
            list1.add(bean.getDictname());
        }
        options.setPicker(list1);
        options.show();
    }

    @Override
    public void OnLoadEmployeeSuccess(final List<TicketTypeBean> list) {
        OptionsPickerView options = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvKHName.setText(list.get(options1).getDictname());
                tvKHName.setTag(list.get(options1).getDictid());
            }
        }).setTitleText("选择考核").setSubmitText("确定").build();
        List<String> list1 = new ArrayList<>();
        for (TicketTypeBean bean : list) {
            list1.add(bean.getDictname());
        }
        options.setPicker(list1);
        options.show();
    }

    List<ImageDao> unLoadImages;

    @Override
    public void OnUpLoadImagesSuccess(final String path, final int position, final int imageNo) {
        Restimes++;
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
//                更新本地的图片
                final ImageDao dao = Realm.getDefaultInstance().where(ImageDao.class).equalTo("idx", position)
                        .equalTo("planIdx", UUID)
                        .findFirst();
                if (dao != null) {
                    Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            dao.setUpLoad(true);
                            dao.setToup(true);
                            dao.setFileBackPath(path);
                            subscriber.onNext(0);
                        }
                    });
                }
            }
        }).subscribeOn(Schedulers.from(JXApplication.getExecutor())).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (imageNo < AllImagesTimes - 1) {
                            if (unLoadImages != null && unLoadImages.size() > 0) {
                                String base64 = Utils.getBase64StringFromImg(unLoadImages.get(ImagesTimes + 1).getFilePath());
                                mPresenter.UpLoadImages(base64, JPG_EXTNAME, unLoadImages.get(ImagesTimes + 1).getIdx(), ImagesTimes + 1);
                                ImagesTimes++;
                            } else {
                                String base64 = Utils.getBase64StringFromImg(imagesTemp.get(ImagesTimes + 1).path);
                                mPresenter.UpLoadImages(base64, JPG_EXTNAME, ImagesTimes + 1, ImagesTimes + 1);
                                ImagesTimes++;
                            }
                        } else {
                            if (Restimes == AllImagesTimes) {
                                Observable.create(new Observable.OnSubscribe<Integer>() {
                                    @Override
                                    public void call(final Subscriber<? super Integer> subscriber) {
                                        Log.e("Realm", "realm add start");
                                        Log.e("当前线程", Thread.currentThread().getName() + "--" + Thread.currentThread().getId() + "");
                                        unLoadImages = Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance().where(ImageDao.class).equalTo("planIdx", UUID)
                                                .equalTo("isUpLoad", false).findAll());
                                        if (unLoadImages == null || unLoadImages.size() == 0) {
                                            subscriber.onNext(0);
                                        } else {
                                            subscriber.onNext(unLoadImages.size());

                                        }
                                    }
                                }).subscribeOn(Schedulers.from(JXApplication.getExecutor()))
                                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(Integer integer) {
                                        if (integer == 0) {
                                            Observable
                                                    .create(new Observable.OnSubscribe<String>() {
                                                        @Override
                                                        public void call(Subscriber<? super String> subscriber) {
                                                            Log.e("Realm", "realm add start");
                                                            Log.e("当前线程", Thread.currentThread().getName() + "--" + Thread.currentThread().getId() + "");
                                                            ArrayList<Map<String, String>> list = new ArrayList<>();
                                                            List<ImageDao> daos = Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance().where(ImageDao.class)
                                                                    .equalTo("planIdx", UUID)
                                                                    .findAll());
                                                            if (daos != null && daos.size() > 0) {
                                                                for (ImageDao dao : daos) {
                                                                    Map<String, String> map = new HashMap<>();
                                                                    map.put("filename", dao.getFilename());
                                                                    map.put("filePath", dao.getFileBackPath());
                                                                    list.add(map);
                                                                }
                                                            }
                                                            String jsonstr2 = JSON.toJSONString(list);
                                                            subscriber.onNext(jsonstr2);
                                                        }
                                                    })
                                                    .subscribeOn(Schedulers.from(JXApplication.getExecutor()))
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new Observer<String>() {
                                                        @Override
                                                        public void onCompleted() {

                                                        }

                                                        @Override
                                                        public void onError(Throwable e) {

                                                        }

                                                        @Override
                                                        public void onNext(String s) {
                                                            Log.e("Realm", "realm add finish");
                                                            submitTicket(s);
                                                        }
                                                    });
                                        } else {
                                            getProgressDialog().dismiss();
                                            Observable.create(new Observable.OnSubscribe<Integer>() {
                                                @Override
                                                public void call(Subscriber<? super Integer> subscriber) {
                                                    unLoadImages = Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance().where(ImageDao.class)
                                                            .equalTo("planIdx", UUID).equalTo("isUpLoad", false).findAll());
                                                    if (unLoadImages != null && unLoadImages.size() > 0) {
                                                        subscriber.onNext(unLoadImages.size());
                                                    } else {
                                                        subscriber.onNext(0);
                                                    }
                                                }
                                            }).subscribeOn(Schedulers.from(JXApplication.getExecutor()))
                                                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
                                                @Override
                                                public void onCompleted() {

                                                }

                                                @Override
                                                public void onError(Throwable e) {

                                                }

                                                @Override
                                                public void onNext(Integer integer) {
                                                    if (integer > 0) {
                                                        if (NetWorkUtils.isWifiConnected(TicketInfoActivity.this)) {
                                                            new AlertDialog.Builder(TicketInfoActivity.this).setTitle("提示！")
                                                                    .setMessage("当前有" + integer + "张图片未上传成功，请重试")
                                                                    .setPositiveButton("重试", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            getProgressDialog().show();
                                                                            Observable.create(new Observable.OnSubscribe<Integer>() {
                                                                                @Override
                                                                                public void call(Subscriber<? super Integer> subscriber) {
                                                                                    AllImagesTimes = unLoadImages.size();
                                                                                    ImagesTimes = 0;
                                                                                    Restimes = 0;
                                                                                    String base64 = Utils.getBase64StringFromImg(unLoadImages.get(0).getFilePath());
                                                                                    mPresenter.UpLoadImages(base64, JPG_EXTNAME, unLoadImages.get(0).getIdx(), 0);
                                                                                }
                                                                            }).subscribeOn(Schedulers.from(JXApplication.getExecutor()))
                                                                                    .subscribe();
                                                                        }
                                                                    })
//                                                        .setNegativeButton("保存本地", new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
//                                                            @Override
//                                                            public void call(final Subscriber<? super Integer> subscriber) {
//                                                                SavaSubmitDao(subscriber);
//                                                            }
//                                                        }).subscribeOn(Schedulers.from(JXApplication.getExecutor()))
//                                                                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
//                                                            @Override
//                                                            public void onCompleted() {
//
//                                                            }
//
//                                                            @Override
//                                                            public void onError(Throwable e) {
//
//                                                            }
//
//                                                            @Override
//                                                            public void onNext(Integer integer) {
//                                                                if (integer==1){
//                                                                    new AlertDialog.Builder(TicketInfoActivity.this).setTitle("成功保存到提票单")
//                                                                            .setMessage("因网络故障，已保存到提票单，您可以在检修过程界面进行查看！")
//                                                                            .setPositiveButton("去查看", new DialogInterface.OnClickListener() {
//                                                                                @Override
//                                                                                public void onClick(DialogInterface dialog, int which) {
//
//                                                                                }
//                                                                            }).setNegativeButton("关闭", new DialogInterface.OnClickListener() {
//                                                                        @Override
//                                                                        public void onClick(DialogInterface dialog, int which) {
//
//                                                                        }
//                                                                    }).show();
//                                                                }
//                                                            }
//                                                        });
//                                                    }
//                                                })
                                                                    .show();
                                                        } else {
                                                            ToastUtil.toastShort("当前网络状态差，有" + integer + "条照片未上传，请重试");
                                                        }
                                                    }
                                                }
                                            });

                                        }
                                    }
                                });

                            }
                        }
                    }
                });

    }

    @Override
    public void OnGetImagesSuccess(List<FileBaseBean> images) {
        if (images.size() > 0) {
            this.images.clear();
            for (FileBaseBean str : images) {
                String strs = Utils.getImageUrl(str.getFileUrl());
                this.images.add(strs);
            }
            adapter.notifyDataSetChanged();
            imagesTemp.clear();
            if (images.size() > 0) {
                for (int i = 0; i < images.size(); i++) {
                    ImageItem item = new ImageItem();
                    item.path = Utils.getImageUrl(images.get(i).getFileUrl());
                    imagesTemp.add(item);
                }
            }
        }
    }

    @Override
    public void OnGetImagesXpSuccess(List<FileBaseBean> images) {

    }

    @Override
    public void OnSubmitSuccess() {
        getProgressDialog().dismiss();
        Toast toast = new Toast(this);
        ImageView image = new ImageView(this);
        image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        image.setImageResource(R.mipmap.submit_success);
        toast.setView(image);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
        if ("2".equals(state)) {
            Observable.create(new Observable.OnSubscribe<Integer>() {
                @Override
                public void call(final Subscriber<? super Integer> subscriber) {
                    Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.where(TicketSubmitDao.class).equalTo("idx", UUID).findAll()
                                    .deleteFirstFromRealm();
                            subscriber.onNext(0);
                        }
                    });
                }
            }).subscribeOn(Schedulers.from(JXApplication.getExecutor())).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Integer>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Integer integer) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ActivityUtil.startActivity(TicketInfoActivity.this, TP_ProcessActivity.class);
                                }
                            }, 2000);
                        }
                    });
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 2000);
        }

    }

    @Override
    public void OnLoadFail(String msg) {
        getProgressDialog().dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnSubmitFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void OnLoadFail(String msg, final int position, final int imageNo) {
        if (position == -1 && imageNo == -1) {
            getProgressDialog().dismiss();
            ToastUtil.toastShort(msg);
//            rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
//                @Override
//                public void call(final Subscriber<? super Integer> subscriber) {
//                    SavaSubmitDao(subscriber);
//                }
//            }).subscribeOn(Schedulers.from(JXApplication.getExecutor()))
//                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
//                @Override
//                public void onCompleted() {
//
//                }
//
//                @Override
//                public void onError(Throwable e) {
//
//                }
//
//                @Override
//                public void onNext(Integer integer) {
//                    if (integer==1){
//                        new AlertDialog.Builder(TicketInfoActivity.this).setTitle("成功保存到提票单")
//                                .setMessage("因网络故障，已保存到提票单，您可以在检修过程界面进行查看！")
//                                .setPositiveButton("去查看", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Bundle bundle = new Bundle();
//                                        bundle.putInt("state",2);
//                                        ActivityUtil.startActivityWithDelayed(TicketInfoActivity.this,TicketListActivity.class,bundle);
//                                    }
//                                }).setNegativeButton("关闭", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        setResult(300);
//                                        finish();
//                                    }
//                                }, 2000);
//                            }
//                        }).show();
//                    }
//                }
//            });
        } else {
            Restimes++;
            if (imageNo < AllImagesTimes - 1) {
                if (unLoadImages != null && unLoadImages.size() > 0) {
                    String base64 = Utils.getBase64StringFromImg(unLoadImages.get(ImagesTimes + 1).getFilePath());
                    mPresenter.UpLoadImages(base64, JPG_EXTNAME, unLoadImages.get(ImagesTimes + 1).getIdx(), ImagesTimes + 1);
                    ImagesTimes++;
                } else {
                    String base64 = Utils.getBase64StringFromImg(imagesTemp.get(ImagesTimes + 1).path);
                    mPresenter.UpLoadImages(base64, JPG_EXTNAME, ImagesTimes + 1, ImagesTimes + 1);
                    ImagesTimes++;
                }
            } else {
                if (Restimes == AllImagesTimes) {
                    Observable.create(new Observable.OnSubscribe<Integer>() {
                        @Override
                        public void call(Subscriber<? super Integer> subscriber) {
                            Log.e("Realm", "realm add start");
                            Log.e("当前线程", Thread.currentThread().getName() + "--" + Thread.currentThread().getId() + "");
                            unLoadImages = Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance().where(ImageDao.class)
                                    .equalTo("planIdx", UUID).equalTo("isUpLoad", false).findAll());
                            if (unLoadImages == null || unLoadImages.size() == 0) {
                                subscriber.onNext(0);
                            } else {
//                                for (int i = 0; i < list.size(); i++) {
//                                    ImageDao dao = list.get(i);
//                                    dao.setToup(false);
//                                }
                                subscriber.onNext(unLoadImages.size());
                            }
                        }
                    }).subscribeOn(Schedulers.from(JXApplication.getExecutor()))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Integer>() {
                                @Override
                                public void call(final Integer aVoid) {
                                    if (aVoid == null || aVoid == 0) {
                                        getProgressDialog().dismiss();
                                    } else {
                                        getProgressDialog().dismiss();
                                        if (NetWorkUtils.isWifiConnected(TicketInfoActivity.this)) {
                                            new AlertDialog.Builder(TicketInfoActivity.this).setTitle("提示！")
                                                    .setMessage("当前有" + aVoid + "张图片未上传成功，是否重试或保存本地？")
                                                    .setPositiveButton("重试", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            getProgressDialog().show();
                                                            Observable.create(new Observable.OnSubscribe<Object>() {
                                                                @Override
                                                                public void call(Subscriber<? super Object> subscriber) {
                                                                    AllImagesTimes = unLoadImages.size();
                                                                    ImagesTimes = 0;
                                                                    Restimes = 0;
                                                                    String base64 = Utils.getBase64StringFromImg(unLoadImages.get(0).getFilePath());
                                                                    mPresenter.UpLoadImages(base64, JPG_EXTNAME, unLoadImages.get(0).getIdx(), 0);
                                                                }
                                                            }).subscribeOn(Schedulers.from(JXApplication.getExecutor())).subscribe();

                                                        }
                                                    })
//                                                    .setNegativeButton("保存本地", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
//                                                        @Override
//                                                        public void call(final Subscriber<? super Integer> subscriber) {
//                                                            SavaSubmitDao(subscriber);
//                                                        }
//                                                    }).subscribeOn(Schedulers.from(JXApplication.getExecutor()))
//                                                            .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
//                                                        @Override
//                                                        public void onCompleted() {
//
//                                                        }
//
//                                                        @Override
//                                                        public void onError(Throwable e) {
//
//                                                        }
//
//                                                        @Override
//                                                        public void onNext(Integer integer) {
//                                                            if (integer==1){
//                                                                getProgressDialog().dismiss();
//                                                                new AlertDialog.Builder(TicketInfoActivity.this).setTitle("成功保存到提票单")
//                                                                        .setMessage("因网络故障，已保存到提票单，您可以在检修过程界面进行查看！")
//                                                                        .setPositiveButton("去查看", new DialogInterface.OnClickListener() {
//                                                                            @Override
//                                                                            public void onClick(DialogInterface dialog, int which) {
//                                                                                Bundle bundle = new Bundle();
//                                                                                bundle.putInt("state",2);
//                                                                                ActivityUtil.startActivityWithDelayed(TicketInfoActivity.this,TicketListActivity.class,bundle);
//                                                                            }
//                                                                        }).setNegativeButton("关闭", new DialogInterface.OnClickListener() {
//                                                                    @Override
//                                                                    public void onClick(DialogInterface dialog, int which) {
//                                                                        new Handler().postDelayed(new Runnable() {
//                                                                            @Override
//                                                                            public void run() {
//                                                                                setResult(300);
//                                                                                finish();
//                                                                            }
//                                                                        }, 2000);
//                                                                    }
//                                                                }).show();
//                                                            }
//                                                        }
//                                                    });
//                                                }
//                                            })
                                                    .show();
                                        } else {
                                            getProgressDialog().dismiss();
                                            ToastUtil.toastShort("因网络问题，当前有" + aVoid + "张图片未上传，请检查网络重新上传");
//                                            rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
//                                                @Override
//                                                public void call(final Subscriber<? super Integer> subscriber) {
//                                                    SavaSubmitDao(subscriber);
//                                                }
//                                            }).subscribeOn(Schedulers.from(JXApplication.getExecutor()))
//                                                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
//                                                @Override
//                                                public void onCompleted() {
//
//                                                }
//
//                                                @Override
//                                                public void onError(Throwable e) {
//
//                                                }
//
//                                                @Override
//                                                public void onNext(Integer integer) {
//                                                    if (integer==1){
//                                                        getProgressDialog().dismiss();
//                                                        new AlertDialog.Builder(TicketInfoActivity.this).setTitle("成功保存到提票单")
//                                                                .setMessage("因网络故障，已保存到提票单，您可以在检修过程界面进行查看！")
//                                                                .setPositiveButton("去查看", new DialogInterface.OnClickListener() {
//                                                                    @Override
//                                                                    public void onClick(DialogInterface dialog, int which) {
//                                                                        Bundle bundle = new Bundle();
//                                                                        bundle.putInt("state",2);
//                                                                        bundle.putString("type",type);
//                                                                        ActivityUtil.startActivityWithDelayed(TicketInfoActivity.this,TicketListActivity.class,bundle);
//                                                                    }
//                                                                }).setNegativeButton("关闭", new DialogInterface.OnClickListener() {
//                                                            @Override
//                                                            public void onClick(DialogInterface dialog, int which) {
//                                                                new Handler().postDelayed(new Runnable() {
//                                                                    @Override
//                                                                    public void run() {
//                                                                        setResult(300);
//                                                                        finish();
//                                                                    }
//                                                                }, 500);
//                                                            }
//                                                        }).show();
//                                                    }
//                                                }
//                                            });
                                        }
                                    }
                                }
                            });

                }
            }
        }

    }

    @Override
    public void OnDicLoadSuccess(List<DictBean> list, int position) {
        getProgressDialog().dismiss();
        if (list != null && list.size() > 0) {
            if (position == -1) {//获取父亲列表 ：position==-1
                dictBeanList.clear();
                dictBeanList.addAll(list);
                for (int i = 0; i < dictBeanList.size(); i++) {
                    getProgressDialog().show();
                    Map<String, Object> map = new HashMap<>();
                    map.put("dicttypeid", "JXGC_Fault_Ticket_YYFX");
                    mPresenter.getDict(dictBeanList.get(i).getId(), JSON.toJSONString(map), i);
                }
            } else {//父节点下标 position>=0
                dictBeanList.get(position).setGetChildSuccess(true);
                if (dictBeanList.get(position).getChildList() == null) {
                    List<DictBean> list1 = new ArrayList<>();
                    list1.addAll(list);
                    dictBeanList.get(position).setChildList(list1);
                } else {
                    dictBeanList.get(position).getChildList().clear();
                    dictBeanList.get(position).getChildList().addAll(list);
                }
            }

        } else {
            if (position != -1) {
                dictBeanList.get(position).setGetChildSuccess(true);
            }
        }
        Utils.setListViewHeightBasedOnChildren(lvEos);
        dictAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnDicLoadFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void OnBackSuccess() {

    }

    @Override
    public void OnLoadStandardSuccess(List<StandardBean> list) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    List<String> list = new ArrayList<>();

    private void SavaSubmitDao(final Subscriber<? super Integer> subscriber) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TicketSubmitDao dao = realm.createObject(TicketSubmitDao.class);
                dao.setIdx(UUID);
                dao.setOperatorid(SysInfo.userInfo.emp.getEmpid());
                dao.setTrainTypeIDX(trainTypeIDX);
                dao.setTrainNo(TrainNo);
                dao.setTrainTypeShortName(ShortName);
                dao.setFaultDesc(etGZXXInfo.getText().toString());
                dao.setFaultFixPlaceIDX(((FaultTreeBean) tvBJName.getTag()).getFlbm());
                dao.setFixPlaceFullCode(((FaultTreeBean) tvBJName.getTag()).getGxwzbm());
                dao.setFixPlaceFullName(tvBJName.getText().toString());
                dao.setFaultOccurDate(System.currentTimeMillis());
                dao.setReasonAnalysisZhuanYe(tvZYName.getText().toString());
                dao.setReasonAnalysisZhuanYeID(tvZYName.getTag().toString());
                dao.setReasonAnalysisKaoHe(tvKHName.getText().toString());
                dao.setReasonAnalysisKaoHeId(tvKHName.getTag().toString());
                if (etSXFNInfo.getText() != null && !etSXFNInfo.getText().toString().equals(""))
                    dao.setResolutionContent(etSXFNInfo.getText().toString());
                dao.setType(type);
                subscriber.onNext(1);
            }
        });
    }

    private void UploadImages() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvBJName:
                Map map = new HashMap();
                map.put("fjdID", "0");
                map.put("sycx", ShortName);
                map.put("useType", "");
                mPresenter.getFalutInfo(0, JSON.toJSONString(map));
                break;
            case R.id.tvZYName:
                mPresenter.getLable(ITicketInfoPresenter.TYPE_MAJOR);
                break;
            case R.id.tvKHName:
                mPresenter.getLable(ITicketInfoPresenter.TYPE_EMPLOYEE);
                break;
            case R.id.btNext:
                if (NetWorkUtils.isWifiConnected(this)) {
                    getProgressDialog().show();
                    submitTicketToBack();
                } else {
                    ToastUtil.toastShort("当前网络状态差，不能完成提交请检查网络！");
                }
                break;
        }
    }

    //判断是否为子节点
    public LinkedList<FaultTreeBean> isChild(LinkedList<FaultTreeBean> temps, String parentId) {
        for (FaultTreeBean mu : temps) {
            //遍历出父id等于参数的id，add进子节点集合
            if (parentId.equals(mu.getFjdID())) {
                //递归遍历下一级
                isChild(temps, mu.getId());
                childMenu.add(mu);
            }
        }
        return childMenu;
    }

    /**
     * 提交 提票信息(图片上传...)
     */
    public void submitTicketToBack() {
        if (isAllselecyesd()) {
            if (imagesTemp != null && imagesTemp.size() > 0) {
                if (unLoadImages != null && unLoadImages.size() > 0) {
                    Observable.create(new Observable.OnSubscribe<Integer>() {
                        @Override
                        public void call(Subscriber<? super Integer> subscriber) {
                            Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    RealmResults<ImageDao> images = realm.where(ImageDao.class)
                                            .equalTo("isUpLoad", false)
                                            .equalTo("planIdx", UUID).findAll();
//                                    if(images!=null&&images.size()>0){
//                                        for(int i = 0;i<images.size();i++){
//                                            images.get(i).setIdx(i);
//                                        }
//                                    }
                                    unLoadImages = realm.copyFromRealm(Realm.getDefaultInstance().where(ImageDao.class)
                                            .equalTo("planIdx", UUID).equalTo("isUpLoad", false).findAll());
                                    AllImagesTimes = unLoadImages.size();
                                    if (AllImagesTimes > 0) {
                                        ImagesTimes = 0;
                                        Restimes = 0;
                                        String base64 = Utils.getBase64StringFromImg(unLoadImages.get(0).getFilePath());
                                        mPresenter.UpLoadImages(base64, JPG_EXTNAME, unLoadImages.get(0).getIdx(), 0);
                                    }

                                }
                            });

                        }
                    }).subscribeOn(Schedulers.from(JXApplication.getExecutor()))
                            .subscribe();
                } else {
                    Observable.create(new Observable.OnSubscribe<Integer>() {
                        @Override
                        public void call(final Subscriber<? super Integer> subscriber) {
                            Log.e("Realm", "realm add start");
                            Log.e("当前线程", Thread.currentThread().getName() + "--" + Thread.currentThread().getId() + "");
                            if (Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance().where(ImageDao.class)
                                    .equalTo("planIdx", UUID).findAll()) == null || Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance().where(ImageDao.class)
                                    .equalTo("planIdx", UUID).findAll()).size() == 0) {
                                Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
//                                        realm.where(ImageDao.class).findAll().deleteAllFromRealm();
                                        for (int i = 0; i < imagesTemp.size(); i++) {
                                            ImageDao image = realm.createObject(ImageDao.class);
                                            image.setFilePath(imagesTemp.get(i).path);
                                            image.setUpLoad(false);
                                            image.setFilename(imagesTemp.get(i).name);
                                            image.setIdx(i);
                                            image.setPlanIdx(UUID);
                                            image.setToup(false);
                                        }
                                        subscriber.onNext(0);
                                    }
                                });
                            } else {
                                Observable.create(new Observable.OnSubscribe<Object>() {
                                    @Override
                                    public void call(Subscriber<? super Object> subscriber) {

                                    }
                                }).subscribeOn(Schedulers.from(JXApplication.getExecutor())).subscribe();
                                Log.e("当前线程1:", Thread.currentThread().getName() + Thread.currentThread().getId());
                                ArrayList<Map<String, String>> list = new ArrayList<>();
                                List<ImageDao> daos = Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance().where(ImageDao.class)
                                        .equalTo("planIdx", UUID)
                                        .findAll());
                                if (daos != null && daos.size() > 0) {
                                    for (ImageDao dao : daos) {
                                        Map<String, String> map = new HashMap<>();
                                        map.put("filename", dao.getFilename());
                                        map.put("filePath", dao.getFileBackPath());
                                        list.add(map);
                                    }
                                }
                                String jsonstr2 = JSON.toJSONString(list);
                                submitTicket(jsonstr2);
                            }
                        }
                    }).subscribeOn(Schedulers.from(JXApplication.getExecutor()))
                            .observeOn(Schedulers.from(JXApplication.getExecutor())).subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Integer integer) {
                            if (integer == 0) {
                                Log.e("Realm", "realm add finish");
                                if (imagesTemp.size() > 0) {
                                    ImagesTimes = 0;
                                    AllImagesTimes = imagesTemp.size();
                                    Restimes = 0;
                                    String base64 = Utils.getBase64StringFromImg(imagesTemp.get(0).path);
                                    mPresenter.UpLoadImages(base64, JPG_EXTNAME, 0, 0);
                                }
                            }
                        }
                    });
                }

            } else {
                submitTicket("");
            }
        }
    }

    int checkState = 0;

    private void submitTicket(String imgs) {
        ArrayList<Map<String, Object>> list2 = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("trainTypeIDX", trainTypeIDX);
        map1.put("trainNo", TrainNo);
        map1.put("trainTypeShortName", ShortName);
        map1.put("faultID", "");
        map1.put("faultName", "");
        map1.put("faultDesc", etGZXXInfo.getText().toString());
        if (tvBJName.getTag() != null) {
            map1.put("faultFixPlaceIDX", ((FaultTreeBean) tvBJName.getTag()).getFlbm());
            map1.put("fixPlaceFullCode", ((FaultTreeBean) tvBJName.getTag()).getGxwzbm());
            map1.put("fixPlaceFullName", tvBJName.getText().toString());
        }
        if (checkState == 1) {
            map1.put("isApproval", 1);
            map1.put("ticketType", "1");
        } else if (checkState == 2) {
            map1.put("isApproval", 0);
            map1.put("ticketType", "1");
        } else if (checkState == 3) {
            map1.put("isApproval", 0);
            map1.put("ticketType", "2");
        } else {
            map1.put("isApproval", 0);
            map1.put("ticketType", filter);
        }
//        if (cbIsTodo.isChecked()) {
//            map1.put("isApproval", 1);
//        } else {
//            map1.put("isApproval", 0);
//        }
        if (faultOccurDate == 0) {
            map1.put("faultOccurDate", System.currentTimeMillis());
        } else {
            map1.put("faultOccurDate", faultOccurDate);
        }

        map1.put("professionalTypeIdx", "");
        map1.put("professionalTypeName", "");
        map1.put("professionalTypeSeq", "");
        /*处理选中的子节点*/
        mPresenter.assembleSelectedDict(dictBeanList,map1);
        if (etSXFNInfo.getText() != null && !"".equals(etSXFNInfo.getText().toString()))
            map1.put("resolutionContent", etSXFNInfo.getText().toString());
        map1.put("type", type);
//        if(checkState==1){
//            map1.put("isApproval", 1);
//        }else if(checkState==2){
//            map1.put("ticketType","1");
//        }else if(checkState==3){
//            map1.put("ticketType","2");
//        }else {
//            map1.put("ticketType",filter);
//        }
        Logger.json(JSON.toJSONString(map1));
        list2.add(map1);
        String jsonstr1 = JSON.toJSONString(list2);
        mPresenter.SaveTicket(SysInfo.userInfo.emp.getOperatorid(), jsonstr1, imgs);
    }

    @Override
    public void OnImageClick(int position, String Pid) {
        if (Faults.get(position).isOpen()) {
            Faults.get(position).setOpen(false);
            String parentId = Faults.get(position).getId();
            LinkedList<FaultTreeBean> temps = new LinkedList<>();
            temps.addAll(Faults);
            Faults.clear();
            LinkedList ChildList = new LinkedList();
            ChildList = isChild(temps, parentId);
            if (ChildList.size() > 0)
                temps.removeAll(ChildList);
            Faults.addAll(temps);

//            LinkedList<FaultTreeBean> temps = new LinkedList<>();
//            temps.addAll(Faults);
//            Faults.clear();
//            setChildIds(parentId);
//            for (int i = 0; i < temps.size(); i++) {
//                if (!temps.get(i).getFjdID().equals(parentId)) {
//                    Faults.add(temps.get(i));
//                }
//            }
            treeViewAdapter.notifyDataSetChanged();
        } else {
            Map map = new HashMap();
            map.put("fjdID", Pid);
            map.put("sycx", ShortName);
            map.put("useType", "");
            mPresenter.getFalutInfo(position, JSON.toJSONString(map));
        }

    }

    @Override
    public void OnTextClick(int position) {
        if (Faults != null && Faults.size() > 0) {
            if (Faults.size() > position) {
                tvBJName.setText(Faults.get(position).getWzqm());
                tvBJName.setTag(Faults.get(position));
                if (Faults.get(position).getFlbm() != null && !"".equals(Faults.get(position).getFlbm())) {
                    Map map = new HashMap();
                    map.put("flbm", Faults.get(position).getFlbm());
                    mPresenter.getFaultList(JSON.toJSONString(map));
                }
            }
        }

        dialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        if (imagesTemp != null && imagesTemp.size() > 0 && state.equals("0")) {
            new AlertDialog.Builder(this).setTitle("提示！").setMessage("当前有照片还未上传，是否确定退出")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.where(ImageDao.class).findAll().deleteAllFromRealm();
                    }
                });
            }
        }).subscribeOn(Schedulers.from(JXApplication.getExecutor())).subscribe();
    }
}
