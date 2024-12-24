import styles from "./Navbar.module.scss";
import { AnimatePresence, motion } from "framer-motion";
import React, { useContext, useEffect, useState } from "react";
import { NavLink, Link, useNavigate, useLocation } from "react-router-dom";
import { FaArrowRightLong } from "react-icons/fa6";
import { ACTION_TYPE, GlobalContext, User } from "../../../context/Context";
import AuthService from "../../../services/auth.service";
import UserService from "../../../services/user.service";
import { AiFillMessage } from "react-icons/ai";

function Navbar() {
  const { state, dispatch } = useContext(GlobalContext);
  const [currentUser, setCurrentUser] = useState<User | null>();
  const [menuState, setMenuState] = useState(false);
  const [rotation, setRotation] = useState(0);
  const navigate = useNavigate();
  const location = useLocation();

  const showMenu = () => {
    window.innerWidth <= 650 ? setMenuState(false) : setMenuState(true);
  };

  const closeMenu = () => {
    if (window.innerWidth <= 650) setMenuState(false);
  };

  const handleClick = () => {
    setMenuState(!menuState);
    rotation === 0 ? setRotation(180) : setRotation(0);
  };

  useEffect(() => {
    showMenu();

    const fetchCurrentUser = async () => {
      try {
        const response = await UserService.getCurrentUser();
        setCurrentUser(response);
      } catch (error) {}
    };

    fetchCurrentUser();
  }, [state?.token]);

  useEffect(() => {
    setCurrentUser(state.user);
  }, [state.user]);

  window.addEventListener("resize", showMenu);

  const handleLogout = () => {
    dispatch({ type: ACTION_TYPE.SET_USER, payload: null });
    dispatch({ type: ACTION_TYPE.SET_TOKEN, payload: null });
    dispatch({ type: ACTION_TYPE.SET_UNREAD_MESSAGES, payload: 0 });

    AuthService.logout();
    UserService.removeUser();

    navigate("/home");
  };
  return (
    <nav className={styles.nav}>
      <Link to={"/"} className={styles.homeButton}>
        Frizer.mk
      </Link>
      <motion.button
        className={styles.toggleButton}
        onClick={handleClick}
        animate={{ rotate: rotation }}
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          height="48"
          width="48"
          viewBox="0 0 50 50"
        >
          <path d="M6 36v-3h36v3Zm0-10.5v-3h36v3ZM6 15v-3h36v3Z" />
        </svg>
      </motion.button>
      <AnimatePresence>
        {menuState && (
          <motion.ul
            className={styles.navlinks}
            initial={{ y: -25, opacity: 0 }}
            animate={{ y: 0, opacity: 1 }}
            transition={{ type: "tween" }}
            exit={{ y: -25, opacity: 0 }}
          >
            <NavLink to="/" onClick={closeMenu}>
              <li className={styles.navlink}>
                <span>Почетна</span>
              </li>
            </NavLink>
            
            {currentUser && (
              <NavLink to="/appointments" onClick={closeMenu}>
              <li className={styles.navlink}>
                <span>Термини</span>
              </li>
            </NavLink>
            )}

            {currentUser && (
              <NavLink to={"/profile"} onClick={closeMenu}>
                <li className={styles.navlink}>
                  <span>Здраво, {currentUser.firstName}</span>
                </li>
              </NavLink>
            )}

            {currentUser && (
              <NavLink to={"/messages"} onClick={closeMenu}>
                <li className={styles.navlink}>
                  <span className={styles.iconWrapper}>
                      <AiFillMessage size={24} />
                    {location.pathname === "/messages" && state.unreadMessages != 0 && (
                      <div className={styles.notificationBadge}>
                        {state.unreadMessages}
                      </div>
                    )}
                  </span>
                </li>
              </NavLink>
            )}

            {!currentUser && (
              <NavLink
                to={currentUser ? "#" : "/login"}
                onClick={currentUser ? handleLogout : closeMenu}
              >
                <li className={styles.navlink}>
                  {currentUser ? (
                    "Logout"
                  ) : (
                    <>
                      Најави се <FaArrowRightLong />
                    </>
                  )}
                </li>
              </NavLink>
            )}
          </motion.ul>
        )}
      </AnimatePresence>
    </nav>
  );
}

export default Navbar;
