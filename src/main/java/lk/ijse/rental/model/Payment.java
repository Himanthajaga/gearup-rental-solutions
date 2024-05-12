package lk.ijse.rental.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private String paymentId;
    private String paymentType;
    private String s_email;
    private double paymentAmount;

}
