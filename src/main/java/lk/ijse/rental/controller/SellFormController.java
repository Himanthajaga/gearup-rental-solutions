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
import javafx.scene.input.KeyEvent;
import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.model.*;
import lk.ijse.rental.model.tm.TblMaterialcartTm;
import lk.ijse.rental.repository.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class SellFormController {
    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnPay;

    @FXML
    private JFXComboBox<String> cmbCustomerEmail;

    @FXML
    private JFXComboBox<String> cmbMaterialId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCustomerEmail;

    @FXML
    private TableColumn<?, ?> colMaterialDescription;

    @FXML
    private TableColumn<?, ?> colMaterialId;

    @FXML
    private TableColumn<?, ?> colSellingDate;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;
    @FXML
    private JFXButton btnPrintBill;
    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblMaterialDescription;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblSellId;

    @FXML
    private Label lblUnitprice;

    @FXML
    private TableView<TblMaterialcartTm> tblSellBuildingMaterial;

    @FXML
    private TextField txtMaterialQty;

    private ObservableList<TblMaterialcartTm> TblMaterialcartList = FXCollections.observableArrayList();
    private double netTotal = 0;
    public void initialize() {
        setCellValueFactory();
        loadNextSellId();
        setDate();
        getCustomerIds();
        getItemCodes();
    }

    private void getItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = BuildingMaterialRepo.getIds();
            for (String code : codeList) {
                obList.add(code);
            }

            cmbMaterialId.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }
    @FXML
    void btnPrintBillOnAction(ActionEvent event) throws JRException, SQLException, ClassNotFoundException {
        HashMap hashMap = new HashMap();
        hashMap.put("sellID", lblSellId.getText());
        hashMap.put("build_Des", lblMaterialDescription.getText());
        hashMap.put("soldAmount", txtMaterialQty.getText());
        hashMap.put("customerName", lblCustomerName.getText());
        hashMap.put("netTotal", lblNetTotal.getText());


        InputStream resourceAsStream = this.getClass().getResourceAsStream("/reports/sell.jrxml");
        JasperDesign jasperDesign = JRXmlLoader.load(resourceAsStream);

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                hashMap,
                new JREmptyDataSource()
        );

        JasperViewer.viewReport(jasperPrint, false);
    }
    private void getCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = CustomerRepo.getEmails();

            for (String id : idList) {
                obList.add(id);
            }
            cmbCustomerEmail.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));
    }

    private void loadNextSellId() {
        try {
            String currentId = SellRepo.currentId();
            String nextId = nextId(currentId);

            lblSellId.setText(nextId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
           int currentIdNum = Integer.parseInt(currentId.replace("S", ""));
            currentIdNum = currentIdNum + 1;

            if (currentIdNum < 10) {
                return "S00" + currentIdNum;
            } else if (currentIdNum < 100) {
                return "S0" + currentIdNum;
            } else {
                return "S" + currentIdNum;
            }
    }

    private void setCellValueFactory() {
       colMaterialId.setCellValueFactory(new PropertyValueFactory<>("colMaterialId"));
       colSellingDate.setCellValueFactory(new PropertyValueFactory<>("colSellingDate"));
       colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("colCustomerEmail"));
     colMaterialDescription.setCellValueFactory(new PropertyValueFactory<>("colMaterialDescription"));
      colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("colUnitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("colTotal"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("colAction"));
    }
    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String code = (String) cmbMaterialId.getValue();
        String sellingDate = lblOrderDate.getText();
        String customerEmail = cmbCustomerEmail.getValue();
        String description = lblMaterialDescription.getText();
        double unitPrice = Double.parseDouble(lblUnitprice.getText());
       double qty = Double.parseDouble(txtMaterialQty.getText());
        double total = unitPrice*qty;
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblSellBuildingMaterial.getSelectionModel().getSelectedIndex();
               TblMaterialcartList.remove(selectedIndex);

                tblSellBuildingMaterial.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblSellBuildingMaterial.getItems().size(); i++) {
            if (code.equals(colMaterialId.getCellData(i))) {


                TblMaterialcartList.get(i).getColTotal();

                tblSellBuildingMaterial.refresh();
                calculateNetTotal();
                txtMaterialQty.setText("");
                lblQtyOnHand.setText("");

                return;
            }
        }

        TblMaterialcartTm cartTm = new TblMaterialcartTm(
                code,
                sellingDate,
                customerEmail,
                description,
                unitPrice,
                qty,
                total,
                btnRemove);

        TblMaterialcartList.add(cartTm);

        tblSellBuildingMaterial.setItems(TblMaterialcartList);
        calculateNetTotal();
    }

    private void calculateNetTotal() {
        netTotal = 0;
        for (int i = 0; i < tblSellBuildingMaterial.getItems().size(); i++) {
            TblMaterialcartTm tm = TblMaterialcartList.get(i);
            netTotal += tm.getColTotal();
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnClearSellBuildingMaterialOnAction(ActionEvent event) {
    clearfields();
    }

    private void clearfields() {
        cmbMaterialId.setValue(null);
        cmbCustomerEmail.setValue(null);
        lblMaterialDescription.setText("");
        lblUnitprice.setText("");
        txtMaterialQty.clear();
        lblQtyOnHand.setText("");
    }

    @FXML
    void btnPayOnAction(ActionEvent event) {
        String sellId = lblSellId.getText();
        Date date = Date.valueOf(LocalDate.now());
        String cusEmail = cmbCustomerEmail.getValue();
        double total = Double.parseDouble(lblNetTotal.getText());

        var Sell = new Sell(sellId, date, cusEmail, total);

        List<SellMaterial> odList = new ArrayList<>();
        for (int i = 0; i < tblSellBuildingMaterial.getItems().size(); i++) {
            TblMaterialcartTm tm =TblMaterialcartList.get(i);

            SellMaterial od = new SellMaterial(
                    sellId,
                    tm.getColMaterialId(),
                   String.valueOf(tm.getColUnitPrice()),
                    String.valueOf(tm.getColQty())
            );
            odList.add(od);
        }

        PlaceSell po = new PlaceSell(Sell, odList);
        try {
            boolean isPlaced = PlaceSellRepo.PlaceSell(po);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Sell placed!").show();
                loadNextSellId();
            } else {
                new Alert(Alert.AlertType.WARNING, "Sell not placed!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {
        String email = cmbCustomerEmail.getValue();
        try {
            Customer customer = CustomerRepo.searchByemail(email);
            if (customer != null) {
                lblCustomerName.setText(customer.getC_name());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer is not found!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbMaterialIdOnaction(ActionEvent event) {
        String code = cmbMaterialId.getValue();
        try {
            BuildingMaterial buildingMaterial = BuildingMaterialRepo.searchById(code);
            if (buildingMaterial != null) {
                lblMaterialDescription.setText(buildingMaterial.getBm_desc());
                lblUnitprice.setText(String.valueOf(buildingMaterial.getBm_price()));
                lblQtyOnHand.setText(buildingMaterial.getBm_qty());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }
    @FXML
    void QTYRELEASED(KeyEvent event) {
        if (txtMaterialQty.getText().matches("[0-9]+")) {
            double qty = Double.parseDouble(txtMaterialQty.getText());
            double qtyOnHand = Double.parseDouble(lblQtyOnHand.getText());
            if (qty > qtyOnHand) {
                new Alert(Alert.AlertType.INFORMATION, "Invalid qty!").show();
                txtMaterialQty.clear();
                txtMaterialQty.requestFocus();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Invalid qty!").show();
            txtMaterialQty.clear();
            txtMaterialQty.requestFocus();
        }

    }


}
