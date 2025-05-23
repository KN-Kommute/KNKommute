    <template>
      <div class="login-page">
        <!-- Lado esquerdo do ecrã: login e branding -->
        <div class="left-side">
          <!-- Logos empilhadas uma em cima da outra -->
          <div class="logo-wrapper">
            <img src="@/assets/image 1.png" alt="Anchor Logo" class="anchor-logo" />
            <img src="@/assets/vector.png" alt="Car Logo" class="car-logo" />
          </div>

          <!-- Nome da marca -->
          <h1 class="brand-title"><span>KN</span>Kommute</h1>
          <p class="welcome-text">Welcome!</p>

          <!-- Formulário de login -->
          <form class="login-form" @submit.prevent="handleLogin">
            <!-- Campo do email -->
            <div class="form-group">
              <input id="email" type="email" v-model="email" placeholder="Email" required />
            </div>

            <!-- Campo da password -->
            <div class="form-group">
              <input id="password" type="password" v-model="password" placeholder="Password" required />
            </div>

            <!-- Mensagem de erro -->
            <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>

            <!-- Botões de ação -->
            <button type="submit" class="btn primary">Login</button>
            <button type="button" class="btn secondary" @click="goToRegister">Register</button>
          </form>
        </div>

        <!-- Imagem da direita -->
        <div class="right-side">
          <img src="@/assets/carrododrift.png" alt="Drifting Car" class="bg-image" />
        </div>
      </div>
    </template>

    <script setup lang="ts">
    import { ref } from 'vue'
    import { useRouter } from 'vue-router'

    const email = ref('')
    const password = ref('')
    const errorMessage = ref('')

    const router = useRouter()

    const login = (emailInput, passwordInput) => {
      const validUser = {
        email: 'teste@exemplo.com',
        password: '123456'
      }
      return emailInput === validUser.email && passwordInput === validUser.password
    }

    function handleLogin()   {
      if (login(email.value, password.value)) {
        errorMessage.value = ''
        router.push('/dashboard')
      } else {
        errorMessage.value = 'Credenciais inválidas. Por favor, tente novamente.'
      }
    }

    function goToRegister() {
      router.push('/register')
    }
    </script>

    <style scoped>
    @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600;800&display=swap');

    .login-page {
      display: flex;
      height: 100vh;
      font-family: 'Inter', sans-serif;
    }

    .left-side {
      width: 50%;
      padding: 40px;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
    }

    .right-side {
      width: 50%;
      height: 100%;
      overflow: hidden;
    }

    .bg-image {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    .logo-wrapper {
      position: relative;
      width: 149px;
      height: 132px;
      margin-bottom: 20px;
    }

    .anchor-logo {
      position: absolute;
      bottom: 132px;
      left: 50%;
      transform: translateX(-50%);
      width: 42px;
      height: 42px;
    }

    .car-logo {
      width: 149px;
      height: 132px;
      margin: 0 auto;
      display: block;
    }

    .brand-title {
      color: #002f6c;
      font-size: 40px;
      font-weight: 400;
      text-align: center;
    }

    .brand-title span {
      display: block;
      letter-spacing: -1px;
    }

    .welcome-text {
      margin: 20px 0;
      font-size: 18px;
      color: #002f6c;
      font-weight: 500;
    }

    .login-form {
      display: flex;
      flex-direction: column;
      gap: 20px;
      width: 100%;
      max-width: 320px;
    }

    .form-group {
      display: flex;
      flex-direction: column;
    }

    .form-group label {
      margin-bottom: 6px;
      font-weight: 500;
      color: #002f6c;
    }

    input {
      padding: 10px;
      font-size: 16px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }

    .btn {
      padding: 12px;
      font-size: 16px;
      cursor: pointer;
      transition: background-color 0.3s, color 0.3s;
      border-radius: 4px;
    }

    /* Login */
    .btn.primary {
      background-color: #002f6c;
      color: white;
      border: none;
    }

    /* Register */
    .btn.secondary {
      background-color: white;
        color: #333;
        border: 1px solid #ccc;
    }

    .error-message {
      color: red;
      font-size: 14px;
      text-align: center;
    }
    </style>
