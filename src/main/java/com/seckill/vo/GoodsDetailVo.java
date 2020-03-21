package com.seckill.vo;

import com.seckill.entity.User;
import lombok.Data;

@Data
public class GoodsDetailVo {
	private int seckillStatus = 0;
	private int remainSeconds = 0;
	private GoodsVo goods ;
	private User user;
}
