import { useContext } from "react";
import { NavLink } from "react-router-dom";
import { ACTION_TYPE, GlobalContext } from "../../../context/Context";
import AuthService from "../../../services/auth.service";
import UserService from "../../../services/user.service";
import { RiArrowRightUpLine } from "react-icons/ri";

function ProfileLogout() {
  const { dispatch } = useContext(GlobalContext);

  const handleLogout = () => {
    dispatch({ type: ACTION_TYPE.SET_USER, payload: null });
    dispatch({ type: ACTION_TYPE.SET_TOKEN, payload: null });
    AuthService.logout();
    UserService.removeUser();
  };

  return (
    <NavLink to={"/home"} onClick={handleLogout}>
      <button className={`secondaryButton`} style={{display: "flex", alignItems: "center", justifyContent: "center", gap: "0.3em"}}>
        <span> Одјави се</span>
        <RiArrowRightUpLine />
      </button>
    </NavLink>
  );
}

export default ProfileLogout;