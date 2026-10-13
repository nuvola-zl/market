package org.example.market.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Goods {
    private Integer id;   //商品ID
    private String name;   //商品名称
    private BigDecimal price;   //销售单价
    private String unit;   //单位
    private String image;   //商品图片路径
    private Integer stock;   //库存数量
    private String category;   //商品分类
    private String description;   //商品描述（可空）
}
