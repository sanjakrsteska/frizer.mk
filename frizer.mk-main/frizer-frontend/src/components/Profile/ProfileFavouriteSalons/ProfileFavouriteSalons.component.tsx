import { useEffect, useState } from "react";
import SalonProfileCard from "../../fragments/SalonProfileCard/SalonProfileCard.component";
import styles from "./ProfileFavouriteSalons.module.scss";
import { Customer } from "../../../interfaces/Customer.interface";
import { User } from "../../../context/Context";
import CustomerService from "../../../services/customer.service";
import SalonService from "../../../services/salon.service";
import { Salon } from "../../../interfaces/Salon.interface";
interface ProfileFavouriteSalonsProps {
  currentUser?: User | null;
}
function ProfileFavouriteSalons({ currentUser }: ProfileFavouriteSalonsProps) {
  const [customer, setCustomer] = useState<Customer | null>();
  const [salons, setSalons] = useState<Salon[]>([]);

  useEffect(() => {
    const getCustomer = async () => {
      try {
        if (currentUser) {
          const response = await CustomerService.getCustomerByEmail(
            currentUser?.email
          );
          setCustomer(response?.data);
        }
      } catch (error) {
        console.error("Failed to fetch customer:", error);
      }
    };

    getCustomer();
  }, [currentUser]);

  useEffect(() => {
    const getFavouriteSalons = async () => {
      try {
        if (customer) {
          const response = await SalonService.getFavouriteSalons();
          setSalons(response.data);
        }
      } catch (error) {
        console.error("Failed to fetch salons:", error);
      }
    };

    if (customer) {
      getFavouriteSalons();
    }
  }, [customer]);

  return (
    <>
      <h2>Омилени салони</h2>
      <div className={styles.cardsContainer}>
        {salons.map((salon, i) => (
          <SalonProfileCard key={i} salon={salon} />
        ))}
      </div>
    </>
  );
}

export default ProfileFavouriteSalons;
