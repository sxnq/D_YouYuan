package baway.com.dyouyuan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import baway.com.dyouyuan.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhoneLoginActivity extends Activity {

    @BindView(R.id.loginactivity_fanhui)
    ImageView loginactivityFanhui;
    @BindView(R.id.phoneloginactivity_etphone)
    EditText phoneloginactivityEtphone;
    @BindView(R.id.phoneloginactivity_yanzhengma)
    EditText phoneloginactivityYanzhengma;
    @BindView(R.id.phoneloginactivity_bthuoquyanzhengma)
    Button phoneloginactivityBthuoquyanzhengma;
    @BindView(R.id.phoneloginactivity_btsure)
    Button phoneloginactivityBtsure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.loginactivity_fanhui, R.id.phoneloginactivity_bthuoquyanzhengma, R.id.phoneloginactivity_btsure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginactivity_fanhui:
                break;
            case R.id.phoneloginactivity_bthuoquyanzhengma:
                break;
            case R.id.phoneloginactivity_btsure:
                break;
        }
    }
}
