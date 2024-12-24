import React, { useEffect, useRef, useState } from "react";
import { City } from "../../../interfaces/City.interface";
import CityService from "../../../services/city.service";
import styles from "./ProfileAddSalonForm.module.scss";
import { SalonCreateRequest } from "../../../interfaces/forms/SalonCreateRequest.interface";
import L from "leaflet";
import DistanceService from "../../../services/distance.service";
import SalonService from "../../../services/salon.service";
import ValidatorService from "../../../services/validator.service";
import { Salon } from "../../../interfaces/Salon.interface";

interface ProfileAddSalonFormProps {
  updateSalonsAfterCreate: (createdSalon: Salon) => void;
}

const ProfileAddSalonForm = ({
  updateSalonsAfterCreate,
}: ProfileAddSalonFormProps) => {
  const [cities, setCities] = useState<City[]>([]);

  const [formData, setFormData] = useState<SalonCreateRequest>({
    name: "",
    description: "",
    phoneNumber: "",
    latitude: -1,
    longitude: -1,
    city: "Берово",
    location: "",
  });

  const mapRef = useRef<HTMLDivElement>(null);
  const mapInstanceRef = useRef<L.Map | null>(null);
  const markerLayerRef = useRef<L.LayerGroup | null>(null);

  useEffect(() => {
    if (mapRef.current && !mapInstanceRef.current) {
      mapInstanceRef.current = L.map(mapRef.current, {
        center: [41.6086, 21.7453],
        zoom: 9,
      });

      L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        maxZoom: 19,
        attribution: "© OpenStreetMap",
      }).addTo(mapInstanceRef.current);

      markerLayerRef.current = L.layerGroup().addTo(mapInstanceRef.current);

      addMarkerForUserLocation();

      mapInstanceRef.current.on("click", (e: L.LeafletMouseEvent) => {
        const { lat, lng } = e.latlng;
        updateFormCoordinates(lat, lng);
        addMarker(lat, lng);
      });
    }

    setCities(CityService.getAllCities());
  }, []);

  const addMarker = (lat: number, lng: number) => {
    if (markerLayerRef.current) {
      if (
        lat < 40.873926 ||
        lat > 42.376477 ||
        lng < 20.453475 ||
        lng > 23.040348
      )
        return;

      const blueIcon = new L.Icon({
        iconUrl:
          "https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-blue.png",
        shadowUrl:
          "https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png",
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41],
      });

      markerLayerRef.current.clearLayers();

      const marker = L.marker([lat, lng], { icon: blueIcon }).addTo(
        markerLayerRef.current
      );

      marker
        .bindPopup(
          `<p style="text-align: center;">Околина на<br><b>${
            DistanceService.getClosestCityFromCoordinate({
              latitude: lat,
              longitude: lng,
            })?.name || ""
          }</b></p>`
        )
        .openPopup();
    }
  };

  const updateFormCoordinates = (lat: number, lng: number) => {
    setFormData((prevFormData) => ({
      ...prevFormData,
      latitude: lat,
      longitude: lng,
    }));
  };

  const addMarkerForUserLocation = () => {
    if (navigator.geolocation && mapInstanceRef.current) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords;
          const greenIcon = new L.Icon({
            iconUrl:
              "https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png",
            shadowUrl:
              "https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png",
            iconSize: [25, 41],
            iconAnchor: [12, 41],
            popupAnchor: [1, -34],
            shadowSize: [41, 41],
          });

          const userMarker = L.marker([latitude, longitude], {
            icon: greenIcon,
          }).addTo(mapInstanceRef.current!);
          const userRadius = L.circle([latitude, longitude], {
            radius: 500,
            color: "green",
          }).addTo(mapInstanceRef.current!);

          userMarker.bindPopup("Вашата локација").openPopup();
          userRadius.bindPopup("Вашиот радиус");
        },
        (error) => {
          console.error("Грешка при земањето на локацијата:", error);
        },
        { timeout: 10000 }
      );
    }
  };

  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >
  ) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!ValidatorService.isPhoneValid(formData.phoneNumber)) {
      alert(
        "Внесете валиден телефонски број.\nТелефонскиот број треба да почнува со 07 или +3897."
      );
      return;
    }

    if (
      ValidatorService.isCorrdinateInValid({
        latitude: formData.latitude,
        longitude: formData.longitude,
      })
    ) {
      alert("Изберете валидна локација на мапата.");
      return;
    }

    SalonService.createSalon(formData)
      .then((response) => {
        updateSalonsAfterCreate(response.data);
        alert("Успешно креиран салон");
        clearForm();
      })
      .catch((error) => {
        console.error("Error creating salon:", error);
      });
  };

  const clearForm = () => {
    setFormData({
      name: "",
      description: "",
      phoneNumber: "",
      latitude: -1,
      longitude: -1,
      city: "Берово",
      location: "",
    });
  };

  return (
    <form onSubmit={handleSubmit} className={styles.addSalonForm}>
      <h2>Додади салон</h2>
      <label htmlFor="name">Име</label>
      <input
        name="name"
        type="text"
        placeholder="Име на салонот"
        required
        minLength={3}
        value={formData.name}
        onChange={handleChange}
      />
      <label htmlFor="description">Опис</label>
      <textarea
        rows={5}
        cols={250}
        maxLength={300}
        name="description"
        placeholder="Опис на салонот"
        id="description"
        value={formData.description}
        onChange={handleChange}
      />
      <label htmlFor="phoneNumber">Телефонски број</label>
      <input
        name="phoneNumber"
        type="text"
        placeholder="Телефонски број"
        required
        value={formData.phoneNumber}
        onChange={handleChange}
      />
      <input name="latitude" type="hidden" required value={formData.latitude} />
      <input
        name="longitude"
        type="hidden"
        required
        value={formData.longitude}
      />
      <label htmlFor="city">Град</label>
      <select
        id="city"
        name="city"
        value={formData.city}
        onChange={handleChange}
      >
        {cities.map((city, index) => (
          <option key={index} value={city.name}>
            {city.name}
          </option>
        ))}
      </select>
      <label htmlFor="location">Локација</label>
      <input
        name="location"
        type="text"
        placeholder="Локација"
        required
        value={formData.location}
        onChange={handleChange}
      />
      <label className={styles.mapLabel}>Одбери точна локација</label>
      <div id={styles.map} ref={mapRef}></div>
      <button type="submit" className={`primaryButton ${styles.primaryButton}`}>
        Додади
      </button>
    </form>
  );
};

export default ProfileAddSalonForm;
