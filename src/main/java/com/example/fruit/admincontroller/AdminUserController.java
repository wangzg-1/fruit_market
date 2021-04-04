package com.example.fruit.admincontroller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.fruit.entity.AdminUser;
import com.example.fruit.service.AdminUserService;
import com.example.fruit.service.UserService;
import com.example.fruit.entity.User;
import com.example.fruit.enums.GenderEnum;
import com.example.fruit.util.PageRequest;
import com.example.fruit.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminUserService adminUserService;

    //后台管理
    @RequestMapping("/user/listPager")
    public ModelAndView list(User user, HttpServletRequest request){
        PageRequest pageBean = new PageRequest(1,10);
        String pageNow = request.getParameter("pageNow");
        ModelAndView modelAndView = new ModelAndView();
        PageResult users = userService.findPage(pageBean);
        modelAndView.addObject("users",users.getContent());
        PageResult pageResult=new PageResult();
        pageResult.setTotalPages(users.getTotalPages());
        modelAndView.addObject("pageCode", pageResult/*.replaceAll("<","<").replaceAll("&gt:",">")*/);
        modelAndView.setViewName("adminpage/listUser");
        return modelAndView;
    }

    @RequestMapping("/user/search")
    public String search(Model model, HttpServletRequest request){
        String name=request.getParameter("name");
        List<User> users=userService.findUserByName(name);
        model.addAttribute("users",users);
        return "adminpage/listUser";
    }


    @RequestMapping("/user/toAdd")
    public String AddUser(User user){
        return "adminpage/useradd";
    }

    @RequestMapping("/user/add")
    public String add(User user,  HttpServletRequest request){
        if(user.getGenderCode() == 1){
            user.setGender(GenderEnum.MAN);
        }
        if(user.getGenderCode() == 0){
            user.setGender(GenderEnum.WOMAN);
        }
        userService.addUser(user);
        return "redirect:/admin/user/listPager";
    }


    @RequestMapping("/user/toEdit")
    public ModelAndView toEdit(User user){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminpage/useradd");
        if(!(user.getId() == null || "".equals(user.getId()))) {
            User t = userService.findUserById(user.getId());
            modelAndView.addObject("user", t);
        }
        return modelAndView;
    }

    @RequestMapping("/user/edit")
    public String edit(User user,  HttpServletRequest request){
        if(user.getGenderCode() == 1){
            user.setGender(GenderEnum.MAN);
        }
        if(user.getGenderCode() == 0){
            user.setGender(GenderEnum.WOMAN);
        }
        userService.updateUser(user);
        return "redirect:/admin/user/listPager";
    }

    @RequestMapping("/user/del/{bid}")
    public String del(@PathVariable(value = "bid") Integer bid){
        userService.deleteUser(bid);
        return "redirect:/admin/user/listPager";
    }
    @RequestMapping("index")
    public String index(){
        return "adminpage/index";
    }
    @RequestMapping("/login")
    public String login(String loginName, String password, HttpSession session){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_name",loginName);
        wrapper.eq("password",password);
        AdminUser adminUser= adminUserService.getOne(wrapper);
        if(adminUser == null){
            session.setAttribute("errorMsg", "登录失败");
            return "adminpage/login";
        }else{
            session.setAttribute("adminUser",adminUser.getUserName());
            return "adminpage/index";
        }
    }
}
