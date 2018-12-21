package jx.yunda.com.terminal.modules.main.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.common.StringConstants;
import jx.yunda.com.terminal.modules.main.MainActivity;
import jx.yunda.com.terminal.modules.main.adapter.MessageGroupAdapter;
import jx.yunda.com.terminal.modules.main.model.MessageGroup;
import jx.yunda.com.terminal.modules.main.model.MessageGroupReceive;
import jx.yunda.com.terminal.modules.main.presenter.IMessageGroup;
import jx.yunda.com.terminal.modules.main.presenter.MessageGroupPresenter;
import jx.yunda.com.terminal.utils.LogUtil;

/**
 * <li>说明：消息群组列表
 * <li>创建人：zhubs
 * <li>创建日期：2018年7月20日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 */
public class MessageGroupFragment extends BaseFragment<MessageGroupPresenter> implements IMessageGroup {

    @BindView(R.id.message_group_recycler)
    RecyclerView messageGroupRecycler;
    MessageGroupAdapter messageGroupAdapter;

    public MessageGroupFragment() {
        // Required empty public constructor
    }

    @Override
    protected MessageGroupPresenter getPresenter() {
        return new MessageGroupPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.main_message_group_fm;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("消息组页面", "开始接收消息");
        ((MainActivity) getActivity()).sendInitMsgToMsgServer();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d("消息组页面", "暂停接收消息");
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Override
    public void initSubViews(View view) {
        messageGroupAdapter = new MessageGroupAdapter();
        messageGroupRecycler.setAdapter(messageGroupAdapter);
        messageGroupRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void initData() {
    }

    /**
     * <li>说明：获取消息组
     * <li>创建人：zhubs
     * <li>创建日期：2018年7月19日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMessagesGroup(MessageGroupReceive msgReceive) {
        if (!StringConstants.MESSAGE_1.equals(msgReceive.getPageSign())) return;
        pageNotifyDataSetChanged(msgReceive.getRstList());
    }

    @Override
    public void pageNotifyDataSetChanged(List<MessageGroup> messageGroups) {
        try {
            int unReadCountTotal = 0;
            for (MessageGroup msgGroup : messageGroups) {
                unReadCountTotal += msgGroup.getUnReadMsgCounts();
            }
            ((MainActivity) getActivity()).setUnReadMessageCount(unReadCountTotal);
            messageGroupAdapter.setData(messageGroups);
            messageGroupAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtil.e("组消息页面刷新", e.toString());
        }
    }
}
