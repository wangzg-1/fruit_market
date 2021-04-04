package com.example.fruit.businesscontroller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.fruit.entity.Business;
import com.example.fruit.enums.GenderEnum;
import com.example.fruit.service.BusinessService;
import com.example.fruit.vo.TableDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@Controller
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @RequestMapping("index")
    public String index(){
        return "adminpage/index";
    }

    @RequestMapping("/register")
    public String register(Business business, Model model){
        boolean result = false;
        try {
            if(business.getGenderCode() == 1){
                business.setGender(GenderEnum.MAN);
            }
            if(business.getGenderCode() == 0){
                business.setGender(GenderEnum.WOMAN);
            }
            result = businessService.save(business);
        } catch (Exception e) {
            model.addAttribute("error",business.getLoginName()+"已存在！请重新输入！");
            return "adminpage/register";
        }
        if(result) return "adminpage/login";
        return "adminpage/register";
    }

    /**
     * 登录
     * @param loginName
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("/login")
    public String login(String loginName, String password, HttpSession session){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("login_name",loginName);
        wrapper.eq("password",password);
        Business business = businessService.getOne(wrapper);
        if(business == null){
            return "adminpage/login";
        }else{
            session.setAttribute("business",business);
            return "adminpage/index";
        }
    }

    /**
     * 退出
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "adminpage/login";
    }

    /**
     * 用户信息
     */
    @GetMapping("/userInfo")
    public ModelAndView userInfo(HttpSession session){
        Business business = (Business) session.getAttribute("business");
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }


    @RequestMapping("/findAllTableUser")
    @ResponseBody
    public TableDataVO findAllTableUser(Integer page, Integer limit){
        return businessService.findAllTableData(page, limit);
    }


}