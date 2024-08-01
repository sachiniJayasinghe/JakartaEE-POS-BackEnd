package lk.ijse.gdse68.javaeeposbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto implements Serializable {
    private String cusID;
    private String cusName;
    private String cusAddress;
    private double cusSalary;


}
//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