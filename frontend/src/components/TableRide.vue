<template>
  <div class="p-8">
    <el-table :data="rides" style="width: 100%; border: none" :row-class-name="getRowClass">
      <!--header-cell-class-name="custom-header"-->
      <el-table-column prop="owner" label="Owner" align="left" min-width="70" />
      <el-table-column prop="phoneNumber" label="Phone" align="left" min-width="70"/>
      <el-table-column prop="date" label="Date" align="left" min-width="70"/>
      <el-table-column prop="from" label="From" align="left" min-width="70"/>
      <el-table-column prop="to" label="To" align="left" min-width="70"/>
      <el-table-column prop="time" label="Time" align="left" min-width="70"/>
      <el-table-column prop="value" label="Cost" align="left" min-width="40"/>
      <el-table-column label="" align="left">
        <template #default="scope">
          <div class="action-buttons">
            <el-button size="small" class="details-btn" @click="showRideDetails(scope.row)">
              Details
            </el-button>
            <el-button size="small" class="participate-btn"
                       :class="scope.row.participating ? 'cancel' : 'participate'"
                       @click="handleParticipation(scope.row)"
                       v-if="scope.row.ownerId !== authStore.user.id">
              {{ scope.row.participating ? 'Cancel' : 'Participate' }}
            </el-button>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="" align="left">
        <template #default="scope">
          <div class="action-buttons">
            <el-button size="small" class="approve-btn" @click="showRideToApprove(scope.row)"
                       v-if="scope.row.ownerId === authStore.user.id">
              Participations
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <!-- Modal Participação -->
    <el-dialog v-model="showModal" width="400px" center>
      <div class="modal-content">
        <h2 class="modal-title">{{ selectedRide.owner }} Ride</h2>
        <p><strong>Owner:</strong> {{ selectedRide.owner }}</p>
        <p><strong>Contact:</strong> {{ selectedRide.phoneNumber }}</p>
        <p><strong>Date:</strong> {{ selectedRide.date }}</p>
        <p><strong>From:</strong> {{ selectedRide.from }}</p>
        <p><strong>To:</strong> {{ selectedRide.to }}</p>
        <p><strong>Time:</strong> {{ selectedRide.time }}h</p>
        <p><strong>Total value:</strong> {{ selectedRide.value }}</p>
        <p><strong>Total participants of this ride:</strong> {{ selectedRide.participantCount }}</p>


        <el-form-item>
          <el-input v-model="pickupAddress" placeholder="Enter your pickup location" clearable />
        </el-form-item>

        <el-form-item>
          <el-date-picker v-model="pickupTime" type="datetime"
                          placeholder="Select pickup date and time"
                          format="YYYY-MM-DD HH:mm" value-format="YYYY-MM-DDTHH:mm"
                          style="width: 100%" />
        </el-form-item>
        <div class="modal-footer">
          <el-button @click="showModal = false">Go back</el-button>
          <el-button type="primary" @click="openConfirmModal"
                     :disabled="!pickupAddress.trim() || !pickupTime">
            Participate
          </el-button>
        </div>
      </div>
    </el-dialog>

    <!-- Modal confirmação participação -->
    <el-dialog v-model="showConfirmParticipationModal" title="Confirm Participation" width="400px"
               center top="23vh">
      <div class="modal-content">
        <p>Are you sure you want to participate in this ride?</p>
        <div class="modal-footer">
          <el-button @click="showConfirmParticipationModal = false">No</el-button>
          <el-button type="primary" @click="confirmParticipation"> Yes, participate</el-button>
        </div>
      </div>
    </el-dialog>

    <!-- Modal cancelar participação -->
    <el-dialog v-model="showCancelModal" title="Cancel Participation?" width="400px" center>
      <div class="modal-content">
        <p>Are you sure you want to cancel your participation?</p>
        <div class="modal-footer">
          <el-button @click="showCancelModal = false">No</el-button>
          <el-button type="danger" @click="confirmCancel()">Yes, cancel</el-button>
        </div>
      </div>
    </el-dialog>

    <!-- Modal detalhes da boleia -->
    <el-dialog v-model="showDetailsModal" title="Ride Details" width="400px" center>
      <div class="modal-content">
        <p><strong>Owner:</strong> {{ selectedDetailsRide.owner }}</p>
        <p><strong>Phone:</strong> {{ selectedDetailsRide.phoneNumber }}</p>
        <p><strong>Date:</strong> {{ selectedDetailsRide.date }}</p>
        <p><strong>From:</strong> {{ selectedDetailsRide.from }}</p>
        <p><strong>To:</strong> {{ selectedDetailsRide.to }}</p>
        <p><strong>Time:</strong> {{ selectedDetailsRide.time }}h</p>
        <p><strong>Cost:</strong> {{ selectedDetailsRide.value }}</p>
        <div class="modal-footer">
          <el-button @click="showDetailsModal = false">Close</el-button>
        </div>
      </div>
    </el-dialog>

    <!-- Modal rides to approve -->
    <el-dialog v-model="showRidesToApproveModal" title="Ride to Approve Details" center>
      <div class="modal-content">
        <!-- TABELA DE PEDIDOS -->
        <table class="Profile__table">
          <thead>
          <tr>
            <th>Participant</th>
            <th>Location</th>
            <th>Time</th>
            <th>Actions</th>
          </tr>
          </thead>
          <tbody>
          <tr v-if="pendingParticipations.length === 0">
            <td colspan="4" style="text-align: center; padding: 20px;">
              <img src="@/assets/car_repair.png" alt="No Rides" class="Rides__no-rides-image"
                   style="width: 80px; opacity: 0.7;" />
              <p class="PageLayout__no-rides-text">You don’t have any pending participations.</p>
            </td>
          </tr>

          <tr v-for="participant in pendingParticipations" :key="participant.id">
            <td>{{ participant.participantName }}</td>
            <td>{{ participant.pickupLocation }}</td>
            <td>{{ participant.pickupTime }}</td>
            <td>
              <el-button type="success" @click="acceptParticipation(participant)">✓ Accept
              </el-button>
              <el-button type="danger" @click="rejectParticipation(participant)">✕ Reject
              </el-button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { PropType } from 'vue'
