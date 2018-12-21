package jx.yunda.com.terminal.modules.TrainRecandition.adapter;

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
import jx.yunda.com.terminal.widget.treeview.utils.Utils;

public class RecanditionListAdapter extends BaseAdapter {
    public Context mContext;
    public List<RecanditionBean> mlist;
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
    public RecanditionListAdapter(Context context, List<RecanditionBean> list) {
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
            holder.tvDownIcon = (TextView)convertView.findViewById(R.id.tv_downIcon);
            holder.tvdownPartsize = (TextView)convertView.findViewById(R.id.tvdownPartsize);
            holder.tvUpIcon = (TextView)convertView.findViewById(R.id.tv_upIcon);
            holder.tvupParts = (TextView)convertView.findViewById(R.id.tvupParts);
            holder.llPartsInfo = (LinearLayout) convertView.findViewById(R.id.llPartsInfo);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tvPlanName.setText(mlist.get(position).getNodeName());
        holder.tvTime.setText(Utils.formatTime(mlist.get(position).getPlanBeginTime(),"MM-dd HH:mm")
                +"——"+ Utils.formatTime(mlist.get(position).getPlanEndTime(),"MM-dd HH:mm"));
//        if(RecanditionListActivity.STATUS_NOTSTARTED.equals(mlist.get(position).getStatus())){
//            holder.tvStart.setVisibility(View.VISIBLE);
//            holder.tvStart.setText("开工");
//            holder.tvStart.setTextColor(ContextCompat.getColor(mContext, R.color.acivity_color));
//            holder.tvStart.setBackgroundResource(R.drawable.button_selector_blue);
//        }else if(RecanditionListActivity.STATUS_RUNNING.equals(mlist.get(position).getStatus())){
//            holder.tvStart.setVisibility(View.VISIBLE);
//            holder.tvStart.setText("完工");
//            holder.tvStart.setTextColor(ContextCompat.getColor(mContext, R.color.white));
//            holder.tvStart.setBackgroundResource(R.drawable.button_selector);
//        }else {
//            holder.tvStart.setEnabled(false);
//            holder.tvStart.setVisibility(View.GONE);
//            holder.tvStart.setText("已完工");
//            holder.tvStart.setTextColor(ContextCompat.getColor(mContext, R.color.white));
//            holder.tvStart.setBackgroundResource(R.drawable.button_selector);
//        }
        holder.tvStart.setVisibility(View.VISIBLE);
        holder.tvStart.setText("完工");
        holder.tvStart.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        holder.tvStart.setBackgroundResource(R.drawable.button_selector);
        if(mlist.get(position).getPartsOffCount()!=null&&mlist.get(position).getPartsOnCount()!=null){
//            holder.llPartsInfo.setVisibility(View.VISIBLE);
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


//        holder.tvupParts.setText(mlist.get(position).getUpTimes()+"/"+mlist.get(position).getUpALl());
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
        TextView tvDownIcon;
        TextView tvdownPartsize;
        TextView tvUpIcon;
        TextView tvupParts;
        TextView tvPartsName;
        LinearLayout llPartsInfo;


    }
}
