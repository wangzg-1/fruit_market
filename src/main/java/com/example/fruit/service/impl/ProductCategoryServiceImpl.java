package com.example.fruit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.example.fruit.entity.Product;
import com.example.fruit.entity.ProductCategory;
import com.example.fruit.mapper.ProductCategoryMapper;
import com.example.fruit.mapper.ProductMapper;
import com.example.fruit.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fruit.util.PageRequest;
import com.example.fruit.util.PageResult;
import com.example.fruit.util.PageUtils;
import com.example.fruit.vo.ProductCategoryVO;
import com.example.fruit.vo.ProductVO;
import com.example.fruit.vo.TableDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductCategoryVO> getAllProductCategoryVO() {
        //一级分类
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("type", 1);
        List<ProductCategory> levelOne = productCategoryMapper.selectList(wrapper);
        List<ProductCategoryVO> levelOneVO = levelOne.stream().map(e -> new ProductCategoryVO(e.getId(), e.getName())).collect(Collectors.toList());
        //图片赋值
        //商品信息赋值
        for (int i = 0; i < levelOneVO.size(); i++) {
            levelOneVO.get(i).setBannerImg("/images/banner" + i + ".png");
            levelOneVO.get(i).setTopImg("/images/top" + i + ".png");
            wrapper = new QueryWrapper();
            wrapper.eq("categorylevelone_id", levelOneVO.get(i).getId());
            List<Product> productList = productMapper.selectList(wrapper);
            List<ProductVO> productVOList = productList.stream()
                    .map(e -> new ProductVO(
                            e.getId(),
                            e.getName(),
                            e.getPrice(),
                            e.getFileName()
                    )).collect(Collectors.toList());
            levelOneVO.get(i).setProductVOList(productVOList);
        }
        for (ProductCategoryVO levelOneProductCategoryVO : levelOneVO) {
            wrapper = new QueryWrapper();
            wrapper.eq("type", 2);
            wrapper.eq("parent_id", levelOneProductCategoryVO.getId());
            List<ProductCategory> levelTwo = productCategoryMapper.selectList(wrapper);
            List<ProductCategoryVO> levelTwoVO = levelTwo.stream().map(e -> new ProductCategoryVO(e.getId(), e.getName())).collect(Collectors.toList());
            levelOneProductCategoryVO.setChildren(levelTwoVO);
            for (ProductCategoryVO levelTwoProductCategoryVO : levelTwoVO) {
                wrapper = new QueryWrapper();
                wrapper.eq("type", 3);
                wrapper.eq("parent_id", levelTwoProductCategoryVO.getId());
                List<ProductCategory> levelThree = productCategoryMapper.selectList(wrapper);
                List<ProductCategoryVO> levelThreeVO = levelThree.stream().map(e -> new ProductCategoryVO(e.getId(), e.getName())).collect(Collectors.toList());
                levelTwoProductCategoryVO.setChildren(levelThreeVO);
            }
        }
        return levelOneVO;
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryMapper.selectList(null);
    }

    @Override
    public void addProductCategory(ProductCategory productCategory) {
        productCategoryMapper.insert(productCategory);
    }

    @Override
    public void updateProductCategory(ProductCategory productCategory) {
        productCategoryMapper.updateById(productCategory);
    }

    @Override
    public void deleteProductCategory(Integer id) {
        productCategoryMapper.deleteById(id);
    }

    @Override
    public List<ProductCategory> findUserByName(String name) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.like("name",name);
        List<ProductCategory> list = productCategoryMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public ProductCategory findProductCategoryById(Integer id) {
        ProductCategory productCategory = productCategoryMapper.selectById(id);
        return productCategory;
    }

    @Override
    public TableDataVO findAllTableData(Integer page, Integer limit) {
        IPage<ProductCategory> productCategoryIPage = new Page<>(page,limit);
        IPage<ProductCategory> result = productCategoryMapper.selectPage(productCategoryIPage,null);
        List<ProductCategory> productCategoryList = result.getRecords();
        TableDataVO tableDataVO = new TableDataVO();
        tableDataVO.setCode(0);
        tableDataVO.setMsg("");
        tableDataVO.setData(productCategoryList);
        tableDataVO.setCount(result.getTotal());
        return tableDataVO;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest));
    }

    private PageInfo<ProductCategory> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<ProductCategory> sysMenus = productCategoryMapper.selectList(null);
        return new PageInfo<ProductCategory>(sysMenus);
    }


}
