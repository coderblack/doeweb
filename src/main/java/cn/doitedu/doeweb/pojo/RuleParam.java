package cn.doitedu.doeweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RuleParam {
    private List<PropertyParam> tag_conditions;
    private ActionConditions action_conditions;
    private List<EventParam> seq_conditions;
}
