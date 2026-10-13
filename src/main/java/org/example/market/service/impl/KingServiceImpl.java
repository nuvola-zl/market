package org.example.market.service.impl;

import jakarta.annotation.Resource;
import org.example.market.mapper.KingMapper;
import org.example.market.pojo.HeroSkin;
import org.example.market.service.KingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KingServiceImpl implements KingService {
    @Resource
    private KingMapper kingMapper;
    //添加商品皮肤
    @Override
    public void addKingSkin(HeroSkin heroSkin) {
        kingMapper.addHeroSkin(heroSkin);
    }

    //查询所有商品皮肤
    @Override
    public List<HeroSkin> getAllHeroSkinkins() {
        return kingMapper.getAllHeroSkinkins();
    }

    //根据英雄位置查询商品皮肤
    @Override
    public List<HeroSkin> getHeroSkinkinsByCategory(String category) {
        return kingMapper.getHeroSkinkinsByCategory(category);
    }

    //根据商品皮肤id查询商品皮肤
    @Override
    public HeroSkin getHeroSkinById(Integer id) {
        return kingMapper.getHeroSkinById(id);
    }

    //更新商品皮肤库存
    @Override
    public void updateHeroSkinStock(Integer id) {
        kingMapper.updateHeroSkinStock(id);
    }
}
