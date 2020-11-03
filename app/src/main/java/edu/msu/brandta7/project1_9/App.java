package edu.msu.brandta7.project1_9;

import android.app.Application;
import android.content.Context;

/**
 * Class to have application keep a reference to itself in case a context is needed and can't be gotten elsewhere
 */
public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
