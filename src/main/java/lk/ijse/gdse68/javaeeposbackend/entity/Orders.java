package lk.ijse.gdse68.javaeeposbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    private String orderID;
    private Date orderDate;
    private String cusID;

}
