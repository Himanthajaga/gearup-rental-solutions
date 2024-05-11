package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.rental.model.Admin;
import lk.ijse.rental.model.Customer;
import lk.ijse.rental.qrGenerate.QrcodeForMachine;
import lk.ijse.rental.repository.AdminRepo;
import lk.ijse.rental.repository.CustomerRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SignupFormController {

    @FXML
    private Text btnLogin;

    @FXML
    private JFXButton btnSignin;

    @FXML
    private TextField txtAdminEmail;

    @FXML
    private TextField txtAdminId;

    @FXML
    private TextField txtAdminName;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private PasswordField txtPassword;

    private AnchorPane rootNode;
    @FXML
    private Text txtMassage;

    private List<Admin> adminList = new ArrayList<>();
    private QrcodeForMachine qrcodeForUser = new QrcodeForMachine();
    AdminRepo adminRepo = new AdminRepo();

    @FXML
    void btnLoginOnAction(MouseEvent event) throws IOException {


        Stage stage1 = (Stage) btnLogin.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Sign up Form");

        stage.show();

    }

    @FXML
    void btnPwOnAction(ActionEvent event) {

    }
    @FXML
    void txtAdminConfirmPasswordOnReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        if (!idPattern.matcher(txtConfirmPassword.getText()).matches()) {
            addError(txtConfirmPassword);

        }else{
            removeError(txtConfirmPassword);
        }
    }

    private void removeError(TextField textfield) {
        textfield.setStyle("-fx-border-color: green; -fx-border-width: 5");
    }

    private void addError(TextField textfield) {
        textfield.setStyle("-fx-border-color: red; -fx-border-width: 5");
    }

    @FXML
    void txtAdminEmailOnReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("(^[a-zA-Z0-9_.]+[@]{1}[a-z0-9]+[\\.][a-z]+$)");
        if (!idPattern.matcher(txtAdminEmail.getText()).matches()) {
            addError( txtAdminEmail);

        }else{
            removeError( txtAdminEmail);
        }
    }

    @FXML
    void txtAdminIdOnReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^(A)[0-9]{1,}$");
        if (!idPattern.matcher(txtAdminId.getText()).matches()) {
            addError(txtAdminId);

        }else{
            removeError(txtAdminId);
        }
    }

    @FXML
    void txtAdminNameOnReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z ]*$");
        if (!idPattern.matcher(txtAdminName.getText()).matches()) {
            addError(txtAdminName);

        }else{
            removeError(txtAdminName);
        }
    }

    @FXML
    void txtAdminPasswordOnReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        if (!idPattern.matcher(txtPassword.getText()).matches()) {
            addError(txtPassword);

        }else{
            removeError(txtPassword);
        }
    }
    @FXML
    void btnSignInOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        String id = txtAdminId.getText();
        String name = txtAdminName.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String email = txtAdminEmail.getText();


        Admin admin = new Admin(id, name, password,confirmPassword,email);
        try {
            boolean isAdded = adminRepo.save(admin);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                qrcodeForUser.CreateQr(id);
                adminList.add(admin);
                Stage stage1 = (Stage)btnLogin.getScene().getWindow();
                stage1.close();
                Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

                Scene scene = new Scene(rootNode);
                Stage stage = new Stage();
                stage.setScene(scene);

                stage.setTitle("Sign up Form");

                stage.show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
        private boolean validateAccount () {
            String email = txtAdminEmail.getText();
            boolean isEmailValidated = email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

            if (!isEmailValidated) {
                txtAdminEmail.requestFocus();
                changeTextFieldStyle(txtAdminEmail);
                return false;
            }

            boolean isEmailExist = false;

            try {
                isEmailExist =adminRepo.searchByEmail(email);

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            if (isEmailExist) {
                txtMassage.setText("Email Already Exist");
              txtAdminEmail.getStyleClass().add("mfx-text-field-error");
                txtMassage.setVisible(true);
                return false;
            }

            txtAdminEmail.getStyleClass().removeAll("mfx-text-field-error");
            txtMassage.setVisible(false);


            String userid = txtAdminId.getText();
            boolean isUserIdValidated = userid.matches("[A-Za-z]{3,}");

            if (!isUserIdValidated) {
                txtAdminId.requestFocus();
                changeTextFieldStyle(txtAdminId);
                return false;
            }

            //Check Username Exist
            boolean isUserIdExist = false;

            try {
                isUserIdExist = adminRepo.searchById(userid);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            if (isUserIdExist) {
                txtMassage.requestFocus();
                txtMassage.setText("Username Already Exist");
                txtMassage.setVisible(true);
                return false;
            }


            String password = txtPassword.getText();
            boolean isPasswordValidated = password.matches("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}");

            if (!isPasswordValidated) {
                txtPassword.requestFocus();
                changeTextFieldStyle(txtPassword);
                return false;
            }

            String reEnterPassword = txtConfirmPassword.getText();
            boolean isReEnterPasswordValidated = reEnterPassword.matches("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}");

            if (!isReEnterPasswordValidated) {
                txtConfirmPassword.requestFocus();
                changeTextFieldStyle(txtConfirmPassword);
                return false;
            }


            //Check Passwords

            if (!password.equals(reEnterPassword)) {
                txtMassage.setText("Password doesnt Match");
                return false;
            }


            changeTextFieldStyle();


            return true;
        }
            private void changeTextFieldStyle (TextField txtField){

                //Disable the visibility of the error message
               txtField.setVisible(false);

               txtAdminEmail.getStyleClass().removeAll("mfx-text-field-error");
               txtAdminId.getStyleClass().removeAll("mfx-text-field-error");
                txtPassword.getStyleClass().removeAll("mfx-text-field-error");
                txtConfirmPassword.getStyleClass().removeAll("mfx-text-field-error");

                txtField.getStyleClass().add("mfx-text-field-error");

                //Enable the visibility of the error message
                if (txtField == txtAdminEmail) {
                    txtMassage.setText("Invalid Email");
                    txtMassage.setVisible(true);
                } else if (txtField == txtAdminId) {
                    txtMassage.setText("Invalid Username");
                    txtMassage.setVisible(true);
                } else if (txtField == txtPassword) {
                    txtMassage.setText("Enter a valid Password with at least 4 characters and a number");
                    txtMassage.setVisible(true);
                } else if (txtField == txtConfirmPassword) {
                    txtMassage.setText("Invalid ReEnterPassword");
                    txtMassage.setVisible(true);
                }


            }

            private void changeTextFieldStyle () {

                txtMassage.setVisible(false);

                txtAdminEmail.getStyleClass().removeAll("mfx-text-field-error");
                txtAdminId.getStyleClass().removeAll("mfx-text-field-error");
                txtPassword.getStyleClass().removeAll("mfx-text-field-error");
                txtConfirmPassword.getStyleClass().removeAll("mfx-text-field-error");


            }

        }


