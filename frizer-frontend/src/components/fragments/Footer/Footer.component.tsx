import React, { useEffect, useState } from "react";
import styles from "./Footer.module.scss";

function Footer() {
  const [currentYear, setCurrentYear] = useState<string>("");

  useEffect(() => {
    setCurrentYear(() => new Date().getFullYear().toString());
  }, []);

  return (
    <div className={`${styles.footerContainer}`}>
      <p className={`${styles.copyRight}`}>Clippers Duo &copy; {currentYear}</p>
    </div>
  );
}

export default Footer;
