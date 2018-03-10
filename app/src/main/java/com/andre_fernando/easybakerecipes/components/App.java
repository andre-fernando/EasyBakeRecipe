package com.andre_fernando.easybakerecipes.components;

import android.app.Application;
import android.content.Context;

import com.andre_fernando.easybakerecipes.BuildConfig;

import timber.log.Timber;


public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        App.context=getApplicationContext();

        if (BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree(){
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element)+":"+element.getLineNumber();
                }
            });
        }
    }

    public static Context getAppContext(){
        return App.context;
    }
}
