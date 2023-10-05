package com.example.startdemo.service;

import com.example.startdemo.dao.LoginLogDao;
import com.example.startdemo.dao.UserDao;
import com.example.startdemo.domain.LoginLog;
import com.example.startdemo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * user的业务层
 */

@Service    //将userservice标注为一个服务曾的Bean
public class UserService {

    private UserDao userDao;
    private LoginLogDao loginLogDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    @Autowired
    public void setLoginLogDao(LoginLogDao loginLogDao) {
        this.loginLogDao = loginLogDao;
    }

    //检查用户名和密码的正确性
    public boolean hasMatchUser(String userName,String password){
        int matchCount = userDao.getMatchCount(userName,password);
        return matchCount > 0;
    }

    //以用户名为条件加载user对象
    public User findUserByUserName(String userName){
        return userDao.findUserByUserName(userName);
    }


    //当成功登录之后调用，用于更新用户的登录时间和ip
    @Transactional  //事务注解
    public void loginSuccess(User user){
        user.setCredits(5 + user.getCredits());
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(user.getUserId());
        loginLog.setIp(user.getLastIp());
        loginLog.setLoginDate(user.getLastVisit());
        userDao.updateLoginInfo(user);
        loginLogDao.insertLoginLog(loginLog);
    }
}
