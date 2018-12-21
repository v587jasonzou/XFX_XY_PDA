package jx.yunda.com.terminal.modules.FJ_ticket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import jx.yunda.com.terminal.modules.FJ_ticket.model.RecheckSubmitDao;
import jx.yunda.com.terminal.modules.FJ_ticket.presenter.ISubmitRecheck;
import jx.yunda.com.terminal.modules.FJ_ticket.presenter.MakeTicketRecheckPresenter;
import jx.yunda.com.terminal.modules.FJ_ticket.presenter.SubmitCheckPresenter;
import jx.yunda.com.terminal.modules.tpprocess.ITicketInfoPresenter;
import jx.yunda.com.terminal.modules.tpprocess.ImagesAdapter;
import jx.yunda.com.terminal.modules.tpprocess.RealmDb.ImageDao;
import jx.yunda.com.terminal.modules.tpprocess.RealmDb.TicketSubmitDao;
import jx.yunda.com.terminal.modules.tpprocess.TicketInfoActivity;
import jx.yunda.com.terminal.modules.tpprocess.TicketListActivity;
import jx.yunda.com.terminal.modules.tpprocess.adapter.TreeViewAdapter;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultBean;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultTreeBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTypeBean;
import jx.yunda.com.terminal.modules.tpprocess.rxjavaforrealm.RealmObservable;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.NetWorkUtils;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.PhotoReadActivity;
import jx.yunda.com.terminal.widget.SodukuGridView;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SubmitRecheckActivity extends BaseActivity<SubmitCheckPresenter> implements ISubmitRecheck, View.OnClickListener, TreeViewAdapter.OnImageClickLisnter {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvTicketName)
    TextView tvTicketName;
    @BindView(R.id.tvBJName)
    TextView tvBJName;
    @BindView(R.id.tvZYName)
    TextView tvZYName;
    @BindView(R.id.etGZXXInfo)
    EditText etGZXXInfo;
    @BindView(R.id.gvImages)
    SodukuGridView gvImages;
    @BindView(R.id.btNext)
    Button btNext;
    ArrayList<String> images = new ArrayList<>();
    ArrayList<ImageItem> imagesTemp = new ArrayList<>();
    List<FaultTreeBean> Faults = new LinkedList<>();
    ImagesAdapter adapter;
    TreeViewAdapter treeViewAdapter;
    String type, ShortName, TrainNo, typeId, trainTypeIDX, state, faultFixFullName, zhuanye, kaohe, faultDesc, resolutionContent, idx, UUID, zhuanyeId, kaoheId, faultFixPlaceIDX, fixPlaceFullCode;
    long faultOccurDate = 0;
    AlertDialog dialog;
    ListView faultsListView;
    public static int IMAGE_PICKER = 666;
    public static final String JPG_EXTNAME = ".jpg";
    int ImagesTimes = 0;
    int AllImagesTimes = 0;
    int Restimes = 0;
    List<FaultBean> nowFaults = new ArrayList<>();
    PopupMenu popupMenu;
    String nowtext ="";
    @Override
    protected SubmitCheckPresenter getPresenter() {
        return new SubmitCheckPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_submit_recheck;
    }

    @Override
    public void initSubViews(View view) {

    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        ShortName = bundle.getString("ShortName");
        TrainNo = bundle.getString("TrainNo");
        trainTypeIDX = bundle.getString("trainTypeIDX");
        idx = bundle.getString("ticketTrainIDX");
        UUID = Utils.getUUID();
        images.add("0");
        tvTicketName.setText(ShortName + " " + TrainNo);
        tvBJName.setOnClickListener(this);
        tvZYName.setOnClickListener(this);
        btNext.setOnClickListener(this);
        popupMenu = new PopupMenu(this, etGZXXInfo);
        setEditText();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                etGZXXInfo.setText(item.getTitle());
                nowtext = item.getTitle().toString();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(etGZXXInfo.getWindowToken(), 0);
                }
                popupMenu.dismiss();
                for(FaultBean bean : nowFaults){
                    if(bean.getFaultName().equals(nowtext)){
                        etGZXXInfo.setTag(bean.getFaultId());
                        break;
                    }
                }
                return false;
            }
        });
        adapter = new ImagesAdapter(this, images);
