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

/**
 * activity that displays the fragments the fragments
 */
public class OverviewActivity extends AppCompatActivity
        implements OverviewFragment.stepsClickListener ,
                    StepFragment.NextStepListener{

    private Recipe recipe;
    private int current_step;
    private static final String CURRENT_STEP_KEY="current";
    private static final String IS_OVERVIEW = "overview";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Intent overview = getIntent();
        recipe = overview.getParcelableExtra("recipe");

        if (savedInstanceState !=null){
            boolean is_overview =savedInstanceState.getBoolean(IS_OVERVIEW);
            if (is_overview){
                Init_Overview();
            } else {
                current_step = savedInstanceState.getInt(CURRENT_STEP_KEY);
                Recover_StepFragment(current_step);
            }
        }else Init_Overview();

    }

    /**
     * Recovery from configuration change
     * @param step_number the step number the user last seen.
     */
    private void Recover_StepFragment(int step_number){
        if (MainActivity.twoPane) Init_Tablet(step_number);
        else ReplaceStep(step_number);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.container_overview_a);
        if ((MainActivity.twoPane) || (current instanceof StepFragment)){
            outState.putInt(CURRENT_STEP_KEY,current_step);
            if (current instanceof OverviewFragment) {
                outState.putBoolean(IS_OVERVIEW, true);
            }
            else outState.putBoolean(IS_OVERVIEW,false);
        } else outState.putBoolean(IS_OVERVIEW, true);
        super.onSaveInstanceState(outState);
    }

    private void Init_Overview() {
        if (MainActivity.twoPane) Init_Tablet(0);
        else Init_Phone();
    }

    private void Init_Phone() {
        OverviewFragment overviewFragment = new OverviewFragment();
        Bundle b = new Bundle();
        b.putParcelable("recipe",recipe);
        overviewFragment.setArguments(b);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.container_overview_a,overviewFragment);
        t.addToBackStack(null);
        t.commit();
    }


    private void Init_Tablet(int step_number) {
        //Overview Left Panel
        OverviewFragment overviewFragment = new OverviewFragment();
        Bundle b_overview = new Bundle();
        b_overview.putParcelable("recipe",recipe);
        overviewFragment.setArguments(b_overview);
        current_step=step_number;
        //Step Fragment Right Panel
        StepFragment stepFragment = new StepFragment();
        Bundle b_steps = new Bundle();
        b_steps.putParcelable("step",recipe.getSteps().get(current_step));
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

    private void ReplaceStep(int position){
        StepFragment stepFragment = new StepFragment();
        current_step=position;
        Bundle b = new Bundle();
        b.putParcelable("step",recipe.getSteps().get(position));
        b.putString("recipe",recipe.getName());
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
