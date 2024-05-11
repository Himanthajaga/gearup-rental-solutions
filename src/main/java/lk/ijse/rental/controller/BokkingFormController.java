package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.rental.model.Bokking;
import lk.ijse.rental.model.Customer;
import lk.ijse.rental.model.Machine;
import lk.ijse.rental.model.tm.BokkingTm;
import lk.ijse.rental.repository.BokkingRepo;
import lk.ijse.rental.repository.CustomerRepo;
import lk.ijse.rental.repository.MachineRepo;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BokkingFormController {
    private List<Bokking> bokkingList = new ArrayList<>();


    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDeleteBokking;

    @FXML
    private JFXButton btnSaveBokking;

    @FXML
    private JFXButton btnUpdateBokking;

    @FXML
    private JFXComboBox<String> cmbCustomerID;

    @FXML
    private JFXComboBox<String > cmbMachineId;

    @FXML
    private TableColumn<?, ?> colBokkingDate;

    @FXML
    private TableColumn<?, ?> colBokkingId;

    @FXML
    private TableColumn<?, ?> colCustomerEmail;

    @FXML
    private TableColumn<?, ?> colMachineId;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblMachineId;

    @FXML
    private TableView<BokkingTm> tblBokking;

    @FXML
    private TextField txtBokkingId;

    @FXML
    private DatePicker txtBokkkingDate;




    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        System.exit(0);

    }
    public void initialize() {

        this.bokkingList=getAllBokkings();
        setCellValueFactory();
        loadBokkingTable();
        getCustomerIds();
        getMachineIds();
    }

    private void getMachineIds() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = MachineRepo.getIds();

            for (String id : idList) {
                obList.add(id);
            }
            cmbMachineId.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }

    private void getCustomerIds() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = CustomerRepo.getEmails();

            for (String id : idList) {
                obList.add(id);
            }
            cmbCustomerID.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }

    private void setCellValueFactory() {
        colBokkingId.setCellValueFactory(new PropertyValueFactory<>("colBokkingId"));
        colBokkingDate.setCellValueFactory(new PropertyValueFactory<>("colBokkingDate"));
       colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("colCustomerEmail"));
        colMachineId.setCellValueFactory(new PropertyValueFactory<>("colMachineId"));

    }
    private void loadBokkingTable() {

        ObservableList<BokkingTm> tmList = FXCollections.observableArrayList();

        for (Bokking bokking : bokkingList) {
            BokkingTm bokkingTm = new BokkingTm(
                    bokking.getBokkingId(),
                    bokking.getBokkingDate(),
                    bokking.getCustomerEmail(),
                    bokking.getMachineId()

            );

            tmList.add(bokkingTm);
        }
        tblBokking.setItems(tmList);
        BokkingTm selectedItem = (BokkingTm) tblBokking.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }


    private List<Bokking> getAllBokkings() {
        List<Bokking> bokkingList = null;
        try {
            bokkingList = BokkingRepo.getAll();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return bokkingList;
    }

    @FXML
    void IconBackOnAction(MouseEvent event) {

    }


    @FXML
    void btnDeleteBokkingOnAction(ActionEvent event) {
        String id = txtBokkingId.getText();

        try {
            boolean isDeleted = BokkingRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Bokking deleted!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtBokkingId.getText();

        try {
            Bokking bokking = BokkingRepo.searchById(id);

            if (bokking != null) {
                txtBokkingId.setText(bokking.getBokkingId());
                txtBokkkingDate.getValue().toString();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {
        String customerId = (String) cmbCustomerID.getValue();
        try {
            Customer name = CustomerRepo.searchByemail(customerId);
            lblCustomerId.setText(name.getC_name());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void txtBokkingOnReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^(B)[0-9]{1,}$");
        if (!idPattern.matcher(txtBokkingId.getText()).matches()) {
            addError(txtBokkingId);

        }else{
            removeError(txtBokkingId);
        }
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: green; -fx-border-width: 5");
    }

    private void addError(TextField textField) {
        textField.setStyle("-fx-border-color: red; -fx-border-width: 5");
    }

    @FXML
    void cmbMachineIdOnaction(ActionEvent event) {
        String machineId = (String) cmbMachineId.getValue();
        try {
            Machine machine= MachineRepo.searchById(machineId);
            lblMachineId.setText(machine.getM_Name());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveBokkingOnAction(ActionEvent event) {
        String bokkingId = txtBokkingId.getText();
        String bokkingDate = txtBokkkingDate.getValue().toString();
        String customerId = cmbCustomerID.getValue().toString();
        String machineId = cmbMachineId.getValue().toString();

        Bokking bokking = new Bokking(bokkingId, bokkingDate,customerId,machineId);
        try {
            boolean isAdded = BokkingRepo.save(bokking);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                bokkingList.add(bokking);
                loadBokkingTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnUpdateBokkingOnAction(ActionEvent event) {
        String bokkingId = txtBokkingId.getText();
        String bokkingDate = txtBokkkingDate.getValue().toString();
        String customerId = lblCustomerId.getText();
        String machineId = lblMachineId.getText();
        Bokking bokking = new Bokking(bokkingId, bokkingDate,customerId,machineId);
        try {
            boolean isUpdated = BokkingRepo.update(bokking);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                bokkingList.add(bokking);
                loadBokkingTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void btnClearBokkingOnAction(ActionEvent event) {
    clearFields();
    }

    private void clearFields() {
        txtBokkingId.clear();
        txtBokkkingDate.getEditor().clear();
        cmbCustomerID.getEditor().clear();
        cmbMachineId.getEditor().clear();
        lblCustomerId.setText("");
        lblMachineId.setText("");
    }

}
