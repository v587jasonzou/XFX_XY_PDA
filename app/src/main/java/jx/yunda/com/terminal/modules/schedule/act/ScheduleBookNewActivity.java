package jx.yunda.com.terminal.modules.schedule.act;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.ORGBook.frag.AssingFragment;
import jx.yunda.com.terminal.modules.ORGBook.frag.BookFragment;
import jx.yunda.com.terminal.modules.ORGBook.frag.TpWorkFragment;
import jx.yunda.com.terminal.modules.schedule.frg.ScheduleAssingFragment;
import jx.yunda.com.terminal.modules.schedule.frg.ScheduleBookFragment;
import jx.yunda.com.terminal.modules.schedule.frg.ScheduleTpWorkFragment;
import jx.yunda.com.terminal.widget.MyTablayout;


public class ScheduleBookNewActivity extends BaseActivity {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tabBook)
    MyTablayout tabBook;
    @BindView(R.id.fmContent)
    FrameLayout fmContent;
    ScheduleBookFragment bookFragment;
    ScheduleAssingFragment assingFragment;
    ScheduleTpWorkFragment tpFragment;
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
        return R.layout.activity_schedule_book_new;
    }

    @Override
    public void initSubViews(View view) {

    }

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
        getSupportFragmentManager().beginTransaction().add(R.id.fmContent,bookFragment,"book")
                .commit();
        tabBook.setText(getString(R.string.book_title),getString(R.string.faultt_ticket_plan),getString(R.string.fw_plan));
        tabBook.setOnTabSelectListner(new MyTablayout.OnTabSelectListner() {
            @Override
            public void OnTabClick(int position) {
                if(position==0){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fmContent,bookFragment,"book").commit();
                }else if(position==2) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fmContent,assingFragment,"assing").commit();
                }else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fmContent,tpFragment,"tipiao").commit();
                }
            }
        });
    }

    private void initFragment() {
        bookFragment = new ScheduleBookFragment();
        assingFragment = new ScheduleAssingFragment();
        tpFragment = new ScheduleTpWorkFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
