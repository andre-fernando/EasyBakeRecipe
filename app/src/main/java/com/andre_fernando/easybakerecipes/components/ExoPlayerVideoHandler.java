package com.andre_fernando.easybakerecipes.components;


import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

/**
 * This is a helper class for the video player
 */
public class ExoPlayerVideoHandler {
    private static ExoPlayerVideoHandler instance;

    public static ExoPlayerVideoHandler getInstance(){
        if(instance == null){
            instance = new ExoPlayerVideoHandler();
        }
        return instance;
    }

    private SimpleExoPlayer player;
    private Uri playerUri;
    private long savedPosition =0;

    private ExoPlayerVideoHandler(){}

    public void prepareExoPlayerForUri(Context context, Uri uri,
                                       SimpleExoPlayerView exoPlayerView){
        if(context != null && uri != null && exoPlayerView != null){
            if(!uri.equals(playerUri) || player == null){
                // Create a new player if the player is null or
                // we want to play a new video
                playerUri = uri;
                // Do all the standard ExoPlayer code here...
                player = ExoPlayerFactory
                        .newSimpleInstance(App.getContext(), new DefaultTrackSelector());
                exoPlayerView.setPlayer(player);

                MediaSource mediaSource = new ExtractorMediaSource(
                                                uri,
                                                new DefaultHttpDataSourceFactory("ua"),
                                                new DefaultExtractorsFactory(),
                                                null,
                                                null
                                            );
                if (savedPosition != 0){
                    player.seekTo(savedPosition);
                }
                savedPosition = 0;

                player.setPlayWhenReady(false);
                // Prepare the player with the source.
                player.prepare(mediaSource,false,false);
            }
            exoPlayerView.setPlayer(player);
        }
    }

    public void releaseVideoPlayer(){
        if(player != null)
        {
            player.release();
        }
        player = null;
    }

    public long getCurrentPosition(){
        if(player != null){
            return player.getCurrentPosition();
        }
        return 0;
    }

    public void saveCurrentPosition(){
        if(player != null){
            savedPosition= player.getCurrentPosition();
        }
    }

    public void getSavedPosition(long position){
        savedPosition = position;
    }
}
