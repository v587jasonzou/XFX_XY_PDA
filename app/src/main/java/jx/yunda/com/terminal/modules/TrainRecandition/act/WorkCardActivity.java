package jx.yunda.com.terminal.modules.TrainRecandition.act;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import jx.yunda.com.terminal.modules.TrainRecandition.adapter.CardEquipAdapter;
import jx.yunda.com.terminal.modules.TrainRecandition.adapter.WorkCardAdapter;
import jx.yunda.com.terminal.modules.TrainRecandition.model.OrderBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.WorkCardBean;
import jx.yunda.com.terminal.modules.TrainRecandition.presenter.IWorkCardPresenter;
import jx.yunda.com.terminal.modules.TrainRecandition.presenter.IworkCard;
import jx.yunda.com.terminal.modules.receiveTrain.fragment.ReceiveTrainOperationActivity;
import jx.yunda.com.terminal.modules.tpApproval.TPApprovalActivity;
import jx.yunda.com.terminal.modules.tpprocess.ImagesAdapter;
import jx.yunda.com.terminal.modules.tpprocess.RealmDb.ImageDao;
import jx.yunda.com.terminal.modules.tpprocess.TicketInfoActivity;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.NetWorkUtils;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.PhotoReadActivity;
import jx.yunda.com.terminal.widget.SodukuGridView;
import jx.yunda.com.terminal.widget.SwitchView;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @Description:作业任务卡界面
 * @author: 周雪巍
 * @time: 2018/7/23 10:50
 **/
public class WorkCardActivity extends BaseActivity<IWorkCardPresenter> implements IworkCard {
    @BindView(R.id.switchView)
    SwitchView switchView; //开关控件（必填项/所有项）
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvWorkNum)
    TextView tvWorkNum;
    @BindView(R.id.tvCardNum)
    TextView tvCardNum;
    @BindView(R.id.EquipName)
    TextView EquipName;
    @BindView(R.id.lvList)
    RecyclerView lvList;
    @BindView(R.id.btNext)
    Button btNext;
    @BindView(R.id.llEmpty)
    LinearLayout llEmpty;
    @BindView(R.id.tvNodeName)
    TextView tvNodeName;
    @BindView(R.id.btJianDin)
    Button btJianDin;
    @BindView(R.id.btPass)
    Button btPass;
    WorkCardAdapter cardAdapter;
    List<WorkCardBean> cardBeans = new ArrayList<>();
    List<OrderBean> orderBeans = new ArrayList<>();
    List<OrderBean> orderBeansAll = new ArrayList<>();
    List<OrderBean> orderBeansMust = new ArrayList<>();

    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    Dialog dialog;
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
    String professionTypeIdx;
    int cardseq = 0;
    int statu = 1;
    boolean isBack = false;
    boolean isComplet = false;
    public static int IMAGE_PICKER = 666;
    public static final String JPG_EXTNAME = ".jpg";
    int ImagesTimes = 0;
    int AllImagesTimes = 0;
    int Restimes = 0;
    ArrayList<String> images = new ArrayList<>();
    ArrayList<ImageItem> imagesTemp = new ArrayList<>();
    ImagesAdapter adapter;
    String UUID;
    String state = "0";
    @BindView(R.id.gvImages)
    SodukuGridView gvImages;
    @BindView(R.id.llUpLoadImages)
    LinearLayout llUpLoadImages;
    @BindView(R.id.btAddTicket)
    Button btAddTicket;
    @BindView(R.id.llCard)
    LinearLayout llCard;
    @BindView(R.id.tvStart)
    TextView tvStart;
    @Override
    protected IWorkCardPresenter getPresenter() {
        return new IWorkCardPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activty_train_recandition_card;
    }

    @Override
    public void initSubViews(View view) {

    }

    List<ImageDao> unLoadImages;

    @Override
    public void initData() {
        final Bundle bundle = getIntent().getExtras();
        UUID = Utils.getUUID();
        images.add("0");
        adapter = new ImagesAdapter(this, images);
        adapter.setState("0");
        gvImages.setAdapter(adapter);
        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == images.size() - 1) {
                    Intent intent = new Intent(WorkCardActivity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                    startActivityForResult(intent, IMAGE_PICKER);
                } else {
                    Bundle bundle1 = new Bundle();
                    Intent intent = new Intent(WorkCardActivity.this, PhotoReadActivity.class);
                    bundle1.putSerializable("images", imagesTemp);
                    bundle1.putInt("position", position);
                    bundle1.putString("state", state);
                    intent.putExtras(bundle1);
                    startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
                }

            }
        });
        idx = bundle.getString("nodeidx");
        type = bundle.getString("type");
        trainNo = bundle.getString("trainNo");
        nodeName = bundle.getString("nodeName");
        typeIdx = bundle.getString("typeIdx");
        dictid = bundle.getString("dictid");
        dictname = bundle.getString("dictname");
        filter2 = bundle.getString("filter2");
        professionTypeIdx = bundle.getString("professionTypeIdx");
        if(!TextUtils.isEmpty(professionTypeIdx)){
            btJianDin.setVisibility(View.VISIBLE);
            btJianDin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("trainNo",trainNo);
                    bundle1.putString("professionTypeIdx",professionTypeIdx);
                    bundle1.putString("name",type);
                    ActivityUtil.startActivityResultWithDelayed(WorkCardActivity.this, TPApprovalActivity.class, bundle1, 202);
                }
            });
        }

        btAddTicket.setVisibility(View.VISIBLE);
        if (nodeName != null) {
            tvNodeName.setText(nodeName);
        }
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        menuTp.setTitle(type + " " + trainNo);
        srRefresh.setRefreshHeader(new ClassicsHeader(this));
        srRefresh.setRefreshFooter(new ClassicsFooter(this));
        srRefresh.setNoMoreData(true);
        srRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                setData();
            }
        });
        srRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {

            }
        });
        //使用线性布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lvList.setLayoutManager(layoutManager);
        lvList.setHasFixedSize(true);
