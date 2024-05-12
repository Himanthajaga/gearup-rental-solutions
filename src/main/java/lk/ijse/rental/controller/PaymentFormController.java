package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.model.BuildingMaterial;
import lk.ijse.rental.model.Payment;
import lk.ijse.rental.model.Supplier;
import lk.ijse.rental.model.tm.PaymentTm;
import lk.ijse.rental.model.tm.TblPaymentCartTm;
import lk.ijse.rental.repository.BuildingMaterialRepo;
import lk.ijse.rental.repository.PaymentRepo;
import lk.ijse.rental.repository.SupplierRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentFormController {
    private List<Payment> paymentList = new ArrayList<>();

    @FXML
    private JFXButton btnPaySupplier;

    @FXML
    private JFXComboBox<String> cmbCustomerEmail;

    @FXML
    private TableColumn<?, ?> colMaterialName;

    @FXML
    private TableColumn<?, ?> colPaymentAmount;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colPaymentType;

    @FXML
    private TableColumn<?, ?> colSEmail;

    @FXML
    private TableColumn<?, ?> colSName;

    @FXML
    private Label lblMaterialDescription;

    @FXML
    private Label lblMaterialId;

    @FXML
    private Label lblOrderDate;

    @FXML
    private TextField lblPayType;

    @FXML
    private Label lblSellId;

    @FXML
    private Label lblSupplierName;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private TextField txtPaymentAmount;
    @FXML
    private JFXButton btnPrintBill;


    private ObservableList<TblPaymentCartTm> TblPaymentcartList = FXCollections.observableArrayList();
    public void initialize() {
        this.paymentList=getALLPayments();
        setCellValueFactory();
        loadPaymentTable();
        loadNextOrderId();
        setDate();
        getSupplierEmails();
    }

    private void getSupplierEmails() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = SupplierRepo.getemails();

            for (String id : idList) {
                obList.add(id);
            }
            cmbCustomerEmail.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }
    @FXML
    void btnPrintBillOnAction(ActionEvent event) throws JRException, SQLException, ClassNotFoundException {
        JasperDesign jasperDesign =
                JRXmlLoader.load("src/main/resources/reports/RentOrderReport.jrxml");
        JasperReport jasperReport =
                JasperCompileManager.compileReport(jasperDesign);

        Map<String, Object> data = new HashMap<>();
        data.put("OrderID",lblSellId.getLabelFor());

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        data,
                        DbConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint,false);
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));
    }

    private void loadNextOrderId() {
        try {
            String currentId = PaymentRepo.currentId();
            String nextId = nextId(currentId);

          lblSellId.setText(nextId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnPaySupplierOnAction(ActionEvent event) {
        String paymentId = lblSellId.getText();
        String paymentType = lblPayType.getText();
        String s_email = cmbCustomerEmail.getValue();
        String paymentAmount = txtPaymentAmount.getText();
        Payment payment = new Payment(paymentId, paymentType, s_email, Double.parseDouble(paymentAmount));
        try {
            boolean isAdded = PaymentRepo.save(payment);
            if (isAdded) {
                paymentList.add(payment);
                loadPaymentTable();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private String nextId(String currentId) {
        int currentIdNum = Integer.parseInt(currentId.replace("P", ""));
        currentIdNum = currentIdNum + 1;
        if (currentIdNum < 10) {
            return "P00" + currentIdNum;
        } else if (currentIdNum < 100) {
            return "P0" + currentIdNum;
        } else {
            return "P" + currentIdNum;
        }
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
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("colPaymentType"));
        colSEmail.setCellValueFactory(new PropertyValueFactory<>("colSEmail"));
        colPaymentAmount.setCellValueFactory(new PropertyValueFactory<>("colPaymentAmount"));

    }
    private void loadPaymentTable() {

        ObservableList<PaymentTm> tmList = FXCollections.observableArrayList();

        for (Payment payment : paymentList) {
            PaymentTm paymentTm= new PaymentTm(
                    payment.getPaymentId(),
                    payment.getPaymentType(),
                    payment.getS_email(),
                    payment.getPaymentAmount()


            );
            System.out.println("machineTm = " + paymentTm);
            tmList.add(paymentTm);
        }
        tblPayment.setItems(tmList);
        PaymentTm selectedItem = (PaymentTm) tblPayment.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }
    @FXML
    void cmbCustomerEmailOnAction(ActionEvent event) {
        String code = cmbCustomerEmail.getValue();
        try {
            BuildingMaterial buildingMaterial = BuildingMaterialRepo.searchByEmail(code);
            Supplier supplier = SupplierRepo.searchByEmail(code);
            if (buildingMaterial != null) {
                lblMaterialDescription.setText(buildingMaterial.getBm_desc());
                lblMaterialId.setText(String.valueOf(buildingMaterial.getBm_id()));
               lblSupplierName.setText(supplier.getS_name());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void paymentAmountOnReleased(KeyEvent event) {

    }
}
