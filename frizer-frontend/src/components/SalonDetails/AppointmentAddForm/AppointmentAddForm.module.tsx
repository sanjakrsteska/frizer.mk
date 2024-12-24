import React, { useEffect, useState } from "react";
import styles from "./AppointmentAddForm.module.scss";
import { Employee } from "../../../interfaces/Employee.interface";
import { Salon } from "../../../interfaces/Salon.interface";
import { TimeSlot } from "../../../interfaces/TimeSlot.interface";
import AppointmentService from "../../../services/appointment.service";
import { AppointmentCreateRequest } from "../../../interfaces/AppointmentCreateRequest.interface";
import { useNavigate } from "react-router-dom";
import { User } from "../../../context/Context";
import CustomerService from "../../../services/customer.service";
import { Customer } from "../../../interfaces/Customer.interface";

interface AppointmentAddFormProps {
  salon?: Salon;
  treatment?: number | null;
  employees: Employee[];
  onClose?: () => void;
  availableTimeSlots: TimeSlot[][];
  user?: User;
}

function AppointmentAddForm({
  salon,
  treatment,
  employees,
  onClose,
  availableTimeSlots: initialAvailableTimeSlots,
  user,
}: AppointmentAddFormProps) {
  const [selectedDay, setSelectedDay] = useState<number>(-1);
  const [selectedTime, setSelectedTime] = useState<number>(-1);
  const [selectedEmployee, setSelectedEmployee] = useState<number | "">("");
  const [customer, setCustomer] = useState<Customer | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCustomer = async () => {
      try {
        const response = await CustomerService.getCustomerByEmail(
          user?.email ?? ""
        );
        if (response) {
          setCustomer(response.data);
        } else {
          setCustomer(null);
        }
      } catch (err) {}
    };

    fetchCustomer();
  }, [user]);

  const adjustTime = (dateStr: string, hours: number) => {
    const date = new Date(dateStr);
    date.setHours(date.getHours() + hours);
    return date.toISOString();
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (selectedDay < 0 || selectedTime < 0 || selectedEmployee === "") {
      return;
    }

    const timeSlot = initialAvailableTimeSlots[selectedDay][selectedTime];
    if (!timeSlot) {
      return;
    }

    const appointmentData: AppointmentCreateRequest = {
      dateFrom: adjustTime(timeSlot.from, 1),
      dateTo: adjustTime(timeSlot.to, 1),
      treatmentId: treatment || -1,
      salonId: salon?.id || -1,
      employeeId: selectedEmployee,
      customerId: customer?.id || -1,
    };

    try {
      await AppointmentService.createAppointments(appointmentData);
      navigate("/appointments");
    } catch (error) {}
  };

  return (
    <form className={styles.treatmentChooseTimeForm} onSubmit={handleSubmit}>
      <h2>Одбери термин</h2>
      <input type="hidden" name="salon" value={salon?.id} />
      <input type="hidden" name="treatment" value={treatment || ""} />

      <p>Одбери вработен</p>
      <select
        id="chooseEmployee"
        value={selectedEmployee}
        required
        onChange={(e) => setSelectedEmployee(Number(e.target.value))}
      >
        <option value="">Вработен</option>
        {employees.length > 0 ? (
          employees.map((employee) => (
            <option key={employee.id} value={employee.id}>
              {employee.firstName} {employee.lastName}
            </option>
          ))
        ) : (
          <option value="">Нема достапни вработени</option>
        )}
      </select>

      <p>Одбери датум</p>
      <select
        id="chooseDay"
        required
        onChange={(e) => setSelectedDay(Number(e.target.value))}
      >
        <option value="">Датум</option>
        {selectedEmployee !== "" &&
          initialAvailableTimeSlots.map((el, i) => {
            const currentDatePlusDays = new Date();
            currentDatePlusDays.setDate(currentDatePlusDays.getDate() + i);

            const formattedDate = currentDatePlusDays.toLocaleDateString(
              "en-GB",
              {
                day: "2-digit",
                month: "short",
                year: "numeric",
              }
            );

            return (
              <option value={i} key={i}>
                {formattedDate}
              </option>
            );
          })}
      </select>

      <p>Одбери термин</p>
      <select
        id="time"
        name="time"
        value={selectedTime}
        required
        onChange={(e) => setSelectedTime(Number(e.target.value))}
      >
        <option value="">Термин</option>
        {selectedDay > -1 &&
          initialAvailableTimeSlots[selectedDay].map((timeSlot, i) => {
            return (
              <option value={i} key={i}>
                {new Date(timeSlot.from).toLocaleTimeString("en-GB", {
                  hour: "2-digit",
                  minute: "2-digit",
                })}{" "}
                -{" "}
                {new Date(timeSlot.to).toLocaleTimeString("en-GB", {
                  hour: "2-digit",
                  minute: "2-digit",
                })}{" "}
              </option>
            );
          })}
      </select>

      {onClose && (
        <button type="button" className="secondaryButton" onClick={onClose}>
          Откажи
        </button>
      )}
      <button type="submit" className="primaryButton">
        Резервирај
      </button>
    </form>
  );
}

export default AppointmentAddForm;
