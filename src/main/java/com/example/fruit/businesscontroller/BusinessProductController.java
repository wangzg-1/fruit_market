package com.example.fruit.businesscontroller;

import com.example.fruit.service.ProductService;
import com.example.fruit.util.StringUtils;
import com.example.fruit.entity.Product;
import com.example.fruit.util.PageRequest;
import com.example.fruit.util.PageResult;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/business")
public class BusinessProductController {
    @Autowired
    private ProductService productService;

    //后台管理
    @RequestMapping("/product/listPager")
    public ModelAndView list(Product product, HttpServletRequest request){
        PageRequest pageBean = new PageRequest(1,10);
        String pageNow = request.getParameter("pageNow");
        ModelAndView modelAndView = new ModelAndView();
        PageResult products = productService.findPage(pageBean);
        modelAndView.addObject("products",products.getContent());
        PageResult pageResult=new PageResult();
        pageResult.setTotalPages(products.getTotalPages());
        modelAndView.addObject("pageCode", pageResult/*.replaceAll("<","<").replaceAll("&gt:",">")*/);
        modelAndView.setViewName("adminpage/listProduct");
        return modelAndView;
    }

    @RequestMapping("/product/search")
    public String search(Model model, HttpServletRequest request){
        String name=request.getParameter("name");
        List<Product> products=productService.findUserByName(name);
        model.addAttribute("products",products);
        return "adminpage/listProduct";
    }

    @RequestMapping("/product/toAdd")
    public String AddUser(Product product){
        return "adminpage/productadd";
    }

    @RequestMapping("/product/add")
    public String add(Product product, MultipartFile img,HttpServletRequest request){
        try {
            String diskPath = "C://Users/YY/Desktop/mmall002/src/main/resources/static/images/"+img.getOriginalFilename();
            String serverPath = img.getOriginalFilename();
            if(StringUtils.isNotBlank(img.getOriginalFilename())){
                FileUtils.copyInputStreamToFile(img.getInputStream(),new File(diskPath));
                product.setFileName(serverPath);
            }
            productService.addProduct(product);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:/business/product/listPager";
    }


    @RequestMapping("/product/toEdit")
    public ModelAndView toEdit(Product product){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminpage/productadd");
        if(!(product.getId() == null || "".equals(product.getId()))) {
            Product t = productService.findProductById(product.getId());
            modelAndView.addObject("product", t);
        }
        return modelAndView;
    }

    @RequestMapping("/product/edit")
    public String edit(Product product,  MultipartFile img, HttpServletRequest request){
        try {
            String diskPath = "C://Users/YY/Desktop/mmall002/src/main/resources/static/images/"+img.getOriginalFilename();
            String serverPath = img.getOriginalFilename();
            if(StringUtils.isNotBlank(img.getOriginalFilename())){
                FileUtils.copyInputStreamToFile(img.getInputStream(),new File(diskPath));
                product.setFileName(serverPath);
            }
            productService.updateProduct(product);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:/business/product/listPager";
    }

    @RequestMapping("/product/del/{bid}")
    public String del(@PathVariable(value = "bid") Integer bid){
        productService.deleteProduct(bid);
        return "redirect:/business/product/listPager";
    }
}