//        adapter.setState(state);
        setSupportActionBar(menuTp);
        initDialog();
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gvImages.setAdapter(adapter);
        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == images.size() - 1) {
                    Intent intent = new Intent(SubmitRecheckActivity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                    startActivityForResult(intent, IMAGE_PICKER);
                } else {
                    Bundle bundle1 = new Bundle();
                    Intent intent = new Intent(SubmitRecheckActivity.this, PhotoReadActivity.class);
                    bundle1.putSerializable("images", imagesTemp);
                    bundle1.putInt("position", position);
                    bundle1.putString("state", "0");
                    intent.putExtras(bundle1);
                    startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
                }
            }
        });
    }

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

    private void setEditText() {
        etGZXXInfo.setOnFocusChangeListener(new android.view.View.
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
                        android.view.Menu menu_more = popupMenu.getMenu();
                        menu_more.clear();
                        int size = temps.size();
                        for (int i = 0; i < size; i++) {
                            menu_more.add(android.view.Menu.NONE, android.view.Menu.FIRST + i, i, temps.get(i).getFaultName());
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
                if(!nowtext.equals(s.toString())){
                    etGZXXInfo.setTag(null);
                }
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
                    android.view.Menu menu_more = popupMenu.getMenu();
                    menu_more.clear();
                    int size = temps.size();
                    for (int i = 0; i < size; i++) {
                        menu_more.add(android.view.Menu.NONE, android.view.Menu.FIRST + i, i, temps.get(i).getFaultName());
                    }
                    popupMenu.show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private boolean isAllselecyesd() {
        if (tvBJName.getTag() == null || "".equals(tvBJName.getTag().toString())) {
            Toast.makeText(this, "不能提交，还未选择部件位置！", Toast.LENGTH_SHORT).show();
            getProgressDialog().dismiss();
            return false;
        }
        if (tvZYName.getTag() == null || "".equals(tvZYName.getTag().toString())) {
            Toast.makeText(this, "不能提交，还未选择类别！", Toast.LENGTH_SHORT).show();
            getProgressDialog().dismiss();
            return false;
        }
        if (etGZXXInfo.getText() == null || "".equals(etGZXXInfo.getText().toString())) {
            Toast.makeText(this, "不能提交，还未填写不良现象！", Toast.LENGTH_SHORT).show();
            getProgressDialog().dismiss();
            return false;
        }
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
                    this.images.add("0");
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
                    this.images.add("0");
                    adapter.notifyDataSetChanged();
                } else {
                    this.images.clear();
                    this.images.add("0");
                    adapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void OnLoacationLoadsuccess(int position, List<FaultTreeBean> faultTreeBeanList) {
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
    public void OnLoacationLoadFaild(String msg) {
        ToastUtil.toastShort(msg);
    }

    List<ImageDao> unLoadImages;

    @Override
    public void OnTypeLoadSuccess(final List<TicketTypeBean> list) {
        OptionsPickerView options = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvZYName.setText(list.get(options1).getDictname());
                tvZYName.setTag(list.get(options1).getDictid());
            }
        }).setTitleText("选择类别").setSubmitText("确定").build();
        List<String> list1 = new ArrayList<>();
        for (TicketTypeBean bean : list) {
            list1.add(bean.getDictname());
        }
        options.setPicker(list1);
        options.show();
    }

    @Override
    public void OnTypeLoadFaild(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void OnUpLoadImagesFaild(String msg, final int position, final int imageNo) {
        Restimes++;
        if(imageNo<AllImagesTimes-1){
            if(unLoadImages!=null&&unLoadImages.size()>0){
                String base64 = Utils.getBase64StringFromImg(unLoadImages.get(ImagesTimes+1).getFilePath());
                mPresenter.UpLoadImages(base64, JPG_EXTNAME, unLoadImages.get(ImagesTimes+1).getIdx(),ImagesTimes+1);
                ImagesTimes++;
            }else {
                String base64 = Utils.getBase64StringFromImg(imagesTemp.get(ImagesTimes+1).path);
                mPresenter.UpLoadImages(base64, JPG_EXTNAME, ImagesTimes+1,ImagesTimes+1);
                ImagesTimes++;
            }
        }else {
            if(Restimes==AllImagesTimes){
                Observable
                        .create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {
                                Log.e("Realm", "realm add start");
                                Log.e("当前线程", Thread.currentThread().getName() + "--" + Thread.currentThread().getId() + "");
                                unLoadImages = Realm.getDefaultInstance().where(ImageDao.class)
                                        .equalTo("isUpLoad", false).
                                                equalTo("planIdx",UUID).findAll();
                                if (unLoadImages == null || unLoadImages.size() == 0) {
                                    subscriber.onNext(0);
                                } else {
                                    subscriber.onNext(unLoadImages.size());
                                }
                            }

                        })
                        .subscribeOn(Schedulers.from(JXApplication.getExecutor()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(final Integer aVoid) {
                                if (aVoid == 0) {

                                } else {
                                    if(NetWorkUtils.isWifiConnected(SubmitRecheckActivity.this)){
                                        new AlertDialog.Builder(SubmitRecheckActivity.this).setTitle("提示！")
                                                .setMessage("当前有" + aVoid + "张图片未上传成功，是否重试或保存本地？")
                                                .setPositiveButton("重试", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        getProgressDialog().show();
                                                        Restimes = 0;
                                                        AllImagesTimes = unLoadImages.size();
                                                        ImagesTimes = 0;
                                                        String base64 = Utils.getBase64StringFromImg(unLoadImages.get(0).getFilePath());
                                                        mPresenter.UpLoadImages(base64, JPG_EXTNAME, unLoadImages.get(0).getIdx(),0);
                                                    }
                                                }).setNegativeButton("取消",null
//                                                new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
//                                                    @Override
//                                                    public void call(final Subscriber<? super Integer> subscriber) {
//                                                        SavaSubmitDao(subscriber);
//                                                    }
//                                                }).subscribeOn(Schedulers.from(JXApplication.getExecutor()))
//                                                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
//                                                    @Override
//                                                    public void onCompleted() {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onError(Throwable e) {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onNext(Integer integer) {
//                                                        if (integer==1){
//                                                            getProgressDialog().dismiss();
//                                                            new AlertDialog.Builder(SubmitRecheckActivity.this).setTitle("成功保存到提票单")
//                                                                    .setMessage("因网络故障，已保存到提票单，您可以在检修过程界面进行查看！")
//                                                                    .setPositiveButton("去查看", new DialogInterface.OnClickListener() {
//                                                                        @Override
//                                                                        public void onClick(DialogInterface dialog, int which) {
//                                                                            Bundle bundle = new Bundle();
//                                                                            bundle.putInt("state",2);
//                                                                            ActivityUtil.startActivityWithDelayed(SubmitRecheckActivity.this,TicketListActivity.class,bundle);
//                                                                        }
//                                                                    }).setNegativeButton("关闭", new DialogInterface.OnClickListener() {
//                                                                @Override
//                                                                public void onClick(DialogInterface dialog, int which) {
//                                                                    new Handler().postDelayed(new Runnable() {
//                                                                        @Override
//                                                                        public void run() {
//                                                                            setResult(300);
//                                                                            finish();
//                                                                        }
//                                                                    }, 2000);
//                                                                }
//                                                            }).show();
//                                                        }
//                                                    }
//                                                });
//                                            }
//                                        }
                                        ).show();
                                    }else {
                                        getProgressDialog().dismiss();
                                        ToastUtil.toastShort("当前网络状态较差，有"+aVoid+"张照片未上传，请重试");
//                                        rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
//                                            @Override
//                                            public void call(final Subscriber<? super Integer> subscriber) {
//                                                SavaSubmitDao(subscriber);
//                                            }
//                                        }).subscribeOn(Schedulers.from(JXApplication.getExecutor()))
//                                                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
//                                            @Override
//                                            public void onCompleted() {
//
//                                            }
//
//                                            @Override
//                                            public void onError(Throwable e) {
//
//                                            }
//
//                                            @Override
//                                            public void onNext(Integer integer) {
//                                                if (integer==1){
//                                                    getProgressDialog().dismiss();
//                                                    new AlertDialog.Builder(SubmitRecheckActivity.this).setTitle("成功保存到提票单")
//                                                            .setMessage("因网络故障，已保存到提票单，您可以在检修过程界面进行查看！")
//                                                            .setPositiveButton("去查看", new DialogInterface.OnClickListener() {
//                                                                @Override
//                                                                public void onClick(DialogInterface dialog, int which) {
//                                                                    Bundle bundle = new Bundle();
//                                                                    bundle.putInt("state",2);
//                                                                    ActivityUtil.startActivityWithDelayed(SubmitRecheckActivity.this,TicketListActivity.class,bundle);
//                                                                }
//                                                            }).setNegativeButton("关闭", new DialogInterface.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(DialogInterface dialog, int which) {
//                                                            new Handler().postDelayed(new Runnable() {
//                                                                @Override
//                                                                public void run() {
//                                                                    setResult(300);
//                                                                    finish();
//                                                                }
//                                                            }, 500);
//                                                        }
//                                                    }).show();
//                                                }
//                                            }
//                                        });
                                    }

                                }
                            }
                        });

            }
        }
    }

    @Override
    public void OnUpLoadImagesSuccess(final String path,final int position, final int imageNo) {
        Restimes++;
        rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                final ImageDao dao = Realm.getDefaultInstance().where(ImageDao.class).equalTo("idx", position)
                        .equalTo("planIdx",UUID)
                        .findFirst();
                if(dao!=null){
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
                        if(imageNo<AllImagesTimes-1){
                            if(unLoadImages!=null&&unLoadImages.size()>0){
                                String base64 = Utils.getBase64StringFromImg(unLoadImages.get(ImagesTimes+1).getFilePath());
                                mPresenter.UpLoadImages(base64, JPG_EXTNAME, unLoadImages.get(ImagesTimes+1).getIdx(),ImagesTimes+1);
                                ImagesTimes++;
                            }else {
                                String base64 = Utils.getBase64StringFromImg(imagesTemp.get(ImagesTimes+1).path);
                                mPresenter.UpLoadImages(base64, JPG_EXTNAME, ImagesTimes+1,ImagesTimes+1);
                                ImagesTimes++;
                            }
                        }else {
                            if(Restimes==AllImagesTimes){
                                rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
                                    @Override
                                    public void call(final Subscriber<? super Integer> subscriber) {
                                        Log.e("Realm", "realm add start");
                                        Log.e("当前线程", Thread.currentThread().getName() + "--" + Thread.currentThread().getId() + "");
                                        unLoadImages = Realm.getDefaultInstance().where(ImageDao.class)
                                                .equalTo("isUpLoad", false)
                                                .equalTo("planIdx",UUID).findAll();
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
                                        if(integer==0){
                                            rx.Observable
                                                    .create(new rx.Observable.OnSubscribe<String>() {
                                                        @Override
                                                        public void call(Subscriber<? super String> subscriber) {
                                                            Log.e("Realm", "realm add start");
                                                            Log.e("当前线程", Thread.currentThread().getName() + "--" + Thread.currentThread().getId() + "");
                                                            ArrayList<Map<String,String>> list = new ArrayList<>();
                                                            List<ImageDao> daos =Realm.getDefaultInstance().copyFromRealm( Realm.getDefaultInstance().where(ImageDao.class)
                                                                    .equalTo("planIdx",UUID)
                                                                    .findAll());
                                                            if(daos!=null&&daos.size()>0){
                                                                for(ImageDao dao:daos){
                                                                    Map<String,String> map = new HashMap<>();
                                                                    map.put("filename",dao.getFilename());
                                                                    map.put("filePath",dao.getFileBackPath());
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
                                            if(NetWorkUtils.isWifiConnected(SubmitRecheckActivity.this)){
                                                new AlertDialog.Builder(SubmitRecheckActivity.this).setTitle("提示！")
                                                        .setMessage("当前有" + integer + "张图片未上传成功，是否重试或保存本地？")
                                                        .setPositiveButton("重试", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                getProgressDialog().show();
                                                                Restimes = 0;
                                                                AllImagesTimes = unLoadImages.size();
                                                                ImagesTimes = 0;
                                                                String base64 = Utils.getBase64StringFromImg(unLoadImages.get(0).getFilePath());
                                                                mPresenter.UpLoadImages(base64, JPG_EXTNAME, unLoadImages.get(0).getIdx(),0);
                                                            }
                                                        }).setNegativeButton("取消",null
//                                                        new DialogInterface.OnClickListener() {
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
//                                                                    new AlertDialog.Builder(SubmitRecheckActivity.this).setTitle("成功保存到提票单")
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
//                                                }
                                                ).show();
                                            }else {
                                                getProgressDialog().dismiss();
                                                ToastUtil.toastShort("当前网络状态差，有"+integer+"张照片未上传成功，请重试");
                                            }
                                        }
                                    }
                                });

                            }
                        }
                    }
                });
    }
    private void SavaSubmitDao(final Subscriber<? super Integer> subscriber){
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RecheckSubmitDao dao = realm.createObject(RecheckSubmitDao.class);
                dao.setIdx(UUID);
                dao.setOperatorid(SysInfo.userInfo.emp.getEmpid());
                dao.setTrainTypeIDX(trainTypeIDX);
                dao.setTrainNo(TrainNo);
                dao.setTrainTypeShortName(ShortName);
                dao.setFaultDesc(etGZXXInfo.getText().toString());
                dao.setFaultFixPlaceIDX(((FaultTreeBean)tvBJName.getTag()).getFlbm());
                dao.setFixPlaceFullCode(((FaultTreeBean)tvBJName.getTag()).getGxwzbm());
                dao.setFixPlaceFullName(tvBJName.getText().toString());
                dao.setFaultOccurDate(System.currentTimeMillis());
                dao.setReasonAnalysisZhuanYe(tvZYName.getText().toString());
                dao.setReasonAnalysisZhuanYeID(tvZYName.getTag().toString());
                dao.setType(type);
                subscriber.onNext(1);
            }
        });
    }
    @Override
    public void OnGetImagesSuccess(List<FileBaseBean> images) {

    }

    @Override
    public void OnGetImagesFaild(String images) {

    }

    @Override
    public void OnSubmitSuccess() {
        getProgressDialog().dismiss();
        ToastUtil.toastShort("添加成功");
        setResult(300);
        finish();
    }

    @Override
    public void OnSubmitFaild(String msg) {
        getProgressDialog().dismiss();
    }

    @Override
    public void OnLoadListSuccess(List<FaultBean> Faults) {
        nowFaults.clear();
        if (Faults.size() > 0) {
            nowFaults.addAll(Faults);
        }
    }

    @Override
    public void OnLoadListFaild(String msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
                mPresenter.getLable(SubmitCheckPresenter.TYPE_MAJOR);
                break;
            case R.id.btNext:
                if(!NetWorkUtils.isWifiConnected(this)){
                    ToastUtil.toastShort("当前网络差，请检查网络后重试！");
                    return;
                }
                getProgressDialog().show();
                if (isAllselecyesd()) {
                    if(unLoadImages!=null&&unLoadImages.size()>0){
                        rx.Observable.create(new rx.Observable.OnSubscribe<Integer>(){
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {
                                Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        RealmResults<ImageDao> images = realm.where(ImageDao.class)
                                                .equalTo("isUpLoad", false)
                                                .equalTo("planIdx",UUID).findAll();
//                                    if(images!=null&&images.size()>0){
//                                        for(int i = 0;i<images.size();i++){
//                                            images.get(i).setIdx(i);
//                                        }
//                                    }
                                        unLoadImages = realm.copyFromRealm(Realm.getDefaultInstance().where(ImageDao.class)
                                                .equalTo("planIdx",UUID).equalTo("isUpLoad", false).findAll());
                                        AllImagesTimes = unLoadImages.size();
                                        if(AllImagesTimes>0){
                                            ImagesTimes = 0;
                                            Restimes = 0;
                                            String base64 = Utils.getBase64StringFromImg(unLoadImages.get(0).getFilePath());
                                            mPresenter.UpLoadImages(base64, JPG_EXTNAME, unLoadImages.get(0).getIdx(),0);
                                        }

                                    }
                                });

                            }
                        }).subscribeOn(Schedulers.from(JXApplication.getExecutor()))
                                .subscribe();
                    }else {
                        if (imagesTemp != null && imagesTemp.size() > 0) {
                            rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
                                @Override
                                public void call(final Subscriber<? super Integer> subscriber) {
                                    Log.e("Realm", "realm add start");
                                    Log.e("当前线程", Thread.currentThread().getName() + "--" + Thread.currentThread().getId() + "");
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

                                }
                            }).subscribeOn(Schedulers.from(JXApplication.getExecutor()))
                                    .observeOn(Schedulers.io()).subscribe(new Subscriber<Integer>() {
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

                        } else {
                            submitTicket("");
                        }
                    }

                }
                break;
        }
    }

    @Override
    public void OnImageClick(int position, String Pid) {
        if (Faults.get(position).isOpen()) {
            Faults.get(position).setOpen(false);
            String parentId = Faults.get(position).getId();
            LinkedList<FaultTreeBean> temps = new LinkedList<>();
            temps.addAll(Faults);
            Faults.clear();
            LinkedList<FaultTreeBean> ChildList = new LinkedList<>();
            ChildList = isChild(temps,parentId);
            if(ChildList.size()>0)
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
    LinkedList<FaultTreeBean> childMenu=new LinkedList<>();
    //判断是否为子节点
    public LinkedList<FaultTreeBean> isChild(LinkedList<FaultTreeBean> temps,String parentId){
        for(FaultTreeBean mu: temps){
            //遍历出父id等于参数的id，add进子节点集合
            if(parentId.equals(mu.getFjdID())){
                //递归遍历下一级
                isChild(temps,mu.getId());
                childMenu.add(mu);
            }
        }
        return childMenu;
    }
    private void submitTicket(String imgs){
        ArrayList<Map<String,Object>> list2 = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("trainTypeShortName",ShortName);
        map1.put("trainNo",TrainNo);
        map1.put("trainTypeIDX",trainTypeIDX);
        map1.put("ticketSource","机车复检复验");
        map1.put("recheckStatus","10");
        map1.put("recheckType","10");
        map1.put("flbm",((FaultTreeBean)tvBJName.getTag()).getFlbm());
        map1.put("fixPlaceFullName",tvBJName.getText().toString());
        map1.put("fixPlaceFullCode",((FaultTreeBean)tvBJName.getTag()).getGxwzbm());
        Map<String,String> map2 = new HashMap<>();
        map2.put("lb",tvZYName.getText().toString());
        map2.put("lb_IDX",tvZYName.getTag().toString());
        if(etGZXXInfo.getTag()!=null){
            map2.put("blzt_IDX",etGZXXInfo.getTag().toString());
        }
        map2.put("blzt",etGZXXInfo.getText().toString());
//        map1.put("reasonAnalysisID",tvZYName.getTag().toString()+","+tvKHName
//                .getTag().toString());
//        map1.put("reasonAnalysis",tvZYName.getText().toString()+","+tvKHName
//                .getText().toString());
//        map1.put("resolutionContent",etSXFNInfo.getText().toString());
//        map1.put("type",type);
//        list2.add(map1);
//        String jsonstr1 = JSON.toJSONString(list2);
        mPresenter.SaveTicket(JSON.toJSONString(map1),JSON.toJSONString(map2),"","",idx,
                SysInfo.userInfo.emp.getOperatorid()+"",imgs);
    }
}
