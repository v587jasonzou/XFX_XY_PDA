package jx.yunda.com.terminal.modules.quality.act;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.quality.frg.Equipfrg;
import jx.yunda.com.terminal.modules.quality.frg.TicketFrg;
import jx.yunda.com.terminal.modules.quality.frg.TrainFrg;
import jx.yunda.com.terminal.modules.quality.presenter.IQuality;
import jx.yunda.com.terminal.modules.quality.presenter.QualityPresenter;
import jx.yunda.com.terminal.modules.tpprocess.TicketListActivity;
import jx.yunda.com.terminal.utils.ActivityUtil;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class QualityActivity extends BaseActivity<QualityPresenter> implements IQuality {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tb_title)
    TabLayout tbTitle;
    @BindView(R.id.tv_TrainNo)
    TextView tvTrainNo;
    @BindView(R.id.tv_EquipNo)
    TextView tvEquipNo;
    @BindView(R.id.tv_TicketNo)
    TextView tvTicketNo;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.tvData)
    TextView tvData;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.frContent)
    FrameLayout frContent;
    boolean isTrain = false;
    boolean isEquip = false;
    boolean isTicket = false;
    @Override
    protected QualityPresenter getPresenter() {
        return new QualityPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_quality;
    }

    @Override
    public void initSubViews(View view) {

    }
//    FragmentTransaction fg;
    TrainFrg trainFrg;
    Equipfrg equipfrg;
    TicketFrg ticketFrg;
    @Override
    public void initData() {
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frContent,trainFrg,"train")
        .commit();
        tbTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    tvTrainNo.setBackground(ContextCompat.getDrawable(QualityActivity.this,R.mipmap.circle_blue));
                    tvEquipNo.setBackground(ContextCompat.getDrawable(QualityActivity.this,R.mipmap.circle_gray));
                    tvTicketNo.setBackground(ContextCompat.getDrawable(QualityActivity.this,R.mipmap.circle_gray));
                    getSupportFragmentManager().beginTransaction().replace(R.id.frContent,trainFrg,"train").commit();
                }else {
                    tvTrainNo.setBackground(ContextCompat.getDrawable(QualityActivity.this,R.mipmap.circle_gray));
                    tvEquipNo.setBackground(ContextCompat.getDrawable(QualityActivity.this,R.mipmap.circle_gray));
                    tvTicketNo.setBackground(ContextCompat.getDrawable(QualityActivity.this,R.mipmap.circle_blue));
                    getSupportFragmentManager().beginTransaction().replace(R.id.frContent,ticketFrg,"ticket").commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setData();
    }
    public void swichframent(int type){
        switch (type){
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.frContent,trainFrg,"train").commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.frContent,equipfrg,"equip").commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.frContent,ticketFrg,"ticket").commit();
                break;
        }
    }

    private void initFragment() {
        trainFrg = new TrainFrg();
        equipfrg = new Equipfrg();
        ticketFrg = new TicketFrg();
    }

    public void setData(){
        getProgressDialog().show();
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                isEquip = false;
                isTicket = false;
                isTrain = false;
                mPresenter.getTrainList(SysInfo.userInfo.emp.getOperatorid() + "", "");
                Map<String,Object> map = new HashMap<>();
                mPresenter.getPlans(0+"",1000+"","2", JSON.toJSONString(map), SysInfo.userInfo.emp.getOperatorid()+"","1");
                Map<String, Object> map1 = new HashMap<>();
                mPresenter.getTIckets(JSON.toJSONString(map),0+"",1000+"","3");
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }
    @Override
    public void getTrainQualitySuccess(int size) {
        if(size>0){
            tvTrainNo.setVisibility(View.VISIBLE);
            tvTrainNo.setText(size+"");
        }else {
            tvTrainNo.setVisibility(View.GONE);
        }
        isTrain = true;
        if(isTrain&&isEquip&&isTicket){
            getProgressDialog().dismiss();
        }
    }

    @Override
    public void getTrainQualityFaild(String msg) {
        isTrain = true;
        if(isTrain&&isEquip&&isTicket){
            getProgressDialog().dismiss();
        }
    }

    @Override
    public void getEquipQualitySuccess(int size) {
        isEquip = true;
        if(size>0){
            tvEquipNo.setVisibility(View.VISIBLE);
            tvEquipNo.setText(size+"");
        }else {
            tvEquipNo.setVisibility(View.GONE);
        }
        if(isTrain&&isEquip&&isTicket){
            getProgressDialog().dismiss();
        }
    }

    @Override
    public void getEquipQualityFaild(String msg) {
        isEquip = true;
        if(isTrain&&isEquip&&isTicket){
            getProgressDialog().dismiss();
        }
    }

    @Override
    public void getTicketQualitySuccess(int size) {
        isTicket = true;
        if(size>0){
            tvTicketNo.setVisibility(View.VISIBLE);
            tvTicketNo.setText(size+"");
        }else {
            tvTicketNo.setVisibility(View.GONE);
        }
        if(isTrain&&isEquip&&isTicket){
            getProgressDialog().dismiss();
        }
    }

    @Override
    public void getTicketQualityFaild(String msg) {
        isTicket = true;
        if(isTrain&&isEquip&&isTicket){
            getProgressDialog().dismiss();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200){
            setData();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toobar跟menu
        getMenuInflater().inflate(R.menu.menu_quality, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.iv_wenhao) {
            new AlertDialog.Builder(this).setTitle("操作提示！")
                    .setMessage("向左滑动质检项，划出操作按钮后进行质检操作！")
                    .setPositiveButton("确定",null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
