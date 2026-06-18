<template>
  <div class="forum-page">
    <div class="forum-header">
      <h2>论坛讨论</h2>
      <el-button type="primary" @click="showCreateDialog = true">发表新帖</el-button>
    </div>

    <!-- 推荐帖子 -->
    <div class="forum-section">
      <h3>推荐讨论</h3>
      <div class="posts-list">
        <div v-for="post in recommendedPosts" :key="post.id" class="post-item">
          <div class="post-left">
            <h4>{{ post.title }}</h4>
            <p>{{ post.content?.substring(0, 100) }}...</p>
            <div class="post-meta">
              <span>👤 {{ post.user?.nickname }}</span>
              <span>👁️ {{ post.viewCount }}</span>
              <span>👍 {{ post.likeCount }}</span>
              <span>💬 {{ post.replyCount }}</span>
            </div>
          </div>
          <div class="post-category">{{ post.category }}</div>
        </div>
      </div>
    </div>

    <!-- 最近热点 -->
    <div class="forum-section">
      <h3>最近热点</h3>
      <div class="posts-list">
        <div v-for="post in hotPosts" :key="post.id" class="post-item">
          <div class="post-left">
            <h4>{{ post.title }}</h4>
            <p>{{ post.content?.substring(0, 100) }}...</p>
            <div class="post-meta">
              <span>👤 {{ post.user?.nickname }}</span>
              <span>👁️ {{ post.viewCount }}</span>
              <span>👍 {{ post.likeCount }}</span>
              <span>💬 {{ post.replyCount }}</span>
            </div>
          </div>
          <div class="post-category">{{ post.category }}</div>
        </div>
      </div>
    </div>

    <!-- 创建帖子对话框 -->
    <el-dialog v-model="showCreateDialog" title="发表新帖" width="60%">
      <el-form :model="newPost" label-width="100px">
        <el-form-item label="标题">
          <el-input v-model="newPost.title" placeholder="请输���帖子标题" />
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="newPost.category">
            <el-option label="讨论" value="讨论" />
            <el-option label="资源" value="资源" />
            <el-option label="新闻" value="新闻" />
          </el-select>
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="newPost.content" type="textarea" rows="8" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showCreateDialog = false">取消</el-button>
        <el-button type="primary" @click="submitPost">发布</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { forumAPI } from '@/api'
import { ElMessage } from 'element-plus'

const recommendedPosts = ref([])
const hotPosts = ref([])
const showCreateDialog = ref(false)
const newPost = ref({
  title: '',
  category: '',
  content: ''
})

onMounted(async () => {
  try {
    const { data: recData } = await forumAPI.getRecommended()
    recommendedPosts.value = recData.data

    const { data: hotData } = await forumAPI.getHot()
    hotPosts.value = hotData.data
  } catch (error) {
    ElMessage.error('获取数据失败')
  }
})

const submitPost = async () => {
  if (!newPost.value.title || !newPost.value.category || !newPost.value.content) {
    ElMessage.error('请填写所有必填项')
    return
  }

  try {
    await forumAPI.createPost(newPost.value.title, newPost.value.content, newPost.value.category)
    ElMessage.success('帖子发布成功')
    showCreateDialog.value = false
    newPost.value = { title: '', category: '', content: '' }
    // 刷新列表
    const { data: recData } = await forumAPI.getRecommended()
    recommendedPosts.value = recData.data
  } catch (error) {
    ElMessage.error('发布失败')
  }
}
</script>

<style scoped>
.forum-page {
  padding: 20px 0;
}

.forum-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.forum-header h2 {
  font-size: 28px;
}

.forum-section {
  margin-bottom: 40px;
}

.forum-section h3 {
  font-size: 20px;
  margin-bottom: 15px;
  border-left: 4px solid #667eea;
  padding-left: 10px;
}

.posts-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.post-item {
  background: white;
  padding: 20px;
  border-radius: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  transition: all 0.3s;
}

.post-item:hover {
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.12);
  transform: translateX(5px);
}

.post-left {
  flex: 1;
}

.post-left h4 {
  font-size: 16px;
  margin-bottom: 8px;
  color: #333;
}

.post-left p {
  color: #999;
  font-size: 14px;
  margin-bottom: 10px;
}

.post-meta {
  display: flex;
  gap: 15px;
  font-size: 12px;
  color: #666;
}

.post-category {
  background: #f5f7fa;
  padding: 6px 12px;
  border-radius: 20px;
  font-size: 12px;
  color: #667eea;
  white-space: nowrap;
}
</style>