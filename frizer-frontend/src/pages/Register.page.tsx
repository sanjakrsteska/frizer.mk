import Navbar from "../components/fragments/Navbar/Navbar.component";
import LoginRegisterToggle from "../components/Login/LoginRegisterToggle/LoginRegisterToggle.component";
import Footer from "../components/fragments/Footer/Footer.component";
import styled from "styled-components";

function Register() {
  return (
    <>
      <Navbar />
      <RegisterContainer>
        <LoginRegisterToggle />
      </RegisterContainer>
      <Footer />
    </>
  );
}

const RegisterContainer = styled.div`
  padding: 10vh 8vw 0;
  min-height: calc(100vh - 55px);
  display: grid;
  place-items: center;
`;

export default Register;
