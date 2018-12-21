package jx.yunda.com.terminal.modules.transNotice.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
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
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.base.baseDictData.model.DicDataItem;
import jx.yunda.com.terminal.base.baseDictData.presenter.BaseDictPresenter;
import jx.yunda.com.terminal.modules.message.model.MessageGroupPerson;
import jx.yunda.com.terminal.modules.message.model.Receiver;
import jx.yunda.com.terminal.modules.message.widget.MessageInputEditText;
import jx.yunda.com.terminal.modules.transNotice.TransNoticeActivity;
import jx.yunda.com.terminal.modules.transNotice.adapter.TransNoticeSelectPersonAdapter;
import jx.yunda.com.terminal.modules.transNotice.adapter.TransNoticeSelectTrainAdapter;
import jx.yunda.com.terminal.modules.transNotice.adapter.TransNoticeTrainAdapter;
import jx.yunda.com.terminal.modules.transNotice.model.TransNotice;
import jx.yunda.com.terminal.modules.transNotice.model.TransNoticeTrain;
import jx.yunda.com.terminal.modules.transNotice.model.Train;
import jx.yunda.com.terminal.modules.transNotice.presenter.ITransNoticeEdit;
import jx.yunda.com.terminal.modules.transNotice.presenter.TransNoticeEditPresenter;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.utils.DateUtil;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.DateTimeText;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class TransNoticeEditFragment extends BaseFragment<TransNoticeEditPresenter> implements ITransNoticeEdit {
    BaseDictPresenter baseDictPresenter;
    List<DicDataItem> startPositions = new ArrayList<>();
    List<DicDataItem> endPositions = new ArrayList<>();
    TransNoticeTrainAdapter editTrainAdapter;
    @BindView(R.id.trans_notice_edit_recycler)
    RecyclerView editTrainRecycler;
    @BindView(R.id.trans_notice_edit_name)
    EditText noticeName;
    @BindView(R.id.trans_notice_edit_select_train)
    LinearLayout selectTrain;
    @BindView(R.id.trans_notice_edit_department)
    TextView department;
    @BindView(R.id.trans_notice_edit_time)
    DateTimeText noticeTime;
    @BindView(R.id.trans_notice_edit_person)
    MessageInputEditText noticePerson;
    @BindView(R.id.trans_notice_edit_submit)
    Button editSubmit;
    @BindView(R.id.trans_notice_edit_save)
    Button editSave;

    TransNoticeSelectPersonAdapter selectPersonAdapter;
    TransNoticeSelectTrainAdapter selectTrainAdapter;
    AlertDialog selectNoticePersonDialog;
    AlertDialog selectNoticeTrainDialog;

    String msgGroupId;
    List<Receiver> selectReceivers = new ArrayList<>();
    List<TransNoticeTrain> selectTransNoticeTrains = new ArrayList<>();
    TransNotice selectTransNotice;

    @Override
    public TransNoticeEditPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new TransNoticeEditPresenter(this);
        return mPresenter;
    }

    public String getMsgGroupId() {
        return msgGroupId;
    }

    public void setMsgGroupId(String msgGroupId) {
        this.msgGroupId = msgGroupId;
    }

    public TransNotice getSelectTransNotice() {
        return selectTransNotice;
    }

    public void setSelectTransNotice(TransNotice selectTransNotice) {
        this.selectTransNotice = selectTransNotice;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.trans_notice_edit_fm;
    }

    @Override
    public void initSubViews(View view) {
        selectTransNoticeTrains.clear();
        baseDictPresenter = new BaseDictPresenter(this);
        //设置通知人选择编辑dialog
        setNoticePersonDialog();
        RxView.clicks(noticePerson).throttleFirst(5, TimeUnit.SECONDS)
                .compose(this.<Void>bindToLifecycle())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (selectPersonAdapter != null)
                            selectPersonAdapter.setSelectReceivers(getSelectReceivers());
                        //getPresenter().getGroupPersons(msgGroupId);
                        getPresenter().getGroupPersonsByPostName("地勤");
                    }
                });
        noticePerson.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
                if (lengthBefore > 1 && lengthAfter == 0) {
                    MessageInputEditText.MyImageSpan[] spans = noticePerson.getText().getSpans(start, start + 1, MessageInputEditText.MyImageSpan.class);
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
        RxView.clicks(selectTrain).throttleFirst(5, TimeUnit.SECONDS)
                .compose(this.<Void>bindToLifecycle())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        getPresenter().getSelectTrains();
                    }
                });

        //设置调车编辑
        editTrainAdapter = new TransNoticeTrainAdapter(this);
        editTrainRecycler.setAdapter(editTrainAdapter);
        editTrainRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        //保存按钮，保存事件
        RxView.clicks(editSave).throttleFirst(5, TimeUnit.SECONDS)
                .compose(this.<Void>bindToLifecycle())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        operateTransNotice(false);
                    }
                });
        //确定按钮，保存事件
        RxView.clicks(editSubmit).throttleFirst(5, TimeUnit.SECONDS)
                .compose(this.<Void>bindToLifecycle())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        operateTransNotice(true);
                    }
                });
    }

    @Override
    public void initData() {
        noticePerson.addSpansBeforeText(selectReceivers);
        editTrainAdapter.setSubmitNotice(false);
        if (selectTransNotice == null) {
            editSave.setVisibility(View.VISIBLE);
            editSubmit.setVisibility(View.VISIBLE);
            initPage();
        } else {
            editTrainAdapter.setSubmitNotice("1".equals(selectTransNotice.getSubmitStatus()));
            editSave.setVisibility("1".equals(selectTransNotice.getSubmitStatus()) ? View.GONE : View.VISIBLE);
            editSubmit.setVisibility("1".equals(selectTransNotice.getSubmitStatus()) ? View.GONE : View.VISIBLE);
            noticeName.setEnabled(false);
            noticeName.setText(selectTransNotice.getNoticeName());
            department.setText(selectTransNotice.getDepartment());
            noticeTime.setText(selectTransNotice.getNoticeTime());
            if (TextUtils.isEmpty(selectTransNotice.getJsonAcceptPerson()))
                selectReceivers = new ArrayList<>();
            else
                selectReceivers = JSON.parseObject(selectTransNotice.getJsonAcceptPerson(), new TypeReference<ArrayList<Receiver>>() {
                });
            getPresenter().getTransNoticeTrains(selectTransNotice.getIdx());
        }
        baseDictPresenter.getDicDataForTransTrainStartPosition(true, new HttpOnNextListener() {
            @Override
            public void onNext(String result, String mothead) {
                List<DicDataItem> bean = JSON.parseObject(result, new TypeReference<List<DicDataItem>>() {
                });
                if (bean != null && bean.size() > 0) {
                    startPositions = bean;
                }
            }

            @Override
            public void onError(ApiException e) {

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

    private void initPage() {
        try {
            selectReceivers = new ArrayList<>();
            selectTransNoticeTrains = new ArrayList<>();
            noticeName.setEnabled(true);
            getPresenter().getDefaultNoticeName();
            department.setText(SysInfo.userInfo.org.getOrgname());
            noticeTime.setText(DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm"));
            noticePerson.setText("");
            selectTransNoticeTrains.clear();
            editTrainAdapter.setData(selectTransNoticeTrains);
            editTrainAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
        }
    }

    private void setNoticePersonDialog() {
        if (selectNoticePersonDialog == null) {
            View view = View.inflate(getContext(), R.layout.trans_notice_select_person_dialog, null);
            RecyclerView rvList = (RecyclerView) view.findViewById(R.id.trans_notice_select_person_recycler);
            //TextView tvSelectedPersons = (TextView) view.findViewById(R.id.trans_notice_select_person_persons);
            Button btnCancel = (Button) view.findViewById(R.id.trans_notice_select_person_cancel);
            Button btnConfirm = (Button) view.findViewById(R.id.trans_notice_select_person_confirm);
            //设置可选择的机车列表
            selectPersonAdapter = new TransNoticeSelectPersonAdapter();
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
                                    r.setGroupId(msgGroupId);
                                    selectReceivers.add(r);
                                }
                            }
                            if (selectReceivers != null && selectReceivers.size() > 0)
                                noticePerson.addSpansBeforeText(selectReceivers);
                            dismissSelectPersonDialog();
                        }
                    });
            selectNoticePersonDialog = new AlertDialog.Builder(getContext()).setView(view).create();
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
            View view = View.inflate(getContext(), R.layout.trans_notice_select_train_dialog, null);
            RecyclerView rvList = (RecyclerView) view.findViewById(R.id.trans_notice_select_train_recycler);
            EditText tvSelectedTrain = (EditText) view.findViewById(R.id.trans_notice_select_train_search);
            Button btnCancel = (Button) view.findViewById(R.id.trans_notice_select_train_cancel);
            Button btnConfirm = (Button) view.findViewById(R.id.trans_notice_select_train_confirm);
            selectTrainAdapter = new TransNoticeSelectTrainAdapter();
            rvList.setAdapter(selectTrainAdapter);
            rvList.setLayoutManager(new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false));
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
                    .map(new Func1<String, List<Train>>() {
                        @Override
                        public List<Train> call(String s) {
                            List<Train> pList = new ArrayList<>();
                            if (s.toString().equals("请输入车号") || "".equals(s.toString())) {
                                pList = selectTrainAdapter.getDataAll();
                            } else {
                                for (int i = 0; i < selectTrainAdapter.getDataAll().size(); i++) {
                                    Train t = selectTrainAdapter.getDataAll().get(i);
                                    if (t.getTrainNo().contains(s))
                                        pList.add(t);
                                }
                            }
                            return pList;
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<Train>>() {
                        @Override
                        public void call(List<Train> trains) {
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
                            List<Train> trains = selectTrainAdapter.getData();
                            TransNoticeTrain snTrain = null;
                            for (Train train : trains) {
                                if (train.isChecked) {
                                    snTrain = new TransNoticeTrain();
                                    snTrain.setTrainNo(train.getTrainNo());
                                    snTrain.setTrainTypeIDX(train.getTrainTypeIDX());
                                    snTrain.setTrainTypeShortName(train.getTrainTypeShortName());
                                    snTrain.setdName(train.getdName());
                                    snTrain.setdId(train.getdId());
                                    snTrain.setdShortName(train.getdShortName());
                                    snTrain.setNoticeIDX(selectTransNotice == null ? "" : selectTransNotice.getIdx());
                                    pageAddNewDataSetChangedForNoticeTrain(snTrain);
                                    selectTransNoticeTrains.add(snTrain);
                                }
                            }
                            dismissSelectTrainDialog();
                        }
                    });
            selectNoticeTrainDialog = new AlertDialog.Builder(getContext()).setView(view).create();
            selectNoticeTrainDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    selectTrainAdapter.setData(new ArrayList<Train>());
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

    @Override
    public void pageNotifyNewDataSetChangedForSelectPersonDialog(List<MessageGroupPerson> msgPersons) {
        try {
            if (selectPersonAdapter != null) {
                selectPersonAdapter.setData(msgPersons);
                selectPersonAdapter.notifyDataSetChanged();
                showSelectPersonDialog();
            }
        } catch (Exception ex) {
            LogUtil.e("消息组人员编辑页面刷新", ex.toString());
        }
    }

    @Override
    public void pageNotifyNewDataSetChangedForSelectTrainDialog(List<Train> trains) {
        try {
            if (trains != null && trains.size() > 0 && getSelectTransNoticeTrains() != null && getSelectTransNoticeTrains().size() > 0) {
                for (TransNoticeTrain t : getSelectTransNoticeTrains()) {
                    for (Train t2 : trains) {
                        if ((t2.getTrainTypeShortName() + t2.getTrainNo()).equals(t.getTrainTypeShortName() + t.getTrainNo())) {
                            trains.remove(t2);
                            break;
                        }
                    }
                }
            }
            if (selectTrainAdapter != null) {
                selectTrainAdapter.setDataAll(trains);
                selectTrainAdapter.setData(trains);
                selectTrainAdapter.notifyDataSetChanged();
                showSelectTrainDialog();
            }
        } catch (Exception ex) {
            LogUtil.e("调车通知添加页面，机车选择信息列表刷新", ex.toString());
        }
    }

    @Override
    public void pageNotifyNewDataSetChangedForNoticeTrain(List<TransNoticeTrain> trains) {
        try {
            if (selectReceivers != null && selectReceivers.size() > 0)
                noticePerson.addSpansBeforeText(selectReceivers);
            if (editTrainAdapter != null) {
                selectTransNoticeTrains.clear();
                selectTransNoticeTrains = trains;
                editTrainAdapter.setData(trains);
                editTrainAdapter.notifyDataSetChanged();
            }
        } catch (Exception ex) {
            LogUtil.e("调车通知添加页面，机车信息列表刷新", ex.toString());
        }
    }

    @Override
    public void pageAddNewDataSetChangedForNoticeTrain(TransNoticeTrain train) {
        try {
            if (editTrainAdapter != null) {
                editTrainAdapter.getData().add(train);
                editTrainAdapter.notifyItemInserted(editTrainAdapter.getItemCount() - 1);
            }
        } catch (Exception ex) {
            LogUtil.e("调车通知添加页面，机车信息列表刷新", ex.toString());
        }
    }

    @Override
    public void setNoticeDefaultName(String name) {
        noticeName.setText(name);
    }

    @Override
    public void closeFragment(boolean isNewRefresh) {
        if (isNewRefresh) {
            if (selectTransNotice == null) {
                initPage();
            } else {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.remove(this);
                transaction.commit();
                ((TransNoticeActivity) getActivity()).setMainPageList(0);
            }
        } else {
            getActivity().finish();
        }
    }

    //设置选在起始位置控件
    public void setOptionsPickerView(final EditText startView, final EditText endView, final TransNoticeTrain data) {
        try {
            if (startPositions == null || startPositions.size() == 0 || endPositions == null || endPositions.size() == 0) {
                ToastUtil.toastShort("系统基础数据字典中未配置起始地址或目的地址！");
                return;
            }
            OptionsPickerView options = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    DicDataItem home = startPositions.get(options1);
                    DicDataItem Destination = endPositions.get(options2);
                    data.setHomePosition(home.getDictname());
                    data.setHomePositionId(home.getDictid());
                    data.setHomePositionConfig(home.getFilter1());
                    data.setDestination(Destination.getDictname());
                    data.setDestinationId(Destination.getDictid());
                    data.setDestinationConfig(Destination.getFilter1());
                    startView.setText(home.getDictname());
                    endView.setText(Destination.getDictname());
                }
            })
                    .setTitleText("机车位置")
                    .setSubmitText("确定")
                    .setCancelText("取消")
                    .isDialog(true).build();
            List<String> startList = new ArrayList<>();
            List<String> endList = new ArrayList<>();
            List<List<String>> options2Items = new ArrayList<>();
            int p1 = 0;
            int p2 = 0;
            int m = 0;
            int n = 0;
            for (DicDataItem dicItem : endPositions) {
                endList.add(dicItem.getDictname());
                if (dicItem.getDictname().equals(endView.getText().toString()))
                    p2 = n;
                n++;
            }
            for (DicDataItem dicItem : startPositions) {
                startList.add(dicItem.getDictname());
                options2Items.add(endList);
                if (dicItem.getDictname().equals(startView.getText().toString()))
                    p1 = m;
                m++;
            }
            options.setSelectOptions(p1, p2);
            List<String> options1Items = startList;
            options.setPicker(options1Items, options2Items);
            options.show();
        } catch (Exception ex) {
        }
    }

    @OnClick({R.id.trans_notice_edit_back, R.id.trans_notice_edit_list_view})
    void viewClick(View view) {
        switch (view.getId()) {
            case R.id.trans_notice_edit_back:
                closeFragment(!(selectTransNotice == null));
                break;
            case R.id.trans_notice_edit_list_view:
                showListActivity();
                break;
            default:
                break;
        }
    }

    private void showListActivity() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(this);
        transaction.commit();
        TransNoticeActivity listActivity = (TransNoticeActivity) getActivity();
        listActivity.setMainPageList(0);
    }

    public List<Receiver> getSelectReceivers() {
        return selectReceivers;
    }

    public void setSelectReceivers(ArrayList<Receiver> selectReceivers) {
        this.selectReceivers = selectReceivers;
    }

    public List<TransNoticeTrain> getSelectTransNoticeTrains() {
        return selectTransNoticeTrains;
    }

    public void setSelectTransNoticeTrains(List<TransNoticeTrain> selectTrains) {
        this.selectTransNoticeTrains = selectTrains;
    }

    void operateTransNotice(boolean isSubmit) {
        try {
            TransNotice transNotice = selectTransNotice == null ? new TransNotice() : selectTransNotice;
            if (selectTransNoticeTrains == null || selectTransNoticeTrains.size() == 0) {
                ToastUtil.toastShort("请添加机车信息！");
                return;
            }
            List<TransNoticeTrain> transNoticeTrains = selectTransNoticeTrains == null ? new ArrayList<TransNoticeTrain>() : selectTransNoticeTrains;
            transNotice.setNoticeName(noticeName.getText().toString());
            transNotice.setDepartment(department.getText().toString());
            transNotice.setNoticeTime(noticeTime.getText().toString());
            transNotice.setJsonAcceptPerson(JSON.toJSONString(selectReceivers));
            for (TransNoticeTrain t : transNoticeTrains) {
                t.setUpdatorName(SysInfo.userInfo.emp.getEmpname());
            }
            if (isSubmit)
                getPresenter().submitTransNotice(transNotice, transNoticeTrains);
            else
                getPresenter().saveTransNotice(transNotice, transNoticeTrains);
        } catch (Exception e) {
            ToastUtil.toastShort(e.getMessage());
            LogUtil.e("调车通知编辑页面，保存", e.toString());
        }
    }

    public void initPage(String gId, TransNotice sn) {
        msgGroupId = gId;
        selectTransNotice = sn;
        selectReceivers = new ArrayList<>();
        selectTransNoticeTrains = new ArrayList<>();
    }
}
