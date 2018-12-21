package jx.yunda.com.terminal.modules.tpApproval.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.base.baseDictData.model.DicDataItem;
import jx.yunda.com.terminal.base.baseDictData.presenter.BaseDictPresenter;
import jx.yunda.com.terminal.common.StringConstants;
import jx.yunda.com.terminal.modules.tpApproval.TPApprovalActivity;
import jx.yunda.com.terminal.modules.tpApproval.adapter.TicketBQDictAdapter;
import jx.yunda.com.terminal.modules.tpApproval.model.TPApproval;
import jx.yunda.com.terminal.modules.tpApproval.presenter.ITPApprovalContent;
import jx.yunda.com.terminal.modules.tpApproval.presenter.TPApprovalContentPresenter;
import jx.yunda.com.terminal.modules.tpprocess.adapter.SelectDictAdapter;
import jx.yunda.com.terminal.modules.tpprocess.adapter.TicketDictAdapter;
import jx.yunda.com.terminal.modules.tpprocess.model.DictBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.MyListView;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;


public class TPApprovalContentFragment extends BaseFragment<TPApprovalContentPresenter> implements ITPApprovalContent {
    BaseDictPresenter baseDictPresenter;
    private FaultTicket selectApproval;
    //页面标题
    @BindView(R.id.tp_approval_content_title)
    TextView title;
    //车型车号
    @BindView(R.id.tp_approval_content_train)
    TextView trainTypeAndNo;
    //故障位置
    @BindView(R.id.tp_approval_content_fault_position)
    TextView faultPosition;
    //不良状态
    @BindView(R.id.tp_approval_content_blzt)
    EditText blzt;
    //专业
    @BindView(R.id.tp_approval_content_zy)
    EditText zhuanYe;
    //考核
    @BindView(R.id.tp_approval_content_kh)
    EditText kaoHe;

    //施修方法
    @BindView(R.id.tp_approval_content_sxff)
    EditText shiXiuFangFa;

    //动态加载专业、考核 Start
    @BindView(R.id.lvEos)
    MyListView lvEos;
    TicketBQDictAdapter dictAdapter;
    List<DictBean> dictBeanList = new ArrayList<>();
    SelectDictAdapter selectDictAdapter;
    GridView gvTrains;
    int EquipPosition = 0;
    AlertDialog Dicdialog;
    //End

    public FaultTicket getSelectApproval() {
        return selectApproval;
    }

    public void setSelectApproval(FaultTicket selectApproval) {
        this.selectApproval = selectApproval;
    }

    @Override
    protected TPApprovalContentPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new TPApprovalContentPresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.tp_approval_content_fm;
    }

    @Override
    public void initSubViews(View view) {
        baseDictPresenter = new BaseDictPresenter(this);
        baseDictPresenter.getDictForBQ("ROOT_0", new HttpOnNextListener() {
            @Override
            public void onNext(String result, String mothead) {
                try {
                    List<DictBean> rstList = JSON.parseObject(result, new TypeReference<List<DictBean>>() {
                    });
                    OnDicLoadSuccess(rstList, -1);
                } catch (Exception e) {
                    ToastUtil.toastShort("获取基础数据失败");
                    LogUtil.e("获取基础数据失败：", e.toString());
                }
            }

            @Override
            public void onError(ApiException e) {
                ToastUtil.toastShort(e.getMessage());
                LogUtil.e("获取基础数据失败：", e.toString());
            }
        });
        dictAdapter = new TicketBQDictAdapter(getActivity(), dictBeanList);
        lvEos.setAdapter(dictAdapter);
        dictAdapter.setOnDicClickListner(new TicketBQDictAdapter.OnDicClickListner() {
            @Override
            public void OnDicClick(int position) {
                EquipPosition = position;
                if (dictBeanList.size() > 0) {
                    if (dictBeanList.get(position).isGetChildSuccess()) {
                        if (dictBeanList.get(position).getChildList() != null && dictBeanList.get(position).getChildList().size() > 0) {
                            View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_schedule_book, null);
                            gvTrains = (GridView) view.findViewById(R.id.gvTrains);
                            selectDictAdapter = new SelectDictAdapter(getActivity(), dictBeanList.get(position).getChildList());
                            gvTrains.setAdapter(selectDictAdapter);
                            Dicdialog = new AlertDialog.Builder(getActivity()).setView(view)
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
                        } else {
                            ToastUtil.toastShort("当前标签无可选择项");
                        }

                    } else {
                        ToastUtil.toastShort("当前标签数据还在加载请稍等");
                    }

                }
            }
        });


