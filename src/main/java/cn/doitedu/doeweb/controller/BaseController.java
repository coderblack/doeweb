package cn.doitedu.doeweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BaseController {

    @RequestMapping("/add")
    @ResponseBody
    public int handleSubmit(int a ,int b){
        return a+b;
    }

    @RequestMapping("/login")  // 表示将这个方法映射到 /login 这个请求路径
    @ResponseBody  // 表示该方法返回的不是一个完整页面，而是返回一段数据
    public String handleLogin(String name,String password){
        // 这里可以有任意复杂的逻辑
        // 也可以在这个方法里面调用你所封装的其他类、其他方法
        // 与任何普通的java程序开发完全没有差别

        if(name.startsWith("yao")) {
            return "sucess";
        }else{
            return "incorrect";
        }
    }


}
