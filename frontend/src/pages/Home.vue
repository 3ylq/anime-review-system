<template>
  <div class="home-page">
    <!-- 轮播图 -->
    <div class="carousel-section">
      <swiper :modules="modules" :slides-per-view="1" :autoplay="{ delay: 5000 }" :pagination="{ clickable: true }">
        <swiper-slide v-for="item in carouselItems" :key="item.id">
          <div class="carousel-slide" :style="{ backgroundImage: `url(${item.image})` }">
            <div class="carousel-content">
              <h2>{{ item.title }}</h2>
              <p>{{ item.desc }}</p>
            </div>
          </div>
        </swiper-slide>
      </swiper>
    </div>

    <!-- 分类导航 -->
    <div class="category-section">
      <h3>热门分类</h3>
      <div class="category-list">
        <div
          v-for="cat in categories"
          :key="cat"
          class="category-item"
          @click="selectCategory(cat)"
          :class="{ active: selectedCategory === cat }"
        >
          {{ cat }}
        </div>
      </div>
    </div>

    <!-- 评分排行榜 -->
    <div class="ranking-section">
      <h3>评分排行榜</h3>
      <div class="anime-grid">
        <anime-card
          v-for="anime in topRated"
          :key="anime.id"
          :anime="anime"
        />
      </div>
    </div>

    <!-- 最受欢迎 -->
    <div class="popular-section">
      <h3>最受欢迎</h3>
      <div class="anime-grid">
        <anime-card
          v-for="anime in mostPopular"
          :key="anime.id"
          :anime="anime"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { animeAPI } from '@/api'
import AnimeCard from '@/components/AnimeCard.vue'
import { Swiper, SwiperSlide } from 'swiper/vue'
import { Autoplay, Pagination } from 'swiper/modules'
import 'swiper/css'
import 'swiper/css/pagination'

const modules = [Autoplay, Pagination]
const categories = ref([])
const selectedCategory = ref('')
const topRated = ref([])
const mostPopular = ref([])

const carouselItems = [
  {
    id: 1,
    image: 'https://via.placeholder.com/1200x400?text=进击的巨人',
    title: '进击的巨人',
    desc: '这是一部关于人类与巨人之战的宏大史诗'
  },
  {
    id: 2,
    image: 'https://via.placeholder.com/1200x400?text=死亡笔记',
    title: '死亡笔记',
    desc: '一个天才少年与魔鬼笔记本的故事'
  },
  {
    id: 3,
    image: 'https://via.placeholder.com/1200x400?text=新世纪福音战士',
    title: '新世纪福音战士',
    desc: '日本动画史上的经典之作'
  }
]

onMounted(async () => {
  try {
    const { data: catData } = await animeAPI.getCategories()
    categories.value = catData.data

    const { data: topData } = await animeAPI.getTopRated(0, 8)
    topRated.value = topData.data

    const { data: popData } = await animeAPI.getMostPopular(0, 8)
    mostPopular.value = popData.data
  } catch (error) {
    console.error('获取数据失败:', error)
  }
})

const selectCategory = (cat) => {
  selectedCategory.value = cat
}
</script>

<style scoped>
.home-page {
  padding: 20px 0;
}

.carousel-section {
  margin-bottom: 40px;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.carousel-slide {
  height: 400px;
  background-size: cover;
  background-position: center;
  display: flex;
  align-items: center;
  justify-content: center;
}

.carousel-content {
  text-align: center;
  color: white;
  background: rgba(0, 0, 0, 0.3);
  padding: 40px;
  border-radius: 12px;
}

.carousel-content h2 {
  font-size: 36px;
  margin-bottom: 10px;
}

.carousel-content p {
  font-size: 16px;
}

.category-section {
  margin-bottom: 40px;
}

.category-section h3 {
  font-size: 20px;
  margin-bottom: 15px;
}

.category-list {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.category-item {
  padding: 8px 16px;
  background: white;
  border: 2px solid #e0e0e0;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s;
}

.category-item:hover,
.category-item.active {
  background: #667eea;
  color: white;
  border-color: #667eea;
}

.ranking-section,
.popular-section {
  margin-bottom: 40px;
}

.ranking-section h3,
.popular-section h3 {
  font-size: 20px;
  margin-bottom: 15px;
}

.anime-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 20px;
}

@media (max-width: 768px) {
  .anime-grid {
    grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
  }
}
</style>