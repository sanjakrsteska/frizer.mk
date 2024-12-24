import { Employee } from '../interfaces/Employee.interface';
import { EmployeeCreate } from '../interfaces/EmployeeCreateRequest.interface';
import axios from './config/axios';

const EmployeeService = {
    getEmployees: () => {
        return axios.get<Employee[]>("/employees");
    },
    getEmployee: (id: number) => {
        return axios.get<Employee>(`/employees/${id}`);
    },
    getEmployeesByIds: (ids: number[]) => {
        const params = new URLSearchParams();
        ids.forEach(id => params.append('ids', id.toString()));
        
        return axios.get<Employee[]>('/employees/ids', { params });
      },
    async createEmployee(employeeCreate: EmployeeCreate) {
        try {
          const response = await axios.post('/employees/add', employeeCreate);
          return response.data;
        } catch (error) {
          throw new Error('Failed to create employee');
        }
      },
      async deleteEmployee(id: number) {
        try {
          const response = await axios.delete(`/employees/delete/${id}`);
          return response.data;
        } catch (error) {
          throw new Error('Failed to delete employee');
        }
      }
  
};

export default EmployeeService;

