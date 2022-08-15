package cn.doitedu.doeweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogCollectDataqVo {

    private int code;
    private String msg;
    private int count;
    private List<LogServerLinesInfo> data;

}
