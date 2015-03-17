package sheyko.aleksey.restsurvey;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static sheyko.aleksey.restsurvey.provider.Questions.questions;


public class QuizFragment extends Fragment {

    public static QuizFragment newInstance(int page) {
        QuizFragment f = new QuizFragment();

        Bundle args = new Bundle();
        args.putInt("page", page);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);

        int currentPage = getArguments().getInt("page");
        String question = questions[currentPage];

        TextView questionTextView = (TextView) rootView.findViewById(R.id.questionTextView);
        questionTextView.setText(question);
        return rootView;
    }
}
