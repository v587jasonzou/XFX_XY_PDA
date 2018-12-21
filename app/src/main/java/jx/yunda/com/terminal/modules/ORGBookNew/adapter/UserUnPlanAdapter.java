package jx.yunda.com.terminal.modules.ORGBookNew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.ORGBookNew.model.Nodebean;

public class UserUnPlanAdapter extends BaseAdapter {
    Context context;
    List<Nodebean> list;
    ViewHolder holder;
    OnItemClickListner onItemClickListner;
    public UserUnPlanAdapter(Context context, List<Nodebean> list) {
        this.context = context;
        this.list = list;
    }
    public interface OnItemClickListner {
        void OnItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_book_user_unplans, null);
            holder = new ViewHolder();
            holder.tvPlanInfo = (TextView)convertView.findViewById(R.id.tvPlanInfo);
            holder.tvTrainInfo = (TextView)convertView.findViewById(R.id.tvTrainInfo);
            holder.ivDelete = (ImageView)convertView.findViewById(R.id.ivDelete);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Nodebean entry = list.get(position);
        if (entry != null) {
            if(entry.getTrainTypeShortName()!=null&&entry.getTrainNo()!=null){
                holder.tvTrainInfo.setText(entry.getTrainTypeShortName()+" "+entry.getTrainNo());
            }else {
                holder.tvTrainInfo.setText("");
            }
            if(entry.getNodeName()!=null){
                holder.tvPlanInfo.setText(entry.getNodeName());
            }
        }
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.OnItemClick(position);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tvTrainInfo;
        TextView tvPlanInfo;
        ImageView ivDelete;
    }
}
