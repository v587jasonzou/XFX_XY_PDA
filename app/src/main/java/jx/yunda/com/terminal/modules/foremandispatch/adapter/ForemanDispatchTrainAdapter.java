package jx.yunda.com.terminal.modules.foremandispatch.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.foremandispatch.model.TrainForForemanDispatch;

public class ForemanDispatchTrainAdapter extends BaseAdapter {
    public Context mcontext;
    public List<TrainForForemanDispatch> list;
    ViewHolder holder;
    public ForemanDispatchTrainAdapter(Context context, List<TrainForForemanDispatch> list){
        mcontext = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        if(list!=null&&list.size()>0){
            return list.size();
        }else {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.rollings_item_red_dot,null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
        holder.tv_redDot = (ImageView) convertView.findViewById(R.id.tv_redDot);
        if(list!=null&&list.size()>0){
            holder.tv_number.setText(list.get(position).getTrainTypeShortName()+" "+list.get(position).getTrainNo());
            if(list.get(position).getState()==0){
                holder.tv_number.setTextColor(ContextCompat.getColor(mcontext, R.color.tickettextcolor));
                holder.tv_number.setBackground(ContextCompat.getDrawable(mcontext,R.drawable.editext_bg));
            }else {
                holder.tv_number.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                holder.tv_number.setBackground(ContextCompat.getDrawable(mcontext,R.drawable.editext_bg_blue));
            }

            if (list.get(position).getUnDoneCountFwh() > 0 || list.get(position).getUnDoneCountTp() > 0) {
                holder.tv_redDot.setVisibility(View.VISIBLE);
            } else {
                holder.tv_redDot.setVisibility(View.GONE);
            }
        }
        return convertView;
    }
    class ViewHolder{
        TextView tv_number;
        ImageView tv_redDot;
    }
}
