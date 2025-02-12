package JavaFxDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

public class CreateUserWindow extends Application {

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=DBMSProject2;encrypt=false";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "AfifaDatabasePassword";

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

        Label emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font("Gabriola", 22));
        emailLabel.setStyle("-fx-text-fill: #3F4238;");

        TextField emailField = new TextField();
        emailField.setFont(Font.font("Gabriola", 22));
        emailField.setStyle("-fx-background-color: #A9B179; -fx-border-color: #C08769; -fx-text-fill: #FEFAE0;");

        Button createUserButton = new Button("Create User");
        createUserButton.setFont(Font.font("Gabriola", 22));
        createUserButton.setStyle("-fx-background-color: #C08769; -fx-text-fill: #FEFAE0;");

        createUserButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();

            if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
                createUser(username, password, email);
                primaryStage.close();
            } else {
                System.out.println("Please fill in all fields.");
            }
        });

        VBox layout = new VBox(20, usernameLabel, usernameField, passwordLabel, passwordField, emailLabel, emailField, createUserButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #FEFAE0;");

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setTitle("Create User");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createUser(String username, String password, String email) {
        String query = "INSERT INTO Users (username, passwordHash, email) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
            System.out.println("User created successfully!");
        } catch (SQLException e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}