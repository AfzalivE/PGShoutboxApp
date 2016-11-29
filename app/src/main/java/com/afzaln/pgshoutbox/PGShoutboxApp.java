package com.afzaln.pgshoutbox;

import android.app.Application;

import timber.log.Timber;
import timber.log.Timber.DebugTree;

/**
 * Created by afzal on 2016-06-05.
 */
public class PGShoutboxApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            // For debug logging
            Timber.plant(new DebugTree());
        }
    }
}
