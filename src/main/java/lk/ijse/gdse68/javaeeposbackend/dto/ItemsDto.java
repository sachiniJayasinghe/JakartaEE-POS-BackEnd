package lk.ijse.gdse68.javaeeposbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsDto {
    private String code;
    private String name;
    private BigDecimal price;
    private int qty;


}
