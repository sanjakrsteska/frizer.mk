import { Image } from '../interfaces/Image.interface';
import { Salon } from '../interfaces/Salon.interface';
import { SalonCreateRequest} from "../interfaces/forms/SalonCreateRequest.interface";
import { SalonEditRequest } from '../interfaces/forms/SalonEditRequest.interface';
import axios from './config/axios';

const SalonService = {
    getSalons: () => {
        return axios.get<Salon[]>("/salons");
    },
    getSalon: (id: number) => {
        return axios.get<Salon>(`/salons/${id}`);
    },
    getSalonsByIds: (ids: number[]) => {
        const params = new URLSearchParams();
        ids.forEach(id => params.append('ids', id.toString()));
        
        return axios.get<Salon[]>('/salons/ids', { params });
    },
    getAllOwnedSalons: () => {
        return axios.get<Salon[]>('/salons/get-all-owned');
    },
    getTopNSalons: (n: number) => {
        return axios.get<Salon[]>(`/salons/top?count=${n}`);
    },
    getFavouriteSalons: () => {
        return axios.get<Salon[]>('/salons/favourites');
    },
    searchSalons: (queryParams: string) => {
        return axios.get<Salon[]>(`/salons?${queryParams}`);
    },
    createSalon: (salon: SalonCreateRequest) => {
        return axios.post<Salon>(`/salons/add`, salon);
    },
    editSalon: (salonId: number, salon: SalonEditRequest) => {
        return axios.put<Salon>(`/salons/edit/${salonId}`, salon);
    },
    deleteSalon: (salonId: number) => {
        return axios.delete<Salon>(`/salons/delete/${salonId}`);
    },
    getImage: async (salonId: number, imageId: number): Promise<Image> => {
        const response = await axios.get(`/salons/${salonId}/image/${imageId}`, {
            headers: {
                'Content-Type': 'application/json',
            },
        });
        return response.data; 
    },
    getSalonImageUrl(salonId: number, imageId: number): string {
        const baseUrl = 'http://localhost:8080/api/salons';
        return `${baseUrl}/${salonId}/image/${imageId}`;
    },
    addImageToSalon: async (salonId: number, image: File, imageNo: number) => {
        const formData = new FormData();
        formData.append('image', image);
        formData.append('imageNo', imageNo.toString());

        return await axios.post<Salon>(`/salons/${salonId}/upload-image`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });
    },
    addBackgroundImageToSalon: async (salonId: number, image: File) => {
        const formData = new FormData();
        formData.append('image', image);
        return await axios.post<Salon>(`/salons/${salonId}/upload-background-image`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });
    },
    addSalonToFavourites: (salonId: number) => {
        return axios.post<Salon>(`/salons/add-favourites/${salonId}`);
    },
    removeSalonFromFavourites: (salonId: number) => {
        return axios.delete<Salon>(`/salons/delete-favourites/${salonId}`);
    }
};
    
export default SalonService;

