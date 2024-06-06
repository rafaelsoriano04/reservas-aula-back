import SidebarMenu from "./components/menu";
import React from "react";
import Login from "./components/login";
import Horarios from "./components/horario";
import Reservas from "./components/Reservas";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "@fortawesome/fontawesome-free/css/all.min.css";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="*" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/menu" element={<SidebarMenu />} />
      </Routes>
    </Router>
  );
}

export default App;
