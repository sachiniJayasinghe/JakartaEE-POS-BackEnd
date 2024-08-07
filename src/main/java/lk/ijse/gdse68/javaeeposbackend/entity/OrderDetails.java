package lk.ijse.gdse68.javaeeposbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrderDetails {
    private String code;
    private String orderID;
    private int qty;
    private double price;
}
