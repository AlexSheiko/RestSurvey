package sheyko.aleksey.restsurvey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sheyko.aleksey.restsurvey.PageFragment.OnAnswerSelectedListener;

import static sheyko.aleksey.restsurvey.provider.Questions.questions;


public class SurveyActivity extends FragmentActivity
        implements OnAnswerSelectedListener {

    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        mPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(
                getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

        ParseObject session = new ParseObject("Session");
        session.saveInBackground();
    }

    @Override public void onAnswerSelected() {
        if (mPager.getCurrentItem() < questions.length - 1) {
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        } else {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Session");
            query.orderByDescending("createdAt");
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override public void done(ParseObject session, ParseException e) {
                    if (e == null) {
                        Map<String, String> dimensions = new HashMap<>();

                        List<List<String>> answers = session.getList("answers");
                        for(List<String> answer : answers) {
                            dimensions.put(answer.get(0), answer.get(1));
                        }
                        ParseAnalytics.trackEventInBackground("Answers", dimensions);
                    }
                }
            });
            startActivity(new Intent(this, StartActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return questions.length;
        }
    }
}
