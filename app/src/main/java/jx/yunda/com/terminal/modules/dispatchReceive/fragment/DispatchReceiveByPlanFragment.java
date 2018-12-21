package jx.yunda.com.terminal.modules.dispatchReceive.fragment;


import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.modules.dispatchReceive.presenter.DispatchReceiveByPlanPresenter;
import jx.yunda.com.terminal.modules.dispatchReceive.presenter.IDispatchReceiveByPlan;
import jx.yunda.com.terminal.utils.LogUtil;

public class DispatchReceiveByPlanFragment extends BaseFragment<DispatchReceiveByPlanPresenter> implements IDispatchReceiveByPlan {

    @Override
    protected DispatchReceiveByPlanPresenter getPresenter() {
        return null;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.dispatch_receive_by_plan_fm;
    }

    @Override
    public void initSubViews(View view) {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.dispatch_receive_by_plan_back})
    void viewClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.dispatch_receive_by_plan_back:
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.remove(this);
                    transaction.commit();
                    break;
                default:
                    break;
            }

        } catch (Exception ex) {
            LogUtil.e("计划内接车操作页面返回", ex.toString());
        }
    }
}
