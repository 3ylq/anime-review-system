# 🎬 动漫点评系统

一个基于 Spring Boot + Vue 3 + MySQL 的现代化动漫点评平台。

## ✨ 功能特性

### 🔐 用户模块
- 账号密码登录
- 用户注册
- 忘记密码功能
- 个人信息管理
- 修改密码

### 🏠 首页
- 动漫轮播图
- 搜索功能
- 动漫评分排行榜
- 最受欢迎动漫展示
- 动漫分类浏览

### 💬 论坛模块
- 论坛推荐
- 最近热点讨论
- 发表讨论帖
- 回复和评论

### 👤 个人中心
- 用户头像和基本信息
- 个人简介编辑
- 修改密码
- 我的收藏
- 我的评论历史
- 评论回复
- 消息通知

### 🎨 动漫详情页
- 收藏功能
- 点赞功能
- 评论功能
- 评分功能
- 动漫简介
- 主要人物介绍
- 作者信息

## 🎯 技术栈

### 后端
- Spring Boot 2.7.x
- Spring Data JPA
- MySQL 8.0
- JWT 身份认证
- Lombok
- Maven

### 前端
- Vue 3
- Vue Router 4
- Pinia (状态管理)
- Axios
- Vite
- TailwindCSS / Element Plus
- ECharts (数据可视化)

## 🚀 快速开始

### 后端启动

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

访问：`http://localhost:8080`

### 前端启动

```bash
cd frontend
npm install
npm run dev
```

访问：`http://localhost:5173`

## 📊 数据库配置

### MySQL 连接信息
```
Host: localhost
Port: 3306
Username: root
Password: root
Database: anime_review
```

### 初始化数据库

```sql
-- 运行 backend/src/main/resources/db/schema.sql
```

## 📁 主要文件说明

### 后端文件结构
- `AnimeReviewApplication.java` - 应用启动类
- `controller/` - 接口控制器
- `service/` - 业务逻辑
- `entity/` - JPA 实体
- `repository/` - 数据访问
- `config/` - Spring 配置

### 前端文件结构
- `pages/` - 页面组件
- `components/` - 可复用组件
- `router/` - 路由配置
- `store/` - 状态管理
- `api/` - 后端接口调用

## 🎨 设计特点

- 💎 **卡片式设计** - 整洁的卡片布局
- 🌊 **柔和阴影** - 营造深度感
- 🔄 **圆角元素** - 现代简约风格
- 📱 **响应式设计** - 适配各种屏幕
- ✨ **流畅动画** - 提升用户体验

## 📄 许可证

MIT License

## 👨‍💻 作者

Created with ❤️ for anime lovers
