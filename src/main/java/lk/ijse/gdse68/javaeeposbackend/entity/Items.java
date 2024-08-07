package lk.ijse.gdse68.javaeeposbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items {
    private String code;
    private String name;
    private BigDecimal price;
    private int qty;

}
