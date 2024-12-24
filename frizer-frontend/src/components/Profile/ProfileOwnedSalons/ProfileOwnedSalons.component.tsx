import { Salon } from "../../../interfaces/Salon.interface";
import SalonProfileCard from "../../fragments/SalonProfileCard/SalonProfileCard.component";
import styles from "./ProfileOwnedSalons.module.scss";

interface ProfileOwnedSalonsProps {
  salons: Salon[];
  setCurrentSalonEdit: (salon: Salon | null) => void;
  updateSalonsAfterDelete: (salonId: number) => void;
}

function ProfileOwnedSalons({
  salons,
  setCurrentSalonEdit,
  updateSalonsAfterDelete
}: ProfileOwnedSalonsProps) {

  return (
    <>
      <h2>Сопствени салони</h2>
      <div className={styles.cardsContainer}>
        {salons.map((salon, i) => (
            <SalonProfileCard key={i} salon={salon} setCurrentSalonEdit={setCurrentSalonEdit} updateSalonsAfterDelete={updateSalonsAfterDelete}/>
        ))}
      </div>
    </>
  );
}

export default ProfileOwnedSalons;
