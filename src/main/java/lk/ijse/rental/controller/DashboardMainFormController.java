package lk.ijse.rental.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.rental.util.DateTimeUtil;

import java.io.IOException;
import java.time.LocalTime;

public class DashboardMainFormController {
    @FXML
    private JFXButton btnSell;
    @FXML
    private Label lblName;
    @FXML
    private ImageView IconBack;
    @FXML
    private ImageView btnExit;

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
    private JFXButton btnRentMachine;

    @FXML
    private JFXButton btnReservation;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private JFXButton btnmechanic;

    @FXML
    private AnchorPane paneHolder;
    @FXML
    private JFXButton btnReturn;
    @FXML
    private AnchorPane rootNode;
    @FXML
    private Label lblSetTime;
    @FXML
    void btnReturnOnActionOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/retunMachine_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }

    public void realTime(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> lblSetTime.setText(DateTimeUtil.timenow())));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        lblSetTime.setText(DateTimeUtil.dateNow());
    }
    private void setGreetings() {
        LocalTime currentTime = LocalTime.now();
        String greeting = (currentTime.getHour() < 12) ? "Good Morning Welcome!" : "Good Evening Welcome!";
        lblName.setText(greeting);
    }
    public void initialize() throws IOException {

       // setAdminName();
        setGreetings();
        realTime();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }

//    private void setAdminName() {
//        lblName.setText("Hello     "+ AdminRepo.adminId();
//    }

    @FXML
    void BtnBokkingOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/bokking_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }

    @FXML
    void IconBackOnAction(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }

    @FXML
    void IconHomeOnAction(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }

    @FXML
    void btnAdminOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/admin_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }

    @FXML
    void btnBuildingMaterialOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/building_material_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }
    @FXML
    void btnExitOnAction(MouseEvent event) {
System.exit(0);
    }
    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/customer_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }
    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        if (new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to logout?").showAndWait().get().equals(ButtonType.OK)){
            Stage stage1 = (Stage)btnLogout.getScene().getWindow();
            stage1.close();
            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.setTitle("login form");

            stage.show();
        }
    }
    @FXML
    void btnSellOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/sell_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }

    @FXML
    void btnMachineOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/machine_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }

    @FXML
    void btnMechanicOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/mechanic_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }

    @FXML
    void btnRentMachineOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/rentMachine_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }

    @FXML
    void btnReservationOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/reservation_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/supplier_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }
}
