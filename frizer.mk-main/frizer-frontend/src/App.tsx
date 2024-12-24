import "./App.css";
import Home from "./pages/Home.page";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./pages/Login.page";
import Register from "./pages/Register.page";
import SalonDetails from "./pages/SalonDetails.page";
import Profile from "./pages/Profile.page";

import { GlobalContext, GlobalContextProvider, User } from "./context/Context";
import PrivateRoute from "./guard/PrivateRoute";
import SalonSearchResults from "./pages/SalonSearchResults.page";
import ScrollToTop from "./utils/ScrollToTop";
import Appointments from "./pages/Appointments.page";
import { useContext, useEffect, useState } from "react";
import UserService from "./services/user.service";
import MessagesPage from "./pages/Messaging.page";

function App() {
  const { state, dispatch } = useContext(GlobalContext);
  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    const fetchUser = async () => {
      const response = await UserService.getCurrentUser();
      if (response) {
        setUser(response);
      }
    };
    fetchUser();
  }, [state.user]);
  
  return (
    <>
      <ScrollToTop />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/home" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/salons/:id" element={<SalonDetails />} />
        <Route
          path="/appointments"
          element={
            <PrivateRoute element={<Appointments userId={user?.id} />} />
          }
        />
        <Route
          path="/profile"
          element={<PrivateRoute element={<Profile />} />}
        />
         <Route
          path="/messages"
          element={<PrivateRoute element={<MessagesPage user={user} />} />}
        />
        <Route path="/salons" element={<SalonSearchResults />} />
        <Route path="*" element={<div>Error: Page not found</div>} />
      </Routes>
    </>
  );
}
export default App;
