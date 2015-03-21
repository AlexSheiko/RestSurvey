package sheyko.aleksey.restsurvey.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.ParseUser;

public class AdminDispatchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, AdminPanelActivity.class));
        } else {
            startActivity(new Intent(this, AdminLoginActivity.class));
        }
    }
}
