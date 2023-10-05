package com.example.startdemo.dao;

import com.example.startdemo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 访问User的Dao
 */
//通过spring注解定义一个Dao
@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    //根据用户名查询用户的sql语句
    private final static String MATCH_COUNT_SQL = "select count(*) from "
            + "t_user where user_name =? and password=?";

    private final static String UPDATE_LOGIN_INFO_SQL = "update t_user set "
            + "last_visit=?,last_ip=?,credits=? where user_id=?";

    private final static String QUERY_BY_USERNAME = "select user_id,user_name,credits"
            + "from t_user where user_name=?";

    @Autowired      //自动注入jdbcTemplate的Bean
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //根据用户名和密码获取匹配的用户数
    public int getMatchCount(String userName,String password){
        String sqlStr = "select count(*) from t_user"
                + "where user_name=? and password=?";

        return jdbcTemplate.queryForObject(MATCH_COUNT_SQL,new Object[]{userName,password},Integer.class);
    }

    //根据用户名获取user对象
    public User findUserByUserName(final String userName){
        final User user = new User();
        jdbcTemplate.query(QUERY_BY_USERNAME,new Object[]{userName},
                //匿名类方式实现的回调函数
                //将ResultSet对象转换为User对象
                new RowCallbackHandler(){
                    public void processRow(ResultSet resultSet) throws SQLException{
                        user.setUserId(resultSet.getInt("user_id"));
                        user.setUserName(userName);
                        user.setCredits(resultSet.getInt("credits"));
                    }
                });
        return user;

    }

    public void updateLoginInfo(User user){
        jdbcTemplate.update(UPDATE_LOGIN_INFO_SQL,new Object[]{
                user.getLastVisit(),user.getLastIp(),user.getCredits(),user.getUserId()
        });
    }
}