//        lvList.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));//用类设置分割线
//Rv.addItemDecoration(new DividerItemDecoration(this, R.drawable.list_divider)); //用已有图片设置分割线
        //为ListView绑定适配器
        cardAdapter = new WorkCardAdapter(this, orderBeans);
        lvList.setAdapter(cardAdapter);
        cardAdapter.setOnItemClickListner(new WorkCardAdapter.OnItemClickListner() {
            @Override
            public void OnItemClick(int position) {
                Bundle bundle1 = new Bundle();
                bundle.putSerializable("cardBeans", (Serializable) orderBeans);
                bundle.putInt("position", position);
                if (!TextUtils.isEmpty(idx))
                    bundle.putString("nodeidx", idx);
                if (!TextUtils.isEmpty(type))
                    bundle.putString("type", type);
                if (!TextUtils.isEmpty(trainNo))
                    bundle.putString("trainNo", trainNo);
                if (!TextUtils.isEmpty(nodeName))
                    bundle.putString("nodeName", nodeName);
                if (!TextUtils.isEmpty(typeIdx))
                    bundle.putString("typeIdx", typeIdx);
                if (!TextUtils.isEmpty(dictid))
                    bundle.putString("dictid", dictid);
                if (!TextUtils.isEmpty(dictname))
                    bundle.putString("dictname", dictname);
                if (!TextUtils.isEmpty(filter2))
                    bundle.putString("filter2", filter2);
                if (!TextUtils.isEmpty(flbm))
                    bundle.putString("flbm", flbm);
                if (!TextUtils.isEmpty(coid))
                    bundle.putString("coid", coid);
                if (!TextUtils.isEmpty(wzqm))
                    bundle.putString("wzqm", wzqm);
                ActivityUtil.startActivityResultWithDelayed(WorkCardActivity.this, WorkOrderActivity.class, bundle, 200);
            }
        });
        switchView.setSwitchText("必填项", "所有项");
        switchView.setOnSwitchChangeListner(new SwitchView.OnSwitchChangeListner() {
            @Override
            public void OnSwichChanged(int state) {
                if (state == 0) {
                    statu = 0;
                } else {
                    statu = 1;
                }
                setData();
            }
        });
        setData();
        llEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }
        });
        btPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderBeansAll.size()>0&&orderBeansAll.get(0).getWorkCardIdx()!=null){
                    getProgressDialog().show();
                    mPresenter.PassWorkCard(orderBeansAll.get(0).getWorkCardIdx());
                }else {
                    ToastUtil.toastShort("当前数据无作业卡ID");
                }
            }
        });
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderBeansMust.size() > 0) {
                    for (OrderBean bean : orderBeansMust) {
                        if (!bean.getStatus().equals("COMPLETE")) {
                            ToastUtil.toastShort("还有必填项未填，请填写后提交");
                            return;
                        }
                    }
                    isComplet = true;
                    getProgressDialog().show();
                    mPresenter.SubmitWorkCard(SysInfo.userInfo.emp.getOperatorid(), orderBeansAll.get(0).getWorkCardIdx());
                } else {
                    if(imagesTemp.size()==0){
                        for (OrderBean bean : orderBeansAll) {
                            if (!bean.getStatus().equals("COMPLETE")&&"1".equals(bean.getIsPhotograph()) ) {
                                ToastUtil.toastShort("请先拍摄照片！");
                                return;
                            }
                        }
                    }
                    for (OrderBean bean : orderBeansAll) {
                        if (!bean.getStatus().equals("COMPLETE")&&bean.getDetectCount()!=null&&Integer.parseInt(bean.getDetectCount())>0) {
                            ToastUtil.toastShort("还有必填项未填，请填写后提交");
                            return;
                        }
                    }
                    if (orderBeansAll.size() > 0||!TextUtils.isEmpty(professionTypeIdx)) {
                        isComplet = true;
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
                                                    if(TextUtils.isEmpty(professionTypeIdx)){
                                                        mPresenter.UpLoadImages(base64, JPG_EXTNAME, unLoadImages.get(0).getIdx(), 0);
                                                    }else {
                                                        mPresenter.UpLoadNodeImages(base64, JPG_EXTNAME, unLoadImages.get(0).getIdx(), 0);
                                                    }

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
                                            if(TextUtils.isEmpty(professionTypeIdx)){
                                                mPresenter.SubmitWorkCard(SysInfo.userInfo.emp.getOperatorid(), orderBeansAll.get(0).getWorkCardIdx(), jsonstr2);
                                            }else {
                                                mPresenter.SubmitNode(idx, jsonstr2);
                                            }

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
                                                if(TextUtils.isEmpty(professionTypeIdx)){
                                                    mPresenter.UpLoadImages(base64, JPG_EXTNAME, 0, 0);
                                                }else {
                                                    mPresenter.UpLoadNodeImages(base64, JPG_EXTNAME, 0, 0);
                                                }
                                            }
                                        }
                                    }
                                });
                            }

                        } else {
                            if(TextUtils.isEmpty(professionTypeIdx)){
                                mPresenter.SubmitWorkCard(SysInfo.userInfo.emp.getOperatorid(), orderBeansAll.get(0).getWorkCardIdx());
                            }else {
                                mPresenter.SubmitNode(idx,"");
                            }

                        }

                    } else {
                        isComplet = true;
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
                                                    mPresenter.UpLoadNodeImages(base64, JPG_EXTNAME, unLoadImages.get(0).getIdx(), 0);

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
                                            mPresenter.SubmitNode(idx, jsonstr2);

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
                                                mPresenter.UpLoadNodeImages(base64, JPG_EXTNAME, 0, 0);
                                            }
                                        }
                                    }
                                });
                            }

                        } else {
                            mPresenter.SubmitNode(idx,"");

                        }
