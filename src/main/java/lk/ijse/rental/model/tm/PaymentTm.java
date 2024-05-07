package lk.ijse.rental.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTm {
    private String colPaymentId;
    private String colPaymentAmount;
    private String colPaymentType;
    private String colCid;

}
