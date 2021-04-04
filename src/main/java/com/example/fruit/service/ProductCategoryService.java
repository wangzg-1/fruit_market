package com.example.fruit.service;

import com.example.fruit.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.fruit.util.PageRequest;
import com.example.fruit.util.PageResult;
import com.example.fruit.vo.ProductCategoryVO;
import com.example.fruit.vo.TableDataVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface ProductCategoryService extends IService<ProductCategory> {
    public List<ProductCategoryVO> getAllProductCategoryVO();

    public List<ProductCategory> findAll();

    public void addProductCategory(ProductCategory productCategory);

    public void updateProductCategory(ProductCategory productCategory);

    public void deleteProductCategory(Integer id) ;

    public List<ProductCategory> findUserByName(String name);

    public ProductCategory findProductCategoryById(Integer id) ;

    public TableDataVO findAllTableData(Integer page, Integer limit);

    PageResult findPage (PageRequest pageRequest);


}
