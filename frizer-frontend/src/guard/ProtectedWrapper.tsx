import React, { useContext, useEffect } from "react";
import { GlobalContext, ACTION_TYPE, DecodedToken } from "../context/Context";
import App from "../App";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";

function ProtectedWrapper() {
  const { state, dispatch } = useContext(GlobalContext);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      try {
        if (token.split(".").length !== 3) {
          throw new Error("Invalid token format");
        }
        
        const decodedToken = jwtDecode<DecodedToken>(token);
        if (decodedToken.exp && decodedToken.exp * 1000 < Date.now()) { // exp is in seconds
          localStorage.removeItem("token");
          localStorage.removeItem("currentUser");
          dispatch({ type: ACTION_TYPE.SET_USER, payload: null });
          dispatch({ type: ACTION_TYPE.SET_TOKEN, payload: null });
          navigate("/login"); // Redirect to login
          return;
        }
        const currentUser = {
          id: decodedToken.id,
          firstName: decodedToken.firstName,
          lastName: decodedToken.lastName,
          email: decodedToken.email,
          roles: decodedToken.authorities,
          phoneNumber: decodedToken.phoneNumber,
        };

        dispatch({ type: ACTION_TYPE.SET_USER, payload: currentUser });
        dispatch({ type: ACTION_TYPE.SET_TOKEN, payload: token });
      } catch (error) {
        console.error("Error decoding token:", error);
        localStorage.removeItem("token");
        localStorage.removeItem("currentUser");
        dispatch({ type: ACTION_TYPE.SET_USER, payload: null });
        dispatch({ type: ACTION_TYPE.SET_TOKEN, payload: null });
        navigate("/login"); 
      }
    }
  }, [dispatch]);

  return <App />;
}

export default ProtectedWrapper;
