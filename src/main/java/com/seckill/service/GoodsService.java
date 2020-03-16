package com.seckill.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seckill.dao.GoodsDao;
import com.seckill.entity.Goods;;
import com.seckill.vo.GoodsVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author EMINEM
 * @since 2020-03-16
 */
@Service
public class GoodsService extends ServiceImpl<GoodsDao, Goods> {

    public List<GoodsVo> listGoodsVo() {
        return baseMapper.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return baseMapper.getGoodsVoByGoodsId(goodsId);
    }
}
