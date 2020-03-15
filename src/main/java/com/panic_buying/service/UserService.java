package com.panic_buying.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panic_buying.dao.UserDao;
import com.panic_buying.entity.User;
import com.panic_buying.exception.GlobalException;
import com.panic_buying.redis.PanicBuyingUserKey;
import com.panic_buying.utils.CodeMsg;
import com.panic_buying.utils.MD5Util;
import com.panic_buying.utils.UUIDUtil;
import com.panic_buying.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Service
public class UserService extends ServiceImpl<UserDao, User> {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

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
}
