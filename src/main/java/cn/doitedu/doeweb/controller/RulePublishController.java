package cn.doitedu.doeweb.controller;

import cn.doitedu.doeweb.pojo.RuleParam;
import cn.doitedu.doeweb.service.ConditionCalc;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * {
 * "action_conditions":{
 *    "events":[
 *      {"eventParam":
 *        {"eventId":"addcart",
 *         "properties":[{"prop":"1","op":"=","value":"1"}],
 *         "windowStart":"2022-08-01 00:00:00",
 *         "windowEnd":"2022-08-23 00:00:00"}
 *      },
 *      {"eventParam":
 *        {"eventId":"addcart",
 *        "properties":[{"prop":"1","op":"=","value":"1"}],
 *        "windowStart":"2022-08-01 00:00:00",
 *        "windowEnd":"2022-08-23 00:00:00"}
 *     }
 *   ],
 *   "type":"and"
 * },
 * "seq_conditions":
 *   [
 *   {"eventId":"addcart","properties":[{"prop":"1","op":"le","value":"11"}]},
 *   {"eventId":"addcart","properties":[{"prop":"productdz","op":"lt","value":"22"}]},
 *   {"properties":[{"prop":"1","op":"ge","value":"33"}]}]}
 */
@RestController
public class RulePublishController {

    @Autowired
    ConditionCalc conditionCalc;

    @RequestMapping(value = "/api/rule/publish" ,method = RequestMethod.POST)
    public String publishRule(@RequestBody RuleParam ruleParam){

        System.out.println("收到的参数如下");
        System.out.println(JSON.toJSONString(ruleParam, true));

        boolean res = conditionCalc.calc(ruleParam.getAction_conditions());
        System.out.println("规则计算结果：   " + res);
        return "ok";
    }
}
