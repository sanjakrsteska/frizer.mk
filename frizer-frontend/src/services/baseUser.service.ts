import { BaseUser } from "../interfaces/BaseUser.interface";
import axios from "./config/axios";

const BaseUserService = {
  getAvailableUsers: () => {
    return axios.get<BaseUser[]>("/users/available");
  }
};

export default BaseUserService;
