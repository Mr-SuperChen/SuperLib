package superlib.cjt.co.openlibrary.utils;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * <pre>
 * wpt
 * </pre>
 */
public class LCSharedPreferencesHelper {

    public final static String TAG = "SharedPreferencesHelper";
    public final static String SHARED_PATH = "app_share";

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Context mContext;

    private static LCSharedPreferencesHelper sh;

    public static synchronized LCSharedPreferencesHelper instance(
            Context context, String pName) {
        if (sh == null) {
            sh = new LCSharedPreferencesHelper(context, pName);
        }
        return sh;
    }

    public LCSharedPreferencesHelper(Context context, String pName) {
        this.mContext = context;
        sp = mContext.getSharedPreferences(pName, 0);
        editor = sp.edit();
    }

    public void putValue(String key, String value) {
        editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getIntValue(String key) {
        return sp.getInt(key, 0);
    }

    public String getValue(String key) {
        return sp.getString(key, null);
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaults) {
        return sp.getBoolean(key, defaults);
    }

    public void moveValue(String key) {
        editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }
}
