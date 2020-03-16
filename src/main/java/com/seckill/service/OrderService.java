package com.seckill.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seckill.dao.OrderDao;
import com.seckill.entity.Order;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author EMINEM
 * @since 2020-03-16
 */
@Service
public class OrderService extends ServiceImpl<OrderDao, Order> {

}
