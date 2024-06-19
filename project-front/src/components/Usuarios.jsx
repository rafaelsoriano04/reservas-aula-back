import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button, Modal } from "react-bootstrap";
import "../styles/usuarios.css";
import axios from "axios";
import { ok, oops, deleteConfirmation } from "../utils/Alerts";

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

  // useEffects
  useEffect(() => {
    document.addEventListener("click", handleDocumentClick);
    return () => {
      document.removeEventListener("click", handleDocumentClick);
    };
  }, []);

  useEffect(() => {
    getUsuarios();
  }, []);

  const getUsuarios = async () => {
    const url = `http://localhost:8080/usuario`;
    try {
      const response = await axios.get(url);
      setUsuarios(response.data);
    } catch (error) {
      if (error.response) {
        const { message } = error.response.data;
        if (message == "No hay usuarios") {
          oops(message);
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

  const cargarUsuarios = () => {
    const usuariosFiltrados = usuarios.filter(usuario => {
      return (
        (filtroTipo === "" || usuario.tipo === filtroTipo) &&
        (filtroUsername === "" ||
          usuario.username.toLowerCase().includes(filtroUsername.toLowerCase()))
      );
    });

    const usuariosOrdenados = usuariosFiltrados.sort((a, b) =>
      a.username.localeCompare(b.username)
    );

    return usuariosOrdenados.map(usuario => (
      <tr key={usuario.id} onClick={e => handleRowClick(e, usuario)}>
        <td>{usuario.username}</td>
        <td>{usuario.tipo}</td>
      </tr>
    ));
  };

  const limpiar = () => {
    setUsername("");
    setTipo("");
    setNewPassword("");
  };

  const handleCloseModal = () => setShowModal(false);
  const handleShowModal = () => setShowModal(true);

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

  // Render
  return (
    <div className="container-fluid">
      <div className="content">
        <div className="header ">
          <h2>Usuarios</h2>
        </div>
        <div className="row mb-3 mt-4 justify-content-center"></div>
        <div className="row justify-content-center mb-2">
          <div className="col-auto">
            <button className="btn" onClick={handleShowModal}>
              Nuevo usuario
            </button>
          </div>
        </div>
        <div className="row mb-0 mt-3 justify-content-center">
          <div className="col-auto d-flex align-items-center">
          <label className="me-2">Buscar:</label>
            <input
              type="text"
              className="form-control"
              placeholder="Username"
              value={filtroUsername}
              onChange={handleFiltroUsername}
              maxLength={30}
            />
          </div>
          <div className="col-auto d-flex align-items-center">
            <label className="me-2">Estado:</label>
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
                  // setFormData({
                  //   id: selectedUsuario.id,
                  //   nombre: selectedUsuario.nombre,
                  //   carrera: selectedUsuario.carrera,
                  // });
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
    </div>
  );
};

export default Usuarios;
