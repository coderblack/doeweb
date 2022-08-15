package cn.doitedu.doeweb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventParam {
    private String eventId;
    private List<PropertyParam> properties;
    private String windowStart;
    private String windowEnd;
}
