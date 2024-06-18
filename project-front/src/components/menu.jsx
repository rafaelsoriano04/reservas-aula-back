import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "../styles/menu.css";
import { useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import Horarios from "./horario";
import Reservas from "./Reservas";
import Aulas from "./AuLabas";
import Docentes from "./docentes";
import Usuarios from "./Usuarios";
import Materia from "./materia";

const SidebarMenu = () => {
  const navigate = useNavigate();
  const [activeComponent, setActiveComponent] = useState("reservas");
  const [tipoUsuario, setTipoUsuario] = useState("");

  useEffect(() => {
    const dato = localStorage.getItem("tipoUsuario");
    if (dato) {
      setTipoUsuario(dato);
    }
  });

  const handleLogout = event => {
    event.preventDefault();
    localStorage.removeItem("tipoUsuario");
    navigate("/");
  };

  const renderActiveComponent = () => {
    switch (activeComponent) {
      case "horarios":
        return <Horarios />;
      case "aulabs":
        return <Aulas />;
      case "docentes":
        return <Docentes />;
      case "materia":
        return <Materia />;
      case "usuarios":
        return <Usuarios />;
      case "reservas":
      default:
        return <Reservas />;
    }
  };

  const showUsersOption = () => {
    if (tipoUsuario === "Administrador") {
      return (
        <Link onClick={() => setActiveComponent("usuarios")}>
          <i className="fas fa-users"></i>Usuarios
        </Link>
      );
    }
  };

  return (
    <div className="container p-0 m-0">
      <div className="row">
        <div className="col-md-3">
          <div className="sidebar">
            <div className="header-logo">
              <img
                src="src/img/1.png"
                alt="Logo de la Universidad Técnica de Ambato"
              />
              <p>FISEI</p>
            </div>
            <div className="menu-items">
              <Link onClick={() => setActiveComponent("reservas")}>
                <i className="fas fa-calendar-alt"></i>Reservas
              </Link>
              <Link onClick={() => setActiveComponent("horarios")}>
                <i className="fas fa-clock"></i>Horarios
              </Link>
              <Link onClick={() => setActiveComponent("aulabs")}>
                <i className="fas fa-chalkboard-teacher"></i>Aulas-Labs
              </Link>
              <Link onClick={() => setActiveComponent("docentes")}>
                <i className="fas fa-user"></i>Docentes
              </Link>
              <Link onClick={() => setActiveComponent("materia")}>
                <i className="fas fa-book"></i>Materia
              </Link>
              {showUsersOption()}
            </div>
            <a href="/" className="menu-bottom" onClick={handleLogout}>
              <i className="fas fa-sign-out-alt"></i>Cerrar Sesión
            </a>
          </div>
        </div>
        <div className="col-md-9">
          <div className="content">{renderActiveComponent()}</div>
        </div>
      </div>
    </div>
  );
};

export default SidebarMenu;
