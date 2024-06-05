import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import '../styles/menu.css'
import { useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import Horarios from "./horario";
import Reservas from "./Reservas";
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

const SidebarMenu = () => {
    const navigate = useNavigate();
    const [horariosActive, setHorariosActive] = useState(false);

    const handleLogout = (event) => {
        event.preventDefault();
        navigate('/');
    };

    const handleHorarios = () => {
        setHorariosActive(true);
    }

    const handleReservas = () => {
        setHorariosActive(false);
    }

    return (
        <div className="container p-0 m-0">
            <div className="row">
                <div className="col-md-3">
                    <div className="sidebar">
                        <div className="header-logo">
                            <img src="src/img/1.png" alt="Logo de la Universidad Técnica de Ambato" />
                            <p>FISEI</p>
                        </div>
                        <div className="menu-items">
                            <Link onClick={handleReservas}><i className="fas fa-calendar-alt"></i>Reservas</Link>
                            <Link onClick={handleHorarios}><i className="fas fa-clock"></i>Horarios</Link>
                            <Link ><i className="fas fa-chalkboard-teacher"></i>Aulas-Labs</Link>
                        </div>
                        <a href="/" className="menu-bottom" onClick={handleLogout}><i className="fas fa-sign-out-alt"></i>Cerrar Sesión</a>
                    </div>
                </div>
                <div className="col-md-9">
                    <div className="content">
                        {horariosActive ? <Horarios /> : <Reservas />}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default SidebarMenu;