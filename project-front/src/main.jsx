import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import "bootstrap/dist/js/bootstrap.min.js";
import LabReservations from "./componentes/Reservas.jsx";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <LabReservations />
  </React.StrictMode>,
);
