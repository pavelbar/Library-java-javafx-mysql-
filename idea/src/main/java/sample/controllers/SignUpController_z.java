package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Handlers.DatabaseHandler;
import sample.User_z;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class SignUpController_z {

    @FXML
    private Button exit_button;

    @FXML
    private Button su_button;

    @FXML
    private TextField su_first_name;

    @FXML
    private TextField su_last_name;

    @FXML
    private TextField su_phone_number;

    @FXML
    private TextField su_user_name;

    @FXML
    private TextField su_password;

    @FXML
    private RadioButton su_dzr;

    @FXML
    private RadioButton su_nn;

    @FXML
    private Label exist_label;

    @FXML
    void initialize() {
        //try register
        su_button.setOnAction(actionEvent -> {
            if (su_first_name.getText().trim().isEmpty() || su_last_name.getText().trim().isEmpty() ||
                    su_user_name.getText().trim().isEmpty() || su_password.getText().trim().isEmpty() ||
                    su_phone_number.getText().trim().isEmpty()) {
                    exist_label.setTextFill(Color.color(1, 1, 1));
                    exist_label.setText("Empty fields");
                    System.out.println("Empty fields");
            } else {
                try {
                    signUpNewUser();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        });
        // exit
        exit_button.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(getClass().getClassLoader().getResource("signIN_z.fxml")));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage curstage = (Stage) exit_button.getScene().getWindow();
            curstage.setScene(new Scene(root));
        });

        su_dzr.setOnAction(actionEvent -> {
            su_nn.setSelected(false);
            su_dzr.setSelected(true);
        });

        su_nn.setOnAction(actionEvent -> {
            su_dzr.setSelected(false);
            su_nn.setSelected(true);
        });
    }
    //for register
    private void openProfile(User_z userZ) throws SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getClassLoader().getResource("user_profile.fxml")));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage curstage = (Stage) su_button.getScene().getWindow();
        curstage.setScene(new Scene(root));
        UserProfileController userProfileController = loader.getController(); //получаем контроллер для второй формы
        userProfileController.setUser(userZ);
        userProfileController.setFields(userZ);


            userProfileController.zsetBooksToComboboks();// передаем необходимые параметры

    }

    //for register
    private void signUpNewUser() throws SQLException {
        exist_label.setText("");
        DatabaseHandler databaseHandler = new DatabaseHandler();
        String firstname = su_first_name.getText();
        String lastname = su_last_name.getText();
        String phone_number = su_phone_number.getText();
        String username = su_user_name.getText();
        String password = su_password.getText();
        String office = "";

        if (su_dzr.isSelected()) {
            office = "dzr";
            System.out.println("dzr");
        } else {
            office = "nn";
            System.out.println("nn");
        }

        User_z userZ = new User_z(firstname, lastname, username, password, office, phone_number);
        if (!databaseHandler.isExist(username)) {
            System.out.println("Login NOT busy");
            databaseHandler.signUpUser(userZ);
            System.out.println("Opening user profile");
            openProfile(userZ);
        } else {
            exist_label.setTextFill(Color.color(1, 1, 1));
            exist_label.setText("Login busy");
            System.out.println("Login busy");
        }

    }

}
