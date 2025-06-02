import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null as null | { email: string, name: string },
    isLoggedIn: false,
  }),

  actions: {
    login(userData: { email: string, name: string }) {
      this.user = userData
      this.isLoggedIn = true
    },

    logout() {
      this.user = null
      this.isLoggedIn = false
    }
  },

  persist: true
})
