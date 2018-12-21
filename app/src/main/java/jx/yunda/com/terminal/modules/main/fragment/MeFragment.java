package jx.yunda.com.terminal.modules.main.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.message.netty.service.NettyService;
import jx.yunda.com.terminal.message.okhttp.client.manager.WebSocketManager;
import jx.yunda.com.terminal.modules.login.LoginActivity;
import jx.yunda.com.terminal.modules.main.MainActivity;
import jx.yunda.com.terminal.modules.main.presenter.IMe;
import jx.yunda.com.terminal.modules.main.presenter.MePresenter;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment<MePresenter> implements IMe {

    @BindView(R.id.me_username)
    TextView meUsername;
    @BindView(R.id.me_job_num)
    TextView meJobNumber;
    @BindView(R.id.me_posiname)
    TextView mePosiname;
    @BindView(R.id.me_orgname)
    TextView meOrgname;

    @Override
    protected MePresenter getPresenter() {
        return new MePresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.main_me_fm;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).sendInitMsgToMsgServer();
    }

    @Override
    public void initSubViews(View view) {

    }

    @Override
    public void initData() {
        try {
            if (SysInfo.userInfo != null) {
                meUsername.setText(SysInfo.userInfo.emp.getEmpname());
                meJobNumber.setText(SysInfo.userInfo.emp.getUserid() + "");
                mePosiname.setText(SysInfo.userInfo.posiname);
                meOrgname.setText(SysInfo.userInfo.org.getOrgname());
            }
        } catch (Exception ex) {
            LogUtil.e("设置当前登录人信息：", ex.toString());
        }
    }

    @OnClick(R.id.login_out_btn)
    void viewClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.login_out_btn:
                    SysInfo.InitInfo();
                    ActivityUtil.startActivity(this.getActivity(), LoginActivity.class, null);
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            LogUtil.e("登出：", ex.toString());
        }
    }
}
