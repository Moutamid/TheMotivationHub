package com.moutamid.themotivitaionhub;

import static com.moutamid.themotivitaionhub.Utils.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import hb.xvideoplayer.MxVideoPlayer;
import hb.xvideoplayer.MxVideoPlayerWidget;

public class VideoActivity extends AppCompatActivity {
    private static final String TAG = "VideoActivity";
    private Context context = VideoActivity.this;

    private MxVideoPlayerWidget videoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        findViewById(R.id.settingsBtnVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(context, SettingsActivity.class));

            }
        });

        int count = getInt(Constants.COUNTER, 0);

        String link;

        if (count == 0) {
            link = Constants.linksArray[0];
            store(Constants.COUNTER, count + 1);
        } else if (count == 920) {
            link = Constants.linksArray[0];
            store(Constants.COUNTER, 0);
        } else {
            link = Constants.linksArray[count];
            store(Constants.COUNTER, count + 1);
        }

        videoPlayer = findViewById(R.id.videoplayerspeededitor);
        videoPlayer.startPlay(link, MxVideoPlayer.SCREEN_LAYOUT_NORMAL, "");

    }


    @Override
    protected void onPause() {
        super.onPause();
        MxVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (MxVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}