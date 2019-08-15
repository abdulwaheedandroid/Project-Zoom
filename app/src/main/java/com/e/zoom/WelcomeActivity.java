package com.e.zoom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zipow.videobox.LoginActivity;

import us.zoom.sdk.ZoomApiError;
import us.zoom.sdk.ZoomAuthenticationError;
import us.zoom.sdk.ZoomError;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomSDKAuthenticationListener;
import us.zoom.sdk.ZoomSDKInitializeListener;

public class WelcomeActivity extends Activity implements Constants, ZoomSDKInitializeListener, ZoomSDKAuthenticationListener, View.OnClickListener {

    private static final String WEB_DOMAIN = "https://zoom.us" ;
    // https://zoom.us
    private Button mBtnLogin;
    private Button mBtnWithoutLogin;
    private ZoomSDK mZoomSDK;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mZoomSDK = ZoomSDK.getInstance();
        if(mZoomSDK.isLoggedIn()) {
            finish();
            showMainActivity();
            return;
        }

        setContentView(R.layout.activity_welcome);

        mBtnLogin = (Button)findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
        mBtnWithoutLogin = (Button)findViewById(R.id.btn_without_login);
        mBtnWithoutLogin.setOnClickListener(this);

        if(savedInstanceState == null) {
            mZoomSDK.initialize(this, APP_KEY, APP_SECRET, WEB_DOMAIN, this);
        }

        if(mZoomSDK.isInitialized()) {
            mBtnLogin.setVisibility(View.VISIBLE);
            mBtnWithoutLogin.setVisibility(View.VISIBLE);
        } else {
            mBtnLogin.setVisibility(View.GONE);
            mBtnWithoutLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_login) {
            showLoginActivity();
        } else if(v.getId() == R.id.btn_without_login) {
            showMainActivity();
        }
    }

    @Override
    public void onZoomSDKLoginResult(long result) {
        Toast.makeText(this, "Welcome activity \n onZoomSDKLoginResult", Toast.LENGTH_SHORT).show();
        if((int)result == ZoomAuthenticationError.ZOOM_AUTH_ERROR_SUCCESS) {
            showMainActivity();
            finish();
        } else {
            mBtnLogin.setVisibility(View.VISIBLE);
            mBtnWithoutLogin.setVisibility(View.VISIBLE);
        }

    }

    private void showLoginActivity() {
        Intent intent = new Intent(this, LoginAc.class);
        startActivity(intent);
    }

    private void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onZoomSDKLogoutResult(long result) {
        Toast.makeText(this, "Welcome activity \n onZoomSDKLogoutResult", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onZoomIdentityExpired() {
        Toast.makeText(this, "Welcome activity \n onZoomIdentityExpired", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onZoomSDKInitializeResult(int errorCode, int internalErrorCode) {
        Toast.makeText(this, "Welcome activity \n onZoomSDKInitializeResult", Toast.LENGTH_SHORT).show();



        if( errorCode != ZoomError.ZOOM_ERROR_SUCCESS) {
            Toast.makeText(this, "Failed to initialize Zoom SDK. Error: " + errorCode + ", internalErrorCode=" + internalErrorCode, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Initialize Zoom SDK successfully.", Toast.LENGTH_LONG).show();

            if(mZoomSDK.tryAutoLoginZoom() == ZoomApiError.ZOOM_API_ERROR_SUCCESS) {
                mZoomSDK.addAuthenticationListener(this);
                mBtnLogin.setVisibility(View.GONE);
                mBtnWithoutLogin.setVisibility(View.GONE);
            } else {
                mBtnWithoutLogin.setVisibility(View.VISIBLE);
                mBtnLogin.setVisibility(View.VISIBLE);
            }
        }
    }
}