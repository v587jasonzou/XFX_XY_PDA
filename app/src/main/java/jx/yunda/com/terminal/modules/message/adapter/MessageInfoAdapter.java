package jx.yunda.com.terminal.modules.message.adapter;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.modules.message.TransOtherModules;
import jx.yunda.com.terminal.modules.message.model.MessageInfo;
import jx.yunda.com.terminal.utils.DateUtil;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.SizeConvert;
import rx.functions.Action1;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

public class MessageInfoAdapter extends BaseRecyclerAdapter<MessageInfo, MessageInfoAdapter.MessageInfoHolder> {
    TransOtherModules transOtherModules;

    public MessageInfoAdapter(TransOtherModules transOtherModules) {
        this.transOtherModules = transOtherModules;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.message_item;
    }

    @Override
    protected MessageInfoHolder createViewHolder(View view) {
        return new MessageInfoAdapter.MessageInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageInfoHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getData() == null || getData().size() == 0) return;
        final MessageInfo msg = getData().get(position);
        if (msg.getSenderId().equals(SysInfo.userInfo.emp.getEmpid() + "")) return;
        holder.messageOtherInfo.getPaint().setFlags(0);
        if (msg.getReceiversId().contains(SysInfo.userInfo.emp.getEmpid().toString()) && ("1".equals(msg.getWebOpenMode()) || "2".equals(msg.getWebOpenMode()))) {
            holder.messageOtherInfo.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            RxView.clicks(holder.messageOtherInfo)
                    .throttleFirst(5, TimeUnit.SECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            transOtherModules.transOtherModulesByMsg(msg);
                        }
                    });

        } else {
            RxView.clicks(holder.messageOtherInfo)
                    .throttleFirst(5, TimeUnit.SECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {

                        }
                    });
        }
    }


    public static class MessageInfoHolder extends BaseRecyclerAdapter.BaseViewHolder<MessageInfo> {
        @BindView(R.id.message_other_panel)
        RelativeLayout messageOtherPanel;
        @BindView(R.id.message_other_from)
        TextView messageOtherFrom;
        @BindView(R.id.message_other_time)
        TextView messageOtherTime;
        @BindView(R.id.message_other_info)
        TextView messageOtherInfo;
        @BindView(R.id.message_other_return)
        TextView messageOtherReturn;

        @BindView(R.id.message_my_panel)
        RelativeLayout messageMyPanel;
        @BindView(R.id.message_my_from)
        TextView messageMyFrom;
        @BindView(R.id.message_my_time)
        TextView messageMyTime;
        @BindView(R.id.message_my_info)
        TextView messageMyInfo;
        @BindView(R.id.message_my_return)
        TextView messageMyReturn;

        public MessageInfoHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(final MessageInfo data) {
            try {
                String senderName = data.getSenderName() + (TextUtils.isEmpty(data.getWorkTeamName()) ? "" : ("【" + data.getWorkTeamName() + "】"));
                String createTime = getDateStrByToday(data.getCreateTime());
                String msgReturn = "";
                if (!TextUtils.isEmpty(data.getIsReadedSign()) && !data.getIsReadedSign().contains(SysInfo.userInfo.emp.getEmpname())) {
                    msgReturn = data.getIsReadedSign();
                } else if (!TextUtils.isEmpty(data.getIsReadedSign())) {
                    String[] names = data.getIsReadedSign().split(",");
                    if (names != null && names.length > 0) {
                        for (String n : names) {
                            if (!SysInfo.userInfo.emp.getEmpname().equals(n))
                                msgReturn += TextUtils.isEmpty(msgReturn) ? n : ("," + n);
                        }
                    }
                }
                msgReturn = TextUtils.isEmpty(msgReturn) ? "" : ("已读：" + msgReturn);
                int msgReturnVisibilityInt = TextUtils.isEmpty(msgReturn) ? View.GONE : View.VISIBLE;
                if (!data.getSenderId().equals(SysInfo.userInfo.emp.getEmpid() + "")) {
                    messageMyPanel.setVisibility(View.GONE);
                    messageMyReturn.setVisibility(View.GONE);
                    messageOtherPanel.setVisibility(View.VISIBLE);
                    messageOtherReturn.setVisibility(msgReturnVisibilityInt);
                    messageOtherFrom.setText(senderName);
                    messageOtherTime.setText(createTime);
                    messageOtherInfo.setText(data.getMsgContent());
                    messageOtherReturn.setText(msgReturn);
                    if (!TextUtils.isEmpty(data.getMsgContent()) && data.getMsgContent().contains(SysInfo.userInfo.emp.getEmpname()))
                        messageOtherInfo.setBackgroundResource(R.drawable.message_left_sky_blue);
                    else
                        messageOtherInfo.setBackgroundResource(R.drawable.message_left_gray);
                    messageOtherInfo.setPadding(SizeConvert.dip2px(30), SizeConvert.dip2px(10), SizeConvert.dip2px(15), SizeConvert.dip2px(10));
                } else {
                    messageOtherPanel.setVisibility(View.GONE);
                    messageOtherReturn.setVisibility(View.GONE);
                    messageMyPanel.setVisibility(View.VISIBLE);
                    messageMyReturn.setVisibility(msgReturnVisibilityInt);
                    messageMyFrom.setText(senderName);
                    messageMyTime.setText(createTime);
                    messageMyInfo.setText(data.getMsgContent());
                    messageMyReturn.setText(msgReturn);
                    //messageMyInfo.setPadding(20, 15, 50, 15);
                }
            } catch (Exception ex) {
                LogUtil.e("消息页面", ex.toString());
            }
        }

        private String getDateStrByToday(String millisecondStr) {

            try {
                if (TextUtils.isEmpty(millisecondStr)) return "";
                String todayStr = DateUtil.date2String(new Date(), "yyyy-MM-dd");
                String timeStr = DateUtil.millisecondStr2DateStr(millisecondStr, "yyyy-MM-dd");
                if (todayStr.equals(timeStr))
                    return DateUtil.millisecondStr2DateStr(millisecondStr, "HH:mm:ss");
                else
                    return DateUtil.millisecondStr2DateStr(millisecondStr, "yyyy-MM-dd HH:mm:ss");
            } catch (Exception e) {
                return "";
            }
        }
    }
}
