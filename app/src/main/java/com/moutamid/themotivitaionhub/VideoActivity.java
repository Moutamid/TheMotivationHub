package com.moutamid.themotivitaionhub;

import static com.moutamid.themotivitaionhub.Utils.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Log.d(TAG, "onCreate: ");
        findViewById(R.id.settingsBtnVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(context, SettingsActivity.class));

            }
        });

        int count = getInt(Constants.COUNTER, 0);
        Log.d(TAG, "onCreate: count " + count);
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

        youTubePlayerView = findViewById(R.id.youtube_player_view_fragment_view);
        initYoutubePlayer(link);
        Log.d(TAG, "onCreate: link " + link);
//        videoPlayer = findViewById(R.id.videoplayerspeededitor);
//        videoPlayer.startPlay(link, MxVideoPlayer.SCREEN_LAYOUT_NORMAL, "");
        Log.d(TAG, "onCreate: finished");
    }

    private YouTubePlayer youTubePlayer1;
    private YouTubePlayerView youTubePlayerView;

    private void initYoutubePlayer(String link) {
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                Log.d(TAG, "onReady: public void onReady(YouTubePlayer youTubePlayer) {");

                youTubePlayer1 = youTubePlayer;

//                youTubePlayer.cueVideo(getVideoId(videoUrl), 0);
                youTubePlayer.loadVideo(getVideoId(link), 0);
//                youTubePlayer.addListener(youTubePlayerListener());

//                addFullScreenListenerToPlayer(youTubePlayer);

//                ActivityVideoPlayer.this.addFullScreenListenerToPlayer(youTubePlayer);

//                YouTubePlayerTracker tracker = new YouTubePlayerTracker();
//                youTubePlayer.addListener(tracker);
//
//                Log.d(TAG, "onReady: tracker.getCurrentSecond();"+tracker.getCurrentSecond());
//                Log.d(TAG, "onReady: tracker.getState();"+tracker.getState());
//                Log.d(TAG, "onReady: tracker.getVideoDuration()"+tracker.getVideoDuration());
//                Log.d(TAG, "onReady: tracker.getVideoId();"+tracker.getVideoId());
            }

        });

        youTubePlayerView.enableBackgroundPlayback(false);

        // Showing Video Title
        youTubePlayerView.getPlayerUiController().showVideoTitle(false);
//        youTubePlayerView.getPlayerUiController().setVideoTitle("Loading...");

        // Showing Custom Forward and Backward Icons
        youTubePlayerView.getPlayerUiController().showCustomAction1(false);
        youTubePlayerView.getPlayerUiController().showCustomAction2(false);

        // Showing Menu Button
        youTubePlayerView.getPlayerUiController().showMenuButton(false);

        // Hiding Full Screen Button
        youTubePlayerView.getPlayerUiController().showFullscreenButton(false);

        youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
        // Showing Full Screen Button
//        youTubePlayerView.getPlayerUiController().showFullscreenButton(true);
//        youTubePlayerView.getPlayerUiController().setFullScreenButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                youTubePlayerView.toggleFullScreen();
//            }
//        });

        // Hiding Seekbar
        youTubePlayerView.getPlayerUiController().showSeekBar(false);

//        TextView tv = root.findViewById(R.id.current_sec);

//        tv.setText(taskArrayList.get(currentPosition).getTotalViewTimeQuantity());

        // INIT PLAYER VIEW
//        getVideoTitle.setId(videoUrl);
//        getVideoTitle.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MxVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
//        if (MxVideoPlayer.backPress()) {
//            return;
//        }
        super.onBackPressed();
    }

    private static String getVideoId(String videoUrl) {
        String videoId = "";
        String regex = "http(?:s)?:\\/\\/(?:m.)?(?:www\\.)?youtu(?:\\.be\\/|be\\.com\\/(?:watch\\?(?:feature=youtu.be\\&)?v=|v\\/|embed\\/|user\\/(?:[\\w#]+\\/)+))([^&#?\\n]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(videoUrl);
        if (matcher.find()) {
            videoId = matcher.group(1);
        }
        return videoId;
    }
}