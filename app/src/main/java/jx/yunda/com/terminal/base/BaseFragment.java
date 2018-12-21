package jx.yunda.com.terminal.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.List;

import butterknife.ButterKnife;
import jx.yunda.com.terminal.entity.MessageSend;
import jx.yunda.com.terminal.message.okhttp.client.manager.WebSocketManager;


/**
 * Created by Spark on 2016/7/10.
 * Github: github/SparkYuan
 */
public abstract class BaseFragment<T extends BasePresenter> extends RxFragment implements IView {
    protected T mPresenter;
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }
    View containerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (containerView == null) {//
            containerView = inflater.inflate(getContentViewId(), container, false);
        }
        ViewGroup parent = (ViewGroup) containerView.getParent();
        if (parent != null) {
            parent.removeView(containerView);
        }
        ButterKnife.bind(this,containerView);
        initPresenter();
        initSubViews(containerView);
        return containerView;
    }
    private void initPresenter() {
        if (mPresenter == null)
            mPresenter = getPresenter();
    }
    protected abstract T getPresenter();
    @Override
    public abstract ViewGroup getViewGroupRoot();

    @Override
    public abstract int getContentViewId();

    @Override
    public abstract void initSubViews(View view) ;

    @Override
    public abstract void initData();
}
