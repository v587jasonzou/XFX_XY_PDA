package jx.yunda.com.terminal.modules.quality.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.TrainRecandition.model.RecanditionBean;
import jx.yunda.com.terminal.modules.quality.model.TrainQualityBean;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;

public class TrainQualityadapter extends BaseAdapter {
    public Context mContext;
    public List<TrainQualityBean> mlist;
    ViewHolder holder;
    OnBtnClickLisnter onBtnClickLisnter;
    public interface OnBtnClickLisnter {
        void OnBtnClick(int position, String status);
    }
    public void setOnBtnClickLisnter(OnBtnClickLisnter onBtnClickLisnter){
        this.onBtnClickLisnter = onBtnClickLisnter;
    }
    public TrainQualityadapter(Context context, List<TrainQualityBean> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_train_quality, null);
            holder = new ViewHolder();
            holder.tvPlanName = (TextView)convertView.findViewById(R.id.tvPlanName);
            holder.tvTime = (TextView)convertView.findViewById(R.id.tvTime);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        if(mlist.get(position).getNodeName()!=null)
        holder.tvPlanName.setText(mlist.get(position).getNodeName());
        holder.tvTime.setText(mlist.get(position).getPlanBeginTime()
                +"——"+mlist.get(position).getPlanEndTime());
        return convertView;
    }


    class ViewHolder {
        TextView tvPlanName;
        TextView tvTime;

    }
}
