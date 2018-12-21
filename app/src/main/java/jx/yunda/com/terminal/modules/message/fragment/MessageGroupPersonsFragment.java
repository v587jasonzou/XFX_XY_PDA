package jx.yunda.com.terminal.modules.message.fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.modules.message.MessageActivity;
import jx.yunda.com.terminal.modules.message.adapter.MessageGroupPersonAdapter;
import jx.yunda.com.terminal.modules.message.model.MessageGroupPerson;
import jx.yunda.com.terminal.modules.message.model.Receiver;
import jx.yunda.com.terminal.modules.message.presenter.IMessageGroupPersons;
import jx.yunda.com.terminal.modules.message.presenter.MessageGroupPersonsPresenter;
import jx.yunda.com.terminal.utils.LogUtil;

/**
 * <li>说明：消息页面：群组内可选择发送的人员列表
 * <li>创建人：zhubs
 * <li>创建日期：2018年7月20日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 */
public class MessageGroupPersonsFragment extends BaseFragment<MessageGroupPersonsPresenter> implements IMessageGroupPersons {

    @BindView(R.id.message_group_person_select_total)
    TextView msgGroupPersonSelectTotal;
    @BindView(R.id.message_group_person_recycler)
    RecyclerView messageGroupRecycler;
    MessageGroupPersonAdapter msgGroupPersonAdapter;

    @Override
    protected MessageGroupPersonsPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new MessageGroupPersonsPresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.message_group_persons_fm;
    }

    @Override
    public void initSubViews(View view) {
        msgGroupPersonAdapter = new MessageGroupPersonAdapter(this);
        messageGroupRecycler.setAdapter(msgGroupPersonAdapter);
        messageGroupRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void initData() {
        MessageActivity msgActivity = (MessageActivity) getActivity();
        getPresenter().getGroupPersons(msgActivity.messageGroup.getGroupId());
    }

    @OnCheckedChanged(R.id.message_group_person_all_select)
    void viewOnCheckedChange(CompoundButton buttonView, boolean isChecked) {
        List<MessageGroupPerson> datas = msgGroupPersonAdapter.getData();
        if (datas == null || datas.size() == 0) return;
        for (MessageGroupPerson data : datas) {
            data.isChecked = isChecked;
        }
        msgGroupPersonAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.message_group_persons_cancel, R.id.message_group_persons_confirm})
    void viewClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.message_group_persons_cancel:
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.remove(this);
                    transaction.commit();
                    break;
                case R.id.message_group_persons_confirm:
                    MessageActivity msgActivity = (MessageActivity) getActivity();
                    msgActivity.messageInputText.setText("");
                    msgActivity.selectReceivers = new ArrayList<>();
                    String inputContent = msgActivity.messageInputText.getText().toString();
                    List<MessageGroupPerson> msgGroupPersons = msgGroupPersonAdapter.getData();
                    Receiver r = null;
                    for (MessageGroupPerson person : msgGroupPersons) {
                        if (person.isChecked) {
                            r = new Receiver();
                            r.setEmpId(person.getEmpId());
                            r.setEmpName(person.getEmpName());
                            r.setEmpPost(person.getEmpPost());
                            r.setGroupId(msgActivity.messageGroup.getGroupId());
                            msgActivity.selectReceivers.add(r);
                        }
                    }
                    msgActivity.messageInputText.addSpansBeforeText(msgActivity.selectReceivers);
                    FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction1.remove(this);
                    transaction1.commit();
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            LogUtil.e("消息接收人员选择", ex.toString());
        }
    }


    @Override
    public void pageNotifyDataSetChanged(List<MessageGroupPerson> messageGroupPersons) {
        try {
            MessageActivity msgActivity = (MessageActivity) getActivity();
            List<MessageGroupPerson> tempPsersons = new ArrayList<>();
            for (MessageGroupPerson p : messageGroupPersons) {
                if ((SysInfo.userInfo.emp.getEmpid() + "").equals(p.getEmpId()))
                    continue;
                p.isChecked = msgActivity.isSelectMessageGroupPerson(p.getEmpId());
                tempPsersons.add(p);
            }
            msgGroupPersonAdapter.setData(tempPsersons);
            msgGroupPersonAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LogUtil.e("消息组人员页面刷新", e.toString());
        }
    }

    private int selectTotal = 0;

    @Override
    public void setMsgGroupPersonSelectTotal(int addCount, boolean isAdd) {
        selectTotal = isAdd ? (selectTotal + addCount) : addCount;
        LogUtil.d("消息组人员选择", selectTotal + "");
        msgGroupPersonSelectTotal.setText("已选择" + selectTotal + "人");
    }
}
