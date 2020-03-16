package com.seckill.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seckill.dao.UserDao;
import com.seckill.entity.User;
import com.seckill.exception.GlobalException;
import com.seckill.redis.PanicBuyingUserKey;
import com.seckill.utils.CodeMsg;
import com.seckill.utils.MD5Util;
import com.seckill.utils.UUIDUtil;
import com.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Service
public class UserService extends ServiceImpl<UserDao, User> {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public static final String COOKI_NAME_TOKEN = "token";

//    @Transactional //spring事务注解，出错会回滚操作
//    public Result<?> test() {
//        User user = new User(6, "test");
//        baseMapper.insert(user);
//
//        User user2 = new User(2, "test0");
//        baseMapper.insert(user2);
//
//        return Result.success(null);
//    }

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        User user = baseMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        String dbPass = user.getPassword();
        String salt = user.getSalt();
        String targetPass = MD5Util.md5BySalt(password, salt);
        if (!targetPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return true;
    }

    private void addCookie(HttpServletResponse response, String token, User user) {
        redisTemplate.opsForValue().set(PanicBuyingUserKey.token.getPrefix() + token, JSON.toJSONString(user),
                PanicBuyingUserKey.token.expireSeconds(), TimeUnit.SECONDS);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(PanicBuyingUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public User getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        String userJson = redisTemplate.opsForValue().get(PanicBuyingUserKey.token.getPrefix() + token);
        if (StringUtils.isEmpty(userJson)) {
            return null;
        }
        User user = JSON.parseObject(userJson, User.class);
        //延长有效期
        if(user != null) {
            addCookie(response, token, user);
        }
        return user;
    }
}
