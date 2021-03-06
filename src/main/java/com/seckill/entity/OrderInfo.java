package com.seckill.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

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
public class OrderInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long goodsId;

    /**
     * 收货地址ID
     */
    private Long deliveryAddrId;

    private String goodsName;

    private Integer goodsCount;

    private Double goodsPrice;

    private Integer orderChannel;

    /**
     * 0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成
     */
    private Integer status;

    private LocalDateTime createDate;

    private LocalDateTime payDate;


}
