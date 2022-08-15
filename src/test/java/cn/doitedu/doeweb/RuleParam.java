package cn.doitedu.doeweb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RuleParam {
    private ActionConditions action_conditions;
    private List<EventParam> seq_conditions;
}
