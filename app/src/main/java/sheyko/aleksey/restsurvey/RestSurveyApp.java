package sheyko.aleksey.restsurvey;

import com.parse.Parse;
import com.parse.ParsePush;

public class RestSurveyApp extends android.app.Application {

    @Override public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "cvwSNlSuCvUWvOP9RYXtPhWZR3Bm69xgT979VZk3",
                "S72yDeO7sVS96p9IRjZzmeE9sy6WwxVhZsdn2sFQ");

        ParsePush.subscribeInBackground("");
    }
}
