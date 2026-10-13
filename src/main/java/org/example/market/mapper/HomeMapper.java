package org.example.market.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.market.pojo.Goods;
import org.example.market.pojo.User;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface HomeMapper {
    //添加商品
    @Insert("insert into goods (name, price,unit,image, stock,category, description) values (#{name}, #{price}, #{unit}, #{image}, #{stock}, #{category}, #{description})")
    void insertGoods(Goods goods);

    //查询所有商品
    @Select("select * from goods")
    List<Goods> getAllGoods();

    //根据分类查询商品
    @Select("select * from goods where category = #{category}")
    List<Goods> getGoodsByCategory(String category);


    //根据商品ID查询商品详情
    @Select("select * from goods where id = #{id}")
    Goods getGoodsById(int id);

    //更新库存数量
    @Update("update goods set stock = stock - #{num} where id = #{id}")
    void updateStock(int num, int id);

    //更新用户钱包余额
    @Update("update user set wallet_balance = #{newBalance} where id = #{id}")
    void updateUserWallet(Integer id, BigDecimal newBalance);

    //根据用户ID查询用户详情
    @Select("select * from user where id = #{id}")
    User getUserById(Integer id);
}
