package com.andre_fernando.easybakerecipes.components;


import android.database.Cursor;
import android.os.AsyncTask;

import com.andre_fernando.easybakerecipes.data_objects.Recipe;
import com.andre_fernando.easybakerecipes.db.IngredientsTable;
import com.andre_fernando.easybakerecipes.db.RecipeContentProvider;
import com.andre_fernando.easybakerecipes.db.RecipeTable;
import com.andre_fernando.easybakerecipes.db.StepsTable;

import java.util.ArrayList;

/**
 * Async class to get all Recipe's from Content Provider
 */
class RecipeFromContentProvider extends AsyncTask<Void, Void, ArrayList<Recipe>> {

    @Override
    protected ArrayList<Recipe> doInBackground(Void... voids) {
        Cursor recipe_cursor = App.getContext().getContentResolver()
                .query(RecipeContentProvider.RECIPE_URI,
                        null,null,null,null);
        ArrayList<RecipeTable> recipeTables = RecipeTable.fromRecipeCursor(recipe_cursor);

        Cursor ingredients_cursor = App.getContext().getContentResolver()
                .query(RecipeContentProvider.INGREDIENTS_URI,
                        null,null,null,null);
        ArrayList<IngredientsTable> ingredientsTables =
                IngredientsTable.fromIngredientsCursor(ingredients_cursor);

        Cursor steps_cursor = App.getContext().getContentResolver()
                .query(RecipeContentProvider.STEPS_URI,
                        null,null,null,null);
        ArrayList<StepsTable> stepsTables = StepsTable.fromStepsCursor(steps_cursor);

        //The returning method converts the multiple arraylist into one single
        //arraylist of Recipe type.
        return Recipe.fromRecipeDB(recipeTables,ingredientsTables,stepsTables);
    }
}