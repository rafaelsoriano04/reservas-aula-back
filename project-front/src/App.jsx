import { useState } from "react";
import SidebarMenu from "../src/menu/menu";
import Login from "../src/login/login";
import Horarios from '../src/horario/horario';
import Reservas from '../src/componentes/Reservas';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
import "./App.css";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Navigate to="/login" />} />
                <Route path="/login" element={<Login />} />
                <Route path="/menu" element={<SidebarMenu />} />
                <Route path="/horarios" component={Horarios} />
                <Route path="/reservas" component={Reservas} />
            </Routes>
        </Router>
    );
}

export default App;
