package cn.doitedu.doeweb;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class ConditionCalc {

    public static void main(String[] args) {

        // String json = "{\"events\":[{\"events\":[{\"eventParam\":{\"eventId\":\"e1\",\"properties\":{\"p1\":\"v1\"},\"windowEnd\":\"2022-08-22 17:00:00\",\"windowStart\":\"2022-08-01 16:00:00\"}},{\"eventParam\":{\"eventId\":\"e2\",\"properties\":{\"p2\":\"v2\"},\"windowEnd\":\"2022-09-22 14:00:00\",\"windowStart\":\"2022-07-01 12:00:00\"}}],\"type\":\"or\"},{\"eventParam\":{\"eventId\":\"e3\",\"properties\":{\"p2\":\"v2\"},\"windowEnd\":\"2022-09-22 14:00:00\",\"windowStart\":\"2022-07-01 12:00:00\"}}],\"type\":\"and\"}\n";


        String json = "{\n" +
                "  \"action_conditions\": {\n" +
                "    \"events\": [\n" +
                "      {\n" +
                "        \"eventParam\": {\n" +
                "          \"eventId\": \"addcart\",\n" +
                "          \"properties\": [\n" +
                "            {\n" +
                "              \"prop\": \"1\",\n" +
                "              \"op\": \"=\",\n" +
                "              \"value\": \"1\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"windowStart\": \"2022-08-01 00:00:00\",\n" +
                "          \"windowEnd\": \"2022-08-31 00:00:00\"\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"eventParam\": {\n" +
                "          \"eventId\": \"adshow\",\n" +
                "          \"properties\": [\n" +
                "            {\n" +
                "              \"prop\": \"1\",\n" +
                "              \"op\": \"=\",\n" +
                "              \"value\": \"1\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"windowStart\": \"2022-08-01 00:00:00\",\n" +
                "          \"windowEnd\": \"2022-08-31 00:00:00\"\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"type\": \"and\"\n" +
                "  },\n" +
                "  \"seq_conditions\": [\n" +
                "    {\n" +
                "      \"eventId\": \"addcart\",\n" +
                "      \"properties\": [\n" +
                "        {\n" +
                "          \"prop\": \"1\",\n" +
                "          \"op\": \"=\",\n" +
                "          \"value\": \"11\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"eventId\": \"pageview\",\n" +
                "      \"properties\": [\n" +
                "        {\n" +
                "          \"prop\": \"pageview\",\n" +
                "          \"op\": \"gt\",\n" +
                "          \"value\": \"22\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"properties\": [\n" +
                "        {\n" +
                "          \"prop\": \"2\",\n" +
                "          \"op\": \"ge\",\n" +
                "          \"value\": \"33\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";


        RuleParam ruleParam = JSON.parseObject(json, RuleParam.class);
        System.out.println(calc(ruleParam.getAction_conditions()));
    }

    public static boolean calc(ActionConditions actionConditions) {

        String type = actionConditions.getType();
        List<ActionConditions> events = actionConditions.getEvents();

        // 如果还没到最底层，则对组合下的每个子条件进行递归调用
        if (events!=null && events.size() > 1) {
            boolean andRes = true;
            boolean orRes = false;
            for (ActionConditions conditions : events) {
                // 如果条件组合的关系为 or
                if (type.equals("or")) {
                    orRes = orRes || calc(conditions);
                    // 只要找到一个分支为true，则直接返回true
                    if(orRes) return true;
                }else {
                    // 每个条件需要为true，最终结果才为true
                    andRes = andRes && calc(conditions);
                }
            }
            return andRes || orRes;
        } else {
            // 如果到了最底层，则对最底层的事件条件进行查询,并返回
            return queryParam(actionConditions.getEventParam());
        }
    }


    // (e1 or e2) and e3
    public static boolean queryParam(EventParam eventParam) {
        String eventId = eventParam.getEventId();
        //boolean b = eventId.equals("e2") || eventId.equals("e4"); // false
        //boolean b = eventId.equals("e2") || eventId.equals("e3");  // true
        // boolean b = eventId.equals("e2") ;  // false
         boolean b = eventId.equals("addcart") || eventId.equals("adshow") ;  // true

        return b;
    }

}
