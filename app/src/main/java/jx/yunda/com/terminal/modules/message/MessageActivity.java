package jx.yunda.com.terminal.modules.message;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jakewharton.rxbinding.view.RxView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.base.JXApplication;
import jx.yunda.com.terminal.common.StringConstants;
import jx.yunda.com.terminal.entity.MessageSend;
import jx.yunda.com.terminal.global.broadcastReceiver.NewWorkBroadcastReceiver;
import jx.yunda.com.terminal.global.listener.INetWorkChangeListener;
import jx.yunda.com.terminal.message.okhttp.client.listener.WsOnOpenHanleListener;
import jx.yunda.com.terminal.message.okhttp.client.manager.WebSocketManager;
import jx.yunda.com.terminal.modules.main.adapter.MenuAdapter;
import jx.yunda.com.terminal.modules.main.model.MessageGroup;
import jx.yunda.com.terminal.modules.message.adapter.MessageInfoAdapter;
import jx.yunda.com.terminal.modules.message.fragment.MessageGroupPersonsEditFragment;
import jx.yunda.com.terminal.modules.message.fragment.MessageGroupPersonsFragment;
import jx.yunda.com.terminal.modules.message.model.MessageInfo;
import jx.yunda.com.terminal.modules.message.model.MessageInfoReceive;
import jx.yunda.com.terminal.modules.message.model.MessageReturn;
import jx.yunda.com.terminal.modules.message.model.Receiver;
import jx.yunda.com.terminal.modules.message.presenter.IMessage;
import jx.yunda.com.terminal.modules.message.presenter.MessagePresenter;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.modules.message.widget.MessageInputEditText;
import rx.functions.Action1;
/**
 * <li>说明：消息Activity
 * <li>创建人：zhubs
 * <li>创建日期：2018年7月20日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 */
public class MessageActivity extends BaseActivity<MessagePresenter> implements IMessage, Validator.ValidationListener, INetWorkChangeListener {
    @BindView(R.id.message_title)
    TextView messageTitle;
    @BindView(R.id.message_content_recycler)
    RecyclerView messageContentRecycler;
    @NotEmpty(message = "发送内容不能为空！")
    @BindView(R.id.message_input_text)
    public MessageInputEditText messageInputText;
    @BindView(R.id.message_send)
    Button messageSend;
    @BindView(R.id.message_network_warn)
    TextView messageNetworkWarn;
    @BindView(R.id.message_content_smart_refresh)
    SmartRefreshLayout messageContentSmartRefresh;
    private NewWorkBroadcastReceiver receiver;
    //验证
    Validator validator;
    MessageInfoAdapter messageInfoAdapter;
    public MessageGroup messageGroup;

    public ArrayList<Receiver> selectReceivers;
    Dialog menuDialog;
    ImageView ivDelete;
    RecyclerView home_train_recycler;
    MenuAdapter trainMenuAdapter;

    public boolean isSelectMessageGroupPerson(String empId) {
        if (TextUtils.isEmpty(empId)) return false;
        for (Receiver r : selectReceivers) {
            if (empId.equals(r.getEmpId()))
                return true;
        }
        return false;
    }

