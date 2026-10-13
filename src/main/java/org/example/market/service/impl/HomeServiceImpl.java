package org.example.market.service.impl;

import jakarta.annotation.Resource;
import org.example.market.mapper.HomeMapper;
import org.example.market.pojo.Goods;
import org.example.market.pojo.User;
import org.example.market.service.HomeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {
    @Resource
    private HomeMapper homeMapper;
    @Override
    public void insertGoods(Goods goods) {
        //添加商品
        homeMapper.insertGoods(goods);
    }

    //查询所有商品
    @Override
    public List<Goods> getAllGoods() {
        return homeMapper.getAllGoods();
    }

    //根据分类查询商品
    @Override
    public List<Goods> getGoodsByCategory(String category) {
        return homeMapper.getGoodsByCategory(category);
    }

    //根据商品ID查询商品详情
    @Override
    public Goods getGoodsById(int id) {
        return homeMapper.getGoodsById(id);
    }

    //更新库存数量
    @Override
    public void updateStock(int num, int id) {
        homeMapper.updateStock(num, id);
    }

    //更新用户钱包余额
    @Override
    public void updateUserWallet(Integer id, BigDecimal newBalance) {
        homeMapper.updateUserWallet(id, newBalance);
    }

    //根据用户ID查询用户详情
    @Override
    public User getUserById(Integer id) {
        return homeMapper.getUserById(id);
    }


}
