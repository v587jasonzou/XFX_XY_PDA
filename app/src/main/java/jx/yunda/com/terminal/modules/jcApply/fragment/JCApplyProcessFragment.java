package jx.yunda.com.terminal.modules.jcApply.fragment;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.modules.jcApply.adapter.JCApplyProcessAdapter;
import jx.yunda.com.terminal.modules.jcApply.model.JCApply;
import jx.yunda.com.terminal.modules.jcApply.model.JCApplyProcess;
import jx.yunda.com.terminal.modules.jcApply.presenter.IJCApplyProcess;
import jx.yunda.com.terminal.modules.jcApply.presenter.JCApplyProcessPresenter;
import jx.yunda.com.terminal.utils.LogUtil;

/**
 * <li>说明：交车申请：流程查看
 * <li>创建人：zhubs
 * <li>创建日期：2018年8月20日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 */
public class JCApplyProcessFragment extends BaseFragment<JCApplyProcessPresenter> implements IJCApplyProcess {

    private JCApply selectApply;
    JCApplyProcessAdapter adapter;
    @BindView(R.id.jc_apply_process_recycler)
    RecyclerView recycler;
    @BindView(R.id.jc_apply_process_title)
    TextView title;

    public void setSelectApply(JCApply selectApply) {
        this.selectApply = selectApply;
    }

    @Override
    protected JCApplyProcessPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new JCApplyProcessPresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.jc_apply_process_fm;
    }

    @Override
    public void initSubViews(View view) {
        if (selectApply != null)
            title.setText("【" + selectApply.getTrainTypeShortName() + " " + selectApply.getTrainNo() + "】" + (TextUtils.isEmpty(selectApply.getRepairClassName()) ? "" : selectApply.getRepairClassName()) + " " + (TextUtils.isEmpty(selectApply.getEndTime()) ? "" : selectApply.getEndTime()));
        adapter = new JCApplyProcessAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
    }

    @Override
    public void initData() {
        if (selectApply != null)
            getPresenter().getProcessByProcessInstID(this.selectApply.getProcessInstId());
    }

    @OnClick({R.id.jc_apply_process_back})
    void viewClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.jc_apply_process_back:
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.remove(this);
                    transaction.commit();
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            LogUtil.e("交车申请流程进度页面", ex.toString());
        }
    }

    @Override
    public void recyclerNotifyDataSetChanged(List<JCApplyProcess> datas) {
        try {
            adapter.setData(datas);
            adapter.notifyDataSetChanged();
        } catch (Exception ex) {
            LogUtil.e("交车申请进度面列表刷新", ex.toString());
        }
    }
}
