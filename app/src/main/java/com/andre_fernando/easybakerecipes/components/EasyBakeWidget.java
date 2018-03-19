package com.andre_fernando.easybakerecipes.components;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.andre_fernando.easybakerecipes.R;
import com.andre_fernando.easybakerecipes.activities.OverviewActivity;
import com.andre_fernando.easybakerecipes.data_objects.Recipe;

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

        // Get the right recipe
        int Recipe_No = RecipeAPI.getLastSeenRecipeNo();
        ArrayList<Recipe> list = new ArrayList<>();
        try {
            list = new RecipeFromContentProvider().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Recipe recipe = list.get(Recipe_No);

        // Set recipe name and list view
        widget.setTextViewText(R.id.widget_recipe_name,recipe.getName());
        Intent lv_intent = new Intent(context,Widget_Service.class);
        widget.setRemoteAdapter(R.id.widget_ingredients_list,lv_intent);

        // OnClick recipe name to open the recipe
        Intent launch_recipe = new Intent(context, OverviewActivity.class);
        launch_recipe.putExtra("recipe",recipe);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(context,0,launch_recipe,PendingIntent.FLAG_UPDATE_CURRENT);
        widget.setOnClickPendingIntent(R.id.widget_recipe_name,pendingIntent);

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
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {    }

    @Override
    public void onDisabled(Context context) {    }
}

