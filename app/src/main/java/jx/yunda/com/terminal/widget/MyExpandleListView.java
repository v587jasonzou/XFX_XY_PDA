package jx.yunda.com.terminal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

public class MyExpandleListView extends ExpandableListView {
    public MyExpandleListView(Context context) {
        super(context);
    }

    public MyExpandleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyExpandleListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