import { Ride } from '../types/KNRidesTypes'
import api from '../utils/axiosConfig'
import { useAuthStore } from '../stores/auth'

const authStore = useAuthStore()

const pickupAddress = ref('')
const pickupTime = ref('')

const pendingParticipations = ref<any[]>([])

defineProps({
  rides: {
    type: Array as PropType<Ride[]>,
    required: true
  }
})

const showModal = ref(false)
const showCancelModal = ref(false)
const showDetailsModal = ref(false)
const showConfirmParticipationModal = ref(false)
const showRidesToApproveModal = ref(false)

const selectedRide = ref<any>({})
const selectedDetailsRide = ref<any>({})
const cancelTargetRide = ref<any>({})


// Função para formatar a data (exemplo: '2025-06-25')
function formatDateToISO(date: string) {
  return new Date(date).toISOString().split('T')[0]
}

const confirmParticipation = async () => {
  try {
    await api.post(`/rides/${selectedRide.value.id}/participation`, {
      pickupLocation: pickupAddress.value,
      pickupTime: pickupTime.value // já em ISO tipo: 2025-06-27T08:30
    })

    selectedRide.value.participating = true
    showConfirmParticipationModal.value = false
    showModal.value = false
    pickupAddress.value = ''
    pickupTime.value = ''
  } catch (error) {
    console.error('Failed to join the ride:', error)
  }
}

const handleParticipation = (ride: any) => {
  if (ride.participating) {
    cancelTargetRide.value = ride
    showCancelModal.value = true
  } else {
    selectedRide.value = ride
    pickupAddress.value = ''
    pickupTime.value = ''
    showModal.value = true
  }
}




const acceptParticipation = async (arg) => {
  await api.post(`/rides/${arg.rideId}/participation/${arg.id}/accept`, {})
  await fetchParticipations(arg.rideId)
}


const rejectParticipation = async (arg) => {
  await api.post(`/rides/${arg.rideId}/participation/${arg.id}/reject`, {})
  await fetchParticipations(arg.rideId)
}

async function fetchParticipations(rideId) {
  try {
    const response = await api.get(`/rides/${rideId}/participations`)
    pendingParticipations.value = response.data.filter(p => p.status === 'PENDING')
  } catch (error) {
    console.error('Error fetching participations:', error)
  }
}


const openConfirmModal = () => {
  if (pickupAddress.value.trim() && pickupTime.value) {
    showConfirmParticipationModal.value = true
  }
}

