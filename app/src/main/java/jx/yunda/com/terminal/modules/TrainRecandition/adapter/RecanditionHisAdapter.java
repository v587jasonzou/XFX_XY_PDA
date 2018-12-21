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

public class RecanditionHisAdapter extends BaseAdapter {
    public Context mContext;
    public List<RecanditionBean> mlist;
    ViewHolder holder;
    OnBtnClickLisnter onBtnClickLisnter;
    public interface OnBtnClickLisnter {
        void OnBtnClick(int position, String status);
    }
    public void setOnBtnClickLisnter(OnBtnClickLisnter onBtnClickLisnter){
        this.onBtnClickLisnter = onBtnClickLisnter;
    }
    public RecanditionHisAdapter(Context context, List<RecanditionBean> list) {
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
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        if(mlist.get(position).getNodeName()!=null)
        holder.tvPlanName.setText(mlist.get(position).getNodeName());
        holder.tvTime.setText(Utils.formatTime(mlist.get(position).getPlanBeginTime(),"MM-dd HH:mm")
                +"——"+Utils.formatTime(mlist.get(position).getPlanEndTime(),"MM-dd HH:mm"));
        if(mlist.get(position).getTrainTypeShortName()!=null&&mlist.get(position).getTrainNo()!=null)
        holder.tvTrainInfo.setText(mlist.get(position).getTrainTypeShortName()+" "+mlist.get(position).getTrainNo());
        holder.tvStarttime.setText(Utils.formatTime(mlist.get(position).getRealEndTime(),"MM-dd HH:mm"));
        return convertView;
    }


    class ViewHolder {
        TextView tvPlanName;
        TextView tvTime;
        TextView tvStarttime;
        TextView tvTrainInfo;


    }
}
