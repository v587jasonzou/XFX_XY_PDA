package jx.yunda.com.terminal.modules.message.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.modules.message.model.MessageGroupPerson;
import jx.yunda.com.terminal.utils.LogUtil;

public class MessageGroupAllPersonAdapter extends BaseRecyclerAdapter<MessageGroupPerson, MessageGroupAllPersonAdapter.MessageGroupAllPersonHolder> {

    private List<MessageGroupPerson> dataAll = new ArrayList<>();

    public List<MessageGroupPerson> getDataAll() {
        return dataAll;
    }

    public void setDataAll(List<MessageGroupPerson> dAll) {
        this.dataAll.clear();
        if (dAll != null)
            this.dataAll.addAll(dAll);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.message_group_persons_item;
    }

    @Override
    protected MessageGroupAllPersonHolder createViewHolder(View view) {
        final MessageGroupAllPersonHolder holder = new MessageGroupAllPersonHolder(view);
        holder.msgGroupPersonCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    MessageGroupPerson temp = getData().get(holder.getAdapterPosition());
                    temp.isChecked = b;
                    for (MessageGroupPerson p : dataAll) {
                        if (p.getEmpId().equals(temp.getEmpId())) {
                            p.isChecked = b;
                            break;
                        }
                    }
                } catch (Exception ex) {
                    LogUtil.e("选择组内人员错误", ex.toString());
                }
            }
        });
        return holder;
    }

    public static class MessageGroupAllPersonHolder extends BaseRecyclerAdapter.BaseViewHolder<MessageGroupPerson> {
        @BindView(R.id.msg_group_person_name)
        TextView msgGroupPersonName;
        @BindView(R.id.msg_group_person_cb)
        CheckBox msgGroupPersonCheckBox;
        MessageGroupPerson msgGroupPerson;

        public MessageGroupAllPersonHolder(View itemView) {
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