//                        ToastUtil.toastLong("已完成所有任务卡检修");
//                        setResult(203);
//                        finish();
                    }

                }
            }
        });
        btAddTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                ActivityUtil.startActivityResultWithDelayed(WorkCardActivity.this, TicketInfoActivity.class, bundle1, 200);
            }
        });
    }

    private void getOrdersort() {
        getProgressDialog().show();
        mPresenter.getWorkCardSort(idx);
    }

    private void setData() {
        if (idx != null && !"".equals(idx)) {
            getProgressDialog().show();
            if (cardseq > 0) {
                mPresenter.CompletePlan(idx, cardseq - 1, workCardIdx);
            } else {
                mPresenter.CompletePlan(idx, cardseq, workCardIdx);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toobar跟menu
        getMenuInflater().inflate(R.menu.menu_card, menu);
//        menu.getItem(0).setTitle("处理记录");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.iv_task) {
            mPresenter.getWorkCardSort(idx);
        }
        return super.onOptionsItemSelected(item);
    }

    ImageView ivDelete;
    SmartRefreshLayout srCard;
    RecyclerView lvEquip;
    CardEquipAdapter dialogAdapter;
    String workCardIdx;

    private void ShowDialog() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.dialog);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                }
            });

            LayoutInflater inflater = LayoutInflater.from(this);
            View viewDialog = inflater.inflate(R.layout.dialog_cardwork, null);

            Window window = dialog.getWindow();
            window.setWindowAnimations(R.style.mystyle);  //添加动画
            dialog.setContentView(viewDialog);
            ivDelete = (ImageView) viewDialog.findViewById(R.id.ivDelete);
            srCard = (SmartRefreshLayout) viewDialog.findViewById(R.id.srCard);
            lvEquip = (RecyclerView) viewDialog.findViewById(R.id.lvEquip);
            dialogAdapter = new CardEquipAdapter(this, cardBeans);
            //使用线性布局
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            lvEquip.setLayoutManager(layoutManager);
            lvEquip.setHasFixedSize(true);
            lvEquip.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));//用类设置分割线
