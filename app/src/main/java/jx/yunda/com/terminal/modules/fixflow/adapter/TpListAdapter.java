package jx.yunda.com.terminal.modules.fixflow.adapter;

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
import jx.yunda.com.terminal.modules.fixflow.entry.TicketInfoBean;

public class TpListAdapter extends BaseAdapter {
    Context context;
    List<TicketInfoBean> list;
    ViewHolder holder;

    public TpListAdapter(Context context, List<TicketInfoBean> list) {
        this.context = context;
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tph_list, null);
            holder = new ViewHolder();
            holder.tvCardName = (TextView) convertView.findViewById(R.id.tvCardName);
            holder.tvForeMan = (TextView) convertView.findViewById(R.id.tvForeMan);
            holder.tvType = (TextView) convertView.findViewById(R.id.tvType);
            holder.tvUnDoInfo = (TextView) convertView.findViewById(R.id.tvUnDoInfo);
            holder.tvDoInfo = (TextView) convertView.findViewById(R.id.tvDoInfo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TicketInfoBean entry = list.get(position);
        if (entry != null) {
            if (entry.getDesc() != null) {
                holder.tvCardName.setText(entry.getDesc());
            } else {
                holder.tvCardName.setText("");
            }
            if (entry.getTicketEmp() != null) {
                holder.tvForeMan.setText(entry.getTicketEmp());
            } else {
                holder.tvForeMan.setText("");
            }
            if (entry.getNodeDetailsStatus() != null) {
                holder.tvType.setText(entry.getNodeDetailsStatus());
            } else {
                holder.tvType.setText("");
            }
//            if (entry.getCardFinishedNo() != null) {
//                holder.tvDoInfo.setText(entry.getCardFinishedNo() + "");
//            } else {
//                holder.tvDoInfo.setText("");
//            }
//            if (entry.getCardUnfinishedNo() != null) {
//                holder.tvUnDoInfo.setText(entry.getCardUnfinishedNo() + "");
//            } else {
//                holder.tvUnDoInfo.setText("");
//            }
        }
        return convertView;
    }



    class ViewHolder {
        TextView tvCardName;
        TextView tvForeMan;
        TextView tvType;
        TextView tvUnDoInfo;
        TextView tvDoInfo;

    }
}
