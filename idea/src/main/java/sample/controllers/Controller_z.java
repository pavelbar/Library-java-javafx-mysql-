package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Admin;
import sample.Const;
import sample.Handlers.DatabaseHandler;
import sample.User_z;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller_z {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginSignUpButton; //to register

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button authSignInButton; // try signIn

    @FXML
    private Label error_input_label;

    @FXML
    void initialize() {

        // try signIn
        authSignInButton.setOnAction(actionEvent -> {
            zSignInLogic();
        });

        //to register
       loginSignUpButton.setOnAction(actionEvent -> {
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(Objects.requireNonNull(getClass().getClassLoader().getResource("signUp_z.fxml")));
           try {
               loader.load();
           } catch (IOException e) {
               e.printStackTrace();
           }
           Parent root = loader.getRoot();
           Stage curstage = (Stage) loginSignUpButton.getScene().getWindow();
           curstage.setScene(new Scene(root));
       });
    }
    // for (try signIn)
    private void loginUser(String loginText, String loginPassword) throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler();

        User_z userZ = new User_z();
        userZ.setUsername(loginText);
        userZ.setPassword(loginPassword);

        Admin admin = new Admin();
        admin.setUsername(loginText);
        admin.setPassword(loginPassword);

        ResultSet resultSetUsers = databaseHandler.getUser(userZ);

        ResultSet resultSetAdmins = databaseHandler.getAdmin(admin);

        if (resultSetUsers.next()) {
            // если несколько клиентов - то первый
            String id = resultSetUsers.getString(Const.USER_ID);
            String fn = resultSetUsers.getString(Const.USER_FIRSTNAME);
            String ln = resultSetUsers.getString(Const.USER_LASTNAME);
            String login = resultSetUsers.getString(Const.USER_USERNAME);
            String password = resultSetUsers.getString(Const.USER_PASSWORD);
            String office = resultSetUsers.getString(Const.USER_OFFICE);
            String phone_number = resultSetUsers.getString(Const.USER_PHONENUMBER);
            User_z userZ1 = new User_z(id, fn, ln, login, password, office, phone_number);
            error_input_label.setTextFill(Color.color(1, 1, 1));
            error_input_label.setText("Success (user)");
            System.out.println("Success (user)");
            openAdminProfile(userZ1);
        } else if (resultSetAdmins.next()){
            // если несколько адинов - то первый
            String id = resultSetAdmins.getString(Const.ADMIN_ID);
            String fn = resultSetAdmins.getString(Const.ADMIN_FIRSTNAME);
            String ln = resultSetAdmins.getString(Const.ADMIN_LASTNAME);
            String login = resultSetAdmins.getString(Const.ADMIN_USERNAME);
            String password = resultSetAdmins.getString(Const.ADMIN_PASSWORD);
            String office = resultSetAdmins.getString(Const.ADMIN_OFFICE);
            String location = resultSetAdmins.getString(Const.ADMIN_LOCATION);
            String phone_number = resultSetAdmins.getString(Const.ADMIN_PHONENUMBER);

            System.out.println("id" + id);
            System.out.println("fn" + fn);
            System.out.println("ln" + ln);
            System.out.println("login" + login);
            System.out.println("pw" + password);
            System.out.println("office" + office);
            System.out.println("location" + location);
            System.out.println("phone" + phone_number);

            Admin admin1 = new Admin(id, fn, ln,login,password,office,location,phone_number);
            System.out.println("fn?? =  " + admin1.getFirstname());

            error_input_label.setTextFill(Color.color(1, 1, 1));
            error_input_label.setText("Success (admin)");
            System.out.println("Success (admin)");
            openAdminProfile(admin1);
        } else {
            error_input_label.setTextFill(Color.color(1, 1, 1));
            error_input_label.setText("Incorrect login/password");
            System.out.println("Incorrect login/password");
        }
    }

    private void openAdminProfile(User_z userZ) throws SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getClassLoader().getResource("user_profile.fxml")));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage curstage = (Stage) authSignInButton.getScene().getWindow();
        curstage.setScene(new Scene(root));
        // соединение с другой формой
        UserProfileController userProfileController = loader.getController(); //получаем контроллер для второй формы
        userProfileController.setUser(userZ);
        userProfileController.setFields(userZ);
        userProfileController.zsetBooksToComboboks();
        userProfileController.zfillHistoryTable();


        try {
            userProfileController.z_setMyAdmin();// передаем необходимые параметры
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void zSignInLogic(){
        String loginText = login_field.getText().trim();
        String loginPassword = password_field.getText().trim();
        if(!loginText.equals("")&&!loginPassword.equals("")){
            try {
                loginUser(loginText, loginPassword);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            error_input_label.setTextFill(Color.color(1, 1, 1));
            error_input_label.setText("Empty login/password");
            System.out.println("Empty login/password");
        }
    }

    @FXML
    public void onEnter(ActionEvent ae){
        System.out.println("enter") ;
        zSignInLogic();
    }

    private void openAdminProfile(Admin admin){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getClassLoader().getResource("admin_profile.fxml")));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage curstage = (Stage) authSignInButton.getScene().getWindow();
        curstage.setScene(new Scene(root));
        // соединение с другой формой
        AdminProfileController adminProfileController = loader.getController(); //получаем контроллер для второй формы
        adminProfileController.setAdmin(admin);
        adminProfileController.zfillHistoryTable();
        System.out.println("open admin");
    }
}

