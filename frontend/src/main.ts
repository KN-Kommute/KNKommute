import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import App from '@/App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

const app = createApp(App)
const pinia = createPinia()

// ⚠️ Primeiro regista o plugin no Pinia
pinia.use(piniaPluginPersistedstate)

// ✅ Depois usa o Pinia na app
app.use(pinia)
app.use(router)
app.use(ElementPlus)

app.mount('#app')
