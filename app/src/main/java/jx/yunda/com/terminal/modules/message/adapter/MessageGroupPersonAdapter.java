package jx.yunda.com.terminal.modules.message.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.modules.message.fragment.MessageGroupPersonsFragment;
import jx.yunda.com.terminal.modules.message.model.MessageGroupPerson;
import jx.yunda.com.terminal.utils.LogUtil;

public class MessageGroupPersonAdapter extends BaseRecyclerAdapter<MessageGroupPerson, MessageGroupPersonAdapter.MessageGroupPersonHolder> {
    private MessageGroupPersonsFragment msgGroupPersonsFragment;

    public MessageGroupPersonAdapter(MessageGroupPersonsFragment msgGroupPersonsFragment) {
        super();
        this.msgGroupPersonsFragment = msgGroupPersonsFragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.message_group_persons_item;
    }

    @Override
    protected MessageGroupPersonHolder createViewHolder(View view) {
        return  new MessageGroupPersonHolder(view);
    }

    @Override
    public void onBindViewHolder(final MessageGroupPersonHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.msgGroupPersonCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    getData().get(holder.getAdapterPosition()).isChecked = b;
                    msgGroupPersonsFragment.setMsgGroupPersonSelectTotal(b ? 1 : -1, true);
                } catch (Exception ex) {
                    LogUtil.e("选择组内人员错误", ex.toString());
                }
            }
        });
    }

    public static class MessageGroupPersonHolder extends BaseRecyclerAdapter.BaseViewHolder<MessageGroupPerson> {
        @BindView(R.id.msg_group_person_name)
        TextView msgGroupPersonName;
        @BindView(R.id.msg_group_person_cb)
        CheckBox msgGroupPersonCheckBox;
        MessageGroupPerson msgGroupPerson;

        public MessageGroupPersonHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(MessageGroupPerson g) {
            try {
                msgGroupPerson = g;
                msgGroupPersonName.setText(g.getEmpName());
                msgGroupPersonCheckBox.setChecked(g.isChecked);
            } catch (Exception ex) {
                LogUtil.e("组消息接收", ex.toString());
            }
        }
    }
}
