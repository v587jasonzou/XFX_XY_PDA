package jx.yunda.com.terminal.modules.ORGBookNew.act;

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
import jx.yunda.com.terminal.modules.ORGBookNew.frag.AssingFragmentNew;
import jx.yunda.com.terminal.modules.ORGBookNew.frag.BookNewFragmentNew;
import jx.yunda.com.terminal.modules.ORGBookNew.frag.TpWorkNewFragmentNew;
import jx.yunda.com.terminal.widget.MyTablayout;


public class ORGBookActivityNew extends BaseActivity {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tabBook)
    MyTablayout tabBook;
    @BindView(R.id.fmContent)
    FrameLayout fmContent;
    BookNewFragmentNew bookFragmentNew;
    AssingFragmentNew assingFragmentNew;
    TpWorkNewFragmentNew tpFragment;
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
        return R.layout.activity_org_book;
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
        getSupportFragmentManager().beginTransaction().add(R.id.fmContent, bookFragmentNew,"book")
                .commit();
        tabBook.setText("点名","提票活","范围活");
        tabBook.setOnTabSelectListner(new MyTablayout.OnTabSelectListner() {
            @Override
            public void OnTabClick(int position) {
                if(position==0){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fmContent, bookFragmentNew,"book").commit();
                }else if(position==2) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fmContent, assingFragmentNew,"assing").commit();
                }else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fmContent,tpFragment,"tipiao").commit();
                }
            }
        });
    }

    private void initFragment() {
        bookFragmentNew = new BookNewFragmentNew();
        assingFragmentNew = new AssingFragmentNew();
        tpFragment = new TpWorkNewFragmentNew();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
