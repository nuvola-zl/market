package org.example.market.service;

import org.example.market.pojo.Goods;
import org.example.market.pojo.User;

import java.math.BigDecimal;
import java.util.List;

public interface HomeService {
    //添加商品
    void insertGoods(Goods goods);


    //查询所有商品
    List<Goods> getAllGoods();

    //根据分类查询商品
    List<Goods> getGoodsByCategory(String category);

    //根据商品ID查询商品详情
    Goods getGoodsById(int id);

    //更新库存数量
    void updateStock(int num, int id);

    //更新用户钱包余额
    void updateUserWallet(Integer id, BigDecimal newBalance);

    //根据用户ID查询用户详情
    User getUserById(Integer id);
}
