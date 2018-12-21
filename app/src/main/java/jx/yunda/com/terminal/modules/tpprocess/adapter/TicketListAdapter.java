package jx.yunda.com.terminal.modules.tpprocess.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketInfoBean;

public class TicketListAdapter extends BaseAdapter {
    Context mContext;
    List<TicketInfoBean> mlist;
    int state = 0;
    OnInfoClickListner onInfoClickListner;
    public TicketListAdapter(Context context, List<TicketInfoBean> list,int state) {
        mContext = context;
        mlist = list;
        this.state = state;
    }
    public interface OnInfoClickListner{
        void OnInfoClick(int position);
    }
    public void setOnInfoClickListner(OnInfoClickListner onInfoClickListner){
        this.onInfoClickListner = onInfoClickListner;
    }
    @Override
    public int getCount() {
        if (mlist != null && mlist.size() > 0) {
            return mlist.size();
        } else {
            return 0;
        }
    }
    public void setState(int st){
        state = st;
    }
    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ticket_info,parent,false);
        CheckBox cb_check = (CheckBox)convertView.findViewById(R.id.cb_check);
        TextView tvTrainNo = (TextView)convertView.findViewById(R.id.tvTrainNo);
        TextView tvData = (TextView)convertView.findViewById(R.id.tvData);
        TextView tvTicketLocation = (TextView)convertView.findViewById(R.id.tvTicketLocation);
        TextView tvTicketName = (TextView)convertView.findViewById(R.id.tvTicketName);
        TextView tvTicketDesign = (TextView)convertView.findViewById(R.id.tvTicketDesign);
        LinearLayout llInfo = (LinearLayout)convertView.findViewById(R.id.llInfo);
        llInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInfoClickListner.OnInfoClick(position);
            }
        });
        if(mlist!=null&&mlist.size()>0){
            if(mlist.get(position)!=null){
                TicketInfoBean bean = mlist.get(position);
                if(bean.getTrainTypeShortName()!=null&&bean.getTrainNo()!=null){
                    tvTrainNo.setText(bean.getTrainTypeShortName()+" "+bean.getTrainNo());
                }
                if(bean.getCreateTime()>0){
                    SimpleDateFormat st = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    String time = st.format(new Date(bean.getCreateTime()));
                    tvData.setText(time);
                }
                if(bean.getFixPlaceFullName()!=null){
                    tvTicketLocation.setText(bean.getFixPlaceFullName());
                }
                if(bean.getFaultDesc()!=null){
                    tvTicketName.setText(bean.getFaultDesc());
                }
                if(bean.getResolutionContent()!=null){
                    tvTicketDesign.setText(bean.getResolutionContent());
                }
            }
        }
        if(state==2){
            cb_check.setVisibility(View.VISIBLE);
        }else {
            cb_check.setVisibility(View.GONE);
        }
        cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mlist.get(position).setCheck(isChecked);
            }
        });
        return convertView;
    }

}
