package sheyko.aleksey.restsurvey;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import sheyko.aleksey.restsurvey.SurveyFragment.OnAnswerSelectedListener;
import sheyko.aleksey.restsurvey.provider.QuestionDataSource;


public class SurveyActivity extends FragmentActivity
        implements OnAnswerSelectedListener {

    private ViewPager mPager;
    private QuestionDataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_survey);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        ActionBar bar = getActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(false);
            bar.setDisplayShowCustomEnabled(true);
            bar.setCustomView(R.layout.ab_survey);
        }
        findViewById(R.id.arrow_back).setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                navigateToPreviousPage();
            }
        });
        findViewById(R.id.arrow_next).setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                navigateToNextPage();
            }
        });

        mPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(
                getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

        ParseObject session = new ParseObject("Session");
        session.saveInBackground();
    }

    @Override public void onAnswerSelected() {
        navigateToNextPage();
    }

    private void navigateToPreviousPage() {
        if (mPager.getCurrentItem() == 0) {
            finish();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private void navigateToNextPage() {
        if (mPager.getCurrentItem() < /* TODO: mDataSource.getCount() - 1*/ mDataSource.getCount() - 1) {
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        } else {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Session");
            query.orderByDescending("createdAt");
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override public void done(ParseObject session, ParseException e) {
                    // TODO: Uncomment before releasing
                    /*
                    if (e == null) {
                        Map<String, String> dimensions = new HashMap<>();

                        List<List<String>> answers = session.getList("answers");
                        for(List<String> answer : answers) {
                            dimensions.put(answer.get(0), answer.get(1));
                        }
                        ParseAnalytics.trackEventInBackground("Answers", dimensions);
                    }
                    */
                }
            });
            startActivity(new Intent(this, FinishActivity.class));
            finish();
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
            mDataSource = new QuestionDataSource();
        }

        @Override
        public Fragment getItem(int position) {
            return SurveyFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return mDataSource.getCount();
        }
    }
}
