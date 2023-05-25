package com.example.mymultimediaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private Uri fileUri;
    int position = 0;
    MediaPlayer mediaPlayer;
    VideoView videoView;
    ProgressDialog progressDialog;


    public void turnOffAudio(View view) {
        if (mediaPlayer == null)
            return;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            position = 0;
        }

    }

    public void turnOnAudio(View view) {
        try {
            Uri audioUri = Uri.parse("android.resource://"
                    + getPackageName() + "/"
                    + R.raw.adrumsmusic);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(getApplicationContext(), audioUri);
            mediaPlayer.prepare(); // for buffering, may take long
            mediaPlayer.start();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

    }
//	Code for the OFF button
    public void turnOffVideo(View view) {
        if (videoView == null)
            return;
        if (videoView.isPlaying()){
            videoView.stopPlayback();
            videoView = null;
        }
        if (progressDialog.isShowing()){
            progressDialog.hide();
            progressDialog.dismiss();
            progressDialog = null;
        }

    }
    public void turnOnVideo(View view) {
        videoView = (VideoView) findViewById(R.id.videoView);
        // Create a progress bar for Video player and show it
        progressDialog = new ProgressDialog(this); // progress bar
        progressDialog.setMessage("Buffering...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(this);
            mediacontroller.setAnchorView(videoView);
            videoView.setMediaController(mediacontroller);
            // set the path to video file
            videoView.setVideoPath(
                    "android.resource://" + getPackageName() + "/"
                            + R.raw.atestvideo);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                progressDialog.dismiss();
                videoView.start();
            }
        });

    }

    public void PauseAudio(View view) {
        if (mediaPlayer == null)
            return;
        if (mediaPlayer.isPlaying()) {
            position = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }

    }

    public void turnResumeAudio(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(position);
            mediaPlayer.start();
        }

    }
}