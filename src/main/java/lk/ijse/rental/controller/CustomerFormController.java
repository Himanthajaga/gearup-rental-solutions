package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.rental.model.Customer;
import lk.ijse.rental.model.tm.CustomerTm;
import lk.ijse.rental.qrGenerate.QrcodeForMachine;
import lk.ijse.rental.repository.CustomerRepo;
import lk.ijse.rental.util.Regex;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static lk.ijse.rental.util.Regex.*;

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
    private Label lblCId;
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
    private QrcodeForMachine qrcodeForUser = new QrcodeForMachine();

    private List<Customer> customerList = new ArrayList<>();

    public void initialize() {

        this.customerList=getAllCustomers();
        setCellValueFactory();
        loadCustomerTable();
        loadnextCusId();
    }

    private void loadnextCusId() {
        try {
            String currentId = CustomerRepo.currentId();
            String nextId = nextId(currentId);

            lblCId.setText(nextId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId == null) {
            return "C001";
        }
        int currentIdNum = Integer.parseInt(currentId.replace("C", ""));
        currentIdNum++;
        if (currentIdNum < 10) {
            return "C00" + currentIdNum;
        } else if (currentIdNum < 100) {
            return "C0" + currentIdNum;
        } else {
            return "C" + currentIdNum;
        }
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
       lblCId.setText("");
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
        String id = txtCId.getText();

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
    void txtCustomerAddressReleasedOnAction(KeyEvent event) {
    Pattern idPattern = Pattern.compile("^([A-z0-9]|[-/,.@+]|\\\\s){4,}$");
        if (!idPattern.matcher(txtAddress.getText()).matches()) {
            addError(txtAddress);

        }else{
            removeError(txtAddress);
        }
    }

    private void addError(TextField textField) {
        textField.setStyle("-fx-border-color: red; -fx-border-width: 5");
    }

    private void removeError(TextField textField) {
       textField.setStyle("-fx-border-color: green; -fx-border-width: 5");
    }

    @FXML
    void txtCustomerEmailReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$");
        if (!idPattern.matcher(txtEmail.getText()).matches()) {
            addError(txtEmail);

        }else{
            removeError(txtEmail);
        }
    }

//    @FXML
//    void txtCustomerIdReleasedOnAction(KeyEvent event) {
//        Pattern idPattern = Pattern.compile("^(C)[0-9]{1,}$");
//        if (!idPattern.matcher(txtCId.getText()).matches()) {
//            addError(txtCId);
//
//        }else{
//            removeError(txtCId);
//        }
//    }

    @FXML
    void txtCustomerNameReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z ]*$");
        if (!idPattern.matcher(txtName.getText()).matches()) {
            addError(txtName);

        }else{
            removeError(txtName);
        }
    }

    @FXML
    void txtCustomerTeleReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[0-9]{10}$");
        if (!idPattern.matcher(txtTele.getText()).matches()) {
            addError(txtTele);

        }else{
            removeError(txtTele);
        }
    }


    @FXML
    void btnSaveCustomerOnAction(ActionEvent event) {
        String email = txtEmail.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTele.getText();
        String id = lblCId.getText();

        Customer customer= new Customer(email,name,address,tel,id);
        try {
            boolean isSaved = CustomerRepo.save(customer);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                customerList.add(customer);
                qrcodeForUser.CreateQr(id);
                loadCustomerTable();
                clearFeilds();
            }else {
                new Alert(Alert.AlertType.ERROR, "Try Again!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

//    private boolean isValid() {
//
//    }


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
              lblCId.setText(customer.getC_id());
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
}
