package lk.ijse.rental.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Supplier {
    private String s_id;
    private String s_name;
    private String s_address;
    private String s_tel;
    private String s_email;
}
