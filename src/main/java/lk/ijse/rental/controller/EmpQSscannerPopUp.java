package lk.ijse.rental.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import lk.ijse.rental.model.Machine;
import lk.ijse.rental.qrGenerate.QrCodeScanner;
import lk.ijse.rental.repository.MachineRepo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class EmpQSscannerPopUp {
    @FXML
    private Label ibiWelcome;

    @FXML
    private Label lblAttNoted;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblNotRegister;

    @FXML
    private Label lblSuchFul;

    @FXML
    private Text lblTime;

    @FXML
    private Label lbltryAgain;
    QrCodeScanner qrCodeScanner = new QrCodeScanner();


    public void btnQRpopUpAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String qrCode = qrCodeScanner.QrScanner();
        System.out.println("QR Code: " + qrCode);
      //  Machine machine = MachineRepo.searchById(qrCode);
        RentMachineFormController rentMachineFormController = new RentMachineFormController();
        rentMachineFormController.as(qrCode);
    }
}