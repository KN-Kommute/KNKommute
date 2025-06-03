// src/stores/auth.ts
import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null as null | { id: number; name: string; email: string },
    token: '' as string
  }),
  actions: {
    setAuth(user: any, token: string) {
      this.user = user
      this.token = token
    },
    logout() {
      this.user = null
      this.token = ''
    }
  },
  persist: true
})
