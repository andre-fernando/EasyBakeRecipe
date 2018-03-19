package com.andre_fernando.easybakerecipes.components;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;

import com.andre_fernando.easybakerecipes.R;

/**
 * This class handles button presses of the widget
 */
public class WidgetIntentService extends IntentService {
    public static final String ACTION_FORWARD = "com.andre_fernando.easybakerecipes.components.action.FORWARD";
    public static final String ACTION_BACK = "com.andre_fernando.easybakerecipes.components.action.BACK";

    public WidgetIntentService() {
        super("WidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FORWARD.equals(action)) {
                handleActionForward();
            } else if (ACTION_BACK.equals(action)) {
                handleActionBack();
            }
        }
    }

    /**
     * Changes the widget to the next recipe
     */
    private void handleActionForward() {
        RecipeAPI.IncreaseRecipeNo();
        updateAllWidgets();
    }

    /**
     * Changes the widget to the previous recipe
     */
    private void handleActionBack() {
        RecipeAPI.DecreaseRecipeNo();
        updateAllWidgets();
    }

    /**
     * updates all the widgets
     */
    private void updateAllWidgets(){
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = widgetManager.getAppWidgetIds(new ComponentName(this,EasyBakeWidget.class));
        for (int i: appWidgetIds){
            widgetManager.notifyAppWidgetViewDataChanged(i,R.id.widget_ingredients_list);
            EasyBakeWidget.updateAppWidget(this,widgetManager,i);
        }
    }
}
