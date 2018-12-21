package jx.yunda.com.terminal.modules.TrainRecandition.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;

public class DialogAdapter extends BaseAdapter {
    Context context;
    List<String> list;
    ViewHolder holder;
    public DialogAdapter(Context context,List<String> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_workorder_dialog,null);
            holder = new ViewHolder();
            holder.tvItem = (TextView) convertView.findViewById(R.id.tvItem);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tvItem.setText(list.get(position));
        return convertView;
    }
    class ViewHolder{
        TextView tvItem;
    }

}
