package lk.ijse.gdse68.javaeeposbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String cusID;
    private String cusName;
    private String cusAddress;
    private double cusSalary;
}
