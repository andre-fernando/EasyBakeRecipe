package com.andre_fernando.easybakerecipes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.andre_fernando.easybakerecipes.R;
import com.andre_fernando.easybakerecipes.data_objects.Recipe;
import com.andre_fernando.easybakerecipes.fragments.OverviewFragment;
import com.andre_fernando.easybakerecipes.fragments.StepFragment;

public class OverviewActivity extends AppCompatActivity
        implements OverviewFragment.stepsClickListener ,
                    StepFragment.NextStepListener{
    Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Init_Overview();

    }


    private void Init_Overview() {
        Intent overview = getIntent();
        recipe = overview.getParcelableExtra("recipe");
        if (MainActivity.twoPane) Init_Tablet();
        else Init_Phone();
    }

    void Init_Phone() {
        OverviewFragment overviewFragment = new OverviewFragment();
        Bundle b = new Bundle();
        b.putParcelable("recipe",recipe);
        overviewFragment.setArguments(b);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.container_overview_a,overviewFragment);
        t.addToBackStack(null);
        t.commit();
    }


    void Init_Tablet() {
        //Overview Left Panel
        OverviewFragment overviewFragment = new OverviewFragment();
        Bundle b_overview = new Bundle();
        b_overview.putParcelable("recipe",recipe);
        overviewFragment.setArguments(b_overview);
        //Step Fragment Right Panel
        StepFragment stepFragment = new StepFragment();
        Bundle b_steps = new Bundle();
        b_steps.putParcelable("step",recipe.getSteps().get(0));
        stepFragment.setArguments(b_steps);

        //Attaching the Fragments
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.add(R.id.container_overview_a,overviewFragment);
        t.add(R.id.container_overview_b,stepFragment);
        t.commit();
    }

    @Override
    public void ClickedStep(int position) {
        ReplaceStep(position);
    }

    @Override
    public void nextStep(int id) {
        int position = id+1;
        int numberofsteps =recipe.getSteps().size();
        if (position > numberofsteps-1){
            ReplaceStep(0);
        } else ReplaceStep(position);
    }

    @Override
    public void previousStep(int id) {
        int position = id-1;
        int numberofsteps =recipe.getSteps().size();
        if (position < 0){
            ReplaceStep(numberofsteps-1);
        } else ReplaceStep(position);
    }

    @Override
    public void onBackPressed() {
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.container_overview_a);
        if ((!MainActivity.twoPane) && (current instanceof StepFragment)){
            Init_Phone();
        }else{
            finish();
        }
    }

    void ReplaceStep(int position){
        StepFragment stepFragment = new StepFragment();
        Bundle b = new Bundle();
        b.putParcelable("step",recipe.getSteps().get(position));
        stepFragment.setArguments(b);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        if (MainActivity.twoPane){
            t.replace(R.id.container_overview_b,stepFragment);
        }else {
            t.replace(R.id.container_overview_a,stepFragment);
        }
        t.commit();
    }
}
