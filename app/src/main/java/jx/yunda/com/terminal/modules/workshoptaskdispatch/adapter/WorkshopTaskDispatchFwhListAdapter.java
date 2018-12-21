package jx.yunda.com.terminal.modules.workshoptaskdispatch.adapter;

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
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FwhBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter.OnViewClickInItemListner;

public class WorkshopTaskDispatchFwhListAdapter extends BaseAdapter {
    Context mContext;
    List<FwhBean> mlist;
    OnViewClickInItemListner onViewClickListner;
    public WorkshopTaskDispatchFwhListAdapter(Context context, List<FwhBean> list) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_workshoptask_fwh, null);
        TextView tvFwhLocation = (TextView)convertView.findViewById(R.id.tvFwhLocation);
        TextView tvOperatorTitle = (TextView)convertView.findViewById(R.id.tvOperatorTitle);
        TextView tvOperator = (TextView)convertView.findViewById(R.id.tvOperator);
        TextView tvWorkstation = (TextView)convertView.findViewById(R.id.tvWorkstation);
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
                FwhBean bean = mlist.get(position);
                if(!TextUtils.isEmpty(bean.getNodeName())){
                    tvFwhLocation.setText(bean.getNodeName());
                }

                if(!TextUtils.isEmpty(bean.getWorkStationBelongTeamName()) || !TextUtils.isEmpty(bean.getWorkerNameStr())){
                    if (FwhBean.NODERDPTYPE_CLASS.equals(bean.getNodeRdpType())) {
                        tvOperator.setText(bean.getWorkStationBelongTeamName());
                        tvOperatorTitle.setText("作业班组");
                    } else if (FwhBean.NODERDPTYPE_EMP.equals(bean.getNodeRdpType())) {
                        tvOperator.setText(bean.getWorkerNameStr());
                        tvOperatorTitle.setText("作业人员");
                    }
                }

                if(!TextUtils.isEmpty(bean.getWorkStationName())){
                    tvWorkstation.setText(bean.getWorkStationName());
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
