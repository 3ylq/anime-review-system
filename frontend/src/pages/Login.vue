<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <h1>🎬 动漫点评系统</h1>
        <p>发现、评论、分享你喜爱的动漫</p>
      </div>

      <el-form :model="loginForm" label-width="0">
        <el-form-item>
          <el-input
            v-model="loginForm.usernameOrEmail"
            placeholder="用户名或邮箱"
            clearable
          />
        </el-form-item>

        <el-form-item>
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="密码"
            clearable
            show-password
          />
        </el-form-item>

        <el-button type="primary" @click="handleLogin" class="login-btn" :loading="loading">
          登 录
        </el-button>
      </el-form>

      <div class="login-footer">
        <RouterLink to="/forget-password" class="footer-link">忘记密码？</RouterLink>
        <RouterLink to="/register" class="footer-link">注册账号</RouterLink>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { authAPI } from '@/api'
import { ElMessage } from 'element-plus'

const router = useRouter()
const authStore = useAuthStore()
const loading = ref(false)

const loginForm = ref({
  usernameOrEmail: '',
  password: ''
})

const handleLogin = async () => {
  if (!loginForm.value.usernameOrEmail || !loginForm.value.password) {
    ElMessage.error('请输入用户名和密码')
    return
  }

  loading.value = true
  try {
    const { data } = await authAPI.login(
      loginForm.value.usernameOrEmail,
      loginForm.value.password
    )
    authStore.setToken(data.token)
    authStore.setUser(data.user)
    ElMessage.success('登录成功')
    router.push('/')
  } catch (error) {
    ElMessage.error(error.response?.data?.error || '登录失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  width: 100%;
  max-width: 400px;
  padding: 40px;
  background: white;
  border-radius: 16px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
  animation: slideUp 0.3s ease-out;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h1 {
  font-size: 28px;
  margin-bottom: 10px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.login-header p {
  color: #999;
  font-size: 14px;
}

.login-btn {
  width: 100%;
  height: 44px;
  font-size: 16px;
  font-weight: 600;
  margin-top: 20px;
}

.login-footer {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  font-size: 14px;
}

.footer-link {
  color: #667eea;
  text-decoration: none;
  transition: color 0.3s;
}

.footer-link:hover {
  color: #764ba2;
}
</style>