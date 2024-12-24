import React, { useEffect, useState } from "react";
import styles from "./SalonResults.module.scss";
import SalonResultsMap from "../SalonResultsMap/SalonResultsMap.component";
import SalonResultsList from "../SalonResultsList/SalonResultsList.component";
import { Salon } from "../../../interfaces/Salon.interface";
import SalonService from "../../../services/salon.service";
import { useSearchParams } from "react-router-dom";
import { buildQueryString } from "../../../utils/queryUtils";
import { QueryParams } from "../../../interfaces/QueryParams.interface";

function SalonResults() {
  const [searchParams] = useSearchParams();
  const [areListedSalons, setAreListedSalons] = useState<boolean>(true);
  const [salons, setSalons] = useState<Salon[]>([]);

  useEffect(() => {
    const params: QueryParams = {
      name: searchParams.get("name") || "",
      city: searchParams.get("city") || "",
      distance: searchParams.get("distance") || "",
      rating: searchParams.get("rating") || "",
      latitude: searchParams.get("latitude") || "",
      longitude: searchParams.get("longitude") || "",
    };

    const queryString = buildQueryString(params);

    const fetchSalons = async () => {
      try {
        const response = await SalonService.searchSalons(queryString);
        setSalons(response.data);
      } catch (error) {
        console.error("Failed to fetch salons:", error);
      }
    };

    fetchSalons();
  }, [searchParams]);

  const toggleList = () => {
    setAreListedSalons(!areListedSalons);
  };

  return (
    <>
      <div>
        <p>
          Пребарувањето врати {salons.length}{" "}
          {salons.length !== 1 ? "резултати" : "резултат"}
        </p>
        <div className={styles.buttons}>
          <button
            className={`${styles.listButton} ${
              areListedSalons ? styles.activeButton : ""
            }`}
            onClick={toggleList}
          >
            Листа од салони
          </button>
          <button
            className={`${styles.mapButton} ${
              !areListedSalons ? styles.activeButton : ""
            }`}
            onClick={toggleList}
          >
            Мапа
          </button>
        </div>
      </div>
      {areListedSalons ? (
        <SalonResultsList salons={salons} />
      ) : (
        <SalonResultsMap salons={salons} />
      )}
    </>
  );
}

export default SalonResults;
