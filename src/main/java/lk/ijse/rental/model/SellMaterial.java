package lk.ijse.rental.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellMaterial {
   private String sellId;
   private String bmId;
    private String bm_unit_price;
   private String bm_qty;
}
