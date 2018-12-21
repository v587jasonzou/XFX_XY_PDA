package jx.yunda.com.terminal.modules.tpprocess.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTypeBean;

public class TicketTypeAdapter extends BaseAdapter {
    List<TicketTypeBean> list;
    Context mContext;
    ViewHolder holder;
    public  TicketTypeAdapter(Context context,List<TicketTypeBean> list){
        mContext = context;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tickrt_type,null);
            holder = new ViewHolder();
            holder.llType = (LinearLayout)convertView.findViewById(R.id.llType);
            holder.tvType = (TextView)convertView.findViewById(R.id.tvType);
            holder.ivType = (ImageView)convertView.findViewById(R.id.ivType);
            holder.ivCircle = (ImageView)convertView.findViewById(R.id.ivCircle);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
//        holder.llType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        if(list!=null&&list.size()>0){
            holder.tvType.setText(list.get(position).getDictname());
            if(list.get(position).getState()==0){
                holder.tvType.setTextColor(ContextCompat.getColor(mContext, R.color.tickettypetextcolor));
                holder.llType.setBackground(ContextCompat.getDrawable(mContext,R.color.slideBg));
                holder.ivType.setBackground(ContextCompat.getDrawable(mContext,R.drawable.tipiao_unclick));
            }else {
                holder.tvType.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.llType.setBackground(ContextCompat.getDrawable(mContext,R.color.textviewBg));
                holder.ivType.setBackground(ContextCompat.getDrawable(mContext,R.drawable.tipiao_click));
            }
            if(list.get(position).getUnSubmit()>0){
                holder.ivCircle.setVisibility(View.VISIBLE);
            }else {
                holder.ivCircle.setVisibility(View.GONE);
            }
        }
        return convertView;
    }
    class ViewHolder{
        LinearLayout llType;
        TextView tvType;
        ImageView ivType,ivCircle;
    }
}
