package com.andre_fernando.easybakerecipes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.andre_fernando.easybakerecipes.R;
import com.andre_fernando.easybakerecipes.components.ExoPlayerVideoHandler;
import com.andre_fernando.easybakerecipes.data_objects.Steps;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This is the activity that portrays the video in fullscreen
 */
public class Fullscreen_Video_Activity extends AppCompatActivity {
    private Steps step;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.exo_player_view_fullscreen)
    SimpleExoPlayerView exo_player_view_fullscreen;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.exo_fullscreen)
    ImageButton bt_exo_fullscreen_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen__video_);
        ButterKnife.bind(this);
        Intent r = getIntent();
        step = r.getParcelableExtra("step");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (step.hasVideo()){
            ExoPlayerVideoHandler.getInstance()
                    .prepareExoPlayerForUri(this,step.getVideoUri(),exo_player_view_fullscreen);

            bt_exo_fullscreen_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ExoPlayerVideoHandler.getInstance().saveCurrentPosition();
        ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
    }
}
