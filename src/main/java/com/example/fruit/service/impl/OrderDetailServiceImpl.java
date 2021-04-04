package com.example.fruit.service.impl;

import com.example.fruit.entity.OrderDetail;
import com.example.fruit.mapper.OrderDetailMapper;
import com.example.fruit.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}
