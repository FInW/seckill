package com.seckill.rabbitmq;

import com.seckill.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SeckillMessage {
    private User user;
    private long goodsId;
}
