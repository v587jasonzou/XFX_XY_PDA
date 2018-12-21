package jx.yunda.com.terminal.modules.schedule.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.schedule.entity.ReceiveTrainBean;

public class ReceiveSchedulAdapter extends BaseAdapter {
    List<ReceiveTrainBean> list;
    Context context;
    ViewHolder holder;
    OnBookClickListner onBookClickListner;
    public ReceiveSchedulAdapter(Context context, List<ReceiveTrainBean> list) {
        this.context = context;
        this.list = list;
    }
    public void setOnBookClickLisnter(OnBookClickListner onBookClickListner){
        this.onBookClickListner = onBookClickListner;
    }
    public interface OnBookClickListner{
        void OnBookClick(int position);
    }
    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_receive_schedule_train, null);
            holder = new ViewHolder();
            holder.bkInfo = (TextView)convertView.findViewById(R.id.bkInfo);
            holder.tvPlan = (TextView)convertView.findViewById(R.id.tvPlan);
            holder.llcontent = (LinearLayout) convertView.findViewById(R.id.llcontent);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        ReceiveTrainBean entry = list.get(position);
        if(entry!=null){
            if(entry.getTrainNo()!=null){
                if(entry.getRepairClassName()!=null&&!"".equals(entry.getRepairClassName())){
                    holder.bkInfo.setText(entry.getTrainTypeShortName()+" "+entry.getTrainNo()
                    +"("+entry.getRepairClassName()+")");
                }else {
                    holder.bkInfo.setText(entry.getTrainTypeShortName()+" "+entry.getTrainNo());
                }
            }else {
                holder.bkInfo.setText("");
            }
            if(entry.getDeliverEmpName()!=null&&!"".equals(entry.getDeliverEmpName())){
                holder.tvPlan.setText(entry.getDeliverEmpName());
            }
            holder.llcontent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onBookClickListner!=null){
                        onBookClickListner.OnBookClick(position);
                    }
                }
            });
        }
        return convertView;
    }

    class ViewHolder {
        TextView bkInfo;
        TextView tvPlan;
        LinearLayout llcontent;

    }
}
