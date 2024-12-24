import  instance   from './config/axios';
import { AuthResponse } from '../context/Context';
import { BaseUserCreate } from '../interfaces/BaseUserCreateRequest.interface';

const AuthService =  {
   async authenticate(email: string, password: string): Promise<AuthResponse> {
    try {
      const response = await instance.post(`/auth/login`, {
        email,
        password,
      });
      return response.data;
    } catch (error) {
      throw new Error('Authentication failed');
    }
  },
  
  async register(userCreate: BaseUserCreate) {
    try {
      const response = await instance.post('/auth/register', userCreate);
      return response.data;
    } catch (error) {
      throw new Error('Failed to create user');
    }
  },
  logout(): void{
    localStorage.removeItem('token'); 
  }
}
export default AuthService; 
