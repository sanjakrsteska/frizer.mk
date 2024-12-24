import React, { useEffect, useState } from 'react'
import styles from './FeaturedSalons.module.scss';
import SalonService from '../../../services/salon.service';
import { Salon } from '../../../interfaces/Salon.interface';
import SalonCard from '../../fragments/SalonCard/SalonCard.component';
import { SALONS_MOCK } from '../../../mock/salons.mock';

function FeaturedSalons() {
    const [salons, setSalons] = useState<Salon[]>([]);

    useEffect(() => {
      const getSalons = async () => {
        try {
          const response = await SalonService.getTopNSalons(8);
          setSalons(response.data);
        } catch (error) {
          console.error("Failed to fetch salons:", error);
        }
      };
  
      getSalons();
    // setSalons(SALONS_MOCK);
    }, []);

  return (
    <div className={`${styles.featuredSalons}`}>
        <h1 className={`${styles.title}`}>Best rated salons</h1>
        {salons.map(salon => <SalonCard key={salon.id} salon={salon}/>)}
    </div>
)
}

export default FeaturedSalons;