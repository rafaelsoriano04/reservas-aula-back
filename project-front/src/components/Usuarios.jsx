import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button, Modal } from "react-bootstrap";
import "../styles/usuarios.css";
import axios from "axios";
import { ok, oops, deleteConfirmation, info } from "../utils/Alerts";
import ReactPaginate from "react-paginate";

const Usuarios = () => {
  const [username, setUsername] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [tipo, setTipo] = useState("");
  const [idUsuario, setIdUsuario] = useState("");
  const [filtroUsername, setFiltroUsername] = useState("");
  const [filtroTipo, setFiltroTipo] = useState("");
  const [isEditing, setIsEditing] = useState(false);
  const [selectedRow, setSelectedRow] = useState(null);
  const [showContextMenu, setShowContextMenu] = useState(false);
  const [usuarios, setUsuarios] = useState([]);
  const [showModal, setShowModal] = useState(false);

  const [contextMenuPosition, setContextMenuPosition] = useState({
    top: 0,
    left: 0,
  });

  // Paginación
  const [paginaActual, setPaginaActual] = useState(0);
  const [itemsPorPagina] = useState(10);

  // useEffects
  useEffect(() => {
    document.addEventListener("click", handleDocumentClick);
    return () => {
      document.removeEventListener("click", handleDocumentClick);
    };
  }, []);

  useEffect(() => {
    if (filtroUsername === "" && filtroTipo === "") {
      getUsuarios();
    }
  }, [filtroUsername, filtroTipo]);

  const getUsuarios = async () => {
    let url;
    if (filtroUsername && filtroTipo) {
      url = `http://localhost:8080/usuario/filter/${filtroTipo}/${filtroUsername}`;
    } else if (!filtroUsername && filtroTipo) {
      url = `http://localhost:8080/usuario/filter-tipo/${filtroTipo}`;
    } else if (filtroUsername && filtroTipo === "Todos") {
      url = `http://localhost:8080/usuario/filter-username/${filtroUsername}`;
    } else {
      url = `http://localhost:8080/usuario`;
    }

    try {
      const response = await axios.get(url);
      setUsuarios(response.data);
    } catch (error) {
      if (error.response) {
        const { message } = error.response.data;
        if (message === "No hay usuarios") {
          info(message);
        } else {
          oops("Error al conectar con el servidor.");
        }
      } else {
        oops("Error al conectar con el servidor.");
      }
      setUsuarios([]); // Limpia los datos si la petición falla
    }
  };

  const eliminarUsuario = async id => {
    const url = `http://localhost:8080/usuario/${id}`;
    const isConfirmed = await deleteConfirmation();
    try {
      if (isConfirmed) {
        await axios.delete(url);
        getUsuarios();
        ok("Registro eliminado exitosamente.");
      }
    } catch (error) {
      oops("No se pudo eliminar el registro. Por favor inténtelo de nuevo.");
    }
  };

  const offset = paginaActual * itemsPorPagina;
  const currentPageData = usuarios.slice(offset, offset + itemsPorPagina);
  const pageCount = Math.ceil(usuarios.length / itemsPorPagina);

  const cargarUsuarios = () => {
    return currentPageData.map(usuario => (
      <tr key={usuario.id} onClick={e => handleRowClick(e, usuario)}>
        <td>{usuario.username}</td>
        <td>{usuario.tipo}</td>
      </tr>
    ));
  };

  const handlePageClick = data => {
    setPaginaActual(data.selected);
  };

  const limpiar = () => {
    setUsername("");
    setTipo("");
    setNewPassword("");
  };

  const saveUsuario = async () => {
    try {
      const usuario = { username, newPassword, tipo };
      await axios.post("http://localhost:8080/usuario", usuario);
      ok("Registro guardado exitosamente.");
      getUsuarios();
      handleCloseModal();
      limpiar();
    } catch (error) {
      if (error.response) {
        const { message } = error.response.data;
        if (message == "El usuario ya existe") {
          oops(message);
        } else {
          oops("Error al conectar con el servidor.");
        }
      } else {
        oops("Error al conectar con el servidor.");
      }
    }
  };

  const editUsuario = async () => {
    try {
      const usuario = { username, newPassword, tipo };
      await axios.put(`http://localhost:8080/usuario/${idUsuario}`, usuario);
      getUsuarios();
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

  // Handlers
  const handleRowClick = (e, usuario) => {
    e.stopPropagation();
    setSelectedRow(usuario.id);
    setContextMenuPosition({ top: e.pageY, left: e.pageX });
    setShowContextMenu(true);
  };

  const handleDocumentClick = e => {
    if (!e.target.closest(".context-menu") && !e.target.closest("td")) {
      setSelectedRow(null);
      setShowContextMenu(false);
    }
  };

  const handleCloseModal = () => setShowModal(false);

  const handleShowModal = () => setShowModal(true);

  const handleUsernameChange = e => {
    setUsername(e.target.value);
  };

  const handleNewPassword = e => {
    setNewPassword(e.target.value);
  };

  const handleTipoChange = e => {
    setTipo(e.target.value); // Actualiza el estado tipo con el valor seleccionado
  };

  const handleFiltroUsername = e => {
    setFiltroUsername(e.target.value);
  };

  const handleFiltroTipo = e => {
    setFiltroTipo(e.target.value);
  };

  const handleSubmit = e => {
    e.preventDefault();
    if (!isEditing) {
      saveUsuario();
    } else {
      editUsuario();
    }
  };

  const handleShowPassword = () => {
    if (!isEditing) {
      return (
        <Form.Group className="mb-3" controlId="formBasicApellido">
          <Form.Label>Contraseña</Form.Label>
          <Form.Control
            type="password"
            placeholder="Ingrese la contraseña"
            maxLength="30"
            value={newPassword}
            onChange={handleNewPassword}
            required
          />
        </Form.Group>
      );
    }
  };

  const handleSearch = () => {
    getUsuarios();
  };

  const handleRefresh = () => {
    setFiltroUsername("");
    setFiltroTipo("");
  };

  // Render
  return (
    <>
      <div>
        <div className="header">
          <h2>Usuarios</h2>
        </div>
        <div className="row mb-0 mt-3 justify-content-between">
          <div className="col d-flex align-items-center">
            <label className="d-flex align-items-center fw-bold me-4">
              Filtros:
            </label>
            <div className="col-auto d-flex align-items-center">
              <label className="me-2">Nombre:</label>
              <input
                type="text"
                className="form-control"
                placeholder="Username"
                value={filtroUsername}
                onChange={handleFiltroUsername}
                maxLength={30}
              />
            </div>
            <div className="col-auto d-flex align-items-center ms-4">
              <label className="me-2">Tipo:</label>
              <select
                className="form-select"
                value={filtroTipo}
                onChange={handleFiltroTipo}
              >
                <option value="">Todos</option>
                <option value="Administrador">Administrador</option>
                <option value="Usuario">Usuario</option>
              </select>
            </div>
            <div className="col-auto d-flex align-items-center ms-4">
              <button className="btn" onClick={handleSearch}>
                <i className="fas fa-search"></i>
              </button>
            </div>
            <div className="col-auto d-flex align-items-center ms-1">
              <button className="btn" onClick={handleRefresh}>
                <i className="fas fa-refresh"></i>
              </button>
            </div>
          </div>
          <div className="col-auto">
            <button
              className="btn"
              onClick={() => {
                setIsEditing(false);
                handleShowModal();
              }}
            >
              Nuevo usuario
            </button>
          </div>
        </div>
        <div className="mt-0">
          <table className="table table-bordered mt-3">
            <thead>
              <tr>
                <th>Username</th>
                <th>Tipo</th>
              </tr>
            </thead>
            <tbody>{cargarUsuarios()}</tbody>
          </table>
          <ReactPaginate
            previousLabel={"<"}
            nextLabel={">"}
            breakLabel={"..."}
            pageCount={pageCount}
            marginPagesDisplayed={2}
            pageRangeDisplayed={3}
            onPageChange={handlePageClick}
            containerClassName={"pagination"}
            activeClassName={"active"}
            pageClassName={"page-item"}
            pageLinkClassName={"page-link"}
            previousClassName={"page-item"}
            previousLinkClassName={"page-link"}
            nextClassName={"page-item"}
            nextLinkClassName={"page-link"}
            breakClassName={"page-item"}
            breakLinkClassName={"page-link"}
          />

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
                const selectedUsuario = usuarios.find(
                  usuario => usuario.id === selectedRow
                );
                if (selectedUsuario) {
                  setIdUsuario(selectedUsuario.id);
                  setUsername(selectedUsuario.username);
                  setTipo(selectedUsuario.tipo);
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
              onClick={() => eliminarUsuario(selectedRow)}
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
              <Form.Label>Username</Form.Label>
              <Form.Control
                type="text"
                placeholder="Ingrese el nombre de usuario"
                maxLength="30"
                value={username}
                onChange={handleUsernameChange}
                required
              />
            </Form.Group>
            {handleShowPassword()}
            <Form.Group className="mb-3">
              <Form.Label>Tipo</Form.Label>
              <Form.Select
                placeholder="Seleccione un tipo"
                value={tipo}
                onChange={handleTipoChange} // Asegúrate de pasar la referencia de la función
                required
              >
                <option value="">Seleccione un tipo</option>
                <option value="Administrador">Administrador</option>
                <option value="Usuario">Usuario</option>
              </Form.Select>
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
