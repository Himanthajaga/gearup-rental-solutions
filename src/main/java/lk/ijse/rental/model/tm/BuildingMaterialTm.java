package lk.ijse.rental.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingMaterialTm {
    private String colMaterialId;
    private String colMaterialdescription;
    private String colMaterialType;
    private String colMaterialPrice;
    private String colMaterialQty;


}
