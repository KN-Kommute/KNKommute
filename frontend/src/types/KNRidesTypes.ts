export interface RideData {
    origin: string
    destination: string
    time: string // ISO string, ex: "2025-06-12T14:30:00.000Z"
    totalValue: number | null
    maxUsers: number
  }
  
  export interface Ride {
    id: number
    from: string
    to: string
    date: string
    time: string
    value: string
    participating: boolean
    owner: string
    ownerPhone: string
  }
  