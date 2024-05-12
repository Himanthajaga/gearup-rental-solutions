package lk.ijse.rental.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SupplierTm {
    private String colSupplierId;
    private String colSupplierName;
    private String colSupplierAddress;
    private String colSupplierTele;
    private String colSupplierEmail;

}
