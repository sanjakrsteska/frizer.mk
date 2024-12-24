export interface ReviewDetails {
    id: number;
    authorFirstName: string;
    authorLastName: string;
    employeeFullName: string;
    authorId: number;
    employeeId: number;
    rating: number;
    comment: string;
    date: Date;
}
