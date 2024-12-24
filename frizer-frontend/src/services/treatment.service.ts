import { Treatment } from '../interfaces/Treatment.interface';
import { TreatmentCreateRequest } from '../interfaces/TreatmentCreateRequest.interface';
import axios from './config/axios';

const TreatmentService = {
    getTreatments: () => {
        return axios.get<Treatment[]>("/treatments");
    },
    getTreatment: (id: number) => {
        return axios.get<Treatment>(`/treatments/${id}`);
    },
    getTreatmentsByIds: (salonTreatmentIds: number[]) => {
        const params = salonTreatmentIds.join(',');
        return axios.get(`/treatments/ids?ids=${params}`);
      },
    
    async createTreatment(treatment: TreatmentCreateRequest) {
    try {
        const response = await axios.post('/treatments/add', treatment);
        return response.data;
    } catch (error) {
    }
    },
    async deleteTreatment(id: number) {
        try {
          const response = await axios.delete(`/treatments/delete/${id}`);
          return response.data;
        } catch (error) {
          throw new Error('Failed to delete treatment');
        }
      }
  
};

export default TreatmentService;


