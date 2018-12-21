package jx.yunda.com.terminal.modules.TrainRecandition.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.TrainRecandition.model.DownEquipBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.EquipBean;

public class DownEquipListAdapter extends BaseAdapter {
    Context mContext;
    List<DownEquipBean> mList;
    ViewHolder holder;
    OnDownClickListner onUpClickListner;
    int status = 0;
    String type = "";
    public DownEquipListAdapter(Context context, List<DownEquipBean> list, String type) {
        mContext = context;
        mList = list;
        this.type = type;
    }
    public void setOnDownClickListner(OnDownClickListner onUpClickListner){
        this.onUpClickListner = onUpClickListner;
    }
    public interface OnDownClickListner{
        void OnDwonClick(int position);
        void OnBackClick(int position);
    }
    public void setStatus(int statu){
        status = statu;
    }
    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.up_equip_item, null);
            holder = new ViewHolder();
            holder.ivReback = (ImageView) convertView.findViewById(R.id.ivReback);
            holder.tvEquipName = (TextView) convertView.findViewById(R.id.tvEquipName);
            holder.tvEquipCode = (TextView) convertView.findViewById(R.id.tvEquipCode);
            holder.tvEquipType = (TextView) convertView.findViewById(R.id.tvEquipType);
            holder.RlContent = (RelativeLayout) convertView.findViewById(R.id.RlContent);
            holder.llContent = (LinearLayout) convertView.findViewById(R.id.llContent);
            holder.tvNoEquipName = (TextView) convertView.findViewById(R.id.tvNoEquipName);
            holder.tvNumber = (TextView) convertView.findViewById(R.id.tvNumber);
            holder.tvScanCode = (TextView) convertView.findViewById(R.id.tvScanCode);
            holder.btUpEquip = (Button) convertView.findViewById(R.id.btUpEquip);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (status == 0) {
            holder.ivReback.setVisibility(View.GONE);
            holder.llContent.setVisibility(View.VISIBLE);
            holder.RlContent.setVisibility(View.GONE);
            holder.btUpEquip.setText("下配件");
            if (mList.get(position).getPartsName() != null)
                holder.tvNoEquipName.setText(mList.get(position).getPartsName());
            if (mList.get(position).getUnloadPlace() != null)
                holder.tvNumber.setText(mList.get(position).getUnloadPlace());
            holder.btUpEquip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUpClickListner.OnDwonClick(position);
                }
            });
        } else {
            holder.RlContent.setVisibility(View.VISIBLE);
            holder.llContent.setVisibility(View.GONE);
            holder.ivReback.setVisibility(View.VISIBLE);
            holder.ivReback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUpClickListner.OnBackClick(position);
                }
            });
            if (mList.get(position).getPartsName() != null)
                holder.tvEquipName.setText(mList.get(position).getPartsName());
            if (mList.get(position).getPartsNo() != null)
                holder.tvEquipCode.setText(mList.get(position).getPartsNo());
            if (mList.get(position).getSpecificationModel() != null)
                holder.tvEquipType.setText(mList.get(position).getSpecificationModel());
            if (mList.get(position).getIdentificationCode() != null)
                holder.tvScanCode.setText(mList.get(position).getIdentificationCode());
        }
        return convertView;
    }


    class ViewHolder {
        ImageView ivReback;
        TextView tvEquipName;
        TextView tvEquipCode, tvNoEquipName, tvNumber, tvScanCode;
        TextView tvEquipType;
        RelativeLayout RlContent;
        LinearLayout llContent;
        Button btUpEquip;
    }
}
