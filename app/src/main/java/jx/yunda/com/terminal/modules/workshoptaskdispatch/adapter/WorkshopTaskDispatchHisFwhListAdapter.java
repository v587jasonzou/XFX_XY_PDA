package jx.yunda.com.terminal.modules.workshoptaskdispatch.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FwhBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter.OnViewClickInItemListner;

public class WorkshopTaskDispatchHisFwhListAdapter extends BaseAdapter {
    Context mContext;
    List<FwhBean> mlist;
    OnViewClickInItemListner onViewClickListner;
    public WorkshopTaskDispatchHisFwhListAdapter(Context context, List<FwhBean> list) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_workshoptask_his_fwh, null);
        TextView tvFwhLocation = (TextView)convertView.findViewById(R.id.tvFwhLocation);
        TextView tvOperatorTitle = (TextView)convertView.findViewById(R.id.tvOperatorTitle);
        TextView tvOperator = (TextView)convertView.findViewById(R.id.tvOperator);
        TextView tvWorkstation = (TextView)convertView.findViewById(R.id.tvWorkstation);
        TextView tvStatusFwh = (TextView)convertView.findViewById(R.id.tv_status_fwh);
        ImageView ivRedispatchFwh = (ImageView)convertView.findViewById(R.id.iv_redispatch_fwh);
        LinearLayout llRedispatchFwh = (LinearLayout)convertView.findViewById(R.id.ll_redispatch_fwh);
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

                ivRedispatchFwh.setVisibility(View.GONE);
                tvStatusFwh.setTextColor(ContextCompat.getColor(tvStatusFwh.getContext(), R.color.icon_yellow));
                if (FwhBean.ISSENDNODE_Y == bean.getIsSendNode() && FwhBean.STATUS_UNSTART.equals(bean.getNodeStatus())) {
                    tvStatusFwh.setText("重派");
                    tvStatusFwh.setTextColor(ContextCompat.getColor(tvStatusFwh.getContext(), R.color.title_blue));
                    ivRedispatchFwh.setVisibility(View.VISIBLE);
                } else if (FwhBean.STATUS_GOING.equals(bean.getNodeStatus())) {
                    tvStatusFwh.setText(FwhBean.STATUS_GOING_CH);
                } else if (FwhBean.STATUS_FINISHED.equals(bean.getNodeStatus())) {
                    tvStatusFwh.setText(FwhBean.STATUS_FINISHED_CH);
                } else if (FwhBean.STATUS_COMPLETE.equals(bean.getNodeStatus())) {
                    tvStatusFwh.setText(FwhBean.STATUS_COMPLETE_CH);
                    tvStatusFwh.setTextColor(ContextCompat.getColor(tvStatusFwh.getContext(), R.color.icon_green));
                } else if (FwhBean.STATUS_STOP.equals(bean.getNodeStatus())) {
                    tvStatusFwh.setText(FwhBean.STATUS_STOP_CH);
                    tvStatusFwh.setTextColor(ContextCompat.getColor(tvStatusFwh.getContext(), R.color.red));
                } else if (FwhBean.STATUS_DELAY.equals(bean.getNodeStatus())) {
                    tvStatusFwh.setText(FwhBean.STATUS_DELAY_CH);
                    tvStatusFwh.setTextColor(ContextCompat.getColor(tvStatusFwh.getContext(), R.color.red));
                }

                llRedispatchFwh.setOnClickListener(new View.OnClickListener() {
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
