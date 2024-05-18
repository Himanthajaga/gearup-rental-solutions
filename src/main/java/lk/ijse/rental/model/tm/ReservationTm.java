package lk.ijse.rental.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationTm {
    private String colReservationId;
    private String colReservationType;
    private String colReservationDate;

}
