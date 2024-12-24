import { ImagePosition } from "../enums/ImagePosition.enum";

export interface Salon{
    id: number,
    name: string,
    description: string,
    location: string,
    city: string,
    phoneNumber: string,
    employeesIds: number[],
    salonTreatmentsIds: number[],
    tagsIds: number[],
    ownerId: number,
    backgroundImage: number | null,
    images: Map<number, ImagePosition>,
    rating: number,
    numberOfReviews: number,
    latitude: number,
    longitude: number
}