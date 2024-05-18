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
import javafx.scene.input.KeyEvent;
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
import java.util.regex.Pattern;

public class ReservationFormController {
    private JFXButton btnClear;
    @FXML
    private TextField txtReservationIdnew;
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
    private TableColumn<?, ?> colReservationDate;
    @FXML
    private DatePicker reseerDate;
    @FXML
    private TextField txtReservationType;
    @FXML
    private Label txtReservationId;
    private List<Reservation> reservationList = new ArrayList<>();
    public void initialize() {

        this.reservationList=getAllReservations();
        setCellValueFactory();
        loadReservationTable();
        loadNextReservationid();
    }

    private void loadNextReservationid() {
        if (reservationList.size() <= 0) {
            txtReservationId.setText("R001");
        } else {
            Reservation lastMechanic = reservationList.get(reservationList.size() - 1);
            String lastId = lastMechanic.getR_id();
            String[] split = lastId.split("R");
            int id = Integer.parseInt(split[1]);
            id = id + 1;
            if (id < 10) {
                txtReservationId.setText("R00" + id);
            } else if (id < 100) {
                txtReservationId.setText("R0" + id);
            } else {
                txtReservationId.setText("R" + id);
            }
        }
    }


    private void setCellValueFactory() {
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("colReservationId"));
        colReservationType.setCellValueFactory(new PropertyValueFactory<>("colReservationType"));
        colReservationDate.setCellValueFactory(new PropertyValueFactory<>("colReservationDate"));




    }
    private void loadReservationTable() {

        ObservableList<ReservationTm> tmList = FXCollections.observableArrayList();

        for (Reservation reservation : reservationList) {
            ReservationTm reservationTm = new ReservationTm(
                   reservation.getR_id(),
                    reservation.getR_type(),
                    reservation.getR_date()


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
        String id = txtReservationIdnew.getText();
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
        String date = reseerDate.getValue().toString();
        if (id.trim().length() == 0 || name.trim().length() == 0) {
            new Alert(Alert.AlertType.WARNING, "Empty Fields").show();
            return;
        }
        Reservation reservation = new Reservation(id, name, date);
        try {
            boolean isSaved = ReservationRepo.save(reservation);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Saved..").show();
                reservationList.add(reservation);
                loadReservationTable();
                clearFields();
                loadNextReservationid();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void btnUpdateReservationOnAction(ActionEvent event) {
        String id = txtReservationIdnew.getText();
        String name = txtReservationType.getText();


        Reservation reservation = new Reservation(id, name, reseerDate.getValue().toString());
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
    txtReservationId.setText("");
    txtReservationType.clear();

    }
//    @FXML
//    void txtReserReleasedOnAction(KeyEvent event) {
//        Pattern idPattern = Pattern.compile("^(R)[0-9]{1,}$");
//        if (!idPattern.matcher(txtReservationId.getText()).matches()) {
//            addError(txtReservationId);
//
//        }else{
//            removeError(txtReservationId);
//        }
//    }

    private void removeError(TextField txtReservationId) {
        txtReservationId.setStyle("-fx-border-color: green; -fx-border-width: 5");
    }

    private void addError(TextField txtReservationId) {
        txtReservationId.setStyle("-fx-border-color: red; -fx-border-width: 5");
    }

    @FXML
    void txtReserTypeReleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z ]*$");
        if (!idPattern.matcher(txtReservationType.getText()).matches()) {
            addError(txtReservationType);

        }else{
            removeError(txtReservationType);
        }
    }

    public void relesed(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^(R)[0-9]{1,}$");
        if (!idPattern.matcher(txtReservationIdnew.getText()).matches()) {
            addError(txtReservationIdnew);

        }else{
            removeError(txtReservationIdnew);
        }
    }
}
