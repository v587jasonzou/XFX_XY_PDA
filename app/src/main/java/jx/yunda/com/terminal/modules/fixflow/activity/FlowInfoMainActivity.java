package jx.yunda.com.terminal.modules.fixflow.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.fixflow.FlowInfoApi;
import jx.yunda.com.terminal.modules.fixflow.entry.FlowNoBean;
import jx.yunda.com.terminal.modules.fixflow.fragment.FwhDoFragment;
import jx.yunda.com.terminal.modules.fixflow.fragment.FwhUndoFragment;
import jx.yunda.com.terminal.modules.fixflow.fragment.TpDoFragment;
import jx.yunda.com.terminal.modules.fixflow.fragment.TpUndoFragment;
import jx.yunda.com.terminal.modules.quality.frg.Equipfrg;
import jx.yunda.com.terminal.modules.quality.frg.TicketFrg;
import jx.yunda.com.terminal.modules.quality.frg.TrainFrg;
import jx.yunda.com.terminal.modules.tpprocess.TicketListActivity;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FlowInfoMainActivity extends BaseActivity {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tb_title)
    TabLayout tbTitle;
    @BindView(R.id.flcontent)
    FrameLayout flcontent;
    @BindView(R.id.downview)
    View downview;
    @Override
    protected BasePresenter getPresenter() {
        return new BasePresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_fix_flow_main;
    }

    @Override
    public void initSubViews(View view) {

    }

    public static String idx, fwhUndoNo, fwhDoNo, TpUndoNo, TpdoNo, shortName, TrainNo;
    public static String orgId="";
    FwhUndoFragment fwhUndoFragment;
    TpUndoFragment tpUndoFragment;
    FwhDoFragment fwhDoFragment;
    TpDoFragment tpDoFragment;
    PopupMenu popupMenu;
    int tabposition = 0;
    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        idx = bundle.getString("idx");
        fwhUndoNo = bundle.getString("fwhUndoNo");
        fwhDoNo = bundle.getString("fwhDoNo");
        TpUndoNo = bundle.getString("TpUndoNo");
        TpdoNo = bundle.getString("TpdoNo");
        shortName = bundle.getString("shortName");
        TrainNo = bundle.getString("TrainNo");
        if (shortName != null) {
            menuTp.setTitle(shortName + " " + TrainNo);
        }
        tbTitle.getTabAt(0).setText("范围活\n" + fwhUndoNo);
        tbTitle.getTabAt(1).setText("JT28\n" + TpUndoNo);
        tbTitle.getTabAt(2).setText("范围活\n" + fwhDoNo);
        tbTitle.getTabAt(3).setText("JT28\n" + TpdoNo);
        initFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.flcontent,fwhUndoFragment,"fwhUndoFragment")
                .commit();
        tbTitle.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabposition = tab.getPosition();
                switch (tab.getPosition()){
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flcontent,fwhUndoFragment,"fwhUndoFragment").commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flcontent,tpUndoFragment,"tpUndoFragment").commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flcontent,fwhDoFragment,"fwhDoFragment").commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flcontent,tpDoFragment,"fwhDoFragment").commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        popupMenu = new PopupMenu(this, downview);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                menus.setTitle(item.getTitle());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(downview.getWindowToken(), 0);
                }
                if(item.getTitle().equals("全部")){
                    orgId = "";
                    tbTitle.getTabAt(0).setText("范围活\n" + fwhUndoNo);
                    tbTitle.getTabAt(1).setText("JT28\n" + TpUndoNo);
                    tbTitle.getTabAt(2).setText("范围活\n" + fwhDoNo);
                    tbTitle.getTabAt(3).setText("JT28\n" + TpdoNo);
                    switch (tabposition){
                        case 0:
                            fwhUndoFragment.Refresh();
                            break;
                        case 1:
                            tpUndoFragment.Refresh();
                            break;
                        case 2:
                            fwhDoFragment.Refresh();
                            break;
                        case 3:
                            tpDoFragment.Refresh();
                            break;
                    }
                }else {
                    orgId = ""+ SysInfo.userInfo.org.getOrgid();
                    getProgressDialog().show();
                    Observable.create(new Observable.OnSubscribe<FlowNoBean>() {
                        @Override
                        public void call(final Subscriber<? super FlowNoBean> subscriber) {
                            RequestFactory.getInstance().createApi(FlowInfoApi.class)
                                    .getWorkNo(idx,orgId).enqueue(new Callback<BaseBean<FlowNoBean>>() {
                                @Override
                                public void onResponse(Call<BaseBean<FlowNoBean>> call, Response<BaseBean<FlowNoBean>> response) {
                                    if(response.body()!=null&&response.body().getRoot()!=null){
                                        subscriber.onNext(response.body().getRoot());
                                    }else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                getProgressDialog().dismiss();
                                                ToastUtil.toastShort("获取信息数量失败");
                                                switch (tabposition){
                                                    case 0:
                                                        fwhUndoFragment.Refresh();
                                                        break;
                                                    case 1:
                                                        tpUndoFragment.Refresh();
                                                        break;
                                                    case 2:
                                                        fwhDoFragment.Refresh();
                                                        break;
                                                    case 3:
                                                        tpDoFragment.Refresh();
                                                        break;
                                                }
                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onFailure(Call<BaseBean<FlowNoBean>> call, Throwable t) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            getProgressDialog().dismiss();
                                            ToastUtil.toastShort("获取信息数量失败");
                                            switch (tabposition){
                                                case 0:
                                                    fwhUndoFragment.Refresh();
                                                    break;
                                                case 1:
                                                    tpUndoFragment.Refresh();
                                                    break;
                                                case 2:
                                                    fwhDoFragment.Refresh();
                                                    break;
                                                case 3:
                                                    tpDoFragment.Refresh();
                                                    break;
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<FlowNoBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(FlowNoBean flowNoBean) {
                            getProgressDialog().dismiss();
                            if(flowNoBean!=null){
                                if(flowNoBean.getFwFinishedNo()!=null){
                                    tbTitle.getTabAt(2).setText("范围活\n" + flowNoBean.getFwFinishedNo().toString());
                                }else {
                                    tbTitle.getTabAt(2).setText("范围活\n" + 0);
                                }
                                if(flowNoBean.getFwUnfinishedNo()!=null){
                                    tbTitle.getTabAt(0).setText("范围活\n" + flowNoBean.getFwUnfinishedNo().toString());
                                }else {
                                    tbTitle.getTabAt(0).setText("范围活\n" + 0);
                                }
                                if(flowNoBean.getTpUnfinishedNo()!=null){
                                    tbTitle.getTabAt(1).setText("JT28\n" + flowNoBean.getTpUnfinishedNo().toString());
                                }else {
                                    tbTitle.getTabAt(1).setText("JT28\n" + 0);
                                }
                                if(flowNoBean.getTpFinishedNo()!=null){
                                    tbTitle.getTabAt(3).setText("JT28\n" + flowNoBean.getTpFinishedNo().toString());
                                }else {
                                    tbTitle.getTabAt(3).setText("JT28\n" + 0);
                                }
                            }
                            switch (tabposition){
                                case 0:
                                    fwhUndoFragment.Refresh();
                                    break;
                                case 1:
                                    tpUndoFragment.Refresh();
                                    break;
                                case 2:
                                    fwhDoFragment.Refresh();
                                    break;
                                case 3:
                                    tpDoFragment.Refresh();
                                    break;
                            }
                        }
                    });
                }

                popupMenu.dismiss();
                return false;
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    private void initFragment() {
        fwhUndoFragment = new FwhUndoFragment();
        tpUndoFragment = new TpUndoFragment();
        fwhDoFragment = new FwhDoFragment();
        tpDoFragment = new TpDoFragment();
    }
    MenuItem menus;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toobar跟menu
        getMenuInflater().inflate(R.menu.tp_menu, menu);
        menus= menu.findItem(R.id.tv_tprecord);
        menus.setTitle("全部");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.tv_tprecord) {
            Menu menu_more = popupMenu.getMenu();
            menu_more.clear();
            menu_more.add(Menu.NONE, Menu.FIRST + 0, 0, "全部");
            menu_more.add(Menu.NONE, Menu.FIRST + 1, 1, "本班组");
            popupMenu.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        orgId="";
    }
}
