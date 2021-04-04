package com.example.fruit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.fruit.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.example.fruit.entity.Product;
import com.example.fruit.entity.ProductCategory;
import com.example.fruit.mapper.ProductCategoryMapper;
import com.example.fruit.mapper.ProductMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fruit.util.PageRequest;
import com.example.fruit.util.PageResult;
import com.example.fruit.util.PageUtils;
import com.example.fruit.vo.TableDataVO;
import com.example.fruit.vo.TableProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>

 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public List<Product> findByCategoryId(String type,Integer categoryId) {
        Map<String,Object> map = new HashMap<>();
        map.put("categorylevel"+type+"_id",categoryId);
        return productMapper.selectByMap(map);
    }

    @Override
    public void addProduct(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void updateProduct(Product product) {
        productMapper.updateById(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        productMapper.deleteById(id);
    }

    @Override
    public Product findProductById(Integer id) {
        Product product = productMapper.selectById(id);
        return product;
    }

    @Override
    public List<Product> findUserByName(String name) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.like("name",name);
        List<Product> list = productMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public TableDataVO<TableProductVO> findAllTableData(Integer page,Integer limit) {
        IPage<Product> productIPage = new Page<>(page,limit);
        IPage<Product> result = productMapper.selectPage(productIPage,null);
        List<Product> productList = result.getRecords();
        List<TableProductVO> tableProductVOList = new ArrayList<>();
        QueryWrapper wrapper;
        ProductCategory productCategory;
        for (Product product : productList) {
            TableProductVO tableProductVO = new TableProductVO();
            BeanUtils.copyProperties(product,tableProductVO);
            wrapper = new QueryWrapper();
            wrapper.eq("id",product.getCategoryleveloneId());
            productCategory = productCategoryMapper.selectOne(wrapper);
            if(productCategory != null){
                tableProductVO.setLevelOne(productCategory.getName());
            }


            wrapper = new QueryWrapper();
            wrapper.eq("id",product.getCategoryleveltwoId());
            productCategory = productCategoryMapper.selectOne(wrapper);
            if(productCategory != null){
                tableProductVO.setLevelTwo(productCategory.getName());
            }


            wrapper = new QueryWrapper();
            wrapper.eq("id",product.getCategorylevelthreeId());
            productCategory = productCategoryMapper.selectOne(wrapper);
            if(productCategory != null){
                tableProductVO.setLevelThree(productCategory.getName());
            }
            tableProductVOList.add(tableProductVO);
        }
        TableDataVO tableDataVO = new TableDataVO();
        tableDataVO.setCode(0);
        tableDataVO.setMsg("");
        tableDataVO.setData(tableProductVOList);
        tableDataVO.setCount(result.getTotal());
        return tableDataVO;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest));
    }

    private PageInfo<Product> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Product> sysMenus = productMapper.selectList(null);
        return new PageInfo<Product>(sysMenus);
    }
}
