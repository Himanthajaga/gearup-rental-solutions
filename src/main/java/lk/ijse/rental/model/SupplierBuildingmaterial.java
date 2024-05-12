package lk.ijse.rental.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierBuildingmaterial {
    private String bm_id;
    private String s_email;
    private String amount;
    private String bm_desc;
}
