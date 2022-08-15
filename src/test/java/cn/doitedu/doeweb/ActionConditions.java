package cn.doitedu.doeweb;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionConditions {

    private String type;
    private List<ActionConditions> events;
    private EventParam eventParam;

    public static void main(String[] args) {

        EventParam eventParam1 = new EventParam();

        PropertyParam propertyParam1 = new PropertyParam("p1", "eq", "v1");
        eventParam1.setProperties(Collections.singletonList(propertyParam1));
        eventParam1.setEventId("e1");

        eventParam1.setWindowStart("2022-08-01 16:00:00");
        eventParam1.setWindowEnd("2022-08-22 17:00:00");


        ActionConditions con1 = new ActionConditions();
        con1.setEventParam(eventParam1);


        EventParam eventParam2 = new EventParam();
        eventParam2.setEventId("e3");
        PropertyParam propertyParam2 = new PropertyParam("p2", "eq", "v2");
        eventParam2.setProperties(Collections.singletonList(propertyParam2));
        eventParam2.setWindowStart("2022-07-01 12:00:00");
        eventParam2.setWindowEnd("2022-09-22 14:00:00");


        ActionConditions con2 = new ActionConditions();
        con2.setEventParam(eventParam2);


        // con1 和  con2 组成 con12
        ActionConditions con12 = new ActionConditions("or", Arrays.asList(con1, con2), null);


        EventParam eventParam3 = new EventParam();
        eventParam3.setEventId("e3");
        PropertyParam propertyParam3 = new PropertyParam("p3", "eq", "v3");
        eventParam3.setProperties(Collections.singletonList(propertyParam3));
        eventParam3.setWindowStart("2022-07-01 12:00:00");
        eventParam3.setWindowEnd("2022-09-22 14:00:00");


        ActionConditions con3 = new ActionConditions();
        con3.setEventParam(eventParam3);

        // con12 和 con3 组成 con123
        ActionConditions con123 = new ActionConditions("and", Arrays.asList(con12, con3), null);

        System.out.println(JSON.toJSONString(con123));

    }

}
