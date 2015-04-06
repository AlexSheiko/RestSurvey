package sheyko.aleksey.restsurvey;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;

public class BaseActivityActionBar extends ActionBarActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        boolean mIsDark = !sp.getBoolean("dark_theme", false);
        if (mIsDark) {
            setTheme(R.style.AppTheme_Dark);
        }
    }
}
