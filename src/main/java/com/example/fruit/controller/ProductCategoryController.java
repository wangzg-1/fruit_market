package com.example.fruit.controller;


import com.example.fruit.entity.ProductCategory;
import com.example.fruit.entity.User;
import com.example.fruit.service.CartService;
import com.example.fruit.service.ProductCategoryService;
import com.example.fruit.vo.TableDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@Controller
@RequestMapping("/productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private CartService cartService;

    @GetMapping("/list")
    public ModelAndView list(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        modelAndView.addObject("list",productCategoryService.getAllProductCategoryVO());
        User user = (User)session.getAttribute("user");
        if(user == null){
            modelAndView.addObject("cartList",new ArrayList<>());
        }else{
            modelAndView.addObject("cartList",cartService.findAllCartVOByUserId(user.getId()));
        }
        return modelAndView;
    }


//    @ResponseBody
//    @RequestMapping("/listPage")
//    public Object listPage(){
//         Page<ProductCategory> page=new Page<>(1,5);
////         List<ProductCategory> records = productCategoryService.page(page).getRecords();
//        return productCategoryService.page(page);
//     }

    @RequestMapping("/findAllTableProductCategory")
    @ResponseBody
    public TableDataVO findAllTableProductCategory(Integer page, Integer limit){
        return productCategoryService.findAllTableData(page, limit);
    }

    @RequestMapping("/add")
    public String add(ProductCategory productCategory,  HttpServletRequest request){
        productCategoryService.addProductCategory(productCategory);
        return "redirect:/productCategory";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@RequestBody ProductCategory productCategory){
        productCategoryService.updateProductCategory(productCategory);
        return "redirect:/productCategory";
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public String del(@RequestBody Integer id){
        productCategoryService.deleteProductCategory(id);
        return "redirect:/productCategory";
    }
}

