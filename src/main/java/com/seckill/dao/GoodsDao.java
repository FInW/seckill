package com.seckill.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seckill.entity.Goods;
import com.seckill.entity.SeckillGoods;
import com.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author EMINEM
 * @since 2020-03-16
 */
@Mapper
@Repository
public interface GoodsDao extends BaseMapper<Goods> {

    @Select("select goods.*, seckill_goods.stock_count, seckill_goods.start_date, " +
            "seckill_goods.end_date, seckill_goods.seckill_price " +
            "from seckill_goods left join goods " +
            "on seckill_goods.goods_id = goods.id")
    List<GoodsVo> listGoodsVo();

    @Select("select goods.*,seckill_goods.stock_count, seckill_goods.start_date, " +
            "seckill_goods.end_date,seckill_goods.seckill_price " +
            "from seckill_goods  left join goods " +
            "on seckill_goods.goods_id = goods.id " +
            "where goods.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    @Update("update seckill_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    int reduceStock(SeckillGoods goods);

}
