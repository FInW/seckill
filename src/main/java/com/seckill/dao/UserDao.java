package com.seckill.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seckill.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author EMINEM
 */
@Mapper
@Repository
public interface UserDao extends BaseMapper<User> {

}
