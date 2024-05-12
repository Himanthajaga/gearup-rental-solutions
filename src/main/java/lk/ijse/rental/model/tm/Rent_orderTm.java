package lk.ijse.rental.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rent_orderTm {
    private String colOrderId;
    private Date colOrderDate;
    private Date colReturnDate;
    private String colCEmail1;
    private double colTotal;
}
