package lk.ijse.rental.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TblOrderCart {
    private String colMachineID;
    private String colDescription;
    private String colQty;
    private String colUnitPrice;
    private String colTotal;
    private String colAction;
}
