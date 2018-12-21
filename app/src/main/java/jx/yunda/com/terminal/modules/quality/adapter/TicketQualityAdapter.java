package jx.yunda.com.terminal.modules.quality.adapter;

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
import jx.yunda.com.terminal.modules.quality.model.QualityTIcketPlanBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketInfoBean;

public class TicketQualityAdapter extends BaseAdapter {
    Context mContext;
    List<QualityTIcketPlanBean> mlist;
    OnInfoClickListner onInfoClickListner;
    public TicketQualityAdapter(Context context, List<QualityTIcketPlanBean> list) {
        mContext = context;
        mlist = list;
    }
    public interface OnInfoClickListner{
        void OnInfoClick(int position);
    }
    public void setOnInfoClickListner(OnInfoClickListner onInfoClickListner){
        this.onInfoClickListner = onInfoClickListner;
    }
    public void refresh(List<QualityTIcketPlanBean> items) {
        this.mlist = items;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if (mlist != null && mlist.size() > 0) {
            return mlist.size();
        } else {
            return 0;
        }
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
    public View getView( int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ticket_info, null);
        CheckBox cb_check = (CheckBox)convertView.findViewById(R.id.cb_check);
        TextView tvTrainNo = (TextView)convertView.findViewById(R.id.tvTrainNo);
        TextView tvData = (TextView)convertView.findViewById(R.id.tvData);
        TextView tvTicketLocation = (TextView)convertView.findViewById(R.id.tvTicketLocation);
        TextView tvTicketName = (TextView)convertView.findViewById(R.id.tvTicketName);
        TextView tvTicketDesign = (TextView)convertView.findViewById(R.id.tvTicketDesign);
        LinearLayout llInfo = (LinearLayout)convertView.findViewById(R.id.llInfo);
//        llInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onInfoClickListner.OnInfoClick(position);
//            }
//        });
        if(mlist!=null&&mlist.size()>0){
            if(mlist.get(position)!=null){
                QualityTIcketPlanBean bean = mlist.get(position);
                if(bean.getTrainTypeShortName()!=null&&bean.getTrainNo()!=null){
                    tvTrainNo.setText(bean.getTrainTypeShortName()+" "+bean.getTrainNo());
                }
                if(bean.getTicketTime()!=null){
                    tvData.setText(bean.getTicketTime());
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
        cb_check.setVisibility(View.GONE);
        return convertView;
    }

}
