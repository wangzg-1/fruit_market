package com.example.fruit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.fruit.entity.Business;
import com.example.fruit.util.PageRequest;
import com.example.fruit.util.PageResult;
import com.example.fruit.vo.TableDataVO;

import java.util.List;

public interface BusinessService extends IService<Business> {
    public List<Business> findAll();

    public void addUser(Business business);

    public void updateUser(Business business);

    public void deleteUser(Integer id) ;

    public Business findUserById(Integer id) ;

    public List<Business> findUserByName(String name);

    /**
     * 分页查询接口
     * 这里统一封装了分页请求和结果，避免直接引入具体框架的分页对象, 如MyBatis或JPA的分页对象
     * 从而避免因为替换ORM框架而导致服务层、控制层的分页接口也需要变动的情况，替换ORM框架也不会
     * 影响服务层以上的分页接口，起到了解耦的作用
     * @param pageRequest 自定义，统一分页查询请求
     * @return PageResult 自定义，统一分页查询结果
     */
    PageResult findPage (PageRequest pageRequest);

    public TableDataVO findAllTableData(Integer page, Integer limit);
}
