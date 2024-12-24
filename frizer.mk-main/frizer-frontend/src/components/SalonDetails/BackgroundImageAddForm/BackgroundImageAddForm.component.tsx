import React, { useState } from "react";
import { Salon } from "../../../interfaces/Salon.interface";
import { User } from "../../../context/Context";
import SalonService from "../../../services/salon.service";
import styles from "./BackgroundImagAddForm.module.scss";
import { RiCloseCircleLine } from "react-icons/ri";
import { FaCloudUploadAlt } from "react-icons/fa";

interface BackgroundImageAddFormProps {
  salon?: Salon;
  user?: User;
  onImageAdd: (salon: Salon) => void;
  onCloseButtonClick: () => void;
}

function BackgroundImageAddForm({
  salon,
  user,
  onImageAdd,
  onCloseButtonClick,
}: BackgroundImageAddFormProps) {
  const [image, setImage] = useState<File | null>(null);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (image) {
      const formData = new FormData();
      formData.append("image", image);

      try {
        const response = await SalonService.addBackgroundImageToSalon(
          salon?.id ?? -1,
          image
        );
        setImage(null);
        onImageAdd(response.data);
      } catch (error) {}
    }
  };

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      setImage(e.target.files[0]);
      const formEvent = new Event("submit", { bubbles: true });
      const form = e.currentTarget.closest("form");
      form?.dispatchEvent(formEvent);
    }
  };

  const handleClose = () => {
    setImage(null);
    onCloseButtonClick();
  };

  return (
    <>
      {user?.id === salon?.ownerId && (
        <div className={styles.backgroundImageFormContainer}>
          <form
            onSubmit={handleSubmit}
            encType="multipart/form-data"
            className={styles.backgroundImageForm}
          >
            <button
              className={styles.closeButton}
              title="Затвори ја формата."
              onClick={handleClose}
              type="button"
            >
              <RiCloseCircleLine />
            </button>
            <h2>Прикачи слика</h2>
            <label htmlFor="file-upload" className={styles.customFileUpload}>
              <FaCloudUploadAlt /> Избери слика
            </label>
            <input
              id="file-upload"
              type="file"
              onChange={handleImageChange}
              required
            />
            <span id={styles.fileName}>
              {image ? image.name : "Нема избрано слика"}
            </span>
            <button
              type="submit"
              className={`primaryButton ${styles.primaryButton}`}
            >
              Прикачи слика
            </button>
          </form>
        </div>
      )}
    </>
  );
}

export default BackgroundImageAddForm;
