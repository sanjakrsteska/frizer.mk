import React from "react";
import styles from "./SalonResultsParameters.module.scss";
import { useQueryParams } from "../../../hooks/UseQueryParams";

function SalonResultsParameters() {
  const queryParams = useQueryParams();

  return (
    <div className={styles.searchFilters}>
      <div className={styles.searchFilter}>
        <div className={styles.title}>Салони со името</div>
        {queryParams.name ? <p>{queryParams.name}</p> : <p>Било кое</p>}
      </div>
      {queryParams.rating && +queryParams.rating > 0 && (
        <div className={styles.searchFilter}>
          <div className={styles.title}>Салони со рејтинг над</div>
          <p>{queryParams.rating}</p>
        </div>
      )}
      {queryParams.distance && (
        <div className={styles.searchFilter}>
          <div className={styles.title}>Салони на оддалеченост до</div>
          <p>{queryParams.distance} km</p>
        </div>
      )}
      {queryParams.city && (
        <div className={styles.searchFilter}>
          <div className={styles.title}>Салони во</div>
          <p>{queryParams.city}</p>
        </div>
      )}
    </div>
  );
}

export default SalonResultsParameters;
