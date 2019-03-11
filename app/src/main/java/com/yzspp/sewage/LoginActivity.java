package com.yzspp.sewage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yzspp.sewage.utils.SSIntentTool;
import com.yzspp.sewage.base.BaseActivity;

//import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button mLoginBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mLoginBtn = findViewById(R.id.login_btn);

        mLoginBtn.setOnClickListener(this);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected String[] getNeedPermissions() {
        return new String[0];
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
//                SSIntentTool.start(LoginActivity.this, MainActivity.class);
                SSIntentTool.start(LoginActivity.this, PreviewActivity.class);
                break;
        }
    }
}
