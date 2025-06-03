import { defineStore } from 'pinia'

interface User {
  id: number
  name: string
  email: string
  phoneNumber: string
}

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null as User | null,
    token: '' as string
  }),
  actions: {
    setAuth(user: User, token: string) {
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
