package com.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author EMINEM
 * @since 2020-03-16
 */
@Data
@Accessors(chain = true)
@TableName("seckill_order")
public class Order implements Serializable {

    private static final long serialVersionUID=1L;

    private Long userId;

    private Long orderId;

    private Long goodsId;


}
