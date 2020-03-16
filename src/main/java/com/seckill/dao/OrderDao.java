package com.seckill.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seckill.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author EMINEM
 * @since 2020-03-16
 */
@Mapper
@Repository
public interface OrderDao extends BaseMapper<Order> {

}
