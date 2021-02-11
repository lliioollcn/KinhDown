package cn.lliiooll.kinhdown.fab;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import cn.lliiooll.kinhdown.utils.UserSettings;

public class DragFloatActionMenu extends FloatingActionMenu {

    private int parentHeight;
    private int parentWidth;
    private final int slop;

    public DragFloatActionMenu(Context context) {
        this(context, null);
    }

    public DragFloatActionMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public DragFloatActionMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //slop = ViewConfiguration.get(context).getScaledPagingTouchSlop();
        slop = 3;
    }


    private int lastX;
    private int lastY;

    private boolean isDrag;

    private boolean canDrag = true;

    public boolean setDrag(boolean drag) {
        this.canDrag = drag;
        return canDrag;
    }

    public boolean canDrag() {
        return canDrag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!canDrag) return super.onTouchEvent(event);
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                setPressed(true);
                isDrag = false;
                getParent().requestDisallowInterceptTouchEvent(true);
                lastX = rawX;
                lastY = rawY;
                ViewGroup parent;
                if (getParent() != null) {
                    parent = (ViewGroup) getParent();
                    parentHeight = parent.getHeight();
                    parentWidth = parent.getWidth();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (parentHeight <= 0 || parentWidth <= 0) {
                    //如果不存在父类的宽高则无法拖动，默认直接返回false
                    isDrag = false;
                    break;
                }
                int dx = rawX - lastX;
                int dy = rawY - lastY;
                // 这里修复一些华为手机无法触发点击事件
                int distance = (int) Math.sqrt(dx * dx + dy * dy);
                // 此处稍微增加一些移动的偏移量，防止手指抖动，误判为移动无法触发点击时间
                //if (distance == 0 || distance <= slop) {
                //Log.e("KinhDown Debug View", String.format("slop:%s # distance:%s", slop, distance));
                //华为默认slop = 32
                if (distance == 0 || distance <= slop) {
                    isDrag = false;
                    break;
                }
                //程序到达此处一定是正在拖动了
                isDrag = true;
                float x = getX() + dx;
                float y = getY() + dy;
                //检测是否到达边缘 左上右下
                x = x < 0 ? 0 : x > parentWidth - getWidth() ? parentWidth - getWidth() : x;
                y = getY() < 0 ? 0 : getY() + getHeight() > parentHeight ? parentHeight - getHeight() : y;
                setX(x);
                setY(y);
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_UP:
                if (isDrag()) {
                    //恢复按压效果
                    setPressed(false);
                }
                // 设置里面设置吸附
                if (UserSettings.isFloatButtonWelt()) {
                    welt(rawX);
                }
                break;
        }
        //如果是拖拽则消耗事件，否则正常传递即可。
        return isDrag() || super.onTouchEvent(event);
    }

    private boolean isDrag() {
        return isDrag;
    }

    private boolean isLeftSide() {
        return getX() == 0;
    }

    private boolean isRightSide() {
        return getX() == parentWidth - getWidth();
    }

    private void welt(int currentX) {
        if (!isLeftSide() || !isRightSide()) {
            if (currentX >= parentWidth / 2) {
                //靠右吸附
                animate().setInterpolator(new DecelerateInterpolator())
                        .setDuration(500)
                        .xBy(parentWidth - getWidth() - getX())
                        .start();
            } else {
                //靠左吸附
                ObjectAnimator oa = ObjectAnimator.ofFloat(this, "x", getX(), 0);
                oa.setInterpolator(new DecelerateInterpolator());
                oa.setDuration(500);
                oa.start();
            }
        }

    }

    @Override
    public boolean callOnClick() {
        if (!canDrag) return super.callOnClick();
        //如果是拖拽则消耗事件，否则正常传递即可。
        return isDrag() || super.callOnClick();
    }
}
