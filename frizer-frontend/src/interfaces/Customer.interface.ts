export interface Customer{
    id: number,
    appointmentsActiveIds: number[],
    appointmentsHistoryIds: number[],
    baseUserId: number,
    email: string,
    firstName: string,
    lastName: string,
    phoneNumber: string,
    roles: string[],
    favouriteSalonsIds: number[]
}