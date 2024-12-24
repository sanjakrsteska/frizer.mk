import { AppointmentCreateRequest } from '../interfaces/AppointmentCreateRequest.interface';
import { AppointmentDetails } from '../interfaces/AppointmentDetails.interface';
import axios from './config/axios';
const AppointmentService = {
    getAppointmentsByCustomer: (id: number) => {
        return axios.get<AppointmentDetails[]>("/appointments/for-customer/"+id);
    },
    getAppointmentsByEmployee: (id: number) => {
        return axios.get<AppointmentDetails[]>("/appointments/for-employee/"+id);
    },
    deleteAppointment(id: number) {
        try {
         return axios.delete(`/appointments/delete/${id}`);
        } catch (error) {
          throw new Error('Failed to delete appointment');
        }
      },
      async createAppointments(appointmentCreate: AppointmentCreateRequest) {
        try {
          const response = await axios.post('/appointments/add', appointmentCreate);
          return response.data;
        } catch (error) {
          throw new Error('Failed to create appointment');
        }
      },
}

export default AppointmentService;