import axios from "./config/axios";
import { City } from "../interfaces/City.interface";
import { CYRILLIC_CITIES } from "../data/cyrilic-cities";

const CityService = {
    getAllCities: (): City[] => {
        return CYRILLIC_CITIES;
    },
    getTopNCitiesBySalonsCount: (count: number) => {
        return axios.get<City[]>(`/cities/top?count=${count}`);
    }
}

export default CityService;