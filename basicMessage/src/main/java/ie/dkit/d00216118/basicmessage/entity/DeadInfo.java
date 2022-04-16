package ie.dkit.d00216118.basicmessage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeadInfo implements Serializable {
    private Integer id;
    private String msg;
}
