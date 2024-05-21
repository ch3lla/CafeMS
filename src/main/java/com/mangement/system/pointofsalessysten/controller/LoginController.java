package com.mangement.system.pointofsalessysten.controller;

import com.mangement.system.pointofsalessysten.dto.EmployeeDto;
import com.mangement.system.pointofsalessysten.utils.Db;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.bson.Document;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button change_passwordBtn;

    @FXML
    private AnchorPane change_password_form;

    @FXML
    private PasswordField confirm_new_password;

    @FXML
    private Button cp_back;

    @FXML
    private Button forgot_passwordBtn;

    @FXML
    private AnchorPane forgot_passwordForm;

    @FXML
    private TextField forgot_password_answer;

    @FXML
    private Button forgot_password_back_btn;

    @FXML
    private ComboBox<?> forgot_password_question;

    @FXML
    private TextField forgot_password_username;

    @FXML
    private PasswordField new_password;

    @FXML
    private Hyperlink si_forgotPassword;

    @FXML
    private Button si_loginBtn;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_createAccountBtn;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_answer;

    @FXML
    private Button su_createAccountBtn;

    @FXML
    private Hyperlink su_loginPage;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<String> su_securityQuestion;

    @FXML
    private TextField su_username;

    private Alert alert;

    public void registerBtn(){
        if (su_username.getText().isEmpty() || su_password.getText().isEmpty() ||
        su_securityQuestion.getSelectionModel().getSelectedItem() == null || su_answer.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);;
            alert.setContentText("Please fil all blank fields");
            alert.showAndWait();
        } else {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setUsername(su_username.getText());
            employeeDto.setPassword(su_password.getText());
            employeeDto.setSecurityQuestion(su_securityQuestion.getSelectionModel().getSelectedItem());
            employeeDto.setAnswer(su_answer.getText());
            Db db = new Db();
            db.connectDB();
            db.insertEmployee(employeeDto);
            db.close();

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);;
            alert.setContentText("Successfully registered account");
            alert.showAndWait();

            su_username.setText("");
            su_password.setText("");
            su_securityQuestion.getSelectionModel().clearSelection();
            su_answer.setText("");

            TranslateTransition slider = new TranslateTransition();
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) -> {
                side_createAccountBtn.setVisible(true);
            });

            slider.play();
        }
    }

    public void loginBtn() {
        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setUsername(si_username.getText());
            employeeDto.setPassword(si_password.getText());

            try {
                Db db = new Db();
                db.connectDB();
                Document employee = db.getEmployee(employeeDto.getUsername());

                if (employee == null) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("User not found");
                    alert.showAndWait();
                } else if (!employee.getString("password").equals(employeeDto.getPassword())) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid password");
                    alert.showAndWait();
                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Login successful");
                    alert.showAndWait();

                    // Reset UI elements only if login is successful
                    si_username.setText("");
                    si_password.setText("");
                }

                db.close();
            } catch (Exception e) {
                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while trying to log in");
                alert.showAndWait();
            }
        }
    }

    public void forgotPassword(){

    }

    public void switchForgotPass(){
        forgot_passwordForm.setVisible(true);
        si_loginForm.setVisible(false);
    }

    public void backToLogin(){
        si_loginForm.setVisible(true);
        forgot_passwordForm.setVisible(false);
    }

    private String[] security_questions = {
        "What is your mother's maiden name?",
                "What was the name of your first pet?",
                "What was the name of your elementary school?",
                "What was your childhood nickname?",
                "What is the name of your favorite childhood friend?",
                "What is the name of the city where you were born?",
                "What was your dream job as a child?",
                "What is your favorite book?",
                "What was the make and model of your first car?",
                "What is your favorite food?"
    };

    public void regQuestionList(){

        List<String> listQ = new ArrayList<>(Arrays.asList(security_questions));
        ObservableList<String> listData = FXCollections.observableArrayList(listQ);
        su_securityQuestion.setItems(listData);
    }

    public void switchForm(ActionEvent event){
        TranslateTransition slider = new TranslateTransition();
        if (event.getSource() == side_createAccountBtn){
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) -> {
                side_createAccountBtn.setVisible(false);
                forgot_passwordForm.setVisible(false);
                si_loginForm.setVisible(true);
                change_password_form.setVisible(false);
                regQuestionList();
            });

            slider.play();
        } else if (event.getSource() == su_loginPage){
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) -> {
                side_createAccountBtn.setVisible(true);

                forgot_passwordForm.setVisible(false);
                si_loginForm.setVisible(true);
                change_password_form.setVisible(false);
            });

            slider.play();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
