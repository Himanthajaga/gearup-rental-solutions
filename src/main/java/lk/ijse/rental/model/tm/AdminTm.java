package lk.ijse.rental.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class AdminTm {
    private String cola_id;
    private String cola_name;
    private String cola_password;
    private String cola_ConfirmPasswors;
    private String cola_Email;
}