import React, { useEffect, useState } from "react";
import { CYRILLIC_CITIES } from "../../../data/cyrilic-cities";
import { City } from "../../../interfaces/City.interface";
import styles from "./Search.module.scss";
import { FaArrowUp, FaArrowDown } from "react-icons/fa";
import { useNavigate } from "react-router-dom";

function Search() {
  const [menuOpen, setMenuOpen] = useState(false);
  const [cities, setCities] = useState<City[]>([]);
  const [formValues, setFormValues] = useState({
    name: "",
    city: "",
    distance: "",
    rating: 0,
    latitude: "",
    longitude: "",
  });
  const navigate = useNavigate();

  const handleChange = (e: any) => {
    setFormValues({
      ...formValues,
      [e.target.name]: e.target.value,
    });
  };

  const toggleMenu = () => {
    setMenuOpen(!menuOpen);
  };

  const handleSubmit = (e: any) => {
    if(menuOpen) toggleMenu();
    e.preventDefault();
    const queryParams = new URLSearchParams();

    Object.keys(formValues).forEach((key) => {
      const value = formValues[key as keyof typeof formValues];
        queryParams.append(key, value.toString());
    });

    navigate('/salons?' + queryParams.toString());

    setFormValues({
      name: "",
      city: "",
      distance: "",
      rating: 0,
      latitude: "",
      longitude: "",
    });
  };

  const requestUserLocation = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords;

          setFormValues({
            ...formValues,
            latitude: latitude + "",
            longitude: longitude + "",
          });
        },

        (error) => {
          console.error("Error obtaining location", error);
        },
        { enableHighAccuracy: true, timeout: 5000, maximumAge: 0 }
      );
    } else {
      console.error("Geolocation is not supported by this browser.");
    }
  };

  useEffect(() => {
    setCities(CYRILLIC_CITIES);
  }, []);

  return (
    <form onSubmit={handleSubmit} className={styles.searchForm}>
      <input
        className={`${styles.searchByNameInput}`}
        name="name"
        type="text"
        placeholder="Име на фитнес центарот..."
        value={formValues.name}
        onChange={handleChange}
      />
      <button type="submit" className="primaryButton">
        Пребарај
      </button>
      <a className={`${styles.advancedToggle}`} onClick={toggleMenu}>
        Напредно {menuOpen ? <FaArrowUp /> : <FaArrowDown />}
      </a>
      {menuOpen && (
        <div className={`${styles.advancedMenu}`}>
          <label htmlFor="city">Град</label>
          <select
            id="city"
            name="city"
            value={formValues.city}
            onChange={handleChange}
          >
            {cities.map((city, index) => (
              <option key={index} value={city.name}>
                {city.name}
              </option>
            ))}
          </select>
          <label htmlFor="distance">Далечина</label>
          <div className={`${styles.distanceInputContainer}`}>
            <input
              type="number"
              name="distance"
              id="distance"
              min="1"
              value={formValues.distance}
              onClick={requestUserLocation}
              onChange={handleChange}
            />
          </div>
          <label htmlFor="rating">Рејтинг</label>
          <input
            type="number"
            name="rating"
            id="rating"
            min="0"
            max="5"
            value={formValues.rating}
            onChange={handleChange}
          />
        </div>
      )}
    </form>
  );
}

export default Search;
