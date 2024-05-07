package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.rental.model.Customer;
import lk.ijse.rental.model.Supplier;
import lk.ijse.rental.model.tm.SupplierTm;
import lk.ijse.rental.repository.CustomerRepo;
import lk.ijse.rental.repository.SupplierRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierFormController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDeleteSupplier;

    @FXML
    private JFXButton btnSaveSupplier;

    @FXML
    private JFXButton btnUpdateSupplier;

    @FXML
    private TableColumn<?, ?> colSupplierAddress;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private TableColumn<?, ?> colSupplierTele;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtSupplierAdddress;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private TextField txtSupplierTele;

    private List<Supplier> supplierList = new ArrayList<>();
    public void initialize() {

        this.supplierList=getAllSuppliers();
        setCellValueFactory();
        loadSupplierTable();
    }


    private void setCellValueFactory() {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("colSupplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("colSupplierName"));
        colSupplierAddress.setCellValueFactory(new PropertyValueFactory<>("colSupplierAddress"));
        colSupplierTele.setCellValueFactory(new PropertyValueFactory<>("colSupplierTele"));




    }
    private void loadSupplierTable() {

        ObservableList<SupplierTm> tmList = FXCollections.observableArrayList();

        for (Supplier supplier : supplierList) {
            SupplierTm supplierTm = new SupplierTm(
                    supplier.getS_id(),
                    supplier.getS_name(),
                    supplier.getS_address(),
                    supplier.getS_tel()



            );
            System.out.println("machineTm = " + supplierTm);
            tmList.add(supplierTm);
        }
        tblSupplier.setItems(tmList);
        SupplierTm selectedItem = (SupplierTm) tblSupplier.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }


    private List<Supplier> getAllSuppliers() {
        List<Supplier> supplierList = null;
        try {
            supplierList = SupplierRepo.getAll();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return supplierList;
    }


    @FXML
    void btnDeleteSupplierOnAction(ActionEvent event) {
        String id = txtSupplierId.getText();
        try {
            boolean isDeleted = SupplierRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier deleted!").show();
                supplierList.removeIf(supplier -> supplier.getS_id().equals(id));
                loadSupplierTable();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    @FXML
    void btnSaveSupplierOnAction(ActionEvent event) {
        String s_id = txtSupplierId.getText();
        String s_name = txtSupplierName.getText();
        String s_address = txtSupplierAdddress.getText();
        String s_tel = txtSupplierTele.getText();

        Supplier supplier = new Supplier(s_id, s_name, s_address, s_tel);
        try {
            boolean isSaved = SupplierRepo.save(supplier);
            if (isSaved) {
                supplierList.add(supplier);
                loadSupplierTable();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnUpdateSupplierOnAction(ActionEvent event) {
        String id =txtSupplierId.getText();
        String name = txtSupplierName.getText();
        String address = txtSupplierAdddress.getText();
        String tel = txtSupplierTele.getText();

        Supplier supplier = new Supplier(id, name, address, tel);

        try {
            boolean isUpdated = SupplierRepo.update(supplier);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                loadSupplierTable();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnClearCustomerOnAction(ActionEvent event) {
    clearFields();
    }

    private void clearFields() {
        txtSupplierId.clear();
        txtSupplierName.clear();
        txtSupplierAdddress.clear();
        txtSupplierTele.clear();
    }



}
