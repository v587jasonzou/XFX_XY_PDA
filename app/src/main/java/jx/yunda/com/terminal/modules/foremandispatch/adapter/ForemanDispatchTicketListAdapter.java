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
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter.OnViewClickInItemListner;

public class ForemanDispatchTicketListAdapter extends BaseAdapter {
    Context mContext;
    List<FaultTicket> mlist;
    OnViewClickInItemListner onViewClickListner;
    public ForemanDispatchTicketListAdapter(Context context, List<FaultTicket> list) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_foremandispatch_ticket, null);
        TextView tvTicketLocation = (TextView)convertView.findViewById(R.id.tvTicketLocation);
        TextView tvResolution = (TextView)convertView.findViewById(R.id.tvResolution);
        Button btnDispatch = (Button)convertView.findViewById(R.id.btn_dispatch);
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
                if(!TextUtils.isEmpty(bean.getResolutionContent())){
                    tvResolution.setText(bean.getResolutionContent());
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
