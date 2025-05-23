<template>
  <el-dialog
    v-model="visible"
    title=""
    width="400px"
    :before-close="handleClose"
    class="custom-dialog modal"
  >
    <template #title>
      <h2 class="modal-title modal__title">New ride</h2>
    </template>

    <el-form label-position="top" class="form-styled modal__form">
      <el-form-item label="Origin" class="modal__form-item">
        <el-input
          v-model="origin"
          placeholder="Origin"
          class="input-styled modal__input"
        />
      </el-form-item>

      <el-form-item label="Destination" class="modal__form-item">
        <el-input
          v-model="destination"
          placeholder="Destination"
          class="input-styled modal__input"
        />
      </el-form-item>

      <el-form-item label="Date and time of departure" class="modal__form-item">
        <el-date-picker
          v-model="departure"
          type="datetime"
          placeholder="Date and time of departure"
          style="width: 100%;"
          class="modal__datepicker"
        />
      </el-form-item>

      <el-form-item label="Total cost of trip" class="modal__form-item">
        <el-input
          v-model="cost"
          type="number"
          placeholder="Total cost of trip"
          class="input-styled modal__input"
        />
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="footer-buttons modal__footer">
        <el-button @click="handleClose" class="cancel-btn modal__button modal__button--cancel">Go back</el-button>
        <el-button type="primary" @click="createRide" class="create-btn modal__button modal__button--create">Create</el-button>
      </div>
    </template>
  </el-dialog>
</template>


<script setup lang="ts">
import { ref, watch } from 'vue'

const props = defineProps<{ modelValue: boolean }>()
const emit = defineEmits(['update:modelValue', 'create'])

const visible = ref(props.modelValue)

watch(() => props.modelValue, (val) => {
  visible.value = val
})

watch(visible, (val) => {
  emit('update:modelValue', val)
})

const origin = ref('')
const destination = ref('')
const departure = ref('')
const cost = ref<number | null>(null)

function handleClose() {
  visible.value = false
}

function createRide() {
  emit('create', {
    origin: origin.value,
    destination: destination.value,
    departure: departure.value,
    cost: cost.value
  })
  handleClose()
}
</script>

<style lang="scss" scoped>
.modal {
  >>> .el-dialog {
    position: absolute;
    left: 100%;
    top: 15vh;
    transform: translateX(-50%);
    max-width: 500px;
    width: 100%;
  }

  >>> .el-dialog__header {
    padding-bottom: 0;
  }

  &__title {
    font-size: 20px;
    font-weight: 600;
    color: #002f6c;
    margin: 0;
  }

  &__input {
    >>> .el-input__inner {
      border-radius: 4px;
      padding: 10px;
    }
  }

  &__footer {
    display: flex;
    justify-content: space-between;
    padding: 0 8px;
  }

  &__button {
    &--cancel {
      background-color: white;
      border: 1px solid #ccc;
      color: #002f6c;
    }

    &--create {
      background-color: #002f6c;
      color: white;
      border: none;
    }
  }
}
</style>


