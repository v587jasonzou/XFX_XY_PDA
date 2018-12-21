package jx.yunda.com.terminal.modules.message.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.message.model.MessageGroupPerson;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;

public class MessageGroupPersonSwipeMenuAdapter extends BaseAdapter {
    public Context mContext;
    private List<MessageGroupPerson> data;
    ViewHolder holder;

    public MessageGroupPersonSwipeMenuAdapter(Context context) {
        super();
        this.mContext = context;
        this.data = new ArrayList<MessageGroupPerson>();
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    @Override
    public MessageGroupPerson getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<MessageGroupPerson> getData() {
        return data;
    }

    public void setData(List<MessageGroupPerson> data) {

        this.data.clear();
        this.data.addAll(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        try {
            MessageGroupPerson p = data.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.message_group_persons_edit_item, null);
                holder = new ViewHolder();
                holder.tbPersonName = (TextView) convertView.findViewById(R.id.msg_group_person_edit_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (!TextUtils.isEmpty(p.getEmpName()))
                holder.tbPersonName.setText(p.getEmpName() + (TextUtils.isEmpty(p.getEmpPost()) ? "" : ("【" + p.getEmpPost() + "】")));
        } catch (Exception ex) {

        }
        return convertView;
    }

    class ViewHolder {
        TextView tbPersonName;
    }
}
