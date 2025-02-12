package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JavaFxDemo.Question;

public class DatabaseHelperClass {

	private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=DBMSProject2;encrypt=false";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "AfifaDatabasePassword";

    public static List<Question> fetchQuestionsFromDatabase(String category) {
        String query = "SELECT q.questionID, q.questionText, ao.answerText, ao.isCorrect " +
                        "FROM Questions q " +
                        "INNER JOIN Quiz z ON q.quizID = z.quizID " +
                        "INNER JOIN AnswerOption ao ON q.questionID = ao.questionID " +
                        "WHERE z.quizTitle = ?";

        List<Question> questions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();

            // Process results
            int currentQuestionID = -1;
            Question currentQuestion = null;

            while (rs.next()) {
                int questionID = rs.getInt("questionID");
                String questionText = rs.getString("questionText");
                String answerText = rs.getString("answerText");
                boolean isCorrect = rs.getBoolean("isCorrect");

                if (currentQuestionID != questionID) {
                    currentQuestionID = questionID;
                    currentQuestion = new Question(questionText, "");
                    questions.add(currentQuestion);
                }

                // Add option to the current question
                currentQuestion.addOption(answerText, isCorrect);
                if (isCorrect) {
                    currentQuestion.correctAnswer = answerText;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching questions: " + e.getMessage());
        }
        return questions;
    }
}