package jx.yunda.com.terminal.modules.TrainRecandition.adapter;

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
import jx.yunda.com.terminal.modules.TrainRecandition.model.JXTrainBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTrainBean;

public class TrainAdapter extends BaseAdapter {
    public Context mcontext;
    public List<JXTrainBean> list;
    ViewHolder holder;
    public TrainAdapter(Context context, List<JXTrainBean> list){
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
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.rollings_item_new,null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
        holder.ivCircle = (ImageView)convertView.findViewById(R.id.ivCircle);
        if(list!=null&&list.size()>0){
            if(list.get(position).getFwhUnDoneCount()==null||list.get(position).getFwhUnDoneCount()==0){
                holder.ivCircle.setVisibility(View.GONE);
            }else {
                holder.ivCircle.setVisibility(View.VISIBLE);
            }
            holder.tv_number.setText(list.get(position).getTrainTypeShortName()+" "+list.get(position).getTrainNo());
            if(list.get(position).getState()==0){
                holder.tv_number.setTextColor(ContextCompat.getColor(mcontext, R.color.tickettextcolor));
                holder.tv_number.setBackground(ContextCompat.getDrawable(mcontext,R.drawable.editext_bg));
            }else {
                holder.tv_number.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                holder.tv_number.setBackground(ContextCompat.getDrawable(mcontext,R.drawable.editext_bg_blue));
            }
        }
        return convertView;
    }
    class ViewHolder{
        TextView tv_number;
        ImageView ivCircle;
    }
}
