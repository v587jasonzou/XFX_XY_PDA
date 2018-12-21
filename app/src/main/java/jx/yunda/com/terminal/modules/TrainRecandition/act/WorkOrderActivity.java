package jx.yunda.com.terminal.modules.TrainRecandition.act;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.base.JXApplication;
import jx.yunda.com.terminal.entity.FileBaseBean;
import jx.yunda.com.terminal.modules.TrainRecandition.adapter.DialogAdapter;
import jx.yunda.com.terminal.modules.TrainRecandition.adapter.HegeItemAdapter;
import jx.yunda.com.terminal.modules.TrainRecandition.model.InspectorOrderBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.OrderBean;
import jx.yunda.com.terminal.modules.TrainRecandition.presenter.IWorkOrder;
import jx.yunda.com.terminal.modules.TrainRecandition.presenter.WorkOrderPresenter;
import jx.yunda.com.terminal.modules.tpprocess.ImagesAdapter;
import jx.yunda.com.terminal.modules.tpprocess.TicketInfoActivity;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.PhotoReadActivity;
import jx.yunda.com.terminal.widget.SodukuGridView;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class WorkOrderActivity extends BaseActivity<WorkOrderPresenter> implements IWorkOrder, View.OnClickListener {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvTakePhoto)
    TextView tvTakePhoto;
    @BindView(R.id.tvphotoParts)
    TextView tvphotoParts;
    @BindView(R.id.gvImages)
    SodukuGridView gvImages;
    @BindView(R.id.llTakePhoto)
    LinearLayout llTakePhoto;
    @BindView(R.id.tvDataItem)
    TextView tvDataItem;
    @BindView(R.id.rlDataItem)
    RecyclerView rlDataItem;
    @BindView(R.id.llDataItem)
    LinearLayout llDataItem;
    @BindView(R.id.tvQuliItem)
    TextView tvQuliItem;
    @BindView(R.id.rlQuliItem)
    RecyclerView rlQuliItem;
    @BindView(R.id.llQuliItem)
    LinearLayout llQuliItem;
    @BindView(R.id.btLast)
    Button btLast;
    @BindView(R.id.btNext)
    Button btNext;
    @BindView(R.id.llSubmit)
    RelativeLayout llSubmit;
    @BindView(R.id.tvTitleResult)
    TextView tvTitleResult;
    @BindView(R.id.tvPrograss)
    TextView tvPrograss;
    boolean isTitle;
    ImagesAdapter imagesAdapter;

    ArrayList<String> images = new ArrayList<>();
    ArrayList<ImageItem> imagesItems = new ArrayList<>();
    List<OrderBean> orderBeans = new ArrayList<>();
    List<InspectorOrderBean> InspectorOrders = new ArrayList<>();
    List<InspectorOrderBean> InspectorOrderstemp = new ArrayList<>();
    HegeItemAdapter adapter;
    String state = "order";
    AlertDialog dialog;
    List<String> dialogItems = new ArrayList<>();
    DialogAdapter dialogAdapter;
    int position = 0;
    int EquipPosition;
    public static int IMAGE_PICKER = 666;
    public static final String JPG_EXTNAME = ".jpg";
    public static String TYPE_NEXT = "next";
    public static String TYPE_LAST = "last";
    public static String TYPE_COMPLETE = "complete";
    public static String TYPE_NORMAL = "normal";
    ListView lvDialog;
    boolean isJump = false;
    String status;
    @Override
    protected WorkOrderPresenter getPresenter() {
        return new WorkOrderPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_workorder;
    }

    @Override
    public void initSubViews(View view) {

    }
    String idx = "";
    String type = "";
    String typeIdx = "";
    String trainNo = "";
    String nodeName = "";
    String dictid;
    String dictname;
    String filter2;
    String flbm;
    String coid;
    String wzqm;
    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        orderBeans = (List<OrderBean>) bundle.getSerializable("cardBeans");
        position = bundle.getInt("position");
        setProgressed();
