package com.example.fruit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fruit.entity.Business;
import com.example.fruit.entity.User;
import com.example.fruit.mapper.BusinessMapper;
import com.example.fruit.service.BusinessService;
import com.example.fruit.util.PageRequest;
import com.example.fruit.util.PageResult;
import com.example.fruit.util.PageUtils;
import com.example.fruit.vo.TableDataVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements BusinessService {
    @Autowired
    private BusinessMapper businessMapper;

    @Override
    public List<Business> findAll() {
        return businessMapper.selectList(null);
    }

    @Override
    public void addUser(Business business) {
        businessMapper.insert(business);
    }

    @Override
    public void updateUser(Business business) {
        businessMapper.updateById(business);
    }

    @Override
    public void deleteUser(Integer id) {
        businessMapper.deleteById(id);
    }

    @Override
    public Business findUserById(Integer id) {
        Business business = businessMapper.selectById(id);
        return business;
    }

    @Override
    public List<Business> findUserByName(String name) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.like("login_name",name);
        List<Business> list = businessMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest));
    }

    private PageInfo<Business> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Business> sysMenus = businessMapper.selectList(null);
        return new PageInfo<Business>(sysMenus);
    }

    @Override
    public TableDataVO findAllTableData(Integer page, Integer limit) {
        IPage<Business> userIPage = new Page<>(page,limit);
        IPage<Business> result = businessMapper.selectPage(userIPage,null);
        List<Business> userList = result.getRecords();
        TableDataVO tableDataVO = new TableDataVO();
        tableDataVO.setCode(0);
        tableDataVO.setMsg("");
        tableDataVO.setData(userList);
        tableDataVO.setCount(result.getTotal());
        return tableDataVO;
    }
}
