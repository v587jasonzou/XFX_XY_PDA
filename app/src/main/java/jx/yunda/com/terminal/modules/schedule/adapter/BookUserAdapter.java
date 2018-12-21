package jx.yunda.com.terminal.modules.schedule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.schedule.entity.ReceiveUserBean;

public class BookUserAdapter extends BaseAdapter {
    Context context;
    List<ReceiveUserBean> list;
    ViewHolder holder;
    public  BookUserAdapter(Context context,List<ReceiveUserBean> list){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.dialog_simple_item,null);
            holder = new ViewHolder();
            holder.tvItem = (TextView)convertView.findViewById(R.id.tvItem);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        ReceiveUserBean entry = list.get(position);
        if(entry!=null){
            if(entry.getEmpName()!=null){
                holder.tvItem.setText(entry.getEmpName());
            }else {
                holder.tvItem.setText("");
            }
        }
        return convertView;
    }
    class ViewHolder{
        TextView tvItem;
    }
}
