package cn.doitedu.doeweb.controller;

import cn.doitedu.doeweb.pojo.LogCollectDataqVo;
import cn.doitedu.doeweb.pojo.LogServerLinesInfo;
import cn.doitedu.doeweb.pojo.Person;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;

@RestController  // 表示本控制器中的所有方法，返回的都是数据，而不是页面
public class LogCollectDataqController {
    Connection conn;

    public LogCollectDataqController() throws SQLException {

        conn = DriverManager.getConnection("jdbc:mysql://doitedu:3306/dataq?useUnicode=true&characterEncoding=utf8", "root", "root");

    }


    /**
     * 前端页面查询质量报告的请求方法
     * @return
     * @throws SQLException
     */
    @RequestMapping("/api/dataq/list")
    public LogCollectDataqVo listLogCollectDataq() throws SQLException {

        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery("select * from logserver_line_cnt");

        ArrayList<LogServerLinesInfo> data = new ArrayList<>();
        while(resultSet.next()){
            long id = resultSet.getLong("id");
            String biz_name = resultSet.getString("biz_name");
            String log_date = resultSet.getString("log_date");
            String log_server = resultSet.getString("log_server");
            String log_type = resultSet.getString("log_type");
            long log_count = resultSet.getLong("log_count");
            Timestamp create_time = resultSet.getTimestamp("create_time");
            data.add(new LogServerLinesInfo(id,biz_name,log_date,log_server,log_type,log_count,create_time.toString()));
        }


        return new LogCollectDataqVo(0, "", data.size(), data);

    }


    // {"biz_name":"多易商城","log_type":"","log_server":"","log_date":""}
    @RequestMapping("/api/dataq/query")
    public LogCollectDataqVo queryLogCollectDataq(String searchParams) throws SQLException {

        LogServerLinesInfo queryParams = JSON.parseObject(searchParams, LogServerLinesInfo.class);

        StringBuilder sb = new StringBuilder();
        sb.append("select * from logserver_line_cnt where 1=1");

        if(queryParams.getBiz_name() != null && !queryParams.getBiz_name().trim().equals("")) sb.append(" and biz_name = '" +queryParams.getBiz_name() +"'");
        if(queryParams.getLog_type() != null && !queryParams.getLog_type().trim().equals("")) sb.append(" and log_type = '" +queryParams.getLog_type()+"'");
        if(queryParams.getLog_server() != null && !queryParams.getLog_server().trim().equals("")) sb.append(" and log_server = '" +queryParams.getLog_server()+"'");
        if(queryParams.getLog_date() != null && !queryParams.getLog_date() .trim().equals("")) sb.append(" and log_date = '" +queryParams.getLog_date()+"'");

        System.out.println(sb);

        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(sb.toString());

        ArrayList<LogServerLinesInfo> data = new ArrayList<>();
        while(resultSet.next()){
            long id = resultSet.getLong("id");
            String bizName = resultSet.getString("biz_name");
            String logDate = resultSet.getString("log_date");
            String logServer = resultSet.getString("log_server");
            String logType = resultSet.getString("log_type");
            long logCount = resultSet.getLong("log_count");
            Timestamp createTime = resultSet.getTimestamp("create_time");
            data.add(new LogServerLinesInfo(id,bizName,logDate,logServer,logType,logCount,createTime.toString()));
        }


        return new LogCollectDataqVo(0, "", data.size(), data);

    }


    // {"biz_name":"多易商城","log_type":"","log_server":"","log_date":""}
    @RequestMapping("/api/dataq/getLogserverAmt")
    public String getLogserverLineAmt(String dt) throws SQLException {


        String sql = "select sum() as amt from logserver_line_cnt where log_date = '" + dt + "'";

        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);

        long amt = 0;
        while(resultSet.next()){
            amt = resultSet.getLong("amt");
        }

