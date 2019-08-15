package com.e.zoom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import us.zoom.sdk.ZoomApiError;
import us.zoom.sdk.ZoomAuthenticationError;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomSDKAuthenticationListener;

public class LoginAc extends Activity implements ZoomSDKAuthenticationListener, View.OnClickListener {

    private EditText mEdtUserName;
    private EditText mEdtPassword;
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mEdtUserName = (EditText)findViewById(R.id.et_username);
        mEdtPassword = (EditText)findViewById(R.id.et_pass);

        mBtnLogin = (Button)findViewById(R.id.btn_loginn);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mEdtUserName.getText().toString().trim();
                String password = mEdtPassword.getText().toString().trim();
                if(userName.length() == 0 || password.length() == 0) {
                    Toast.makeText(LoginAc.this, "You need to enter user name and password.", Toast.LENGTH_LONG).show();
                    return;
                }
                ZoomSDK zoomSDK = ZoomSDK.getInstance();
                if(!(zoomSDK.loginWithZoom(userName, password) == ZoomApiError.ZOOM_API_ERROR_SUCCESS)) {
                    Toast.makeText(LoginAc.this, "ZoomSDK has not been initialized successfully or sdk is logging in.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginAc.this, "login gonna be invisible", Toast.LENGTH_SHORT).show();
                    //  mBtnLogin.setVisibility(View.GONE);
                   // onZoomSDKLoginResult();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onZoomSDKLoginResult(long result) {
        Toast.makeText(this, "LOGIN A/C \n onZoomSDKLoginResult", Toast.LENGTH_SHORT).show();

        if(result == ZoomAuthenticationError.ZOOM_AUTH_ERROR_SUCCESS) {
            Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Login failed result code = " + result, Toast.LENGTH_SHORT).show();
        }
        mBtnLogin.setVisibility(View.VISIBLE);

    }

    @Override
    public void onZoomSDKLogoutResult(long result) {
        Toast.makeText(this, "LOGIN A/C \n onZoomSDKLogoutResult", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onZoomIdentityExpired() {
        Toast.makeText(this, "LOGIN A/C \n  onZoomSDKLogoutResult", Toast.LENGTH_SHORT).show();
    }
}
