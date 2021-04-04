//package com.example.fruit.businesscontroller;
//
//import com.example.fruit.entity.Orders;
//import com.example.fruit.service.OrderService;
//import com.example.fruit.util.PageRequest;
//import com.example.fruit.util.PageResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//@Controller
//@RequestMapping("business")
//public class BusinessOrderController {
//    @Autowired
//    private OrderService orderService;
//
//    //后台管理
//    @RequestMapping("/order/listPager")
//    public ModelAndView list(Orders order, HttpServletRequest request){
//        PageRequest pageBean = new PageRequest(1,10);
//        String pageNow = request.getParameter("pageNow");
//        ModelAndView modelAndView = new ModelAndView();
//        PageResult orders = orderService.findPage(pageBean);
//        modelAndView.addObject("orders",orders.getContent());
//        PageResult pageResult=new PageResult();
//        pageResult.setTotalPages(orders.getTotalPages());
//        modelAndView.addObject("pageCode", pageResult/*.replaceAll("<","<").replaceAll("&gt:",">")*/);
//        modelAndView.setViewName("adminpage/listorder");
//        return modelAndView;
//    }
//
//    @RequestMapping("/order/search")
//    public String search(Model model, HttpServletRequest request){
//        String name=request.getParameter("name");
//        List<Orders> orders=orderService.findUserByName(name);
//        model.addAttribute("orders",orders);
//        return "adminpage/listorder";
//    }
//
//}
