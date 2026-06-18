<template>
  <div class="anime-detail">
    <div class="detail-container" v-if="anime">
      <div class="detail-header">
        <img :src="anime.coverImage" class="cover-image" />
        <div class="anime-info">
          <h1>{{ anime.title }}</h1>
          <div class="rating-section">
            <el-rate v-model="anime.rating" disabled show-score />
            <span class="review-count">({{ anime.reviewCount }}条评论)</span>
          </div>
          <div class="info-items">
            <div class="info-item">
              <span class="label">作者:</span>
              <span>{{ anime.author }}</span>
            </div>
            <div class="info-item">
              <span class="label">导演:</span>
              <span>{{ anime.director }}</span>
            </div>
            <div class="info-item">
              <span class="label">工作室:</span>
              <span>{{ anime.studio }}</span>
            </div>
            <div class="info-item">
              <span class="label">集数:</span>
              <span>{{ anime.episodeCount }}</span>
            </div>
            <div class="info-item">
              <span class="label">播放时间:</span>
              <span>{{ anime.airDate }} - {{ anime.endDate }}</span>
            </div>
          </div>

          <div class="action-buttons">
            <el-button
              :type="isFavorited ? 'danger' : 'default'"
              @click="handleFavorite"
            >
              {{ isFavorited ? '✓ 已收藏' : '+ 收藏' }}
            </el-button>
            <el-button type="primary">👍 点赞</el-button>
            <el-button type="success">💬 评论</el-button>
          </div>
        </div>
      </div>

      <div class="detail-content">
        <div class="section">
          <h3>简介</h3>
          <p>{{ anime.description }}</p>
        </div>

        <div class="section">
          <h3>主要人物</h3>
          <div class="characters">{{ anime.characters }}</div>
        </div>

        <div class="section">
          <h3>用户评论</h3>
          <div class="review-form">
            <el-input v-model="reviewContent" type="textarea" placeholder="发表你的看法..." />
            <el-rate v-model="reviewRating" />
            <el-button type="primary" @click="submitReview">提交评论</el-button>
          </div>

          <div class="reviews-list">
            <div v-for="review in reviews" :key="review.id" class="review-item">
              <div class="review-header">
                <span class="reviewer-name">{{ review.user.nickname }}</span>
                <el-rate :model-value="review.rating" disabled />
              </div>
              <p>{{ review.content }}</p>
              <div class="review-footer">
                <span class="review-time">{{ formatDate(review.createdAt) }}</span>
                <el-button text type="primary" @click="likeReview(review.id)">
                  👍 {{ review.likeCount }}
                </el-button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { animeAPI, reviewAPI, favoriteAPI } from '@/api'
import { ElMessage } from 'element-plus'

const route = useRoute()
const anime = ref(null)
const reviews = ref([])
const isFavorited = ref(false)
const reviewContent = ref('')
const reviewRating = ref(5)

onMounted(async () => {
  try {
    const { data: animeData } = await animeAPI.getById(route.params.id)
    anime.value = animeData.data

    const { data: reviewsData } = await reviewAPI.getByAnime(route.params.id)
    reviews.value = reviewsData.data

    const { data: favData } = await favoriteAPI.isFavorited(route.params.id)
    isFavorited.value = favData.data
  } catch (error) {
    ElMessage.error('获取数据失败')
  }
})

const handleFavorite = async () => {
  try {
    if (isFavorited.value) {
      await favoriteAPI.removeFavorite(route.params.id)
      isFavorited.value = false
      ElMessage.success('已取消收藏')
    } else {
      await favoriteAPI.addFavorite(route.params.id)
      isFavorited.value = true
      ElMessage.success('已收藏')
    }
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const submitReview = async () => {
  if (!reviewContent.value.trim()) {
    ElMessage.error('请输入评论内容')
    return
  }

  try {
    await reviewAPI.createReview(route.params.id, reviewContent.value, reviewRating.value)
    ElMessage.success('评论发布成功')
    reviewContent.value = ''
    reviewRating.value = 5
    // 刷新评论列表
    const { data: reviewsData } = await reviewAPI.getByAnime(route.params.id)
    reviews.value = reviewsData.data
  } catch (error) {
    ElMessage.error('发布失败')
  }
}

const likeReview = async (reviewId) => {
  try {
    await reviewAPI.likeReview(reviewId)
    ElMessage.success('点赞成功')
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const formatDate = (date) => {
  return new Date(date).toLocaleDateString()
}
</script>

<style scoped>
.anime-detail {
  padding: 20px 0;
}

.detail-container {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.detail-header {
  display: flex;
  gap: 30px;
  padding: 30px;
  border-bottom: 1px solid #e0e0e0;
}

.cover-image {
  width: 250px;
  height: 350px;
  border-radius: 8px;
  object-fit: cover;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.anime-info {
  flex: 1;
}

.anime-info h1 {
  font-size: 32px;
  margin-bottom: 15px;
}

.rating-section {
  margin-bottom: 20px;
}

.review-count {
  margin-left: 10px;
  color: #999;
}

.info-items {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
  margin-bottom: 20px;
}

.info-item {
  display: flex;
  gap: 10px;
}

.info-item .label {
  font-weight: 600;
  color: #666;
  min-width: 60px;
}

.action-buttons {
  display: flex;
  gap: 10px;
}

.detail-content {
  padding: 30px;
}

.section {
  margin-bottom: 30px;
}

.section h3 {
  font-size: 18px;
  margin-bottom: 15px;
  border-left: 4px solid #667eea;
  padding-left: 10px;
}

.section p {
  line-height: 1.8;
  color: #666;
}

.characters {
  background: #f5f7fa;
  padding: 15px;
  border-radius: 8px;
  line-height: 1.8;
}

.review-form {
  background: #f5f7fa;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
}

.reviews-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.review-item {
  background: #f5f7fa;
  padding: 15px;
  border-radius: 8px;
}

.review-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.reviewer-name {
  font-weight: 600;
}

.review-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
  padding-top: 10px;
  border-top: 1px solid #e0e0e0;
  font-size: 12px;
}

.review-time {
  color: #999;
}
</style>