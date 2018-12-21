package jx.yunda.com.terminal.modules.pullTrainNotice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.base.baseDictData.model.DicDataItem;
import jx.yunda.com.terminal.base.baseDictData.presenter.BaseDictPresenter;
import jx.yunda.com.terminal.modules.message.model.MessageGroupPerson;
import jx.yunda.com.terminal.modules.message.model.Receiver;
import jx.yunda.com.terminal.modules.message.widget.MessageInputEditText;
import jx.yunda.com.terminal.modules.pullTrainNotice.adapter.PullTrainNoticeSelectPersonAdapter;
import jx.yunda.com.terminal.modules.pullTrainNotice.adapter.PullTrainNoticeSelectTrainAdapter;
import jx.yunda.com.terminal.modules.pullTrainNotice.model.PullTrainNotice;
import jx.yunda.com.terminal.modules.pullTrainNotice.model.PullTrainNoticeItem;
import jx.yunda.com.terminal.modules.pullTrainNotice.presenter.IPullTrainNoticeEdit;
import jx.yunda.com.terminal.modules.pullTrainNotice.presenter.PullTrainNoticeEditPresenter;
import jx.yunda.com.terminal.modules.transNotice.model.TransNoticeTrain;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.DateUtil;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.DateTimeText;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class PullTrainNoticeEditActivity extends BaseActivity<PullTrainNoticeEditPresenter> implements IPullTrainNoticeEdit, Validator.ValidationListener {
    BaseDictPresenter baseDictPresenter;
    @BindView(R.id.pull_train_notice_edit_detail_btn)
    ImageButton detailBtn;
    @NotEmpty(message = "通知人不能为空！")
    @BindView(R.id.pull_train_notice_edit_persons)
    MessageInputEditText noticePersons;
    @BindView(R.id.pull_train_notice_edit_train)
    EditText train;
    @BindView(R.id.pull_train_notice_edit_start_position)
    TextView startPosition;
    @NotEmpty(message = "迁移台位不能为空！")
    @BindView(R.id.pull_train_notice_edit_end_position)
    TextView endPosition;
    @NotEmpty(message = "截止时间不能为空！")
    @BindView(R.id.pull_train_notice_edit_end_time)
    DateTimeText endTime;
    @BindView(R.id.pull_train_notice_edit_notice_time)
    TextView noticeTime;
    @Length(min = 0, max = 100, message = "备注长度不能超过100")
    @BindView(R.id.pull_train_notice_edit_remark)
    EditText remark;
    @BindView(R.id.pull_train_notice_edit_save)
    Button saveBtn;
    AlertDialog selectNoticePersonDialog;
    AlertDialog selectNoticeTrainDialog;
    PullTrainNoticeSelectPersonAdapter selectPersonAdapter;
    PullTrainNoticeSelectTrainAdapter selectTrainAdapter;
    List<Receiver> selectReceivers = new ArrayList<>();
    List<PullTrainNotice> selectTrains = new ArrayList<>();
    PullTrainNotice selectPullTrainNotice;
    PullTrainNoticeItem selectPullTrainNoticeItem;
    //验证
    Validator validator;
    List<DicDataItem> endPositions = new ArrayList<>();

    @Override
    protected PullTrainNoticeEditPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new PullTrainNoticeEditPresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.pull_train_notice_edit_act;
    }

    @Override
    public void initSubViews(View view) {
        baseDictPresenter = new BaseDictPresenter(this);
        // 初始化验证对象
        initValidator();
        setNoticePersonDialog();
        RxView.clicks(noticePersons).throttleFirst(5, TimeUnit.SECONDS)
                .compose(this.<Void>bindToLifecycle())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (selectPersonAdapter != null)
                            selectPersonAdapter.setSelectReceivers(selectReceivers);
                        getPresenter().getGroupPersonsByPostName("牵车");
                    }
                });
        noticePersons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
                if (lengthBefore > 1 && lengthAfter == 0) {
                    MessageInputEditText.MyImageSpan[] spans = noticePersons.getText().getSpans(start, start + 1, MessageInputEditText.MyImageSpan.class);
                    for (MessageInputEditText.MyImageSpan myImageSpan : spans) {
                        Receiver receiver = myImageSpan.getReceiver();
                        selectReceivers.remove(receiver);
                        break;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //设置机车选择dialog
        setNoticeTrainDialog();
        RxView.clicks(train).throttleFirst(5, TimeUnit.SECONDS)
                .compose(this.<Void>bindToLifecycle())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        selectPullTrainNotice = new PullTrainNotice();
                        selectPullTrainNoticeItem = new PullTrainNoticeItem();
                        if (selectTrainAdapter != null)
                            getPresenter().getPullTrainPlan("");
                    }
                });
        RxView.clicks(saveBtn).throttleFirst(5, TimeUnit.SECONDS)
                .compose(this.<Void>bindToLifecycle())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        validator.validate();
                    }
                });

        RxView.clicks(endPosition).throttleFirst(5, TimeUnit.SECONDS)
                .compose(this.<Void>bindToLifecycle())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        setOptionsPickerView();
                    }
                });
        baseDictPresenter.getDicDataForTransTrainEndPosition(true, new HttpOnNextListener() {
            @Override
            public void onNext(String result, String mothead) {
                List<DicDataItem> bean = JSON.parseObject(result, new TypeReference<List<DicDataItem>>() {
                });
                if (bean != null && bean.size() > 0) {
                    endPositions = bean;
                }
            }

            @Override
            public void onError(ApiException e) {

            }
        });
    }

    @Override
    public void initData() {
        try {
            selectPullTrainNotice = new PullTrainNotice();
            selectPullTrainNoticeItem = new PullTrainNoticeItem();
            detailBtn.setVisibility(View.GONE);
            noticePersons.setText("");
            train.setText("");
            startPosition.setText("");
            endPosition.setText("");
            endTime.setText("");
            noticeTime.setText(DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm"));
            remark.setText("");
        } catch (Exception ex) {
        }
    }

    /**
     * 初始化验证框架
     */
    public void initValidator() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @OnClick({R.id.pull_train_notice_edit_back, R.id.pull_train_notice_edit_detail_btn})
    void viewClick(View view) {
        switch (view.getId()) {
            case R.id.pull_train_notice_edit_back:
                finish();
                break;
            case R.id.pull_train_notice_edit_detail_btn:
                if (selectPullTrainNotice != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectPullTrainNotice", selectPullTrainNotice);
                    ActivityUtil.startActivityNotInActivity(view.getContext(), PullTrainNoticeDetailActivity.class, bundle);
                } else ToastUtil.toastShort("请选择要迁移的机车！");
                break;
            default:
                break;

        }
    }

    private void setNoticePersonDialog() {
        if (selectNoticePersonDialog == null) {
            View view = View.inflate(this, R.layout.pull_train_notice_select_person_dialog, null);
            RecyclerView rvList = (RecyclerView) view.findViewById(R.id.pull_train_notice_select_person_recycler);
            //TextView tvSelectedPersons = (TextView) view.findViewById(R.id.trans_notice_select_person_persons);
            Button btnCancel = (Button) view.findViewById(R.id.pull_train_notice_select_person_cancel);
            Button btnConfirm = (Button) view.findViewById(R.id.pull_train_notice_select_person_confirm);
            //设置可选择的机车列表
            selectPersonAdapter = new PullTrainNoticeSelectPersonAdapter();
            rvList.setAdapter(selectPersonAdapter);
            rvList.setLayoutManager(new LinearLayoutManager(view.getContext()));

            RxView.clicks(btnCancel)
                    .throttleFirst(5, TimeUnit.SECONDS)
                    .compose(this.<Void>bindToLifecycle())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            dismissSelectPersonDialog();
                        }
                    });
            RxView.clicks(btnConfirm)
                    .throttleFirst(5, TimeUnit.SECONDS)
                    .compose(this.<Void>bindToLifecycle())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            List<MessageGroupPerson> msgGroupPersons = selectPersonAdapter.getData();
                            Receiver r = null;
                            selectReceivers = new ArrayList<>();
                            for (MessageGroupPerson person : msgGroupPersons) {
                                if (person.isChecked) {
                                    r = new Receiver();
                                    r.setEmpId(person.getEmpId());
                                    r.setEmpName(person.getEmpName());
                                    r.setEmpPost(person.getEmpPost());
                                    //r.setGroupId(msgGroupId);
                                    selectReceivers.add(r);
                                }
                            }
                            if (selectReceivers != null && selectReceivers.size() > 0)
                                noticePersons.addSpansBeforeText(selectReceivers);
                            dismissSelectPersonDialog();
                        }
                    });
            selectNoticePersonDialog = new AlertDialog.Builder(this).setView(view).create();
            selectNoticePersonDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    selectPersonAdapter.setData(new ArrayList<MessageGroupPerson>());
                }
            });
        }
    }

    public void dismissSelectPersonDialog() {
        if (selectNoticePersonDialog != null)
            selectNoticePersonDialog.dismiss();
    }

    public void showSelectPersonDialog() {
        if (selectNoticePersonDialog != null)
            selectNoticePersonDialog.show();
    }

    private void setNoticeTrainDialog() {
        if (selectNoticeTrainDialog == null) {
            View view = View.inflate(this, R.layout.pull_train_notice_select_train_dialog, null);
            RecyclerView rvList = (RecyclerView) view.findViewById(R.id.pull_train_notice_select_train_recycler);
            EditText tvSelectedTrain = (EditText) view.findViewById(R.id.pull_train_notice_select_train_search);
            Button btnCancel = (Button) view.findViewById(R.id.pull_train_notice_select_train_cancel);
            Button btnConfirm = (Button) view.findViewById(R.id.pull_train_notice_select_train_confirm);
            selectTrainAdapter = new PullTrainNoticeSelectTrainAdapter(this);
            rvList.setAdapter(selectTrainAdapter);
            rvList.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
            RxTextView.textChanges(tvSelectedTrain)
                    .debounce(1, TimeUnit.SECONDS)
                    .compose(this.<CharSequence>bindToLifecycle())
                    .skip(1)
                    .map(new Func1<CharSequence, String>() {
                        @Override
                        public String call(CharSequence charSequence) {
                            return charSequence.toString();
                        }
                    }).observeOn(Schedulers.io())
                    .map(new Func1<String, List<PullTrainNotice>>() {
                        @Override
                        public List<PullTrainNotice> call(String s) {
                            List<PullTrainNotice> pList = new ArrayList<>();
                            if (s.toString().equals("请输入车号") || "".equals(s.toString())) {
                                pList = selectTrainAdapter.getDataAll();
                            } else {
                                for (int i = 0; i < selectTrainAdapter.getDataAll().size(); i++) {
                                    PullTrainNotice t = selectTrainAdapter.getDataAll().get(i);
                                    if (t.getTrainNo().contains(s))
                                        pList.add(t);
                                }
                            }
                            return pList;
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<PullTrainNotice>>() {
                        @Override
                        public void call(List<PullTrainNotice> trains) {
                            selectTrainAdapter.setData(trains);
                            selectTrainAdapter.notifyDataSetChanged();
                        }
                    });
            RxView.clicks(btnCancel)
                    .throttleFirst(5, TimeUnit.SECONDS)
                    .compose(this.<Void>bindToLifecycle())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            dismissSelectTrainDialog();
                        }
                    });
            RxView.clicks(btnConfirm)
                    .throttleFirst(5, TimeUnit.SECONDS)
                    .compose(this.<Void>bindToLifecycle())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            List<PullTrainNotice> trains = selectTrainAdapter.getData();
                            TransNoticeTrain snTrain = null;
                            for (PullTrainNotice train : trains) {

                            }
                            dismissSelectTrainDialog();
                        }
                    });
            selectNoticeTrainDialog = new AlertDialog.Builder(this).setView(view).create();
            selectNoticeTrainDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    selectTrainAdapter.setData(new ArrayList<PullTrainNotice>());
                }
            });
        }
    }

    public void dismissSelectTrainDialog() {
        if (selectNoticeTrainDialog != null)
            selectNoticeTrainDialog.dismiss();
    }

    public void showSelectTrainDialog() {
        if (selectNoticeTrainDialog != null)
            selectNoticeTrainDialog.show();
    }

    public void setPageBySelectPullTrainNotice(PullTrainNotice pullTrainNotice) {
        initData();
        if (pullTrainNotice == null) return;
        detailBtn.setVisibility(View.VISIBLE);
        selectPullTrainNotice = pullTrainNotice;
        train.setText(pullTrainNotice.getTrainTypeShortName() + " " + pullTrainNotice.getTrainNo());
        startPosition.setText(pullTrainNotice.getDictname());
        mPresenter.getNoConfirmPullTrainDetailItemByTrainPlanIdx(pullTrainNotice.getIdx());
    }

    @Override
    public void setPageByNoConfirmPullTrainDetailItem(PullTrainNoticeItem item) {
        try {
            if (item == null) return;
            selectPullTrainNoticeItem = item;
            endTime.setText(DateUtil.millisecondStr2DateStr(item.getLastTime(), "yyyy-MM-dd HH:mm:SS"));
            endPosition.setText(item.getDestination());
            if (TextUtils.isEmpty(item.getNoticeTime()))
                noticeTime.setText(item.getNoticeTime());
            remark.setText(item.getRemark());
            if (!TextUtils.isEmpty(item.getJsonAcceptPerson())) {
                selectReceivers = JSON.parseObject(item.getJsonAcceptPerson(), new TypeReference<List<Receiver>>() {
                });
                noticePersons.addSpansBeforeText(selectReceivers);
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void pageNotifyNewDataSetChangedForSelectPersonDialog(List<MessageGroupPerson> msgPersons) {
        try {
            if (selectPersonAdapter != null) {
                selectPersonAdapter.setData(msgPersons);
                selectPersonAdapter.notifyDataSetChanged();
                showSelectPersonDialog();
            }
        } catch (Exception ex) {
            LogUtil.e("牵车通知编辑页面，消息组人员编辑页面刷新，错误：", ex.toString());
        }
    }

    @Override
    public void pageNotifyNewDataSetChangedForSelectTrainDialog(List<PullTrainNotice> trains) {
        try {
            if (selectTrainAdapter != null) {
                selectTrainAdapter.setDataAll(trains);
                selectTrainAdapter.setData(trains);
                selectTrainAdapter.notifyDataSetChanged();
                showSelectTrainDialog();
            }
        } catch (Exception ex) {
            LogUtil.e("牵车通知编辑页面，机车选择信息列表刷新，错误：", ex.toString());
        }
    }

    @Override
    public void onValidationSucceeded() {
        try {
            if (selectPullTrainNotice == null) return;
            String groupId = selectPullTrainNotice.getGroupId();
            PullTrainNoticeItem item = new PullTrainNoticeItem();
            item.setIdx(selectPullTrainNoticeItem.getIdx());
            item.setTrainPlanIdx(selectPullTrainNotice.getIdx());
            item.setHomePosition(startPosition.getText().toString());
            item.setDestination(endPosition.getText().toString());
            item.setLastTime(endTime.getText().toString());
            item.setJsonAcceptPerson(JSON.toJSONString(selectReceivers));
            item.setNoticeTime(noticeTime.getText().toString());
            item.setConfirmStatus(TextUtils.isEmpty(selectPullTrainNoticeItem.getConfirmStatus()) ? "0" : selectPullTrainNoticeItem.getConfirmStatus());
            item.setConfirmPersonId(selectPullTrainNoticeItem.getConfirmPersonId());
            item.setConfirmPersonName(selectPullTrainNoticeItem.getConfirmPersonName());
            item.setConfirmTime(selectPullTrainNoticeItem.getConfirmTime());
            item.setUpdator(SysInfo.userInfo.emp.getEmpid().toString());
            item.setUpdatorName(SysInfo.userInfo.emp.getEmpname());
            item.setUpdateTime(DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:SS"));
            mPresenter.saveOrUpdateNotice(groupId, item);
        } catch (Exception ex) {
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String msgs = "";
        for (ValidationError error : errors) {
            msgs += error.getCollatedErrorMessage(this) + "\n";
        }
        if (!TextUtils.isEmpty(msgs)) {
            ToastUtil.toastShort(msgs);
        }
    }

    //设置选在起始位置控件
    public void setOptionsPickerView() {
        try {
            if (endPositions == null || endPositions.size() == 0) {
                ToastUtil.toastShort("系统基础数据字典中未配置起始地址或目的地址！");
                return;
            }
            OptionsPickerView options = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    DicDataItem Destination = endPositions.get(options1);
                    selectPullTrainNoticeItem.setDestination(Destination.getDictname());
                    endPosition.setText(Destination.getDictname());
                }
            })
                    .setTitleText("迁移台位")
                    .setSubmitText("确定")
                    .setCancelText("取消")
                    .isDialog(true).build();
            List<String> endList = new ArrayList<>();
            int p2 = 0;
            int n = 0;
            for (DicDataItem dicItem : endPositions) {
                endList.add(dicItem.getDictname());
                if (dicItem.getDictname().equals(endPosition.getText().toString()))
                    p2 = n;
                n++;
            }
            options.setSelectOptions(p2);
            options.setPicker(endList);
            options.show();
        } catch (Exception ex) {
        }
    }
}
