import { Navigate } from 'react-router-dom';

interface PrivateRouteProps {
  element: React.ReactElement;
}

function PrivateRoute({ element }: PrivateRouteProps) {
  const auth = localStorage.getItem("token") != null;
  
  return (
    <>
      {auth ? element : <Navigate to="/login" />}
    </>
  );
}
export default PrivateRoute;