package sheyko.aleksey.restsurvey.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sheyko.aleksey.restsurvey.R;
import sheyko.aleksey.restsurvey.provider.Question;


public class CustomerSurveyFragment extends Fragment
        implements OnClickListener {

    private static final String TAG = CustomerSurveyFragment.class.getSimpleName();

    private List<Question> mQuestions = new ArrayList<>();
    private String mCurrentQuestion;

    OnAnswerSelectedListener mCallback;

    public interface OnAnswerSelectedListener {
        public void onAnswerSelected();
    }

    public static CustomerSurveyFragment newInstance(int page) {
        CustomerSurveyFragment f = new CustomerSurveyFragment();

        Bundle args = new Bundle();
        args.putInt("page", page);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnAnswerSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAnswerSelectedListener");
        }
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseQuery<Question> query = Question.getQuery();
        query.fromLocalDatastore();
        try {
            mQuestions = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextView barTitle = (TextView) getActivity()
                .findViewById(R.id.ab_title);

        barTitle.setText(String.format(
                "%s questions left",
                mQuestions.size() - 1));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_survey, container, false);

        int page = getArguments().getInt("page");
        mCurrentQuestion = mQuestions.get(page).getTitle();

        TextView questionTextView = (TextView) rootView.findViewById(R.id.questionTextView);
        if (!mCurrentQuestion.trim().isEmpty()) {
            questionTextView.setText(mCurrentQuestion);
        } else {
            Log.i(TAG, String.format(
                    "There's no questions left for page %d", page));
            questionTextView.setText(R.string.unspecified_question);
        }

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        boolean isDark = !sp.getBoolean("dark_theme", false);

        if (isDark) {
            rootView.findViewById(R.id.container).setBackgroundColor(
                    getResources().getColor(R.color.window_bg_dark));
            ((TextView) rootView.findViewById(R.id.questionTextView)).setTextColor(
                    Color.parseColor("#c2ffffff"));

            Button firstButton = (Button) rootView.findViewById(R.id.firstButton);
            firstButton.setBackgroundResource(R.drawable.button_choice_dark);
            firstButton.setTextColor(
                    Color.parseColor("#c2ffffff"));

            Button secondButton = (Button) rootView.findViewById(R.id.secondButton);
            secondButton.setBackgroundResource(R.drawable.button_choice_dark);
            secondButton.setTextColor(
                    Color.parseColor("#c2ffffff"));

            Button thirdButton = (Button) rootView.findViewById(R.id.thirdButton);
            thirdButton.setBackgroundResource(R.drawable.button_choice_dark);
            thirdButton.setTextColor(
                    Color.parseColor("#c2ffffff"));

            Button fourthButton = (Button) rootView.findViewById(R.id.fourthButton);
            fourthButton.setBackgroundResource(R.drawable.button_choice_dark);
            fourthButton.setTextColor(
                    Color.parseColor("#c2ffffff"));
        }

        if (mQuestions.get(page).getAnswers().size() > 0) {
            Button firstButton = (Button) rootView.findViewById(R.id.firstButton);
            firstButton.setVisibility(View.VISIBLE);
            firstButton.setText(mQuestions.get(page).getAnswers().get(0) + "");
        }

        if (mQuestions.get(page).getAnswers().size() > 1) {
            Button secondButton = (Button) rootView.findViewById(R.id.secondButton);
            secondButton.setVisibility(View.VISIBLE);
            secondButton.setText(mQuestions.get(page).getAnswers().get(1));
        }

        if (mQuestions.get(page).getAnswers().size() > 2) {
            Button thirdButton = (Button) rootView.findViewById(R.id.thirdButton);
            thirdButton.setVisibility(View.VISIBLE);
            thirdButton.setText(mQuestions.get(page).getAnswers().get(2));
        }

        if (mQuestions.get(page).getAnswers().size() > 3) {
            Button fourthButton = (Button) rootView.findViewById(R.id.fourthButton);
            fourthButton.setVisibility(View.VISIBLE);
            fourthButton.setText(mQuestions.get(page).getAnswers().get(3));
        }

        rootView.findViewById(R.id.firstButton).setOnClickListener(this);
        rootView.findViewById(R.id.secondButton).setOnClickListener(this);
        rootView.findViewById(R.id.thirdButton).setOnClickListener(this);

        return rootView;
    }

    @Override public void onClick(final View view) {
        ((CustomerSurveyActivity) getActivity())
                .getCurrentSession(new GetCallback<ParseObject>() {
                    @Override public void done(ParseObject session, ParseException e) {
                        session.add("answers", Arrays.asList(
                                mCurrentQuestion, ((Button) view).getText()));
                        session.saveEventually();
                    }
                });
        mCallback.onAnswerSelected();

        TextView barTitle = (TextView) getActivity()
                .findViewById(R.id.ab_title);

        int questionsLeft = mQuestions.size() - ((CustomerSurveyActivity)
                getActivity()).getCurrentPage() - 1;

        if (questionsLeft > 0) {
            barTitle.setText(String.format(
                    "%d questions left", questionsLeft));
        } else {
            barTitle.setText("Last question");
        }
    }
}
