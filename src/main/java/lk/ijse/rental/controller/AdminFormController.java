package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.rental.model.Admin;
import lk.ijse.rental.model.tm.AdminTm;
import lk.ijse.rental.qrGenerate.QrcodeForMachine;
import lk.ijse.rental.repository.AdminRepo;
import lk.ijse.rental.util.Regex;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AdminFormController {
    @FXML
    private Label txtId;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDeleteAdmin;

    @FXML
    private JFXButton btnSaveAdmin;

    @FXML
    private JFXButton btnUpdateAdmin;

    @FXML
    private TableColumn<?, ?> cola_ConfirmPasswors;

    @FXML
    private TableColumn<?, ?> cola_Email;

    @FXML
    private TableColumn<?, ?> cola_id;

    @FXML
    private TableColumn<?, ?> cola_name;

    @FXML
    private TableColumn<?, ?> cola_password;

    @FXML
    private TableView<AdminTm> tblAdmin;

    @FXML
    private TextField txtAid;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtConfirmPassword;
    @FXML
    private TextField txtPassword;
    private List<Admin> adminList = new ArrayList<>();

    private QrcodeForMachine qrcodeForUser = new QrcodeForMachine();
    AdminRepo adminRepo = new AdminRepo();

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
    System.exit(0);
    }

    public void initialize(){
        this.adminList = getAllAdmins();
        setCellValueFactory();
        loadAdminTable();
        loadNextAdminId();
    }

    private void loadNextAdminId() {
        try {
            String lastAdminId = adminRepo.getLastAdminId();
            if (lastAdminId != null) {
                int id = Integer.parseInt(lastAdminId.replace("A", ""));
                id++;
                if (id < 10) {
                    txtId.setText("A00" + id);
                } else if (id < 100) {
                    txtId.setText("A0" + id);
                } else {
                    txtId.setText("A" + id);
                }
            } else {
                txtId.setText("A001");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        cola_id.setCellValueFactory(new PropertyValueFactory<>("cola_id"));
        cola_name.setCellValueFactory(new PropertyValueFactory<>("cola_name"));
        cola_password.setCellValueFactory(new PropertyValueFactory<>("cola_password"));
        cola_ConfirmPasswors.setCellValueFactory(new PropertyValueFactory<>("cola_ConfirmPasswors"));
        cola_Email.setCellValueFactory(new PropertyValueFactory<>("cola_Email"));


    }
    private List<Admin> getAllAdmins() {
        List<Admin> adminsList = null;
        try {
            adminsList = adminRepo.getAll();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return adminsList;
    }
    private void loadAdminTable() {

        ObservableList<AdminTm> tmList = FXCollections.observableArrayList();

        for (Admin admin : adminList) {
            AdminTm adminTm = new AdminTm(
                    admin.getA_id(),
                    admin.getA_name(),
                    admin.getA_password(),
                    admin.getA_confirmPassword(),
                    admin.getA_email()

            );
            tmList.add(adminTm);
        }
        tblAdmin.setItems(tmList);
        AdminTm selectedItem = (AdminTm) tblAdmin.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }
    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtPassword.setText("");
    }
    @FXML
    void btnClearAdminOnAction(ActionEvent event) {
        clearFields();
    }


    @FXML
    void btnDeleteAdminOnAction(ActionEvent event) {
        String adminId = txtAid.getText();
        try {
            boolean isDeleted =adminRepo.delete(adminId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted..").show();
                adminList.removeIf(temp -> temp.getA_id().equals(adminId));
                loadAdminTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveAdminOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String email = txtEmail.getText();

        if (!Regex.idValidation(id)) {
            new Alert(Alert.AlertType.WARNING, "Invalid Admin ID").show();
            txtId.requestFocus();
            return;
        }
       Admin admin = new Admin(id, name, password,confirmPassword,email);
        try {
            boolean isAdded = adminRepo.save(admin);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                qrcodeForUser.CreateQr(id);
                adminList.add(admin);
                loadAdminTable();
                loadNextAdminId();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnUpdateAdminOnAction(ActionEvent event) {
        String adminId = txtAid.getText();
        String adminName = txtName.getText();
        String adminPassword = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String adminEmail = txtPassword.getText();


        Admin admin = new Admin(adminId, adminName, adminPassword,confirmPassword,adminEmail);
        try {
            boolean isUpdated = adminRepo.update(admin);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
               adminList.add(admin);
                loadAdminTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void txtAdminConfirmPasswordOnreleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[0-9]{1,}$");
        if (!idPattern.matcher(txtConfirmPassword.getText()).matches()) {
            addError(txtConfirmPassword);

        }else{
            removeError(txtConfirmPassword);
        }
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: green; -fx-border-width: 5");
    }

    private void addError(TextField textField) {
        textField.setStyle("-fx-border-color: red; -fx-border-width: 5");
    }

    @FXML
    void txtAdminEmailOnreleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("(^[a-zA-Z0-9_.]+[@]{1}[a-z0-9]+[\\.][a-z]+$)");
        if (!idPattern.matcher(txtEmail.getText()).matches()) {
            addError(txtEmail);

        }else{
            removeError(txtEmail);
        }
    }

//    @FXML
//    void txtAdminIdOnreleasedOnAction(KeyEvent event) {
//        Pattern idPattern = Pattern.compile("^(A)[0-9]{1,}$");
//        if (!idPattern.matcher(txtId.getText()).matches()) {
//            addError(txtId);
//
//        }else{
//            removeError(txtId);
//        }
//    }

    @FXML
    void txtAdminnameOnreleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[A-z|\\\\s]{3,}$");
        if (!idPattern.matcher(txtName.getText()).matches()) {
            addError(txtName);

        }else{
            removeError(txtName);
        }
    }

    @FXML
    void txtAdminpasswordOnreleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[0-9]{1,}$");
        if (!idPattern.matcher(txtPassword.getText()).matches()) {
            addError(txtPassword);

        }else{
            removeError(txtPassword);
        }
    }

    public void idReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^(A)[0-9]{1,}$");
        if (!idPattern.matcher(txtAid.getText()).matches()) {
            addError(txtAid);

        }else{
            removeError(txtAid);
        }
    }
}


