
import { StrictMode } from 'react';
import * as ReactDOM from 'react-dom/client';
import { GlobalContextProvider } from './context/Context';
import { BrowserRouter } from 'react-router-dom';
import './index.css';
import ProtectedWrapper from './guard/ProtectedWrapper';
const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <BrowserRouter>
    <GlobalContextProvider>
      <StrictMode>
        <ProtectedWrapper />
      </StrictMode>
    </GlobalContextProvider>
  </BrowserRouter>
);