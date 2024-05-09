package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.rental.model.Machine;
import lk.ijse.rental.model.tm.MachineTm;
import lk.ijse.rental.qrGenerate.QrcodeForMachine;
import lk.ijse.rental.repository.MachineRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineFormController {
    @FXML
    private JFXButton btnReturn;
    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDeleteMachine;

    @FXML
    private JFXButton btnSaveMachine;

    @FXML
    private JFXButton btnUpdateMachine;

    @FXML
    private TableColumn<?, ?> colMachineDescription;

    @FXML
    private TableColumn<?, ?> colMachineId;

    @FXML
    private TableColumn<?, ?> colMachineIsAvailable;

    @FXML
    private TableColumn<?, ?> colMachineName;


    @FXML
    private TableColumn<?, ?> colMachineRentalPrice;

    @FXML
    private TableView<MachineTm> tblMachine;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtMachineId;

    @FXML
    private TextField txtMachineName;


    @FXML
    private TextField txtRentalprice;

    @FXML
    private TextField txtisavailable;
    @FXML
    private AnchorPane paneHolder;
    private QrcodeForMachine qrcodeForUser = new QrcodeForMachine();

    private List<Machine> machineList = new ArrayList<>();


    public void initialize() {

        this.machineList=getAllMachines();
        setCellValueFactory();
        loadMachineTable();
    }



    private void setCellValueFactory() {
        colMachineId.setCellValueFactory(new PropertyValueFactory<>("colMachineId"));
        colMachineName.setCellValueFactory(new PropertyValueFactory<>("colMachineName"));
        colMachineDescription.setCellValueFactory(new PropertyValueFactory<>("colMachineDescription"));
        colMachineRentalPrice.setCellValueFactory(new PropertyValueFactory<>("colMachineRentalPrice"));
        colMachineIsAvailable.setCellValueFactory(new PropertyValueFactory<>("colMachineIsAvailable"));



    }
    private void loadMachineTable() {

        ObservableList<MachineTm> tmList = FXCollections.observableArrayList();

        for (Machine machine : machineList) {
            MachineTm machineTm= new MachineTm(
                    machine.getM_Id(),
                    machine.getM_Name(),
                    machine.getM_desc(),
                    machine.getM_rental_price(),
                    machine.getIsAvaiable()


            );
           // System.out.println("machineTm = " + machineTm);
            tmList.add(machineTm);
        }
        tblMachine.setItems(tmList);
        MachineTm selectedItem = (MachineTm) tblMachine.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }


    private List<Machine> getAllMachines() {
        List<Machine> machineList = null;
        try {
           machineList =MachineRepo.getAll();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return machineList;
    }


    @FXML
    void btnDeleteMachineOnAction(ActionEvent event) {
        String id = txtMachineId.getText();

        try {
            boolean isDeleted = MachineRepo.delete(id);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted", ButtonType.OK).show();
                machineList.removeIf(machine -> machine.getM_Id().equals(id));
                loadMachineTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Try Again", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    @FXML
    void btnSaveMachinerOnAction(ActionEvent event) {
        String id = txtMachineId.getText();
        String name = txtMachineName.getText();
        String desc = txtDesc.getText();
        String rentalPrice = txtRentalprice.getText();
        String isAvailable = txtisavailable.getText();


        Machine machine = new Machine(id, name, desc, rentalPrice,isAvailable);

        try {
            boolean isAdded = MachineRepo.save(machine);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved", ButtonType.OK).show();
                qrcodeForUser.CreateQr(id);
                machineList.add(machine);
                loadMachineTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Try Again", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnUpdateMachineOnAction(ActionEvent event) {
        String id = txtMachineId.getText();
        String name = txtMachineName.getText();
        String desc = txtDesc.getText();
        String rentalPrice = txtRentalprice.getText();
        String isAvailable = txtisavailable.getText();


        Machine machine = new Machine(id, name, desc, rentalPrice,isAvailable);

        try {
            boolean isUpdated = MachineRepo.update(machine);

            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated", ButtonType.OK).show();
                machineList.add(machine);
                loadMachineTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Try Again", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void btnClearCustomerOnAction(ActionEvent event) {
    clearFields();
    }
    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtMachineId.getText();

        try {
            Machine machine = MachineRepo.searchById(id);

            if (machine != null) {
                txtMachineId.setText(machine.getM_Id());
                txtMachineName.setText(machine.getM_Name());
                txtDesc.setText(machine.getM_desc());
                txtRentalprice.setText(machine.getM_rental_price());
                txtisavailable.setText(machine.getIsAvaiable());


            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void clearFields() {
        txtDesc.clear();
        txtMachineId.clear();
        txtMachineName.clear();
        txtRentalprice.clear();
        txtisavailable.clear();


    }

}
