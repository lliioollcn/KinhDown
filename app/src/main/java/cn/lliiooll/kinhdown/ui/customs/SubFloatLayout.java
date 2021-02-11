package cn.lliiooll.kinhdown.ui.customs;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.lliiooll.kinhdown.R;

public class SubFloatLayout extends ViewGroup {

    public String mTagText;
    public SubFloatTextView mTextView;
    //public FloatingActionButton mFloatButton;
    public OnFabClickListener mOnFabClickListener;
    public OnTagClickListener mOnTagClickListener;

    public SubFloatLayout(@NonNull Context context) {
        this(context, null);
    }

    public SubFloatLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubFloatLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SubFloatLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SubFloatLayout);
        mTagText = typedArray.getString(R.styleable.SubFloatLayout_tagText);// 取值
        int icon = typedArray.getResourceId(R.styleable.SubFloatLayout_fabIcon, R.drawable.ic_float_button_backgroung);// 取值
        typedArray.recycle();// 释放
        mTextView = new SubFloatTextView(context);
        mTextView.setText(mTagText);
        mTextView.create();
        //mFloatButton = new FloatingActionButton(context);
       // mFloatButton.setBackground(getResources().getDrawable(icon));
        addView(mTextView);
    }

    public void setOnFabClickListener(OnFabClickListener listener) {
        this.mOnFabClickListener = listener;
    }

    public void setOnTagClickListener(OnTagClickListener listener) {
        this.mOnTagClickListener = listener;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View tagView = getChildAt(0);
        View fabView = getChildAt(1);
        int tagWidth = tagView.getMeasuredWidth();
        int tagHeight = tagView.getMeasuredHeight();
        int fabWidth = fabView.getMeasuredWidth();
        int fabHeight = fabView.getMeasuredHeight();
        int tl = dp2px(8);
        int tt = (getMeasuredHeight() - tagHeight) / 2;
        int tr = tl + tagWidth;
        int tb = tt + tagHeight;
        int fl = tr + dp2px(8);
        int ft = (getMeasuredHeight() - fabHeight) / 2;
        int fr = fl + fabWidth;
        int fb = ft + fabHeight;
        fabView.layout(fl, ft, fr, fb);
        tagView.layout(tl, tt, tr, tb);
        bindEvents(tagView, fabView);
    }

    private void bindEvents(View tagView, View fabView) {
        tagView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTagClickListener != null) {
                    mOnTagClickListener.onTagClick();
                }
            }
        });
        fabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnFabClickListener != null) {
                    mOnFabClickListener.onFabClick();
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);// 获取子view
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            width += view.getMeasuredWidth();
            height = Math.max(height, view.getMeasuredHeight());
        }
        width += dp2px(8 + 8 + 8);
        height += dp2px(8 + 8);
        setMeasuredDimension(width, height);
    }

    private int dp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , value, getResources().getDisplayMetrics());
    }

    public void setTextColor(int color) {
        mTextView.setTextColor(color);
    }


    public interface OnFabClickListener {
        void onFabClick();
    }

    public interface OnTagClickListener {
        void onTagClick();
    }
}
