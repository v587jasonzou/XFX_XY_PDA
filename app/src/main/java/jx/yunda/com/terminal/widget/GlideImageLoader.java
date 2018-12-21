package jx.yunda.com.terminal.widget;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.loader.ImageLoader;

import jx.yunda.com.terminal.R;

public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)
                .load(path)
                .thumbnail(0.2f)
                .override(width,height)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)
                .load(path)
                .thumbnail(0.2f)
                .override(width,height)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
