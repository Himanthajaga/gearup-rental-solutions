package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.repository.AdminRepo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.regex.Pattern;

public class LoginFormController {

    @FXML
    private Text ForgotPassword;

    @FXML
    private Text Signup;

    @FXML
    private JFXButton btnlogin;

    @FXML
    private TextField txtpassword;

    @FXML
    private TextField txtuserId;
    @FXML
    private AnchorPane rootNode;
    @FXML
    private Text txtGreetings;

    public void initialize()  {
        setGreetings();
    }

    private void setGreetings() {
        LocalTime currentTime = LocalTime.now();
        String greeting = (currentTime.getHour() < 12) ? "Good Morning" : "Good Evening";
        txtGreetings.setText(greeting);
    }
    @FXML
    void btnForgotPwOnAction(MouseEvent event) throws IOException {
        Stage stage1 = (Stage) Signup.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/otp_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Sign up Form");

        stage.show();
    }

    @FXML
    void btnSignupOnAction(MouseEvent event) throws IOException {
        Stage stage1 = (Stage) Signup.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/signup_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Sign up Form");

        stage.show();
    }
    @FXML
    void btnloginOnAction(ActionEvent event) {

        String userId = txtuserId.getText();
        String pw = txtpassword.getText();

        try {
            checkCredential(userId, pw);
            AdminRepo.adminId = userId;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void addError(TextField textField) {
        textField.setStyle("-fx-border-color: red; -fx-border-width: 5");
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: green; -fx-border-width: 5");
    }
    @FXML
    void txtPasswordOnReleased(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[0-9]{1,}$");
        if (!idPattern.matcher(txtpassword.getText()).matches()) {
            addError(txtpassword);

        }else{
            removeError(txtpassword);
        }
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
    private void checkCredential(String userId, String pw) throws SQLException, IOException, ClassNotFoundException {
        String sql = "SELECT a_id, a_password FROM admin WHERE a_name = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String dbPw = resultSet.getString(2);

            if(dbPw.equals(pw)) {
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "Password is incorrect!").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "user id not found!").show();
        }
    }

    private void navigateToTheDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_main_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");

    }
    @FXML
    void btnForgotPasswordOnAction(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/view/forgotPassword_form.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.rootNode.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("OTP Form");
        } catch (IOException e) {
            e.printStackTrace();
    }}
    @FXML
    void txtpasswordOnAction(ActionEvent event) {

        btnloginOnAction(event);
    }

    @FXML


    public void btnuserIdOnAction(ActionEvent actionEvent) {
    }
}
