package lk.ijse.rental.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.rental.model.Payment;
import lk.ijse.rental.model.tm.PaymentTm;
import lk.ijse.rental.repository.PaymentRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentFormController {
    private List<Payment> paymentList = new ArrayList<>();
    @FXML
    private TableColumn<?, ?> colPaymentAmount;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colPaymentType;

    @FXML
    private TableView<PaymentTm> tblPayment;
    @FXML
    private TableColumn<?, ?> colCid;
    public void initialize() {
        this.paymentList=getALLPayments();
        setCellValueFactory();
        loadPaymentTable();
    }
    private List<Payment> getALLPayments() {
        List<Payment>paymentList = null;
        try {
           paymentList = PaymentRepo.getAll();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return paymentList;
    }
    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("colPaymentId"));
        colPaymentAmount.setCellValueFactory(new PropertyValueFactory<>("colPaymentAmount"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("colPaymentType"));
        colCid.setCellValueFactory(new PropertyValueFactory<>("colCid"));

    }
    private void loadPaymentTable() {

        ObservableList<PaymentTm> tmList = FXCollections.observableArrayList();

        for (Payment payment : paymentList) {
            PaymentTm paymentTm= new PaymentTm(
                    payment.getPaymentId(),
                    payment.getPaymentAmount(),
                    payment.getPaymentType(),
                    payment.getCid()


            );
            System.out.println("machineTm = " + paymentTm);
            tmList.add(paymentTm);
        }
        tblPayment.setItems(tmList);
        PaymentTm selectedItem = (PaymentTm) tblPayment.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }
}
