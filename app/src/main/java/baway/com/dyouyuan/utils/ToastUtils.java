package baway.com.dyouyuan.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtils {

    private static Toast toast;
    /**
     * 单例吐司
     */
    public static void showToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }

        //toast.setGravity(Gravity.FILL_HORIZONTAL|Gravity.TOP,0,70);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setText(msg);
        toast.show();
    }
}
