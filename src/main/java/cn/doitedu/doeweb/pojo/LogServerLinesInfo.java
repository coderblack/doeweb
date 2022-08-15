package cn.doitedu.doeweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogServerLinesInfo {

    private long id;
    private String biz_name;
    private String log_date;
    private String log_server;
    private String log_type;
    private long log_count;
    private String create_time;
}
