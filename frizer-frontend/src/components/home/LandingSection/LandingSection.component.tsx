import React, { useEffect, useState } from 'react'
import Search from '../../fragments/Search/Search.component';
import { Link } from 'react-router-dom';
import styles from './LandingSection.module.scss';
import CityService from '../../../services/city.service';
import { City } from '../../../interfaces/City.interface';

function LandingSection() {
    const [cities, setCities] = useState<City[]>([]);

    useEffect(() => {
        const getTop6Cities = async () => {
            try {
              const response = await CityService.getTopNCitiesBySalonsCount(6);
              setCities(response.data);
            } catch (error) {
              console.error("Failed to fetch salons:", error);
            }
          };
      
          getTop6Cities();
        //   setcities(SALONS_PER_CITY_MOCK);
    }, [])

    function getCitySearchUrl(city: string){
        const queryParams = new URLSearchParams({
            name: '',    
            city: city,
            distance: '0',
            rating: '0',
            latitude: '',
            longitude: ''
          }).toString();

          return `/salons?${queryParams}`;
    }

  return (
    <div className={`${styles.landingPage}`}>
        <img src="./assets/images/background-image.png" alt="Background" />
        <div className={`${styles.blackOverlay}`}></div>
        <h1 className={`${styles.title}`}>Само еден клик оддалечени од <br/>
        вашиот омилен салон за убавина</h1>
        <h4 className={`${styles.subtitle}`}>Резервирај брзо, лесно и едноставно со Frizer.mk</h4>

        <Search/>

        <div className={`${styles.tags}`}>
        <h4>Фитнес центри по градови</h4>
        {cities.map((city, i) => {
            return (
            <Link to={getCitySearchUrl(city.name)} className={`${styles.tag}`} key={i}>
                <div>
                    <h5>{city.name}</h5>
                    <p>{city.salonsIdsInCity?.length} {city.salonsIdsInCity && city.salonsIdsInCity?.length > 0 ? 'салони': 'салон' }</p>
                </div>
            </Link>
            );
        })}
            
        </div>
  </div>  
  )
}

export default LandingSection;