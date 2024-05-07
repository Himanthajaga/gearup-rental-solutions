package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.rental.model.Customer;
import lk.ijse.rental.model.tm.CustomerTm;
import lk.ijse.rental.repository.CustomerRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerFormController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDeleteCustomer;

    @FXML
    private JFXButton btnSaveCustomer;

    @FXML
    private JFXButton btnUpdateCustomer;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCustomerEmail;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTelephone;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTele;

    private List<Customer> customerList = new ArrayList<>();

    public void initialize() {

        this.customerList=getAllCustomers();
        setCellValueFactory();
        loadCustomerTable();
    }


    private void setCellValueFactory() {
        colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("colCustomerEmail"));
        colName.setCellValueFactory(new PropertyValueFactory<>("colName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("colAddress"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("colTelephone"));
       colCustomerId.setCellValueFactory(new PropertyValueFactory<>("colCustomerId"));
    }
    @FXML
    void btnClearCustomerOnAction(ActionEvent event) {
    clearFeilds();
    }

    private void clearFeilds() {
       txtCId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtTele.setText("");
        txtEmail.setText("");
    }

    private void loadCustomerTable() {

        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();

        for (Customer customer : customerList) {
            CustomerTm customerTm = new CustomerTm(
                    customer.getC_mail(),
                    customer.getC_name(),
                    customer.getC_address(),
                    customer.getC_tel(),
                    customer.getC_id()
            );

            tmList.add(customerTm);
        }
       tblCustomer.setItems(tmList);
        CustomerTm selectedItem = (CustomerTm) tblCustomer.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }


    private List<Customer> getAllCustomers() {
        List<Customer> customerList = null;
        try {
            customerList = CustomerRepo.getAll();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }


    @FXML
    void btnDeleteCustomerOnAction(ActionEvent event) {
        String id = txtEmail.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                loadCustomerTable();
                clearFeilds();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnSaveCustomerOnAction(ActionEvent event) {
        String email = txtEmail.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTele.getText();
        String id = txtCId.getText();

        Customer customer= new Customer(email,name,address,tel,id);
        try {
            boolean isSaved = CustomerRepo.save(customer);
            if (isSaved) {
                customerList.add(customer);
                loadCustomerTable();
                clearFeilds();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }




    @FXML
    void btnUpdateCustomerOnAction(ActionEvent event) {
        String email = txtEmail.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTele.getText();
        String id = txtCId.getText();


        Customer customer = new Customer(email, name, address, tel, id);

        try {
            boolean isUpdated = CustomerRepo.update(customer);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                loadCustomerTable();
                clearFeilds();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtEmail.getText();

        try {
            Customer customer = CustomerRepo.searchByemail(id);

            if (customer != null) {
                txtEmail.setText(customer.getC_mail());
                txtName.setText(customer.getC_name());
                txtAddress.setText(customer.getC_address());
                txtTele.setText(customer.getC_tel());
               txtCId.setText(customer.getC_id());
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
}
