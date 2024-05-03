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
import javafx.stage.Window;

import java.io.IOException;

public class SignupFormController {

    @FXML
    private TextField btnAddress;

    @FXML
    private TextField btnConfirmPw;

    @FXML
    private Text btnLogin;

    @FXML
    private TextField btnPw;

    @FXML
    private JFXButton btnSignin;

    @FXML
    private TextField btnTelephone;

    @FXML
    private TextField btnUsername;

    @FXML
    void btnAddressonAction(ActionEvent event) {

    }

    @FXML
    void btnConfirmPwOnAction(ActionEvent event) {

    }

    @FXML
    void btnLoginOnAction(MouseEvent event) throws IOException {


        Stage stage1 = (Stage)btnLogin.getScene().getWindow();
        stage1.close();

    }

    @FXML
    void btnPwOnAction(ActionEvent event) {

    }

    @FXML
    void btnSignInOnAction(ActionEvent event) {

    }

    @FXML
    void btnUsernameOnAction(ActionEvent event) {

    }

    @FXML
    void btntelephoneOnAction(ActionEvent event) {

    }

}
