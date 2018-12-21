package jx.yunda.com.terminal.modules.partsrecandition.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.TrainRecandition.model.RecanditionBean;
import jx.yunda.com.terminal.modules.partsrecandition.model.PartsPlanBean;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;

public class PartsHisAdapter extends BaseAdapter {
    public Context mContext;
    public List<PartsPlanBean> mlist;
    ViewHolder holder;
    OnBtnClickLisnter onBtnClickLisnter;
    public interface OnBtnClickLisnter {
        void OnBtnClick(int position, String status);
    }
    public void setOnBtnClickLisnter(OnBtnClickLisnter onBtnClickLisnter){
        this.onBtnClickLisnter = onBtnClickLisnter;
    }
    public PartsHisAdapter(Context context, List<PartsPlanBean> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_recandition_history, null);
            holder = new ViewHolder();
            holder.tvPlanName = (TextView)convertView.findViewById(R.id.tvPlanName);
            holder.tvTime = (TextView)convertView.findViewById(R.id.tvTime);
            holder.tvStarttime = (TextView)convertView.findViewById(R.id.tvStarttime);
            holder.tvTrainInfo = (TextView)convertView.findViewById(R.id.tvTrainInfo);
            holder.tvPartsName = (TextView)convertView.findViewById(R.id.tvPartsName);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        if(mlist.get(position).getWpNodeName()!=null)
        holder.tvPlanName.setText(mlist.get(position).getWpNodeName());
        String starttime = mlist.get(position).getPlanStartTime();
        String endTime = mlist.get(position).getPlanEndTime();
        if(starttime!=null&&endTime!=null){
            starttime = Utils.formatTime(Utils.stringToLong(starttime,"yyyy-MM-dd HH:mm:ss"),"MM-dd HH:mm");
            endTime = Utils.formatTime(Utils.stringToLong(endTime,"yyyy-MM-dd HH:mm:ss"),"MM-dd HH:mm");
        }
        holder.tvTime.setText(starttime +"——"+endTime);
        if(mlist.get(position).getPartsName()!=null&&mlist.get(position).getPartsNo()!=null)
        holder.tvTrainInfo.setText(mlist.get(position).getUnloadTrainType()+" "+mlist.get(position).getUnloadTrainNo());
        String realTime = mlist.get(position).getRealEndTime();
        if(realTime!=null){
            realTime = Utils.formatTime(Utils.stringToLong(realTime,"yyyy-MM-dd HH:mm:ss"),"MM-dd HH:mm");
            holder.tvStarttime.setText(realTime);
        }
        if(mlist.get(position).getPartsName()!=null){
            holder.tvPartsName.setText(mlist.get(position).getPartsName());
        }
        return convertView;
    }


    class ViewHolder {
        TextView tvPlanName;
        TextView tvTime;
        TextView tvStarttime;
        TextView tvTrainInfo;
        TextView tvPartsName;

    }
}
