    <template>
      <div class="dashboard">
        <aside class="sidebar">
          <div class="user-header">
            <img src="@/assets/userprofile.png" alt="User Avatar" class="avatar" />
            <div class="user-text">
              <span class="welcome">Welcome,</span>
              <span class="username"></span>
            </div>
          </div>
          <nav class="nav">
            <button class="nav-item" @click="goToRides">
              <span>Rides</span>
            </button>
            <button class="nav-item active">
              <span>Profile</span>
            </button>
          </nav>
          <button class="logout-btn" @click="handleLogout">Logout</button>
        </aside>


        <!-- Main Content -->
        <main class="content">
          <div class="profile-section">
            <div class="logo-wrapper">
              <img src="@/assets/image 1.png" alt="Anchor Logo" class="anchor-logo" />
              <img src="@/assets/Vector.png" alt="Car Logo" class="car-logo" />
            </div>
            <h2 class="profile-title">Profile</h2>
            <hr />
            <form class="profile-form">
              <input type="email" value=""placeholder="Email" />
              <input type="text" value="" placeholder="Mobile Number" />
              <input type="password" placeholder="New password" />
              <input type="password" placeholder="Repeat new password" />
              <button type="submit" class="save-btn">Save profile</button>
            </form>
          </div>
        </main>
      </div>
    </template>

    <script setup>
    import { useRouter } from 'vue-router'

    const router = useRouter()

    function handleLogout() {
      router.push('/login')
    }

    function goToRides() {
      router.push('/rides')
    }
    </script>



    <style scoped>
    .dashboard {
      display: flex;
      height: 100vh;
      font-family: 'Inter', sans-serif;
      font-size: 18px; /* Aumenta o tamanho base */
    }

    .sidebar {
      width: 400px; /* aumentado */
      background-color: #002f6c;
      color: white;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      padding: 30px 24px;
    }

    .user-header {
      display: flex;
      align-items: center;
      gap: 16px;
    }

    .avatar {
      width: 70px;
      height: 70px;
      border-radius: 50%;
    }

    .user-text {
      display: flex;
      flex-direction: column;
    }

    .welcome {
      font-size: 16px;
      opacity: 0.9;
    }

    .username {
      font-weight: 600;
      font-size: 18px;
    }

    .nav {
      display: flex;
      flex-direction: column;
      gap: 14px;
      margin-top: 40px;
    }

    .nav-item {
      background: none;
      border: none;
      color: white;
      display: flex;
      align-items: center;
      padding: 12px 14px;
      gap: 12px;
      font-size: 18px;
      cursor: pointer;
    }

    .nav-item.active {
      background-color: white;
      color: #002f6c;
      border-radius: 10px;
    }

    .logout-btn {
      background-color: #d92d20;
      color: white;
      border: none;
      padding: 12px;
      font-weight: bold;
      font-size: 16px;
      cursor: pointer;
      width: 100%;
      margin-top: auto;
    }

    /* Main Content */
    .content {
      flex: 1;
      padding: 60px;
    }

    .profile-section {
      max-width: 600px;
      margin: 0 auto;
      text-align: center;
    }

    /* Logos agrupados */
.logo-wrapper {
  position: relative;
  width: 120px;
  height: 90px;
  margin: 0 auto 30px auto;
}

.car-logo {
  width: 100px;    /* igual ao .top-car-icon */
  height: auto;
  display: block;
  margin: 0 auto;
}

.anchor-logo {
  position: absolute;
  top: -27px;      /* igual ao .anchor-icon */
  left: 50%;
  transform: translateX(-50%);
  width: 30px;     /* igual ao .anchor-icon */
  height: auto;
}


    /* Título */
    .profile-title {
      color: #002f6c;
      font-size: 24px;
      font-weight: 600;
      margin-bottom: 16px;
    }

    hr {
      border: none;
      border-top: 1px solid #d0d0d0;
      margin-bottom: 32px;
    }

    /* Formulário */
    .profile-form {
      display: flex;
      flex-direction: column;
      gap: 20px;
      font-size: 16px;
    }

    .profile-form input {
      padding: 14px;
      font-size: 16px;
      border: 1px solid #ccc;
      border-radius: 6px;
    }

    .save-btn {
      background-color: #002f6c;
      color: white;
      border: none;
      padding: 14px 24px;
      font-weight: bold;
      cursor: pointer;
      align-self: flex-end;
      font-size: 16px;
      border-radius: 6px;
    }
    </style>
