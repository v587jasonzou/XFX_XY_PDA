package jx.yunda.com.terminal.modules.message.presenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import jx.yunda.com.terminal.common.StringConstants;
import jx.yunda.com.terminal.modules.main.model.MessageGroupReceive;
import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.message.model.MessageInfo;
import jx.yunda.com.terminal.modules.message.model.MessageInfoReceive;

public class MessagePresenter extends BasePresenter<IMessage> {
    public MessagePresenter(IMessage view) {
        super(view);

    }


}
