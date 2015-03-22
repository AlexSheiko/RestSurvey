package sheyko.aleksey.restsurvey.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.List;

import sheyko.aleksey.restsurvey.BaseActivityActionBar;
import sheyko.aleksey.restsurvey.R;

public class AdminPanelActivity extends BaseActivityActionBar {

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        ParseUser user = ParseUser.getCurrentUser();
        if (user == null) return;

        mPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        TextView signedAsLabel = (TextView) findViewById(R.id.signedAsLabel);
        signedAsLabel.setText(String.format("Signed in as\n%s", user.getUsername()));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Session");
        query.whereGreaterThan("createdAt", cal.getTime());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> sessionList, ParseException e) {
                if (e == null) {
                    TextView feedbacksCount = (TextView) findViewById(R.id.feedbacksCount);
                    Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                            AdminPanelActivity.this, android.R.anim.fade_in);
                    feedbacksCount.startAnimation(hyperspaceJumpAnimation);
                    feedbacksCount.setText(sessionList.size() + "");
                }
            }
        });


        Button b = (Button) findViewById(R.id.startButton);
        b.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(new Intent(AdminPanelActivity.this,
                        CustomerStartActivity.class));
            }
        });
        if (!mPreferences.getBoolean("dark_theme", false)) {
            b.setBackground(getResources().getDrawable(
                    R.drawable.button_start_dark));
        }
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
                    item.setIcon(R.drawable.ic_theme_light);
                } else {
                    item.setIcon(R.drawable.ic_theme_dark);
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
                boolean lockOn = !(mPreferences.getBoolean("lock_app", false));
                mPreferences.edit().putBoolean(
                        "lock_app", lockOn).apply();
                if (lockOn) {
                    Toast.makeText(this, "App lock enabled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "App lock disabled", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_theme:
                boolean isDark = !(mPreferences.getBoolean("dark_theme", false));
                mPreferences.edit().putBoolean(
                        "dark_theme", isDark).apply();
                Intent intent = new Intent(this, ChangeThemeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
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
