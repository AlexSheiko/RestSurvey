package sheyko.aleksey.restsurvey.provider;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

@ParseClassName("Question")
public class Question extends ParseObject {

    public String getTitle() {
        return getString("title");
    }

    public List<String> getAnswers() {
        return getList("answers");
    }

    public static ParseQuery<Question> getQuery() {
        return ParseQuery.getQuery(Question.class);
    }
}
