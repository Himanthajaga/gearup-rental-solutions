package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.rental.model.*;
import lk.ijse.rental.model.tm.TblOrderCartTm;
import lk.ijse.rental.repository.CustomerRepo;
import lk.ijse.rental.repository.MachineRepo;
import lk.ijse.rental.repository.PlaceOrderRepo;
import lk.ijse.rental.repository.RentOrderRepo;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RentMachineFormController {
    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXComboBox<String>cmbCustomerId;

    @FXML
    private JFXComboBox<String>cmbMachineId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colMachineID;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblUnitprice;

    @FXML
    private TableView<TblOrderCartTm> tblOrderCart;



    private ObservableList<TblOrderCartTm> TblOrdercartList = FXCollections.observableArrayList();
    private double netTotal = 0;
    public void initialize() {
        setCellValueFactory();
        loadNextOrderId();
        setDate();
        getCustomerIds();
        getItemCodes();
    }

    private void setCellValueFactory() {
        colMachineID.setCellValueFactory(new PropertyValueFactory<>("colMachineID"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("colDescription"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("colUnitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("colTotal"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("colAction"));
    }

    private void getItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<Machine> machineList = MachineRepo.getAll();

            for (Machine machine : machineList) {
                if (machine.getIsAvaiable().equals("1")){
                    obList.add(machine.getM_Id());
                }
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
            cmbCustomerId.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }

    }

    private void loadNextOrderId() {
        try {
            String currentId = RentOrderRepo.currentId();
            String nextId = nextId(currentId);

            lblOrderId.setText(nextId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("O");
//            System.out.println("Arrays.toString(split) = " + Arrays.toString(split));
            int id = Integer.parseInt(split[1]);    //2
            return "O" + ++id;

        }
        return "O1";
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));
    }
    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String code = (String) cmbMachineId.getValue();
        String description = lblDescription.getText();
        double unitPrice = Double.parseDouble(lblUnitprice.getText());
        double total = unitPrice;
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblOrderCart.getSelectionModel().getSelectedIndex();
                TblOrdercartList.remove(selectedIndex);

                tblOrderCart.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            if (code.equals(colMachineID.getCellData(i))) {




                TblOrdercartList.get(i).getColTotal();

                tblOrderCart.refresh();
                calculateNetTotal();

                return;
            }
        }

        TblOrderCartTm cartTm = new TblOrderCartTm(
                code,
                description,
                unitPrice,
                total,
                btnRemove);

        TblOrdercartList.add(cartTm);

        tblOrderCart.setItems(TblOrdercartList);
        calculateNetTotal();
    }
    private void calculateNetTotal() {
        netTotal = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = lblOrderId.getText();
        Date date = Date.valueOf(LocalDate.now());
        Date returnDate = Date.valueOf(LocalDate.now().plusDays(7));
        String cusId = (String) cmbCustomerId.getValue();
        double total = Double.parseDouble(lblNetTotal.getText());

        var RentOrder = new RentOrder(orderId, date, returnDate, cusId, total   );

        List<OrderDetail> odList = new ArrayList<>();
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
           TblOrderCartTm tm = TblOrdercartList.get(i);

            OrderDetail od = new OrderDetail(
                    orderId,
                    tm.getColMachineID()
            );
            odList.add(od);
        }

        PlaceOrder po = new PlaceOrder(RentOrder, odList);
        try {
            boolean isPlaced = PlaceOrderRepo.placeOrder(po);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "order placed!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "order not placed!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {

        String customerId =cmbCustomerId.getValue();
        try {
    Customer name = CustomerRepo.searchByemail(customerId);
            lblCustomerName.setText(name.getC_name());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbMachineIdOnAction(ActionEvent event) {
        String code = cmbMachineId.getValue();
        try {
           Machine machine =MachineRepo.searchById(code);
            if (machine != null) {
                lblDescription.setText(machine.getM_desc());
                lblUnitprice.setText(String.valueOf(machine.getM_rental_price()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }



}
