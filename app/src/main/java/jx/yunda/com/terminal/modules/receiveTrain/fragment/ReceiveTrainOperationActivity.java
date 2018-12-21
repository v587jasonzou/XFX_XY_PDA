package jx.yunda.com.terminal.modules.receiveTrain.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.base.JXApplication;
import jx.yunda.com.terminal.modules.ORGBook.model.BookCalenderBean;
import jx.yunda.com.terminal.modules.receiveTrain.adapter.OrgTreeViewAdapter;
import jx.yunda.com.terminal.modules.receiveTrain.model.NodeEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.OrgEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.ReceiveRequest;
import jx.yunda.com.terminal.modules.receiveTrain.model.ReceiveTrainNotice;
import jx.yunda.com.terminal.modules.receiveTrain.model.RepairEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.TrainEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.TrainNoEntity;
import jx.yunda.com.terminal.modules.receiveTrain.presenter.IReceiveTrainOperation;
import jx.yunda.com.terminal.modules.receiveTrain.presenter.ReceiveTrainOperationPresenter;
import jx.yunda.com.terminal.modules.tpprocess.ImagesAdapter;
import jx.yunda.com.terminal.modules.tpprocess.RealmDb.ImageDao;
import jx.yunda.com.terminal.modules.tpprocess.TicketInfoActivity;
import jx.yunda.com.terminal.utils.NetWorkUtils;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.DateTimeText;
import jx.yunda.com.terminal.widget.PhotoReadActivity;
import jx.yunda.com.terminal.widget.SodukuGridView;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ReceiveTrainOperationActivity extends BaseActivity<ReceiveTrainOperationPresenter> implements IReceiveTrainOperation {

    public ReceiveTrainNotice entity;
    @BindView(R.id.receive_train_operation_back)
    ImageButton receiveTrainOperationBack;
    @BindView(R.id.receive_train_operation_train)
    TextView receiveTrainOperationTrain;
    @BindView(R.id.receive_train_operation_section)
    TextView receiveTrainOperationSection;
    @BindView(R.id.receive_train_operation_xc)
    AutoCompleteTextView receiveTrainOperationXc;
    @BindView(R.id.tp_approval_content_zy)
    TextView tpApprovalContentZy;
    @BindView(R.id.tp_approval_content_kh)
    TextView tpApprovalContentKh;
    @BindView(R.id.receive_train_operation_calendar)
    TextView receiveTrainOperationCalendar;
    @BindView(R.id.receive_train_operation_processr)
    TextView receiveTrainOperationProcessr;
    @BindView(R.id.tp_approval_content_sxff)
    EditText tpApprovalContentSxff;
    @BindView(R.id.receive_train_operation_trainNo)
    AutoCompleteTextView receive_train_operation_trainNo;
    @BindView(R.id.btNext)
    Button btNext;
    @BindView(R.id.ivCH_Spinner)
    ImageView ivCH_Spinner;
    Unbinder unbinder;
    List<TrainEntity> trainEntities = new ArrayList<>();
    List<TrainNoEntity> trainNoEntities = new ArrayList<>();
    List<RepairEntity> repairEntities = new ArrayList<>();
    List<NodeEntity> nodeEntities = new ArrayList<>();
    BookCalenderBean calenderBean;
    OrgTreeViewAdapter treeViewAdapter;
    List<OrgEntity> Faults = new LinkedList<>();
    AlertDialog dialog;
    ListView faultsListView;
    LinkedList<OrgEntity> childMenu = new LinkedList<>();
    public static int IMAGE_PICKER = 666;
    public static final String JPG_EXTNAME = ".jpg";
    int ImagesTimes = 0;
    int AllImagesTimes = 0;
    ArrayList<String> images = new ArrayList<>();
    ArrayList<ImageItem> imagesTemp = new ArrayList<>();
    ImagesAdapter adapter;
    @BindView(R.id.gvImages)
    SodukuGridView gvImages;
    String UUID;
    String state = "0";

    @Override
    protected ReceiveTrainOperationPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new ReceiveTrainOperationPresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.receive_train_operation_fm;
    }

    @Override
    public void initSubViews(View view) {

    }

    ReceiveRequest request;

    @Override
    public void initData() {
        request = new ReceiveRequest();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            entity = (ReceiveTrainNotice) bundle.getSerializable("TrainNotice");
        }
        UUID = Utils.getUUID();
        images.add("0");
        if (entity != null) {
            request.setTransTrainId(entity.getIdx());
            if (entity.getTrainTypeShortName() != null) {
                request.setTrainTypeIDX(entity.getTrainTypeIDX());
                request.setTrainTypeShortName(entity.getTrainTypeShortName());
                receiveTrainOperationTrain.setText(entity.getTrainTypeShortName());
                receiveTrainOperationTrain.setTag(entity.getTrainTypeIDX());
                receiveTrainOperationTrain.setEnabled(false);
            }
            if (entity.getTrainNo() != null) {
                request.setTrainNo(entity.getTrainNo());
                receive_train_operation_trainNo.setText(entity.getTrainNo());
                receive_train_operation_trainNo.setEnabled(false);
                ivCH_Spinner.setEnabled(false);
            }
            if (entity.getDName() != null) {
                request.setdID(entity.getDId() + "");
                request.setdNAME(entity.getDName());
                receiveTrainOperationSection.setText(entity.getDName());
                receiveTrainOperationSection.setTag(entity.getDId());
                receiveTrainOperationSection.setEnabled(false);
            }
            if (entity.getRepairClassName() != null && entity.getRepairClassIDX() != null) {
                receiveTrainOperationXc.setText(entity.getRepairClassName());
                receiveTrainOperationXc.setTag(entity.getRepairClassIDX());
                request.setRepairClassIDX(entity.getRepairClassIDX());
                request.setRepairClassName(entity.getRepairClassName());
            }
        }
