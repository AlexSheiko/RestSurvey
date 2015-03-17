package sheyko.aleksey.restsurvey;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import static sheyko.aleksey.restsurvey.provider.Questions.questions;


public class PageFragment extends Fragment
        implements OnClickListener {

    private static final String TAG = PageFragment.class.getSimpleName();

    public static PageFragment newInstance(int page) {
        PageFragment f = new PageFragment();

        Bundle args = new Bundle();
        args.putInt("page", page);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);

        int currentPage = getArguments().getInt("page");
        String question = questions[currentPage];

        TextView questionTextView = (TextView) rootView.findViewById(R.id.questionTextView);
        if (!question.trim().isEmpty()) {
            questionTextView.setText(question);
        } else {
            Log.i(TAG, String.format(
                    "There's no questions left for page %d", currentPage));
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
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override public void done(ParseObject currentSession, ParseException e) {
                if (e == null) {
                    switch (view.getId()) {
                        case R.id.buttonGreat:

                            break;
                        case R.id.buttonGood:

                            break;
                        case R.id.buttonOkay:

                            break;
                        case R.id.buttonBad:

                            break;
                    }
                }
            }
        });
    }
}
