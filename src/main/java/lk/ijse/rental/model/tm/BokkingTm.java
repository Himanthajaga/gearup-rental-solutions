package lk.ijse.rental.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BokkingTm {
    private String colBokkingId;
    private String colBokkingDate;
    private String colCustomerEmail;
    private String colMachineId;

}
