package jx.yunda.com.terminal.modules.FJ_ticket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.FJ_ticket.model.RecheckBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketInfoBean;

public class CheckListAdapter extends BaseAdapter {
    Context context;
    List<RecheckBean> list;
    ViewHolder holder;
    public CheckListAdapter(Context context, List<RecheckBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.recheck_list_item, null);
            holder.tvTicketLocation = (TextView) convertView.findViewById(R.id.tvTicketLocation);
            holder.tvTicketType = (TextView)convertView.findViewById(R.id.tvTicketType);
            holder.tvTicketDesign = (TextView)convertView.findViewById(R.id.tvTicketDesign);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tvTicketLocation.setText(list.get(position).getFixPlaceFullName());
        if(list.get(position).getLb()!=null&&!list.get(position).getLb().equals("")){
            holder.tvTicketType.setText(list.get(position).getLb());
        }else {
            holder.tvTicketType.setText("无");
        }
        if(list.get(position).getResolutionContent()!=null&&!list.get(position).getResolutionContent().equals("")&&!list.get(position).getResolutionContent().equals("null")){
            holder.tvTicketDesign.setText(list.get(position).getResolutionContent());
        }else {
            holder.tvTicketDesign.setText("无");
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tvTicketLocation;
        TextView tvTicketType;
        TextView tvTicketDesign;
    }
}
