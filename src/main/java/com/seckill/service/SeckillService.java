package com.seckill.service;

import com.seckill.entity.OrderInfo;
import com.seckill.entity.SeckillOrder;
import com.seckill.entity.User;
import com.seckill.redis.SeckillKey;
import com.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SeckillService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Transactional
    public OrderInfo seckill(User user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单
        boolean success =  goodsService.reduceStock(goods);
        if (success) {
            //order_info maiosha_order
            return orderService.createOrder(user, goods);
        } else {
            setGoodsOver(goods.getId());
            return null;
        }
    }

    public long getSecKillResult(Long userId, long goodsId) {
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(userId, goodsId);
        if(order != null) {//秒杀成功
            return order.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisTemplate.opsForValue().set(SeckillKey.isGoodsOver.getPrefix(goodsId), "true");
    }

    private boolean getGoodsOver(long goodsId) {
        return redisTemplate.hasKey(SeckillKey.isGoodsOver.getPrefix(goodsId));
    }
}
