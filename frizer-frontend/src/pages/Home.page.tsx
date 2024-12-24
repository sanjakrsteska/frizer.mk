import FeaturedSalons from "../components/home/FeaturedSalons/FeaturedSalons.component";
import Navbar from "../components/fragments/Navbar/Navbar.component";
import Footer from "../components/fragments/Footer/Footer.component";
import LandingSection from "../components/home/LandingSection/LandingSection.component";

function Home() {
  return (
    <>
      <Navbar/>
      <LandingSection/>
      <FeaturedSalons/>
      <Footer/>
    </>
  );
}

export default Home;
