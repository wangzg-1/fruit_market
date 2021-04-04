package com.example.fruit.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.fruit.entity.ProductCategory;
import com.example.fruit.entity.User;
import com.example.fruit.mapper.ProductCategoryMapper;
import com.example.fruit.mapper.UserMapper;
import com.example.fruit.util.PageRequest;
import com.example.fruit.util.PageResult;
import com.example.fruit.vo.TableDataVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Test
    void test(){
        ProductCategory productCategory=new ProductCategory();
        productCategory.setName("黄忠");
        productCategory.setParentId(0);
        productCategory.setType(2);
        productCategoryService.addProductCategory(productCategory);
    }

    @Test
    void test2(){
        List<ProductCategory> list=productCategoryService.list();
        list.forEach(System.out::println);
    }

    @Test
    void test3(){
        Page<ProductCategory> page=new Page<>(1,5);
        productCategoryMapper.selectPage(page,null);
        page.getRecords().forEach(System.out::println);
    }

    @Test
    void test4(){
        IPage<User> userIPage = new Page<>(1,5);
        IPage<User> result = userMapper.selectPage(userIPage,null);
        List<User> userList = result.getRecords();
        TableDataVO tableDataVO = new TableDataVO();
        tableDataVO.setCode(0);
        tableDataVO.setMsg("");
        tableDataVO.setData(userList);
        tableDataVO.setCount(result.getTotal());
        System.out.println(tableDataVO);
    }

    @Test
    void test5(){
        TableDataVO allTableData = orderService.findAllTableData(1, 5);
        System.out.println(allTableData);
    }

    @Test
    void test6(){
        PageRequest cc=new PageRequest(1,5);
        PageResult page = userService.findPage(cc);
        page.getContent().forEach(System.out::println);
    }

    @Test
    void test7(){
        List<User> ck = userService.findUserByName("h");
        ck.forEach(System.out::println);
    }
}