import axios from './config/axios';
import { TimeSlot } from "../interfaces/TimeSlot.interface";

const TimeSlotService = {
     getAvailableTimeSlots: (salonId: number, employeeId: number, durationMultiplier: number) => {
        try {
          return axios.get<TimeSlot[][]>(`/time-slots`, {
            params: {
              salonId: salonId,
              employeeId: employeeId,
              durationMultiplier: durationMultiplier,
            },
          });
        } catch (error) {
          console.error('Error fetching available time slots:', error);
          throw error;
        }
      }
    
}
export default TimeSlotService;