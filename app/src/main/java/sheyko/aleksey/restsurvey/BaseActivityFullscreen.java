package sheyko.aleksey.restsurvey;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class BaseActivityFullscreen extends Activity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        boolean mIsDark = !sp.getBoolean("dark_theme", false);
        if (mIsDark) {
            setTheme(R.style.AppTheme_NoActionBar_Dark);
        }
    }
}
