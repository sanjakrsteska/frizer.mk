import styles from "./SalonResultsList.module.scss";
import SalonCard from "../../fragments/SalonCard/SalonCard.component";
import { Salon } from "../../../interfaces/Salon.interface";

interface SalonResultsListProps {
  salons: Salon[]
};

function SalonResultsList({salons}: SalonResultsListProps) {
  return (
    <div className={styles.cards}>
      {salons.map((salon, i) => {
        return (
          <SalonCard key={i} salon={salon}/>
        );
      })}
    </div>
  );
}

export default SalonResultsList;
