package jx.yunda.com.terminal.modules.FJ_ticket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.entity.FileBaseBean;
import jx.yunda.com.terminal.modules.FJ_ticket.presenter.IRecheckInfo;
import jx.yunda.com.terminal.modules.FJ_ticket.presenter.RechecjInfoPresenter;
import jx.yunda.com.terminal.modules.tpprocess.ImagesAdapter;
import jx.yunda.com.terminal.modules.tpprocess.TicketInfoActivity;
import jx.yunda.com.terminal.modules.tpprocess.TicketInfoReadActivity;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.PhotoReadActivity;
import jx.yunda.com.terminal.widget.SodukuGridView;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;

public class RecheckInfoActivity extends BaseActivity<RechecjInfoPresenter> implements IRecheckInfo {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvTicketName)
    TextView tvTicketName;
    @BindView(R.id.tvBJName)
    TextView tvBJName;
    @BindView(R.id.tvZYName)
    TextView tvZYName;
    @BindView(R.id.etGZXXInfo)
    TextView etGZXXInfo;
    @BindView(R.id.gvImages)
    SodukuGridView gvImages;
    List<String> images = new ArrayList<>();
    ImagesAdapter adapter;
    @Override
    protected RechecjInfoPresenter getPresenter() {
        return new RechecjInfoPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_recheck_info;
    }

    @Override
    public void initSubViews(View view) {

    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String TrainName = bundle.getString("TrainName");
            String idx = bundle.getString("idx");
            String lb = bundle.getString("lb");
            String fixPlaceFullName = bundle.getString("fixPlaceFullName");
            String blzt = bundle.getString("blzt");
            if(TrainName!=null){
                tvTicketName.setText(TrainName);
            }
            if(lb!=null){
                tvZYName.setText(lb);
            }
            if(fixPlaceFullName!=null){
                tvBJName.setText(fixPlaceFullName);
            }
            if(blzt!=null){
                etGZXXInfo.setText(blzt);
            }
            if(idx!=null){
                mPresenter.getImages(idx);
            }
        }
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new ImagesAdapter(this,images);
        gvImages.setAdapter(adapter);
        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle1 = new Bundle();
                Intent intent = new Intent(RecheckInfoActivity.this, PhotoReadActivity.class);
                bundle1.putInt("position", position);
                bundle1.putString("state","2");
                ArrayList<ImageItem> imageItems = new ArrayList<>();
                for(String str:images){
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = str;
                    imageItems.add(imageItem);
                }
                bundle1.putSerializable("images", imageItems);
                intent.putExtras(bundle1);
                startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
            }
        });
    }

    @Override
    public void GetImagesSuccess(List<FileBaseBean> images) {
        if(images.size()>0){
            this.images.clear();
            for(FileBaseBean str:images){
                String strs = Utils.getImageUrl(str.getFileUrl());
                this.images.add(strs);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void GetImagesFaild(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
