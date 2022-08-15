package cn.doitedu.doeweb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyParam {

    private String prop;
    private String op;
    private String v;
}
