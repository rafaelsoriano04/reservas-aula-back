import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Nav, Row, Col } from "react-bootstrap";
import "../styles/menu.css";
import "bootstrap/dist/css/bootstrap.min.css";
import Horarios from "./Horarios";
import Reservas from "./Reservas";
import Espacios from "./Espacios";
import Usuarios from "./Usuarios";
import Materias from "./Materias";
import Feriados from "./Feriados";
import Docentes from "./Docentes";

const Menu = () => {
  const navigate = useNavigate();
  const [activeComponent, setActiveComponent] = useState("reservas");
  const [tipoUsuario, setTipoUsuario] = useState("");

  useEffect(() => {
    const dato = localStorage.getItem("tipoUsuario");
    if (dato) {
      setTipoUsuario(dato);
    }
  }, []);

  const handleLogout = event => {
    event.preventDefault();
    localStorage.removeItem("tipoUsuario");
    localStorage.removeItem("jwtToken");

    navigate("/");
  };

  const renderActiveComponent = () => {
    switch (activeComponent) {
      case "horarios":
        return <Horarios />;
      case "espacios":
        return <Espacios />;
      case "docentes":
        return <Docentes />;
      case "materia":
        return <Materias />;
      case "usuarios":
        return <Usuarios />;
      case "feriados":
        return <Feriados />;
      default:
        return <Reservas />;
    }
  };

  const showUsersOption = () => {
    if (tipoUsuario === "Administrador") {
      return (
        <Nav.Item>
          <Nav.Link onClick={() => setActiveComponent("usuarios")}>
            <i className="fas fa-users me-2"></i>Usuarios
          </Nav.Link>
        </Nav.Item>
      );
    }
  };

  return (
    <Row className="vh-100">
      <Col xs={2} className="sidebar">
        <Nav className="flex-column sticky-top bg-light h-100">
          <div className="sidebar-sticky d-flex flex-column h-100">
            <div className="header-logo">
              <img src="src/img/1.png" alt="Logo uta" className="mb-4" />
            </div>
            <Nav.Item>
              <Nav.Link onClick={() => setActiveComponent("reservas")}>
                <i className="fas fa-calendar-alt me-2"></i>Reservas
              </Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link onClick={() => setActiveComponent("horarios")}>
                <i className="fas fa-clock me-2"></i>Horarios
              </Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link onClick={() => setActiveComponent("espacios")}>
                <i className="fas fa-chalkboard-teacher me-2"></i>Aulas-Labs
              </Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link onClick={() => setActiveComponent("docentes")}>
                <i className="fas fa-user me-2"></i>Docentes
              </Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link onClick={() => setActiveComponent("materias")}>
                <i className="fas fa-book me-2"></i>Materia
              </Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link onClick={() => setActiveComponent("feriados")}>
                <i className="fas fa-umbrella-beach me-2"></i>Feriados
              </Nav.Link>
            </Nav.Item>
            {showUsersOption()}
            <div className="mt-auto">
              <Nav.Item>
                <Nav.Link onClick={handleLogout}>
                  <i className="fas fa-sign-out-alt me-2"></i>Cerrar Sesión
                </Nav.Link>
              </Nav.Item>
            </div>
          </div>
        </Nav>
      </Col>
      <Col xs={10} className="main-content">
        {renderActiveComponent()}
      </Col>
    </Row>
  );
};

export default Menu;
