# market —— 超市购物系统（Spring Boot + Thymeleaf 单体）

一个"啥都卖"的超市系统（前后端不分离），从商品上架到下单购买的全流程练手项目。

## 功能

- 用户注册 / 登录（拦截器登录校验）
- 商品浏览、上架、购买
- 商品管理（增删改查）
- 多类型商品模块（含游戏皮肤类商品）

## 技术栈

Spring Boot · Thymeleaf · MyBatis · MySQL · Spring MVC 拦截器 · Maven

## 运行

1. 创建 MySQL 数据库 `supermarket_db`；
2. 配置数据库连接（默认 `root` / `123456`）；
3. 运行 `MarketApplication`，访问 `http://localhost:8080`。

## 说明

- **商品图片未包含在仓库中**（体积原因，`static/image/` 已排除）：
  如需查看完整页面效果，请将商品图片放入 `src/main/resources/static/image/` 目录，
  图片与商品记录的对应关系见数据库。
- 这是学习过程中的练手项目，页面与逻辑保留了当时的原貌
