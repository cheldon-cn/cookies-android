package com.cycle.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

/**
 * @author cycle.member
 * @date 2018/4/29
 */
public class PreImeEditText extends androidx.appcompat.widget.AppCompatEditText {

    private View mParentView;

    public PreImeEditText(Context context) {
        super(context);
    }

    public PreImeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PreImeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setParentView(View v) {
        mParentView = v;
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (mParentView.getVisibility() == View.VISIBLE) {
                mParentView.setVisibility(View.GONE);
                return true;
            }
        }
        return super.dispatchKeyEventPreIme(event);
    }
}
