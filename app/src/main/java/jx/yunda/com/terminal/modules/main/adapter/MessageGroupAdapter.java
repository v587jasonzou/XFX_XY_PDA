package jx.yunda.com.terminal.modules.main.adapter;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import org.w3c.dom.Text;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.common.StringConstants;
import jx.yunda.com.terminal.modules.main.model.MessageGroup;
import jx.yunda.com.terminal.modules.message.MessageActivity;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.LogUtil;

public class MessageGroupAdapter extends BaseRecyclerAdapter<MessageGroup, MessageGroupAdapter.MessageGroupHolder> {
    @Override
    protected int getLayoutId() {
        return R.layout.main_message_group_item;
    }

    @Override
    protected MessageGroupAdapter.MessageGroupHolder createViewHolder(View view) {
        return new MessageGroupAdapter.MessageGroupHolder(view);
    }

    public static class MessageGroupHolder extends BaseRecyclerAdapter.BaseViewHolder<MessageGroup> {
        @BindView(R.id.message_group_type)
        TextView messageGroupType;
        @BindView(R.id.message_group_name)
        TextView messageGroupName;
        @BindView(R.id.message_last_message)
        TextView messageLastMessage;
        @BindView(R.id.message_update_time)
        TextView messageUpdateTime;
        @BindView(R.id.message_count)
        TextView messgeCount;
        MessageGroup messageGroup;
        private int clickFlag = 0;

        public MessageGroupHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(MessageGroup g) {
            try {
                messageGroup = g;
                messageGroupType.setText(TextUtils.isEmpty(g.getGroupType()) ? "调" : g.getGroupType());
                messageGroupName.setText(g.getGroupName());
                String lastMsg = TextUtils.isEmpty(g.getLastMessage()) ? "" : (TextUtils.isEmpty(g.getSenderName()) ? g.getLastMessage() : (g.getSenderName() + "：" + g.getLastMessage()));
                messageLastMessage.setText(lastMsg);
                messageUpdateTime.setText(g.getUpdateTime());
                messgeCount.setText(g.getUnReadMsgCounts() + "");
                messgeCount.setVisibility(g.getUnReadMsgCounts() == 0 ? View.GONE : View.VISIBLE);
                if (!TextUtils.isEmpty(g.getBackgroundColor()))
                    messageGroupType.setBackgroundColor(Color.parseColor(g.getBackgroundColor()));
                else
                    messageGroupType.setBackgroundResource(R.color.msg_group_type);
            } catch (Exception ex) {
                LogUtil.e("组消息接收", ex.toString());
            }
        }

        @OnClick(R.id.message_group_frame)
        public void onClick(View view) {
            // 防止多次点击
            if (clickFlag == 0) {
                Bundle bundle = new Bundle();
                bundle.putString(StringConstants.MESSAGE_GROUP_BUNDLE_KEY, JSON.toJSONString(messageGroup));
                ActivityUtil.startActivityNotInActivity(view.getContext(), MessageActivity.class, bundle);
                clickFlag = 1;
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    clickFlag = 0;
                }
            }, 1000);
        }
    }
}
