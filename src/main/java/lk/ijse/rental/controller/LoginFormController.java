package lk.ijse.dep.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private Text ForgotPassword;

    @FXML
    private Text Signup;

    @FXML
    private JFXButton btnlogin;

    @FXML
    private TextField btnpassword;

    @FXML
    private TextField btnusername;

    @FXML
    void btnForgotPwOnAction(MouseEvent event) {

    }

    @FXML
    void btnSignupOnAction(MouseEvent event) throws IOException {
        AnchorPane rootNode  = FXMLLoader.load( this.getClass().getResource("/view/signup_form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("sign up Page");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnloginOnAction(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) btnlogin.getScene().getWindow();
        stage1.close();
        AnchorPane rootNode  = FXMLLoader.load( this.getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Dashboard Page");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnpasswordOnAction(ActionEvent event) {

    }

    @FXML
    void btnusernameOnAction(ActionEvent event) {

    }

}
