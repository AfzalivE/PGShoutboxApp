package com.afzaln.pgshoutbox.data.source.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by afzal on 2016-11-19.
 */
public class PreferenceProvider {
    private static PreferenceProvider INSTANCE;
    private final SharedPreferences preferences;

    private PreferenceProvider(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferenceProvider getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PreferenceProvider(context);
        }

        return INSTANCE;
    }
}
