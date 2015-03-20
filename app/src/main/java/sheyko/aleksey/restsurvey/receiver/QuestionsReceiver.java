package sheyko.aleksey.restsurvey.receiver;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

public class QuestionsReceiver extends ParsePushBroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i("QuestionsReceiver", "onReceive");
    }

    @Override protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);
        String data = intent.getStringExtra("com.parse.data");
        Log.i("QuestionsReceiver", String.format(
                "Push notification message: %s", data));
    }

    @Override protected Notification getNotification(Context context, Intent intent) {
        String data = intent.getStringExtra("com.parse.data");
        Log.i("QuestionsReceiver", String.format(
                "Push notification message: %s", data));
        return super.getNotification(context, intent);
    }
}
