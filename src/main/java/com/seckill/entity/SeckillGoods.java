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
public class SeckillGoods implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Long goodsId;

    private Double seckillPrice;

    /**
     * 库存
     */
    private Integer stockCount;

    private LocalDateTime startDate;

    private LocalDateTime endDate;


}
