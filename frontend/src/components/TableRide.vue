<template>
  <div class="p-8">
    <!-- Tabela de Viagens -->
    <el-table
      :data="tableData"
      style="width: 100%; border: none"
      :row-class-name="getRowClass"
      header-cell-class-name="custom-header"
      row-style="{ height: '65px' }"
    >
      <el-table-column prop="owner" label="Owner" align="left" />
      <el-table-column prop="date" label="Date" align="left" />
      <el-table-column prop="from" label="From" align="left" />
      <el-table-column prop="to" label="To" align="left" />
      <el-table-column prop="time" label="Time" align="left" />
      <el-table-column prop="value" label="Total value" align="left" />
      <el-table-column label="" align="left">
        <template #default="scope">
          <div class="action-buttons">
            <el-button
              size="small"
              class="details-btn"
              @click="showRideDetails(scope.row)"
            >
              Details
            </el-button>
            <el-button
              size="small"
              class="participate-btn"
              :class="scope.row.participating ? 'cancel' : 'participate'"
              @click="handleParticipation(scope.row)"
            >
              {{ scope.row.participating ? 'Cancel' : 'Participate' }}
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <!-- Modal de Participação -->
    <el-dialog v-model="showModal" width="400px" :show-close="true" center>
      <div class="modal-content">
        <h2 class="modal-title">{{ selectedRide.owner }} Ride</h2>
        <p><strong>Owner:</strong> {{ selectedRide.owner }}</p>
        <p><strong>Contact:</strong> +351 999 999 999</p>
        <p><strong>Date:</strong> {{ selectedRide.date }}</p>
        <p><strong>From:</strong> {{ selectedRide.from }}</p>
        <p><strong>To:</strong> {{ selectedRide.to }}</p>
        <p><strong>Time:</strong> {{ selectedRide.time }}h</p>
        <p><strong>Total value:</strong> {{ selectedRide.value }}</p>
        <p><strong>Total participants of this ride:</strong> 5</p>

        <el-form-item label="Pickup address">
          <el-input
            v-model="pickupAddress"
            placeholder="Enter your pickup location"
            clearable
          />
        </el-form-item>

        <div class="modal-footer">
          <el-button @click="showModal = false">Go back</el-button>
          <el-button
            type="primary"
            @click="openConfirmModal"
            :disabled="!pickupAddress.trim()"
          >
            Participate
          </el-button>
        </div>
      </div>
    </el-dialog>

    <!-- Modal de Confirmação de Participação -->
    <el-dialog
      v-model="showConfirmParticipationModal"
      title="Confirm Participation"
      width="400px"
      center
      top="23vh"
    >
      <div class="modal-content">
        <p>Are you sure you want to participate in this ride?</p>
        <div class="modal-footer">
          <el-button @click="showConfirmParticipationModal = false">No</el-button>
          <el-button type="primary" @click="confirmParticipation">
            Yes, participate
          </el-button>
        </div>
      </div>
    </el-dialog>

    <!-- Modal de Cancelamento -->
    <el-dialog
      v-model="showCancelModal"
      title="Cancel Participation?"
      width="400px"
      center
    >
      <div class="modal-content">
        <p>Are you sure you want to cancel your participation?</p>
        <div class="modal-footer">
          <el-button @click="showCancelModal = false">No</el-button>
          <el-button type="danger" @click="confirmCancel">Yes, cancel</el-button>
        </div>
      </div>
    </el-dialog>

    <!-- Modal de Detalhes -->
    <el-dialog
      v-model="showDetailsModal"
      title="Ride Details"
      width="400px"
      center
    >
      <div class="modal-content">
        <p><strong>Owner:</strong> {{ selectedDetailsRide.owner }}</p>
        <p><strong>Contact:</strong> +351 999 999 999</p>
        <p><strong>Date:</strong> {{ selectedDetailsRide.date }}</p>
        <p><strong>From:</strong> {{ selectedDetailsRide.from }}</p>
        <p><strong>To:</strong> {{ selectedDetailsRide.to }}</p>
        <p><strong>Time:</strong> {{ selectedDetailsRide.time }}h</p>
        <p><strong>Total value:</strong> {{ selectedDetailsRide.value }}</p>
        <div class="modal-footer">
          <el-button @click="showDetailsModal = false">Close</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const tableData = ref([
  {
    owner: 'Cláudio Souza',
    date: '13/10/2025',
    from: 'Ericeira',
    to: 'Porto',
    time: '11:00',
    value: '30€',
    participating: false
  }
])

