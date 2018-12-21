package jx.yunda.com.terminal.modules.TrainRecandition.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.TrainRecandition.model.InspectorOrderBean;

public class DataItemlvAdapter extends BaseAdapter {
    List<InspectorOrderBean> mList;
    LayoutInflater inflater;
    Context mContext;
    MyOnItemclickLisnter onItemClickListner;
    public void setOnMyItemClickListner(MyOnItemclickLisnter onItemClickListner){
        this.onItemClickListner = onItemClickListner;
    }
    public DataItemlvAdapter(Context context,List<InspectorOrderBean> list){
        mContext = context;
        mList = list;
    }
    public interface MyOnItemclickLisnter{
        void OnItemClick(int position);
    }

    @Override
    public int getCount() {
        if(mList!=null){
            return mList.size();
        }else {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_workorder_data, parent, false);
        EditText etData = (EditText) convertView.findViewById(R.id.etData);
        ImageView tvUnit = (ImageView) convertView.findViewById(R.id.tvUnit);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        InspectorOrderBean entry = mList.get(position);
        if (entry != null) {
            tvTitle.setText(entry.getDetectItemContent());
            if (entry.getDetectResult() != null) {
                etData.setText(entry.getDetectResult());
            } else {
                if (entry.getResultName() != null) {
                    etData.setText(entry.getResultName());
                }
            }

            if (entry.getItemResultCount() > 0) {
                tvUnit.setVisibility(View.VISIBLE);
                tvUnit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListner.OnItemClick(position);
                    }
                });
            } else {
                tvUnit.setVisibility(View.GONE);
            }

        }
        return convertView;
    }
}
