package sheyko.aleksey.restsurvey;

import android.app.Activity;
import android.os.Bundle;


public class QuizActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new QuizFragment())
                    .commit();
        }
    }
}
