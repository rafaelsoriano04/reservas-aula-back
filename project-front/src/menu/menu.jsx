import React from 'react';
import '../menu/menu.css'

const SidebarMenu = () => {
    return (
        <div className="sidebar">
            <div className="header-logo">
                <img src="src\img\1.png" alt="Logo de la Universidad Técnica de Ambato" />
                <p>FISEI</p>
            </div>
            <div className="menu-items">
                <a href="#"><i className="fas fa-home"></i>Inicio</a>
                <a href="#"><i className="fas fa-chalkboard-teacher"></i>Aulas-Labs</a>
                <a href="#"><i className="fas fa-clock"></i>Horarios</a>
                <a href="#"><i className="fas fa-calendar-alt"></i>Reservas</a>
            </div>
            <a href="#" className="menu-bottom"><i className="fas fa-sign-out-alt"></i>Cerrar Sesión</a>
        </div>
    );
};

export default SidebarMenu;
