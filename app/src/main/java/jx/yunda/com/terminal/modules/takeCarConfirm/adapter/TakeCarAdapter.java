package jx.yunda.com.terminal.modules.takeCarConfirm.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.takeCarConfirm.entry.TakeCarBean;

public class TakeCarAdapter extends BaseAdapter {
    Context context;
    List<TakeCarBean> list;
    ViewHolder holder;
    public TakeCarAdapter(Context context, List<TakeCarBean> list) {
        this.context = context;
        this.list = list;
    }
    OnConfirmClickListner onConfirmClickListner;
    public interface OnConfirmClickListner{
        void OnConfirmClick(int position);
    }
    public void setOnConfirmClickListner(OnConfirmClickListner onConfirmClickListner){
        this.onConfirmClickListner = onConfirmClickListner;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_take_car_confirm_list, null);
            holder = new ViewHolder();
            holder.cardView = (CardView)convertView.findViewById(R.id.cardView);
            holder.tvTrainNo = (TextView) convertView.findViewById(R.id.tvTrainNo);
            holder.tvRepairClass = (TextView) convertView.findViewById(R.id.tvRepairClass);
            holder.tvStartLoc = (TextView) convertView.findViewById(R.id.tvStartLoc);
            holder.tvEndLoc = (TextView) convertView.findViewById(R.id.tvEndLoc);
            holder.tvConfirm = (TextView) convertView.findViewById(R.id.tvConfirm);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        TakeCarBean entry = list.get(position);
        if(entry!=null){
            if(entry.getTrainTypeShortName()!=null&&entry.getTrainNo()!=null){
                holder.tvTrainNo.setText(entry.getTrainTypeShortName()+" "+entry.getTrainNo());
            }else {
                holder.tvTrainNo.setText("");
            }
            if(entry.getRepairClassName()!=null){
                holder.tvRepairClass.setText(entry.getRepairClassName());
            }else {
                holder.tvRepairClass.setText("");
            }
            if(entry.getHomePosition()!=null){
                holder.tvStartLoc.setText(entry.getHomePosition());
            }else {
                holder.tvStartLoc.setText("无");
            }
            if(entry.getDestination()!=null){
                holder.tvEndLoc.setText(entry.getDestination());
            }else {
                holder.tvEndLoc.setText("无");
            }
            holder.tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onConfirmClickListner.OnConfirmClick(position);
                }
            });
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvTrainNo;
        TextView tvRepairClass;
        TextView tvStartLoc;
        TextView tvEndLoc;
        TextView tvConfirm;
        CardView cardView;
    }
}
