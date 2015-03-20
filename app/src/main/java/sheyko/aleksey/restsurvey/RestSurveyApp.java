package sheyko.aleksey.restsurvey;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;

public class RestSurveyApp extends android.app.Application {

    @Override public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "cvwSNlSuCvUWvOP9RYXtPhWZR3Bm69xgT979VZk3",
                "S72yDeO7sVS96p9IRjZzmeE9sy6WwxVhZsdn2sFQ");

        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }
}
