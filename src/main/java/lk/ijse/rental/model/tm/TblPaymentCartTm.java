package lk.ijse.rental.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TblPaymentCartTm {
    private String colPaymentId;
    private String colPaymentType;
     private String colSEmail;
     private String colSName;
        private String colMaterialName;
        private double colPaymentAmount;
}
