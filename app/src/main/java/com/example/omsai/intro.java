package com.example.omsai;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.VideoView;

public class intro extends AppCompatActivity {
    VideoView videoView;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAGS_CHANGED,WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);
        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide();
        videoView=findViewById(R.id.videoView);
        uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.intro_video);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });

    }
}