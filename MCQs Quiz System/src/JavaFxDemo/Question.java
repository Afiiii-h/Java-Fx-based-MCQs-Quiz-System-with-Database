package JavaFxDemo;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String question;
    public String correctAnswer;
    private List<Option> options;

    public Question(String question, String correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.options = new ArrayList<>();
    }

    public void addOption(String answerText, boolean isCorrect) {
        options.add(new Option(answerText, isCorrect));
    }

    public String getQuestion() {
        return question;
    }

    public List<Option> getOptions() {
        return options;
    }

    public boolean isCorrect(String answer) {
        return correctAnswer.equals(answer);
    }

    class Option {
        private String answerText;
        private boolean isCorrect;

        public Option(String answerText, boolean isCorrect) {
            this.answerText = answerText;
            this.isCorrect = isCorrect;
        }

        public String getAnswerText() {
            return answerText;
        }

        public boolean isCorrect() {
            return isCorrect;
        }
    }
}