<template>
  <div class="anime-card">
    <div class="card-image">
      <img :src="anime.coverImage" :alt="anime.title" />
      <div class="card-overlay">
        <RouterLink :to="`/anime/${anime.id}`" class="view-btn">
          查看详情
        </RouterLink>
      </div>
    </div>
    <div class="card-content">
      <h4>{{ anime.title }}</h4>
      <div class="card-info">
        <span class="rating">
          ⭐ {{ anime.rating }}
        </span>
        <span class="category">{{ anime.category }}</span>
      </div>
      <p class="description">{{ anime.description?.substring(0, 50) }}...</p>
    </div>
  </div>
</template>

<script setup>
defineProps({
  anime: {
    type: Object,
    required: true
  }
})
</script>

<style scoped>
.anime-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: all 0.3s;
  cursor: pointer;
}

.anime-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
}

.card-image {
  position: relative;
  overflow: hidden;
  height: 280px;
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s;
}

.anime-card:hover .card-image img {
  transform: scale(1.1);
}

.card-overlay {
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.3s;
}

.anime-card:hover .card-overlay {
  opacity: 1;
}

.view-btn {
  padding: 8px 20px;
  background: #667eea;
  color: white;
  border-radius: 20px;
  text-decoration: none;
  transition: background 0.3s;
}

.view-btn:hover {
  background: #764ba2;
}

.card-content {
  padding: 15px;
}

.card-content h4 {
  font-size: 14px;
  margin-bottom: 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.card-info {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  margin-bottom: 8px;
}

.rating {
  color: #ff9800;
  font-weight: 600;
}

.category {
  background: #f5f7fa;
  padding: 2px 8px;
  border-radius: 10px;
  color: #667eea;
}

.description {
  font-size: 12px;
  color: #999;
  line-height: 1.5;
}
</style>