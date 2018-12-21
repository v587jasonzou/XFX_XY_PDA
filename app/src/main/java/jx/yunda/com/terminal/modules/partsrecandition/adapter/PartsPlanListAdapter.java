package jx.yunda.com.terminal.modules.partsrecandition.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.TrainRecandition.act.RecanditionListActivity;
import jx.yunda.com.terminal.modules.TrainRecandition.model.RecanditionBean;
import jx.yunda.com.terminal.modules.partsrecandition.model.PartsPlanBean;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;

public class PartsPlanListAdapter extends BaseAdapter {
    public Context mContext;
    public List<PartsPlanBean> mlist;
    ViewHolder holder;
    OnBtnClickLisnter onBtnClickLisnter;
    public interface OnBtnClickLisnter {
        void OnBtnClick(int position, String status);
        void OnDownClick(int position);
        void OnUpClick(int position);
    }
    public void setOnBtnClickLisnter(OnBtnClickLisnter onBtnClickLisnter){
        this.onBtnClickLisnter = onBtnClickLisnter;
    }
    public PartsPlanListAdapter(Context context, List<PartsPlanBean> list) {
        mContext = context;
        mlist = list;
    }

    @Override
    public int getCount() {
        if (mlist != null) {
            return mlist.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_recandition, null);
            holder = new ViewHolder();
            holder.tvPlanName = (TextView)convertView.findViewById(R.id.tvPlanName);
            holder.tvTime = (TextView)convertView.findViewById(R.id.tvTime);
            holder.tvStart = (TextView)convertView.findViewById(R.id.tvStart);
            holder.tvUpIcon = (TextView)convertView.findViewById(R.id.tv_upIcon);
            holder.tvDownIcon = (TextView)convertView.findViewById(R.id.tv_downIcon);
            holder.llPartsInfo = (LinearLayout) convertView.findViewById(R.id.llPartsInfo);
            holder.tvPartsName = (TextView)convertView.findViewById(R.id.tvPartsName);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        if(mlist.get(position).getPartsOffCount()!=null&&mlist.get(position).getPartsOnCount()!=null){
            holder.llPartsInfo.setVisibility(View.VISIBLE);
            holder.tvDownIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBtnClickLisnter.OnDownClick(position);
                }
            });
            holder.tvUpIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBtnClickLisnter.OnUpClick(position);
                }
            });

        }else {
            holder.llPartsInfo.setVisibility(View.GONE);
        }
        if(mlist.get(position).getWpNodeName()!=null)
        holder.tvPlanName.setText(mlist.get(position).getWpNodeName());
        String starttime = mlist.get(position).getPlanStartTime();
        String endTime = mlist.get(position).getPlanEndTime();
        if(starttime!=null&&endTime!=null){
            starttime = Utils.formatTime(Utils.stringToLong(starttime,"yyyy-MM-dd HH:mm:ss"),"MM-dd HH:mm");
            endTime = Utils.formatTime(Utils.stringToLong(endTime,"yyyy-MM-dd HH:mm:ss"),"MM-dd HH:mm");
        }
        if(mlist.get(position).getPartsName()!=null){
            holder.tvPartsName.setText(mlist.get(position).getPartsName());
        }
        holder.tvTime.setText(starttime +"——"+endTime);

        holder.tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnClickLisnter.OnBtnClick(position,mlist.get(position).getStatus());
            }
        });
        return convertView;
    }


    class ViewHolder {
        TextView tvPlanName;
        TextView tvTime;
        TextView tvStart;
        TextView tvUpIcon;
        TextView tvDownIcon;
        TextView tvPartsName;
        LinearLayout llPartsInfo;

    }
}
