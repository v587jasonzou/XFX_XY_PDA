package jx.yunda.com.terminal.modules.main;

import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.common.StringConstants;
import jx.yunda.com.terminal.entity.MessageSend;
import jx.yunda.com.terminal.global.broadcastReceiver.NewWorkBroadcastReceiver;
import jx.yunda.com.terminal.global.listener.INetWorkChangeListener;
import jx.yunda.com.terminal.message.okhttp.client.listener.WsOnOpenHanleListener;
import jx.yunda.com.terminal.message.okhttp.client.manager.WebSocketManager;
import jx.yunda.com.terminal.modules.main.fragment.HomeFragment;
import jx.yunda.com.terminal.modules.main.fragment.MeFragment;
import jx.yunda.com.terminal.modules.main.fragment.MessageGroupFragment;
import jx.yunda.com.terminal.modules.main.model.MessageReceive;
import jx.yunda.com.terminal.modules.main.model.TabItem;
import jx.yunda.com.terminal.modules.main.presenter.IMain;
import jx.yunda.com.terminal.modules.main.presenter.MainPresenter;
import jx.yunda.com.terminal.utils.LogUtil;

public class MainActivity extends BaseActivity<MainPresenter> implements IMain, INetWorkChangeListener {

    @BindView(R.id.main_tabhost)
    FragmentTabHost fragmentTabHost;
    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindView(R.id.main_network_warn)
    TextView mainNetworkWarn;

    private NewWorkBroadcastReceiver receiver;
    private TabItem[] tabItems;

    @Override
    protected MainPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new MainPresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.main_act;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initSubViews(View view) {
        try {

            // 实例化tabhost
            fragmentTabHost.setup(this, getSupportFragmentManager(),
                    R.id.main_container);
            //去掉分割线
            fragmentTabHost.getTabWidget().setDividerDrawable(null);
            //初始化tab数组
            tabItems = new TabItem[]{
                    new TabItem(getResources().getString(R.string.main_application), getResources().getString(R.string.main_application), R.mipmap.app_default, R.mipmap.app_activity, HomeFragment.class),
                    new TabItem(getResources().getString(R.string.main_message), "群聊", R.mipmap.news_default, R.mipmap.news_activity, MessageGroupFragment.class),
                    new TabItem(getResources().getString(R.string.main_me), getResources().getString(R.string.main_me), R.mipmap.me_default, R.mipmap.me_activity, MeFragment.class)
            };
            for (int i = 0; i < tabItems.length; i++) {
                TabHost.TabSpec spec = fragmentTabHost.newTabSpec(tabItems[i].getName()).setIndicator(getView(tabItems[i]));
                fragmentTabHost.addTab(spec, tabItems[i].getRelFragment(), null);
                //设置背景(必须在addTab之后，由于需要子节点（底部菜单按钮）否则会出现空指针异常)
                //fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_selector);
                TextView tv = (TextView) fragmentTabHost.getTabWidget().getChildAt(i).findViewById(R.id.tab_name);
                ImageView img = (ImageView) fragmentTabHost.getTabWidget().getChildAt(i).findViewById(R.id.tab_img);
                img.setImageDrawable(setTintDrawable(getResources().getDrawable(tabItems[i].getImageResouseId()).mutate(), getResources().getColorStateList(R.color.tag_selector)));
                fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                    @Override
                    public void onTabChanged(String tabId) {
                        upDateTab();
                    }
                });
            }
        } catch (Exception ex) {
            LogUtil.e("主页面初始化", ex.toString());
        }
    }

    public static Drawable setTintDrawable(Drawable drawable, ColorStateList colorStateList) {
        Drawable drawable_new = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(drawable_new, colorStateList);
        return drawable_new;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiver = new NewWorkBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        upDateTab();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("主页面", "开始接收消息");
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        getPresenter().setWebSocketCallBack(new WsOnOpenHanleListener() {
            @Override
            public void wsStartConnnectedHanle() {
                if (WebSocketManager.getInstance().isWsConnected()) {
                    mainNetworkWarn.setVisibility(View.GONE);
                    sendInitMsgToMsgServer();
                } else {
                    mainNetworkWarn.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d("主页面", "暂停接收消息");
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        getPresenter().setWebSocketCallBack(null);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMessage(MessageReceive msgReceive) {
        if (!StringConstants.MESSAGE_0.equals(msgReceive.getPageSign())) return;
        setUnReadMessageCount(msgReceive.getUnReadCount());
    }

    /**
     * tab更新事件
     */
    private void upDateTab() {
        try {
            LogUtil.d("主页面Tag选择", fragmentTabHost.getCurrentTab() + "");
            selectTabItemIndex = fragmentTabHost.getCurrentTab();
            TabItem item = tabItems[selectTabItemIndex];
            mainTitle.setText(item.getTitle());
            // for (int i = 0; i < fragmentTabHost.getTabWidget().getChildCount(); i++) {
            //
            //     TextView tv = (TextView) fragmentTabHost.getTabWidget().getChildAt(i).findViewById(R.id.tab_name);
            //     ImageView img = (ImageView) fragmentTabHost.getTabWidget().getChildAt(i).findViewById(R.id.tab_img);
            //     if (fragmentTabHost.getCurrentTab() == i) {//选中
            //         tv.setTextColor(this.getResources().getColor(R.color.acivity_color));
            //         img.setImageResource(tabItems[i].getSelectedImageResouseId());
            //     } else {//不选中
            //         tv.setTextColor(this.getResources().getColor(R.color.black_overlay));
            //         img.setImageResource(tabItems[i].getImageResouseId());
            //     }
            // }
        } catch (Exception ex) {
            LogUtil.e("主页面Tag切换", ex.toString());
        }
    }

    private int selectTabItemIndex = 0;

    public void sendInitMsgToMsgServer() {
        if (!WebSocketManager.getInstance().isWsConnected()) return;
        //发送消息，通知服务发送的消息类型
        MessageSend msgSend = new MessageSend();
        msgSend.setPageSign(selectTabItemIndex == 1 ? StringConstants.MESSAGE_1 : StringConstants.MESSAGE_0);
        msgSend.setEmpId(SysInfo.userInfo.emp.getEmpid().toString());
        msgSend.setOperatorId(SysInfo.userInfo.emp.getOperatorid().toString());
        msgSend.setEmpName(SysInfo.userInfo.emp.getEmpname());
        getPresenter().sendMessage(msgSend);
    }

    @Override
    public void initData() {
    }

    private View getView(TabItem item) {
        //取得布局实例
        View view = View.inflate(MainActivity.this, R.layout.main_tab_content, null);
        //取得布局对象
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_img);
        TextView textView = (TextView) view.findViewById(R.id.tab_name);
        TextView textViewCount = (TextView) view.findViewById(R.id.tab_message_count);
        //设置图标
        imageView.setImageResource(item.getImageResouseId());
        //设置标题
        textView.setText(item.getName());
        textViewCount.setText("-1");
        textViewCount.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void setUnReadMessageCount(int c) {
        //if (c == 0) return;
        View view = fragmentTabHost.getTabWidget().getChildAt(1);
        TextView textViewCount = (TextView) view.findViewById(R.id.tab_message_count);
        textViewCount.setText(c + "");
        textViewCount.setVisibility(c == 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void NetWorkChange(boolean isAvailable) {
        if (isAvailable) {
            if (WebSocketManager.getInstance().isWsConnected()) {
                mainNetworkWarn.setVisibility(View.GONE);
            } else {
                getPresenter().webSocketConnnect();
            }

        } else {
            mainNetworkWarn.setVisibility(View.VISIBLE);
        }
    }
}




