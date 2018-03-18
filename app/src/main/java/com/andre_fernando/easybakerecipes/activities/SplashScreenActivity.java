package com.andre_fernando.easybakerecipes.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.andre_fernando.easybakerecipes.R;
import com.andre_fernando.easybakerecipes.components.App;
import com.andre_fernando.easybakerecipes.components.RecipeAPI;
import com.andre_fernando.easybakerecipes.data_objects.Recipe;
import com.andre_fernando.easybakerecipes.db.RecipeDB;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * This is the splash screen activity used to load data from the server
 */
public class SplashScreenActivity extends AppCompatActivity {
    public static RecipeDB sDB;
    public static ArrayList<Recipe> AllRecipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Init_Main();
    }

    private void Init_Main(){
        sDB = RecipeDB.createDB(this);
        new AsyncStartup().execute();

    }


    /**
     * Async task to get the Recipe data.
     * This uses the Recipe API class.
     */
    private static class AsyncStartup extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            AllRecipes=RecipeAPI.getData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (AllRecipes.size()>0){
                Intent launchMain = new Intent(App.getContext(), MainActivity.class);
                launchMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                App.getContext().startActivity(launchMain);
            } else {
                Timber.e("Failed to load data. Causes: No internet, first launch.");
                Toast.makeText(App.getContext(), "No Internet available!!", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
