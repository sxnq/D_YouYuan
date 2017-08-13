package baway.com.dyouyuan.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import baway.com.dyouyuan.R;


/**
 * 所有的 activity 都继承
 */
public class IActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iactivity);

        AppManager.getAppManager().addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);

    }

    @Override
    public void onClick(View v) {

    }
}
