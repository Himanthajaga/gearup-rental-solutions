package lk.ijse.rental.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Mechanic {
    private String mec_id;
    private String mec_name;
    private String mec_address;
    private String mec_tel;
    private String mec_desc;
    private String mec_salary;
}
