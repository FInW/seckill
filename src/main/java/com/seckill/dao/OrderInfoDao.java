package com.seckill.dao;

import com.seckill.entity.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
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
public interface OrderInfoDao extends BaseMapper<OrderInfo> {
}
