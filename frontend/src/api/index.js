import api from './axios'

export const authAPI = {
  register: (username, email, password, nickname) => {
    return api.post('/auth/register', {
      username,
      email,
      password,
      nickname
    })
  },

  login: (usernameOrEmail, password) => {
    return api.post('/auth/login', {
      usernameOrEmail,
      password
    })
  },

  resetPassword: (email, newPassword) => {
    return api.post('/auth/reset-password', {
      email,
      newPassword
    })
  },

  changePassword: (oldPassword, newPassword) => {
    return api.post('/auth/change-password', {
      oldPassword,
      newPassword
    })
  }
}

export const animeAPI = {
  getAll: (page = 0, size = 10) => {
    return api.get('/anime', { params: { page, size } })
  },

  getById: (id) => {
    return api.get(`/anime/${id}`)
  },

  search: (keyword, page = 0, size = 10) => {
    return api.get('/anime/search', { params: { keyword, page, size } })
  },

  getByCategory: (category, page = 0, size = 10) => {
    return api.get(`/anime/category/${category}`, { params: { page, size } })
  },

  getTopRated: (page = 0, size = 10) => {
    return api.get('/anime/top-rated', { params: { page, size } })
  },

  getMostPopular: (page = 0, size = 10) => {
    return api.get('/anime/most-popular', { params: { page, size } })
  },

  getCategories: () => {
    return api.get('/anime/categories')
  }
}

export const reviewAPI = {
  createReview: (animeId, content, rating) => {
    return api.post('/review/create', {
      animeId,
      content,
      rating
    })
  },

  getByAnime: (animeId, page = 0, size = 10) => {
    return api.get(`/review/anime/${animeId}`, { params: { page, size } })
  },

  likeReview: (reviewId) => {
    return api.post(`/review/${reviewId}/like`)
  }
}

export const favoriteAPI = {
  addFavorite: (animeId) => {
    return api.post(`/favorite/${animeId}`)
  },

  removeFavorite: (animeId) => {
    return api.delete(`/favorite/${animeId}`)
  },

  isFavorited: (animeId) => {
    return api.get(`/favorite/${animeId}`)
  }
}

export const forumAPI = {
  createPost: (title, content, category) => {
    return api.post('/forum/post', {
      title,
      content,
      category
    })
  },

  getRecommended: (page = 0, size = 10) => {
    return api.get('/forum/recommended', { params: { page, size } })
  },

  getHot: (page = 0, size = 10) => {
    return api.get('/forum/hot', { params: { page, size } })
  }
}

export const userAPI = {
  getUserInfo: () => {
    return api.get('/user/info')
  },

  getUserProfile: (userId) => {
    return api.get(`/user/${userId}`)
  },

  updateUser: (data) => {
    return api.put('/user/update', data)
  }
}