        return amt+"";

    }



    /**
     * 接收日志服务器质量检查脚本所上报的日志行数信息
     * @param logServerLinesInfo
     * @return
     */
    @RequestMapping("/api/dataq/logcollect/add")
    public String addLogCollectDataqInfo(@RequestBody LogServerLinesInfo logServerLinesInfo) throws SQLException {

        PreparedStatement preparedStatement = conn.prepareStatement(
                "insert into logserver_line_cnt (biz_name,log_date,log_server,log_type,log_count,create_time) " +
                        "values (?,?,?,?,?,?)");
        preparedStatement.setString(1,logServerLinesInfo.getBiz_name());
        preparedStatement.setString(2,logServerLinesInfo.getLog_date());
        preparedStatement.setString(3,logServerLinesInfo.getLog_server());
        preparedStatement.setString(4,logServerLinesInfo.getLog_type());
        preparedStatement.setLong(5,logServerLinesInfo.getLog_count());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        preparedStatement.setTimestamp(6,timestamp);

        preparedStatement.execute();

        return "0";
    }


    /**
     * 接收hdfs落地日志行数信息的方法
     */
    @RequestMapping("/api/dataq/logcollect/hdfsadd")
    public String addLogCollectDataqHdfsInfo(@RequestBody LogServerLinesInfo logServerLinesInfo) throws SQLException {

        System.out.println(logServerLinesInfo);

        PreparedStatement preparedStatement = conn.prepareStatement(
                "insert into hdfslog_line_cnt (biz_name,log_date,log_type,log_count,create_time) " +
                        "values (?,?,?,?,?)");
        preparedStatement.setString(1,logServerLinesInfo.getBiz_name());
        preparedStatement.setString(2,logServerLinesInfo.getLog_date());
        preparedStatement.setString(3,logServerLinesInfo.getLog_type());
        preparedStatement.setLong(4,logServerLinesInfo.getLog_count());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        preparedStatement.setTimestamp(5,timestamp);

        preparedStatement.execute();

        return "0";
    }

    @RequestMapping("/api/dataq/listhdfs")
    public LogCollectDataqVo listLogCollectDataqHdfs() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery("select * from hdfslog_line_cnt");

        ArrayList<LogServerLinesInfo> data = new ArrayList<>();
        while(resultSet.next()){
            long id = resultSet.getLong("id");
            String biz_name = resultSet.getString("biz_name");
            String log_date = resultSet.getString("log_date");
            String log_type = resultSet.getString("log_type");
            long log_count = resultSet.getLong("log_count");
            Timestamp create_time = resultSet.getTimestamp("create_time");
            data.add(new LogServerLinesInfo(id,biz_name,log_date,null,log_type,log_count,create_time.toString()));
        }


        return new LogCollectDataqVo(0, "", data.size(), data);

    }


    /**
     * 本 restapi的测试 命令：   curl 'http://192.168.77.2:8080/api/dataq/checkd?biz_name=doit_mall&log_typ_event_log&log_date=2022-07-15'
     * @param biz_name
     * @param log_type
     * @param log_date
     * @return
     * @throws SQLException
     */
    @RequestMapping("/api/dataq/checkd")
    public String checkLogCollectExistsDuplicate(String biz_name,String log_type,String log_date) throws SQLException {

        // 查询指定条件下，日志服务器上的日志行数总和
        PreparedStatement preparedStatement = conn.prepareStatement("select sum(log_count) from logserver_line_cnt where biz_name = ? and log_type = ? and log_date = ?");
        preparedStatement.setString(1,biz_name);
        preparedStatement.setString(2,log_type);
        preparedStatement.setString(3,log_date);

        ResultSet resultSet = preparedStatement.executeQuery();

        long logserverLineAmount = 0;
        while(resultSet.next()){
            logserverLineAmount = resultSet.getLong(1);
        }

        System.out.println("日志服务器上的日志总行数查询结果为： " + logserverLineAmount);

        // 查询指定条件下，hdfs上的日志行数
        PreparedStatement statement = conn.prepareStatement("select log_count from hdfslog_line_cnt where biz_name = ? and log_type = ? and log_date = ? ");
        statement.setString(1,biz_name);
        statement.setString(2,log_type);
        statement.setString(3,log_date);

        ResultSet resultSet2 = statement.executeQuery();

        long hdfsLineAmount = 0;
        while(resultSet2.next()){
            hdfsLineAmount = resultSet2.getLong(1);
        }


        System.out.println("hdfs上的日志总行数查询结果为： " + hdfsLineAmount);


        // 比较大小关系，进行返回:
        return (hdfsLineAmount <= logserverLineAmount)? "0":"1";

    }

}
