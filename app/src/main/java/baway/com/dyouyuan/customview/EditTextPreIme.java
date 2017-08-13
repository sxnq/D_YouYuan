package baway.com.dyouyuan.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class EditTextPreIme extends EditText {

    public EditTextPreIme(Context context) {
        super(context);
    }

    public EditTextPreIme(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextPreIme(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //when the softinput display
            //处理事件
            if(listener != null){
                listener.onBack();
            }
        }
        return super.dispatchKeyEventPreIme(event);
    }


    public EditTextListener listener ;

    public void setListener(EditTextListener listener){
        this.listener = listener;
    }

    public interface EditTextListener {
        public void onBack();
    }
}