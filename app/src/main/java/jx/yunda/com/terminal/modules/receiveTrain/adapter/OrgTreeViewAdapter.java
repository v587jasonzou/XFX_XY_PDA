package jx.yunda.com.terminal.modules.receiveTrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.receiveTrain.model.OrgEntity;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultTreeBean;

public class OrgTreeViewAdapter extends BaseAdapter {
    Context mContext;
    List<OrgEntity> list;
    ViewHoleder holeder;
    OnImageClickLisnter onImageClickLisnter;
   public interface OnImageClickLisnter{
        void OnImageClick(int position, String Pid);
        void OnTextClick(int position);
    }
    public void setOnImageClickLisnter(OnImageClickLisnter onImageClickLisnter){
        this.onImageClickLisnter = onImageClickLisnter;
    }
    public OrgTreeViewAdapter(Context context, List<OrgEntity> list){
        mContext = context;
        this.list = list;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
//        if(convertView==null){
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tree_list,null);
//            holeder = new ViewHoleder();
//            holeder.ivArrow = (ImageView)convertView.findViewById(R.id.ivArrow);
//            holeder.ivArrow.setTag(position);
//            holeder.tvBlank = (TextView)convertView.findViewById(R.id.tvBlank);
//            holeder.tvFault = (TextView)convertView.findViewById(R.id.tvFault);
//            convertView.setTag(holeder);
//        }else {
//            holeder = (ViewHoleder)convertView.getTag();
//        }
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tree_list,null);
//        holeder = new ViewHoleder();
        ImageView ivArrow = (ImageView)convertView.findViewById(R.id.ivArrow);
        LinearLayout llImage = (LinearLayout)convertView.findViewById(R.id.llImage);
        llImage.setTag(position);
        boolean isOpern = list.get(position).isOpen();
        boolean isleaf = list.get(position).isLeaf();
        String blank = "";
        for(int i=0;i<list.get(position).getMyLevel();i++){
            blank = blank+"     ";
        }
        TextView tvBlank = (TextView)convertView.findViewById(R.id.tvBlank);
        tvBlank.setText(blank);
        TextView tvFault = (TextView)convertView.findViewById(R.id.tvFault);
        tvFault.setText(list.get(position).getName());
        if(isleaf){
            llImage.setEnabled(false);
            llImage.setVisibility(View.INVISIBLE);
        }else {
            llImage.setEnabled(true);
            llImage.setVisibility(View.VISIBLE);
            if(isOpern){
                ivArrow.setImageResource(R.drawable.item_open);
            }else {
                ivArrow.setImageResource(R.drawable.item_close);
            }
        }
        tvFault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageClickLisnter.OnTextClick(position);
            }
        });
        llImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageClickLisnter.OnImageClick((Integer)v.getTag(),list.get((Integer)v.getTag()).getId());
            }
        });
//        holeder.tvFault.setText(list.get(position).getName());
//        holeder.ivArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onImageClickLisnter.OnImageClick((Integer)v.getTag(),list.get((Integer)v.getTag()).getId());
//            }
//        });
        return convertView;
    }
    class ViewHoleder{
        ImageView ivArrow;
        TextView tvFault,tvBlank;
        LinearLayout llImage;
    }
}