//        if (position == 0) {
//            btLast.setVisibility(View.GONE);
//        }
        if (position == orderBeans.size() - 1) {
            btNext.setText("完成");
        }
        idx = bundle.getString("nodeidx");
        type = bundle.getString("type");
        trainNo = bundle.getString("trainNo");
        nodeName = bundle.getString("nodeName");
        typeIdx = bundle.getString("typeIdx");
        dictid = bundle.getString("dictid");
        dictname = bundle.getString("dictname");
        filter2 = bundle.getString("filter2");
        flbm = bundle.getString("flbm");
        coid = bundle.getString("coid");
        wzqm = bundle.getString("wzqm");
        setdata();
        images.add("0");
        imagesAdapter = new ImagesAdapter(this, images);
//        imagesAdapter.setState(state);
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getNowPositiong() != -1) {
                    submitEditItem(adapter.getNowPositiong());
                }
                setResult(201);
                finish();

            }
        });
        gvImages.setAdapter(imagesAdapter);
        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (state.equals("0") || state.equals("order")) {
                    if (position == images.size() - 1) {
                        Intent intent = new Intent(WorkOrderActivity.this, ImageGridActivity.class);
                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                        startActivityForResult(intent, IMAGE_PICKER);
                    } else {
                        Bundle bundle1 = new Bundle();
                        Intent intent = new Intent(WorkOrderActivity.this, PhotoReadActivity.class);
                        bundle1.putSerializable("images", imagesItems);
                        bundle1.putInt("position", position);
                        bundle1.putString("state", state);
                        intent.putExtras(bundle1);
                        startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
                    }
                } else {
                    Bundle bundle1 = new Bundle();
                    Intent intent = new Intent(WorkOrderActivity.this, PhotoReadActivity.class);
                    bundle1.putInt("position", position);
                    bundle1.putString("state", state);
                    bundle1.putSerializable("images", imagesItems);
                    bundle1.putSerializable("is", imagesItems);
                    intent.putExtras(bundle1);
                    startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);

                }

            }
        });
    }

    private void setProgressed() {
        if(orderBeans.size()>0){
            tvPrograss.setText((position+1)+"/"+orderBeans.size());
        }

    }

    public void setNextItem() {
        if (isTitle) {
//            InspectorOrderstemp.get(EquipPosition).setRepairResult(tvTitleResult.getText().toString());
            submitEditItemNew(EquipPosition, TYPE_NEXT);
        } else {
            submitEditItemNew(0, TYPE_NEXT);
//            InspectorOrders.get(EquipPosition).setDetectResult(dialogItems.get(position));
//            submitEditItem(EquipPosition);
//            adapter.notifyDataSetChanged();
        }

    }

    public void setLastItem() {
        status = TYPE_LAST;
        if (isTitle) {
//            InspectorOrderstemp.get(EquipPosition).setRepairResult(tvTitleResult.getText().toString());
            submitEditItemNew(EquipPosition, TYPE_LAST);
        } else {
            submitEditItemNew(0, TYPE_LAST);
        }

    }

    private void setdata() {
        String idx = orderBeans.get(position).getIdx();
        mPresenter.getWorkOrders(idx);
        if (orderBeans.get(position) != null) {
            if(orderBeans.get(position).getWorkCardName()!=null){
                tvphotoParts.setText(orderBeans.get(position).getWorkCardName());
            }
        }
        btNext.setOnClickListener(this);
        btLast.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rlDataItem.setFocusable(true);
        rlDataItem.setLayoutManager(layoutManager);
        rlDataItem.setHasFixedSize(true);
//        lvList.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));//用类设置分割线
//Rv.addItemDecoration(new DividerItemDecoration(this, R.drawable.list_divider)); //用已有图片设置分割线
        //为ListView绑定适配器
        adapter = new HegeItemAdapter(this, InspectorOrders);
        rlDataItem.setAdapter(adapter);
        adapter.setOnItemClickListner(new HegeItemAdapter.OnItemClickListner() {
            @Override
            public void OnItemClick(int position) {
                isTitle = false;
                EquipPosition = position;
                mPresenter.getItems(InspectorOrders.get(position).getDetectItemCode());
            }

            @Override
            public void OnEditComplet(int position) {
                EquipPosition = position;
                submitEditItem(position);
            }

            @Override
            public void OnComplet(final int position, final String msg) {
//                Observable.create(new Observable.OnSubscribe<Object>() {
//                    @Override
//                    public void call(Subscriber<? super Object> subscriber) {
//                        Map<String,Object> map = new HashMap<>();
//                        map.put("idx",InspectorOrders.get(position).getIdx());
//                        map.put("detectResult",msg);
//                        mPresenter.SubmitEduit(SysInfo.userInfo.emp.getOperatorid(), JSON.toJSONString(map));
//                    }
//                }).subscribeOn(Schedulers.io()).subscribe();
            }
        });
        tvTitleResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTitle = true;
                dialogItems.clear();
                dialogItems.add("合格");
                dialogItems.add("不合格");
                if (dialog == null) {
                    View view = LayoutInflater.from(WorkOrderActivity.this).inflate(R.layout.dialog_check_result, null);
                    lvDialog = (ListView) view.findViewById(R.id.lvDialog);
                    dialogAdapter = new DialogAdapter(WorkOrderActivity.this, dialogItems);
                    lvDialog.setAdapter(dialogAdapter);
                    lvDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (isTitle) {
                                tvTitleResult.setText(dialogItems.get(position));
                                InspectorOrderstemp.get(EquipPosition).setRepairResult(dialogItems.get(position));
                                submitEditItemNew(EquipPosition, TYPE_NORMAL);
                            } else {
                                InspectorOrders.get(EquipPosition).setDetectResult(dialogItems.get(position));
                                submitEditItem(EquipPosition);
                                adapter.notifyDataSetChanged();
                            }
                            dialog.dismiss();
                        }
                    });
                    dialog = new AlertDialog.Builder(WorkOrderActivity.this).setView(view)
                            .setNegativeButton("取消", null)
                            .create();
                }
                dialogAdapter.notifyDataSetChanged();
                dialog.show();
            }
        });
    }

    public void submitEditItem(final int position) {
        if (!this.isFinishing()) {
            getProgressDialog().show();
        }
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                if (InspectorOrders.size() > position) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("idx", InspectorOrders.get(position).getIdx());
                    map.put("detectResult", InspectorOrders.get(position).getDetectResult());
                    mPresenter.SubmitEduit(SysInfo.userInfo.emp.getOperatorid(), JSON.toJSONString(map));
                }
            }
        }).subscribeOn(Schedulers.from(JXApplication.getExecutor())).subscribe();
    }

    public void submitEditItemNew(final int position, final String type) {
        if (!this.isFinishing()) {
            getProgressDialog().show();
        }
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                if (isTitle) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("idx", InspectorOrderstemp.get(position).getTaskIdx());
                    map.put("repairResult", InspectorOrderstemp.get(position).getRepairResult());
                    map.put("workCardIDX", orderBeans.get(0).getWorkCardIdx());
                    mPresenter.SubmitEduitNew(SysInfo.userInfo.emp.getOperatorid(), JSON.toJSONString(map), "", type);
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("idx", InspectorOrders.get(position).getTaskIdx());
                    map.put("repairResult", "合格");
                    map.put("workCardIDX", orderBeans.get(0).getWorkCardIdx());
                    mPresenter.SubmitEduitNew(SysInfo.userInfo.emp.getOperatorid(), JSON.toJSONString(map), "", type);
                }

            }
        }).subscribeOn(Schedulers.from(JXApplication.getExecutor())).subscribe();
    }

    @Override
    public void submitOrderSuccess(String msg) {
        isAllfinish = false;
        if (msg.equals(TYPE_LAST)) {
            position = position - 1;
            String idx = orderBeans.get(position).getIdx();
            mPresenter.getWorkOrders(idx);
//            if (position == 0) {
//                btLast.setVisibility(View.GONE);
//            }
            if (position >= orderBeans.size() - 1) {
                btNext.setText("完成");
            } else {
                btNext.setText("下一项");
            }
            if (orderBeans.get(position) != null) {
                if(orderBeans.get(position).getWorkCardName()!=null){
                    tvphotoParts.setText(orderBeans.get(position).getWorkCardName());
                }
            }
            setProgressed();
        } else if (msg.equals(TYPE_NEXT)) {
            position = position + 1;
            String idx = orderBeans.get(position).getIdx();
            mPresenter.getWorkOrders(idx);
            if (position >= orderBeans.size() - 1) {
                btNext.setText("完成");
            } else {
                btNext.setText("下一项");
            }
//            if (position > 0) {
//                btLast.setVisibility(View.VISIBLE);
//            }
            if (orderBeans.get(position) != null) {
                if(orderBeans.get(position).getWorkCardName()!=null){
                    tvphotoParts.setText(orderBeans.get(position).getWorkCardName());
                }
            }
            setProgressed();
        } else if (msg.equals(TYPE_COMPLETE)) {
            setResult(201);
            finish();
        } else if (msg.equals("IMAGES")) {

        } else {
            getProgressDialog().dismiss();
        }
    }

    @Override
    public void submitOrderFaild(String msg, String type) {
        getProgressDialog().dismiss();
        if (type.equals("IMAGES")) {
            new AlertDialog.Builder(this).setTitle("提示！")
                    .setMessage("当前照片上传失败，是否重新上传")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getProgressDialog().show();
                            Observable.create(new Observable.OnSubscribe<Object>() {
                                @Override
                                public void call(Subscriber<? super Object> subscriber) {
                                    Map<String, Object> map = new HashMap<>();
                                    if (InspectorOrders.size() > 0) {
                                        map.put("idx", InspectorOrders.get(position).getTaskIdx());
                                        map.put("repairResult", InspectorOrders.get(position).getRepairResult());
                                        map.put("workCardIDX", orderBeans.get(0).getWorkCardIdx());
                                    } else {
                                        map.put("idx", InspectorOrderstemp.get(position).getTaskIdx());
                                        map.put("repairResult", InspectorOrderstemp.get(position).getRepairResult());
                                        map.put("workCardIDX", orderBeans.get(0).getWorkCardIdx());
                                    }
                                    Map<String, Object> map1 = new HashMap<>();
                                    map1.put("filePath", imagesItems.get(imagesItems.size() - 1).path);
                                    List<Map<String, Object>> list = new ArrayList<>();
                                    list.add(map1);
                                    mPresenter.SubmitEduitNew(SysInfo.userInfo.emp.getOperatorid(), JSON.toJSONString(map), JSON.toJSONString(list), "IMAGES");

                                }
                            }).subscribeOn(Schedulers.from(JXApplication.getExecutor())).subscribe();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            imagesItems.remove(imagesItems.size() - 1);
                            images.remove(images.size() - 2);
                            imagesAdapter.notifyDataSetChanged();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void upLoadImagesSuccess(final String path, String idx) {
        imagesItems.get(imagesItems.size() - 1).name = idx;
        imagesItems.get(imagesItems.size() - 1).path = path;
        getProgressDialog().dismiss();
//        Observable.create(new Observable.OnSubscribe<Object>() {
//            @Override
//            public void call(Subscriber<? super Object> subscriber) {
//                Map<String,Object> map = new HashMap<>();
//                if(InspectorOrders.size()>0){
//                    map.put("idx",InspectorOrders.get(position).getTaskIdx());
//                    map.put("repairResult",InspectorOrders.get(position).getRepairResult());
//                    map.put("workCardIDX",orderBeans.get(0).getWorkCardIdx());
//                }else {
//                    map.put("idx",InspectorOrderstemp.get(position).getTaskIdx());
//                    map.put("repairResult",InspectorOrderstemp.get(position).getRepairResult());
//                    map.put("workCardIDX",orderBeans.get(0).getWorkCardIdx());
//                }
//                Map<String,Object> map1 = new HashMap<>();
//                map1.put("filePath",path);
//                List< Map<String,Object>> list = new ArrayList<>();
//                list.add(map1);
//                mPresenter.SubmitEduitNew(SysInfo.userInfo.emp.getOperatorid(), JSON.toJSONString(map),JSON.toJSONString(list),"IMAGES");
//
//            }
//        }).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void upLoadImagesFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
        new AlertDialog.Builder(this).setTitle("提示！")
                .setMessage("当前照片上传失败，是否重试？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getProgressDialog().show();
                        String idx = "";
                        if (InspectorOrders.size() > 0) {
                            idx = InspectorOrders.get(position).getTaskIdx();
                        } else {
                            idx = InspectorOrderstemp.get(0).getTaskIdx();
                        }
                        getProgressDialog().show();
                        mPresenter.UpLoadImage(imagesItems.get(imagesItems.size() - 1), idx);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imagesItems.remove(imagesItems.size() - 1);
                        images.remove(images.size() - 2);
                        imagesAdapter.notifyDataSetChanged();
                    }
                }).show();
    }

    @Override
    public void LoaddataSuccess(List<InspectorOrderBean> list) {
        getProgressDialog().dismiss();
        InspectorOrders.clear();
        InspectorOrderstemp.clear();
        if (list != null && list.size() > 0) {
            for (InspectorOrderBean bean : list) {
                if (bean.getIdx() != null) {
                    InspectorOrders.add(bean);
                }
            }
            tvTitle.setText(list.get(0).getWorkTaskName());
        }
        if (list.size() == 1 && InspectorOrders.size() == 0) {
            InspectorOrderstemp.addAll(list);
            if(list.get(0).getRepairResult()!=null&&!list.get(0).getRepairResult().equals(""))
            tvTitleResult.setText(list.get(0).getRepairResult());
        }
        if (InspectorOrders.size() > 0) {
            isTitle = false;
            llDataItem.setVisibility(View.VISIBLE);
            tvTitleResult.setVisibility(View.GONE);
        } else {
            isTitle = true;
            tvTitleResult.setVisibility(View.VISIBLE);
            llDataItem.setVisibility(View.GONE);
        }
        String idx = "";
        if (InspectorOrders.size() > 0) {
            idx = InspectorOrders.get(0).getTaskIdx();
        } else {
            idx = InspectorOrderstemp.get(0).getTaskIdx();
        }
        mPresenter.getImages(idx, "");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void LoaddataFaild(String msg) {
        ToastUtil.toastShort(msg);
        getProgressDialog().dismiss();
    }

    @Override
    public void getItemsSuccess(List<String> list) {
        dialogItems.clear();
        if (list != null) {
            dialogItems.addAll(list);
            if (dialog == null) {
                View view = LayoutInflater.from(WorkOrderActivity.this).inflate(R.layout.dialog_check_result, null);
                lvDialog = (ListView) view.findViewById(R.id.lvDialog);
                dialogAdapter = new DialogAdapter(WorkOrderActivity.this, dialogItems);
                lvDialog.setAdapter(dialogAdapter);
                lvDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (isTitle) {
                            tvTitleResult.setText(dialogItems.get(position));
                            InspectorOrderstemp.get(EquipPosition).setRepairResult(dialogItems.get(position));
                            submitEditItemNew(EquipPosition, TYPE_NORMAL);
                        } else {
                            InspectorOrders.get(EquipPosition).setDetectResult(dialogItems.get(position));
                            submitEditItem(EquipPosition);
                            adapter.notifyDataSetChanged();
                        }
                        dialog.dismiss();
                    }
                });
                dialog = new AlertDialog.Builder(WorkOrderActivity.this).setView(view)
                        .setNegativeButton("取消", null)
                        .create();
            }
            dialogAdapter.notifyDataSetChanged();
            dialog.show();
        } else {
            ToastUtil.toastShort("无更多数据");
        }
    }

    @Override
    public void getItemsFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }
