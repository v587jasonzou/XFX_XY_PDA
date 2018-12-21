package jx.yunda.com.terminal.modules.transNotice.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.modules.message.model.MessageGroupPerson;
import jx.yunda.com.terminal.modules.message.model.Receiver;
import jx.yunda.com.terminal.utils.LogUtil;

public class TransNoticeSelectPersonAdapter extends BaseRecyclerAdapter<MessageGroupPerson, TransNoticeSelectPersonAdapter.TransNoticePersonHolder> {
    public List<Receiver> selectReceivers = new ArrayList<>();

    public List<Receiver> getSelectReceivers() {
        return selectReceivers;
    }

    public void setSelectReceivers(List<Receiver> selectReceivers) {
        this.selectReceivers = selectReceivers;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.message_group_persons_item;
    }

    @Override
    protected TransNoticePersonHolder createViewHolder(View view) {
        return new TransNoticePersonHolder(view);
    }

    @Override
    public void onBindViewHolder(TransNoticePersonHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final MessageGroupPerson person = getData().get(position);
        for (Receiver r : selectReceivers) {
            if (r.getEmpName().equals(person.getEmpName())) {
                person.isChecked = true;
                break;
            }
        }
        holder.msgGroupPersonCheckBox.setChecked(person.isChecked);
        holder.msgGroupPersonCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    person.isChecked = b;
                } catch (Exception ex) {
                    LogUtil.e("选择组内人员错误", ex.toString());
                }
            }
        });
    }

    public static class TransNoticePersonHolder extends BaseRecyclerAdapter.BaseViewHolder<MessageGroupPerson> {
        @BindView(R.id.msg_group_person_name)
        TextView msgGroupPersonName;
        @BindView(R.id.msg_group_person_cb)
        CheckBox msgGroupPersonCheckBox;
        MessageGroupPerson msgGroupPerson;

        public TransNoticePersonHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(MessageGroupPerson g) {
            try {
                msgGroupPerson = g;
                msgGroupPersonName.setText(g.getEmpName());
                //msgGroupPersonCheckBox.setChecked(g.isChecked);
            } catch (Exception ex) {
                LogUtil.e("组消息接收", ex.toString());
            }
        }
    }
}
