package coderschoolasignment.todoapp;

import android.content.Context;
import android.content.SharedPreferences;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by Administrator on 28/02/2016.
 */
public class SharedPreference {
    public static void WritetoSharePreference(Context context, String key, String value)
    {
        SharedPreferences prefs = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }



    public static String ReadFromSharedPreference(Context context,String key,String defaultValue)
    {
        SharedPreferences prefs = getDefaultSharedPreferences(context);
        String result = prefs.getString(key, defaultValue);
        return result;
    }


}
