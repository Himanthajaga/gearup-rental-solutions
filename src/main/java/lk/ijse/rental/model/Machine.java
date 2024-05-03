package lk.ijse.dep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Machine {
   private String machineId;
    private String machineName;
    private String machineType;
    private String machineBrand;

}
