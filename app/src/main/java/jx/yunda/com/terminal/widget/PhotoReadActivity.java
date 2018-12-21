package jx.yunda.com.terminal.widget;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.ArrayList;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.tpprocess.adapter.MyPhotoAdapter;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PhotoReadActivity extends AppCompatActivity {
    ViewPager viewPager;
    Toolbar menu_tp;
    ImageView iv_delete;
    ArrayList<ImageItem> images = new ArrayList<>();
    int ppsition;
    MyPhotoAdapter adapter;
    String state = "0";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ppsition = bundle.getInt("position",0);
        state = bundle.getString("state");
        images = (ArrayList<ImageItem>)bundle.getSerializable("images");
//        if("1".equals(state)){
//            for(int i =0 ;i<images.size();i++){
//                images.get(i).path = Utils.getImageBase64(i+"");
//            }
//        }
        adapter = new MyPhotoAdapter(this,ppsition,images,state);
        viewPager = (ViewPager) findViewById(R.id.iv_veiewPger);
        menu_tp = (Toolbar) findViewById(R.id.menu_tp);
        menu_tp.setTitle("（"+(ppsition+1)+"/"+images.size()+"）");
        iv_delete = (ImageView)findViewById(R.id.iv_delete);
        if(!"0".equals(state)){
            iv_delete.setVisibility(View.GONE);
        }
        if("order".equals(state)){
            iv_delete.setVisibility(View.VISIBLE);
        }
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(ppsition);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ppsition = position;
                menu_tp.setTitle("（"+(ppsition+1)+"/"+images.size()+"）");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 new AlertDialog.Builder(PhotoReadActivity.this).setTitle("提示")
                         .setMessage("确定删除当前照片？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         if(state.equals("order")){
                             Observable.create(new Observable.OnSubscribe<String>() {
                                 @Override
                                 public void call(final Subscriber<? super String> subscriber) {
                                     RequestFactory.getInstance().createApi(TicketApi.class).DeleteImages(""
                                             ,images.get(ppsition).name).enqueue(new Callback<BaseBean>() {
                                         @Override
                                         public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                                             if(response!=null&&response.body()!=null){
                                                 if(response.body().isSuccess()){
                                                     subscriber.onNext("success");
                                                 }else {
                                                     subscriber.onNext("删除照片失败");
                                                 }
                                             }else {
                                                 subscriber.onNext("删除照片失败");
                                             }
                                         }

                                         @Override
                                         public void onFailure(Call<BaseBean> call, Throwable t) {
                                             subscriber.onNext("删除照片失败");
                                         }
                                     });
                                 }
                             }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                                     .subscribe(new Observer<String>() {
                                         @Override
                                         public void onCompleted() {

                                         }

                                         @Override
                                         public void onError(Throwable e) {

                                         }

                                         @Override
                                         public void onNext(String aBoolean) {
                                             if(aBoolean.equals("success")){
                                                 if(images.size()>1){
                                                     images.remove(ppsition);
                                                     adapter.notifyDataSetChanged();
                                                     menu_tp.setTitle("（"+(ppsition+1)+"/"+images.size()+"）");
                                                 }else {
                                                     images.remove(ppsition);
                                                     Intent intent1 = new Intent();
                                                     intent1.putExtra("images",images);
                                                     setResult(ImagePicker.RESULT_CODE_ITEMS,intent1);
                                                     finish();
                                                 }
                                             }else {
                                                 ToastUtil.toastShort(aBoolean);
                                             }
                                         }
                                     });

                         }else {
                             if(images.size()>1){
                                 images.remove(ppsition);
                                 adapter.notifyDataSetChanged();
                                 menu_tp.setTitle("（"+(ppsition+1)+"/"+images.size()+"）");
                             }else {
                                 images.remove(ppsition);
                                 Intent intent1 = new Intent();
                                 intent1.putExtra("images",images);
                                 setResult(ImagePicker.RESULT_CODE_ITEMS,intent1);
                                 finish();
                             }
                         }

                     }
                 }).setNegativeButton("取消",null).show();
            }
        });
        setSupportActionBar(menu_tp);
        menu_tp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("images",images);
                setResult(ImagePicker.RESULT_CODE_ITEMS,intent1);
                finish();
            }
        });
    }
}
