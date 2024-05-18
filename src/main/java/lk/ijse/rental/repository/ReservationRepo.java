package lk.ijse.rental.repository;

import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.model.Admin;
import lk.ijse.rental.model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepo {
    public static boolean save(Reservation reservation) throws SQLException, ClassNotFoundException {
//        In here you can now save your reservation
        String sql = "INSERT INTO reservation VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, reservation.getR_id());
        pstm.setObject(2, reservation.getR_type());
        pstm.setObject(3, reservation.getR_date());




        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }

    public static boolean update(Reservation reservation) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE reservation SET r_type = ?,r_date=? WHERE r_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);


        pstm.setObject(1, reservation.getR_type());
        pstm.setObject(2, reservation.getR_id());
        pstm.setObject(3, reservation.getR_date());

        return pstm.executeUpdate() > 0;
    }

    public static Reservation searchById(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM reservation WHERE r_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Reservation reservation = null;

        if (resultSet.next()) {
            String r_id = resultSet.getString(1);
            String r_type =resultSet.getString(2);
            String r_date = resultSet.getString(3);




            reservation = new Reservation(r_id, r_type, r_date);
        }
        return reservation;
    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM reservation WHERE r_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Reservation> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM reservation";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Reservation> reservationList = new ArrayList<>();
        while (resultSet.next()) {
            String r_id = resultSet.getString(1);
            String r_type = resultSet.getString(2);
            String r_date = resultSet.getString(3);




            Reservation reservation = new Reservation(r_id, r_type, r_date);
            reservationList.add(reservation);
        }
        return reservationList;
    }

    public static List<String> getIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT r_id FROM reservation";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

}
