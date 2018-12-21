package jx.yunda.com.terminal.modules.tpprocess;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.utils.SizeConvert;

public class ImagesAdapter extends BaseAdapter {
    Context mContext;
    List<String> list;
    ViewHolder holder;
    String state = "0";
    public ImagesAdapter(Context context , List<String> list){
        mContext = context;
        this.list = list;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public int getCount() {
        if(list!=null&&list.size()>0){
            return list.size();
        }else {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.images_item,null);
            holder = new ViewHolder();
            holder.ivImages = (ImageView)convertView.findViewById(R.id.ivImages);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        if("0".equals(list.get(position))){
            Glide.with(mContext).load(R.mipmap.addimages).into(holder.ivImages);
        }else {
            if(list!=null&&list.size()>0){
                Glide.with(mContext).load(list.get(position)).
                        override(SizeConvert.dip2px(100), SizeConvert.dip2px(100))
                        .centerCrop()
                        .placeholder((ContextCompat.getDrawable(mContext,R.mipmap.image_upload_icon)))
                        .error(ContextCompat.getDrawable(mContext,R.mipmap.image_upload_faild))
                        .into(holder.ivImages);
            }
    }

        return convertView;
    }
    class ViewHolder{
        ImageView ivImages;
    }
}
