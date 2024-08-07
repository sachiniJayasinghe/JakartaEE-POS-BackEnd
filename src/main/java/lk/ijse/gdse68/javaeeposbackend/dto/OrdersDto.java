package lk.ijse.gdse68.javaeeposbackend.dto;

import lk.ijse.gdse68.javaeeposbackend.entity.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrdersDto {
    private String orderID;
    private Date orderDate;
    private String cusID;
    private OrderDetailsDto[] orderItems;
}
