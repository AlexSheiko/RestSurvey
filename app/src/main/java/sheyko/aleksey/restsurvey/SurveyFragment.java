package sheyko.aleksey.restsurvey;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Arrays;

import sheyko.aleksey.restsurvey.provider.QuestionDataSource;


public class SurveyFragment extends Fragment
        implements OnClickListener {

    private static final String TAG = SurveyFragment.class.getSimpleName();

    private QuestionDataSource mDataSource;
    private String mCurrentQuestion;

    OnAnswerSelectedListener mCallback;

    public interface OnAnswerSelectedListener {
        public void onAnswerSelected();
    }

    public static SurveyFragment newInstance(int page) {
        SurveyFragment f = new SurveyFragment();

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
        mDataSource = new QuestionDataSource();

        TextView barTitle = (TextView) getActivity()
                .findViewById(R.id.ab_title);

        int currentPage = getArguments().getInt("page");
        if (currentPage < mDataSource.getCount()) {
            barTitle.setText(String.format("%s questions left",
                    mDataSource.getCount() - currentPage));
        } else {
            barTitle.setText("Last question");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_survey, container, false);

        int page = getArguments().getInt("page");
        mCurrentQuestion = mDataSource.getQuestions().get(page);

        TextView questionTextView = (TextView) rootView.findViewById(R.id.questionTextView);
        if (!mCurrentQuestion.trim().isEmpty()) {
            questionTextView.setText(mCurrentQuestion);
        } else {
            Log.i(TAG, String.format(
                    "There's no questions left for page %d", page));
            questionTextView.setText(R.string.unspecified_question);
        }

        rootView.findViewById(R.id.buttonGood).setOnClickListener(this);
        rootView.findViewById(R.id.buttonOkay).setOnClickListener(this);
        rootView.findViewById(R.id.buttonBad).setOnClickListener(this);

        return rootView;
    }

    @Override public void onClick(final View view) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Session");
        query.orderByDescending("createdAt");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override public void done(ParseObject session, ParseException e) {
                if (e == null) {
                    session.add("answers", Arrays.asList(
                            mCurrentQuestion, ((Button) view).getText()));
                    // TODO: Uncomment before releasing
                    // session.saveEventually();
                    mCallback.onAnswerSelected();
                } else {
                    Toast.makeText(getActivity(),
                            "Please check your network connection",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
