<template>
  <div class="register-container">
    <div class="register-card">
      <div class="register-header">
        <h1>创建账号</h1>
        <p>加入我们的动漫社区</p>
      </div>

      <el-form :model="registerForm" label-width="0">
        <el-form-item>
          <el-input
            v-model="registerForm.username"
            placeholder="用户名"
            clearable
          />
        </el-form-item>

        <el-form-item>
          <el-input
            v-model="registerForm.email"
            type="email"
            placeholder="邮箱"
            clearable
          />
        </el-form-item>

        <el-form-item>
          <el-input
            v-model="registerForm.nickname"
            placeholder="昵称"
            clearable
          />
        </el-form-item>

        <el-form-item>
          <el-input
            v-model="registerForm.password"
            type="password"
            placeholder="密码"
            clearable
            show-password
          />
        </el-form-item>

        <el-button type="primary" @click="handleRegister" class="register-btn" :loading="loading">
          注 册
        </el-button>
      </el-form>

      <div class="register-footer">
        <p>已有账号？<RouterLink to="/login" class="footer-link">立即登录</RouterLink></p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { authAPI } from '@/api'
import { ElMessage } from 'element-plus'

const router = useRouter()
const loading = ref(false)

const registerForm = ref({
  username: '',
  email: '',
  nickname: '',
  password: ''
})

const handleRegister = async () => {
  if (!registerForm.value.username || !registerForm.value.email || !registerForm.value.password) {
    ElMessage.error('请填写所有必填项')
    return
  }

  loading.value = true
  try {
    await authAPI.register(
      registerForm.value.username,
      registerForm.value.email,
      registerForm.value.password,
      registerForm.value.nickname
    )
    ElMessage.success('注册成功，请登录')
    router.push('/login')
  } catch (error) {
    ElMessage.error(error.response?.data?.error || '注册失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.register-card {
  width: 100%;
  max-width: 400px;
  padding: 40px;
  background: white;
  border-radius: 16px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
}

.register-header {
  text-align: center;
  margin-bottom: 30px;
}

.register-header h1 {
  font-size: 28px;
  margin-bottom: 10px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.register-header p {
  color: #999;
  font-size: 14px;
}

.register-btn {
  width: 100%;
  height: 44px;
  font-size: 16px;
  font-weight: 600;
  margin-top: 20px;
}

.register-footer {
  text-align: center;
  margin-top: 20px;
  font-size: 14px;
  color: #999;
}

.footer-link {
  color: #667eea;
  text-decoration: none;
  font-weight: 600;
}
</style>