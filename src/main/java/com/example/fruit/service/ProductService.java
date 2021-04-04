package com.example.fruit.service;

import com.example.fruit.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.fruit.util.PageRequest;
import com.example.fruit.util.PageResult;
import com.example.fruit.vo.TableDataVO;
import com.example.fruit.vo.TableProductVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface ProductService extends IService<Product> {
    public List<Product> findByCategoryId(String type,Integer categoryId);

    public void addProduct(Product product);

    public void updateProduct(Product product);

    public void deleteProduct(Integer id) ;

    public Product findProductById(Integer id) ;

    public List<Product> findUserByName(String name);

    /**
     * 后台管理系统返回商品数据
     */
    public TableDataVO<TableProductVO> findAllTableData(Integer page,Integer limit);

    PageResult findPage (PageRequest pageRequest);
}
