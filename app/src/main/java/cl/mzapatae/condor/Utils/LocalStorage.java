package cl.mzapatae.condor.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/*
 * Created by MZapatae on 27-09-15.
 */
public class LocalStorage {
    private static final String _SHARED_PREFERENCE_FILE = "cl.mzapatae.condor.localStorage";

    private static final String KEY_USER_REGISTERED = "user_loggin";

    private static final String TWITTER_CONSUMER_KEY = "consumer_key";
    private static final String TWITTER_CONSUMER_SECRET = "consumer_secret";
    private static final String KEY_OAUTH_TOKEN = "oauth_token";
    private static final String KEY_OAUTH_SECRET = "oauth_token_secret";

    private static SharedPreferences _SHARED_PREFERENCE;
    private static SharedPreferences.Editor _PREFERENCE_EDITOR;

    public static void initLocalStorage(Context context) {
        _SHARED_PREFERENCE = context.getSharedPreferences(_SHARED_PREFERENCE_FILE, Activity.MODE_PRIVATE);
    }

    public static Boolean isLoggedInTwitter() {
        return _SHARED_PREFERENCE.getBoolean(KEY_USER_REGISTERED, false);
    }

    public static void setLoggedInTwitter() {
        _PREFERENCE_EDITOR = _SHARED_PREFERENCE.edit();
        _PREFERENCE_EDITOR.putBoolean(KEY_USER_REGISTERED, true);
        _PREFERENCE_EDITOR.apply();
    }

    public static String getConsumerKey() {
        return _SHARED_PREFERENCE.getString(TWITTER_CONSUMER_KEY, "");
    }

    public static void setConsumerKey(String consumerKey) {
        _PREFERENCE_EDITOR = _SHARED_PREFERENCE.edit();
        _PREFERENCE_EDITOR.putString(TWITTER_CONSUMER_KEY, consumerKey);
        _PREFERENCE_EDITOR.apply();
    }

    public static String getConsumerSecret() {
        return _SHARED_PREFERENCE.getString(TWITTER_CONSUMER_SECRET, "");
    }

    public static void setConsumerSecret(String consumerSecret) {
        _PREFERENCE_EDITOR = _SHARED_PREFERENCE.edit();
        _PREFERENCE_EDITOR.putString(TWITTER_CONSUMER_SECRET, consumerSecret);
        _PREFERENCE_EDITOR.apply();
    }

    public static String getOauthToken() {
        return _SHARED_PREFERENCE.getString(KEY_OAUTH_TOKEN, "");
    }

    public static void setOauthToken(String oauthToken) {
        _PREFERENCE_EDITOR = _SHARED_PREFERENCE.edit();
        _PREFERENCE_EDITOR.putString(KEY_OAUTH_TOKEN, oauthToken);
        _PREFERENCE_EDITOR.apply();
    }

    public static String getOauthSecret() {
        return _SHARED_PREFERENCE.getString(KEY_OAUTH_SECRET, "");
    }

    public static void setOauthSecret(String oauthKey) {
        _PREFERENCE_EDITOR = _SHARED_PREFERENCE.edit();
        _PREFERENCE_EDITOR.putString(KEY_OAUTH_SECRET, oauthKey);
        _PREFERENCE_EDITOR.apply();
    }
}