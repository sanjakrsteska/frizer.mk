import React, { useContext, useEffect, useState } from "react";
import styles from "./ProfileEditDetailsForm.module.scss";
import { ACTION_TYPE, GlobalContext, User } from "../../../context/Context";
import { UserEditRequest } from "../../../interfaces/UserEditRequest";
import UserService from "../../../services/user.service";

interface ErrorsState {
  firstName?: string;
  lastName?: string;
  phoneNumber?: string;
}

interface ProfileEditDetailsFormProps {
  currentUser?: User | null;
}

function ProfileEditDetailsForm({ currentUser }: ProfileEditDetailsFormProps) {
  const { state, dispatch } = useContext(GlobalContext);
  const [formData, setFormData] = useState<UserEditRequest>({
    firstName: "",
    lastName: "",
    phoneNumber: "",
  });

  const [errors, setErrors] = useState<ErrorsState>({});

  const validate = (): boolean => {
    let errors: ErrorsState = {};
    if (!formData.firstName) errors.firstName = "Името е задолжително.";
    if (!formData.lastName) errors.lastName = "Презимето е задолжително.";
    if (!formData.phoneNumber) {
      errors.phoneNumber = "Телефонскиот број е задолжителен.";
    } else if (
      !/^((\+3897\d{7})|(07\d{7}))$/.test(formData.phoneNumber)
    ) {
      errors.phoneNumber =
        "Телефонскиот број мора да започнува со +3897 или 07 и да има точно 9 или 12 знаци.";
    }

    setErrors(errors);
    return Object.keys(errors).length === 0;
  };

  useEffect(() => {
    if (currentUser) {
      setFormData({
        firstName: currentUser.firstName,
        lastName: currentUser.lastName,
        phoneNumber: currentUser.phoneNumber,
      });
    }
  }, [currentUser]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (validate() && currentUser) {
      try {
        const updatedUser = await UserService.updateUserDetails(
          currentUser.id,
          formData
        );
        dispatch({ type: ACTION_TYPE.SET_USER, payload: updatedUser });
        alert("Успешно променети податоци");
      } catch (error) {
        console.error("Грешка при промената на податоците: ", error);
      }
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  return (
    <div>
      <div className={styles.profileImage}>
        <img src="./assets/images/male-avatar.png" alt="Avatar image" />
      </div>
      <form onSubmit={handleSubmit} className={styles.editProfileForm}>
        <h2>Промени информации</h2>
        <label htmlFor="firstName">Име</label>
        <input
          type="text"
          name="firstName"
          id="firstName"
          value={formData.firstName}
          onChange={handleChange}
          required
        />
        {errors.firstName && (
          <small className={styles.error}>{errors.firstName}</small>
        )}

        <label htmlFor="lastName">Презиме</label>
        <input
          type="text"
          name="lastName"
          id="lastName"
          value={formData.lastName}
          onChange={handleChange}
          required
        />
        {errors.lastName && (
          <small className={styles.error}>{errors.lastName}</small>
        )}

        <label htmlFor="phoneNumber">Телефонски број</label>
        <input
          type="text"
          name="phoneNumber"
          id="phoneNumber"
          value={formData.phoneNumber}
          onChange={handleChange}
          required
          maxLength={12}
        />
        {errors.phoneNumber && (
          <small className={styles.error}>{errors.phoneNumber}</small>
        )}

        <label htmlFor="email">Емаил</label>
        <input
          type="text"
          name="email"
          id="email"
          value={currentUser?.email || ""}
          readOnly
        />
        <button
          type="submit"
          className={`primaryButton ${styles.primaryButton}`}
        >
          Промени
        </button>
      </form>
    </div>
  );
}

export default ProfileEditDetailsForm;