//        tpApprovalContentKh.setText(Utils.formatTime(new Date(),"yyyy-MM-dd HH:mm"));
        initDialog();
        adapter = new ImagesAdapter(this, images);
        adapter.setState("0");
        gvImages.setAdapter(adapter);
        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == images.size() - 1) {
                    Intent intent = new Intent(ReceiveTrainOperationActivity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                    startActivityForResult(intent, IMAGE_PICKER);
                } else {
                    Bundle bundle1 = new Bundle();
                    Intent intent = new Intent(ReceiveTrainOperationActivity.this, PhotoReadActivity.class);
                    bundle1.putSerializable("images", imagesTemp);
                    bundle1.putInt("position", position);
                    bundle1.putString("state", state);
                    intent.putExtras(bundle1);
                    startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
                }

            }
        });
//        receive_train_operation_trainNo.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if ("".equals(s.toString())) {
//                    return;
//                }
//                trainNos.clear();
//                for (TrainNoEntity bean : trainNoTemps) {
//                    if (bean.getTrainNo().contains(s.toString())) {
//                        trainNos.add(bean);
//                    }
//                }
//                final PopupMenu popupMenu = new PopupMenu(ReceiveTrainOperationActivity.this, receive_train_operation_trainNo);
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                        if (imm != null) {
//                            imm.hideSoftInputFromWindow(receive_train_operation_trainNo.getWindowToken(), 0);
//                        }
//                        receive_train_operation_trainNo.setText(item.getTitle().toString());
////                            receiveTrainOperationTrain.setTag(trainNos.get(item.getItemId()).getid);
//                        receiveTrainOperationSection.setText(trainNos.get(item.getItemId()).getDName());
//                        receiveTrainOperationSection.setTag(trainNos.get(item.getItemId()).getDId());
//                        request.setdID(trainNos.get(item.getItemId()).getDId());
//                        request.setdNAME(trainNos.get(item.getItemId()).getDName());
//                        popupMenu.dismiss();
//                        request.setTrainNo(trainNos.get(item.getItemId()).getTrainNo());
//                        return false;
//                    }
//                });
//                Menu menu_more = popupMenu.getMenu();
//                menu_more.clear();
//                int size = trainNos.size();
//                for (int i = 0; i < size; i++) {
//                    menu_more.add(Menu.NONE, i, i, trainNos.get(i).getTrainNo());
//                }
//                popupMenu.show();
//                ivCH_Spinner.setEnabled(false);
//                popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
//                    @Override
//                    public void onDismiss(PopupMenu menu) {
//                        ivCH_Spinner.setEnabled(true);
//                    }
//                });
//            }
//        });
        ivCH_Spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(receive_train_operation_trainNo.isPopupShowing()){
                    receive_train_operation_trainNo.dismissDropDown();
                }else {
                    receive_train_operation_trainNo.showDropDown();
                }

            }
        });

    }

    boolean isFirst = false;
    boolean mBackKeyPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirst = true;
        getProgressDialog().show();
        mPresenter.getBookCalender();
    }

    private void initDialog() {
        //设置设备故障选择弹出框
        View view = LayoutInflater.from(this).inflate(R.layout.treeview_dialog, null);
        faultsListView = (ListView) view.findViewById(R.id.lvTree);
        treeViewAdapter = new OrgTreeViewAdapter(this, Faults);
        treeViewAdapter.setOnImageClickLisnter(new OrgTreeViewAdapter.OnImageClickLisnter() {
            @Override
            public void OnImageClick(int position, String Pid) {
                if (Faults.get(position).isOpen()) {
                    Faults.get(position).setOpen(false);
                    String parentId = Faults.get(position).getId();
                    LinkedList<OrgEntity> temps = new LinkedList<>();
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
//                    Map map = new HashMap();
//                    map.put("fjdID", Pid);
//                    map.put("sycx", ShortName);
//                    map.put("useType", "");
//                    mPresenter.getFalutInfo(position, JSON.toJSONString(map));
                    mPresenter.getOrgs(position, Faults.get(position).getId());
                }
            }

            @Override
            public void OnTextClick(int position) {
                if (Faults != null && Faults.size() > 0) {
                    if (Faults.size() > position) {
                        receiveTrainOperationSection.setText(Faults.get(position).getName());
                        receiveTrainOperationSection.setTag(Faults.get(position).getId());
                        request.setdNAME(Faults.get(position).getName());
                        request.setdID(Faults.get(position).getId());
                    }
                }

                dialog.dismiss();
            }
        });
        faultsListView.setAdapter(treeViewAdapter);
        dialog = new AlertDialog.Builder(this).setTitle("请选择配属段").setView(view)
                .setPositiveButton("确定", null).create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Faults.clear();
                treeViewAdapter.notifyDataSetChanged();
            }
        });
    }

    //判断是否为子节点
    public LinkedList<OrgEntity> isChild(LinkedList<OrgEntity> temps, String parentId) {
        for (OrgEntity mu : temps) {
            //遍历出父id等于参数的id，add进子节点集合
            if (parentId.equals(mu.getFjdId())) {
                //递归遍历下一级
                isChild(temps, mu.getId());
                childMenu.add(mu);
            }
        }
        return childMenu;
    }

    List<ImageDao> unLoadImages;

    @OnClick({R.id.receive_train_operation_back, R.id.receive_train_operation_train, R.id.receive_train_operation_section,
            R.id.receive_train_operation_trainNo, R.id.receive_train_operation_xc, R.id.receive_train_operation_calendar,
            R.id.receive_train_operation_processr, R.id.btNext})
    void viewClick(View view) {
        switch (view.getId()) {
            case R.id.receive_train_operation_back:
                finish();
                break;
            case R.id.receive_train_operation_train:
                getProgressDialog().show();
                mPresenter.getTrainType();
                break;
            case R.id.receive_train_operation_section:
                getProgressDialog().show();
                mPresenter.getOrgs(0, "0");
                break;
            case R.id.receive_train_operation_trainNo:
//                if (receiveTrainOperationTrain.getTag() == null) {
//                    ToastUtil.toastShort("请先选择车型");
//                    return;
//                }
//                getProgressDialog().show();
//                mPresenter.getTrainNo(receiveTrainOperationTrain.getTag().toString());
                break;
            case R.id.receive_train_operation_xc:
//                if (receiveTrainOperationTrain.getTag() == null) {
//                    ToastUtil.toastShort("请先选择车型");
//                    return;
//                }
//                getProgressDialog().show();
//                mPresenter.getRepair(receiveTrainOperationTrain.getTag().toString());
                break;
            case R.id.receive_train_operation_calendar:
                getProgressDialog().show();
                mPresenter.getBookCalender();
                break;
            case R.id.receive_train_operation_processr:
                if (receiveTrainOperationTrain.getTag() == null) {
                    ToastUtil.toastShort("请先选择车型");
                    return;
                }
                if (receiveTrainOperationXc.getTag() == null) {
                    ToastUtil.toastShort("请先选择修程");
                    return;
                }
                getProgressDialog().show();
                mPresenter.getNode(receiveTrainOperationTrain.getTag().toString(), receiveTrainOperationXc.getTag().toString());
                break;
            case R.id.btNext:
                if (receiveTrainOperationTrain.getTag() == null) {
                    ToastUtil.toastShort("还未选择车型");
                    return;
                }
                if (receive_train_operation_trainNo.getText() == null ||
                        receive_train_operation_trainNo.getText().toString().equals("")) {
                    ToastUtil.toastShort("还未选择车号");
                    return;
                }
                if (receiveTrainOperationSection.getTag() == null) {
                    ToastUtil.toastShort("还未选择配属段");
                    return;
                }
                if (receiveTrainOperationProcessr.getTag() == null) {
                    ToastUtil.toastShort("还未选择流程");
                    return;
                }
//                if(receiveTrainOperationCalendar.getTag()==null){
//                    ToastUtil.toastShort("还未选择日历");
//                    return;
//                }
                if (receiveTrainOperationXc.getTag() == null) {
                    ToastUtil.toastShort("还未选择修程");
                    return;
                }
                if(imagesTemp.size()==0){
                    ToastUtil.toastShort("还未拍摄照片");
                    return;
                }
                if (tpApprovalContentSxff.getText() != null) {
                    request.setRemarks(tpApprovalContentSxff.getText().toString());
                }
                if (tpApprovalContentKh.getText() != null && !"".equals(tpApprovalContentKh.getText().toString())) {
                    request.setPlanBeginTime(tpApprovalContentKh.getText().toString());
                }else {
                    request.setPlanBeginTime(Utils.formatTime(new Date(), "yyyy-MM-dd HH:mm"));
                }
                getProgressDialog().show();
                if (imagesTemp != null && imagesTemp.size() > 0) {
                    if (unLoadImages != null && unLoadImages.size() > 0) {
                        rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
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
                        rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
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
                                    rx.Observable.create(new rx.Observable.OnSubscribe<Object>() {
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
                                    request.setTrainPhoto(jsonstr2);
                                    mPresenter.Confirm(JSON.toJSONString(request));
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
                    mPresenter.Confirm(JSON.toJSONString(request));
                }

                break;
            default:
                break;
        }
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
                    tpApprovalContentKh.setText(Utils.formatTime(new Date(), "yyyy-MM-dd HH:mm"));
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
    public void getTrainNameSuccess(final List<TrainEntity> list) {
        getProgressDialog().dismiss();
        if (list != null && list.size() > 0) {
            final PopupMenu popupMenu = new PopupMenu(this, receiveTrainOperationTrain);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(receiveTrainOperationTrain.getWindowToken(), 0);
                    }
                    receiveTrainOperationTrain.setText(item.getTitle().toString());
                    receiveTrainOperationTrain.setTag(list.get(item.getItemId()).getTypeID());
                    receive_train_operation_trainNo.setText("");
                    receive_train_operation_trainNo.setTag(null);
                    receiveTrainOperationXc.setText("");
                    receiveTrainOperationXc.setTag(null);
                    receiveTrainOperationProcessr.setText("");
                    receiveTrainOperationProcessr.setTag(null);
                    popupMenu.dismiss();
                    request.setTrainTypeShortName(list.get(item.getItemId()).getShortName());
                    request.setTrainTypeIDX(list.get(item.getItemId()).getTypeID());
                    getProgressDialog().show();
                    mPresenter.getTrainNo(receiveTrainOperationTrain.getTag().toString());
                    return false;
                }
            });
            Menu menu_more = popupMenu.getMenu();
            menu_more.clear();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                menu_more.add(Menu.NONE, i, i, list.get(i).getShortName());
            }
            popupMenu.show();
            ivCH_Spinner.setEnabled(false);
            popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                @Override
                public void onDismiss(PopupMenu menu) {
                    ivCH_Spinner.setEnabled(true);
                }
            });
        } else {
            ToastUtil.toastShort("无相关车型数据");
        }
    }

    List<TrainNoEntity> trainNos = new ArrayList<>();
    List<TrainNoEntity> trainNoTemps = new ArrayList<>();
    List<RepairEntity> trainNos1 = new ArrayList<>();
    List<RepairEntity> trainNoTemps1 = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    String[] atr ;
    ArrayAdapter<String> arrayAdapter1;
    String[] atr1 ;
    @Override
    public void getTrainNoSuccess(final List<TrainNoEntity> list) {
        trainNos.clear();
        trainNoTemps.clear();
        if (list != null && list.size() > 0) {
            trainNos.addAll(list);
            trainNoTemps.addAll(list);
            atr = new String[trainNos.size()];
            for(int i = 0;i<trainNos.size();i++){
                atr[i] = trainNos.get(i).getTrainNo();
            }
            arrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, atr);
            receive_train_operation_trainNo.setAdapter(arrayAdapter);
            receive_train_operation_trainNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TrainNoEntity entity  = null;
                    for(TrainNoEntity bean :trainNos){
                        if(bean.getTrainNo().equals(receive_train_operation_trainNo.getText().toString())){
                            entity = bean;
                            break;
                        }
                    }
                    if(entity!=null){
                        receive_train_operation_trainNo.setText(entity.getTrainNo());
//                            receiveTrainOperationTrain.setTag(trainNos.get(item.getItemId()).getid);
                        receiveTrainOperationSection.setText(entity.getDName());
                        receiveTrainOperationSection.setTag(entity.getDId());
                        request.setdID(entity.getDId());
                        request.setdNAME(entity.getDName());
                        receive_train_operation_trainNo.dismissDropDown();
                        request.setTrainNo(entity.getTrainNo());
                    }

                }
            });
        }
        getProgressDialog().dismiss();
        if (list != null && list.size() > 0) {

        } else {
            ToastUtil.toastShort("无相关车号数据");
        }
        if (receiveTrainOperationTrain.getTag() == null) {
                    ToastUtil.toastShort("请先选择车型");
                    return;
                }
                getProgressDialog().show();
                mPresenter.getRepair(receiveTrainOperationTrain.getTag().toString());
    }

    @Override
    public void getRepairSuccess(final List<RepairEntity> list) {
        getProgressDialog().dismiss();
        if (list != null && list.size() > 0) {
//            final PopupMenu popupMenu = new PopupMenu(this, receiveTrainOperationXc);
//            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    if (imm != null) {
//                        imm.hideSoftInputFromWindow(receiveTrainOperationXc.getWindowToken(), 0);
//                    }
//                    receiveTrainOperationXc.setText(item.getTitle().toString());
//                    receiveTrainOperationXc.setTag(list.get(item.getItemId()).getXcID());
//                    receiveTrainOperationProcessr.setText("");
//                    receiveTrainOperationProcessr.setTag(null);
//                    request.setRepairClassIDX(list.get(item.getItemId()).getXcID());
//                    request.setRepairClassName(list.get(item.getItemId()).getXcName());
//                    if (receiveTrainOperationTrain.getTag() != null && receiveTrainOperationXc.getTag() != null) {
//                        isFirst = true;
//                        getProgressDialog().show();
//                        mPresenter.getNode(receiveTrainOperationTrain.getTag().toString(), receiveTrainOperationXc.getTag().toString());
//                    }
//                    popupMenu.dismiss();
//                    return false;
//                }
//            });
//            Menu menu_more = popupMenu.getMenu();
//            menu_more.clear();
//            int size = list.size();
//            for (int i = 0; i < size; i++) {
//                menu_more.add(Menu.NONE, i, i, list.get(i).getXcCode());
//            }
//            popupMenu.show();
            trainNos1.addAll(list);
            trainNoTemps1.addAll(list);
            atr1 = new String[trainNos1.size()];
            for(int i = 0;i<trainNos1.size();i++){
                atr1[i] = trainNos1.get(i).getXcCode();
            }
            arrayAdapter1 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, atr1);
            receiveTrainOperationXc.setAdapter(arrayAdapter1);
            receiveTrainOperationXc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    RepairEntity entity  = null;
                    for(RepairEntity bean :trainNos1){
                        if(bean.getXcCode().equals(receiveTrainOperationXc.getText().toString())){
                            entity = bean;
                            break;
                        }
                    }
                    if(entity!=null){
                        receiveTrainOperationXc.setText(entity.getXcCode());
                        receiveTrainOperationXc.setTag(entity.getXcID());
                        receiveTrainOperationProcessr.setText("");
                        receiveTrainOperationProcessr.setTag(null);
                        request.setRepairClassIDX(entity.getXcID());
                        request.setRepairClassName(entity.getXcName());
                        if (receiveTrainOperationTrain.getTag() != null && receiveTrainOperationXc.getTag() != null) {
                            isFirst = true;
                            getProgressDialog().show();
                            mPresenter.getNode(receiveTrainOperationTrain.getTag().toString(), receiveTrainOperationXc.getTag().toString());
                        }
                    }

                }
            });
        } else {
            ToastUtil.toastShort("无相关修程数据");
        }
    }

    @Override
    public void getNodeSuccess(final List<NodeEntity> list) {
        getProgressDialog().dismiss();
        if (list != null && list.size() > 0) {
            if (isFirst) {
                receiveTrainOperationProcessr.setText(list.get(0).getProcessName());
                receiveTrainOperationProcessr.setTag(list.get(0).getIdx());
                request.setProcessIDX(list.get(0).getIdx());
                request.setProcessName(list.get(0).getProcessName());
            } else {
                final PopupMenu popupMenu = new PopupMenu(this, receiveTrainOperationProcessr);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(receiveTrainOperationProcessr.getWindowToken(), 0);
                        }
                        receiveTrainOperationProcessr.setText(item.getTitle().toString());
                        receiveTrainOperationProcessr.setTag(list.get(item.getItemId()).getIdx());
                        request.setProcessIDX(list.get(item.getItemId()).getIdx());
                        request.setProcessName(item.getTitle().toString());
                        popupMenu.dismiss();
                        return false;
                    }
                });
                Menu menu_more = popupMenu.getMenu();
                menu_more.clear();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    menu_more.add(Menu.NONE, i, i, list.get(i).getProcessName());
                }
                popupMenu.show();
            }
        } else {
            ToastUtil.toastShort("无相关流程数据");
        }
        isFirst = false;
    }

    @Override
    public void getCalenderSuccess(BookCalenderBean bean) {
        getProgressDialog().dismiss();
        if (bean != null && bean.getDefInfo() != null) {
            if (isFirst) {
                BookCalenderBean.DefInfoBean entity = bean.getDefInfo();
                if (entity != null) {
                    receiveTrainOperationCalendar.setText(entity.getCalendarName());
                    receiveTrainOperationCalendar.setTag(entity.getIdx());
                    request.setWorkCalendarIDX(entity.getIdx());
                }
            } else {
                final BookCalenderBean.DefInfoBean entity = bean.getDefInfo();
                final PopupMenu popupMenu = new PopupMenu(this, receiveTrainOperationCalendar);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(receiveTrainOperationCalendar.getWindowToken(), 0);
                        }
                        receiveTrainOperationCalendar.setText(item.getTitle().toString());
                        receiveTrainOperationCalendar.setTag(entity.getIdx());
                        request.setWorkCalendarIDX(entity.getIdx());
                        popupMenu.dismiss();
                        return false;
                    }
                });
                Menu menu_more = popupMenu.getMenu();
                menu_more.clear();
                menu_more.add(Menu.NONE, 0, 0, entity.getCalendarName());
                popupMenu.show();
            }

        } else {
            ToastUtil.toastShort("无相关日历数据");
        }
        if (isFirst) {
            if (this.entity != null) {
                getProgressDialog().show();
                mPresenter.getNode(receiveTrainOperationTrain.getTag().toString(), receiveTrainOperationXc.getTag().toString());
            } else {
                isFirst = false;
            }
        }
    }

    @Override
    public void getOrgsSuccess(List<OrgEntity> list, int position) {
        getProgressDialog().dismiss();
        if (Faults.size() == 0) {
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setMyLevel(0);
                }
            }
            Faults.addAll(list);
            dialog.show();
            treeViewAdapter.notifyDataSetChanged();
        } else {
            dialog.show();
            if (list != null && list.size() > 0) {
                Faults.get(position).setOpen(true);
                int level = Faults.get(position).getMyLevel();
                Faults.get(position).setOpen(true);
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setMyLevel(level + 1);
                    list.get(i).setFjdId(Faults.get(position).getId());
                }
            }
            Faults.addAll(position + 1, list);
            treeViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void ConfirmSuccess() {
        getProgressDialog().dismiss();
        ToastUtil.toastShort("提交成功");
        finish();
    }

    @Override
    public void getDataFaild(String msg, String type) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    int Restimes = 0;

    @Override
    public void OnUpLoadImagesSuccess(final String path, final int position, final int imageNo) {
        Restimes++;
        rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
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
                                rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
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
                                            rx.Observable
                                                    .create(new rx.Observable.OnSubscribe<String>() {
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
                                                            request.setTrainPhoto(s);
                                                            mPresenter.Confirm(JSON.toJSONString(request));
                                                        }
                                                    });
                                        } else {
                                            getProgressDialog().dismiss();
                                            rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
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
                                                        if (NetWorkUtils.isWifiConnected(ReceiveTrainOperationActivity.this)) {
                                                            new AlertDialog.Builder(ReceiveTrainOperationActivity.this).setTitle("提示！")
                                                                    .setMessage("当前有" + integer + "张图片未上传成功，请重试")
                                                                    .setPositiveButton("重试", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            getProgressDialog().show();
                                                                            rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
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
    public void OnLoadFail(String msg, int position, int imageNo) {
        if (position == -1 && imageNo == -1) {
            getProgressDialog().dismiss();
            ToastUtil.toastShort(msg);

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
                    rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
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
                                        if (NetWorkUtils.isWifiConnected(ReceiveTrainOperationActivity.this)) {
                                            new AlertDialog.Builder(ReceiveTrainOperationActivity.this).setTitle("提示！")
                                                    .setMessage("当前有" + aVoid + "张图片未上传成功，是否重试或保存本地？")
                                                    .setPositiveButton("重试", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            getProgressDialog().show();
                                                            rx.Observable.create(new rx.Observable.OnSubscribe<Object>() {
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
                                                    .show();
                                        } else {
                                            getProgressDialog().dismiss();
                                            ToastUtil.toastShort("因网络问题，当前有" + aVoid + "张图片未上传，请检查网络重新上传");
                                        }
                                    }
                                }
                            });

                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiveTrainOperationTrain != null) {
            receiveTrainOperationTrain.setText("");
            receiveTrainOperationTrain.setTag(null);
        }
        if (receive_train_operation_trainNo != null) {
            receive_train_operation_trainNo.setText("");
        }
        if (receiveTrainOperationCalendar != null) {
            receiveTrainOperationCalendar.setText("");
            receiveTrainOperationCalendar.setTag(null);
        }
        if (receiveTrainOperationProcessr != null) {
            receiveTrainOperationProcessr.setText("");
            receiveTrainOperationProcessr.setTag(null);
        }
        if (receiveTrainOperationXc != null) {
            receiveTrainOperationXc.setText("");
            receiveTrainOperationXc.setTag(null);
        }
        if (receiveTrainOperationSection != null) {
            receiveTrainOperationSection.setText("");
            receiveTrainOperationSection.setTag(null);
        }
        rx.Observable.create(new rx.Observable.OnSubscribe<Object>() {
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