//Rv.addItemDecoration(new DividerItemDecoration(this, R.drawable.list_divider)); //用已有图片设置分割线
            //为ListView绑定适配器
            lvEquip.setAdapter(dialogAdapter);
            dialogAdapter.setOnItemClickListner(new CardEquipAdapter.OnItemClickListner() {
                @Override
                public void OnItemClick(int position) {
                    ToastUtil.toastShort("点击第" + position);
                    tvCardNum.setText(cardBeans.get(position).getWorkCardSeq());
                    EquipName.setText(cardBeans.get(position).getWorkCardName());
                    dialog.dismiss();
                    if (cardBeans.get(position).getWorkCardSeq() != null) {
                        cardseq = Integer.parseInt(cardBeans.get(position).getWorkCardSeq());
                    }
                    workCardIdx = cardBeans.get(position).getIdx();
                    setData();
                }
            });
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    @Override
    public void LoadDataSuccess(List<OrderBean> list) {
        orderBeans.clear();
        orderBeansAll.clear();
        orderBeansMust.clear();
        if (list != null && list.size() > 0) {
            orderBeansAll.addAll(list);
//            for (OrderBean bean : list) {
//                if (bean.getDetectCount() != null && Integer.parseInt(bean.getDetectCount()) > 0) {
//                    orderBeansMust.add(bean);
//                } else if (bean.getIsPhotograph() != null && bean.getIsPhotograph().equals("1")) {
//                    orderBeansMust.add(bean);
//                }
//            }
            llCard.setVisibility(View.VISIBLE);
            tvWorkNum.setVisibility(View.VISIBLE);
            switchView.setVisibility(View.GONE);
            EquipName.setText(list.get(0).getWorkCardName());
            tvWorkNum.setText(list.get(0).getAllCount() + "");
            tvCardNum.setText(list.get(0).getWorkCardSeq());
            if (list.get(0).getFlbm() != null) {
                flbm = list.get(0).getFlbm();
            }
            if (list.get(0).getCoid() != null) {
                coid = list.get(0).getCoid();
            }
            if (list.get(0).getWzqm() != null) {
                wzqm = list.get(0).getWzqm();
            }
        }
        boolean isPass = false;
        for(OrderBean order:orderBeansAll){
            if(order.getExclusionWorkCard()!=null){
                isPass = true;
                break;
            }
        }
        if(isPass){
            btPass.setVisibility(View.VISIBLE);
        }else {
            btPass.setVisibility(View.GONE);
        }
        if (statu == 1) {
            if (orderBeansAll.size() > 0) {
                orderBeans.addAll(orderBeansAll);
            }
        } else {
            if (orderBeansMust.size() > 0) {
                orderBeans.addAll(orderBeansMust);
            }
        }
        srRefresh.finishRefresh();
        llUpLoadImages.setVisibility(View.VISIBLE);
        if (orderBeans.size() == 0) {
            if (statu == 0 || professionTypeIdx != null) {
                llEmpty.setVisibility(View.GONE);
            } else {
                llEmpty.setVisibility(View.VISIBLE);
            }

        } else {
            llEmpty.setVisibility(View.GONE);
        }
        int temp = 0;
        for(OrderBean orderBean:orderBeansAll){
            if(orderBean.getIsPhotograph()!=null&&"1".equals(orderBean.getIsPhotograph())){
                temp++;
            }
        }
        if(temp==0){
            tvStart.setVisibility(View.GONE);
        }else {
            tvStart.setVisibility(View.VISIBLE);
        }
        cardAdapter.notifyDataSetChanged();
        getProgressDialog().dismiss();
        if (isComplet) {
            if (orderBeansAll.size() == 0) {
                ToastUtil.toastLong("已完成所有任务卡检修");
                setResult(203);
                finish();
            }
        }
        isComplet = false;
    }

    @Override
    public void LoadDataFaild(String msg) {
        srRefresh.finishRefresh();
        ToastUtil.toastShort(msg);
        getProgressDialog().dismiss();
    }

    @Override
    public void LoadMoreDataSuccess() {

    }

    @Override
    public void LoadMoreDataFaild(String msg) {

    }

    @Override
    public void SubmitSuccess() {
        ToastUtil.toastShort("提交成功");
        if(TextUtils.isEmpty(professionTypeIdx)){
            workCardIdx = "";
            UUID = Utils.getUUID();
            if(images!=null)
            images.clear();
            if(imagesTemp!=null)
            imagesTemp.clear();
//            if(unLoadImages!=null)
//            unLoadImages.clear();
            images.add("0");
            adapter.notifyDataSetChanged();
            setData();
        }else {
            finish();
        }

    }

    @Override
    public void SubmitFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void ChangeOrderSuccess() {

    }

    @Override
    public void ChangeOrderFaild(String msg) {

    }

    @Override
    public void getCardsSortSuccess(List<WorkCardBean> list) {
        getProgressDialog().dismiss();
        cardBeans.clear();
        if (list != null && list.size() > 0) {
            cardBeans.addAll(list);
        }
        ShowDialog();
        dialogAdapter.notifyDataSetChanged();
    }

    @Override
    public void getCardsSortFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

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
                                if(TextUtils.isEmpty(professionTypeIdx)){
                                    mPresenter.UpLoadImages(base64, JPG_EXTNAME, unLoadImages.get(ImagesTimes + 1).getIdx(), ImagesTimes + 1);
                                }else {
                                    mPresenter.UpLoadNodeImages(base64, JPG_EXTNAME, unLoadImages.get(ImagesTimes + 1).getIdx(), ImagesTimes + 1);
                                }

                                ImagesTimes++;
                            } else {
                                String base64 = Utils.getBase64StringFromImg(imagesTemp.get(ImagesTimes + 1).path);
                                if(TextUtils.isEmpty(professionTypeIdx)&&orderBeansAll.size()>0){
                                    mPresenter.UpLoadImages(base64, JPG_EXTNAME, ImagesTimes + 1, ImagesTimes + 1);
                                }else {
                                    mPresenter.UpLoadNodeImages(base64, JPG_EXTNAME, ImagesTimes + 1, ImagesTimes + 1);
                                }

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
                                                            if(TextUtils.isEmpty(professionTypeIdx)&&orderBeansAll.size()>0){
                                                                mPresenter.SubmitWorkCard(SysInfo.userInfo.emp.getOperatorid(), orderBeansAll.get(0).getWorkCardIdx(), s);
                                                            }else {
                                                                mPresenter.SubmitNode(idx, s);
                                                            }

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
                                                        if (NetWorkUtils.isWifiConnected(WorkCardActivity.this)) {
                                                            new AlertDialog.Builder(WorkCardActivity.this).setTitle("提示！")
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
                                                                                    if(TextUtils.isEmpty(professionTypeIdx)&&orderBeansAll.size()>0){
                                                                                        mPresenter.UpLoadImages(base64, JPG_EXTNAME, unLoadImages.get(0).getIdx(), 0);
                                                                                    }else {
                                                                                        mPresenter.UpLoadNodeImages(base64, JPG_EXTNAME, unLoadImages.get(0).getIdx(), 0);
                                                                                    }

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
                    if (TextUtils.isEmpty(professionTypeIdx)&&orderBeansAll.size()>0) {
                        mPresenter.UpLoadImages(base64, JPG_EXTNAME, unLoadImages.get(ImagesTimes + 1).getIdx(), ImagesTimes + 1);
                    }else {
                        mPresenter.UpLoadNodeImages(base64, JPG_EXTNAME, unLoadImages.get(ImagesTimes + 1).getIdx(), ImagesTimes + 1);
                    }
                    ImagesTimes++;
                } else {
                    String base64 = Utils.getBase64StringFromImg(imagesTemp.get(ImagesTimes + 1).path);
                    if(TextUtils.isEmpty(professionTypeIdx)&&orderBeansAll.size()>0){
                        mPresenter.UpLoadImages(base64, JPG_EXTNAME, ImagesTimes + 1, ImagesTimes + 1);
                    }else {
                        mPresenter.UpLoadNodeImages(base64, JPG_EXTNAME, ImagesTimes + 1, ImagesTimes + 1);
                    }

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
                                        if (NetWorkUtils.isWifiConnected(WorkCardActivity.this)) {
                                            new AlertDialog.Builder(WorkCardActivity.this).setTitle("提示！")
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
                                                                    if (TextUtils.isEmpty(professionTypeIdx)) {
                                                                        mPresenter.UpLoadImages(base64, JPG_EXTNAME, unLoadImages.get(0).getIdx(), 0);
                                                                    }else {
                                                                        mPresenter.UpLoadNodeImages(base64, JPG_EXTNAME, unLoadImages.get(0).getIdx(), 0);
                                                                    }

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 201) {
            isBack = true;
            isComplet = true;
            setData();
        } else if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
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
}
