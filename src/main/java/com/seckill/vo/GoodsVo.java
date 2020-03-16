package com.seckill.vo;

import com.seckill.entity.Goods;
import lombok.Data;

import java.util.Date;

@Data
public class GoodsVo extends Goods {
    private Double seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
