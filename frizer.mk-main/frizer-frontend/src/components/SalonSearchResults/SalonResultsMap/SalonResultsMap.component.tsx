import React, { useEffect, useRef } from "react";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import { Salon } from "../../../interfaces/Salon.interface";
import { useNavigate } from "react-router-dom";

interface SalonResultsMapProps {
  salons: Salon[];
}

const SalonResultsMap: React.FC<SalonResultsMapProps> = ({ salons }) => {
  const mapRef = useRef<HTMLDivElement>(null);
  const mapInstanceRef = useRef<L.Map | null>(null);
  const navigate = useNavigate();

  const navigateToDetails = (id: number) => {
    navigate(`/salons/${id}`);
  };

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

      addMarkerForUserLocation();
    } else {
      updateMap();
    }
  }, [salons]);

  const updateMap = () => {
    if (mapInstanceRef.current) {
      mapInstanceRef.current.eachLayer((layer) => {
        if (layer instanceof L.Marker) {
          mapInstanceRef.current?.removeLayer(layer);
        }
      });

      addMarkerForUserLocation();
      addMarkersForSalons();
    }
  };

  const createPopup = (id: number, name: string): L.Popup => {
    const popupContent = `
      <div style="text-align: center;">
        <b>${name}</b></br>
        <button 
          style="margin-top: 1em; padding: 0.5em 1em;" 
          class="secondaryButton" 
          onclick="window.navigateToDetails(${id})">Прегледај</button>
      </div>
    `;
    return L.popup().setContent(popupContent);
  };

  const addMarkersForSalons = () => {
    salons.forEach((salon) => {
      addMarkersForSalon(salon);
      // const marker = L.marker([salon.latitude, salon.longitude]).addTo(map);
      // marker.bindPopup(createPopup(salon.id, salon.name));
    });
  };

  const addMarkersForSalon = (salon: Salon) => {
    if (mapInstanceRef.current && salon) {
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

      const marker = L.marker([salon.latitude, salon.longitude], {
        icon: blueIcon,
      }).addTo(mapInstanceRef.current);

      marker.bindPopup(createPopup(salon.id, salon.name));
    }
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

          userMarker.bindPopup("Your location").openPopup();
          userRadius.bindPopup("Your radius");
        },
        (error) => {
          console.error("Error retrieving user location:", error);
        },
        { timeout: 10000 }
      );
    }
  };

  useEffect(() => {
    (window as any).navigateToDetails = navigateToDetails;
  }, []);

  return (
    <div
      id="map"
      ref={mapRef}
      style={{
        marginTop: "3em",
        width: "100%",
        height: "70vh",
        borderRadius: "var(--border-radius)",
        zIndex: 0,
      }}
    ></div>
  );
};

export default SalonResultsMap;
