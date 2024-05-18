package lk.ijse.rental.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Reservation {
    private String r_id;
    private String r_type;
    private String r_date;

}