    @Override
    protected MessagePresenter getPresenter() {
        return new MessagePresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.message_act;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiver = new NewWorkBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
        LogUtil.d("消息页面", "开始接收消息");
        try {
            if (!EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().register(this);
            selectReceivers = new ArrayList<Receiver>();
            getPresenter().setWebSocketCallBack(new WsOnOpenHanleListener() {
                @Override
                public void wsStartConnnectedHanle() {
                    if (WebSocketManager.getInstance().isWsConnected()) {
                        messageNetworkWarn.setVisibility(View.GONE);
                        sendInitMsgToMsgServer("");
                    } else {
                        messageNetworkWarn.setVisibility(View.VISIBLE);
                    }
                }
            });
            //发送消息，通知服务发送的消息类型
            sendInitMsgToMsgServer("");
        } catch (Exception ex) {
            LogUtil.e("消息页面初始化数据", ex.toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("消息页面", "暂停接收消息");
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        getPresenter().setWebSocketCallBack(null);
        unregisterReceiver(receiver);
    }

    @Override
    public void initSubViews(View view) {
        messageInfoAdapter = new MessageInfoAdapter(new TransOtherModules(this));
        messageInfoAdapter.setData(new ArrayList<MessageInfo>());
        messageContentRecycler.setAdapter(messageInfoAdapter);
        messageContentRecycler.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        Bundle budle = intent.getExtras();
        messageGroup = JSON.parseObject(budle.getString(StringConstants.MESSAGE_GROUP_BUNDLE_KEY), new TypeReference<MessageGroup>() {
        });
        messageTitle.setText(messageGroup.getGroupName());
        // 初始化验证对象
        initValidator();
        // 消息发送按钮，发送事件，并设置防抖动监听绑定
        RxView.clicks(messageSend).throttleFirst(2, TimeUnit.SECONDS)
                .compose(this.<Void>bindToLifecycle())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        validator.validate();
                    }
                });

        messageInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
                if (lengthBefore > 1 && lengthAfter == 0) {
                    MessageInputEditText.MyImageSpan[] spans = messageInputText.getText().getSpans(start, start + 1, MessageInputEditText.MyImageSpan.class);
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
        messageContentSmartRefresh.setRefreshHeader(new ClassicsHeader(this));
        messageContentSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                String startId = messageInfoAdapter.getData() != null && messageInfoAdapter.getData().size() > 0 ? messageInfoAdapter.getData().get(0).getIdx() : "";
                sendInitMsgToMsgServer(startId);
            }
        });
    }


    @Override
    public void initData() {

    }

    private void sendInitMsgToMsgServer(String startId) {
        if (TextUtils.isEmpty(startId)) {
            messageInfoAdapter.getData().clear();
            messageInfoAdapter.notifyDataSetChanged();
        }
        MessageSend msgSend = new MessageSend();
        msgSend.setPageSign(StringConstants.MESSAGE_2);
        msgSend.setEmpId(SysInfo.userInfo.emp.getEmpid().toString());
        msgSend.setEmpName(SysInfo.userInfo.emp.getEmpname());
        msgSend.setOperatorId(SysInfo.userInfo.emp.getOperatorid().toString());
        msgSend.setGroupId(messageGroup.getGroupId());
        msgSend.setStartId(startId);
        msgSend.setLimit("20");
        getPresenter().sendMessage(msgSend);
    }

    /**
     * 初始化验证框架
     */
    public void initValidator() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public void onValidationSucceeded() {
        try {
            String msgContent = messageInputText.getInputText();
            if (TextUtils.isEmpty(msgContent)) {
                Toast.makeText(JXApplication.getContext(), "发送内容不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            MessageInfo msgInfo = new MessageInfo();
            msgInfo.setGroupId(messageGroup.getGroupId());
            msgInfo.setSenderId(SysInfo.userInfo.emp.getEmpid().toString());
            msgInfo.setSenderName(SysInfo.userInfo.emp.getEmpname());
            msgInfo.setWorkTeamId(SysInfo.userInfo.org.getOrgid().toString());
            msgInfo.setWorkTeamName(SysInfo.userInfo.org.getOrgname());
            msgInfo.setReceivers(selectReceivers);
            msgInfo.setMsgContent(msgContent);
            msgInfo.setWebOpenMode("0");
            msgInfo.setIsJx("1");
            //发送消息，通知服务发送的消息类型
            MessageSend msgSend = new MessageSend();
            msgSend.setPageSign(StringConstants.MESSAGE_3);
            msgSend.setEmpId(SysInfo.userInfo.emp.getEmpid().toString());
            msgSend.setOperatorId(SysInfo.userInfo.emp.getOperatorid().toString());
            msgSend.setGroupId(messageGroup.getGroupId());
            msgSend.setSendMessage(msgInfo);
            if (mPresenter.sendMessage(msgSend)) {
                selectReceivers.clear();
                messageInputText.setText("");
            }
        } catch (Exception ex) {
            LogUtil.e("消息发送失败", ex.toString());
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String msgs = "";
        for (ValidationError error : errors) {
            msgs += error.getCollatedErrorMessage(this) + "\n";
        }
        if (!TextUtils.isEmpty(msgs)) {
            ToastUtil.toastShort(msgs);
        }
    }

    @OnClick({R.id.message_back, R.id.message_group_person_edit, R.id.message_module_choose, R.id.message_group_persons})
    void viewClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.message_back:
                    this.finish();
                    break;
                case R.id.message_group_person_edit:
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.message_panel, new MessageGroupPersonsEditFragment());
                    transaction.commit();
                    break;
                case R.id.message_module_choose:
                    showMenuDialog();
                    break;
                case R.id.message_group_persons:
                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    transaction2.add(R.id.message_panel, new MessageGroupPersonsFragment());
                    transaction2.commit();
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            LogUtil.e("消息页面", ex.toString());
        }
    }

    private void showMenuDialog() {
        if (menuDialog == null) {
            menuDialog = new Dialog(this, R.style.dialog);
            LayoutInflater inflater = LayoutInflater.from(this);
            View viewDialog = inflater.inflate(R.layout.menu_dialog, null);
            Window window = menuDialog.getWindow();
            window.setWindowAnimations(R.style.mystyle);  //添加动画
            menuDialog.setContentView(viewDialog);
            ivDelete = (ImageView) viewDialog.findViewById(R.id.ivDelete);
            home_train_recycler = (RecyclerView) viewDialog.findViewById(R.id.home_train_recycler);
            trainMenuAdapter = new MenuAdapter();
            home_train_recycler.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
            home_train_recycler.setAdapter(trainMenuAdapter);
            if (SysInfo.partMenus != null && SysInfo.partMenus.size() > 0) {
                trainMenuAdapter.setData(SysInfo.partMenus);
                trainMenuAdapter.notifyDataSetChanged();
            }
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuDialog.dismiss();
                }
            });
        }
        menuDialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMessage(MessageInfoReceive msgReceive) {
        if (StringConstants.MESSAGE_3.equals(msgReceive.getPageSign()))
            pageNotifyNewDataSetChanged(msgReceive.getRstList());
        else if (StringConstants.MESSAGE_2.equals(msgReceive.getPageSign()))
            pageNotifyHistoryDataSetChanged(msgReceive.getRstList());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void msgReturnReceipt(MessageReturn msgReturn) {
        try {
            if (msgReturn == null || !StringConstants.MESSAGE_4.equals(msgReturn.getPageSign()) || TextUtils.isEmpty(msgReturn.getReceiverName()))
                return;
            if (msgReturn.getReceiverName().contains(SysInfo.userInfo.emp.getEmpname())) return;
            if (messageInfoAdapter.getData() == null || messageInfoAdapter.getData().size() == 0)
                return;
            for (int i = messageInfoAdapter.getData().size() - 1; i > 0; i--) {
                MessageInfo msg = messageInfoAdapter.getData().get(i);
                if (msg.getIdx().equals(msgReturn.getMsgInfoId())) {
                    if (!TextUtils.isEmpty(msgReturn.getReceiverName()) && (TextUtils.isEmpty(msg.getIsReadedSign()) || !msg.getIsReadedSign().contains(msgReturn.getReceiverName()))) {
                        msg.setIsReadedSign(TextUtils.isEmpty(msg.getIsReadedSign()) ? msgReturn.getReceiverName() : (msg.getIsReadedSign() + "," + msgReturn.getReceiverName()));
                        messageInfoAdapter.notifyItemChanged(i);
                    }
                    break;
                }
            }
        } catch (Exception ex) {
        }
    }

    private boolean isFirstLoadMessage = true;

    @Override
    public void pageNotifyNewDataSetChanged(List<MessageInfo> messageGroups) {
        try {
            if (messageGroups == null || messageGroups.size() == 0) return;
            for (MessageInfo msg : messageGroups) {
                messageInfoAdapter.getData().add(msg);
                messageInfoAdapter.notifyItemInserted(messageInfoAdapter.getData().size() - 1);
            }
            int position = messageInfoAdapter.getItemCount();
            messageContentRecycler.smoothScrollToPosition(position - 1);
        } catch (Exception ex) {
            LogUtil.e("消息页面更新新消息", ex.toString());
        }
    }

    boolean isFirstLoad = true;

    @Override
    public void pageNotifyHistoryDataSetChanged(List<MessageInfo> messageGroups) {
        try {
            messageContentSmartRefresh.finishRefresh();
            if (messageGroups == null || messageGroups.size() == 0) return;
            int len = messageGroups.size();
            for (int i = len - 1; i >= 0; i--) {
                messageInfoAdapter.getData().add(0, messageGroups.get(i));
                messageInfoAdapter.notifyItemInserted(0);
            }
            if (isFirstLoad)
                messageContentRecycler.smoothScrollToPosition(messageInfoAdapter.getItemCount() - 1);
            isFirstLoad = false;
        } catch (Exception ex) {
            LogUtil.e("消息页面更新历史消息", ex.toString());
        }
    }

    @Override
    public void NetWorkChange(boolean isAvailable) {

        if (isAvailable) {
            if (WebSocketManager.getInstance().isWsConnected()) {
                messageNetworkWarn.setVisibility(View.GONE);
            } else {
                getPresenter().webSocketConnnect();
            }

        } else {
            messageNetworkWarn.setVisibility(View.VISIBLE);
        }
    }
}
