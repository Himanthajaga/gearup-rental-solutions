package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.rental.model.Mechanic;
import lk.ijse.rental.model.tm.MechanicTm;
import lk.ijse.rental.qrGenerate.QrcodeForMachine;
import lk.ijse.rental.repository.MechanicRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MechanicFormController {
    @FXML
    private Label txtMid;
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
    private TextField txtMidnew;
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

    private QrcodeForMachine qrcodeForUser = new QrcodeForMachine();

    public void initialize() {

        this.mechanicList = getAllMechanics();
        setCellValueFactory();
        loadMechanicTable();
        loadNextMechanicId();
    }

    private void loadNextMechanicId() {
        if (mechanicList.size() <= 0) {
            txtMid.setText("ME001");
        } else {
            Mechanic lastMechanic = mechanicList.get(mechanicList.size() - 1);
            String lastId = lastMechanic.getMec_id();
            String[] split = lastId.split("ME");
            int id = Integer.parseInt(split[1]);
            id = id + 1;
            if (id < 10) {
                txtMid.setText("ME00" + id);
            } else if (id < 100) {
                txtMid.setText("ME0" + id);
            } else {
                txtMid.setText("ME" + id);
            }
        }
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
            MechanicTm machineTm = new MechanicTm(
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
        txtMid.setText("");
        txtMName.clear();
        txtMAddress.clear();
        txtMTele.clear();
        txtMSalary.clear();
        txtMDescription.clear();

    }


    @FXML
    void btnDeleteMechanicOnAction(ActionEvent event) {
        String id = txtMName.getText();
        try {
            boolean isDeleted = MechanicRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted..").show();
                mechanicList.removeIf(mechanic -> mechanic.getMec_id().equals(id));
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
    void btnSaveMechanicOnAction(ActionEvent event) {
        String id = txtMid.getText();
        String name = txtMName.getText();
        String address = txtMAddress.getText();
        String tele = txtMTele.getText();
        String desc = txtMDescription.getText();
        String salary = txtMSalary.getText();
        if (id.trim().isEmpty() || name.trim().isEmpty() || address.trim().isEmpty() || tele.trim().isEmpty() || desc.trim().isEmpty() || salary.trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields").show();
            return;
        }

        Mechanic mechanic = new Mechanic(id, name, address, tele, desc, salary);
        try {
            boolean isSaved = MechanicRepo.save(mechanic);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Saved..").show();
                qrcodeForUser.CreateQr(id);
                mechanicList.add(mechanic);
                loadMechanicTable();
                clearFields();
                loadNextMechanicId();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateMechanicOnAction(ActionEvent event) {
        String id = txtMidnew.getText();
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

    @FXML
    void txtMechanicAddressOnReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^([A-z0-9]|[-/,.@+]|\\\\s){4,}$");
        if (!idPattern.matcher(txtMAddress.getText()).matches()) {
            addError(txtMAddress);

        } else {
            removeError(txtMAddress);
        }
    }

    private void removeError(TextField txtMAddress) {
        txtMAddress.setStyle("-fx-border-color: green; -fx-border-width: 5");
    }

    private void addError(TextField txtMAddress) {
        txtMAddress.setStyle("-fx-border-color: red; -fx-border-width: 5");
    }

    @FXML
    void txtMechanicDescriptionOnReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z ]*$");
        if (!idPattern.matcher(txtMDescription.getText()).matches()) {
            addError(txtMDescription);

        } else {
            removeError(txtMDescription);
        }
    }

//    @FXML
//    void txtMechanicIdOnReleasedOnAction(KeyEvent event) {
//        Pattern idPattern = Pattern.compile("^(ME)[0-9]{1,}$");
//        if (!idPattern.matcher(txtMid.getText()).matches()) {
//            addError(txtMid);
//
//        }else{
//            removeError(txtMid);
//        }
//    }

    @FXML
    void txtMechanicNameOnReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z ]*$");
        if (!idPattern.matcher(txtMName.getText()).matches()) {
            addError(txtMName);

        } else {
            removeError(txtMName);
        }
    }

    @FXML
    void txtMechanicSalaryOnReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^([0-9]){1,}[.]([0-9]){1,}$");
        if (!idPattern.matcher(txtMSalary.getText()).matches()) {
            addError(txtMSalary);

        } else {
            removeError(txtMSalary);
        }
    }

    @FXML
    void txtMechanicTeleOnReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[0]{1}[7]{1}[01245678]{1}[0-9]{7}$");
        if (!idPattern.matcher(txtMTele.getText()).matches()) {
            addError(txtMTele);

        } else {
            removeError(txtMTele);
        }
    }

    public void keyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^(ME)[0-9]{1,}$");
        if (!idPattern.matcher(txtMidnew.getText()).matches()) {
            addError(txtMidnew);

        } else {
            removeError(txtMidnew);
        }
    }
}
