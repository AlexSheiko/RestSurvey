package sheyko.aleksey.restsurvey.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
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
        final EditText pin = (EditText) findViewById(R.id.password);

        Button loginButton = (Button) findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                final ParseUser user = new ParseUser();
                final String username = jobTitle.getText().toString().trim();
                final String password = pin.getText().toString();

                user.setUsername(username);
                user.setPassword(password);
                user.put("pin", password);

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            startActivity(new Intent(AdminLoginActivity.this,
                                    AdminPanelActivity.class));
                        } else {
                            if (e.getCode() == 202); {
                                ParseUser.logInInBackground(username, password, new LogInCallback() {
                                    @Override public void done(ParseUser parseUser, ParseException e) {
                                        if (e == null) {
                                            startActivity(new Intent(AdminLoginActivity.this,
                                                    AdminPanelActivity.class));
                                        } else {
                                            Toast.makeText(AdminLoginActivity.this,
                                                    "Failed to login: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
    }
}