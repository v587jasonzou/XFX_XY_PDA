package jx.yunda.com.terminal.modules.schedule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.schedule.entity.StationBean;
import jx.yunda.com.terminal.widget.MyBookOrgView;


public class StationAdapter extends BaseAdapter {
    List<StationBean> mList;
    Context context;
    ViewHolder holder;
    public StationAdapter(Context context, List<StationBean> list) {
        this.context = context;
        mList = list;

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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_schedule_station, null);
            holder = new ViewHolder();
            holder.tvStationName = (TextView) convertView.findViewById(R.id.tvStationName);
            holder.tvNodeName = (TextView) convertView.findViewById(R.id.tvNodeName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final StationBean bean = mList.get(position);
        if(bean!=null){
            final View finalConvertView = convertView;
            holder.tvStationName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bean.getWorkStations()!=null&&bean.getWorkStations().size()>0){
                        final PopupMenu popupMenu = new PopupMenu(context, finalConvertView);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                if (imm != null) {
                                    imm.hideSoftInputFromWindow(holder.tvStationName.getWindowToken(), 0);
                                }
                                bean.setWorkStationName(item.getTitle().toString());
                                bean.setWorkStationIDX(bean.getWorkStations().get(item.getItemId()-1).getWorkStationCode());
                                popupMenu.dismiss();
                                notifyDataSetChanged();
                                return false;
                            }
                        });
                        android.view.Menu menu_more = popupMenu.getMenu();
                        menu_more.clear();
                        int size = bean.getWorkStations().size();
                        for (int i = 0; i < size; i++) {
                            menu_more.add(android.view.Menu.NONE, android.view.Menu.FIRST + i, i, bean.getWorkStations().get(i).getWorkStationName());
                        }
                        popupMenu.show();
                    }
                }
            });
            if(bean.getWorkStationName()!=null&&!"".equals(bean.getWorkStationName())){
                holder.tvStationName.setText(bean.getWorkStationName());
            }
           if(bean.getNodeName()!=null&&!"".equals(bean.getNodeName())){
                holder.tvNodeName.setText(bean.getNodeName());
           }
        }
        return convertView;
    }

     class ViewHolder {
         TextView tvStationName;
         TextView tvNodeName;

    }


}
