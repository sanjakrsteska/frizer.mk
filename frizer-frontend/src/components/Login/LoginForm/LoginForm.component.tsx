import React, { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  GlobalContext,
  ACTION_TYPE,
  DecodedToken,
} from "../../../context/Context";
import { jwtDecode } from "jwt-decode";
import styles from "./LoginForm.module.scss";
import AuthService from "../../../services/auth.service";
import UserService from "../../../services/user.service";

export default function LoginForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isValid, setIsValid] = useState(true);
  const [emailError, setEmailError] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const { dispatch } = useContext(GlobalContext);
  const navigate = useNavigate();

  function isValidEmail(email: string) {
    const emailRegex = /\S+@\S+\.\S+/;
    return emailRegex.test(email);
  }

  function handleChangeEmail(event: React.ChangeEvent<HTMLInputElement>) {
    const newEmail = event.target.value;
    setEmail(newEmail);

    if (!newEmail) {
      setEmailError("Е-маил е задолжително поле.");
    } else if (!isValidEmail(newEmail)) {
      setEmailError("Внесете валидна емаил адреса.");
    } else {
      setEmailError("");
    }
  }

  function handleChangePassword(event: React.ChangeEvent<HTMLInputElement>) {
    const newPassword = event.target.value;
    setPassword(newPassword);

    if (!newPassword) {
      setPasswordError("Лозинката е задолжително поле.");
    } else {
      setPasswordError("");
    }
  }

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    if (isValidEmail(email)) {
      try {
        const response = await AuthService.authenticate(email, password);
        const token = response.token;
        if (!token || token.split(".").length !== 3) {
          throw new Error("Invalid token structure");
        }

        localStorage.setItem("token", token);
        UserService.setUserWithToken();

        const decodedToken = jwtDecode<DecodedToken>(token);
        const currentUser = {
          id: decodedToken.id,
          name: decodedToken.firstName,
          email: decodedToken.sub,
          role: decodedToken.authorities,
        };

        dispatch({ type: ACTION_TYPE.SET_USER, payload: currentUser });
        dispatch({ type: ACTION_TYPE.SET_TOKEN, payload: token });
        navigate("/");
      } catch (error) {
        console.error("Login error:", error);
        setIsValid(false);
        setErrorMessage("Погрешно корисничко име или лозинка.");
        dispatch({ type: ACTION_TYPE.SET_USER, payload: null });
        dispatch({ type: ACTION_TYPE.SET_TOKEN, payload: null });
        UserService.removeUser();
      }
    }
  }
  return (
    <form onSubmit={handleSubmit} className={styles.loginForm}>
      <label htmlFor="username">Корисничко име</label>
      <input
        type="text"
        name="username"
        value={email}
        required
        placeholder="Корисничко име"
        onChange={handleChangeEmail}
      />
      {emailError && <small className={styles.error}>{emailError}</small>}

      <label htmlFor="password">Лозинка</label>
      <input
        type="password"
        name="password"
        value={password}
        required
        placeholder="••••••••"
        onChange={handleChangePassword}
      />
      {passwordError && <small className={styles.error}>{passwordError}</small>}

      {!isValid && <small className={styles.error}>{errorMessage}</small>}

      <button className={`primaryButton ${styles.primaryButton}`} type="submit">
        Login
      </button>
    </form>
  );
}
