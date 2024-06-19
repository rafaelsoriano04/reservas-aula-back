import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button, Modal } from "react-bootstrap";
import "../styles/usuarios.css";
import axios from "axios";
import { ok, oops, deleteConfirmation } from "../utils/Alerts";

const Usuarios = () => {
  const [isEditing, setIsEditing] = useState(false);
  const [selectedRow, setSelectedRow] = useState(null);
  const [showContextMenu, setShowContextMenu] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [contextMenuPosition, setContextMenuPosition] = useState({
    top: 0,
    left: 0,
  });
  const [nombre, setNombre] = useState("");
  const [inicio, setInicio] = useState("");
  const [fin, setFin] = useState("");
  const [idFeriado, setIdFeriado] = useState("");
  const [feriados, setFeriados] = useState([]);
  const [filtroInicio, setFiltroInicio] = useState();
  const [filtroFin, setFiltroFin] = useState();

  // useEffects
  useEffect(() => {
    document.addEventListener("click", handleDocumentClick);
    return () => {
      document.removeEventListener("click", handleDocumentClick);
    };
  }, []);

  useEffect(() => {
    getFeriados();
  }, []);

  const getFeriados = async () => {
    const url = `http://localhost:8080/feriado`;
    try {
      const response = await axios.get(url);
      setFeriados(response.data);
    } catch (error) {
      if (error.response) {
        const { message } = error.response.data;
        if (message == "No hay feriados") {
          oops(message);
        } else {
          oops("Error al conectar con el servidor.");
        }
      } else {
        oops("Error al conectar con el servidor.");
      }
      setFeriados([]); // Limpia los datos si la petición falla
    }
  };

  const eliminarFeriado = async id => {
    const url = `http://localhost:8080/feriado/${id}`;
    const isConfirmed = await deleteConfirmation();
    try {
      if (isConfirmed) {
        await axios.delete(url);
        getFeriados();
        ok("Registro eliminado exitosamente.");
      }
    } catch (error) {
      oops("No se pudo eliminar el registro. Por favor inténtelo de nuevo.");
    }
  };

  // Handlers
  const handleRowClick = (e, feriado) => {
    e.stopPropagation();
    setSelectedRow(feriado.id);
    setContextMenuPosition({ top: e.pageY, left: e.pageX });
    setShowContextMenu(true);
  };

  const handleDocumentClick = e => {
    if (!e.target.closest(".context-menu") && !e.target.closest("td")) {
      setSelectedRow(null);
      setShowContextMenu(false);
    }
  };

  const cargarFeriados = () => {
    const feriadosFiltrados = feriados.filter(feriado => {
      return (
        (feriado.inicio >= filtroInicio && feriado.inicio <= filtroFin) ||
        (feriado.fechaFin >= filtroInicio && feriado.fechaFin <= filtroFin) ||
        (feriado.inicio <= filtroInicio && feriado.fechaFin >= filtroFin)
      );
    });

    const feriadosOrdenados = feriadosFiltrados.sort((a, b) => {
      return new Date(a.fecha).getTime() - new Date(b.fecha).getTime();
    });

    return feriadosOrdenados.map(feriado => (
      <tr key={feriado.id} onClick={e => handleRowClick(e, feriado)}>
        <td>{feriado.nombre}</td>
        <td>{feriado.inicio}</td>
        <td>{feriado.fin}</td>
      </tr>
    ));
  };

  const limpiar = () => {
    setNombre("");
    setInicio("");
    setFin("");
  };

  const handleCloseModal = () => setShowModal(false);
  const handleShowModal = () => setShowModal(true);

  const saveFeriado = async () => {
    try {
      const feriado = { nombre, inicio, fin };
      await axios.post("http://localhost:8080/feriado", feriado);
      ok("Registro guardado exitosamente.");
      getFeriados();
      handleCloseModal();
      limpiar();
    } catch (error) {
      if (error.response) {
        oops("Error al conectar con el servidor.");
      } else {
        oops("Error al conectar con el servidor.");
      }
    }
  };

  const editFeriado = async () => {
    try {
      const feriado = { nombre, inicio, fin };
      await axios.put(`http://localhost:8080/feriado/${idFeriado}`, feriado);
      getFeriados();
      ok("Registro actualizado exitosamente.");
      handleCloseModal();
      limpiar();
    } catch (error) {
      if (error.response) {
        const { message } = error.response.data;
        if (message == "No existe el usuario") {
          oops(message);
        } else {
          oops("Error al conectar con el servidor.");
        }
      } else {
        oops("Error al conectar con el servidor.");
      }
    }
  };

  const nombreChange = e => {
    setNombre(e.target.value);
  };

  const inicioChange = e => {
    setInicio(e.target.value);
  };

  const finChange = e => {
    setFin(e.target.value); // Actualiza el estado tipo con el valor seleccionado
  };

  const handleFiltroInicio = e => {
    setFiltroInicio(e.target.value);
  };

  const handleFiltroFin = e => {
    setFiltroFin(e.target.value);
  };

  const handleSubmit = e => {
    e.preventDefault();
    if (!isEditing) {
      saveFeriado();
    } else {
      editFeriado();
    }
  };

  // Render
  return (
    <>
      <div>
        <div className="header">
          <h2>Usuarios</h2>
        </div>
        <div className="row mb-3 mt-4 justify-content-center"></div>
        <div className="row justify-content-center mb-2">
          <div className="col-auto">
            <button className="btn" onClick={handleShowModal}>
              Nuevo feriado
            </button>
          </div>
        </div>
        <div className="row mb-0 mt-3 justify-content-center">
          <div className="col-auto d-flex align-items-center">
            <label className="me-2">Desde:</label>
            <input
              type="date"
              value={filtroInicio}
              onChange={handleFiltroInicio}
              style={{ padding: "8px", width: "100%", boxSizing: "border-box" }}
            />
          </div>
        </div>
        <div className="row mb-0 mt-3 justify-content-center">
          <div className="col-auto d-flex align-items-center">
            <label className="me-2">Hasta:</label>
            <input
              type="date"
              value={filtroFin}
              onChange={handleFiltroFin}
              style={{ padding: "8px", width: "100%", boxSizing: "border-box" }}
            />
          </div>
        </div>
        <div className="mt-0">
          <table className="table table-bordered mt-3">
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Fecha de Inicio</th>
                <th>Fecha de Fin</th>
              </tr>
            </thead>
            <tbody>{cargarFeriados()}</tbody>
          </table>
          <div
            className="context-menu"
            id="context-menu"
            style={{
              display:
                showContextMenu && selectedRow !== null ? "block" : "none",
              top: contextMenuPosition.top,
              left: contextMenuPosition.left,
            }}
          >
            <Button
              variant="custom"
              id="editar-btn"
              onClick={() => {
                const selectedFeriado = feriados.find(
                  feriado => feriado.id === selectedRow
                );
                if (selectedFeriado) {
                  setIdFeriado(selectedFeriado.id);
                  setNombre(selectedFeriado.nombre);
                  setInicio(selectedFeriado.inicio);
                  setFin(selectedFeriado.fin);
                  setShowContextMenu(false); // Cierra el menú contextual
                  setIsEditing(true);
                  handleShowModal();
                }
              }}
            >
              Editar
            </Button>
            <Button
              variant="custom"
              id="eliminar-btn"
              onClick={() => eliminarFeriado(selectedRow)}
            >
              Eliminar
            </Button>
          </div>
        </div>
      </div>

      <Modal show={showModal} onHide={handleCloseModal} centered>
        <Modal.Header closeButton>
          <Modal.Title>
            {!isEditing ? "Crear Usuario" : "Editar Usuario"}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmit}>
            <Form.Group className="mb-3" controlId="formBasicNombre">
              <Form.Label>Nombre</Form.Label>
              <Form.Control
                type="text"
                placeholder="Ingrese el nombre del feriado"
                maxLength="40"
                value={nombre}
                onChange={nombreChange}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="formBasicApellido">
              <Form.Label>Fecha de inicio</Form.Label>
              <Form.Control
                type="date"
                value={inicio}
                onChange={inicioChange}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Fecha de fin</Form.Label>
              <Form.Control type="date" value={fin} onChange={finChange} />
            </Form.Group>
            <div className="container d-flex justify-content-center">
              <Button
                className="align-items-center"
                variant="primary"
                type="submit"
              >
                Guardar
              </Button>
            </div>
          </Form>
        </Modal.Body>
      </Modal>
    </>
  );
};

export default Usuarios;
