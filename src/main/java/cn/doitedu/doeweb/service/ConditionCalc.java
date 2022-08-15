package cn.doitedu.doeweb.service;

import cn.doitedu.doeweb.pojo.ActionConditions;
import cn.doitedu.doeweb.pojo.EventParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConditionCalc {

    public boolean calc(ActionConditions actionConditions) {

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
    public boolean queryParam(EventParam eventParam) {
        String eventId = eventParam.getEventId();
        //boolean b = eventId.equals("e2") || eventId.equals("e4"); // false
        //boolean b = eventId.equals("e2") || eventId.equals("e3");  // true
        // boolean b = eventId.equals("e2") ;  // false
         boolean b = eventId.equals("addcart") || eventId.equals("adshow") ;  // true

        return b;
    }

}
