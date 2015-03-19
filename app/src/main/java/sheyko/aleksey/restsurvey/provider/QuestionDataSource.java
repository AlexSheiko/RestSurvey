package sheyko.aleksey.restsurvey.provider;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class QuestionDataSource {
    private static final String TAG = QuestionDataSource.class.getSimpleName();

    private List<String> mQuestions;

    public QuestionDataSource() {
        mQuestions = new ArrayList<>();
        // TODO: Update questions from database
        // fillOutQuestionListFromLocalDatabase();
        mQuestions.add("Was the food prepared to your liking?");
        mQuestions.add("How highly you would like to recommend us to your friends?");
        mQuestions.add("How would you rate our service and personal?");
    }

    public List<String> getQuestions() {
        return mQuestions;
    }

    public int getCount() {
        return mQuestions.size();
    }

    public void fillOutQuestionListFromLocalDatabase() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Questions");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override public void done(List<ParseObject> questions, ParseException e) {
                if (e == null) {
                    if (questions.size() > 0) {
                        for (ParseObject entry : questions) {
                            String question = entry.getString("question");
                            mQuestions.add(question);
                        }
                    } else {
                        retrieveQuestionsFromNetwork(new FindCallback<ParseObject>() {
                            @Override public void done(List<ParseObject> questions, ParseException e) {
                                if (e == null) {
                                    if (questions.size() > 0) {
                                        for (ParseObject question : questions) {
                                            question.pinInBackground();
                                        }
                                        fillOutQuestionListFromLocalDatabase();
                                    } else {
                                        Log.w(TAG, "There's no questions available in database. " +
                                                "Please visit Parse.com and add a couple ones.");
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void retrieveQuestionsFromNetwork(FindCallback<ParseObject> callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Questions");
        query.findInBackground(callback);
    }
}