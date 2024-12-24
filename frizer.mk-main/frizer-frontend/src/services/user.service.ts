import { jwtDecode } from "jwt-decode";
import { DecodedToken, User } from "../context/Context";
import axios from "./config/axios";
import { UserEditRequest } from "../interfaces/UserEditRequest";

const UserService = {
  setUser(user: User): void {
    localStorage.setItem("currentUser", JSON.stringify(user));
  },
  removeUser(): void {
    localStorage.removeItem("currentUser");
  },
  setUserWithToken: async (): Promise<User | null> => {
    try {
      const response = await axios.get<User>("/users/me");
      const user = response.data;
      UserService.setUser(user);
      return user;
    } catch (error) {
      console.error(
        "An error occurred while trying to fetch user's details: ",
        error
      );
      return null;
    }
  },
  getCurrentUser: async (): Promise<User | null> => {
    const userJson: string | null = localStorage.getItem("currentUser");
    let user: User | null = userJson ? JSON.parse(userJson) : null;

    if (user) {
      return user;
    } else {
      return await UserService.setUserWithToken();
    }
  },
  updateUserDetails: async (
    id: number,
    data: UserEditRequest
  ): Promise<User | null> => {
    try {
      const response = await axios.put<User>(`/users/edit/${id}`, data);
      const updatedUser = response.data;
      UserService.setUser(updatedUser);
      return updatedUser;
    } catch (error) {
      console.error(
        "An error occurred while trying to update user's details: ",
        error
      );
      return null;
    }
  },
  searchUsers: async (query: string): Promise<User[]> => {
    try {
      const response = await axios.get<User[]>(`/users/search?query=${query}`);
      return response.data;
    } catch (error) {
      console.error("Error fetching search results:", error);
      return [];
    }
  },
  getUserById: async (id: number): Promise<User> => {
      const response = await axios.get<User>(`/users/${id}`);
      return response.data;
   
  }
};
export default UserService;
