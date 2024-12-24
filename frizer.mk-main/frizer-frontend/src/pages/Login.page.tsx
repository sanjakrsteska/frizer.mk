import Navbar from "../components/fragments/Navbar/Navbar.component";
import Footer from "../components/fragments/Footer/Footer.component";
import LoginRegisterToggle from "../components/Login/LoginRegisterToggle/LoginRegisterToggle.component";
import styled from "styled-components";

function Login() {
  return (
    <>
      <Navbar />
      <LoginContainer>
        <LoginRegisterToggle />
      </LoginContainer>
      <Footer />
    </>
  );
}

const LoginContainer = styled.div`
  padding: 0 8vw;
  min-height: calc(100vh - 55px - 3em);
  display: grid;
  place-items: center;
`;

export default Login;
