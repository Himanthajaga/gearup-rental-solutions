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
import lk.ijse.rental.model.Mechanic;
import lk.ijse.rental.model.Reservation;
import lk.ijse.rental.model.tm.ReservationTm;
import lk.ijse.rental.repository.MechanicRepo;
import lk.ijse.rental.repository.ReservationRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationFormController {
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDeleteReservation;

    @FXML
    private JFXButton btnSaveReservation;

    @FXML
    private JFXButton btnUpdateReservation;

    @FXML
    private TableColumn<?, ?> colReservationId;

    @FXML
    private TableColumn<?, ?> colReservationType;

    @FXML
    private TableView<ReservationTm> tblReservation;

    @FXML
    private TextField txtReservationId;

    @FXML
    private TextField txtReservationType;
    private List<Reservation> reservationList = new ArrayList<>();
    public void initialize() {

        this.reservationList=getAllReservations();
        setCellValueFactory();
        loadReservationTable();
    }


    private void setCellValueFactory() {
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("colReservationId"));
        colReservationType.setCellValueFactory(new PropertyValueFactory<>("colReservationType"));




    }
    private void loadReservationTable() {

        ObservableList<ReservationTm> tmList = FXCollections.observableArrayList();

        for (Reservation reservation : reservationList) {
            ReservationTm reservationTm = new ReservationTm(
                   reservation.getR_id(),
                    reservation.getR_type()


            );
            System.out.println("machineTm = " + reservationTm);
            tmList.add(reservationTm);
        }
        tblReservation.setItems(tmList);
        ReservationTm selectedItem = (ReservationTm) tblReservation.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }


    private List<Reservation> getAllReservations() {
        List<Reservation> reservationList = null;
        try {
            reservationList = ReservationRepo.getAll();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return reservationList;
    }


    @FXML
    void btnDeleteReservationOnAction(ActionEvent event) {
        String id = txtReservationId.getText();
        try {
            boolean isDeleted = ReservationRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Deleted..").show();
                reservationList.removeIf(reservation -> reservation.getR_id().equals(id));
                loadReservationTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void btnSaveReservationOnAction(ActionEvent event) {
        String id = txtReservationId.getText();
        String name = txtReservationType.getText();

        Reservation reservation = new Reservation(id, name);
        try {
            boolean isSaved = ReservationRepo.save(reservation);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Saved..").show();
                reservationList.add(reservation);
                loadReservationTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void btnUpdateReservationOnAction(ActionEvent event) {
        String id = txtReservationId.getText();
        String name = txtReservationType.getText();


        Reservation reservation = new Reservation(id, name);
        try {
            boolean isUpdated = ReservationRepo.update(reservation);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                reservationList.add(reservation);
                loadReservationTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnClearCustomerOnAction(ActionEvent event) {
    clearFields();
    }

    private void clearFields() {
    txtReservationId.clear();
    txtReservationType.clear();

    }

}
