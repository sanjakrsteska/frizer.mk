import React, { useEffect, useState } from "react";
import styles from "./TreatmentList.module.scss";
import { Salon } from "../../../interfaces/Salon.interface";
import { Treatment } from "../../../interfaces/Treatment.interface";
import TreatmentService from "../../../services/treatment.service";
import TreatmentItem from "../TreatmentItem/TreatmentItem.component";
import TreatmentDetailsItem from "../TreatmentDetailsItem/TreatmentDetailsItem.component";
import ReserveAppointmentForm from "../ReserveAppointmentForm/ReserveAppointmentForm.component";
import AppointmentAddForm from "../AppointmentAddForm/AppointmentAddForm.module";
import { Employee } from "../../../interfaces/Employee.interface";
import EmployeeService from "../../../services/employee.service";
import { TimeSlot } from "../../../interfaces/TimeSlot.interface";
import TimeSlotService from "../../../services/timeSlot.service";
import { User } from "../../../context/Context";

interface TreatmentListProps {
  salon?: Salon;
  user?: User
}

function TreatmentList({ salon, user }: TreatmentListProps) {
  const [treatments, setTreatments] = useState<Treatment[]>([]);
  const [treatment, setTreatment] = useState<Treatment>();

  const [selectedTreatmentId, setSelectedTreatmentId] = useState<number | null>(null);
  const [showTimeForm, setShowTimeForm] = useState(false);
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [availableTimeSlots, setAvailableTimeSlots] = useState<TimeSlot[][]>([]);
  const employeesIds = salon?.employeesIds || [];
  useEffect(() => {
    const fetchEmployees = async () => {
      try {
        const response = await EmployeeService.getEmployeesByIds(employeesIds);
        setEmployees(response.data);
      } catch (error) {
      }
    };

      fetchEmployees();
   
  }, [employeesIds]);

  useEffect(() => {
    const fetchTreatments = async () => {
      if (salon?.salonTreatmentsIds) {
        try {
          const response = await TreatmentService.getTreatmentsByIds(salon?.salonTreatmentsIds);
          setTreatments(response.data);
        } catch (error) {
        }
      }
    };

    fetchTreatments();
  }, [salon?.salonTreatmentsIds]);

  useEffect(() => {
    setTreatment(treatments.filter(t=>t.id == selectedTreatmentId).at(0))
  }, [selectedTreatmentId]);

  useEffect(() => {
    const fetchTimeSlots = async () => {
      if (selectedTreatmentId && salon) {
        try {
          const response = await TimeSlotService.getAvailableTimeSlots(
            salon.id, employees[0]?.id || -1, treatment?.durationMultiplier || 1 
          );

          setAvailableTimeSlots(response.data);
        } catch (error) {
        }
      }
    };
  
    fetchTimeSlots();
  }, [treatment, salon, employees]);

  const handleReserveClick = (treatmentId: number) => {
    setSelectedTreatmentId(treatmentId);
    setShowTimeForm(true);
  };

  const handleCloseTimeForm = () => {
    setShowTimeForm(false);
    setSelectedTreatmentId(null);
  };

  return (
    <div className={styles.services}>
      <h1>Третмани</h1>
      {showTimeForm ? (
        <AppointmentAddForm
          salon={salon}
          treatment={selectedTreatmentId}
          employees={employees}
          availableTimeSlots={availableTimeSlots}
          onClose={handleCloseTimeForm}
          user={user}
        />
      ) : (
        <>
          {treatments.length === 0 ? (
            <p>Нема третмани</p>
          ) : (
            <>
              <div className={styles.column}>
                {treatments.map((treatment) => (
                  <div key={treatment.id} className={styles.column}>
                    <TreatmentItem treatment={treatment} />
                  </div>
                ))}
              </div>

              <div className={styles.column}>
                {treatments.map((treatment) => (
                  <div key={treatment.id} className={styles.serviceType}>
                    <TreatmentDetailsItem treatment={treatment} />
                    <ReserveAppointmentForm
                      salonId={salon?.id}
                      treatmentId={treatment.id}
                      onReserveClick={handleReserveClick}
                      user={user}
                    />
                  </div>
                ))}
              </div>
            </>
          )}
        </>
      )}
    </div>
  );
}

export default TreatmentList;
