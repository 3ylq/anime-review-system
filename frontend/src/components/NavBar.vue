<template>
  <nav class="navbar">
    <div class="nav-container">
      <div class="nav-logo">
        <RouterLink to="/">
          <span class="logo-icon">🎬</span>
          <span class="logo-text">动漫点评</span>
        </RouterLink>
      </div>

      <ul class="nav-menu">
        <li>
          <RouterLink to="/" class="nav-link" :class="{ active: route.name === 'HomePage' }">
            首页
          </RouterLink>
        </li>
        <li>
          <RouterLink to="/forum" class="nav-link" :class="{ active: route.name === 'Forum' }">
            论坛
          </RouterLink>
        </li>
      </ul>

      <div class="nav-search">
        <el-input
          v-model="searchKeyword"
          placeholder="搜索动漫"
          clearable
          @keyup.enter="handleSearch"
        >
          <template #suffix>
            <el-icon @click="handleSearch" class="search-icon">
              <Search />
            </el-icon>
          </template>
        </el-input>
      </div>

      <div class="nav-user">
        <el-dropdown @command="handleCommand">
          <div class="user-info">
            <img :src="authStore.user?.avatar || 'https://via.placeholder.com/40'" class="avatar" />
            <span>{{ authStore.user?.nickname || authStore.user?.username }}</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="profile">我的主页</el-dropdown-item>
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { ElMessage } from 'element-plus'
import { Search } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()
const searchKeyword = ref('')

const handleSearch = () => {
  if (searchKeyword.value.trim()) {
    router.push({
      name: 'HomePage',
      query: { search: searchKeyword.value }
    })
  }
}

const handleCommand = (command) => {
  if (command === 'profile') {
    router.push('/profile')
  } else if (command === 'logout') {
    authStore.logout()
    ElMessage.success('已退出登录')
    router.push('/login')
  }
}
</script>

<style scoped>
.navbar {
  background: white;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 70px;
}

.nav-logo a {
  display: flex;
  align-items: center;
  gap: 8px;
  text-decoration: none;
  color: #333;
  font-size: 18px;
  font-weight: 600;
}

.logo-icon {
  font-size: 24px;
}

.nav-menu {
  display: flex;
  list-style: none;
  gap: 30px;
  flex: 1;
  margin: 0 40px;
}

.nav-link {
  text-decoration: none;
  color: #666;
  font-size: 14px;
  padding: 5px 0;
  border-bottom: 2px solid transparent;
  transition: all 0.3s;
}

.nav-link:hover,
.nav-link.active {
  color: #667eea;
  border-bottom-color: #667eea;
}

.nav-search {
  width: 200px;
  margin-right: 20px;
}

.search-icon {
  cursor: pointer;
  color: #667eea;
}

.nav-user {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  padding: 5px 10px;
  border-radius: 8px;
  transition: background 0.3s;
}

.user-info:hover {
  background: #f5f7fa;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}
</style>