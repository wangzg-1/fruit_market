package com.example.fruit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.fruit.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.example.fruit.entity.User;
import com.example.fruit.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fruit.util.PageRequest;
import com.example.fruit.util.PageResult;
import com.example.fruit.util.PageUtils;
import com.example.fruit.vo.TableDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.selectList(null);
    }

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public User findUserById(Integer id) {
        User user = userMapper.selectById(id);
        return user;
    }

    @Override
    public List<User> findUserByName(String name) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.like("login_name",name);
        List<User> list = userMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest));
    }

    private PageInfo<User> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<User> sysMenus = userMapper.selectList(null);
        return new PageInfo<User>(sysMenus);
    }

    @Override
    public TableDataVO findAllTableData(Integer page, Integer limit) {
        IPage<User> userIPage = new Page<>(page,limit);
        IPage<User> result = userMapper.selectPage(userIPage,null);
        List<User> userList = result.getRecords();
        TableDataVO tableDataVO = new TableDataVO();
        tableDataVO.setCode(0);
        tableDataVO.setMsg("");
        tableDataVO.setData(userList);
        tableDataVO.setCount(result.getTotal());
        return tableDataVO;
    }
}
