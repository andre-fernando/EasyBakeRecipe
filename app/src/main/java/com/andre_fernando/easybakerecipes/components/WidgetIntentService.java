package com.andre_fernando.easybakerecipes.components;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class WidgetIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
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


    private void handleActionForward() {

        RecipeAPI.IncreaseRecipeNo();
        updateAllWidgets();
    }


    private void handleActionBack() {
        RecipeAPI.DecreaseRecipeNo();
        updateAllWidgets();
    }

    void updateAllWidgets(){
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = widgetManager.getAppWidgetIds(new ComponentName(this,EasyBakeWidget.class));
        for (int i: appWidgetIds){
            EasyBakeWidget.updateAppWidget(this,widgetManager,i);
        }
    }
}
