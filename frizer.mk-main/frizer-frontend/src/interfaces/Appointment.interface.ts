export interface Appointment{
    id: number,
    dateFrom: Date,
    dateTo: Date,
    treatmentId: number,
    salonId: number,
    employeeId: number,
    customerId: number,
    attended: boolean
};