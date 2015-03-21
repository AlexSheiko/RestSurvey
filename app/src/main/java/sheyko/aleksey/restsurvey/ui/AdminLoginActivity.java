package sheyko.aleksey.restsurvey.ui;

import android.app.Activity;
import android.os.Bundle;

import sheyko.aleksey.restsurvey.R;

public class AdminLoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        setContentView(R.layout.activity_admin_login);
    }
}