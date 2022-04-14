package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MultipleChoiceQuestion extends Question {

    private List<String> incorrect_answers;

    @JsonCreator
    public MultipleChoiceQuestion(@JsonProperty("category") String category, @JsonProperty("type") String type,
                                  @JsonProperty("difficulty") String difficulty, @JsonProperty("question") String question,
                                  @JsonProperty("correct_answer") String correct_answer, @JsonProperty("incorrect_answers") List<String> incorrect_answers) {
        super(category, type, difficulty, question, correct_answer);
        this.incorrect_answers = incorrect_answers;
    }

    public List<String> getIncorrect_answers() {
        return incorrect_answers;
    }

    public void setIncorrect_answers(List<String> incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }
}
