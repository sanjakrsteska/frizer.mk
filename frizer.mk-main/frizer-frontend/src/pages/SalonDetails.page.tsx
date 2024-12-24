import React, { useEffect, useState } from "react";
import FeatureImages from "../components/SalonDetails/FeatureImages/FeatureImages.component";
import { Salon } from "../interfaces/Salon.interface";
import SalonBaseInfo from "../components/SalonDetails/SalonBaseInfo/SalonBaseInfo.component";
import styled from "styled-components";
import TreatmentList from "../components/SalonDetails/TreatmentList/TreatmenList.component";
import SalonService from "../services/salon.service";
import { useParams } from "react-router-dom";
import Footer from "../components/fragments/Footer/Footer.component";
import Navbar from "../components/fragments/Navbar/Navbar.component";
import SalonMap from "../components/SalonDetails/SalonMap/SalonMap.component";
import EmployeeList from "../components/SalonDetails/EmployeeList/EmployeeList.component";
import ReviewList from "../components/SalonDetails/ReviewList/ReviewList.component";
import EmployeeAddForm from "../components/SalonDetails/EmployeeAddForm/EmployeeAddForm.component";
import { Employee } from "../interfaces/Employee.interface";
import EmployeeRemoveForm from "../components/SalonDetails/EmployeeRemoveForm/EmployeeRemoveForm.component";
import TreatmentAddForm from "../components/SalonDetails/TreatmentAddForm/TreatmentAddForm.component";
import { Treatment } from "../interfaces/Treatment.interface";
import TreatmentRemoveForm from "../components/SalonDetails/TreatmentRemoveForm/TreatmentRemoveForm.module";
import { User } from "../context/Context";
import UserService from "../services/user.service";
import CustomerService from "../services/customer.service";
import { Customer } from "../interfaces/Customer.interface";

function SalonDetails() {
  const { id } = useParams();
  const [salon, setSalon] = useState<Salon>();
  const [loading, setLoading] = useState<boolean>(true);
  const [user, setUser] = useState<User>();
  const [customer, setCustomer] = useState<Customer | null>();

  useEffect(() => {
    const fetchUser = async () => {
      const response = await UserService.getCurrentUser();
      if (response) {
        setUser(response);
      }
    };
    fetchUser();
    }, []);

  useEffect(() => {
    const fetchSalon = async () => {
      try {
        if (id) {
          const response = await SalonService.getSalon(+id);
          setSalon(response.data);
        }
      } catch (error) {
        console.error("Error fetching salon:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchSalon();
  }, [id]);

  useEffect(() => {
    const getCustomer = async () => {
      try {
        if (user) {
          const response = await CustomerService.getCustomerByEmail(
            user?.email
          );
          setCustomer(response?.data);
        }
      } catch (error) {
        console.error("Failed to fetch customer:", error);
      }
    };

    getCustomer();
  }, [user]);

  const handleEmployeeAdd = async (newEmployee: Employee) => {
    if (salon) {
      try {
        const updatedSalon = {
          ...salon,
          employeesIds: [...salon.employeesIds, newEmployee.id],
        };
        setSalon(updatedSalon);
      } catch (error) {
        console.error("Error updating salon:", error);
      }
    }
  };

  const handleEmployeeRemove = async (employeeId: number) => {
    if (salon) {
      try {
        const updatedEmployeeIds = salon.employeesIds.filter(
          (e) => e !== employeeId
        );
        const updatedSalon = { ...salon, employeesIds: updatedEmployeeIds };
        setSalon(updatedSalon);
      } catch (error) {
        console.error("Error updating salon:", error);
      }
    }
  };

  const handleTreatmentAdd = async (newTreatment: Treatment) => {
    if (salon) {
      try {
        const updatedSalon = {
          ...salon,
          salonTreatmentsIds: [...salon.salonTreatmentsIds, newTreatment.id],
        };
        setSalon(updatedSalon);
      } catch (error) {
        console.error("Error updating salon:", error);
      }
    }
  };

  const handleImageAdd = async (salonWithImage: Salon) => {
    if (salon) {
      try {
        const updatedSalon = {
          ...salon,
          images: salonWithImage.images,
        };
        setSalon(updatedSalon);
      } catch (error) {
        console.error("Error updating salon:", error);
      }
    }
  };

  const handleAddToFavourites = async (favouriteSalon: Salon) => {
    if (salon && customer) {
      try {
        const updatedCustomer = {
          ...customer,
          favouriteSalonsIds: [...customer.favouriteSalonsIds, favouriteSalon.id],
        };
        setCustomer(updatedCustomer);
      } catch (error) {
        console.error("Error updating salon:", error);
      }
    }
  };

  const handleRemoveFromFavourites = async (favouriteSalonId: number) => {
    if (salon && customer) {
      try {
        const favouriteSalons = customer.favouriteSalonsIds.filter(s=> s !== favouriteSalonId);
        const updatedCustomer = {
          ...customer,
          favouriteSalonsIds: favouriteSalons
        };
        setCustomer(updatedCustomer);
      } catch (error) {
        console.error("Error updating salon:", error);
      }
    }
  };
  const handleTreatmentRemove = async (treatmentId: number) => {
    if (salon) {
      try {
        const updatedSalonTreatmentIds = salon.salonTreatmentsIds.filter(
          (e) => e !== treatmentId
        );
        const updatedSalon = {
          ...salon,
          salonTreatmentsIds: updatedSalonTreatmentIds,
        };
        setSalon(updatedSalon);
      } catch (error) {
        console.error("Error updating salon:", error);
      }
    }
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <>
      <Navbar />
      <PageContainer>
        <FeatureImages salon={salon} user={user} onImageAdd={handleImageAdd}/>
        <SalonBaseInfo salon={salon} user={user} onAddToFavourites={handleAddToFavourites} customer={customer} onRemoveFromFavourites={handleRemoveFromFavourites} />
        <TreatmentList salon={salon} user={user} />
        <ReviewList salon={salon} user={user} />
        <EmployeeList salon={salon} />
        <EmployeeAddForm
          salon={salon}
          onEmployeeAdd={handleEmployeeAdd}
          user={user}
        />
        <EmployeeRemoveForm
          salon={salon}
          onEmployeeRemove={handleEmployeeRemove}
          user={user}
        />
        <TreatmentAddForm salon={salon} onTreatmentAdd={handleTreatmentAdd}
         user={user} />
        <TreatmentRemoveForm
          salon={salon}
          onTreatmentRemove={handleTreatmentRemove}
          user={user}
        />
        <SalonMap salon={salon} />
      </PageContainer>
      <Footer />
    </>
  );
}

const PageContainer = styled.div`
  padding: 5em 8vw 0;
`;

export default SalonDetails;
