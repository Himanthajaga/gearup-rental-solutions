package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.rental.model.Admin;
import lk.ijse.rental.model.tm.AdminTm;
import lk.ijse.rental.repository.AdminRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminFormController {

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
    private TextField txtConfirmPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;
    private List<Admin> adminList = new ArrayList<>();


    AdminRepo adminRepo = new AdminRepo();

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
    System.exit(0);
    }

    public void initialize(){
        this.adminList = getAllAdmins();
        setCellValueFactory();
        loadAdminTable();
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
        String adminId = txtId.getText();
        try {
            boolean isDeleted =adminRepo.delete(adminId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted..").show();
                adminList.removeIf(temp -> temp.getA_id().equals(adminId));
                loadAdminTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
//    @FXML
//    void txtSearchOnAction(ActionEvent event) {
//        String adminId = txtId.getText();
//        try {
//            Admin admin = adminRepo.searchById(adminId);
//            if (admin != null) {
//                txtName.setText(admin.getA_name());
//                txtPassword.setText(admin.getA_password());
//            } else {
//                new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
//            }
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    void btnSaveAdminOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtPassword.getText();
        String email = txtPassword.getText();


       Admin admin = new Admin(id, name, password,confirmPassword,email);
        try {
            boolean isAdded = adminRepo.save(admin);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                adminList.add(admin);
                loadAdminTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnUpdateAdminOnAction(ActionEvent event) {
        String adminId = txtId.getText();
        String adminName = txtName.getText();
        String adminPassword = txtPassword.getText();
        String confirmPassword = txtPassword.getText();
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
    }


