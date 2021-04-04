package com.example.fruit.businesscontroller;

import com.example.fruit.entity.ProductCategory;
import com.example.fruit.service.ProductCategoryService;
import com.example.fruit.util.PageRequest;
import com.example.fruit.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/business")
public class BusinessCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    //后台管理
    @RequestMapping("/category/listPager")
    public ModelAndView list(ProductCategory productCategory, HttpServletRequest request){
        PageRequest pageBean = new PageRequest(1,10);
        String pageNow = request.getParameter("pageNow");
        ModelAndView modelAndView = new ModelAndView();
        PageResult categories = productCategoryService.findPage(pageBean);
        modelAndView.addObject("categories",categories.getContent());
        PageResult pageResult=new PageResult();
        pageResult.setTotalPages(categories.getTotalPages());
        modelAndView.addObject("pageCode", pageResult/*.replaceAll("<","<").replaceAll("&gt:",">")*/);
        modelAndView.setViewName("adminpage/listCategory");
        return modelAndView;
    }

    @RequestMapping("/category/search")
    public String search(Model model, HttpServletRequest request){
        String name=request.getParameter("name");
        List<ProductCategory> categories=productCategoryService.findUserByName(name);
        model.addAttribute("categories",categories);
        return "adminpage/listCategory";
    }

    @RequestMapping("/category/toAdd")
    public String AddUser(ProductCategory productCategory){
        return "adminpage/categoryadd";
    }

    @RequestMapping("/category/add")
    public String add(ProductCategory productCategory,  HttpServletRequest request){
        productCategoryService.addProductCategory(productCategory);
        return "redirect:/business/category/listPager";
    }


    @RequestMapping("/category/toEdit")
    public ModelAndView toEdit(ProductCategory productCategory){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminpage/categoryedit");
        if(!(productCategory.getId() == null || "".equals(productCategory.getId()))) {
            ProductCategory t = productCategoryService.findProductCategoryById(productCategory.getId());
            modelAndView.addObject("category", t);
        }
        return modelAndView;
    }

    @RequestMapping("/category/edit")
    public String edit(ProductCategory productCategory,  HttpServletRequest request){
        productCategoryService.updateProductCategory(productCategory);
        return "redirect:/business/category/listPager";
    }

    @RequestMapping("/category/del/{bid}")
    public String del(@PathVariable(value = "bid") Integer bid){
        productCategoryService.deleteProductCategory(bid);
        return "redirect:/business/category/listPager";
    }
}
