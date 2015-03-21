package sheyko.aleksey.restsurvey;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;

public abstract class BaseActivity extends Activity {

    @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        if (preferences.getBoolean("dark_theme", false)) {
            setTheme(R.style.AppTheme_Light);
        } else {
            setTheme(R.style.AppTheme_Dark);
        }
        super.onCreate(savedInstanceState, persistentState);
    }
}
