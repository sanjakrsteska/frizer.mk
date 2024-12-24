import React, { useEffect, useState } from "react";
import styles from "./SalonCard.module.scss";
import { Salon } from "../../../interfaces/Salon.interface";
import { Link } from "react-router-dom";
import { FaStar, FaLocationDot } from "react-icons/fa6";
import DistanceService from "../../../services/distance.service";
import ImageService from "../../../services/image.service";

interface SalonCardProps {
  salon?: Salon;
}

function SalonCard({ salon }: SalonCardProps) {
  const [distance, setDistance] = useState<number>();
  useEffect(() => {
    DistanceService.getDistanceFromUser(salon)
      .then((distance) => {
        setDistance(distance);
      })  
      .catch((error) => {
        console.error("Error getting distance from user:", error);
      });
  }, [salon]);

  const getLogoImage = (): string => {
    if (salon && salon.backgroundImage) {
      return ImageService.getFullSalonImageUrl(salon.id, salon.backgroundImage);
            <img src={getLogoImage()} alt="Logo" />
}
    return "./assets/images/salon_default_image.png";
  };

  return (
    <div className={`${styles.card}`}>
        <img src={getLogoImage()} alt="Salon" className={styles.image} />
        <h3 className={`${styles.title}`}>{salon?.name}</h3>
        <p className={`${styles.rating}`}>
          <FaStar />
          {salon?.rating.toFixed(2)} ({salon?.numberOfReviews} рецензии)
        </p>
        <p className={`${styles.location}`}>
          <FaLocationDot />
          <span>
          {salon?.city}, {salon?.location}
          </span>
        </p>
        {distance && <p>{distance.toFixed(2)} km</p>}
        <Link to={"/salons/" + salon?.id}>
          <button className={`primaryButton ${styles.primaryButton}`}>
            Прегледај
          </button>
        </Link>
    </div>
  );
}

export default SalonCard;
