package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.rental.model.Mechanic;
import lk.ijse.rental.model.tm.MechanicTm;
import lk.ijse.rental.qrGenerate.QrcodeForMachine;
import lk.ijse.rental.repository.MechanicRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MechanicFormController {
    private List<Mechanic> mechanicList = new ArrayList<>();
    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDeleteMechanic;

    @FXML
    private JFXButton btnSaveMechanic;

    @FXML
    private JFXButton btnUpdateMechanic;

    @FXML
    private TableColumn<?, ?> colMAddress;

    @FXML
    private TableColumn<?, ?> colMDesc;

    @FXML
    private TableColumn<?, ?> colMId;

    @FXML
    private TableColumn<?, ?> colMName;

    @FXML
    private TableColumn<?, ?> colMSalary;

    @FXML
    private TableColumn<?, ?> colMTele;

    @FXML
    private TableView<MechanicTm> tblMechanic;

    @FXML
    private TextField txtMAddress;

    @FXML
    private TextField txtMDescription;

    @FXML
    private TextField txtMName;

    @FXML
    private TextField txtMSalary;

    @FXML
    private TextField txtMTele;

    @FXML
    private TextField txtMid;
    private QrcodeForMachine qrcodeForUser = new QrcodeForMachine();
    public void initialize() {

        this.mechanicList=getAllMechanics();
        setCellValueFactory();
        loadMechanicTable();
    }

    private void setCellValueFactory() {
        colMId.setCellValueFactory(new PropertyValueFactory<>("colMId"));
        colMName.setCellValueFactory(new PropertyValueFactory<>("colMName"));
        colMAddress.setCellValueFactory(new PropertyValueFactory<>("colMAddress"));
        colMTele.setCellValueFactory(new PropertyValueFactory<>("colMTele"));
        colMDesc.setCellValueFactory(new PropertyValueFactory<>("colMDesc"));
        colMSalary.setCellValueFactory(new PropertyValueFactory<>("colMSalary"));



    }
    private void loadMechanicTable() {

        ObservableList<MechanicTm> tmList = FXCollections.observableArrayList();

        for (Mechanic mechanic : mechanicList) {
            MechanicTm machineTm= new MechanicTm(
                    mechanic.getMec_id(),
                    mechanic.getMec_name(),
                    mechanic.getMec_address(),
                    mechanic.getMec_tel(),
                    mechanic.getMec_desc(),
                    mechanic.getMec_salary()

            );
            System.out.println("machineTm = " + machineTm);
            tmList.add(machineTm);
        }
        tblMechanic.setItems(tmList);
        MechanicTm selectedItem = tblMechanic.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }


    private List<Mechanic> getAllMechanics() {
        List<Mechanic> mechanicList = null;
        try {
            mechanicList = MechanicRepo.getAll();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return mechanicList;
    }

    @FXML
    void btnClearCustomerOnAction(ActionEvent event) {
    clearFields();
    }

    private void clearFields() {
        txtMid.clear();
        txtMName.clear();
        txtMAddress.clear();
        txtMTele.clear();
        txtMSalary.clear();
        txtMDescription.clear();

    }



    @FXML
    void btnDeleteMechanicOnAction(ActionEvent event) {
        String id = txtMid.getText();
        try {
            boolean isDeleted = MechanicRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted..").show();
                mechanicList.removeIf(mechanic -> mechanic.getMec_id().equals(id));
                loadMechanicTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnSaveMechanicOnAction(ActionEvent event) {
        String id = txtMid.getText();
        String name = txtMName.getText();
        String address = txtMAddress.getText();
        String tele = txtMTele.getText();
        String desc = txtMDescription.getText();
        String salary = txtMSalary.getText();

        Mechanic mechanic = new Mechanic(id, name, address, tele, desc, salary);
        try {
            boolean isSaved = MechanicRepo.save(mechanic);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Saved..").show();
                qrcodeForUser.CreateQr(id);
                mechanicList.add(mechanic);
                loadMechanicTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateMechanicOnAction(ActionEvent event) {
        String id = txtMid.getText();
        String name = txtMName.getText();
        String address = txtMAddress.getText();
        String tele = txtMTele.getText();
        String desc = txtMDescription.getText();
        String salary = txtMSalary.getText();

        Mechanic mechanic = new Mechanic(id, name, address, tele, desc, salary);
        try {
            boolean isUpdated = MechanicRepo.update(mechanic);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                mechanicList.add(mechanic);
                loadMechanicTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
