package com.example.fruit.service;

import com.example.fruit.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.fruit.vo.CartVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface CartService extends IService<Cart> {
    public List<CartVO> findAllCartVOByUserId(Integer id);
}
