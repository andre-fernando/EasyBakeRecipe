package com.andre_fernando.easybakerecipes.components;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.andre_fernando.easybakerecipes.R;
import com.andre_fernando.easybakerecipes.activities.OverviewActivity;
import com.andre_fernando.easybakerecipes.data_objects.Ingredients;
import com.andre_fernando.easybakerecipes.data_objects.Recipe;
import com.andre_fernando.easybakerecipes.db.IngredientsTable;
import com.andre_fernando.easybakerecipes.db.RecipeContentProvider;
import com.andre_fernando.easybakerecipes.db.RecipeTable;
import com.andre_fernando.easybakerecipes.db.StepsTable;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of App Widget functionality.
 */
public class EasyBakeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.easy_bake_widget);

        int Recipe_No = RecipeAPI.getLastSeenRecipeNo();

        ArrayList<Recipe> list = new ArrayList<>();
        try {
            list = new AsyncWidgetRequest().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Recipe recipe = list.get(Recipe_No);

        widget.setTextViewText(R.id.widget_recipe_name,recipe.getName());

        Intent lv_intent = new Intent(context,Widget_Service.class);

        ArrayList<Ingredients> ingredients_list = recipe.getIngredients();
        ArrayList<String> ingredient_name = new ArrayList<>();
        ArrayList<String> ingredient_quantity = new ArrayList<>();
        for (Ingredients i: ingredients_list) {
            ingredient_name.add(i.getIngredient());
            ingredient_quantity.add(i.getQuantityWithMeasure());
        }

        lv_intent.putStringArrayListExtra("name",ingredient_name);
        lv_intent.putStringArrayListExtra("quantity",ingredient_quantity);
        widget.setRemoteAdapter(R.id.widget_ingredients_list,lv_intent);




        Intent launch_recipe = new Intent(context, OverviewActivity.class);
        launch_recipe.putExtra("recipe",recipe);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(context,0,launch_recipe,PendingIntent.FLAG_UPDATE_CURRENT);
        widget.setOnClickPendingIntent(R.id.widget_recipe_name,pendingIntent);
        //widget.setOnClickPendingIntent(R.id.lv_widget_ingredients,pendingIntent);

        //Forward button functionality
        Intent intent_forward = new Intent(context,WidgetIntentService.class);
        intent_forward.setAction(WidgetIntentService.ACTION_FORWARD);
        PendingIntent pending_forward = PendingIntent
                .getService(context,0,intent_forward,PendingIntent.FLAG_UPDATE_CURRENT);
        widget.setOnClickPendingIntent(R.id.widget_forward_button,pending_forward);

        //Back button functionality
        Intent intent_back = new Intent(context,WidgetIntentService.class);
        intent_back.setAction(WidgetIntentService.ACTION_BACK);
        PendingIntent pending_back = PendingIntent
                .getService(context,0,intent_back,PendingIntent.FLAG_UPDATE_CURRENT);
        widget.setOnClickPendingIntent(R.id.widget_back_button,pending_back);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, widget);


        appWidgetManager.notify();

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static class AsyncWidgetRequest extends AsyncTask<Void, Void, ArrayList<Recipe>>{

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

            return Recipe.fromRecipeDB(recipeTables,ingredientsTables,stepsTables);
        }
    }

}