const pickupAddress = ref('')
const showModal = ref(false)
const showCancelModal = ref(false)
const showDetailsModal = ref(false)
const showConfirmParticipationModal = ref(false)

const selectedRide = ref({})
const selectedDetailsRide = ref({})
const cancelTargetRide = ref({})

const handleParticipation = (ride) => {
  if (ride.participating) {
    cancelTargetRide.value = ride
    showCancelModal.value = true
  } else {
    selectedRide.value = ride
    showModal.value = true
  }
}

const openConfirmModal = () => {
  showConfirmParticipationModal.value = true
}

const confirmParticipation = () => {
  selectedRide.value.participating = true
  showConfirmParticipationModal.value = false
  showModal.value = false
}

const confirmCancel = () => {
  cancelTargetRide.value.participating = false
  showCancelModal.value = false
}

const showRideDetails = (ride) => {
  selectedDetailsRide.value = ride
  showDetailsModal.value = true
}

const getRowClass = ({ row }) => {
  return row.participating ? 'participating-row' : ''
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap');

::v-deep(.el-table) {
  font-family: 'Inter', sans-serif;
  font-size: 13px;
  color: #003369;
  background-color: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  width: 90%;
  margin: 0 auto;
}

.custom-header {
  font-family: 'Inter', sans-serif;
  font-weight: 600;
  font-size: 13px;
  background-color: #f8f9fa !important;
  color: #003369 !important;
  padding: 8px 4px !important;
  border-bottom: none !important;
}

::v-deep(.el-table th),
::v-deep(.el-table__header-wrapper th) {
  padding: 8px 4px !important;
  font-size: 13px;
  color: #003369;
  text-align: left !important;
  border-bottom: none !important;
  white-space: nowrap;
}

::v-deep(.el-table__row td),
::v-deep(.el-table__body-wrapper td) {
  padding: 14px 4px !important;
  font-size: 13px;
  color: #003369;
  text-align: left !important;
  border-bottom: 1px solid #f1f1f1;
  white-space: nowrap;
}

::v-deep(.el-table__body tr:hover > td) {
  background-color: #f9fafb !important;
}

.participating-row {
  background-color: #f3f4f6 !important;
}

::v-deep(.el-table__inner-wrapper) {
  padding-left: 0 !important;
  padding-right: 0 !important;
}

.action-buttons {
  display: flex;
  gap: 6px;
  align-items: center;
  justify-content: flex-start;
}

.details-btn {
  background-color: transparent !important;
  border: 1px solid #ccc !important;
  color: #333 !important;
  font-weight: 500;
  padding: 4px 10px;
  border-radius: 6px !important;
}

.participate-btn {
  font-weight: 500;
  color: white !important;
  border: none;
  padding: 4px 10px;
  border-radius: 6px;
}

.participate-btn.participate {
  background-color: #08C792 !important;
}

.participate-btn.cancel {
  background-color: #EE3030 !important;
}

/* Modal */
.modal-content {
  font-family: 'Inter', sans-serif;
  font-size: 14px;
  color: #2c3e50;
  padding: 8px;
}

.modal-title {
  font-weight: 600;
  font-size: 18px;
  margin-bottom: 16px;
  color: #1f2d3d;
}

.modal-footer {
  margin-top: 20px;
  text-align: right;
}

.el-dialog {
  border-radius: 12px !important;
}

::v-deep(.el-dialog__body) {
  padding: 20px !important;
}

::v-deep(.el-dialog__header) {
  font-family: 'Inter', sans-serif;
  font-size: 16px;
  font-weight: 600;
  color: #1f2d3d;
}

::v-deep(.el-dialog .el-button--primary) {
  background-color: #003369 !important;
  border-color: #003369 !important;
  color: #fff !important;
}

.pickup-input .el-input__wrapper {
  border-color: #003369;
}

.pickup-input .el-input__inner {
  color: #003369;
}
</style>
