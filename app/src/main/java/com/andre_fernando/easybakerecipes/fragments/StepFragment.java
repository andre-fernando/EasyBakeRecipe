package com.andre_fernando.easybakerecipes.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.andre_fernando.easybakerecipes.R;
import com.andre_fernando.easybakerecipes.activities.Fullscreen_Video_Activity;
import com.andre_fernando.easybakerecipes.components.App;
import com.andre_fernando.easybakerecipes.components.ExoPlayerVideoHandler;
import com.andre_fernando.easybakerecipes.data_objects.Steps;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;


public class StepFragment extends Fragment {
    private Steps step;
    private Unbinder unbinder;
    private NextStepListener listener;
    private String recipe_name;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tv_step_recipe_name)
    TextView tv_recipe_name;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tv_step_description)
    TextView step_description;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.iv_step_image)
    ImageView step_Image;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.next_Step)
    Button next_step;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.previous_Step)
    Button previous_step;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.step_video_player)
    SimpleExoPlayerView exoPlayerView;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.exo_fullscreen)
    ImageButton bt_exo_fullscreen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.steps_fragment_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this,view);
        Init_StepFragment();
    }

    private void Init_StepFragment(){
        try{
            //noinspection ConstantConditions
            step = getArguments().getParcelable("step");
            recipe_name = getArguments().getString("recipe");
        }catch (NullPointerException e){
            Timber.e("Failed to receive step in stepfragment.");
        }

        if (step !=null){
            //Heading
            tv_recipe_name.setText(recipe_name);

            //Sets description
            step_description.setText(step.getDescription());

            //Sets Image
            if (step.hasImage()){
                Glide.with(this)
                        .load(Uri.parse(step.getThumbnailUrl()))
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.ic_image_placeholder)
                                .error(R.drawable.ic_image_placeholder))
                        .into(step_Image);
            } else {
                step_Image.setVisibility(View.GONE);
            }


            next_step.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.nextStep(step.getId());
                }
            });

            previous_step.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.previousStep(step.getId());
                }
            });

            //Sets video player
            //Init_video();
        }
    }

    private void Init_video(){
        if (step.hasVideo() && exoPlayerView != null){
            exoPlayerView.setVisibility(View.VISIBLE);
            ExoPlayerVideoHandler.getInstance()
                    .prepareExoPlayerForUri(App.getContext(),step.getVideoUri(),exoPlayerView);

            ExoPlayerVideoHandler.getInstance()
                    .goToForeground();

            bt_exo_fullscreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
                    Intent fullscreen_player = new Intent(App.getContext(), Fullscreen_Video_Activity.class);
                    fullscreen_player.putExtra("step",step);
                    startActivity(fullscreen_player);
                }
            });
        } else {
            if (exoPlayerView != null) {
                exoPlayerView.setVisibility(View.INVISIBLE);
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Init_video();
    }

    @Override
    public void onPause() {
        super.onPause();
        ExoPlayerVideoHandler.getInstance().goToBackground();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NextStepListener){
            listener = (NextStepListener) context;
        } else {
            Timber.e("Step Listener not implented.");
            throw new ClassCastException(context.toString()
                    +" must implement StepFragment.NextStepListener");
        }
    }

    public interface NextStepListener {
        void nextStep(int id);

        void previousStep(int id);
    }
}
