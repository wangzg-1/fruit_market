package com.example.fruit.service;

import com.example.fruit.entity.Orders;
import com.example.fruit.entity.User;
import com.example.fruit.util.PageRequest;
import com.example.fruit.util.PageResult;
import com.example.fruit.vo.OrderVO;
import com.example.fruit.vo.TableDataVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface OrderService extends IService<Orders> {
    public boolean save(Orders orders, User user, String address, String remark);

    public List<OrderVO> findAllOrederVOByUserId(Integer id);

    public List<Orders> findUserByName(String name);

    public TableDataVO findAllTableData(Integer page, Integer limit);

    PageResult findPage (PageRequest pageRequest);
}
