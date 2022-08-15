package cn.doitedu.doeweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventParam {
    private String eventId;
    private List<PropertyParam> properties;
    private String windowStart;
    private String windowEnd;
}
