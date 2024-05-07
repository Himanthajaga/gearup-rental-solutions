package lk.ijse.rental.repository;

import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.model.Machine;
import lk.ijse.rental.model.Mechanic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MechanicRepo {
    public static boolean save(Mechanic mechanic) throws SQLException, ClassNotFoundException {
//        In here you can now save your customer
        String sql = "INSERT INTO mechanic VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, mechanic.getMec_id());
        pstm.setString(2, mechanic.getMec_name());
        pstm.setString(3, mechanic.getMec_address());
        pstm.setString(4, mechanic.getMec_tel());
        pstm.setString(5, mechanic.getMec_desc());
        pstm.setString(6, mechanic.getMec_salary());






        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }

    public static boolean update(Mechanic mechanic) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE mechanic SET mec_name = ?,mec_address = ?,mec_tele = ?,mec_desc = ?,mec_salary WHERE mec_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);


        pstm.setString(1, mechanic.getMec_name());
        pstm.setString(2, mechanic.getMec_address());
        pstm.setString(3, mechanic.getMec_tel());
        pstm.setString(4, mechanic.getMec_desc());
        pstm.setString(5, mechanic.getMec_salary());
        pstm.setString(6, mechanic.getMec_id());
        return pstm.executeUpdate() > 0;
    }
    public static Mechanic searchById(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM mechanic WHERE mec_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,id);
        ResultSet resultSet = pstm.executeQuery();

        Mechanic mechanic = null;

        if (resultSet.next()) {
            String mec_id = resultSet.getString(1);
            String mec_name = resultSet.getString(2);
            String mec_address = resultSet.getString(3);
            String mec_tele = resultSet.getString(4);
            String mec_desc = resultSet.getString(5);
            String mec_salary = resultSet.getString(6);
            Mechanic mechanic1 = new Mechanic(mec_id, mec_name, mec_address, mec_tele, mec_desc, mec_salary);

        }
        return mechanic;
    }

    public static boolean delete(String mec_id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM mechanic WHERE mec_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, mec_id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Mechanic> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM mechanic";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Mechanic> mechanicList = new ArrayList<>();
        while (resultSet.next()) {
            String mec_id = resultSet.getString(1);
            String mec_name = resultSet.getString(2);
            String mec_address = resultSet.getString(3);
            String mec_tele = resultSet.getString(4);
            String mec_desc = resultSet.getString(5);
            String mec_salary = resultSet.getString(6);
           Mechanic mechanic = (new Mechanic(mec_id, mec_name, mec_address, mec_tele, mec_desc, mec_salary));


            mechanicList.add(mechanic);
        }
        return mechanicList;
    }

    public static List<String> getIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT mec_id FROM mechanic";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}