//        RxView.clicks(zhuanYe)
//                .throttleFirst(5, TimeUnit.SECONDS)
//                .compose(this.<Void>bindToLifecycle())
//                .subscribe(new Action1<Void>() {
//                    @Override
//                    public void call(Void aVoid) {
//                        baseDictPresenter.getDicDataBy_JXGC_Fault_Ticket_YYFX(new HttpOnNextListener() {
//                            @Override
//                            public void onNext(String result, String mothead) {
//                                BaseBean<List<DicDataItem>> bean = JSON.parseObject(result, new TypeReference<BaseBean<List<DicDataItem>>>() {
//                                });
//                                if (bean.getRoot() != null && bean.getRoot().size() > 0) {
//                                    final List<DicDataItem> majors = new ArrayList<>();
//                                    DicDataItem item = null;
//                                    for (int i = 0; i < bean.getRoot().size(); i++) {
//                                        if ("专业".equals(bean.getRoot().get(i).getDictname())) {
//                                            item = bean.getRoot().get(i);
//                                            break;
//                                        }
//                                    }
//                                    for (int i = 0; i < bean.getRoot().size(); i++) {
//                                        if (bean.getRoot().get(i).getDictid().length() > 2 &&
//                                                bean.getRoot().get(i).getDictid().substring(0, 2).equals(item.getDictid())) {
//                                            majors.add(bean.getRoot().get(i));
//                                        }
//                                    }
//                                    setOptionsPickerView("选择专业", zhuanYe, majors);
//                                }
//                            }
//
//                            @Override
//                            public void onError(ApiException e) {
//
//                            }
//                        });
//                    }
//                });
//        RxView.clicks(kaoHe)
//                .throttleFirst(5, TimeUnit.SECONDS)
//                .compose(this.<Void>bindToLifecycle())
//                .subscribe(new Action1<Void>() {
//                    @Override
//                    public void call(Void aVoid) {
//                        baseDictPresenter.getDicDataBy_JXGC_Fault_Ticket_YYFX(new HttpOnNextListener() {
//                            @Override
//                            public void onNext(String result, String mothead) {
//                                BaseBean<List<DicDataItem>> bean = JSON.parseObject(result, new TypeReference<BaseBean<List<DicDataItem>>>() {
//                                });
//                                if (bean.getRoot() != null && bean.getRoot().size() > 0) {
//                                    final List<DicDataItem> majors = new ArrayList<>();
//                                    DicDataItem item = null;
//                                    for (int i = 0; i < bean.getRoot().size(); i++) {
//                                        if ("考核".equals(bean.getRoot().get(i).getDictname())) {
//                                            item = bean.getRoot().get(i);
//                                            break;
//                                        }
//                                    }
//                                    for (int i = 0; i < bean.getRoot().size(); i++) {
//                                        if (bean.getRoot().get(i).getDictid().length() > 2 &&
//                                                bean.getRoot().get(i).getDictid().substring(0, 2).equals(item.getDictid())) {
//                                            majors.add(bean.getRoot().get(i));
//                                        }
//                                    }
//                                    setOptionsPickerView("选择考核", kaoHe, majors);
//                                }
//                            }
//
//                            @Override
//                            public void onError(ApiException e) {
//
//                            }
//                        });
//                    }
//                });

    }

    @Override
    public void initData() {
        if (selectApproval != null) {
            title.setText("");
            trainTypeAndNo.setText(selectApproval.getTrainTypeShortName() + " " + selectApproval.getTrainNo());
            faultPosition.setText(selectApproval.getFixPlaceFullName());
            blzt.setText(selectApproval.getFaultDesc());
            String[] reasonAnalysis = selectApproval.getReasonAnalysis().split(";");
            String[] reasonAnalysisIDs = selectApproval.getReasonAnalysisID().split(";");
            if (reasonAnalysis != null && reasonAnalysisIDs != null && reasonAnalysis.length > 0) {
                int len = reasonAnalysis.length;
                for (int i = 0; i < len; i++) {
                    String an = reasonAnalysis[i];
                    String anId = reasonAnalysisIDs[i];
                    EditText editText = StringConstants.DIC_JXGC_Fault_Ticket_YYFX_10.equals(anId.substring(0, 2)) ? zhuanYe : kaoHe;
                    if (TextUtils.isEmpty(editText.getText().toString())) {
                        editText.setText(an);
                        editText.setTag(anId);
                    } else {
                        editText.setText(editText.getText().toString() + ";" + an);
                        editText.setTag(editText.getTag() == null ? anId : (editText.getTag().toString() + ";" + anId));
                    }
                }
            }
            shiXiuFangFa.setText(selectApproval.getResolutionContent());
        }
    }

    @OnClick({R.id.tp_approval_content_back, R.id.tp_approval_content_submit})
    void viewClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.tp_approval_content_back:
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.remove(this);
                    transaction.commit();
                    break;
                case R.id.tp_approval_content_submit:
                    selectApproval.setFaultDesc(blzt.getText().toString());
                    selectApproval.setResolutionContent(shiXiuFangFa.getText().toString());
                    // String zy = zhuanYe.getTag() == null ? "" : zhuanYe.getText().toString();
                    // String zyId = zhuanYe.getTag() == null ? "" : zhuanYe.getTag().toString();
                    // String kh = kaoHe.getTag() == "" ? "" : kaoHe.getText().toString();
                    // String khId = kaoHe.getTag() == "" ? "" : kaoHe.getTag().toString();
                    // selectApproval.setReasonAnalysis(TextUtils.isEmpty(zy) ? kh : (zy + (TextUtils.isEmpty(kh) ? "" : (";" + kh))));
                    // selectApproval.setReasonAnalysisID(TextUtils.isEmpty(zyId) ? khId : (zyId + (TextUtils.isEmpty(khId) ? "" : (";" + khId))));
                    if (dictBeanList != null && dictBeanList.size() > 0) {
                        List<DictBean> temps = new ArrayList<>();
                        for (DictBean bean : dictBeanList) {
                            if (bean.getChildList() != null && bean.getChildList().size() > 0) {
                                for (DictBean child : bean.getChildList()) {
                                    if (child.isChecked()) {
                                        temps.add(child);
                                    }
                                }
                            }
                        }
                        if (temps.size() > 0) {
                            String str = "";
                            String strID = "";
                            for (int i = 0; i < temps.size(); i++) {
                                if (i == 0) {
                                    str = str + temps.get(i).getName();
                                    strID = strID + temps.get(i).getId();
                                } else {
                                    str = str + ";" + temps.get(i).getName();
                                    strID = strID + ";" + temps.get(i).getId();
                                }
                            }
                            selectApproval.setReasonAnalysis(str);
                            selectApproval.setReasonAnalysisID(strID);
                        }
                    }
                    getPresenter().submitTpApproval(selectApproval);
                    break;
                default:
                    break;

            }
        } catch (Exception ex) {
        }
    }


    void setOptionsPickerView(String pickerTitle, final EditText pickerView, final List<DicDataItem> items) {
        OptionsPickerView options = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                pickerView.setText(items.get(options1).getDictname());
                pickerView.setTag(items.get(options1).getDictid());
            }
        }).setTitleText(pickerTitle).setSubmitText("确定").build();
        List<String> list1 = new ArrayList<>();
        for (DicDataItem dicItem : items) {
            list1.add(dicItem.getDictname());
        }
        options.setPicker(list1);
        options.show();
    }

    @Override
    public void submitAfter() {
        ((TPApprovalActivity) this.getActivity()).initData();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(this);
        transaction.commit();
    }

    public void OnDicLoadSuccess(List<DictBean> list, int position) {
        if (list != null && list.size() > 0) {
            String[] reasonAnalysisIDs = selectApproval.getReasonAnalysisID().split(";");
            if (position == -1) {
                dictBeanList.clear();
                dictBeanList.addAll(list);
                for (int i = 0; i < dictBeanList.size(); i++) {
                    DictBean bean = dictBeanList.get(i);
                    final int p = i;
                    baseDictPresenter.getDictForBQ(bean.getId(), new HttpOnNextListener() {
                        @Override
                        public void onNext(String result, String mothead) {
                            try {
                                List<DictBean> rstList = JSON.parseObject(result, new TypeReference<List<DictBean>>() {
                                });
                                OnDicLoadSuccess(rstList, p);
                            } catch (Exception e) {
                                ToastUtil.toastShort("获取基础数据失败");
                                LogUtil.e("获取基础数据失败：", e.toString());
                            }
                        }

                        @Override
                        public void onError(ApiException e) {
                            ToastUtil.toastShort(e.getMessage());
                            LogUtil.e("获取基础数据失败：", e.toString());
                        }
                    });
                }
            } else {
                if (reasonAnalysisIDs != null) {
                    int len = reasonAnalysisIDs.length;
                    for (int m = 0; m < len; m++) {
                        String anId = reasonAnalysisIDs[m];
                        for (int n = 0; n < list.size(); n++) {
                            DictBean bean = list.get(n);
                            bean.setChecked(bean.getId().equals(anId));
                        }
                    }
                }
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
}
