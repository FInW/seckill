package com.seckill.entity;

import java.math.BigDecimal;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
public class Goods implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String goodsName;

    private String goodsTitle;

    private String goodsImg;

    private String goodsDetail;

    private Double goodsPrice;

    /**
     * 商品库存 -1表示无限制
     */
    private Integer goodsStock;


}
