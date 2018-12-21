package jx.yunda.com.terminal.modules.quality.frg;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.modules.FJ_ticket.model.RecheckTrainBean;
import jx.yunda.com.terminal.modules.quality.act.QualityActivity;
import jx.yunda.com.terminal.modules.quality.act.TrainQulityActivity;
import jx.yunda.com.terminal.modules.quality.adapter.TrainAdapter;
import jx.yunda.com.terminal.modules.quality.model.QualityTrainBean;
import jx.yunda.com.terminal.modules.quality.presenter.ITrainFrg;
import jx.yunda.com.terminal.modules.quality.presenter.TrainFrgPresenter;
import jx.yunda.com.terminal.modules.tpprocess.TP_ProcessActivity;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTrainBean;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class TrainFrg extends BaseFragment<TrainFrgPresenter> implements ITrainFrg {
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.gvCar)
    GridView gvCar;
    @BindView(R.id.sv_ticketTrain)
    SmartRefreshLayout svTicketTrain;
    @BindView(R.id.btNext)
    Button btNext;
    Unbinder unbinder;
    List<QualityTrainBean> mlist = new ArrayList<>();
    List<QualityTrainBean> mListSearch = new ArrayList<>();
    TrainAdapter adapter ;
    int clickPosition;
    QualityActivity act;
    @Override
    protected TrainFrgPresenter getPresenter() {
        return new TrainFrgPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_train_quality;
    }

    @Override
    public void initSubViews(View view) {

    }

    @Override
    public void initData() {
        act = (QualityActivity)getActivity();
        svTicketTrain.setRefreshHeader(new ClassicsHeader(getContext()));
        svTicketTrain.setRefreshFooter(new ClassicsFooter(getContext()));
        svTicketTrain.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                act.getProgressDialog().show();
                mPresenter.getTrainList(SysInfo.userInfo.emp.getOperatorid() + "", "");
            }
        });
        adapter = new TrainAdapter(getContext(),mlist);
        gvCar.setAdapter(adapter);
        gvCar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i = 0;i<mlist.size();i++){
                    if (i==position){
                        mlist.get(i).setState(1);
                    }else {
                        mlist.get(i).setState(0);
                    }
                }
                clickPosition = position;
                adapter.notifyDataSetChanged();
                btNext.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_selector));
                btNext.setEnabled(true);
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btNext.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.button_selector_gray));
                btNext.setEnabled(false);
                s = s.toString().toUpperCase();
                if (mListSearch.size() > 0) {
                    if ("车型、车号".equals(s.toString()) || "".equals(s.toString())) {
                        mlist.clear();
                        for (QualityTrainBean bean : mListSearch) {
                            if (bean.getState() == 1) {
                                bean.setState(0);
                            }
                        }
                        mlist.addAll(mListSearch);
                        adapter.notifyDataSetChanged();
                    } else {
                        mlist.clear();
                        for (QualityTrainBean bean : mListSearch) {
                            if (bean.getState() == 1) {
                                bean.setState(0);
                            }
                        }
                        for (int i = 0; i < mListSearch.size(); i++) {
                            if (mListSearch.get(i).getTrainNo().contains(s.toString()) ||
                                    mListSearch.get(i).getTrainTypeShortname().contains(s.toString())) {
                                mlist.add(mListSearch.get(i));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        act.getProgressDialog().show();
        mPresenter.getTrainList(SysInfo.userInfo.emp.getOperatorid() + "", "");
        svTicketTrain.setNoMoreData(true);
    }

    @Override
    public void OnLoadTrainSuccess(List<QualityTrainBean> list) {
        act.getProgressDialog().dismiss();
        svTicketTrain.finishRefresh();
        mlist.clear();
        mListSearch.clear();
        if(list!=null){
            mlist.addAll(list);
            mListSearch.addAll(list);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnLoadMoreTrainSuccess(List<QualityTrainBean> list) {

    }

    @Override
    public void OnLoadTrainFaild(String msg) {
        act.getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
        svTicketTrain.finishRefresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.btNext)
    void OnNext(){
        Bundle bundle  = new Bundle();
        bundle.putString("planName",mlist.get(clickPosition).getTrainTypeShortname()+" "+mlist.get(clickPosition)
        .getTrainNo());
        bundle.putString("workPlanId",mlist.get(clickPosition).getWorkPlanIdx());
        ActivityUtil.startActivityResultWithDelayed(getActivity(),TrainQulityActivity.class,bundle,200);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
