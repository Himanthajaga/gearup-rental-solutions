package lk.ijse.rental.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentOrder {
    private String orderId;
    private Date date;
    private Date returnDate;
    private String customerEmail;
    private double total;



}
