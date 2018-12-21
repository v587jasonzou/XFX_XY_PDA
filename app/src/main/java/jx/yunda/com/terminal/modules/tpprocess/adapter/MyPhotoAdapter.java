package jx.yunda.com.terminal.modules.tpprocess.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.List;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.widget.GestureImageView;
import jx.yunda.com.terminal.widget.ZZoomImageView;

public class MyPhotoAdapter extends PagerAdapter {
    Context context;
    int posiotion;
    List<ImageItem> list;
    String state;
    public MyPhotoAdapter(Context context,int posiotion, List<ImageItem> list,String state){
        this.context = context;
        this.posiotion = posiotion;
        this.list = list;
        this.state = state;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
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
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        ImageView imageView = new ImageView(context);
        imageView.setPadding(0,20,0,20);
//        if(state.equals("1")){
//            String base64 = list.get(position).path;
//            byte[] strbyte = Base64.decode(base64,Base64.DEFAULT);
////                    Bitmap bitmap = BitmapFactory.decodeByteArray(strbyte,0,strbyte.length);
//            Glide.with(context).load(strbyte).override(720,1000).into(imageView);
//        }else {
//            Glide.with(context).load(list.get(position).path).
//                    override(720,1000).into(imageView);
//            container.addView(imageView);
//        }
        Glide.with(context).load(list.get(position).path).
                override(720,1000)
                .fitCenter()
                .placeholder((ContextCompat.getDrawable(context, R.mipmap.image_upload_icon)))
                .error(ContextCompat.getDrawable(context,R.mipmap.image_upload_faild))
                .into(imageView);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
