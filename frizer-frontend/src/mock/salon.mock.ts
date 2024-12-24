import { Salon } from "../interfaces/Salon.interface";

export const SALON_MOCK: Salon = {
    id: 3,
    name: "Kaj Shekspiro",
    description: "Frizerski salon za mazhi",
    location: "prilep",
    city: "Прилеп",
    phoneNumber: "broj2",
    employeesIds: [
        1,
        2
    ],
    salonTreatmentsIds: [
        2
    ],
    tagsIds: [
        2
    ],
    ownerId: 2,
    backgroundImage: 1,
    images: new Map(),
    rating: 0.0,
    numberOfReviews: 0,
    latitude: 41.436047,
    longitude: 22.00487
};
