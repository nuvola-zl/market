package org.example.market.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HeroSkin {
    private Integer id; // 主键
    private String heroName; // 英雄名称
    private String heroPosition; // 英雄位置
    private String skinName; // 皮肤名称
    private String skinImg; // 皮肤图片
    private String skinQuality; // 皮肤品质
    private BigDecimal skinPrice; // 皮肤价格
    private Integer skinStock; // 皮肤库存
}
