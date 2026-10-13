package org.example.market.service;

import org.example.market.pojo.HeroSkin;

import java.util.List;

public interface KingService {
    //添加商品皮肤
    void addKingSkin(HeroSkin heroSkin);

    //查询所有商品皮肤
    List<HeroSkin> getAllHeroSkinkins();

    //根据英雄位置查询商品皮肤
    List<HeroSkin> getHeroSkinkinsByCategory(String category);

    //根据商品皮肤id查询商品皮肤
    HeroSkin getHeroSkinById(Integer id);

    //更新商品皮肤库存
    void updateHeroSkinStock(Integer id);
}
