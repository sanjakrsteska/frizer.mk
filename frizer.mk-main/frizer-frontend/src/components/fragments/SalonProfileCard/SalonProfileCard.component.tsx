import React, { useEffect, useState } from "react";
import { RiEditLine, RiDeleteBinLine } from "react-icons/ri";
import { Salon } from "../../../interfaces/Salon.interface";
import ImageService from "../../../services/image.service";
import UserService from "../../../services/user.service";
import styles from "./SalonProfileCard.module.scss";
import { Link } from "react-router-dom";
import SalonService from "../../../services/salon.service";

interface SalonProfileCardProps {
  salon: Salon;
  setCurrentSalonEdit?: (salon: Salon) => void;
  updateSalonsAfterDelete?: (salonId: number) => void;
}

function SalonProfileCard({
  salon,
  setCurrentSalonEdit,
  updateSalonsAfterDelete,
}: SalonProfileCardProps) {
  const [canEdit, setCanEdit] = useState<Boolean>(false);

  useEffect(() => {
    const checkIfEligibleToEdit = async () => {
      try {
        const user = await UserService.getCurrentUser();
        setCanEdit(salon.ownerId === user?.id);
      } catch (error) {
        console.error("Failed to fetch current user:", error);
      }
    };

    checkIfEligibleToEdit();
    // eslint-disable-next-line
  }, []);

  const handleEdit = (salon: Salon) => {setCurrentSalonEdit && setCurrentSalonEdit(salon)};

  const handleDelete = (salonId: number) => {
    const shouldDelete = window.confirm(
      `Дали сте сигурни дека сакате да го избришете салонот со ID ${salonId}`
    );
    {
      shouldDelete && updateSalonsAfterDelete && 
        SalonService.deleteSalon(salonId)
          .then((response) => {
            updateSalonsAfterDelete(response.data.id);
            alert("Упсешно избришан салон");
          })
          .catch((error) =>
            console.error("Грешка при бришењето на салонот: ", error)
          );
    }
  };

  const getLogoImage = (): string => {
    if (salon && salon.backgroundImage) {
      return ImageService.getFullSalonImageUrl(salon.id, salon.backgroundImage);
    }
    return "./assets/images/salon_default_image.png";
  };

  return (
    <div className={styles.salonCard}>
      <div className={styles.leftSide}>
        <img src={getLogoImage()} alt="Logo" />
        <div className={styles.description}>
          <h2>{salon.name}</h2>
          <p>{salon.description}</p>
        </div>
      </div>
      <div className={styles.actions}>
        {canEdit && (
          <>
            <button onClick={() => handleEdit(salon)} title="Промени салон">
              <RiEditLine size={20} />
            </button>
            <button
              onClick={() => handleDelete(salon.id)}
              title="Избриши салон"
            >
              <RiDeleteBinLine size={20} />
            </button>
          </>
        )}
        <button className={`primaryButton ${styles.primaryButton}`}>
          <Link to={`/salons/${salon?.id}`}>Прегледај</Link>
        </button>
      </div>
    </div>
  );
}

export default SalonProfileCard;
