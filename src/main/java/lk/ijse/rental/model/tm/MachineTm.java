package lk.ijse.rental.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineTm {
    private String colMachineId;
    private String colMachineName;
    private String colMachineDescription;
    private String colMachineRentalPrice;
    private String colMachineIsAvailable;



}
