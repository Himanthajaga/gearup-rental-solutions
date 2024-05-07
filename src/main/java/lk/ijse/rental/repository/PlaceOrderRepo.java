package lk.ijse.rental.repository;

import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.model.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderRepo {
    public static boolean placeOrder(PlaceOrder po) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = RentOrderRepo.save(po.getRentorder());
            System.out.println("isOrderSaved: " + isOrderSaved);
            if (isOrderSaved) {
                boolean isOrderDetailSaved = OrderDetailRepo.save(po.getOdList());
                System.out.println("isOrderDetailSaved: " + isOrderDetailSaved);
                if (isOrderDetailSaved) {
                    boolean isAvailable = MachineRepo.isAvailable(po.getOdList());
                    System.out.println("isItemQtyUpdate: " + isAvailable);
                    if (isAvailable) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