boolean isAllfinish =false;
    @Override
    public void SubmitEditSuccess() {
        if (TYPE_COMPLETE.equals(status)) {
            if (isTitle) {
                submitEditItemNew(EquipPosition, TYPE_COMPLETE);
            } else {
                if(InspectorOrders!=null){
                    for(int i = 0;i<InspectorOrders.size();i++){
                        if(InspectorOrders.get(i).getDetectResult()==null||InspectorOrders.get(i).getDetectResult().equals("")){
                            ToastUtil.toastShort("还有数据项未填写！");
                            getProgressDialog().dismiss();
                            return;
                        }
                    }
                    submitEditItemNew(0, TYPE_COMPLETE);
                }else {
                    if(InspectorOrderstemp!=null){
                        for(int i = 0;i<InspectorOrderstemp.size();i++){
                            if(InspectorOrderstemp.get(i).getDetectResult()==null||InspectorOrderstemp.get(i).getDetectResult().equals("")){
                                ToastUtil.toastShort("还有数据项未填写！");
                                getProgressDialog().dismiss();
                                return;
                            }
                        }
                        submitEditItemNew(0, TYPE_COMPLETE);
                    }
                }
            }
        }else {
            getProgressDialog().dismiss();
        }
    }

    @Override
    public void SubmitEditFaild(String msg) {
        ToastUtil.toastShort(msg);
        getProgressDialog().dismiss();
    }

    @Override
    public void OnGetImagesSuccess(List<FileBaseBean> images) {
        this.images.clear();
        imagesItems.clear();
        if (images.size() > 0) {
            for (FileBaseBean str : images) {
                String strs = Utils.getImageUrl(str.getFileUrl());
                this.images.add(strs);
            }
            if (images.size() > 0) {
                for (int i = 0; i < images.size(); i++) {
                    ImageItem item = new ImageItem();
                    item.path = Utils.getImageUrl(images.get(i).getFileUrl());
                    item.name = images.get(i).getFileIdx();
                    imagesItems.add(item);
                }
            }
        }
        this.images.add("0");
        imagesItems.clear();
        imagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnGetImagesFaild(String msg) {
        this.images.clear();
        this.images.add("0");
        imagesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0) {
                    imagesItems.addAll(images);
                    String idx = "";
                    if (InspectorOrders.size() > 0) {
                        idx = InspectorOrders.get(position).getTaskIdx();
                    } else {
                        idx = InspectorOrderstemp.get(0).getTaskIdx();
                    }

                    getProgressDialog().show();
                    mPresenter.UpLoadImage(images.get(0), idx);
//                    ImagePicker.getInstance().getSelectedImages().addAll(images);
//                    ImagePicker.getInstance().getSelectImageCount();
                    this.images.remove(this.images.size() - 1);
                    for (int i = 0; i < images.size(); i++) {
                        this.images.add(images.get(i).path);
                    }
                    if (state.equals("0") || state.equals("order")) {
                        this.images.add("0");
                    }
                    imagesAdapter.notifyDataSetChanged();
                }
            } else if (requestCode == ImagePicker.REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra("images");
                imagesItems.clear();
                if (images != null && images.size() > 0) {
                    imagesItems.addAll(images);
                    this.images.clear();
                    for (int i = 0; i < images.size(); i++) {
                        this.images.add(images.get(i).path);
                    }
                    if (state.equals("0") || state.equals("order")) {
                        this.images.add("0");
                    }
                    imagesAdapter.notifyDataSetChanged();
                } else {
                    this.images.clear();
                    if (state.equals("0")|| state.equals("order")) {
                        this.images.add("0");
                    }
                    imagesAdapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btLast:
                Bundle bundle1 = new Bundle();
                bundle1.putString("type", dictname);
                bundle1.putString("ShortName", type);
                bundle1.putString("trainTypeIDX", typeIdx);
                bundle1.putString("TrainNo", trainNo);
                bundle1.putString("typeId", dictid);
                bundle1.putString("state", "0");
                bundle1.putString("filter", filter2);
                if (!TextUtils.isEmpty(flbm))
                    bundle1.putString("flbm", flbm);
                if (!TextUtils.isEmpty(coid))
                    bundle1.putString("coid", coid);
                if (!TextUtils.isEmpty(wzqm))
                    bundle1.putString("wzqm", wzqm);
                ActivityUtil.startActivityResultWithDelayed(WorkOrderActivity.this, TicketInfoActivity.class, bundle1, 200);
//                if (isTitle) {
//                    if(llTakePhoto.getVisibility()==View.VISIBLE){
//                        if(images.size()<=1){
//                            ToastUtil.toastShort("还未拍摄照片");
//                            return;
//                        }
//                    }
//                    InspectorOrderstemp.get(0).setRepairResult(tvTitleResult.getText().toString());
////                    submitEditItemNew(EquipPosition);
//                } else {
//                    if(llTakePhoto.getVisibility()==View.VISIBLE){
//                        if(images.size()<=1){
//                            ToastUtil.toastShort("还未拍摄照片");
//                            return;
//                        }
//                    }
//                    for (int i = 0; i < InspectorOrders.size(); i++) {
//                        if (InspectorOrders.get(i).getDetectResult() == null || InspectorOrders.get(i).getDetectResult().equals("")) {
//                            ToastUtil.toastShort("有必填项未填写值");
//                            return;
//                        }
//                    }
////                    if(InspectorOrders.size()>0){
////                        for(int i = 0;i<InspectorOrders.size();i++){
////                            if(InspectorOrders.get(i).getItemResultCount()>0){
////                                submitEditItem(i);
////                            }
////                        }
////                    }
//                }
//                isAllfinish = true;
//                if (adapter.getNowPositiong() != -1) {
//                    submitEditItem(adapter.getNowPositiong());
//                }
//                setLastItem();

                break;
            case R.id.btNext:
                if (isTitle) {
//                    if(llTakePhoto.getVisibility()==View.VISIBLE){
//                        if(images.size()<=1){
//                            ToastUtil.toastShort("还未拍摄照片");
//                            return;
//                        }
//                    }
                    InspectorOrderstemp.get(0).setRepairResult(tvTitleResult.getText().toString());
//                    submitEditItemNew(EquipPosition);
                } else {
//                    if(llTakePhoto.getVisibility()==View.VISIBLE){
//                        if(images.size()<=1){
//                            ToastUtil.toastShort("还未拍摄照片");
//                            return;
//                        }
//                    }
                    for (int i = 0; i < InspectorOrders.size(); i++) {
                        if (InspectorOrders.get(i).getDetectResult() == null || InspectorOrders.get(i).getDetectResult().equals("")) {
                            ToastUtil.toastShort("有必填项未填写值");
                            return;
                        }
                    }
                }
                isAllfinish = true;
                if (position == orderBeans.size() - 1) {
                    status = TYPE_COMPLETE;
                    if (isTitle) {
                        submitEditItemNew(EquipPosition, TYPE_COMPLETE);
                    } else {
                        if (adapter.getNowPositiong() != -1) {
                            submitEditItem(adapter.getNowPositiong());
                        }
                    }
                } else {
                    status = TYPE_NEXT;
                    if (adapter.getNowPositiong() != -1) {
                        submitEditItem(adapter.getNowPositiong());
                    }
                    setNextItem();
                }
                break;
        }
    }
}
