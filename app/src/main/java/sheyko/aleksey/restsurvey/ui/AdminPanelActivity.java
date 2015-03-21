package sheyko.aleksey.restsurvey.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import sheyko.aleksey.restsurvey.BaseActivity;
import sheyko.aleksey.restsurvey.R;

public class AdminPanelActivity extends BaseActivity {

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        ParseUser user = ParseUser.getCurrentUser();

        TextView signedAsLabel = (TextView) findViewById(R.id.signedAsLabel);
        signedAsLabel.setText(String.format("Signed in as\n%s", user.getUsername()));

        Button b = (Button) findViewById(R.id.startButton);
        b.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(new Intent(AdminPanelActivity.this,
                        CustomerStartActivity.class));
            }
        });

        mPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
    }

    @Override public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem lock = menu.findItem(R.id.action_lock);
        updateMenuItemAppearance(lock);
        MenuItem theme = menu.findItem(R.id.action_theme);
        updateMenuItemAppearance(theme);
        return super.onPrepareOptionsMenu(menu);
    }

    private void updateMenuItemAppearance(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_lock:
                boolean lockOn = mPreferences.getBoolean(
                        "lock_app", false);
                item.setChecked(lockOn);
                if (lockOn) {
                    item.setIcon(R.drawable.ic_lock_on);
                } else {
                    item.setIcon(R.drawable.ic_lock_off);
                }
                break;
            case R.id.action_theme:
                boolean isDark = mPreferences.getBoolean(
                        "dark_theme", false);
                item.setChecked(isDark);
                if (isDark) {
                    item.setIcon(R.drawable.ic_theme_dark);
                } else {
                    item.setIcon(R.drawable.ic_theme_light);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin_panel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_lock:
                // TODO: Add HOME intent-filter to CustomerStartActivity
                boolean lockOn = !(mPreferences.getBoolean("lock_app", false));
                mPreferences.edit().putBoolean(
                        "lock_app", lockOn).apply();
                if (lockOn) {
                    Toast.makeText(this, "Lock app enabled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Lock app disabled", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_theme:
                recreate();
                boolean isDark = !(mPreferences.getBoolean("dark_theme", false));
                mPreferences.edit().putBoolean(
                        "dark_theme", isDark).apply();
                break;
            case R.id.action_logout:
                ParseUser.logOut();
                startActivity(new Intent(this, AdminLoginActivity.class));
                break;
        }
        updateMenuItemAppearance(item);
        return super.onOptionsItemSelected(item);
    }
}
