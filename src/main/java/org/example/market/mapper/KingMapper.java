package org.example.market.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.market.pojo.HeroSkin;

import java.util.List;

@Mapper
public interface KingMapper {
    //添加商品皮肤
    @Insert("insert into hero_skin (hero_name, hero_position, skin_name, skin_img,skin_quality,skin_price,skin_stock) " +
            "values (#{heroName}, #{heroPosition}, #{skinName}, #{skinImg}, #{skinQuality}, #{skinPrice}, #{skinStock})")
    void addHeroSkin(HeroSkin heroSkin);

    //查询所有商品皮肤
    @Select("select * from hero_skin")
    List<HeroSkin> getAllHeroSkinkins();

    //根据英雄位置查询商品皮肤
    @Select("select * from hero_skin where hero_position = #{category}")
    List<HeroSkin> getHeroSkinkinsByCategory(String category);

    //根据商品皮肤id查询商品皮肤
    @Select("select * from hero_skin where id = #{id}")
    HeroSkin getHeroSkinById(Integer id);

    //更新商品皮肤库存
    @Update("update hero_skin set skin_stock = skin_stock - 1 where id = #{id}")
    void updateHeroSkinStock(Integer id);
}
