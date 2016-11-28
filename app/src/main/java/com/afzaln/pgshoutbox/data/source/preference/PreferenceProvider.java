package com.afzaln.pgshoutbox.data.source.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import timber.log.Timber;

/**
 * Created by afzal on 2016-11-19.
 */
public class PreferenceProvider {
    private static final String KEY_SECURITY_TOKEN = "security-token";
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

    public String getSecurityToken() {
        return preferences.getString(KEY_SECURITY_TOKEN, "");
    }

    public void saveSecurityToken(String securityToken) {
        if (securityToken.isEmpty()) {
            Timber.e("Security token is empty");
        }
        preferences.edit().putString(KEY_SECURITY_TOKEN, securityToken).commit();
    }
}
