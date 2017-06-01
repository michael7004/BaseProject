package com.example.indianic.baseproject.activity;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.utills.Utills;

/**
 * OnLineVideoPlayerActivity class created on 30/05/17.
 */

public class OnLineVideoPlayerActivity extends BaseActivity {

    private VideoView myVideoView;
    private int position = 0;
    private MediaController mediaControls;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_test);

        String vidPath = getIntent().getStringExtra("VID_PATH");

        //set the media controller buttons
        if (mediaControls == null) {
            mediaControls = new MediaController(OnLineVideoPlayerActivity.this);
        }

        //initialize the VideoView
        myVideoView = (VideoView) findViewById(R.id.activity_test_vv_container);

        progressDialog = Utills.showProgressDialogNew(OnLineVideoPlayerActivity.this, getString(R.string.msg_loading), false);

        try {
            //set the media controller in the VideoView
            myVideoView.setMediaController(mediaControls);

            //set the uri of the video to be played
            myVideoView.setVideoURI(Uri.parse(vidPath));

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        myVideoView.requestFocus();
        //we also set an setOnPreparedListener in order to know when the video file is ready for playback
        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                // close the progress bar and play the video
                progressDialog.dismiss();
                //if we have a position on savedInstanceState, the video playback should start from here
                myVideoView.seekTo(position);
                if (position == 0) {
                    myVideoView.start();
                } else {
                    //if we come from a resumed activity, video playback will be paused
                    myVideoView.pause();
                }
            }
        });

    }

    @Override
    public void initView() {

    }
}
