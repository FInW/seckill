package com.panic_buying.controller;

import com.panic_buying.entity.User;
import com.panic_buying.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	UserService userService;
	
	@Autowired
	RedisTemplate<String, Serializable> redisTemplate;
	
    @RequestMapping("/to_list")
    public String list(Model model, User user) {
    	model.addAttribute("user", user);
        return "goods_list";
    }
    
}
