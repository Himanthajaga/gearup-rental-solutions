package lk.ijse.rental.repository;

import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.model.Machine;
import lk.ijse.rental.model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineRepo {
public static  String m_id;
    public static boolean save(Machine machine) throws SQLException, ClassNotFoundException {
//        In here you can now save your machine
        String sql = "INSERT INTO machine VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, machine.getM_Id());
        pstm.setString(2, machine.getM_Name());
        pstm.setString(3, machine.getM_desc());
        pstm.setString(4, machine.getM_rental_price());
        pstm.setString(5, machine.getIsAvaiable());
        pstm.setString(6, MachineRepo.m_id);






        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }
    public static boolean Available(Machine machine){
        if(machine.getIsAvaiable().equals("Yes")){
            return true;
        }

        return false;
    }
    public static boolean checkAvailability(String machineId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT isAvailable FROM machine WHERE m_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,machineId);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            return resultSet.getString(1).equals("1");
        }
        return false;
    }
    public static boolean isAvailable(List<OrderDetail> odList) throws SQLException, ClassNotFoundException {
        for (OrderDetail orderDetail : odList) {
            if (checkAvailability(orderDetail.getMachineId())) {
               updateAvailable(orderDetail.getMachineId());
            return true;
            }
            return false;
        }
        return false;
    }

    private static void updateAvailable(String machine) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE machine SET isAvailable = ? WHERE m_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,"0");
        pstm.setObject(2,machine);
        pstm.executeUpdate();
    }

    public static boolean update(Machine machine) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE machine SET m_id = ?, m_name = ?,m_desc = ?,m_rental_price = ?,isAvailable = ? WHERE m_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, machine.getM_Id());
        pstm.setString(2, machine.getM_Name());
        pstm.setString(3, machine.getM_desc());
        pstm.setString(4, machine.getM_rental_price());
        pstm.setString(5, machine.getIsAvaiable());
        return pstm.executeUpdate() > 0;
    }
    public static Machine searchById(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM machine WHERE m_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,id);
        ResultSet resultSet = pstm.executeQuery();

        Machine machine = null;

        if (resultSet.next()) {
            String m_id = resultSet.getString(1);
            String m_name = resultSet.getString(2);
            String m_desc = resultSet.getString(3);
            String m_rental_price = resultSet.getString(4);
            String isAvailable = resultSet.getString(5);

           Machine machine1 = new Machine(m_id, m_name, m_desc, m_rental_price,isAvailable);

            machine = machine1;

        }
        return machine;
    }

    public static boolean delete(String m_id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM machine WHERE m_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, m_id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Machine> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM machine";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Machine> machineList = new ArrayList<>();
        while (resultSet.next()) {
            String m_id = resultSet.getString(1);
            String m_name = resultSet.getString(2);
            String m_desc = resultSet.getString(3);
            String m_rental_price = resultSet.getString(4);
            String isAvailable = resultSet.getString(5);

          Machine machine = new Machine(m_id, m_name, m_desc,m_rental_price,isAvailable);


            machineList.add(machine);
        }
        return machineList;
    }

    public static List<String> getIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT m_id FROM machine";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}
