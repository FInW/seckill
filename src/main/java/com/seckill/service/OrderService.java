package com.seckill.service;

import com.alibaba.fastjson.JSON;
import com.seckill.dao.OrderInfoDao;
import com.seckill.dao.SeckillOrderDao;
import com.seckill.entity.OrderInfo;
import com.seckill.entity.SeckillOrder;
import com.seckill.entity.User;
import com.seckill.redis.OrderKey;
import com.seckill.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author EMINEM
 * @since 2020-03-16
 */
@Service
public class OrderService {

    @Autowired
    private SeckillOrderDao seckillOrderDao;

    @Autowired
    private OrderInfoDao orderInfoDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public SeckillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId) {
//        return seckillOrderDao.getSeckillOrderByUserIdGoodsId(userId, goodsId);

        String json = redisTemplate.opsForValue().get(OrderKey.getSeckillOrderByUidGid.getPrefix(userId + "_" + goodsId));
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseObject(json, SeckillOrder.class);
    }

    @Transactional
    public OrderInfo createOrder(User user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(LocalDateTime.now());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getSeckillPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderInfoDao.insert(orderInfo);
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setGoodsId(goods.getId());
        seckillOrder.setOrderId(orderInfo.getId());
        seckillOrder.setUserId(user.getId());
        seckillOrderDao.insert(seckillOrder);

        redisTemplate.opsForValue().set(
                OrderKey.getSeckillOrderByUidGid.getPrefix(user.getId() + "_" + goods.getId()),
                JSON.toJSONString(seckillOrder));
        return orderInfo;
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
