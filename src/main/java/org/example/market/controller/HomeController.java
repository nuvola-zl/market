package org.example.market.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.example.market.pojo.Goods;
import org.example.market.pojo.User;
import org.example.market.service.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Resource
    private HomeService homeService;

    // 真正退出登录：清空Session
    @GetMapping("/logout")
    public String logout(HttpSession session){
        // 销毁整个会话，清除所有登录信息
        session.invalidate();
        // 重定向到登录页
        return "redirect:/login";
    }

    // 打开添加商品页面（GET 请求）
    @GetMapping("/add")
    public String toAddPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null || !"admin".equals(user.getRole())) {
            return "redirect:/login";
        }

        // 给页面一个空的 goods 对象，防止 thymeleaf 报错
        model.addAttribute("goods", new Goods());
        return "addGoods"; // 跳转到添加商品页面
    }


    @PostMapping("/addGoods")
    public String addGoods(Goods goods, Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null || !"admin".equals(loginUser.getRole())) {
            return "redirect:/login";
        }

        // ========== 校验逻辑（出错不跳转，保留数据）==========
        if (goods.getName() == null || goods.getName().trim().isEmpty()) {
            model.addAttribute("msg", "商品名称不能为空");
            model.addAttribute("goods", goods); // 保留数据
            return "addGoods";
        }
        if (goods.getPrice() == null) {
            model.addAttribute("msg", "单价不能为空");
            model.addAttribute("goods", goods);
            return "addGoods";
        }
        if (goods.getUnit() == null || goods.getUnit().trim().isEmpty()) {
            model.addAttribute("msg", "单位不能为空");
            model.addAttribute("goods", goods);
            return "addGoods";
        }
        if (goods.getStock() == null) {
            model.addAttribute("msg", "库存不能为空");
            model.addAttribute("goods", goods);
            return "addGoods";
        }
        if (goods.getCategory() == null || goods.getCategory().trim().isEmpty()) {
            model.addAttribute("msg", "分类不能为空");
            model.addAttribute("goods", goods);
            return "addGoods";
        }

        // 图片默认值
        if (goods.getImage() == null) {
            goods.setImage("");
        }

        // 执行添加
        homeService.insertGoods(goods);

        // 添加成功 → 跳首页（不会保留表单，因为完成了）
        return "redirect:/home/goods";
    }



    //列表分为1，全部:all，2，水果:fruit，3，蔬菜:vegetable，4.衣物:clothing，5厨具kitchenware，6电子:elect，7，运动:sport，8，医药:med，9，其他：other
    @GetMapping("/goods")
    public String goodsList(@RequestParam(required = false) String category, Model model) {

        List<Goods> goodsList;

        // 1. 根据分类查询
        if (category == null || "全部".equals(category)) {
            goodsList = homeService.getAllGoods(); // 查全部
        } else {
            goodsList = homeService.getGoodsByCategory(category); // 按分类查
        }

        // 2. 把商品列表传给页面
        model.addAttribute("goodsList", goodsList);
        // 把当前选中的分类也传给页面（用于高亮）
        model.addAttribute("currentCategory", category == null ? "全部" : category);

        return "goodsList"; // 同一个页面！
    }

    // 进入购买页面
    @GetMapping("/toBuyPage")
    public String toBuyPage(@RequestParam("id") Integer id, Model model) {
        Goods goods = homeService.getGoodsById(id);
        model.addAttribute("goods", goods);
        return "buy";
    }

    // 执行购买
    @GetMapping("/doBuy")
    public String doBuy(@RequestParam("id") Integer id,
                        @RequestParam("num") Integer num,
                        Model model,
                        HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        User realUser = homeService.getUserById(loginUser.getId());
        Goods goods = homeService.getGoodsById(id);

        model.addAttribute("goods", goods); // 一定要传回页面

        // 错误判断
        if (goods == null) {
            model.addAttribute("msg", "商品不存在");
            return "buy";
        }
        if (num <= 0) {
            model.addAttribute("msg", "购买数量必须大于0");
            return "buy";
        }
        if (num > goods.getStock()) {
            model.addAttribute("msg", "库存不足！当前库存：" + goods.getStock());
            return "buy";
        }

        BigDecimal totalPrice = goods.getPrice().multiply(new BigDecimal(num));

        if (realUser.getWalletBalance() == null ||
                realUser.getWalletBalance().compareTo(totalPrice) < 0) {
            model.addAttribute("msg", "余额不足！");
            return "buy";
        }

        // 扣钱
        BigDecimal newBalance = realUser.getWalletBalance().subtract(totalPrice);
        homeService.updateUserWallet(realUser.getId(), newBalance);
        loginUser.setWalletBalance(newBalance);
        session.setAttribute("loginUser", loginUser);

        // 扣库存
        homeService.updateStock(num, id);

        model.addAttribute("msg", "购买成功！");
        return "buy";
    }

}
