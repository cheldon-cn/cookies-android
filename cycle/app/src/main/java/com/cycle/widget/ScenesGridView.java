package com.cycle.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * ref: https://stackoverflow.com/questions/44386926/recyclewview-with-nested-gridview-adjust-height
 * @author Lenovo
 * @date 2017/9/28
 */
public class ScenesGridView  extends GridView {

    public ScenesGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScenesGridView(Context context) {
        super(context);
    }

    public ScenesGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}