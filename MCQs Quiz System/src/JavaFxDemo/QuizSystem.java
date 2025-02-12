package JavaFxDemo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import Database.DatabaseHelperClass;

public class QuizSystem extends Application {

    private int currentQuestionIndex = 0;
    private int score = 0;
    boolean lastQuestionAnswered = false;
    private int userID;

    public void setUserID(int userID) {
        this.userID = userID;
    }
    private List<Question> questions = new ArrayList<>();
    private Label questionLabel = new Label();
    private ToggleGroup answerGroup = new ToggleGroup();
    RadioButton answer1 = new RadioButton();
    RadioButton answer2 = new RadioButton();
    RadioButton answer3 = new RadioButton();
    RadioButton answer4 = new RadioButton();

    @Override
    public void start(Stage primaryStage) {
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Math Quiz", "Java Quiz", "DBMS Quiz");
        categoryComboBox.setStyle("-fx-background-color: #A9B179; -fx-border-color: #C08769; -fx-text-fill: #FEFAE0;");

        Label categoryLabel = new Label("Select a category:");
        categoryLabel.setFont(Font.font("Gabriola", 22));
        categoryLabel.setStyle("-fx-text-fill: #3F4238;");

        Button startButton = new Button("Start Quiz");
        startButton.setFont(Font.font("Gabriola", 22));
        startButton.setStyle("-fx-background-color: #C08769; -fx-text-fill: #FEFAE0;");

        questionLabel = new Label();
        questionLabel.setFont(Font.font("Gabriola", 22));
        questionLabel.setStyle("-fx-text-fill: #3F4238;");

        answer1 = new RadioButton();
        answer1.setFont(Font.font("Gabriola", 22));
        answer1.setStyle("-fx-text-fill: #3F4238;");
        answer2 = new RadioButton();
        answer2.setFont(Font.font("Gabriola", 22));
        answer2.setStyle("-fx-text-fill: #3F4238;");
        answer3 = new RadioButton();
        answer3.setFont(Font.font("Gabriola", 22));
        answer3.setStyle("-fx-text-fill: #3F4238;");
        answer4 = new RadioButton();
        answer4.setFont(Font.font("Gabriola", 22));
        answer4.setStyle("-fx-text-fill: #3F4238;");

        answer1.setToggleGroup(answerGroup);
        answer2.setToggleGroup(answerGroup);
        answer3.setToggleGroup(answerGroup);
        answer4.setToggleGroup(answerGroup);

        Button nextButton = new Button("Next");
        nextButton.setFont(Font.font("Gabriola", 22));
        nextButton.setStyle("-fx-background-color: #C08769; -fx-text-fill: #FEFAE0;");

        Label scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.font("Gabriola", 26));
        scoreLabel.setStyle("-fx-text-fill: #6B793E;");

        startButton.setOnAction(e -> {
            String selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                try {
                    questions = DatabaseHelperClass.fetchQuestionsFromDatabase(selectedCategory);
                    if (questions.isEmpty()) {
                        System.out.println("No questions available for the selected category.");
                    } else {
                        currentQuestionIndex = 0;
                        score = 0;
                        updateQuestion();
                    }
                } catch (Exception ex) {
                    System.out.println("Error fetching questions: " + ex.getMessage());
                }
            } else {
                System.out.println("Please select a category.");
            }
        });
        
        nextButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent arg0) {
                if (currentQuestionIndex < questions.size()) {
                    Question currentQuestion = questions.get(currentQuestionIndex);
                    RadioButton selectedAnswer = (RadioButton) answerGroup.getSelectedToggle();
                    if (selectedAnswer != null) {
                        String userAnswer = selectedAnswer.getText();
                        if (!lastQuestionAnswered) {
                            if (currentQuestion.isCorrect(userAnswer)) {
                                score++;
                                scoreLabel.setText("Score: " + score);
                            }
                        }
                        if (currentQuestionIndex < questions.size() - 1) {
                            currentQuestionIndex++;
                            updateQuestion();
                            answerGroup.selectToggle(null);
                        } else {
                            lastQuestionAnswered = true;
                            primaryStage.hide();
                            showResult();
                        }
                    }
                }
            }
        });
        
        VBox layout = new VBox(20, categoryLabel, categoryComboBox, startButton, questionLabel, answer1, answer2, answer3, answer4, nextButton, scoreLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #FEFAE0;");


        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setTitle("Quiz System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateQuestion() {
        if (questions.isEmpty()) {
            System.out.println("No questions available.");
            return;
        }
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionLabel.setText(currentQuestion.getQuestion());

            // Clear existing options
            answer1.setText("");
            answer2.setText("");
            answer3.setText("");
            answer4.setText("");

            // Populate options
            int optionIndex = 0;
            for (Question.Option option : currentQuestion.getOptions()) {
                if (optionIndex == 0) {
                    answer1.setText(option.getAnswerText());
                } else if (optionIndex == 1) {
                    answer2.setText(option.getAnswerText());
                } else if (optionIndex == 2) {
                    answer3.setText(option.getAnswerText());
                } else if (optionIndex == 3) {
                    answer4.setText(option.getAnswerText());
                }
                optionIndex++;
            }
        } else {
            showResult();
        }
    }
    

    private void showResult() {
        Alert resultAlert = new Alert(Alert.AlertType.INFORMATION);
        resultAlert.setTitle("Quiz Result");
        resultAlert.setHeaderText(null);
        resultAlert.setContentText("Your score is: " + score + " out of " + questions.size());

        resultAlert.getDialogPane().setStyle("-fx-background-color: #FEFAE0;");
        resultAlert.getDialogPane().setStyle(resultAlert.getDialogPane().getStyle() + "-fx-font-size: 22; -fx-font-family: 'Gabriola';");

        resultAlert.getDialogPane().lookupButton(ButtonType.OK).setStyle("-fx-background-color: #C08769; -fx-text-fill: #FEFAE0; -fx-font-size: 22;");

        resultAlert.showAndWait();

        // Save score to database
        LoginSystem loginSystem = new LoginSystem();
        loginSystem.saveScore(userID,score);
    }

    public void startQuiz(String category) {
    }
}