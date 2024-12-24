import { CYRILLIC_CITIES } from "../data/cyrilic-cities";
import { City } from "../interfaces/City.interface";
import { Coordinate } from "../interfaces/Coordinate.interface";
import { Salon } from "../interfaces/Salon.interface";

const DistanceService = {
    deg2rad(deg: number): number {
        return deg * (Math.PI / 180);
    },

    getDistance(coordinate1: Coordinate, coordinate2: Coordinate): number {
        const R = 6371;
        const dLat = this.deg2rad(coordinate2.latitude - coordinate1.latitude);
        const dLon = this.deg2rad(coordinate2.longitude - coordinate1.longitude);
        const a =
          Math.sin(dLat / 2) * Math.sin(dLat / 2) +
          Math.cos(this.deg2rad(coordinate1.latitude)) *
            Math.cos(this.deg2rad(coordinate2.latitude)) *
            Math.sin(dLon / 2) *
            Math.sin(dLon / 2);
        const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        const distance = R * c;
        return distance;
      },

      getClosestCityFromCoordinate(coordinate: Coordinate): City | null {
        const cities: City[] = CYRILLIC_CITIES
        let closestCity: City = cities[0];
        let minDistance: number = this.getDistance(coordinate, closestCity.coordinate!!);
    
        for (let i = 1; i < cities.length; i++) {
          const currentCity = cities[i];
          const distance = this.getDistance(coordinate, currentCity.coordinate!!);
    
          if (distance < minDistance) {
            minDistance = distance;
            closestCity = currentCity;
          }
        }
        return closestCity;
    },

    getDistanceFromUser: async function (salon?: Salon): Promise<number> {
        return new Promise((resolve, reject) => {
          if (salon && navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
              (position) => {
                const { latitude, longitude } = position.coords;
                const distance = this.getDistance(
                  { latitude: salon.latitude, longitude: salon.longitude },
                  { latitude, longitude }
                );
                resolve(distance);
              },
              (error) => {
                console.error('Error obtaining location', error);
                reject(error);
              },
              { enableHighAccuracy: true, timeout: 5000, maximumAge: 0 }
            );
          } else {
            console.error('Geolocation is not supported by this browser.');
            resolve(0);
          }
        });
    }
}

export default DistanceService;