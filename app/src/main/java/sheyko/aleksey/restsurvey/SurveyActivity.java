package sheyko.aleksey.restsurvey;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.parse.ParseObject;

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
        if (mPager.getCurrentItem() < questions.length) {
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        } else {
            // TODO: Show finish activity
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
