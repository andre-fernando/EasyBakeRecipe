package com.andre_fernando.easybakerecipes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.andre_fernando.easybakerecipes.R;
import com.andre_fernando.easybakerecipes.fragments.RecipeListFragment;

public class MainActivity extends AppCompatActivity
        implements RecipeListFragment.RecipeSelectListener{
    public static boolean twoPane;
    private long lastpress=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init_Main();
    }

    void Init_Main(){
        twoPane = isTwoPane();
        Init_fragment();

    }

    void Init_fragment(){
        RecipeListFragment recipeListFragment = new RecipeListFragment();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_main_a,recipeListFragment);
        transaction.commit();
    }

    boolean isTwoPane(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int widthPixels = dm.widthPixels;
        int heightPixels = dm.heightPixels;
        float scaleFactor = dm.density;
        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;
        float smallestWidth = Math.min(widthDp,heightDp);
        return smallestWidth>600;
    }

    @Override
    public void onBackPressed() {
        long current_time = System.currentTimeMillis();
        if (current_time-lastpress >5000){
            Toast.makeText(this, R.string.Toast_Back_to_exit, Toast.LENGTH_SHORT).show();
            lastpress=current_time;
        }else {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void recipeSelected(int position) {
        Intent lauch_overview = new Intent(MainActivity.this,OverviewActivity.class);
        lauch_overview.putExtra("recipe",SplashScreenActivity.AllRecipes.get(position));
        startActivity(lauch_overview);
    }

}
