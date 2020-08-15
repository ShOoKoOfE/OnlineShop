package project.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import project.models.User;

public class PreferencesHelper {
    public static final String USERNAME_KEY = "username_key";
    public static final String PASSWORD_KEY = "password_key";

    public static void addSharedPreferences(User user, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERNAME_KEY, user.getUserUsername());
        editor.putString(PASSWORD_KEY, user.getUserPassword());
        editor.apply();
    }

    public static void removeSharedPreferences(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(USERNAME_KEY);
        editor.remove(PASSWORD_KEY);
        editor.apply();
    }

    public static String getPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
}
