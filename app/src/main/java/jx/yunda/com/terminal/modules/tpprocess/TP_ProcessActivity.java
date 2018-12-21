package jx.yunda.com.terminal.modules.tpprocess;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.base.JXApplication;
import jx.yunda.com.terminal.modules.quality.model.QualityTrainBean;
import jx.yunda.com.terminal.modules.tpprocess.RealmDb.TicketSubmitDao;
import jx.yunda.com.terminal.modules.tpprocess.adapter.TicketTypeAdapter;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTrainBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTypeBean;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.NetWorkUtils;
import jx.yunda.com.terminal.utils.ToastUtil;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TP_ProcessActivity extends BaseActivity<MakeTicketPresenter> implements View.OnClickListener,IMakeTicket
                                                                ,AdapterView.OnItemClickListener{

    @BindView(R.id.menu_tp)
    Toolbar menu_tp;
    @BindView(R.id.lvType)
    ListView lvType;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.gvCar)
    GridView gvCar;
    @BindView(R.id.btNext)
    Button btNext;
    @BindView(R.id.tvMesaageNo)
    TextView tvMesaageNo;
    @BindView(R.id.sv_ticketTrain)
    SmartRefreshLayout sv_ticketTrain;
    TicketAdapter adapter;
    TicketTypeAdapter typeAdapter;
    int typePosition = 0;
    int trainPosition;
    List<TicketTrainBean> mList = new ArrayList<>();
    List<TicketTrainBean> mListSearch = new ArrayList<>();
    List<TicketTypeBean> typeList = new ArrayList<>();
    List<RadioButton> radioButtons = new ArrayList<>();
    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_tp__process;
    }

    @Override
    public void initSubViews(View view) {

    }


    @Override
    public void initData() {
        //设置toolbar
        adapter = new TicketAdapter(this,mList);
        typeAdapter = new TicketTypeAdapter(this,typeList);
        gvCar.setAdapter(adapter);
        lvType.setAdapter(typeAdapter);
        gvCar.setOnItemClickListener(this);
//        gvCar.getBackground().setAlpha(100);
        setSupportActionBar(menu_tp);
        menu_tp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPresenter.getTicketTrains();
        mPresenter.getTicketType();
        btNext.setOnClickListener(this);
        tvMesaageNo.setOnClickListener(this);
        sv_ticketTrain.setRefreshHeader(new ClassicsHeader(this));
        sv_ticketTrain.setRefreshFooter(new ClassicsFooter(this));
        sv_ticketTrain.setNoMoreData(true);
        sv_ticketTrain.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.getTicketTrains();
            }
        });
        sv_ticketTrain.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.getTicketTrains();
            }
        });
        lvType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(typeList.size()>0){
                    typePosition = position;
                    for(int i =0;i<typeList.size();i++){
                        if(i==position){
                            typeList.get(i).setState(1);
                        }else {
                            typeList.get(i).setState(0);
                        }
                    }
                    if(typeList.get(typePosition).getUnSubmit()==0){
                        tvMesaageNo.setVisibility(View.GONE);
                    }else {
                        tvMesaageNo.setVisibility(View.VISIBLE);
                        tvMesaageNo.setText(typeList.get(typePosition).getUnSubmit()+"条保存的提票");
                    }
                    typeAdapter.notifyDataSetChanged();
                }
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btNext.setBackground(ContextCompat.getDrawable(TP_ProcessActivity.this,R.drawable.button_selector_gray));
                btNext.setEnabled(false);
                s = s.toString().toUpperCase();
                if (mListSearch.size() > 0) {
                    if ("车型、车号".equals(s.toString()) || "".equals(s.toString())) {
                        mList.clear();
                        for (TicketTrainBean bean : mListSearch) {
                            if (bean.getState() == 1) {
                                bean.setState(0);
                            }
                        }
                        mList.addAll(mListSearch);
                        adapter.notifyDataSetChanged();
                    } else {
                        mList.clear();
                        for (TicketTrainBean bean : mListSearch) {
                            if (bean.getState() == 1) {
                                bean.setState(0);
                            }
                        }
                        for (int i = 0; i < mListSearch.size(); i++) {
                            if (mListSearch.get(i).getTrainNo().contains(s.toString()) ||
                                    mListSearch.get(i).getTrainTypeShortName().contains(s.toString())) {
                                mList.add(mListSearch.get(i));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toobar跟menu
        getMenuInflater().inflate(R.menu.tp_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.tv_tprecord) {
            Bundle bundle = new Bundle();
            bundle.putString("type",typeList.get(typePosition).getDictname());
            ActivityUtil.startActivityWithDelayed(this, TicketListActivity.class,bundle);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btNext:
                Bundle bundle = new Bundle();
                bundle.putString("type",typeList.get(typePosition).getDictname());
                bundle.putString("ShortName",mList.get(trainPosition).getTrainTypeShortName());
                bundle.putString("trainTypeIDX",mList.get(trainPosition).getTrainTypeIDX());
                bundle.putString("TrainNo",mList.get(trainPosition).getTrainNo());
                bundle.putString("typeId",typeList.get(typePosition).getDictid());
                bundle.putString("state","0");
                bundle.putString("filter",typeList.get(typePosition).getFilter2());
                ActivityUtil.startActivityResultWithDelayed(this, TicketInfoActivity.class,bundle,200);
                break;
            case R.id.tvMesaageNo:
                Bundle bundle1 = new Bundle();
                bundle1.putInt("state",2);
                bundle1.putString("type",typeList.get(typePosition).getDictname());
                ActivityUtil.startActivityWithDelayed(this,TicketListActivity.class,bundle1);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==300){
            if(NetWorkUtils.isWifiConnected(this)){
                mPresenter.getTicketTrains();
                mPresenter.getTicketType();
            }else {

            }

        }
    }

    @Override
    protected MakeTicketPresenter getPresenter() {
        return new MakeTicketPresenter(this);
    }

    @Override
    public void onLoadTicktSuccess(List<TicketTrainBean> ticketInfoBeans) {
        sv_ticketTrain.finishRefresh();
        sv_ticketTrain.finishLoadMore();
        if(ticketInfoBeans!=null&&ticketInfoBeans.size()>0){
            mList.clear();
            mListSearch.clear();
            mListSearch.addAll(ticketInfoBeans);
            mList.addAll(ticketInfoBeans);
            adapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this,"无在修机车数据",Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("ResourceType")
    @Override
    public void onLoadTicktTypeSuccess(List<TicketTypeBean> types) {
        if(types!=null&&types.size()>0){
            setTrainList(types);
        }
    }

    private void setTrainList(List<TicketTypeBean> types){
        typeList.clear();
        typeList.addAll(types);
        typeList.get(0).setState(1);
        setUnUploadTicket();
    }
    private void setUnUploadTicket(){
        Observable.create(new Observable.OnSubscribe<List<TicketTypeBean>>() {
            @Override
            public void call(Subscriber<? super List<TicketTypeBean>> subscriber) {
                RealmResults<TicketSubmitDao> daos = Realm.getDefaultInstance().where(TicketSubmitDao.class)
                        .equalTo("operatorid", SysInfo.userInfo.emp.getEmpid()).findAll();
                for(int i = 0;i<typeList.size();i++){
                    int temp = 0;
                    for(int j = 0;j<daos.size();j++){
                        if(typeList.get(i).getDictname().equals(daos.get(j).getType())){
                            temp++;
                        }
                    }
                    typeList.get(i).setUnSubmit(temp);
                }
                subscriber.onNext(typeList);
            }
        }).subscribeOn(Schedulers.from(JXApplication.getExecutor())).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TicketTypeBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<TicketTypeBean> integer) {
                        if(typeList.get(typePosition).getUnSubmit()==0){
                            tvMesaageNo.setVisibility(View.GONE);
                        }else {
                            tvMesaageNo.setVisibility(View.VISIBLE);
                            tvMesaageNo.setText(typeList.get(typePosition).getUnSubmit()+"条保存的提票");
                        }
                        typeAdapter.notifyDataSetChanged();
                    }
                });
    }
    @Override
    public void onLoadTicktFail(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        sv_ticketTrain.finishRefresh();
        sv_ticketTrain.finishLoadMore();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(mList.size()>0){
            for(int i =0;i<mList.size();i++){
                if(i==position){
                    mList.get(i).setState(1);
                }else {
                    mList.get(i).setState(0);
                }
            }
            trainPosition = position;
        }
        adapter.notifyDataSetChanged();
        btNext.setBackground(ContextCompat.getDrawable(this,R.drawable.button_selector));
        btNext.setEnabled(true);
    }
}
