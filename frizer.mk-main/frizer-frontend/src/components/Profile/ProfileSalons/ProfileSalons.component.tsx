import React, { useEffect, useState } from "react";
import ProfileOwnedSalons from "../ProfileOwnedSalons/ProfileOwnedSalons.component";
import ProfileEditSalonForm from "../ProfileEditSalonForm/ProfileEditSalonForm.component";
import ProfileAddSalonForm from "../ProfileAddSalonForm/ProfileAddSalonForm.component";
import { Salon } from "../../../interfaces/Salon.interface";
import SalonService from "../../../services/salon.service";

function ProfileSalons() {
  const [salons, setSalons] = useState<Salon[]>([]);
  const [currentSalonEdit, setCurrentSalonEdit] = useState<Salon | null>(null);

  useEffect(() => {
    const getSalons = async () => {
      try {
        const response = await SalonService.getAllOwnedSalons();
        setSalons(response.data);
      } catch (error) {
        console.error("Грешка при земањето на салоните:", error);
      }
    };

    getSalons();
  }, []);

  const updateSalonAfterEdit = (editedSalon: Salon) => {
    setSalons(prevSalons => 
      prevSalons.map(salon => salon.id === editedSalon.id ? editedSalon : salon)
    );
  };

  const updateSalonsAfterCreate = (createdSalon: Salon) => {
    setSalons(prevSalons => [...prevSalons, createdSalon]);
  }

  const updateSalonsAfterDelete = (salonId: number) => {
    setSalons(prevSalons => prevSalons.filter(salon => salon.id !== salonId));
  }

  return (
    <>
      <ProfileOwnedSalons salons={salons} setCurrentSalonEdit={setCurrentSalonEdit} updateSalonsAfterDelete={updateSalonsAfterDelete} />
      <ProfileEditSalonForm currentSalonEdit={currentSalonEdit} setCurrentSalonEdit={setCurrentSalonEdit} updateSalonAfterEdit={updateSalonAfterEdit} />
      <ProfileAddSalonForm updateSalonsAfterCreate={updateSalonsAfterCreate} />
    </>
  );
}

export default ProfileSalons;
