package jx.yunda.com.terminal.modules.tpprocess.unlinetest;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.netty.util.internal.StringUtil;
import io.realm.Realm;
import io.realm.RealmResults;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.base.JXApplication;
import jx.yunda.com.terminal.entity.FileBaseBean;
import jx.yunda.com.terminal.modules.tpprocess.ITicketInfo;
import jx.yunda.com.terminal.modules.tpprocess.ITicketInfoPresenter;
import jx.yunda.com.terminal.modules.tpprocess.ImagesAdapter;
import jx.yunda.com.terminal.modules.tpprocess.RealmDb.ImageDao;
import jx.yunda.com.terminal.modules.tpprocess.RealmDb.TicketSubmitDao;
import jx.yunda.com.terminal.modules.tpprocess.TP_ProcessActivity;
import jx.yunda.com.terminal.modules.tpprocess.adapter.StandardAdapter;
import jx.yunda.com.terminal.modules.tpprocess.adapter.TreeViewAdapter;
import jx.yunda.com.terminal.modules.tpprocess.model.DictBean;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultBean;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultTreeBean;
import jx.yunda.com.terminal.modules.tpprocess.model.StandardBean;
import jx.yunda.com.terminal.modules.tpprocess.model.StandardChildBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTypeBean;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.NetWorkUtils;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.MyExpandleListView;
import jx.yunda.com.terminal.widget.PhotoReadActivity;
import jx.yunda.com.terminal.widget.SodukuGridView;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class UnLineTestActivity extends BaseActivity<ITicketInfoPresenter> implements ITicketInfo, View.OnClickListener, TreeViewAdapter.OnImageClickLisnter {


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
    TextView etGZXXInfo;
    @BindView(R.id.gvImages)
    SodukuGridView gvImages;
    @BindView(R.id.etSXFNInfo)
    TextView etSXFNInfo;
    @BindView(R.id.btNext)
    TextView btNext;
    ArrayList<String> images = new ArrayList<>();
    ArrayList<String> manageImages = new ArrayList<>();
    ArrayList<ImageItem> imagesTemp = new ArrayList<>();
    ArrayList<ImageItem> imagesManageTemp = new ArrayList<>();
    List<FaultTreeBean> Faults = new LinkedList<>();
    ImagesAdapter adapter, magageAdapter;
    TreeViewAdapter treeViewAdapter;
    String type, ShortName, TrainNo, typeId, trainTypeIDX, faultFixFullName, zhuanye, kaohe, faultDesc, resolutionContent, idx, UUID,
            zhuanyeId, kaoheId, faultFixPlaceIDX, fixPlaceFullCode, ticketEmp, ticketTime;
    String state = "4";
    long faultOccurDate = 0;
    Integer overRangeStatus;
    AlertDialog dialog;
    ListView faultsListView;
    List<ImageDao> unLoadImages;
    public static int IMAGE_PICKER = 666;
    public static final String JPG_EXTNAME = ".jpg";
    int ImagesTimes = 0;
    int AllImagesTimes = 0;
    int Restimes = 0;
    int clickImageType = 0;
    String completeEmp, repairResult, completeTime;
    @BindView(R.id.tvTicketType)
    TextView tvTicketType;
    @BindView(R.id.tvIsbeyond)
    TextView tvIsbeyond;
    @BindView(R.id.llIsbeyond)
    LinearLayout llIsbeyond;
    @BindView(R.id.tvInfoUser)
    TextView tvInfoUser;
    @BindView(R.id.tvInfoData)
    TextView tvInfoData;
    @BindView(R.id.llInfo)
    LinearLayout llInfo;
    @BindView(R.id.etManageResult)
    EditText etManageResult;
    @BindView(R.id.llManageResultEdit)
    LinearLayout llManageResultEdit;
    @BindView(R.id.tvManageResultLook)
    TextView tvManageResultLook;
    @BindView(R.id.llManageResultLook)
    LinearLayout llManageResultLook;
    @BindView(R.id.tvManageInfoUser)
    TextView tvManageInfoUser;
    @BindView(R.id.tvManageInfoData)
    TextView tvManageInfoData;
    @BindView(R.id.llManageInfo)
    LinearLayout llManageInfo;
    @BindView(R.id.gvResultImages)
    SodukuGridView gvResultImages;
    @BindView(R.id.llManage)
    LinearLayout llManage;
    @BindView(R.id.tvUploadImageTitle)
    TextView tvUploadImageTitle;
    @BindView(R.id.cbIsTodo)
    CheckBox cbIsTodo;
    @BindView(R.id.btBackSchedule)
    TextView btBackSchedule;
    @BindView(R.id.btPass)
    TextView btPass;
    @BindView(R.id.lvStandard)
    MyExpandleListView lvStandard;
    TextView tvBackGroup, tvBackPerson;
    AlertDialog backDialog;
    List<StandardBean> mStandards = new ArrayList<>();
    StandardAdapter standardAdapter;
    AlertDialog editDialog;
    EditText etDialogText;
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
        return R.layout.activity_ticket_information;
    }

    @Override
    public void initSubViews(View view) {
    }
    public void saveEditData(int groupPosition,int position, String str) {

    }
    @Override
    public void initData() {
        llManage.setVisibility(View.VISIBLE);
        llInfo.setVisibility(View.VISIBLE);
//                llIsbeyond.setVisibility(View.VISIBLE);
//                llManageResultEdit.setVisibility(View.VISIBLE);
        llManageResultLook.setVisibility(View.GONE);
        if (ticketEmp != null)
            tvInfoUser.setText(ticketEmp);
        if (ticketTime != null)
            tvInfoData.setText(ticketTime);
        cbIsTodo.setVisibility(View.GONE);
        for(int i = 0 ; i<5; i++){
            StandardBean standardBean = new StandardBean();
            standardBean.setStepName("haha"+i);
            List<StandardChildBean> list = new ArrayList<>();
            for(int j = 0; j<4;j++){
                StandardChildBean childBean = new StandardChildBean();
                childBean.setIdx(i+"");
                childBean.setContent(("haha"+i)+j);
                list.add(childBean);
            }
            standardBean.setContentList(list);
            mStandards.add(standardBean);
        }
        standardAdapter = new StandardAdapter(this,mStandards);
        lvStandard.setAdapter(standardAdapter);
        lvStandard.setGroupIndicator(null);
        for (int i = 0; i < mStandards.size(); i++) {
            lvStandard.expandGroup(i);
        }
        standardAdapter.SetMyClickListner(new StandardAdapter.MyOnItemclickListner() {
            @Override
            public void OnParentClick(int parentPosition, boolean isExpand) {

            }

            int parents;
            int child;
            @Override
            public void OnChildClick( int parentPosition,  int ChildPosision) {
                parents = parentPosition;
                child = ChildPosision;
                if(editDialog==null){
                    View view = LayoutInflater.from(UnLineTestActivity.this).inflate(R.layout.dialog_edit,null);
                    etDialogText = (EditText)view.findViewById(R.id.etDialogText);
                    editDialog = new AlertDialog.Builder(UnLineTestActivity.this).setView(view)
                            .setTitle("技术标准").setNegativeButton("取消",null)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(etDialogText.getText()!=null&&etDialogText.getText().toString()!=null){
                                        mStandards.get(parents).getContentList().get(child)
                                                .setInfo(etDialogText.getText().toString());
                                        standardAdapter.notifyDataSetChanged();
                                    }
                                    dialog.dismiss();
                                }
                            }).create();
                }
                editDialog.show();
                if(mStandards.get(parents).getContentList().get(child).getInfo()!=null){
                    etDialogText.setText(mStandards.get(parents).getContentList().get(child).getInfo());
                }else {
                    etDialogText.setText("");
                }
            }
        });
    }

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

    private boolean isAllselecyesd() {
        if (tvBJName.getTag() == null || "".equals(tvBJName.getTag().toString())) {
            Toast.makeText(this, "不能提交，还未选择部件位置！", Toast.LENGTH_SHORT).show();
            getProgressDialog().dismiss();
            return false;
        }
        if (tvZYName.getTag() == null || "".equals(tvZYName.getTag().toString())) {
            Toast.makeText(this, "不能提交，还未选择专业！", Toast.LENGTH_SHORT).show();
            getProgressDialog().dismiss();
            return false;
        }
        if (tvKHName.getTag() == null || "".equals(tvKHName.getTag().toString())) {
            Toast.makeText(this, "不能提交，还未选择考核！", Toast.LENGTH_SHORT).show();
            getProgressDialog().dismiss();
            return false;
        }
        if (etGZXXInfo.getText() == null || "".equals(etGZXXInfo.getText().toString())) {
            Toast.makeText(this, "不能提交，还未填写故障现象内容！", Toast.LENGTH_SHORT).show();
            getProgressDialog().dismiss();
            return false;
        }
        if (etSXFNInfo.getText() == null || "".equals(etSXFNInfo.getText().toString())) {
            Toast.makeText(this, "不能提交，还未选填写施修方案内容！", Toast.LENGTH_SHORT).show();
            getProgressDialog().dismiss();
            return false;
        }
//        if(state.equals("3")){
//            if(etManageResult.getText()==null||etManageResult.getText().toString().equals("")){
//                Toast.makeText(this, "不能提交，还未选填写处理结果！", Toast.LENGTH_SHORT).show();
//                getProgressDialog().dismiss();
//                return false;
//            }
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
                    if (clickImageType == 0) {
                        imagesTemp.addAll(images);
                        this.images.remove(this.images.size() - 1);
                        for (int i = 0; i < images.size(); i++) {
                            this.images.add(images.get(i).path);
                        }
                        if (state.equals("0")) {
                            this.images.add("0");
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        imagesManageTemp.addAll(images);
                        this.manageImages.remove(this.manageImages.size() - 1);
                        for (int i = 0; i < images.size(); i++) {
                            this.manageImages.add(images.get(i).path);
                        }
                        if (state.equals("4")) {
                            this.manageImages.add("0");
                        }
                        magageAdapter.notifyDataSetChanged();
                    }
//                    ImagePicker.getInstance().getSelectedImages().addAll(images);
//                    ImagePicker.getInstance().getSelectImageCount();


                }
            } else if (requestCode == ImagePicker.REQUEST_CODE_PREVIEW) {

                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra("images");
                if (clickImageType == 0) {
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
                    imagesManageTemp.clear();
                    if (images != null && images.size() > 0) {
                        imagesManageTemp.addAll(images);
                        this.manageImages.clear();
                        for (int i = 0; i < images.size(); i++) {
                            this.manageImages.add(images.get(i).path);
                        }
                        if (state.equals("4")) {
                            this.manageImages.add("0");
                        }
                        magageAdapter.notifyDataSetChanged();
                    } else {
                        this.manageImages.clear();
                        if (state.equals("4")) {
                            this.manageImages.add("0");
                        }
                        magageAdapter.notifyDataSetChanged();
                    }
                }

            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    List<FaultBean> nowFaults = new ArrayList<>();
    PopupMenu popupMenu;

    @Override
    public void OnLoadListSuccess(List<FaultBean> Faults) {
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
    boolean isPass = false;
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


    @Override
    public void OnUpLoadImagesSuccess(final String path, final int position, final int imageNo) {
        Restimes++;
        Observable.create(new Observable.OnSubscribe<Integer>() {
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
                                String base64 = "";
                                if (state.equals("4")) {
                                    base64 = Utils.getBase64StringFromImg(imagesManageTemp.get(ImagesTimes + 1).path);
                                } else {
                                    base64 = Utils.getBase64StringFromImg(imagesTemp.get(ImagesTimes + 1).path);
                                }

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
                                                                    .equalTo("planIdx", UUID).findAll());
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
                                                            ArrayList<Map<String, Object>> list2 = new ArrayList<>();
                                                            Map<String, Object> map1 = new HashMap<>();
                                                            String jsonstr1 = "";
                                                            if (state.equals("4")) {
                                                                map1.put("idx", idx);
                                                                map1.put("completeEmpID", SysInfo.userInfo.emp.getEmpid());
                                                                map1.put("completeTimeStr", Utils.formatTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
//                                                                map1.put("repairResult",etManageResult.getText().toString());
                                                                jsonstr1 = JSON.toJSONString(map1);
                                                            } else {
                                                                map1.put("trainNo", TrainNo);
                                                                map1.put("trainTypeShortName", ShortName);
                                                                map1.put("faultID", "");
                                                                map1.put("trainTypeIDX", trainTypeIDX);
                                                                map1.put("faultName", "");
                                                                map1.put("faultDesc", etGZXXInfo.getText().toString());
                                                                map1.put("faultFixPlaceIDX", ((FaultTreeBean) tvBJName.getTag()).getFlbm());
                                                                map1.put("fixPlaceFullCode", ((FaultTreeBean) tvBJName.getTag()).getGxwzbm());
                                                                map1.put("fixPlaceFullName", tvBJName.getText().toString());
                                                                if (faultOccurDate == 0) {
                                                                    map1.put("faultOccurDate", System.currentTimeMillis());
                                                                } else {
                                                                    map1.put("faultOccurDate", faultOccurDate);
                                                                }

                                                                map1.put("professionalTypeIdx", "");
                                                                map1.put("professionalTypeName", "");
                                                                map1.put("professionalTypeSeq", "");
                                                                map1.put("reasonAnalysisID", tvZYName.getTag().toString());
                                                                map1.put("reasonAnalysis", tvZYName.getText().toString());
                                                                map1.put("resolutionContent", etSXFNInfo.getText().toString());
                                                                map1.put("type", type);
                                                                if (isPass) {
                                                                    map1.put("isProcess", 1);
                                                                }else {
                                                                    map1.put("isProcess", 0);
                                                                }
                                                                list2.add(map1);
                                                                jsonstr1 = JSON.toJSONString(list2);
                                                            }

                                                            if (state.equals("4")) {
                                                                if (isPass) {
                                                                    mPresenter.SaveTicketManageOther(SysInfo.userInfo.emp.getEmpid(), jsonstr1, s);
                                                                } else {
                                                                    List<Map<String,Object>> list = new ArrayList<>();
                                                                    for(StandardBean standard: mStandards){
                                                                        List<StandardChildBean> childBeans = standard.getContentList();
                                                                        if(childBeans!=null&&childBeans.size()>0){
                                                                            for(StandardChildBean child:childBeans){
                                                                                if(!StringUtil.isNullOrEmpty(child.getInfo())){
                                                                                    Map<String,Object> map = new HashMap<>();
                                                                                    map.put("idx",child.getIdx());
                                                                                    map.put("contentValue",child.getInfo());
                                                                                    list.add(map);
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    mPresenter.SaveTicketManage(SysInfo.userInfo.emp.getEmpid(), jsonstr1, s,JSON.toJSONString(list));
                                                                }
                                                            } else {
                                                                mPresenter.SaveTicket(SysInfo.userInfo.emp.getEmpid(), jsonstr1, s);
                                                            }

                                                        }
                                                    });
                                        } else {
                                            getProgressDialog().dismiss();
                                            if (NetWorkUtils.isWifiConnected(UnLineTestActivity.this)) {
                                                new AlertDialog.Builder(UnLineTestActivity.this).setTitle("提示！")
                                                        .setMessage("当前有" + integer + "张图片未上传成功，是否重试或保存本地？")
                                                        .setPositiveButton("重试", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                getProgressDialog().show();
//                                                                unLoadImages = Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance().where(ImageDao.class)
//                                                                        .equalTo("planIdx", UUID)
//                                                                        .equalTo("isUpLoad", false).findAll());
                                                                AllImagesTimes = unLoadImages.size();
                                                                ImagesTimes = 0;
                                                                String base64 = Utils.getBase64StringFromImg(unLoadImages.get(0).getFilePath());
                                                                mPresenter.UpLoadImages(base64, JPG_EXTNAME, unLoadImages.get(0).getIdx(), 0);
                                                            }
                                                        }).setNegativeButton("取消", null
//                                                        new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        Observable.create(new Observable.OnSubscribe<Integer>() {
//                                                            @Override
//                                                            public void call(final Subscriber<? super Integer> subscriber) {
//                                                                Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
//                                                                    @Override
//                                                                    public void execute(Realm realm) {
//                                                                        TicketSubmitDao dao = realm.createObject(TicketSubmitDao.class);
//                                                                        dao.setIdx(UUID);
//                                                                        dao.setOperatorid(SysInfo.userInfo.emp.getOperatorid());
//                                                                        dao.setTrainTypeIDX(trainTypeIDX);
//                                                                        dao.setTrainNo(TrainNo);
//                                                                        dao.setTrainTypeShortName(ShortName);
//                                                                        dao.setFaultDesc(etGZXXInfo.getText().toString());
//                                                                        dao.setFaultFixPlaceIDX(((FaultTreeBean) tvBJName.getTag()).getFlbm());
//                                                                        dao.setFixPlaceFullCode(((FaultTreeBean) tvBJName.getTag()).getGxwzbm());
//                                                                        dao.setFixPlaceFullName(tvBJName.getText().toString());
//                                                                        dao.setFaultOccurDate(System.currentTimeMillis());
//                                                                        dao.setReasonAnalysisZhuanYe(tvZYName.getText().toString());
//                                                                        dao.setReasonAnalysisZhuanYeID(tvZYName.getTag().toString());
//                                                                        dao.setReasonAnalysisKaoHe(tvKHName.getText().toString());
//                                                                        dao.setReasonAnalysisKaoHeId(tvKHName.getTag().toString());
//                                                                        dao.setResolutionContent(etSXFNInfo.getText().toString());
//                                                                        dao.setType(type);
//                                                                        subscriber.onNext(1);
//                                                                    }
//                                                                });
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
//                                                                if (integer == 1) {
//                                                                    new AlertDialog.Builder(TicketInfoReadActivity.this).setTitle("成功保存到提票单")
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
                                            } else {
                                                dialog.dismiss();
                                                ToastUtil.toastShort("当前网络状态较差，有" + integer + "张照片未传，请重试");
                                            }
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
        if(state.equals("4")){
            getProgressDialog().dismiss();
        }
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
        if (images.size() > 0) {
            this.manageImages.clear();
            for (FileBaseBean str : images) {
                String strs = Utils.getImageUrl(str.getFileUrl());
                this.manageImages.add(strs);
            }
            magageAdapter.notifyDataSetChanged();
            imagesManageTemp.clear();
            if (images.size() > 0) {
                for (int i = 0; i < images.size(); i++) {
                    ImageItem item = new ImageItem();
                    item.path = Utils.getImageUrl(images.get(i).getFileUrl());
                    imagesManageTemp.add(item);
                }
            }
        }
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
        if ("2".equals(state) || "3".equals(state)) {
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
                                    ActivityUtil.startActivity(UnLineTestActivity.this, TP_ProcessActivity.class);
                                }
                            }, 2000);
                        }
                    });
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setResult(200);
                    finish();
                }
            }, 2000);
        }

    }

    @Override
    public void OnLoadFail(String msg) {
        if(state.equals("4")){
            mPresenter.getImages(idx, "nodeTpAtt");
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnSubmitFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
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

    @Override
    public void onBackPressed() {
        if (imagesManageTemp != null && imagesManageTemp.size() > 0 && state.equals("4")) {
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
    public void OnLoadFail(String msg, final int position, final int imageNo) {
        if (position == -1 && imageNo == -1) {
            Observable.create(new Observable.OnSubscribe<Integer>() {
                @Override
                public void call(final Subscriber<? super Integer> subscriber) {
                    Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            TicketSubmitDao dao = realm.createObject(TicketSubmitDao.class);
                            dao.setIdx(UUID);
                            dao.setOperatorid(SysInfo.userInfo.emp.getOperatorid());
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
                            dao.setResolutionContent(etSXFNInfo.getText().toString());
                            dao.setType(type);
                            subscriber.onNext(1);
                        }
                    });
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
                    if (integer == 1) {
                        new AlertDialog.Builder(UnLineTestActivity.this).setTitle("成功保存到提票单")
                                .setMessage("因网络故障，已保存到提票单，您可以在检修过程界面进行查看！")
                                .setPositiveButton("去查看", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                    }
                }
            });
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
                    Observable
                            .create(new Observable.OnSubscribe<Integer>() {
                                @Override
                                public void call(Subscriber<? super Integer> subscriber) {
                                    Log.e("Realm", "realm add start");
                                    Log.e("当前线程", Thread.currentThread().getName() + "--" + Thread.currentThread().getId() + "");
                                    unLoadImages = Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance().where(ImageDao.class)
                                            .equalTo("isUpLoad", false).
                                                    equalTo("planIdx", UUID).findAll());
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
                                    if (aVoid == null || aVoid == 0) {

                                    } else {
                                        if (NetWorkUtils.isWifiConnected(UnLineTestActivity.this)) {
                                            new AlertDialog.Builder(UnLineTestActivity.this).setTitle("提示！")
                                                    .setMessage("当前有" + aVoid + "张图片未上传成功，是否重试或保存本地？")
                                                    .setPositiveButton("重试", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            getProgressDialog().show();
                                                            AllImagesTimes = unLoadImages.size();
                                                            ImagesTimes = 0;
                                                            String base64 = Utils.getBase64StringFromImg(unLoadImages.get(0).getFilePath());
                                                            mPresenter.UpLoadImages(base64, JPG_EXTNAME, unLoadImages.get(0).getIdx(), 0);
                                                        }
                                                    }).setNegativeButton("取消", null
//                                                    new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    Observable.create(new Observable.OnSubscribe<Integer>() {
//                                                        @Override
//                                                        public void call(final Subscriber<? super Integer> subscriber) {
//                                                            Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
//                                                                @Override
//                                                                public void execute(Realm realm) {
//                                                                    TicketSubmitDao dao = realm.createObject(TicketSubmitDao.class);
//                                                                    dao.setIdx(UUID);
//                                                                    dao.setOperatorid(SysInfo.userInfo.emp.getOperatorid());
//                                                                    dao.setTrainTypeIDX(trainTypeIDX);
//                                                                    dao.setTrainNo(TrainNo);
//                                                                    dao.setTrainTypeShortName(ShortName);
//                                                                    dao.setFaultDesc(etGZXXInfo.getText().toString());
//                                                                    dao.setFaultFixPlaceIDX(((FaultTreeBean) tvBJName.getTag()).getFlbm());
//                                                                    dao.setFixPlaceFullCode(((FaultTreeBean) tvBJName.getTag()).getGxwzbm());
//                                                                    dao.setFixPlaceFullName(tvBJName.getText().toString());
//                                                                    dao.setFaultOccurDate(System.currentTimeMillis());
//                                                                    dao.setReasonAnalysisZhuanYe(tvZYName.getText().toString());
//                                                                    dao.setReasonAnalysisZhuanYeID(tvZYName.getTag().toString());
//                                                                    dao.setReasonAnalysisKaoHe(tvKHName.getText().toString());
//                                                                    dao.setReasonAnalysisKaoHeId(tvKHName.getTag().toString());
//                                                                    dao.setResolutionContent(etSXFNInfo.getText().toString());
//                                                                    dao.setType(type);
//                                                                    subscriber.onNext(1);
//                                                                }
//                                                            });
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
//                                                            if (integer == 1) {
//                                                                getProgressDialog().dismiss();
//                                                                new AlertDialog.Builder(TicketInfoReadActivity.this).setTitle("成功保存到提票单")
//                                                                        .setMessage("因网络故障，已保存到提票单，您可以在检修过程界面进行查看！")
//                                                                        .setPositiveButton("去查看", new DialogInterface.OnClickListener() {
//                                                                            @Override
//                                                                            public void onClick(DialogInterface dialog, int which) {
//
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
//                                            }
                                            ).show();
                                        } else {
                                            getProgressDialog().dismiss();
                                            ToastUtil.toastShort("当前有" + aVoid + "张图片未上传成功,请重试");
//                                            Observable.create(new Observable.OnSubscribe<Integer>() {
//                                                @Override
//                                                public void call(final Subscriber<? super Integer> subscriber) {
//                                                    Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
//                                                        @Override
//                                                        public void execute(Realm realm) {
//                                                            TicketSubmitDao dao = realm.createObject(TicketSubmitDao.class);
//                                                            dao.setIdx(UUID);
//                                                            dao.setOperatorid(SysInfo.userInfo.emp.getOperatorid());
//                                                            dao.setTrainTypeIDX(trainTypeIDX);
//                                                            dao.setTrainNo(TrainNo);
//                                                            dao.setTrainTypeShortName(ShortName);
//                                                            dao.setFaultDesc(etGZXXInfo.getText().toString());
//                                                            dao.setFaultFixPlaceIDX(((FaultTreeBean) tvBJName.getTag()).getFlbm());
//                                                            dao.setFixPlaceFullCode(((FaultTreeBean) tvBJName.getTag()).getGxwzbm());
//                                                            dao.setFixPlaceFullName(tvBJName.getText().toString());
//                                                            dao.setFaultOccurDate(System.currentTimeMillis());
//                                                            dao.setReasonAnalysisZhuanYe(tvZYName.getText().toString());
//                                                            dao.setReasonAnalysisZhuanYeID(tvZYName.getTag().toString());
//                                                            dao.setReasonAnalysisKaoHe(tvKHName.getText().toString());
//                                                            dao.setReasonAnalysisKaoHeId(tvKHName.getTag().toString());
//                                                            dao.setResolutionContent(etSXFNInfo.getText().toString());
//                                                            dao.setType(type);
//                                                            subscriber.onNext(1);
//                                                        }
//                                                    });
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
//                                                    if (integer == 1) {
//                                                        getProgressDialog().dismiss();
//                                                        new AlertDialog.Builder(TicketInfoReadActivity.this).setTitle("成功保存到提票单")
//                                                                .setMessage("因网络故障，已保存到提票单，您可以在检修过程界面进行查看！")
//                                                                .setPositiveButton("去查看", new DialogInterface.OnClickListener() {
//                                                                    @Override
//                                                                    public void onClick(DialogInterface dialog, int which) {
//                                                                        Bundle bundle = new Bundle();
//                                                                        bundle.putInt("state", 2);
//                                                                        ActivityUtil.startActivityWithDelayed(TicketInfoReadActivity.this, TicketListActivity.class, bundle);
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

    }


    @Override
    public void OnDicLoadFaild(String msg) {

    }

    @Override
    public void OnBackSuccess() {
        ToastUtil.toastShort("退回成功");
        getProgressDialog().dismiss();
        finish();
    }

    @Override
    public void OnLoadStandardSuccess(List<StandardBean> list) {
        mStandards.clear();
        if(list!=null&&list.size()>0){
            mStandards.addAll(list);
            for (int i = 0; i < mStandards.size(); i++) {
                lvStandard.expandGroup(i);
            }
        }
        standardAdapter.notifyDataSetChanged();
        mPresenter.getImages(idx, "nodeTpAtt");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    List<String> list = new ArrayList<>();

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
            case R.id.btPass:
                isPass = true;
                getProgressDialog().show();
                if (state.equals("4")) {
                    if (imagesManageTemp != null && imagesManageTemp.size() > 0) {
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
                                    Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
//                                        realm.where(ImageDao.class).findAll().deleteAllFromRealm();
                                            for (int i = 0; i < imagesManageTemp.size(); i++) {
                                                ImageDao image = realm.createObject(ImageDao.class);
                                                image.setFilePath(imagesManageTemp.get(i).path);
                                                image.setUpLoad(false);
                                                image.setFilename(imagesManageTemp.get(i).name);
                                                image.setIdx(i);
                                                image.setPlanIdx(UUID);
                                                image.setToup(false);
                                            }
                                            subscriber.onNext(0);
                                        }
                                    });

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
                                        if (imagesManageTemp.size() > 0) {
                                            ImagesTimes = 0;
                                            AllImagesTimes = imagesManageTemp.size();
                                            Restimes = 0;
                                            String base64 = Utils.getBase64StringFromImg(imagesManageTemp.get(0).path);
                                            mPresenter.UpLoadImages(base64, JPG_EXTNAME, 0, 0);
                                        }
                                    }
                                }
                            });
                        }


                    } else {
                        Log.e("Realm", "realm add finish");
                        ArrayList<Map<String, Object>> list2 = new ArrayList<>();
                        Map<String, Object> map1 = new HashMap<>();
                        String jsonstr1 = "";
                        if (state.equals("4")) {
                            map1.put("idx", idx);
                            map1.put("completeEmpID", SysInfo.userInfo.emp.getEmpid());
                            map1.put("completeTimeStr", Utils.formatTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
//                                                                map1.put("repairResult",etManageResult.getText().toString());
                            jsonstr1 = JSON.toJSONString(map1);
                        } else {

                            map1.put("trainTypeIDX", trainTypeIDX);
                            map1.put("trainNo", TrainNo);
                            map1.put("trainTypeShortName", ShortName);
                            map1.put("faultID", "");
                            map1.put("faultName", "");
                            map1.put("faultDesc", etGZXXInfo.getText().toString());
                            if (tvBJName.getTag() != null) {
                                map1.put("faultFixPlaceIDX", ((FaultTreeBean) tvBJName.getTag()).getFlbm());
                                map1.put("fixPlaceFullCode", ((FaultTreeBean) tvBJName.getTag()).getGxwzbm());
                            }
                            map1.put("fixPlaceFullName", tvBJName.getText().toString());
                            if (faultOccurDate == 0) {
                                map1.put("faultOccurDate", System.currentTimeMillis());
                            } else {
                                map1.put("faultOccurDate", faultOccurDate);
                            }

                            map1.put("professionalTypeIdx", "");
                            map1.put("professionalTypeName", "");
                            map1.put("professionalTypeSeq", "");
                            if (tvZYName.getTag() != null)
                                map1.put("reasonAnalysisID", tvZYName.getTag().toString());
                            map1.put("reasonAnalysis", tvZYName.getText().toString());
                            map1.put("resolutionContent", etSXFNInfo.getText().toString());
                            map1.put("type", type);
                            list2.add(map1);
                            jsonstr1 = JSON.toJSONString(list2);
                        }

                        if (state.equals("4")) {
                            if (isPass) {
                                mPresenter.SaveTicketManageOther(SysInfo.userInfo.emp.getEmpid(), jsonstr1, "");
                            } else {
                                List<Map<String,Object>> list = new ArrayList<>();
                                for(StandardBean standard: mStandards){
                                    List<StandardChildBean> childBeans = standard.getContentList();
                                    if(childBeans!=null&&childBeans.size()>0){
                                        for(StandardChildBean child:childBeans){
                                            if(!StringUtil.isNullOrEmpty(child.getInfo())){
                                                Map<String,Object> map2 = new HashMap<>();
                                                map2.put("idx",child.getIdx());
                                                map2.put("contentValue",child.getInfo());
                                                list.add(map2);
                                            }
                                        }
                                    }
                                }
                                mPresenter.SaveTicketManage(SysInfo.userInfo.emp.getEmpid(), jsonstr1, "",JSON.toJSONString(list));
                            }
                        } else {
                            mPresenter.SaveTicket(SysInfo.userInfo.emp.getEmpid(), jsonstr1, "");
                        }
                    }
                } else {
                    if (isAllselecyesd()) {
                        if (imagesTemp != null && imagesTemp.size() > 0) {
                            Observable.create(new Observable.OnSubscribe<Integer>() {
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
                                                subscriber.onNext(0);
                                            }
                                        }
                                    });

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

                        } else {

                        }
                    }
                }

                break;
            case R.id.btNext:
                isPass = false;
                getProgressDialog().show();
                if (state.equals("4")) {
                    if (imagesManageTemp != null && imagesManageTemp.size() > 0) {
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
                                    Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
//                                        realm.where(ImageDao.class).findAll().deleteAllFromRealm();
                                            for (int i = 0; i < imagesManageTemp.size(); i++) {
                                                ImageDao image = realm.createObject(ImageDao.class);
                                                image.setFilePath(imagesManageTemp.get(i).path);
                                                image.setUpLoad(false);
                                                image.setFilename(imagesManageTemp.get(i).name);
                                                image.setIdx(i);
                                                image.setPlanIdx(UUID);
                                                image.setToup(false);
                                            }
                                            subscriber.onNext(0);
                                        }
                                    });

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
                                        if (imagesManageTemp.size() > 0) {
                                            ImagesTimes = 0;
                                            AllImagesTimes = imagesManageTemp.size();
                                            Restimes = 0;
                                            String base64 = Utils.getBase64StringFromImg(imagesManageTemp.get(0).path);
                                            mPresenter.UpLoadImages(base64, JPG_EXTNAME, 0, 0);
                                        }
                                    }
                                }
                            });
                        }


                    } else {
                        Log.e("Realm", "realm add finish");
                        ArrayList<Map<String, Object>> list2 = new ArrayList<>();
                        Map<String, Object> map1 = new HashMap<>();
                        String jsonstr1 = "";
                        if (state.equals("4")) {
                            map1.put("idx", idx);
                            map1.put("completeEmpID", SysInfo.userInfo.emp.getEmpid());
                            map1.put("completeTimeStr", Utils.formatTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
//                                                                map1.put("repairResult",etManageResult.getText().toString());
                            jsonstr1 = JSON.toJSONString(map1);
                        } else {

                            map1.put("trainTypeIDX", trainTypeIDX);
                            map1.put("trainNo", TrainNo);
                            map1.put("trainTypeShortName", ShortName);
                            map1.put("faultID", "");
                            map1.put("faultName", "");
                            map1.put("faultDesc", etGZXXInfo.getText().toString());
                            if (tvBJName.getTag() != null) {
                                map1.put("faultFixPlaceIDX", ((FaultTreeBean) tvBJName.getTag()).getFlbm());
                                map1.put("fixPlaceFullCode", ((FaultTreeBean) tvBJName.getTag()).getGxwzbm());
                            }
                            map1.put("fixPlaceFullName", tvBJName.getText().toString());
                            if (faultOccurDate == 0) {
                                map1.put("faultOccurDate", System.currentTimeMillis());
                            } else {
                                map1.put("faultOccurDate", faultOccurDate);
                            }

                            map1.put("professionalTypeIdx", "");
                            map1.put("professionalTypeName", "");
                            map1.put("professionalTypeSeq", "");
                            if (tvZYName.getTag() != null)
                                map1.put("reasonAnalysisID", tvZYName.getTag().toString());
                            map1.put("reasonAnalysis", tvZYName.getText().toString());
                            map1.put("resolutionContent", etSXFNInfo.getText().toString());
                            map1.put("type", type);
                            list2.add(map1);
                            jsonstr1 = JSON.toJSONString(list2);
                        }

                        if (state.equals("4")) {
                            if (isPass) {
                                mPresenter.SaveTicketManageOther(SysInfo.userInfo.emp.getEmpid(), jsonstr1, "");
                            } else {
                                List<Map<String,Object>> list = new ArrayList<>();
                                for(StandardBean standard: mStandards){
                                    List<StandardChildBean> childBeans = standard.getContentList();
                                    if(childBeans!=null&&childBeans.size()>0){
                                        for(StandardChildBean child:childBeans){
                                            if(!StringUtil.isNullOrEmpty(child.getInfo())){
                                                Map<String,Object> map2 = new HashMap<>();
                                                map2.put("idx",child.getIdx());
                                                map2.put("contentValue",child.getInfo());
                                                list.add(map2);
                                            }
                                        }
                                    }
                                }
                                mPresenter.SaveTicketManage(SysInfo.userInfo.emp.getEmpid(), jsonstr1, "",JSON.toJSONString(list));
                            }
                        } else {
                            if (isPass) {
                                mPresenter.SaveTicketManageOther(SysInfo.userInfo.emp.getEmpid(), jsonstr1, "");
                            } else {
                                List<Map<String,Object>> list = new ArrayList<>();
                                for(StandardBean standard: mStandards){
                                    List<StandardChildBean> childBeans = standard.getContentList();
                                    if(childBeans!=null&&childBeans.size()>0){
                                        for(StandardChildBean child:childBeans){
                                            if(!StringUtil.isNullOrEmpty(child.getInfo())){
                                                Map<String,Object> map2 = new HashMap<>();
                                                map2.put("idx",child.getIdx());
                                                map2.put("contentValue",child.getInfo());
                                                list.add(map2);
                                            }
                                        }
                                    }
                                }
                                mPresenter.SaveTicketManage(SysInfo.userInfo.emp.getEmpid(), jsonstr1, "",JSON.toJSONString(list));
                            }
                        }
                    }
                } else {
                    if (isAllselecyesd()) {
                        if (imagesTemp != null && imagesTemp.size() > 0) {
                            Observable.create(new Observable.OnSubscribe<Integer>() {
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
                                                subscriber.onNext(0);
                                            }
                                        }
                                    });

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

                        } else {

                        }
                    }
                }

                break;
        }
    }

    LinkedList<FaultTreeBean> childMenu = new LinkedList<>();

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
}
