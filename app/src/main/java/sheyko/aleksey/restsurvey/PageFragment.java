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

import static sheyko.aleksey.restsurvey.provider.Questions.questions;


public class PageFragment extends Fragment
        implements OnClickListener {

    private static final String TAG = PageFragment.class.getSimpleName();

    private String mCurrentQuestion;

    OnAnswerSelectedListener mCallback;

    public interface OnAnswerSelectedListener {
        public void onAnswerSelected();
    }

    public static PageFragment newInstance(int page) {
        PageFragment f = new PageFragment();

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);

        int page = getArguments().getInt("page");
        mCurrentQuestion = questions[page];

        TextView questionTextView = (TextView) rootView.findViewById(R.id.questionTextView);
        if (!mCurrentQuestion.trim().isEmpty()) {
            questionTextView.setText(mCurrentQuestion);
        } else {
            Log.i(TAG, String.format(
                    "There's no questions left for page %d", page));
            questionTextView.setText(R.string.unspecified_question);
        }

        rootView.findViewById(R.id.buttonGreat).setOnClickListener(this);
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
                    session.saveEventually();
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
