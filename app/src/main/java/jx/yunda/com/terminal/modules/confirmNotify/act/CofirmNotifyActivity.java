package jx.yunda.com.terminal.modules.confirmNotify.act;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.base.baseDictData.api.BaseDictApi;
import jx.yunda.com.terminal.base.baseDictData.model.DicDataItem;
import jx.yunda.com.terminal.base.baseDictData.presenter.BaseDictPresenter;
import jx.yunda.com.terminal.modules.confirmNotify.adapter.NotifyConfiemAdapter;
import jx.yunda.com.terminal.modules.confirmNotify.entity.NotifyDetailBean;
import jx.yunda.com.terminal.modules.confirmNotify.entity.NotifyListBean;
import jx.yunda.com.terminal.modules.confirmNotify.presenter.ConfirmNotifyPresenter;
import jx.yunda.com.terminal.modules.confirmNotify.presenter.IConfirmNotify;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.utils.ToastUtil;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CofirmNotifyActivity extends BaseActivity<ConfirmNotifyPresenter> implements IConfirmNotify {
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvOrgName)
    TextView tvOrgName;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.lvNotifyList)
    ListView lvNotifyList;
    List<NotifyDetailBean> mList = new ArrayList<>();
    NotifyConfiemAdapter adapter;
    BaseDictPresenter baseDictPresenter;
    List<DicDataItem> startDataitems;
    List<DicDataItem> endDataitems;
    int EquipPosition;
    @Override
    protected ConfirmNotifyPresenter getPresenter() {
        return new ConfirmNotifyPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_confirm_notify_main;
    }

    @Override
    public void initSubViews(View view) {

    }
    NotifyListBean beans;
    String idx;
    @Override
    public void initData() {
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        baseDictPresenter = new BaseDictPresenter(this);
        Bundle bundle = getIntent().getExtras();
        tvOrgName.setText(SysInfo.userInfo.org.getOrgname());
        if(bundle!=null){
            beans = (NotifyListBean)bundle.getSerializable("notify");
            if(beans == null){
                 idx = bundle.getString("idx");
            }else {
                if(beans.getNoticeTime()!=null){
                    tvTime.setText(beans.getNoticeTime());
                }else {
                    if(beans.getCreateTime()!=null){
                        tvTime.setText(beans.getCreateTime());
                    }
                }
            }
            adapter = new NotifyConfiemAdapter(this,mList);
            lvNotifyList.setAdapter(adapter);
            adapter.setOnConfirmClickLisnter(new NotifyConfiemAdapter.OnConfirmClickLisnter() {
                @Override
                public void OnConfirmClick(int position) {
                    getProgressDialog().show();
                    EquipPosition = position;
                    NotifyDetailBean bean = mList.get(position);
                    Map<String,Object> map = new HashMap<>();
                    map.put("idx",bean.getIdx());
                    map.put("homePositionId",bean.getHomePositionId());
                    map.put("homePosition",bean.getHomePosition());
                    map.put("homePositionConfig",bean.getHomePositionConfig());
                    map.put("destinationId",bean.getDestinationId());
                    map.put("destination",bean.getDestination());
                    map.put("destinationConfig",bean.getDestinationConfig());
                    mPresenter.UploadLocation(JSON.toJSONString(map));
                }
            });
            RequestFactory.getInstance().createApi(BaseDictApi.class).queryDictList("HOME_POSITION_DICT")
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            RequestFactory.getInstance().createApi(BaseDictApi.class).queryDictList("DESTINATION_DICT")
                                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<String>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            getProgressDialog().show();
                                            if(beans==null){
                                                mPresenter.getNotyFyInfo(0,100,idx);
                                            }else {
                                                mPresenter.getNotyFyInfo(0,100,beans.getIdx());
                                            }
                                        }

                                        @Override
                                        public void onNext(String s) {
                                            BaseBean<List<DicDataItem>> bean = JSON.parseObject(s, new TypeReference<BaseBean<List<DicDataItem>>>() {
                                            });
                                            if(bean!=null&&bean.getRoot()!=null){
                                                endDataitems = bean.getRoot();
                                                adapter.setLocation(null,endDataitems);
                                            }
                                            getProgressDialog().show();
                                            if(beans==null){
                                                mPresenter.getNotyFyInfo(0,100,idx);
                                            }else {
                                                mPresenter.getNotyFyInfo(0,100,beans.getIdx());
                                            }

                                        }
                                    });
                        }

                        @Override
                        public void onNext(String s) {
                            BaseBean<List<DicDataItem>> bean = JSON.parseObject(s, new TypeReference<BaseBean<List<DicDataItem>>>() {
                            });
                            if(bean!=null&&bean.getRoot()!=null){
                                startDataitems = bean.getRoot();
                                adapter.setLocation(startDataitems,null);
                            }
                            RequestFactory.getInstance().createApi(BaseDictApi.class).queryDictList("DESTINATION_DICT")
                                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<String>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            getProgressDialog().show();
                                            if(beans==null){
                                                mPresenter.getNotyFyInfo(0,100,idx);
                                            }else {
                                                mPresenter.getNotyFyInfo(0,100,beans.getIdx());
                                            }
                                        }

                                        @Override
                                        public void onNext(String s) {
                                            BaseBean<List<DicDataItem>> bean = JSON.parseObject(s, new TypeReference<BaseBean<List<DicDataItem>>>() {
                                            });
                                            if(bean!=null&&bean.getRoot()!=null){
                                                endDataitems = bean.getRoot();
                                                adapter.setLocation(null,endDataitems);
                                            }
                                            getProgressDialog().show();
                                            if(beans==null){
                                                mPresenter.getNotyFyInfo(0,100,idx);
                                            }else {
                                                mPresenter.getNotyFyInfo(0,100,beans.getIdx());
                                            }

                                        }
                                    });
                        }
                    });
        }

    }

    @Override
    public void GetNotifySuccess(List<NotifyDetailBean> list) {
        getProgressDialog().dismiss();
        mList.clear();
        if(list!=null&&list.size()>0){
            mList.addAll(list);
            if(beans==null){
                tvTime.setText(list.get(0).getCreateTime());
            }
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void GetNotifyFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void ConfirmSuccess() {
        getProgressDialog().dismiss();
        ToastUtil.toastShort("提交成功");
        if(beans!=null&&beans.getIdx()!=null){
            mPresenter.getNotyFyInfo(0,100,beans.getIdx());
        }else {
            if(idx!=null){
                mPresenter.getNotyFyInfo(0,100,idx);
            }
        }
    }

    @Override
    public void ConfirmFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    public void OnConfirmLocationSuccess() {
        mPresenter.ConfirmNotify(mList.get(EquipPosition).getIdx(),SysInfo.userInfo.emp.getEmpid(),
                SysInfo.userInfo.emp.getEmpname());
    }

    @Override
    public void OnConfirmLoacationFaild(String msg) {
        getProgressDialog().dismiss();
        ToastUtil.toastShort(msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
