import { useState } from "react";
import RegisterForm from "../RegisterForm/RegisterForm.component";
import styles from "./LoginRegisterToggle.module.scss";
import LoginForm from "../LoginForm/LoginForm.component";
import { NavLink, useLocation, useNavigate } from "react-router-dom";

function LoginRegisterToggle() {
  const location = useLocation();
  const navigate = useNavigate();
  const [isLogin, setIsLogin] = useState(location.pathname === "/login");

  const toggleForm = (targetPath: string) => {
    navigate(targetPath);
  };

  return (
    <div className={styles.toggleContainer}>
      <h1 className={styles.title}>
        {isLogin ? "Најави се" : "Регистрирај се"}
      </h1>
      <div className={styles.toggleButtons}>
        <NavLink
          to="/login"
          className={isLogin ? styles.activeButton : styles.inactiveButton}
        >
          Најава
        </NavLink>
        <NavLink
          to="/register"
          className={!isLogin ? styles.activeButton : styles.inactiveButton}
        >
          Регистрација
        </NavLink>
      </div>
      {isLogin ? <LoginForm /> : <RegisterForm />}
    </div>
  );
}

export default LoginRegisterToggle;
