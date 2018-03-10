package com.andre_fernando.easybakerecipes.components;

import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.andre_fernando.easybakerecipes.data_objects.Ingredients;
import com.andre_fernando.easybakerecipes.data_objects.Recipe;
import com.andre_fernando.easybakerecipes.data_objects.Steps;
import com.andre_fernando.easybakerecipes.db.IngredientsTable;
import com.andre_fernando.easybakerecipes.db.LastJsonTable;
import com.andre_fernando.easybakerecipes.db.RecipeTable;
import com.andre_fernando.easybakerecipes.db.StepsTable;
import com.andre_fernando.easybakerecipes.utils.NetworkCheck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import timber.log.Timber;

import static com.andre_fernando.easybakerecipes.activities.SplashScreenActivity.sDB;


public class RecipeAPI {

    private static final String pref_recipe_no = "Recipe No.";
    private static final String pref_count = "Count";
    private static String fromServer;

    private static URL getApiUrl(){
        try {
            return new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Timber.e("The URL Malformed.");
            return null;
        }
    }



    public static ArrayList<Recipe> getData(){
        String fromDB = sDB.jsonDAO().getJsonString();
        if (NetworkCheck.isConnected(App.getAppContext())){
            fromServer=getJsonString();
            if (fromDB!=null && fromServer!=null){
                if (fromServer.equals(fromDB)){
                    return queryDB();
                }
            }
            //If internet is active , first launch or if server is updated.
            ArrayList<Recipe> toReturn = parseJsonString(fromServer);
            updateDB(toReturn);
            setupSharedPreference(toReturn.size());
            return toReturn;
        }
        //if no internet access
        if (!fromDB.isEmpty()){
            return queryDB();
        } else {
            return new ArrayList<>();
        }
    }

    static ArrayList<Recipe> queryDB(){
        ArrayList<RecipeTable> recipeTables = new ArrayList<>();
        recipeTables.addAll(sDB.recipeDAO().getAllRecipes());
        ArrayList<IngredientsTable> ingredientsTables = new ArrayList<>();
        ingredientsTables.addAll(sDB.ingredientsDAO().getAllIngredients());
        ArrayList<StepsTable> stepsTables = new ArrayList<>();
        stepsTables.addAll(sDB.stepsDAO().getAllSteps());
        return Recipe.fromRecipeDB(recipeTables,ingredientsTables,stepsTables);
    }

    private static void updateDB(ArrayList<Recipe> recipes){
        //Insert Recipes
        RecipeTable[] recipeTables = RecipeTable.fromRecipArray(recipes);
        sDB.recipeDAO().insertMultipleRecipe(recipeTables);

        //Insert Ingredients
        IngredientsTable[] ingredientsTables = IngredientsTable.fromRecipeObjArray(recipes);
        sDB.ingredientsDAO().insertMultipleIngredients(ingredientsTables);

        //Insert Steps
        StepsTable[] stepsTables = StepsTable.fromRecipeObjArray(recipes);
        sDB.stepsDAO().insertMultipleSteps(stepsTables);

        //Backup String
        sDB.jsonDAO().insertJson(new LastJsonTable(1, fromServer));
    }

    private static String getJsonString(){
        HttpURLConnection urlConnection;
        String jsonStringResponse;
        try {
            URL url = getApiUrl();
            if (url != null) {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();
                Scanner scanner = new Scanner(in).useDelimiter("\\A");

                jsonStringResponse = scanner.hasNext() ? scanner.next() : "";
                urlConnection.disconnect();
                return jsonStringResponse;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private static ArrayList<Recipe> parseJsonString(String jsonString){
        if (jsonString.isEmpty()){
            Timber.e("The String received is empty.");
            return new ArrayList<>();
        }
        try {
            ArrayList<Recipe> toReturn = new ArrayList<>();
            JSONArray Fulldb = new JSONArray(jsonString);
            int id;
            double servings;
            String name, image;
            ArrayList<Ingredients> ingredients;
            ArrayList<Steps> steps;
            for (int x=0;x<Fulldb.length();x++){
                JSONObject obj = Fulldb.getJSONObject(x);
                id=obj.getInt("id");
                name=obj.getString("name");
                servings=obj.getDouble("servings");
                image=obj.getString("image");
                ingredients = parseIngredients(obj.getJSONArray("ingredients"));
                steps = parseSteps(obj.getJSONArray("steps"));
                if (id>=0){
                    toReturn.add(new Recipe(id,name,ingredients,steps,servings,image));
                }
            }
            return toReturn;
        }catch (JSONException e){
            Timber.e("Json Exception at Recipe: %s", e.getMessage());
            return new ArrayList<>();
        }
    }

    private static ArrayList<Ingredients> parseIngredients(JSONArray ar){
        try {
            ArrayList<Ingredients> toReturn = new ArrayList<>();
            double quantity;
            String measure,ingredient;
            for (int x=0;x<ar.length();x++){
                JSONObject obj = ar.getJSONObject(x);
                quantity=obj.getDouble("quantity");
                measure=obj.getString("measure");
                ingredient=obj.getString("ingredient");
                toReturn.add(new Ingredients(quantity,measure,ingredient));
            }
            return toReturn;
        }catch (JSONException e){
            Timber.e("Json Exception at Ingredient: %s", e.getMessage());
            return new ArrayList<>();
        }
    }

    private static ArrayList<Steps> parseSteps(JSONArray ar){
        try{
            ArrayList<Steps> toReturn = new ArrayList<>();
            int id;
            String short_description, description, videoUrl,thumbnailUrl;
            for (int x=0;x<ar.length();x++){
                JSONObject obj = ar.getJSONObject(x);
                id=obj.getInt("id");
                short_description = obj.getString("shortDescription");
                description = obj.getString("description");
                videoUrl = obj.getString("videoURL");
                thumbnailUrl = obj.getString("thumbnailURL");
                if (id >= 0){
                    toReturn.add(new Steps(id,short_description,description,videoUrl,thumbnailUrl));
                }
            }
            return toReturn;
        }catch (JSONException e){
            Timber.e("Json Exception at Steps: %s", e.getMessage());
            return new ArrayList<>();
        }
    }

    // Shared Preference methods
    private static void setupSharedPreference(int count){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(pref_recipe_no,0);
        editor.putInt(pref_count,count);
        editor.apply();
    }

    static int getLastSeenRecipeNo(){
        return PreferenceManager.getDefaultSharedPreferences(App.getAppContext())
                .getInt(pref_recipe_no,0);
    }

    static int getRecipeCount(){
        return PreferenceManager.getDefaultSharedPreferences(App.getAppContext())
                .getInt(pref_count,0);
    }

    public static void IncreaseRecipeNo(){
        int recipeNo = getLastSeenRecipeNo()+1;
        int count = getRecipeCount();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        SharedPreferences.Editor editor = sp.edit();
        if (recipeNo>=count) editor.putInt(pref_recipe_no,0);
        else editor.putInt(pref_recipe_no,recipeNo);
        editor.apply();
    }

    public static void DecreaseRecipeNo(){
        int recipeNo = getLastSeenRecipeNo()-1;
        int count = getRecipeCount();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        SharedPreferences.Editor editor = sp.edit();
        if (recipeNo<1) editor.putInt(pref_recipe_no,count-1);
        else editor.putInt(pref_recipe_no,recipeNo);
        editor.apply();
    }

}
