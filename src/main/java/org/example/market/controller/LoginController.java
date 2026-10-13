package org.example.market.controller;

import org.springframework.ui.Model;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.example.market.pojo.User;
import org.example.market.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping()
public class LoginController {
    @Resource
    private LoginService loginService;
    //1.进入登录界面
    @GetMapping("/login")
    public String loginPage(){
        return "login";   //template
    }
    //2.处理登录请求
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        Model model, HttpSession session){
        // 1. 根据用户名查用户
        User user = loginService.selectByUsername(username);

        // 2. 判断用户是否存在
        if (user == null) {
            model.addAttribute("msg", "用户不存在");
            return "login"; // 回到登录页
        }

        // 3. 判断密码是否正确
        if (!user.getPassword().equals(password)) {
            model.addAttribute("msg", "密码错误");
            return "login";
        }

        // 4. 登录成功！把用户存入 session
        session.setAttribute("loginUser", user);

        // 5. 跳转到首页
        return "redirect:/index";
    }

    //3.进入注册界面
    @GetMapping("/register")
    public String registerPage(){
        return "register";
   //template
    }
    //4.处理注册请求
    @PostMapping("/register")
   public String register(@RequestParam String username, @RequestParam String password,@RequestParam String rePassword,
                          Model model){
        // 1. 校验密码是否一致
        if (!password.equals(rePassword)) {
            model.addAttribute("msg", "两次密码不一致");
            return "register";
        }

        // 2. 校验用户名是否存在
        User user1 = loginService.selectByUsername(username);
        if (user1 != null) {
            model.addAttribute("msg", "用户名已存在");
            return "register";
        }

        // 3. 注册用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("user");

        loginService.insert(user);
        model.addAttribute("msg", "注册成功");
        return "login";
   }
       //测试
    // 访问首页
    @GetMapping("/index")
    public String toIndex(){
        return "index";
    }

}