const confirmCancel = async () => {
  cancelTargetRide.value.participating = false
  showCancelModal.value = false
  try {
    await api.post(`/rides/${cancelTargetRide.value.id}/participation/user/${authStore.user.id}/cancel`, {})
  } catch (error) {
    console.error('An error occurred while cancel participation:', error)
  }

}

const showRideDetails = (ride: any) => {
  selectedDetailsRide.value = ride
  showDetailsModal.value = true
}

const showRideToApprove = async (ride: any) => {
  selectedDetailsRide.value = ride
  showRidesToApproveModal.value = true
  try {
    const response = await api.get(`/rides/${ride.id}/participations`)
    pendingParticipations.value = response.data.filter(p => p.status === 'PENDING')
  } catch (error) {
    console.error('An error occurred while loading participations:', error)
  }
}


const getRowClass = ({ row }: any) => {
  return row.participating ? 'participating-row' : ''
}

</script>

<style scoped>
/* mantém o teu CSS igual */
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap');

.el-table {
  font-family: 'Inter', sans-serif;
  font-size: 13px;
  color: #003369;
  background-color: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  width: 90%;
  margin: 0 auto;
  width: 100%;
  border: ' none';
}

.el-table >>> .el-table__header th.custom-header {
  font-family: 'Inter', sans-serif;
  font-weight: 600;
  font-size: 13px;
  color: #003369;
  padding: 8px 4px;
  border-bottom: none;
  white-space: nowrap;
}

.el-table .el-table__body td {
  padding: 14px 4px;
  font-size: 13px;
  color: #003369;
  text-align: left;
  border-bottom: 1px solid #f1f1f1;
  white-space: nowrap;
  background-color: #fff;
}

.el-table__body tr:hover > td {
  background-color: #f9fafb;
}

.participating-row td {
  background-color: #f3f4f6;
}

.action-buttons {
  display: flex;
  gap: 6px;
  align-items: center;
  justify-content: flex-start;
}

.details-btn {
  background-color: transparent;
  border: 1px solid #ccc;
  color: #333;
  font-weight: 500;
  padding: 4px 10px;
  border-radius: 6px;
}

.participate-btn {
  font-weight: 500;
  color: white;
  border: none;
  padding: 4px 10px;
  border-radius: 6px;
}

.participate-btn.participate {
  background-color: #08c792;
}

.participate-btn.cancel {
  background-color: #ee3030;
}

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
  border-radius: 12px;
}

.el-dialog__body {
  padding: 20px;
}

.el-dialog__header {
  font-family: 'Inter', sans-serif;
  font-size: 16px;
  font-weight: 600;
  color: #1f2d3d;
}

.el-dialog .el-button--primary {
  background-color: #003369;
  border-color: #003369;
  color: #fff;
}

.pickup-input .el-input__wrapper {
  border-color: #003369;
}

.pickup-input .el-input__inner {
  color: #003369;
}

.Profile__table {
  width: 100%;
  margin-top: 2rem;
  border-collapse: separate;
  border-spacing: 0 10px;
  font-size: 14px;

  th {
    text-align: left;
    padding: 12px 16px;
    color: #444;
    font-weight: 600;
    background-color: transparent;
    border-bottom: 1px solid #ddd;
  }

  td {
    padding: 12px 16px;
    background: #fff;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.04);
    border-radius: 8px;
    vertical-align: middle;
  }

  tr {
    transition: transform 0.2s ease;
  }

  tr:hover {
    transform: scale(1.01);
  }

  td:first-child,
  td:last-child {
    border-top-left-radius: 8px;
    border-bottom-left-radius: 8px;
  }

  td:last-child {
    border-top-right-radius: 8px;
    border-bottom-right-radius: 8px;
  }

  .Profile__btn {
    padding: 8px 14px;
    border: none;
    border-radius: 6px;
    font-size: 13px;
    font-weight: 500;
    cursor: pointer;
    transition: background-color 0.2s ease;
    margin-right: 6px;

    &--accept {
      background-color: #e0f7ec;
      color: #1e7f5c;

      &:hover {
        background-color: #c8f0df;
      }
    }

    &--reject {
      background-color: #fdecea;
      color: #c0392b;

      &:hover {
        background-color: #fbd5d1;
      }
    }
  }
}
</style>
