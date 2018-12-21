package jx.yunda.com.terminal.modules.foremandispatch.adapter;

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
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter.OnViewClickInItemListner;

public class ForemanDispatchHisTicketListAdapter extends BaseAdapter {
    Context mContext;
    List<FaultTicket> mlist;
    OnViewClickInItemListner onViewClickListner;
    public ForemanDispatchHisTicketListAdapter(Context context, List<FaultTicket> list) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_foremandispatch_his_ticket, null);
        TextView tvTicketLocation = (TextView)convertView.findViewById(R.id.tvTicketLocation);
        TextView tvOperator = (TextView)convertView.findViewById(R.id.tvOperator);
        TextView tvResolution = (TextView)convertView.findViewById(R.id.tvResolution);
        TextView tvStatus = (TextView)convertView.findViewById(R.id.tv_status);
        ImageView ivRedispatch = (ImageView)convertView.findViewById(R.id.iv_redispatch);
        LinearLayout llRedispatch = (LinearLayout)convertView.findViewById(R.id.ll_redispatch);
        LinearLayout llInfo = (LinearLayout)convertView.findViewById(R.id.llInfo);
        llInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClickListner.onViewClick(v, position);
            }
        });
        if(mlist!=null&&mlist.size()>0){
            if(mlist.get(position)!=null){
                FaultTicket bean = mlist.get(position);
                if(!TextUtils.isEmpty(bean.getFixPlaceFullName())){
                    tvTicketLocation.setText(bean.getFixPlaceFullName());
                }

                if (!TextUtils.isEmpty(bean.getResolutionContent())) {
                    tvResolution.setText(bean.getResolutionContent());
                }

                if(!TextUtils.isEmpty(bean.getDispatchEmp())){
                    tvOperator.setText(bean.getDispatchEmp());
                }

                ivRedispatch.setVisibility(View.GONE);
                // tvStatus.setTextColor(ContextCompat.getColor(tvStatus.getContext(), R.color.icon_yellow));
                if (FaultTicket.STATUS_DRAFT == bean.getStatus()) {
                    tvStatus.setText("重派");
                    tvStatus.setTextColor(ContextCompat.getColor(tvStatus.getContext(), R.color.title_blue));
                    ivRedispatch.setVisibility(View.VISIBLE);
                } else if (FaultTicket.STATUS_OPEN == bean.getStatus()) {
                    tvStatus.setText("重派");
                    tvStatus.setTextColor(ContextCompat.getColor(tvStatus.getContext(), R.color.title_blue));
                    ivRedispatch.setVisibility(View.VISIBLE);
                } else if (FaultTicket.STATUS_OVER == bean.getStatus()) {
                    tvStatus.setText(FaultTicket.STATUS_OVER_CH);
                    tvStatus.setTextColor(ContextCompat.getColor(tvStatus.getContext(), R.color.icon_green));
                }

                llRedispatch.setOnClickListener(new View.OnClickListener() {
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
