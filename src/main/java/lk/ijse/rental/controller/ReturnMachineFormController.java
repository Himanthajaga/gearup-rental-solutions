package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import lk.ijse.rental.model.Machine;
import lk.ijse.rental.repository.MachineRepo;

import java.sql.SQLException;
import java.util.List;

public class ReturnMachineFormController {
    @FXML
    private Label lblDate;
    @FXML
    private Label lblMachineName;
    @FXML
    private JFXButton btnReturn;

    @FXML
    private ComboBox<String> cmbmachineId;

    @FXML
    private Label lblDescription;
    public void initialize() {
        //getIsavailableMachines();
        getItemCodes();
        realTime();
    }

    private void realTime() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    lblDate.setText(String.valueOf(java.time.LocalDate.now()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @FXML
    void btnReturnOnActionOnAction(ActionEvent event) {
        String code = cmbmachineId.getValue();
        try {
            boolean isUpdated = MachineRepo.updateIsAvailable(code, "1");
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Machine Returned", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to return the machine", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void cmbmachineIdOnAction(ActionEvent event) {
        String code = cmbmachineId.getValue();
        try {
            Machine machine = MachineRepo.searchById(code);
            if (machine != null) {
                System.out.println(machine.getM_desc());
                lblDescription.setText(machine.getM_desc());
                lblMachineName.setText(machine.getM_Name());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
//    private void getIsavailableMachines(){
//
//        ObservableList<String> obList = FXCollections.observableArrayList();
//        try {
//            List<Machine> machineList = MachineRepo.searchIsavailable();
//
//            for (Machine machine : machineList) {
//                if (machine.getIsAvaiable().equals("0")){
//                    obList.add(machine.getM_Id());
//                }
//            }
//
//            cmbmachineId.setItems(obList);
//
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//
//        }
//    }


    private void getItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<Machine> machineList = MachineRepo.getAll();

            for (Machine machine : machineList) {
                if (machine.getIsAvaiable().equals("0")){
                    obList.add(machine.getM_Id());
                }
            }

          cmbmachineId.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }
}

