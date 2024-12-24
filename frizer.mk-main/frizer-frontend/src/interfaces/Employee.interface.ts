export interface Employee{
    id: number,
    appointmentsActiveIds: number[],
    appointmentsHistoryIds: number[],
    salonId: number,
    baseUserId: number,
    email: string,
    firstName: string,
    lastName: string,
    phoneNumber: string,
    roles: string[],
    rating: number,
    numberOfReviews : number,
    employmentDate: Date
}