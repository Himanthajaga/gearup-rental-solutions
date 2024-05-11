package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.rental.repository.AdminRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class ForgotPasswordFormController {

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnReset;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtuserId;
    @FXML
    private Text txtMassage;
    AdminRepo adminRepo = new AdminRepo();
    @FXML
    void btnCancelOnAction(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) btnCancel.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Sign up Form");

        stage.show();
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws IOException {

        boolean isDetailsVerified = validateDetails();

        if (!isDetailsVerified){
            return;
        }

        Stage stage1 = (Stage) btnCancel.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Sign up Form");

        stage.show();


    }
    @FXML
    void txtEmailOnReleased(KeyEvent event) {
        Pattern idPattern = Pattern.compile("(^[a-zA-Z0-9_.]+[@]{1}[a-z0-9]+[\\.][a-z]+$)");
        if (!idPattern.matcher(txtEmail.getText()).matches()) {
            addError(txtEmail);

        }else{
            removeError(txtEmail);
        }
    }

    private void removeError(TextField txtEmail) {
        txtEmail.setStyle("-fx-border-color: green; -fx-border-width: 5");

    }

    private void addError(TextField txtEmail) {
        txtEmail.setStyle("-fx-border-color: red; -fx-border-width: 5");
    }

    @FXML
    void txtUsernameOnReleased(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z ]*$");
        if (!idPattern.matcher(txtuserId.getText()).matches()) {
            addError(txtuserId);

        }else{
            removeError(txtuserId);
        }
    }
    private boolean validateDetails() {

        String userName =txtuserId.getText();
        boolean isUserNameValidated = userName.matches("[A-Za-z]{3,}");

        if (!isUserNameValidated){
            txtuserId.requestFocus();
            changeTextFieldStyle(txtuserId);
            return false;
        }

        //Check Username Exist
        boolean isUsernameExist = false;

        try {
            isUsernameExist = adminRepo.searchByName(userName);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (!isUsernameExist){
            txtMassage.setText("Username Doesnt Exist");
            txtMassage.setVisible(true);
            return false;
        }

        String email = txtEmail.getText();

        boolean isEmailValidated = email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        if (!isEmailValidated){
            txtEmail.requestFocus();
            changeTextFieldStyle(txtEmail);
            return false;
        }

        //Check Email
        boolean isEmailExist = false;

        try {
            isEmailExist = adminRepo.searchByEmail(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (!isEmailExist){
            txtMassage.setText("Email Doesnt Match");
            txtMassage.setVisible(true);
            return false;
        }



        changeTextFieldStyle();

        return  true;
    }
    private void changeTextFieldStyle() {
        txtMassage.setVisible(false);

        txtuserId.getStyleClass().removeAll("mfx-text-field-error");
        txtEmail.getStyleClass().removeAll("mfx-text-field-error");
    }

    private void changeTextFieldStyle(TextField txtField) {

        txtMassage.setVisible(false);

       txtuserId.getStyleClass().removeAll("mfx-text-field-error");
        txtEmail.getStyleClass().removeAll("mfx-text-field-error");

        txtField.getStyleClass().add("mfx-text-field-error");




        if (txtField==txtEmail){
            txtMassage.setText("Invalid Email");
            txtMassage.setVisible(true);
        }
        else if (txtField==txtuserId){
            txtMassage.setText("Invalid Username");
            txtMassage.setVisible(true);
        }


    }
    @FXML
    void btnuserIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtpasswordOnAction(ActionEvent event) {

    }

}
