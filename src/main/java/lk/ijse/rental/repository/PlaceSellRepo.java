package lk.ijse.rental.repository;

import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.model.PlaceSell;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceSellRepo {
    public static boolean PlaceSell(PlaceSell po) throws SQLException, ClassNotFoundException {

        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {

            boolean isSellSaved = SellRepo.save(po.getSell());

            if (isSellSaved) {
                boolean isSellMaterialSaved = SellMaterialRepo.save(po.getOdList());


                if (isSellMaterialSaved) {

                    boolean isItemQtyUpdate = BuildingMaterialRepo.updateQty(po.getOdList());

                    System.out.println(isItemQtyUpdate);

                    if (isItemQtyUpdate) {

                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
