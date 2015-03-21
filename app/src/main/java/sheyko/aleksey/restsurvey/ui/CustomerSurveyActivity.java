package sheyko.aleksey.restsurvey.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sheyko.aleksey.restsurvey.BaseActivityFullscreen;
import sheyko.aleksey.restsurvey.R;
import sheyko.aleksey.restsurvey.provider.QuestionDataSource;
import sheyko.aleksey.restsurvey.ui.CustomerSurveyFragment.OnAnswerSelectedListener;


public class CustomerSurveyActivity extends BaseActivityFullscreen
        implements OnAnswerSelectedListener {

    private ViewPager mPager;
    private QuestionDataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
                LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customer_survey);

        ActionBar bar = getActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(false);
            bar.setDisplayShowCustomEnabled(true);
            bar.setCustomView(R.layout.ab_survey);
        }

        mPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(
                getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

        ParseObject session = new ParseObject("Session");
        session.saveEventually();
    }

    public void getCurrentSession(GetCallback<ParseObject> callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Session");
        query.orderByDescending("createdAt");
        query.getFirstInBackground(callback);
    }

    @Override public void onAnswerSelected() {
        TextView barTitle = (TextView) findViewById(R.id.ab_title);
        barTitle.setText(String.format(
                "%s questions left",
                mDataSource.getCount() - mPager.getCurrentItem()));

        navigateToNextPage();
    }

    private void navigateToNextPage() {
        if (mPager.getCurrentItem() < mDataSource.getCount() - 1) {
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        } else {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Session");
            query.orderByDescending("createdAt");
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override public void done(ParseObject session, ParseException e) {
                    if (e == null) {
                        Map<String, String> dimensions = new HashMap<>();

                        List<List<String>> answers = session.getList("answers");
                        for (List<String> answer : answers) {
                            dimensions.put(answer.get(0), answer.get(1));
                        }
                        ParseAnalytics.trackEventInBackground("Answers", dimensions);
                    }
                }
            });
            startActivity(new Intent(this, CustomerFinishActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            getCurrentSession(new GetCallback<ParseObject>() {
                @Override public void done(ParseObject session, ParseException e) {
                    if (session.getList("answers") == null) {
                        session.deleteEventually();
                    }
                }
            });
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            mDataSource = new QuestionDataSource();
        }

        @Override
        public Fragment getItem(int position) {
            return CustomerSurveyFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return mDataSource.getCount();
        }
    }

    public int getCurrentPage() {
        return mPager.getCurrentItem();
    }
}
