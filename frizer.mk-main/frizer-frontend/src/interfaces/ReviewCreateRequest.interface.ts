export interface ReviewCreateRequest {
    customerId: number,
    comment: string
    rating: number
    employeeId: number
}