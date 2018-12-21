package jx.yunda.com.terminal.modules.confirmNotify.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.confirmNotify.entity.NotifyListBean;

public class NotifyListAdapter extends BaseAdapter {
    Context context;
    List<NotifyListBean> list;
    ViewHolder holder;
    OnItemclickListner onItemclickListner;
    public interface OnItemclickListner{
        void OnItemClick(int position);
    }
    public void setOnItemclickListner(OnItemclickListner onItemclickListner){
        this.onItemclickListner = onItemclickListner;
    }
    public NotifyListAdapter(Context context,List<NotifyListBean> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        if(list!=null){
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_notify_list,null);
            holder = new ViewHolder();
            holder.cardView = (CardView)convertView.findViewById(R.id.cardView);
            holder.tvNotifyName = (TextView)convertView.findViewById(R.id.tvNotifyName);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(list!=null&&list.size()>0){
            if(list.get(position).getNoticeName()!=null){
                holder.tvNotifyName.setText(list.get(position).getNoticeName());
            }
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemclickListner.OnItemClick(position);
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView tvNotifyName;
        CardView cardView;
    }
}
