package cn.lliiooll.kinhdown.ui.customs;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class SubFloatTextView extends CardView {

    private final TextView mTextView;

    public SubFloatTextView(@NonNull Context context) {
        this(context, null);
    }

    public SubFloatTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubFloatTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextView = new TextView(context);// 定义TextView
        mTextView.setSingleLine(true);// 单行美观
    }

    public void setText(CharSequence text) {
        if (mTextView != null) {
            mTextView.setText(text);
        }
    }

    public void create() {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        int l = dp2px(8);
        int t = dp2px(8);
        int r = dp2px(8);
        int b = dp2px(8);
        layoutParams.setMargins(l, t, r, b);
        addView(mTextView, layoutParams);
    }

    private int dp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , value, getResources().getDisplayMetrics());
    }

    protected void setTextSize(float size) {
        mTextView.setTextSize(size);
    }

    protected void setTextColor(int color) {
        mTextView.setTextColor(color);
    }

}
