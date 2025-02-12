package JavaFxDemo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.*;

public class LoginSystem extends Application {

private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=DBMSProject2;encrypt=false";
private static final String DB_USER = "sa";
private static final String DB_PASSWORD = "AfifaDatabasePassword";
private boolean isAuthenticated = false;
private int userID;

@Override
public void start(Stage primaryStage) {
    Label usernameLabel = new Label("Username:");
    usernameLabel.setFont(Font.font("Gabriola", 22));
    usernameLabel.setStyle("-fx-text-fill: #3F4238;");

    TextField usernameField = new TextField();
    usernameField.setFont(Font.font("Gabriola", 22));
    usernameField.setStyle("-fx-background-color: #A9B179; -fx-border-color: #C08769; -fx-text-fill: #FEFAE0;");

    Label passwordLabel = new Label("Password:");
    passwordLabel.setFont(Font.font("Gabriola", 22));
    passwordLabel.setStyle("-fx-text-fill: #3F4238;");

    PasswordField passwordField = new PasswordField();
    passwordField.setFont(Font.font("Gabriola", 22));
    passwordField.setStyle("-fx-background-color: #A9B179; -fx-border-color: #C08769; -fx-text-fill: #FEFAE0;");

    Button loginButton = new Button("Login");
    loginButton.setFont(Font.font("Gabriola", 22));
    loginButton.setStyle("-fx-background-color: #C08769; -fx-text-fill: #FEFAE0;");
    
    Button createUserButton = new Button("Create User");
    createUserButton.setFont(Font.font("Gabriola", 22));
    createUserButton.setStyle("-fx-background-color: #C08769; -fx-text-fill: #FEFAE0;");

    Label messageLabel = new Label();
    messageLabel.setFont(Font.font("Gabriola", 22));
    messageLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #6F7863;");
    
    createUserButton.setOnAction(e -> {
        Stage createUserStage = new Stage();
        CreateUserWindow createUserWindow = new CreateUserWindow();
        createUserWindow.start(createUserStage);
    });

    loginButton.setOnAction(e -> {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (authenticateUser(username, password)) {
            messageLabel.setText("Login Successful!");
            QuizSystem quizSystem = new QuizSystem();
            quizSystem.setUserID(userID); // Pass the userID to the QuizSystem class
            quizSystem.start(new Stage());
            primaryStage.close();
        } else {
            messageLabel.setText("Invalid credentials. Try again.");
        }
    });
    
  
    VBox layout = new VBox(20, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, createUserButton, messageLabel);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(20));
    layout.setStyle("-fx-background-color: #FEFAE0;");

    Scene scene = new Scene(layout, 600, 400);
    primaryStage.setTitle("Login System");
    primaryStage.setScene(scene);
    primaryStage.show();
}

private boolean authenticateUser(String username, String password) {
    String query = "SELECT userID FROM Users WHERE username = ? AND passwordHash = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            userID = rs.getInt("userID");
            System.out.println("Authenticated user with ID: " + userID);
            isAuthenticated = true;
        }
    } catch (SQLException e) {
        System.out.println("Database error: " + e.getMessage());
    }
    return isAuthenticated;
}

public void saveScore(int userID, int score) {
    String query = "UPDATE Users SET lastScore = ? WHERE userID = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, score);
        pstmt.setInt(2, userID);
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected == 0) {
            System.out.println("Score not saved. userID not found.");
        } else {
            System.out.println("Score saved successfully!");
        }
    } catch (SQLException e) {
        System.out.println("Error saving score: " + e.getMessage());
    }
}


public static void main(String[] args) {
    launch(args);
}

}