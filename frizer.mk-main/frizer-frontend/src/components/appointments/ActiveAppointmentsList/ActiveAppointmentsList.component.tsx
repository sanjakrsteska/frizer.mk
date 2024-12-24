import React, { useEffect, useMemo, useState } from "react";
import styles from "./ActiveAppointmentsList.module.scss";
import AppointmentService from "../../../services/appointment.service";
import UserService from "../../../services/user.service";
import { User } from "../../../context/Context";
import { AppointmentDetails } from "../../../interfaces/AppointmentDetails.interface";
import ActiveAppointmentRow from "../ActiveAppointmentRow/ActiveAppointmentRow.module";

interface ActiveAppointmentsListProps {
  userId?: number;
}

function ActiveAppointmentsList({ userId }: ActiveAppointmentsListProps) {
  const [appointments, setAppointments] = useState<AppointmentDetails[]>([]);
  const [employeeAppointments, setEmployeeAppointments] = useState<AppointmentDetails[]>([]);
  const [user, setUser] = useState<User>();

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await UserService.getCurrentUser();
        if (response) {
          setUser(response);
        }
      } catch (error) {}
    };
    fetchUser();
  }, []);

  useEffect(() => {
    const fetchAppointments = async () => {
      try {
        if (user) {
          if (user.roles.includes("ROLE_EMPLOYEE")) {
            if (userId) {
              const response =
                await AppointmentService.getAppointmentsByEmployee(userId);
              setEmployeeAppointments(response.data);
            }
          }
          if (userId) {
            const response = await AppointmentService.getAppointmentsByCustomer(
              userId
            );
            setAppointments(response.data);
          }
        }
      } catch (error) {}
    };
    fetchAppointments();
  }, [userId, user]);

  const activeAppointments = appointments.filter(
    (a) =>
      new Date(a.dateFrom).getTime() >= Date.now() &&
      new Date(a.dateTo).getTime() > Date.now()
  );
  const pastAppointments = appointments.filter(
    (a) => new Date(a.dateTo).getTime() < Date.now()
  );

  const activeEmployeeAppointments = employeeAppointments.filter(
    (a) =>
      new Date(a.dateFrom).getTime() >= Date.now() &&
      new Date(a.dateTo).getTime() > Date.now()
  );
  const pastEmployeeAppointments = employeeAppointments.filter(
    (a) => new Date(a.dateTo).getTime() < Date.now()
  );

  const handleAppointmentRemove = (id: number) => {
    setAppointments(
      appointments.filter((appointment) => appointment.id !== id)
    );
    setEmployeeAppointments(
      employeeAppointments.filter((appointment) => appointment.id !== id)
    );
  };

  const pastSalons = Array.from(
    new Set(pastEmployeeAppointments.map((a) => a.salonName))
  );
  const futureSalons = Array.from(
    new Set(activeEmployeeAppointments.map((a) => a.salonName))
  );
  return (
    <>
      <div className={styles.customerAppointments}>
        <h2>Термини</h2>
        {activeAppointments.length == 0 ? (
          <h3>Немате активни термини</h3>
        ) : (
          <div>
            <h3>Вашите активни термини</h3>
            <table>
              <thead>
                <tr>
                  <th>Почеток на термин</th>
                  <th>Крај на термин</th>
                  <th>Третман</th>
                  <th>Салон</th>
                  <th>Вработен</th>
                  <th>Присуствувано</th>
                  <th>Акции</th>
                </tr>
              </thead>
              <tbody>
                {activeAppointments.map((appointment, i) => (
                  <ActiveAppointmentRow
                    key={i}
                    appointment={appointment}
                    showActions={true}
                    onAppointmentRemove={handleAppointmentRemove}
                    showEmployee={true}
                  />
                ))}
              </tbody>
            </table>
          </div>
        )}
        <div>
          {pastAppointments.length == 0 ? (
            <h3>Немате поминати термини</h3>
          ) : (
            <div>
              <h3>Вашите поминати термини</h3>
              <table>
                <thead>
                  <tr>
                    <th>Почеток на термин</th>
                    <th>Крај на термин</th>
                    <th>Третман</th>
                    <th>Салон</th>
                    <th>Вработен</th>
                    <th>Присуствувано</th>
                  </tr>
                </thead>
                <tbody>
                  {pastAppointments.map((appointment) => (
                    <ActiveAppointmentRow
                      appointment={appointment}
                      showActions={false}
                      showEmployee={true}
                    />
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      </div>
      {user?.roles.includes("ROLE_EMPLOYEE") && (
        <div className={styles.employeeAppointments}>
          <h2>Термини</h2>
          {activeEmployeeAppointments.length === 0 ? (
            <h3>Немате активни термини</h3>
          ) : (
            <div>
              {futureSalons.length > 1 && (
                <h3>
                  {" "}
                  Вашите активни термини во салоните:
                  <span>{futureSalons.join(", ")}</span>
                </h3>
              )}
              {futureSalons.length == 1 && (
                <h3>
                  {" "}
                  Вашите активни термини во салонот:
                  <span>{futureSalons.at(0)}</span>
                </h3>
              )}
              <table>
                <thead>
                  <tr>
                    <th>Почеток на термин</th>
                    <th>Крај на термин</th>
                    <th>Третман</th>
                    <th>Салон</th>
                    <th>Корисник</th>
                    <th>Присуствувано</th>
                    <th>Акции</th>
                  </tr>
                </thead>
                <tbody>
                  {activeEmployeeAppointments.map((appointment) => (
                    <ActiveAppointmentRow
                      key={appointment.id}
                      appointment={appointment}
                      showActions={true}
                      onAppointmentRemove={handleAppointmentRemove}
                      showEmployee={true}
                    />
                  ))}
                </tbody>
              </table>
            </div>
          )}
          {pastEmployeeAppointments.length > 0 && (
            <div>
              {pastSalons.length > 1 && (
                <h3>
                  Вашите поминати термини во салоните:
                  <span>{pastSalons.join(", ")}</span>
                </h3>
              )}
              {pastSalons.length == 1 && (
                <h3>
                  Вашите поминати термини во салонот:
                  <span>{pastSalons.at(0)}</span>
                </h3>
              )}
              <table>
                <thead>
                  <tr>
                    <th>Почеток на термин</th>
                    <th>Крај на термин</th>
                    <th>Третман</th>
                    <th>Салон</th>
                    <th>Корисник</th>
                    <th>Присуствувано</th>
                  </tr>
                </thead>
                <tbody>
                  {pastEmployeeAppointments.map((appointment, i) => (
                    <ActiveAppointmentRow
                      key={i}
                      appointment={appointment}
                      showActions={false}
                      showEmployee={true}
                    />
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      )}{" "}
    </>
  );
}

export default ActiveAppointmentsList;
