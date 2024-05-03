package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.rental.util.DateTimeUtil;

import java.io.IOException;

public class DashboardFormController {

    public AnchorPane rootNode;
    @FXML
    private ImageView IconBack;

    @FXML
    private ImageView IconHome;

    @FXML
    private JFXButton btnAdmin;

    @FXML
    private JFXButton btnBokking;

    @FXML
    private JFXButton btnBuildingMaterial;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnMachine;

    @FXML
    private JFXButton btnReservation;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private JFXButton btnmechanic;

    @FXML
    private JFXButton btnRentMachine;
    @FXML
    private Label lblTime;

    @FXML
    private TableView<?> tblDash;

    @FXML
    private TableColumn<?, ?> tblDashboard;

    public void initialize() {
        realTime();
    }

    @FXML
    void BtnBokkingOnAction(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) btnBokking.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/bokking_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Bokking Form");

        stage.show();
    }

    @FXML
    void btnAdminOnAction(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) btnBokking.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/admin_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Admin Form");

        stage.show();
    }

    @FXML
    void btnBuildingMaterialOnAction(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) btnBokking.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/building_material_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Building_Material Form");

        stage.show();
    }
    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) btnBokking.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Customer Form");

        stage.show();
    }

    @FXML
    void btnMachineOnAction(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) btnBokking.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/machine_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Machine Form");

        stage.show();
    }

    @FXML
    void btnMechanicOnAction(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) btnBokking.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/mechanic_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Mechanic Form");

        stage.show();
    }

    @FXML
    void btnReservationOnAction(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) btnBokking.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/reservation_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Reservation Form");

        stage.show();
    }
    @FXML
    void btnRentMachineOnAction(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) btnBokking.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/rentMachine_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Supplier Form");

        stage.show();
    }
    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) btnBokking.getScene().getWindow();
        stage1.close();
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Supplier Form");

        stage.show();
    }
    public void realTime(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> lblTime.setText(DateTimeUtil.timenow())));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        lblTime.setText(DateTimeUtil.dateNow());
    }

    public void IconBackOnAction(MouseEvent mouseEvent) {
    }
    public void IconHomeOnAction(MouseEvent mouseEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Dashboard Form");

        stage.show();
    }
}
