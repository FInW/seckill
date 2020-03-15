package com.panic_buying.controller;

import com.panic_buying.dao.UserDao;
import com.panic_buying.entity.User;
import com.panic_buying.service.UserService;
import com.panic_buying.utils.CodeMsg;
import com.panic_buying.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


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
