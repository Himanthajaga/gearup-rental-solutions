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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.rental.model.BuildingMaterial;
import lk.ijse.rental.model.tm.BuildingMaterialTm;
import lk.ijse.rental.qrGenerate.QrcodeForMachine;
import lk.ijse.rental.repository.BuildingMaterialRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuildingMaterialFormController {
    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDeleteMaterial;

    @FXML
    private JFXButton btnSaveMaterial;

    @FXML
    private JFXButton btnUpdateMaterial;

    @FXML
    private TableColumn<?, ?> colMaterialId;

    @FXML
    private TableColumn<?, ?> colMaterialPrice;

    @FXML
    private TableColumn<?, ?> colMaterialQty;

    @FXML
    private TableColumn<?, ?> colMaterialType;

    @FXML
    private TableColumn<?, ?> colMaterialdescription;

    @FXML
    private TableView<BuildingMaterialTm> tblMaterial;

    @FXML
    private TextField txtMateriaDescription;

    @FXML
    private TextField txtMaterialId;

    @FXML
    private TextField txtMaterialPrice;

    @FXML
    private TextField txtMaterialQty;

    @FXML
    private TextField txtMaterialType;
    private List<BuildingMaterial> bmList = new ArrayList<>();
    private QrcodeForMachine qrcodeForUser = new QrcodeForMachine();

    public void initialize() {

        this.bmList = getAllBuildingMaterials();
        setCellValueFactory();
        loadBuildingMaterialTable();
    }

    private void loadBuildingMaterialTable() {
        ObservableList<BuildingMaterialTm> tmList = FXCollections.observableArrayList();

        for (BuildingMaterial buildingMaterial : bmList) {
            BuildingMaterialTm buildingMaterialTm = new BuildingMaterialTm(
                    buildingMaterial.getBm_id(),
                    buildingMaterial.getBm_desc(),
                    buildingMaterial.getBm_type(),
                    buildingMaterial.getBm_price(),
                    buildingMaterial.getBm_qty()

            );

            tmList.add(buildingMaterialTm);
        }
        tblMaterial.setItems(tmList);
        BuildingMaterialTm selectedItem = tblMaterial.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setCellValueFactory() {
        colMaterialId.setCellValueFactory(new PropertyValueFactory<>("colMaterialId"));
        colMaterialdescription.setCellValueFactory(new PropertyValueFactory<>("colMaterialdescription"));
        colMaterialType.setCellValueFactory(new PropertyValueFactory<>("colMaterialType"));
        colMaterialPrice.setCellValueFactory(new PropertyValueFactory<>("colMaterialPrice"));
        colMaterialQty.setCellValueFactory(new PropertyValueFactory<>("colMaterialQty"));

    }


    private List<BuildingMaterial> getAllBuildingMaterials() {
        List<BuildingMaterial> bmList = null;
        try {
            bmList = BuildingMaterialRepo.getAll();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return bmList;
    }


    @FXML
    void btnDeleteMaterialOnAction(ActionEvent event) {
        String id = txtMaterialId.getText();

        try {
            boolean isDeleted = BuildingMaterialRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Material deleted!").show();
                loadBuildingMaterialTable();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnSaveMaterialOnAction(ActionEvent event) {
        String MaterialId = txtMaterialId.getText();
        String MaterialDesc = txtMateriaDescription.getText();
        String MaterialType = txtMaterialType.getText();
        String MaterialPrice = txtMaterialPrice.getText();
        String MaterialQty = txtMaterialQty.getText();


        BuildingMaterial buildingMaterial = new BuildingMaterial(MaterialId, MaterialDesc, MaterialType, MaterialPrice, MaterialQty);
        try {
            boolean isAdded = BuildingMaterialRepo.save(buildingMaterial);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                qrcodeForUser.CreateQr(MaterialId);
                bmList.add(buildingMaterial);
                loadBuildingMaterialTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnUpdateMaterialOnAction(ActionEvent event) {
        String MaterialId = txtMaterialId.getText();
        String MaterialDesc = txtMateriaDescription.getText();
        String MaterialType = txtMaterialType.getText();
        String MaterialPrice = txtMaterialPrice.getText();
        String MaterialQty = txtMaterialQty.getText();

        BuildingMaterial buildingMaterial = new BuildingMaterial(MaterialId, MaterialDesc, MaterialType, MaterialPrice, MaterialQty);
        try {
            boolean isUpdated = BuildingMaterialRepo.update(buildingMaterial);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                loadBuildingMaterialTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtMaterialId.getText();

        try {
            BuildingMaterial buildingMaterial = BuildingMaterialRepo.searchById(id);

            if (buildingMaterial != null) {
                txtMaterialId.setText(buildingMaterial.getBm_id());
                txtMateriaDescription.setText(buildingMaterial.getBm_desc());
                txtMaterialType.setText(buildingMaterial.getBm_type());
                txtMaterialPrice.setText(buildingMaterial.getBm_price());
                txtMaterialQty.setText(buildingMaterial.getBm_qty());
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearAdminOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtMaterialId.clear();
        txtMaterialType.clear();
        txtMateriaDescription.clear();
        txtMaterialPrice.clear();
        txtMaterialQty.clear();

    }


}
