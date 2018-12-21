package jx.yunda.com.terminal.modules.main.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.modules.main.MainActivity;
import jx.yunda.com.terminal.modules.main.adapter.MenuAdapter;
import jx.yunda.com.terminal.modules.main.presenter.HomePresenter;
import jx.yunda.com.terminal.modules.main.presenter.IHome;
import jx.yunda.com.terminal.utils.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements IHome {

    @BindView(R.id.home_train_linearLayout)
    LinearLayout homeTrainLinearLayout;
    @BindView(R.id.home_part_linearLayout)
    LinearLayout homePartLinearLayout;
    @BindView(R.id.home_train_recycler)
    RecyclerView homeTrainRecycler;
    @BindView(R.id.home_part_recycler)
    RecyclerView homePartRecycler;
    MenuAdapter trainMenuAdapter;
    MenuAdapter partMenuAdapter;

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).sendInitMsgToMsgServer();
        if(mPresenter!=null)
        mPresenter.getPurviewMenu();
    }

    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.main_home_fm;
    }

    @Override
    public void initSubViews(View view) {
        trainMenuAdapter = new MenuAdapter();
        partMenuAdapter = new MenuAdapter();
        setMenuRecyclerView(homeTrainRecycler, trainMenuAdapter);
//        setMenuRecyclerView(homePartRecycler, partMenuAdapter);
        mPresenter.getPurviewMenu();

    }

    private void setMenuRecyclerView(RecyclerView r, MenuAdapter adapter) {
        if (r == null) return;
        r.setLayoutManager(new GridLayoutManager(this.getContext(), 3, GridLayoutManager.VERTICAL, false));
        r.setAdapter(adapter);
    }

    @Override
    public void initData() {
        // 刷新TodoJob数量
    }

    public void setMenuAdapterData() {
        try {
            if (SysInfo.partMenus != null && SysInfo.partMenus.size() > 0) {
                homeTrainLinearLayout.setVisibility(View.VISIBLE);
                trainMenuAdapter.setData(SysInfo.partMenus);
                trainMenuAdapter.notifyDataSetChanged();
            } else
                homeTrainLinearLayout.setVisibility(View.GONE);
//            if (SysInfo.partMenus != null && SysInfo.partMenus.size() > 0) {
//                homePartLinearLayout.setVisibility(View.VISIBLE);
//                partMenuAdapter.setData(SysInfo.partMenus);
//                partMenuAdapter.notifyDataSetChanged();
//            } else {
//                homePartLinearLayout.setVisibility(View.GONE);
//            }
            mPresenter.getTodoJobs();
        } catch (Exception ex) {
            LogUtil.e("模块导航设置错误", ex.getMessage());
        }
    }

    @Override
    public void recyclerAdapterNotifyDataSetChanged() {
        trainMenuAdapter.notifyDataSetChanged();
        partMenuAdapter.notifyDataSetChanged();
    }
}
