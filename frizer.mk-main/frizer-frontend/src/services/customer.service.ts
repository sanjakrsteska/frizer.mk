import { Customer } from "../interfaces/Customer.interface";
import axios from "./config/axios";

const CustomerService = {
    getCustomerByEmail: async (email: string) => {
        try {
          return axios.get<Customer>(`/customers/by`, {
            params: {
              email
            },
          });
        } catch (error) {
          
        }
      }
}
export default CustomerService;