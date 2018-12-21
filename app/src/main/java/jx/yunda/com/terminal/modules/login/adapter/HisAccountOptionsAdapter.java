package jx.yunda.com.terminal.modules.login.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import jx.yunda.com.terminal.R;

public class HisAccountOptionsAdapter extends ArrayAdapter<String> {

    int resourceId;

    public HisAccountOptionsAdapter(@NonNull Context context, int resource, List<String> datas) {
        super(context, resource, datas);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        HisAccountViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new HisAccountViewHolder();
            viewHolder.hisAccountText = (TextView) view.findViewById(R.id.his_account_item);
            if (getCount() == 1)
                viewHolder.hisAccountText.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.item_selector_raduis));
            else if (position == 0)
                viewHolder.hisAccountText.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.item_selector_top_raduis));
            else if (position == getCount() - 1)
                viewHolder.hisAccountText.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.item_selector_bottom_raduis));
            else
                viewHolder.hisAccountText.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.item_selector));
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (HisAccountViewHolder) view.getTag();
        }
        viewHolder.hisAccountText.setText(getItem(position));
        return view;
    }

    class HisAccountViewHolder {
        TextView hisAccountText;
    }
}
