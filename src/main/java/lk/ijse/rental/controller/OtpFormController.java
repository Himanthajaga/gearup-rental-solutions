package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.rental.model.Admin;
import lk.ijse.rental.repository.AdminRepo;
import lk.ijse.rental.util.EmailServiceUtil;

import java.io.IOException;
import java.sql.SQLException;

public class OtpFormController {

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnResend;

    @FXML
    private JFXButton btnverify;

    @FXML
    private Text txtMassage;

    @FXML
    private TextField txtOTp;
    private String otp;
AdminRepo adminRepo = new AdminRepo();
private Admin admin;
    @FXML
    void btnCancelOnAction(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) btnCancel.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/signup_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Sign up Form");

        stage.show();
    }

    @FXML
    void btnResendOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
       txtOTp.getStyleClass().removeAll("mfx-text-field-error");
        txtMassage.setVisible(false);
        sendOtp();
    }

    @FXML
    void btnVerifyOnAction(ActionEvent event) {

        String otp = txtOTp.getText();

        if(!otp.equals(this.otp)){
            txtMassage.setVisible(true);
            txtOTp.getStyleClass().add("mfx-text-field-error");
            return;
        }
    }

    private void switchToLogin() throws IOException {

        Stage stage1 = (Stage) btnCancel.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Sign up Form");

        stage.show();
    }
public void setAdmin(Admin admin) {
    this.admin = admin;
}
    public void sendOtp() throws SQLException, ClassNotFoundException{
        this.otp= EmailServiceUtil.sendMail(admin.getA_email());
    }



}
