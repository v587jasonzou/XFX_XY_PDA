package jx.yunda.com.terminal.modules.confirmNotify.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.baseDictData.model.DicDataItem;
import jx.yunda.com.terminal.modules.confirmNotify.entity.NotifyDetailBean;
import jx.yunda.com.terminal.modules.schedule.entity.StationBean;
import jx.yunda.com.terminal.utils.ToastUtil;

public class NotifyConfiemAdapter extends BaseAdapter {
    Context context;
    List<NotifyDetailBean> list;
    ViewHolder holder;
    List<DicDataItem> starts ;
    List<DicDataItem> ends ;
    OnConfirmClickLisnter onConfirmClickLisnter;
    public void setOnConfirmClickLisnter(OnConfirmClickLisnter onConfirmClickLisnter){
        this.onConfirmClickLisnter = onConfirmClickLisnter;
    }
    public interface OnConfirmClickLisnter{
        void OnConfirmClick(int position);
    }
    public NotifyConfiemAdapter(Context context, List<NotifyDetailBean> list) {
        this.context = context;
        this.list = list;
    }
    public void setLocation(List<DicDataItem> list1,List<DicDataItem> list2){
        if(list1!=null&&list1.size()>0){
            starts = list1;
        }
        if(list2!=null&&list2.size()>0){
            ends = list2;
        }
    }
    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_notify_confirm_list, null);
            holder = new ViewHolder();
            holder.cardView = (CardView)convertView.findViewById(R.id.cardView);
            holder.tvTrainType = (TextView) convertView.findViewById(R.id.tvTrainType);
            holder.tvTrainNo = (TextView) convertView.findViewById(R.id.tvTrainNo);
            holder.tvStartLoc = (TextView) convertView.findViewById(R.id.tvStartLoc);
            holder.tvEndLoc = (TextView) convertView.findViewById(R.id.tvEndLoc);
            holder.tvConfirm = (TextView)convertView.findViewById(R.id.tvConfirm);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        NotifyDetailBean entity = null;
        if(list!=null&&list.size()>0){
            entity = list.get(position);
            holder.tvTrainType.setText(list.get(position).getTrainTypeShortName());
            holder.tvTrainNo.setText(list.get(position).getTrainNo());
            holder.tvStartLoc.setText(list.get(position).getHomePosition());
            holder.tvEndLoc.setText(list.get(position).getDestination());
            if(entity.getConfirmPersonId()==null){
                holder.tvEndLoc.setEnabled(true);
                holder.tvStartLoc.setEnabled(true);
                holder.tvConfirm.setEnabled(true);
                holder.tvConfirm.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
                holder.tvConfirm.setText("确认");
            }else {
                holder.tvEndLoc.setEnabled(false);
                holder.tvStartLoc.setEnabled(false);
                holder.tvConfirm.setEnabled(false);
                holder.tvConfirm.setTextColor(ContextCompat.getColor(context,R.color.green));
                holder.tvConfirm.setText("已确认");
            }
        }
        final NotifyDetailBean finalEntity = entity;
        holder.tvStartLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(starts!=null&&starts.size()>0){
                    final PopupMenu popupMenu = new PopupMenu(context, holder.tvStartLoc);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (imm != null) {
                                imm.hideSoftInputFromWindow(holder.tvStartLoc.getWindowToken(), 0);
                            }
                            finalEntity.setHomePosition(item.getTitle().toString());
                            finalEntity.setHomePositionId(starts.get(item.getItemId()).getDictid());
                            popupMenu.dismiss();
                            notifyDataSetChanged();
                            return false;
                        }
                    });
                    android.view.Menu menu_more = popupMenu.getMenu();
                    menu_more.clear();
                    int size = starts.size();
                    for (int i = 0; i < size; i++) {
                        menu_more.add(android.view.Menu.NONE,  i, i, starts.get(i).getDictname());
                    }
                    popupMenu.show();
                }else {
                    ToastUtil.toastShort("无开始位置数据");
                }
            }
        });

        holder.tvEndLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ends!=null&&ends.size()>0){
                    final PopupMenu popupMenu = new PopupMenu(context, holder.tvEndLoc);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (imm != null) {
                                imm.hideSoftInputFromWindow(holder.tvEndLoc.getWindowToken(), 0);
                            }
                            finalEntity.setDestination(item.getTitle().toString());
                            finalEntity.setDestinationId(ends.get(item.getItemId()).getDictid());
                            popupMenu.dismiss();
                            notifyDataSetChanged();
                            return false;
                        }
                    });
                    android.view.Menu menu_more = popupMenu.getMenu();
                    menu_more.clear();
                    int size = ends.size();
                    for (int i = 0; i < size; i++) {
                        menu_more.add(android.view.Menu.NONE, i, i, ends.get(i).getDictname());
                    }
                    popupMenu.show();
                }else {
                    ToastUtil.toastShort("无结束位置数据");
                }
            }
        });
        holder.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirmClickLisnter.OnConfirmClick(position);
            }
        });
        return convertView;
    }



    class ViewHolder {
        TextView tvTrainType;
        TextView tvTrainNo;
        TextView tvStartLoc;
        TextView tvEndLoc;
        TextView tvConfirm;
        CardView cardView;


    }
}
