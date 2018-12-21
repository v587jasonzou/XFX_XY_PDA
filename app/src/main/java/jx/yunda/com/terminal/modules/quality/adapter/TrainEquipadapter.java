package jx.yunda.com.terminal.modules.quality.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.quality.model.QualityEquipPlanBean;
import jx.yunda.com.terminal.modules.quality.model.TrainQualityBean;

public class TrainEquipadapter extends BaseAdapter {
    public Context mContext;
    public List<QualityEquipPlanBean> mlist;
    ViewHolder holder;
    OnBtnClickLisnter onBtnClickLisnter;
    public interface OnBtnClickLisnter {
        void OnBtnClick(int position, String status);
    }
    public void setOnBtnClickLisnter(OnBtnClickLisnter onBtnClickLisnter){
        this.onBtnClickLisnter = onBtnClickLisnter;
    }
    public TrainEquipadapter(Context context, List<QualityEquipPlanBean> list) {
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
        if(mlist.get(position).getPartsName()!=null)
        holder.tvPlanName.setText(mlist.get(position).getPartsName());
        holder.tvTime.setText(mlist.get(position).getPlanStarttime()
                +"——"+mlist.get(position).getPlanEndtime());
        return convertView;
    }


    class ViewHolder {
        TextView tvPlanName;
        TextView tvTime;

    }
}
