import React from "react";
import ReactDOM from "react-dom/client";
import "bootstrap/dist/js/bootstrap.min.js";
import Reservas from "./componentes/Reservas";
import LabReservations from "./componentes/Reservas";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <LabReservations />
  </React.StrictMode>,
);
