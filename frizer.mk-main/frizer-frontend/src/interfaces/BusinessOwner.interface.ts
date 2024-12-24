export interface BusinessOwner{
    id: number,
    salonListIds: number[],
    baseUserId: number,
    email: string,
    firstName: string,
    lastName: string,
    phoneNumber: string,
    roles: string[]
}