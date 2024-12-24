import React, { useContext, useState } from "react";
import styles from "./RegisterForm.module.scss";
import { useNavigate } from "react-router-dom";
import { GlobalContext } from "../../../context/Context";
import AuthService from "../../../services/auth.service";
import { BaseUserCreate } from "../../../interfaces/BaseUserCreateRequest.interface";
import ValidatorService from "../../../services/validator.service";

function RegisterForm() {
  const { dispatch } = useContext(GlobalContext);
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [confirmedPassword, setConfirmedPassword] = useState<string>("");
  const [firstName, setFirstName] = useState<string>("");
  const [lastName, setLastName] = useState<string>("");
  const [phoneNumber, setPhoneNumber] = useState<string>("+3897");
  const [submitted, setSubmitted] = useState<boolean>(false);
  const [errorMsg, setErrorMsg] = useState<string>();
  const navigate = useNavigate();

  function handleChangeEmail(event: React.ChangeEvent<HTMLInputElement>) {
    setEmail(event.target.value);
  }

  function handleChangePassword(event: React.ChangeEvent<HTMLInputElement>) {
    setPassword(event.target.value);
  }

  function handleChangeConfirmedPassword(
    event: React.ChangeEvent<HTMLInputElement>
  ) {
    setConfirmedPassword(event.target.value);
  }

  function handleChangeFirstName(event: React.ChangeEvent<HTMLInputElement>) {
    setFirstName(event.target.value);
  }

  function handleChangeLastName(event: React.ChangeEvent<HTMLInputElement>) {
    setLastName(event.target.value);
  }

  function handleChangePhoneNumber(event: React.ChangeEvent<HTMLInputElement>) {
    setPhoneNumber(event.target.value);
  }

  function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setSubmitted(true);

    const hasError =
      !ValidatorService.isNameOrSurnameValid(firstName) ||
      !ValidatorService.isNameOrSurnameValid(lastName) ||
      !ValidatorService.isPhoneValid(phoneNumber) ||
      !ValidatorService.isEmailValid(email) ||
      !ValidatorService.isPasswordValid(password) ||
      password !== confirmedPassword;

    if (hasError) return;

    try {
      const user: BaseUserCreate = {
        firstName,
        lastName,
        email,
        password,
        phoneNumber,
      };
      AuthService.register(user)
        .then((response) => {
          if (response.message === "Successfully registered.") {
            navigate("/login");
          }
        })
        .catch(_ => {
          setErrorMsg("Веќе постои профил со истата е-маил адреса.");
        });
    } catch (error) {
      setErrorMsg("Регистрацијата не успеа. Обидете се повторно.");
    }
  }

  return (
    <form onSubmit={handleSubmit} className={styles.registerForm}>
      <label htmlFor="firstName">Име</label>
      <input
        type="text"
        name="firstName"
        value={firstName}
        required
        placeholder="Име"
        onChange={handleChangeFirstName}
      />
      {submitted && !firstName && (
        <small className={styles.error}>Името е задолжително.</small>
      )}
      {submitted &&
        firstName &&
        !ValidatorService.isNameOrSurnameValid(firstName) && (
          <small className={styles.error}>
            Името треба да има минимум 3 карактери и само букви.
          </small>
        )}

      <label htmlFor="lastName">Презиме</label>
      <input
        type="text"
        name="lastName"
        value={lastName}
        required
        placeholder="Презиме"
        onChange={handleChangeLastName}
      />
      {submitted && !lastName && (
        <small className={styles.error}>Презимето е задолжително.</small>
      )}
      {submitted &&
        lastName &&
        !ValidatorService.isNameOrSurnameValid(lastName) && (
          <small className={styles.error}>
            Презимето треба да има минимум 3 карактери и само букви.
          </small>
        )}

      <label htmlFor="phoneNumber">Телефонски број</label>
      <input
        type="text"
        name="phoneNumber"
        value={phoneNumber}
        required
        placeholder="Телефонски број"
        onChange={handleChangePhoneNumber}
      />
      {submitted && !ValidatorService.isPhoneValid(phoneNumber) && (
        <small className={styles.error}>Внесете валиден телефонски број.</small>
      )}
      <label htmlFor="email">Е-маил</label>
      <input
        type="text"
        name="email"
        value={email}
        required
        placeholder="Емаил"
        onChange={handleChangeEmail}
      />
      {submitted && !ValidatorService.isEmailValid(email) && (
        <small className={styles.error}>Внесете валидна емаил адреса.</small>
      )}
      <label htmlFor="password">Лозинка</label>
      <input
        type="password"
        name="password"
        value={password}
        required
        placeholder="Лозинка"
        onChange={handleChangePassword}
      />
      {submitted && password.length < 8 && (
        <small className={styles.error}>
          Лозинката мора да е долга барем 8 карактери.
        </small>
      )}
      {submitted && !ValidatorService.hasUpperCase(password) && (
        <small className={styles.error}>
          Лозинката мора да содржи барем една голема буква.
        </small>
      )}
      {submitted && !ValidatorService.hasLowerCase(password) && (
        <small className={styles.error}>
          Лозинката мора да содржи барем една мала буква.
        </small>
      )}
      {submitted && !ValidatorService.hasNumber(password) && (
        <small className={styles.error}>
          Лозинката мора да содржи барем еден број.
        </small>
      )}
      {submitted && !ValidatorService.hasSpecialCharacter(password) && (
        <small className={styles.error}>
          Лозинката мора да содржи барем еден специјален карактер.
        </small>
      )}
      <label htmlFor="confirmedPassword">Потврди лозинка</label>
      <input
        type="password"
        name="confirmedPassword"
        value={confirmedPassword}
        required
        placeholder="Повторете ја лозинката"
        onChange={handleChangeConfirmedPassword}
      />
      {submitted && password !== confirmedPassword && (
        <small className={styles.error}>Лозинките не се исти.</small>
      )}
      {submitted && errorMsg && (
        <small className={styles.error}>{errorMsg}</small>
      )}
      <button className={`primaryButton ${styles.primaryButton}`} type="submit">
        Регистрирај се
      </button>
    </form>
  );
}

export default RegisterForm;
