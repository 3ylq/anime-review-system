<template>
  <div class="forget-password-container">
    <div class="forget-password-card">
      <div class="header">
        <h1>重置密码</h1>
        <p>输入你的邮箱地址以重置密码</p>
      </div>

      <el-form :model="form" label-width="0">
        <el-form-item>
          <el-input
            v-model="form.email"
            type="email"
            placeholder="请输入邮箱"
            clearable
          />
        </el-form-item>

        <el-form-item>
          <el-input
            v-model="form.newPassword"
            type="password"
            placeholder="新密码"
            clearable
            show-password
          />
        </el-form-item>

        <el-button type="primary" @click="handleReset" class="reset-btn" :loading="loading">
          重 置
        </el-button>
      </el-form>

      <div class="footer">
        <RouterLink to="/login" class="back-link">返回登录</RouterLink>
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

const form = ref({
  email: '',
  newPassword: ''
})

const handleReset = async () => {
  if (!form.value.email || !form.value.newPassword) {
    ElMessage.error('请填写邮箱和新密码')
    return
  }

  loading.value = true
  try {
    await authAPI.resetPassword(form.value.email, form.value.newPassword)
    ElMessage.success('密码重置成功，请重新登录')
    router.push('/login')
  } catch (error) {
    ElMessage.error(error.response?.data?.error || '密码重置失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.forget-password-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.forget-password-card {
  width: 100%;
  max-width: 400px;
  padding: 40px;
  background: white;
  border-radius: 16px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
}

.header {
  text-align: center;
  margin-bottom: 30px;
}

.header h1 {
  font-size: 28px;
  margin-bottom: 10px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.reset-btn {
  width: 100%;
  height: 44px;
  font-size: 16px;
  font-weight: 600;
  margin-top: 20px;
}

.footer {
  text-align: center;
  margin-top: 20px;
}

.back-link {
  color: #667eea;
  text-decoration: none;
}
</style>