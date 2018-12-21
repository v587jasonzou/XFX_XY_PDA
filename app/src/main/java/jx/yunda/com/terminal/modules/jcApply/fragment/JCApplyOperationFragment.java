package jx.yunda.com.terminal.modules.jcApply.fragment;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.modules.jcApply.JCApplyActivity;
import jx.yunda.com.terminal.modules.jcApply.adapter.JCApplyOperationWorkContentAdapter;
import jx.yunda.com.terminal.modules.jcApply.adapter.JCApplyOperationWorkTypeAdapter;
import jx.yunda.com.terminal.modules.jcApply.model.JCApply;
import jx.yunda.com.terminal.modules.jcApply.model.JCApplyOperationWorkContent;
import jx.yunda.com.terminal.modules.jcApply.model.JCApplyOperationWorkType;
import jx.yunda.com.terminal.modules.jcApply.presenter.JCApplyOperationPresenter;
import jx.yunda.com.terminal.modules.jcApply.presenter.IJCApplyOperation;
import jx.yunda.com.terminal.utils.LogUtil;

/**
 * <li>说明：交车申请：申请业务模块
 * <li>创建人：zhubs
 * <li>创建日期：2018年8月20日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 */
public class JCApplyOperationFragment extends BaseFragment<JCApplyOperationPresenter> implements IJCApplyOperation {
    private JCApply selectApply;
    private JCApplyOperationWorkType selectWorkType;
    JCApplyOperationWorkTypeAdapter workTypeAdapter;
    JCApplyOperationWorkContentAdapter workContentAdapter;
    @BindView(R.id.jc_apply_operation_work_type_recycler)
    RecyclerView workTypeRecycler;
    @BindView(R.id.jc_apply_operation_work_content_recycler)
    RecyclerView workContentRecycler;
    @BindView(R.id.jc_apply_operation_title)
    TextView title;

    public JCApply getSelectApply() {
        return selectApply;
    }

    public void setSelectApply(JCApply selectApply) {
        this.selectApply = selectApply;
    }

    public JCApplyOperationWorkType getSelectWorkType() {
        return selectWorkType;
    }

    public void setSelectWorkType(JCApplyOperationWorkType selectWorkType) {
        this.selectWorkType = selectWorkType;
    }

    @Override
    public JCApplyOperationPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new JCApplyOperationPresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.jc_apply_operation_fm;
    }

    @Override
    public void initSubViews(View view) {
        workTypeAdapter = new JCApplyOperationWorkTypeAdapter(this);
        workContentAdapter = new JCApplyOperationWorkContentAdapter(this);
        workTypeRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayout.VERTICAL, false));
        workTypeRecycler.setAdapter(workTypeAdapter);
        workContentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        workContentRecycler.setAdapter(workContentAdapter);
        if (selectApply != null)
            title.setText("【" + selectApply.getTrainTypeShortName() + " " + selectApply.getTrainNo() + "】" + (TextUtils.isEmpty(selectApply.getRepairClassName()) ? "" : selectApply.getRepairClassName()) + " " + (TextUtils.isEmpty(selectApply.getEndTime()) ? "" : selectApply.getEndTime()));
    }

    @Override
    public void initData() {
        getPresenter().getWorkTypes(selectApply.getWorkPlanIdx());
    }

    @OnClick({R.id.jc_apply_operation_back, R.id.jc_apply_operation_submit})
    void viewClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.jc_apply_operation_back:
                    closeJCApplyOperationFragment();
                    break;
                case R.id.jc_apply_operation_submit:
                    getPresenter().submitApply(selectApply);
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            LogUtil.e("交车申请操作页面", ex.toString());
        }
    }

    @Override
    public void closeJCApplyOperationFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(this);
        transaction.commit();
        ((JCApplyActivity) getActivity()).setPageData();
    }

    @Override
    public void workTypeRecyclerNotifyDataSetChanged(List<JCApplyOperationWorkType> datas) {
        try {
            workTypeAdapter.setData(datas == null ? new ArrayList<JCApplyOperationWorkType>() : datas);
            workTypeAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            LogUtil.e("交车申请主页面列表刷新", ex.toString());
        }
    }

    @Override
    public void workContentRecyclerNotifyDataSetChanged(List<JCApplyOperationWorkContent> datas) {
        try {
            workContentAdapter.setData(datas == null ? new ArrayList<JCApplyOperationWorkContent>() : datas);
            workContentAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            LogUtil.e("交车申请主页面列表刷新", ex.toString());
        }
    }

    @Override
    public void workContentRecyclerNotifyItemChanged(int position, JCApplyOperationWorkContent workContent) {
        try {
            workContentAdapter.getData().get(position).isShowBackBtn = workContent.isShowBackBtn;
            workContentAdapter.notifyItemChanged(position);
        } catch (Exception ex) {
            LogUtil.e("交车申请主页面列表刷新", ex.toString());
        }
    }
}
