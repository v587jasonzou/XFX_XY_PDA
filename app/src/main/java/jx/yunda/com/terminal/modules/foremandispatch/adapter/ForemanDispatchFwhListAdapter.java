package jx.yunda.com.terminal.modules.foremandispatch.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.foremandispatch.model.FwFormenBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FwhBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter.OnViewClickInItemListner;

public class ForemanDispatchFwhListAdapter extends BaseAdapter {
    Context mContext;
    List<FwFormenBean> mlist;
    OnViewClickInItemListner onViewClickListner;
    public ForemanDispatchFwhListAdapter(Context context, List<FwFormenBean> list) {
        mContext = context;
        mlist = list;
    }

    public void setOnViewClickListner(OnViewClickInItemListner onViewClickListner){
        this.onViewClickListner = onViewClickListner;
    }
    @Override
    public int getCount() {
        if (mlist != null && mlist.size() > 0) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_foremandispatch_fwh, null);
        TextView tvFwhLocation = (TextView)convertView.findViewById(R.id.tvFwhLocation);
        TextView tvOperator = (TextView)convertView.findViewById(R.id.tvOperator);
        Button btnDispatch = (Button)convertView.findViewById(R.id.btn_dispatch_fwh);
        LinearLayout llInfo = (LinearLayout)convertView.findViewById(R.id.llInfo_fwh);
        llInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClickListner.onViewClick(v, position);
            }
        });
        if(mlist!=null&&mlist.size()>0){
            if(mlist.get(position)!=null){
                FwFormenBean bean = mlist.get(position);
                if(!TextUtils.isEmpty(bean.getNodeName())){
                    tvFwhLocation.setText(bean.getNodeName());
                }

                if(!TextUtils.isEmpty(bean.getWorkStationBelongTeamName()) || !TextUtils.isEmpty(bean.getWorkerNameStr())){
                    tvOperator.setText(TextUtils.isEmpty(bean.getWorkerNameStr()) ? bean.getWorkStationBelongTeamName() : bean.getWorkerNameStr());
                }

                btnDispatch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onViewClickListner != null) {
                            onViewClickListner.onViewClick(view, position);
                        }
                    }
                });
            }
        }

        return convertView;
    }

}
