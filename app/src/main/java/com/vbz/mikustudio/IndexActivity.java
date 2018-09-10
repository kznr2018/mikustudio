package com.vbz.mikustudio;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sprylab.android.widget.TextureVideoView;

public class IndexActivity extends AppCompatActivity {

    private TextureVideoView videoplayer;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        videoplayer = findViewById(R.id.video_player);

    }

    private void startMediaPlayer() {
        if (videoplayer == null) {
            return;
        }
        if (videoplayer.isPressed()) {
            try {
                if (mediaPlayer != null) {
                    float v = SPUtils.getInstance().getBoolean("key_voice_p_on", true) ? 1f : 0f;
                    mediaPlayer.setVolume(v, v);
                    mediaPlayer.setLooping(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            videoplayer.start();
            return;
        }
        videoplayer.setKeepScreenOn(true);
        try {
            videoplayer.setVideoURI(Uri.parse("android.resource:///raw/wallpaper"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        videoplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                IndexActivity.this.mediaPlayer = mediaPlayer;
                mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                            videoplayer.setBackgroundColor(Color.TRANSPARENT);
                        return true;
                    }
                });
                float v = SPUtils.getInstance().getBoolean("key_voice_p_on", true) ? 1f : 0f;
                mediaPlayer.setVolume(v, v);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        });
    }

}
