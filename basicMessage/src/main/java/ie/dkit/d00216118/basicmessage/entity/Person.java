package ie.dkit.d00216118.basicmessage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {
    private Integer id;
    private String name;
    private String userName;

}
