package sheyko.aleksey.restsurvey.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import sheyko.aleksey.restsurvey.R;

public class AdminPanelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        Button b = (Button) findViewById(R.id.startButton);
        b.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(new Intent(AdminPanelActivity.this,
                        CustomerStartActivity.class));
            }
        });
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
//                BroadcastReceiver r = new BroadcastReceiver() {
//                    @Override public void onReceive(Context context, Intent intent) {
//                        Toast.makeText(AdminActivity.this, "Please keep the app opened", Toast.LENGTH_SHORT).show();
//                    }
//                };
//                IntentFilter filter = new IntentFilter();
//                filter.addCategory("android.intent.category.HOME");
//                registerReceiver(r, filter);
                break;
            case R.id.action_switch_theme:
                // TODO: Switch between light and dark themes
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
