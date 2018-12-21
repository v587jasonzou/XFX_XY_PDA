package jx.yunda.com.terminal.modules.tpprocess.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.tpprocess.model.DictBean;

public class TicketDictAdapter extends BaseAdapter {
    Context context;
    List<DictBean> list;
    ViewHolder holder;
    OnDicClickListner onDicClickListner;
    public TicketDictAdapter(Context context, List<DictBean> list) {
        this.context = context;
        this.list = list;
    }
    public interface OnDicClickListner{
        void OnDicClick(int position);
    }
    public void setOnDicClickListner(OnDicClickListner onDicClickListner){
        this.onDicClickListner = onDicClickListner;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ticket_dictinfo, null);
            holder = new ViewHolder();
            holder.tvInfo = (TextView)convertView.findViewById(R.id.tvInfo);
            holder.tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        DictBean entry = list.get(position);
        if(entry!=null){
            holder.tvTitle.setText(entry.getName());
            if(entry.getChildList()!=null&&entry.getChildList().size()>0){
                String str = "";
                for(DictBean child:entry.getChildList()){
                    if(child.isChecked()){
                        if(str.equals("")){
                            str = str+child.getName();
                        }else {
                            str = str+";"+child.getName();
                        }
                    }
                }
                holder.tvInfo.setText(str);
            }
        }
        holder.tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDicClickListner.OnDicClick(position);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tvTitle;
        TextView tvInfo;
    }
}
