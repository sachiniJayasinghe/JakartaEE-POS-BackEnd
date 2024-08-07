package lk.ijse.gdse68.javaeeposbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrderDetailsDto {
    private String code;
    private String orderID;
    private int qty;
    private double price;
}
