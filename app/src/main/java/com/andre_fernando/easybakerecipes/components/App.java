package com.andre_fernando.easybakerecipes.components;

import android.app.Application;
import android.content.Context;

import com.andre_fernando.easybakerecipes.BuildConfig;

import java.lang.ref.WeakReference;

import timber.log.Timber;


public class App extends Application {
    private static WeakReference<Application> sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        App.sApplication = new WeakReference<Application>(this);

        if (BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree(){
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element)+":"+element.getLineNumber();
                }
            });
        }
    }

    public static Context getContext(){
        return sApplication.get().getBaseContext();
    }
}
