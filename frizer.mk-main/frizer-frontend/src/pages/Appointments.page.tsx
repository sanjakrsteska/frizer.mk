import ActiveAppointmentsList from '../components/appointments/ActiveAppointmentsList/ActiveAppointmentsList.component';
import styled from 'styled-components';
import Footer from '../components/fragments/Footer/Footer.component';
import Navbar from '../components/fragments/Navbar/Navbar.component';

interface AppointmentsProps {
  userId?: number;
}

function Appointments({ userId }: AppointmentsProps) {
  return (
    <>
      <Navbar />
      <PageContainer>
        <div className='appointments'>
          <ActiveAppointmentsList userId={userId} />
        </div>
      </PageContainer>
      <Footer />
    </>
  );
}


export default Appointments;



const PageContainer = styled.div`
  .appointments{
  padding: 10vh 8vw 0;
  min-height: calc(100vh - 55px);
.appointments{
    padding: 10vh 8vw 0;
}
.appointments > div > h2{
    margin: 1em 0 0.5em;
}
.appointments > div > div{
    margin-bottom: 2em;
}

`;


