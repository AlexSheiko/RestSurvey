package sheyko.aleksey.restsurvey.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import sheyko.aleksey.restsurvey.R;

public class AdminLoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        setContentView(R.layout.activity_admin_login);

        final EditText jobTitle = (EditText) findViewById(R.id.jobTitle);
        final EditText password = (EditText) findViewById(R.id.password);

        Button loginButton = (Button) findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                ParseUser user = new ParseUser();
                user.setUsername(jobTitle.getText().toString().trim());
                user.setPassword(password.getText().toString());
                user.put("pin", password.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            startActivity(new Intent(AdminLoginActivity.this,
                                    AdminPanelActivity.class));
                        } else {
                            // TODO: Check for existing job title, lack of
                            // number in pin code, etc.
                        }
                    }
                });
            }
        });
    }
}