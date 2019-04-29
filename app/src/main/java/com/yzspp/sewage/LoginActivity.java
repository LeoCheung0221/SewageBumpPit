package com.yzspp.sewage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.vondear.rxtool.view.RxToast;
import com.yzspp.sewage.net.ApiRetrofit;
import com.yzspp.sewage.net.base.WrapObserver;
import com.yzspp.sewage.net.entity.LoginResp;
import com.yzspp.sewage.utils.SSIntentTool;
import com.yzspp.sewage.base.BaseActivity;
import com.yzspp.sewage.utils.SharedPreUtils;

import java.util.HashMap;

//import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private CheckBox mCbxRememberPwd;
    private Button mLoginBtn;
    private EditText mEditUserName;
    private EditText mEditPassword;

    private String mUserName;
    private String mUserPwd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mCbxRememberPwd = findViewById(R.id.remember_pwd);
        mLoginBtn = findViewById(R.id.login_btn);
        mEditUserName = findViewById(R.id.login_input_username);
        mEditPassword = findViewById(R.id.login_input_password);

        mLoginBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserName = SharedPreUtils.getInstance().getUserName();
        if (!TextUtils.isEmpty(mUserName)) {
            mEditUserName.setText(mUserName);
            mEditUserName.setSelection(mUserName.length());
        } else {
            mEditUserName.setText("");
        }
        mUserPwd = SharedPreUtils.getInstance().getUserPwd();
        if (!TextUtils.isEmpty(mUserPwd)) {
            mEditPassword.setText(mUserPwd);
            mEditPassword.setSelection(mUserPwd.length());
        } else {
            mEditPassword.setText("");
        }
    }

    @Override
    protected void setListener() {
        mEditUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (s.length() > 0) {
//                    mUserName = s.toString();
//                } else {
//                    mUserName = null;
//                }
            }
        });

        mCbxRememberPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String userName = mEditUserName.getText().toString().trim();
                    String userPwd = mEditPassword.getText().toString().trim();
                    if (TextUtils.isEmpty(userName)) {
                        RxToast.showToast("用户名尚未填写，请确认！");
                        mCbxRememberPwd.setChecked(false);
                        return;
                    }
                    SharedPreUtils.getInstance().setUserName(userName);

                    if (!TextUtils.isEmpty(userPwd)) {
                        SharedPreUtils.getInstance().setUserPwd(userPwd);
                    }
                } else {
                    SharedPreUtils.getInstance().setLoginInfo(null);
                }
            }
        });
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
                mUserName = mEditUserName.getText().toString().trim();
                mUserPwd = mEditPassword.getText().toString().trim();
                if (TextUtils.isEmpty(mUserName)) {
                    RxToast.showToast("用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mUserPwd)) {
                    RxToast.showToast("密码不能为空");
                    return;
                }
                login();
                break;
        }
    }

    private void login() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userName", mUserName);
        param.put("userPassword", mUserPwd);
        ApiRetrofit.getInstance().login(param, new WrapObserver<LoginResp>() {
            @Override
            public void OnSuccess(LoginResp response) {
                SharedPreUtils.getInstance().setLoginInfo(response);
                if (response.getResult()) {
                    SharedPreUtils.getInstance().setUserPwd(mUserPwd);
                    SSIntentTool.start(LoginActivity.this, PreviewActivity.class);
                    finish();
                } else {
                    SharedPreUtils.getInstance().setUserName(mUserName);
                    RxToast.showToast("接口返回异常");
                }
            }
        });
    }
}
