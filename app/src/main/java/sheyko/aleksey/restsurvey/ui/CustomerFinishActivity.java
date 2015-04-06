package sheyko.aleksey.restsurvey.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import sheyko.aleksey.restsurvey.BaseActivityNoActionBar;
import sheyko.aleksey.restsurvey.R;


public class CustomerFinishActivity extends BaseActivityNoActionBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_finish);

        getSupportActionBar().hide();

        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(this);

        if (!sharedPrefs.getBoolean("dark_theme", false)) {
            (findViewById(R.id.container)).setBackgroundResource(R.color.window_bg_dark);
        }
    }
}
