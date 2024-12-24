export interface AppointmentDetails {
    id: number,
    dateFrom: Date
    dateTo: Date
    treatmentName: string,
    salonName: string,
    employeeName: string,
    employeeBaseUserId: number,
    customerBaseUserId: number,
    customerName: string,
    attended: boolean
}