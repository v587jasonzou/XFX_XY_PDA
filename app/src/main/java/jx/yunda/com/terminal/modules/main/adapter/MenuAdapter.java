package jx.yunda.com.terminal.modules.main.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import jx.yunda.com.terminal.modules.main.model.PurviewMenu;
import jx.yunda.com.terminal.modules.main.model.SecondFuncBean;
import jx.yunda.com.terminal.modules.main.model.TodoJob;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.base.JXApplication;
import jx.yunda.com.terminal.utils.ActivityUtil;

public class MenuAdapter extends BaseRecyclerAdapter<SecondFuncBean, MenuAdapter.MenuViewHolder> {
    protected int getLayoutId() {
        return R.layout.main_menu_grid_item;
    }

    @Override
    protected MenuViewHolder createViewHolder(View view) {
        return new MenuViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        //如果是title就占据2个单元格(重点)
        GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (getData().get(position).getFirst()) {
                    return 3;
                }
                return 1;
            }
        });
    }

    public static class MenuViewHolder extends BaseRecyclerAdapter.BaseViewHolder<SecondFuncBean> {


        @BindView(R.id.menu_item)
        LinearLayout menuItem;
        @BindView(R.id.menu_icon)
        ImageView menuIcon;
        @BindView(R.id.menu_name)
        TextView menuName;
        @BindView(R.id.job_count)
        TextView jobCount;
        @BindView(R.id.flcontent)
        FrameLayout flcontent;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.llContent)
        LinearLayout llContent;
        @BindView(R.id.tvUndoNo)
        TextView tvUndoNo;
        SecondFuncBean menu;
        private int clickFlag = 0;

        public MenuViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(SecondFuncBean m) {
            this.menu = m;
            String tvTitleStr = TextUtils.isEmpty(m.getFuncaction()) ? m.getFuncname() : m.getFuncaction();
            if (m.getFirst()) {
                flcontent.setVisibility(View.GONE);
                llContent.setVisibility(View.VISIBLE);
                tvTitle.setText(tvTitleStr);
            } else {
                flcontent.setVisibility(View.VISIBLE);
                llContent.setVisibility(View.GONE);
                Glide.with(JXApplication.getContext())
                        .load(m.getMenuConfig().getIconId())
                        .into(menuIcon);

                // 设置菜单名称
                menuName.setText(tvTitleStr);
                // 设置待办数量
                jobCount.setVisibility(View.GONE);
                if(m.getUnfinishedNo()!=null&&m.getUnfinishedNo()>0){
                    tvUndoNo.setVisibility(View.VISIBLE);
                    if(m.getUnfinishedNo()>99){
                        tvUndoNo.setText("99+");
                    }else {
                        tvUndoNo.setText(m.getUnfinishedNo()+"");
                    }

                }else {
                    tvUndoNo.setVisibility(View.GONE);
                }
            }
        }

        @OnClick(R.id.menu_item)
        public void onClick(View view) {
            // 防止多次点击
            if (!menu.getFirst()) {
                if (clickFlag == 0) {
                    String function = menu.getFuncaction();
                    if(!TextUtils.isEmpty(function)){
                        Bundle bundle = new Bundle();
                        bundle.putString("function",function);
                        ActivityUtil.startActivityNotInActivity(view.getContext(), menu.getMenuConfig().getRefClass(), bundle);
                    }else {
                        ActivityUtil.startActivityNotInActivity(view.getContext(), menu.getMenuConfig().getRefClass(), null);
                    }
                    clickFlag = 1;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickFlag = 0;
                    }
                }, 1000);
            }

        }
    }
}

