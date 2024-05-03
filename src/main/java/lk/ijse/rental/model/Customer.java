package lk.ijse.rental.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Customer {
    private String c_id;
    private String c_name;
    private String c_address;
    private String c_tel;

}

