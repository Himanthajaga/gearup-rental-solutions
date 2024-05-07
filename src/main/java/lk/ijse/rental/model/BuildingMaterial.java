package lk.ijse.rental.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingMaterial {
    private String bm_id;
    private String bm_desc;
    private String bm_type;
    private String bm_price;
    private String bm_qty;
}
