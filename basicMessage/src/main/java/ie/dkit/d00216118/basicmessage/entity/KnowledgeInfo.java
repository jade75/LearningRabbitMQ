package ie.dkit.d00216118.basicmessage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeInfo implements Serializable {
    private Integer id;
    private String mode;
    private String code;
}
