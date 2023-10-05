package com.example.startdemo.dao;


import com.example.startdemo.domain.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginLogDao {

    private JdbcTemplate jdbcTemplate;

    //保存登录日志的sql
    private final static String INSERT_LOGIN_LOG_SQL = "insert into t_login_log (user_id,ip,login_datetime) values(?,?,?,?)";

    public void insertLoginLog(LoginLog loginLog){
        Object[] args = {loginLog.getUserId(),loginLog.getIp(),loginLog.getLoginDate()};
        jdbcTemplate.update(INSERT_LOGIN_LOG_SQL,args);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
