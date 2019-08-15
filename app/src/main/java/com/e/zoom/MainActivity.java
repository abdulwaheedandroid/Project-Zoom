package com.e.zoom;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import us.zoom.sdk.InstantMeetingOptions;
import us.zoom.sdk.InviteOptions;
import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.JoinMeetingParams;
import us.zoom.sdk.MeetingService;
import us.zoom.sdk.MeetingServiceListener;
import us.zoom.sdk.MeetingStatus;
import us.zoom.sdk.MeetingViewsOptions;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomSDKAuthenticationListener;
import us.zoom.sdk.ZoomSDKInitializeListener;

public class MainActivity extends AppCompatActivity implements Constants
        , ZoomSDKInitializeListener, MeetingServiceListener, ZoomSDKAuthenticationListener
        {

            EditText etMtngNum ;
            Button btnJoinMtng ;
            private static final String WEB_DOMAIN = "https://zoom.us" ;
            Button btnStartInstantMtng ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMtngNum = findViewById(R.id.et_mtng_number);
        btnJoinMtng = findViewById(R.id.btn_join_meeting);
        btnStartInstantMtng = findViewById(R.id.btn_start_instant_meeting);
        // Meeting Number input field

     //   registerMeetingServiceListener();
        ZoomSDK zoomSDK = ZoomSDK.getInstance();

        if(savedInstanceState == null) {
           zoomSDK.initialize(this, APP_KEY, APP_SECRET, WEB_DOMAIN, this);
        }
btnStartInstantMtng.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this, "StartInstantMtng", Toast.LENGTH_SHORT).show();
        // Step 1: Get Zoom SDK instance.
        ZoomSDK zoomSDK = ZoomSDK.getInstance();
        // Check if the zoom SDK is initialized
        if(!zoomSDK.isInitialized()) {
            Toast.makeText(MainActivity.this, "ZoomSDK has not been initialized successfully", Toast.LENGTH_LONG).show();
            return;
        }
        // Step 2: Get meeting service from zoom SDK instance.
        MeetingService meetingService = zoomSDK.getMeetingService();
        // Step 3: Configure meeting options.
        InstantMeetingOptions opts = new InstantMeetingOptions();

        // Some available options
        opts.no_driving_mode = true;
        opts.no_invite = true;
        opts.no_meeting_end_message = true;
        opts.no_titlebar = true;
        opts.no_bottom_toolbar = true;
        opts.no_dial_in_via_phone = true;
        opts.no_dial_out_to_phone = true;
        opts.no_disconnect_audio = true;
        opts.no_share = true;

        // Step 4: Call meeting service to start instant meeting
        meetingService.startInstantMeeting(MainActivity.this, opts);

    }
});
        btnJoinMtng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Step 1: Get meeting number from input field.
                String meetingNo = etMtngNum.getText().toString().trim();
                // Check if the meeting number is empty.
                if(meetingNo.length() == 0) {
                    Toast.makeText(MainActivity.this, "You need to enter a meeting number/ vanity id which you want to join.", Toast.LENGTH_LONG).show();
                    return;
                }

                // Step 2: Get Zoom SDK instance.
                ZoomSDK zoomSDK = ZoomSDK.getInstance();
                // Check if the zoom SDK is initialized
                if(!zoomSDK.isInitialized()) {
                    Toast.makeText(MainActivity.this, "ZoomSDK has not been initialized successfully", Toast.LENGTH_LONG).show();
                    return;
                }

                // Step 3: Get meeting service from zoom SDK instance.
                MeetingService meetingService = zoomSDK.getMeetingService();

                // Step 4: Configure meeting options.
                JoinMeetingOptions opts = new JoinMeetingOptions();

                // Some available options
                //		opts.no_driving_mode = true;
                //		opts.no_invite = true;
                //		opts.no_meeting_end_message = true;
                //		opts.no_titlebar = true;
                //		opts.no_bottom_toolbar = true;
                //		opts.no_dial_in_via_phone = true;
                //		opts.no_dial_out_to_phone = true;
                //		opts.no_disconnect_audio = true;
                //		opts.no_share = true;
                //		opts.invite_options = InviteOptions.INVITE_VIA_EMAIL + InviteOptions.INVITE_VIA_SMS;
                //		opts.no_audio = true;
                //		opts.no_video = true;
                //		opts.meeting_views_options = MeetingViewsOptions.NO_BUTTON_SHARE;
                //		opts.no_meeting_error_message = true;
                //		opts.participant_id = "participant id";

                // Step 5: Setup join meeting parameters
                JoinMeetingParams params = new JoinMeetingParams();

                params.displayName = "Hello World From Zoom SDK";
                params.meetingNo = meetingNo;

                // Step 6: Call meeting service to join meeting
                meetingService.joinMeetingWithParams(MainActivity.this, params, opts);
            }
        });

       
    }

            @Override
            public void onMeetingStatusChanged(MeetingStatus meetingStatus, int errorCode, int internalErrorCode) {
                Toast.makeText(this, "onMeetingStatusChanged", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onZoomSDKLoginResult(long result) {
                Toast.makeText(this, "onZoomSDKLoginResult", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onZoomSDKLogoutResult(long result) {
                Toast.makeText(this, "onZoomSDKLogoutResult", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onZoomIdentityExpired() {
                Toast.makeText(this, "onZoomIdentityExpired", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onZoomSDKInitializeResult(int errorCode, int internalErrorCode) {
                Toast.makeText(this, "onZoomSDKInitializeResult", Toast.LENGTH_SHORT).show();
            }
        }
