package org.example.market.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.example.market.pojo.Goods;
import org.example.market.pojo.HeroSkin;
import org.example.market.pojo.User;
import org.example.market.service.KingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/king")
public class KingController {
    @Resource
    private KingService kingService;

    //请求进入新的主界面
    @GetMapping("/all")
    public String toAllPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        // 检查用户是否登录
        if (user == null) {
            return "redirect:/login";
        }
        return "allKing";
    }

    //请求进入添加页面
    @GetMapping("/addSkin")
    public String toAddPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        // 检查用户是否登录
        if (user == null) {
            return "redirect:/login";
        }
        //判断是否是管理员
        if (!"admin".equals(user.getRole())) {
            // 方案B：用重定向携带消息（推荐，更规范）
            return "redirect:/allKing?msg=只有管理员才能添加皮肤";
        }
        return "addKingSkin";
    }

    //请求添加商品皮肤
    @PostMapping("/addKingSkin")
    public String addKingSkin(HeroSkin heroSkin, Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        //判断是否是管理员
        if (!"admin".equals(loginUser.getRole())) {
            model.addAttribute("msg", "只有管理员才能添加商品,你不是管理员");
            return "redirect:/allKing";

        }
        //添加商品皮肤
        kingService.addKingSkin(heroSkin);
        model.addAttribute("msg", "添加商品皮肤成功");
        return "redirect:/king/skins";
       }


    @GetMapping("/skins")
    public String goodsList(@RequestParam(required = false) String category, Model model) {

        List<HeroSkin> heroSkinList;

        // 1. 根据分类查询
        if (category == null || "全部".equals(category)) {
            heroSkinList = kingService.getAllHeroSkinkins(); // 查全部
        } else {
            heroSkinList = kingService.getHeroSkinkinsByCategory(category); // 按分类查
        }

        // 2. 把商品列表传给页面
        model.addAttribute("heroSkinList", heroSkinList);
        // 把当前选中的分类也传给页面（用于高亮）
        model.addAttribute("currentCategory", category == null ? "全部" : category);

        return "herosSkinList"; // 同一个页面！
    }

    //请求进入商品详情页面
    //此处可以是弹框
    @GetMapping("/skinDetail")
    public String skinDetail(Model model, @RequestParam Integer id) {
        HeroSkin heroSkin = kingService.getHeroSkinById(id);
        model.addAttribute("skinDetail", heroSkin);
        return "skinDetail";
    }

    //详情弹框里面可以请求一个弹框购买
    @PostMapping("/buySkin")
    public String buySkin(Model model, @RequestParam Integer id, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        // 管理员不能购买
        if ("admin".equals(loginUser.getRole())) {
            return "redirect:/king/skins?msg=管理员不能购买皮肤";
        }

        HeroSkin heroSkin = kingService.getHeroSkinById(id);
        if(heroSkin.getSkinStock() == 0){
            return "redirect:/king/skins?msg=你来慢了，商品已经在1秒内被抢光";
        }

        //更新数据库中的商品库存
        kingService.updateHeroSkinStock(id);
        //返回弹框，显示购买信息，显示成功
        model.addAttribute("buySkinYes");
        return "buySkinYes";
    }

    //请求进入用户个人空间
    @GetMapping("/myKing")
    public String toMyKingPage(Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        return "myKing";
    }


}
