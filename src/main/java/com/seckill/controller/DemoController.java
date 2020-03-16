package com.seckill.controller;

import com.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/demo")
@RestController
public class DemoController {

    @Autowired
    private UserService userService;

//    @RequestMapping("/")
//    @ResponseBody
//    String home() {
//        return "Hello World!";
//    }
//
//    @RequestMapping("/hello")
//    @ResponseBody
//    public Result<String> hello() {
//        return Result.success("hello,imooc");
//    }
//
//    @RequestMapping("/helloError")
//    @ResponseBody
//    public Result<String> helloError() {
//        return Result.error(CodeMsg.SERVER_ERROR);
//    }
//
//    @RequestMapping("/thymeleaf")
//    public String thymeleaf(Model model) {
//        model.addAttribute("name", "Joshua");
//        return "hello";
//    }
//
//    @RequestMapping("/db/add")
//    @ResponseBody
//    public Result<?> add() {
//        User user = new User(2, "test0");
//        userService.save(user);
//        return Result.success(user);
//    }

//    @RequestMapping("/db/get")
//    @ResponseBody
//    public Result<?> get() {
//        return Result.success(userService.getById(1));
//    }
//
//    @RequestMapping("/db/test")
//    @ResponseBody
//    public Result<?> test() {
//        return Result.success(userService.test());
//    }
}
