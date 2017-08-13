package baway.com.dyouyuan.utils.keyboard;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/**
 *当前Activity 输入法模式设置  SOFT_INPUT_ADJUST_RESIZE
 *
 *当前View 注册 addOnGlobalLayoutListener
 */


public class KeyBoardHelper {

    private Activity activity;
    private OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener;
    private int screenHeight;
    // 空白高度 = 屏幕高度 - 当前 Activity 的可见区域的高度
    // 当 blankHeight 不为 0 即为软键盘高度。
    private int blankHeight = 0;

    public KeyBoardHelper(Activity activity) {
        this.activity = activity;
        screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
       //设置 当前Activity输入法模式 为resize
//        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void onCreate() {
        View content = activity.findViewById(android.R.id.content);
        // content.addOnLayoutChangeListener(listener); 这个方法有时会出现一些问题
        //当前布局大小如果有改变 返回监听
        content.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    public void onDestory() {
        View content = activity.findViewById(android.R.id.content);
        content.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {

            Rect rect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int newBlankheight = screenHeight - rect.bottom;
            if (newBlankheight != blankHeight) {
                if (newBlankheight > blankHeight) {
                    // keyboard pop
                    if (onKeyBoardStatusChangeListener != null) {
                        onKeyBoardStatusChangeListener.OnKeyBoardPop(newBlankheight);
                    }
                } else {
                    // newBlankheight < blankHeight
                    // keyboard close
                    if (onKeyBoardStatusChangeListener != null) {
                        onKeyBoardStatusChangeListener.OnKeyBoardClose(blankHeight);
                    }
                }
            }
            blankHeight = newBlankheight;
        }
    };

    public void setOnKeyBoardStatusChangeListener(
            OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener) {
        this.onKeyBoardStatusChangeListener = onKeyBoardStatusChangeListener;
    }

    public interface OnKeyBoardStatusChangeListener {

        void OnKeyBoardPop(int keyBoardheight);

        void OnKeyBoardClose(int oldKeyBoardheight);
    }
}