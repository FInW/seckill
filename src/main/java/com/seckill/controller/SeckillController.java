package com.seckill.controller;

import com.seckill.entity.SeckillOrder;
import com.seckill.entity.User;
import com.seckill.rabbitmq.MQSender;
import com.seckill.rabbitmq.SeckillMessage;
import com.seckill.redis.GoodsKey;
import com.seckill.service.GoodsService;
import com.seckill.service.OrderService;
import com.seckill.service.SeckillService;
import com.seckill.utils.CodeMsg;
import com.seckill.utils.RedisUtil;
import com.seckill.utils.Result;
import com.seckill.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private MQSender sender;

    @Autowired
    private RedisUtil redisUtil;

    private HashMap<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        if (goodsVoList == null) {
            return;
        }
        for (GoodsVo goods : goodsVoList) {
            redisUtil.set(GoodsKey.getSeckillGoodsStock, goods.getId(), String.valueOf(goods.getStockCount()));
            localOverMap.put(goods.getId(), false);
        }
    }


    /**
     * 带path参数进行请求，通过验证path的真实性，减少盗刷几率
     *
     * @param model
     * @param user
     * @param goodsId
     * @param path
     * @return
     */
    @RequestMapping(value = "/{path}/do_seckill", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> seckill(Model model, User user,
                                   @RequestParam("goodsId") long goodsId,
                                   @PathVariable String path) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
        //验证path
        boolean check = seckillService.checkPath(user, goodsId, path);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        //减少redis访问
        boolean over = localOverMap.get(goodsId);
        if (over) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        //预减库存
        long stock = redisUtil.decrement(GoodsKey.getSeckillGoodsStock, goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        //判断是否已经秒杀到了
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }

        //入队
        SeckillMessage seckillMessage = new SeckillMessage()
                .setUser(user)
                .setGoodsId(goodsId);
        sender.sendSecKillMessage(seckillMessage);
        return Result.success(0);//排队中
    }

    /**
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(Model model, User user,
                                      @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = seckillService.getSecKillResult(user.getId(), goodsId);
        return Result.success(result);
    }

    //    @AccessLimit(seconds=5, maxCount=5, needLogin=true)
    @RequestMapping(value = "/path", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaPath(User user, @RequestParam("goodsId") long goodsId,
                                         @RequestParam(value = "verifyCode", defaultValue = "0") int verifyCode) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        boolean check = seckillService.checkVerifyCode(user, goodsId, verifyCode);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        String path = seckillService.createSeckillPath(user, goodsId);
        return Result.success(path);
    }

    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaVerifyCod(HttpServletResponse response, User user,
                                              @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        try {
            BufferedImage image = seckillService.createVerifyCode(user, goodsId);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SECKILL_FAIL);
        }
    }
}
