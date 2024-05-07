package lk.ijse.rental.model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TblMaterialcartTm {
    private String colMaterialId;
    private String colSellingDate;
    private String colCustomerEmail;
    private String colMaterialDescription;
    private double colUnitPrice;
    private double colQty;
    private double colTotal;
    private JFXButton colAction;

}
