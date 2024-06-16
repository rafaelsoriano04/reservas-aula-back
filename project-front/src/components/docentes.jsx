import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button, Alert } from "react-bootstrap";
import "../styles/docentes.css";
import axios from "axios";
import { ok, oops, deleteConfirmation } from "../utils/Alerts";

function Docentes() {
  // Variables
  const [selectedRow, setSelectedRow] = useState(null);
  const [docentes, setDocente] = useState([]);
  const [showContextMenu, setShowContextMenu] = useState(false);
  const [formData, setFormData] = useState({
    id: "",
    nombre: "",
    cedula: "",
    apellido: "",
    telefono: "",
  });
  const [isEditing, setIsEditing] = useState(false);
  const [cancel, setCancel] = useState(false);
  const [cedulaError, setCedulaError] = useState("");
  const [telefonoError, setTelefonoError] = useState("");
  const [contextMenuPosition, setContextMenuPosition] = useState({
    top: 0,
    left: 0,
  });

  const generarCodigoCedula = () => {
    const inicialNombre = formData.nombre.charAt(0).toUpperCase();
    const inicialApellido = formData.apellido
    const numeroAleatorio = Math.floor(Math.random() * 100); 
    return `DC${numeroAleatorio}-${inicialNombre}${inicialApellido}`;
  };


  // useEffects
  useEffect(() => {
    document.addEventListener("click", handleDocumentClick);
    return () => {
      document.removeEventListener("click", handleDocumentClick);
    };
  }, []);

  useEffect(() => {
    getDocentes();
  }, []);

  // Funciones
  const crearDocente = async () => {
    
    formData.cedula = generarCodigoCedula();
    console.log (formData.cedula);
      let docente = {
        cedula: formData.cedula,
        nombre: formData.nombre,
        apellido: formData.apellido,
        telefono: formData.telefono,
        direccion: "",
        tipo: "Docente",
      };
      await axios.post(`http://localhost:8080/person`, docente);
      getDocentes();
      setFormData({
        id: "",
        nombre: "",
        cedula: "",
        apellido: "",
        telefono: "",
      });
      Swal.fire({
        title: "Guardado",
        text: "El Docente se guardo",
        icon: "success",
        confirmButtonText: "OK",
      });
  
  };

  const getDocentes = async () => {
    const url = `http://localhost:8080/person/docente`;
    try {
      const response = await axios.get(url);
      setDocente(Array.isArray(response.data) ? response.data : []);
    } catch (error) {
      oops("No se pudo cargar los docentes. Por favor, inténtelo de nuevo.");
      setDocente([]); // Limpia los datos si la petición falla
    }
  };

  const limpiar = async () => {
    setIsEditing(false);
    setCancel(true);
    setFormData({ id: "", nombre: "", cedula: "", apellido: "", telefono: "" });
  };

  const eliminarDocentes = async id => {
    const url = `http://localhost:8080/person/${id}`;
    const isConfirmed = await deleteConfirmation();
    try {
      if (isConfirmed) {
        await axios.delete(url);
        getDocentes();
        ok("Registro eliminado exitosamente.");
      }
    } catch (error) {
      oops(
        "No se pudo eliminar el registro. Es posible que este asociado a un horario."
      );
    }
  };

  const editarDocente = async id => {
    
    const url = `http://localhost:8080/person`;
    let docente = {
      id: formData.id,
      cedula: formData.cedula,
      nombre: formData.nombre,
      apellido: formData.apellido,
      telefono: formData.telefono,
      direccion: "",
      tipo: "Docente",
    };
    try {
      const response = await axios.post(url, docente);
      if (response.status === 200) {
        getDocentes();
        setIsEditing(false);
        setFormData({
          id: "",
          nombre: "",
          cedula: "",
          apellido: "",
          telefono: "",
        });
        ok("Registro actualizado exitosamente.");
      }
    } catch (error) {
      oops("No se pudo actualizar el registro. Por favor, inténtelo de nuevo.");
    }
  };

  // Handlers
  const handleRowClick = (e, docente) => {
    e.stopPropagation();
    setSelectedRow(docente.id);
    setContextMenuPosition({ top: e.pageY, left: e.pageX });
    setShowContextMenu(true);
  };

  const handleDocumentClick = e => {
    if (!e.target.closest(".context-menu") && !e.target.closest("td")) {
      setSelectedRow(null);
      setShowContextMenu(false);
    }
  };

  // Interfaz
  return (
    <div className="container-fluid">
      <div className="content">
        <div className="header">
          <h2>Docentes</h2>
        </div>
        <div className="mt-4">
          <Form id="form-reservas">
            <div className="row">
             
              <div className="col-md-4">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="nombre">Nombre:</Form.Label>
                  <Form.Control
                    type="text"
                    id="nombre"
                    className="form-control"
                    name="nombre"
                    value={formData.nombre}
                    onChange={e =>
                      setFormData({ ...formData, nombre: e.target.value })
                    }
                  />
                </Form.Group>
              </div>
              <div className="col-md-4">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="apellido">Apellido:</Form.Label>
                  <Form.Control
                    type="text"
                    id="apellido"
                    className="form-control"
                    name="apellido"
                    value={formData.apellido}
                    onChange={e =>
                      setFormData({ ...formData, apellido: e.target.value })
                    }
                  />
                </Form.Group>
              </div>
              <div className="col-md-4">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="apellido">Telefono:</Form.Label>
                  <Form.Control
                    type="text"
                    id="telefono"
                    className="form-control"
                    name="telefono"
                    value={formData.telefono}
                    onChange={e =>
                      setFormData({ ...formData, telefono: e.target.value })
                    }
                  />
                </Form.Group>
              </div>
             
            </div>
            <div className="button-group mt-4 text-center">
              {!isEditing ? (
                <Button
                  type="button"
                  className="btn btn-custom"
                  onClick={crearDocente}
                >
                  Crear
                </Button>
              ) : (
                <>
                  <Button
                    type="button"
                    className="btn btn-custom"
                    id="guardar-btn"
                    onClick={editarDocente}
                  >
                    Guardar
                  </Button>
                  <Button
                    type="button"
                    className="btn btn-danger ml-2"
                    onClick={limpiar}
                  >
                    Cancelar
                  </Button>
                </>
              )}
            </div>
          </Form>
          <table className="table table-bordered mt-4">
            <thead>
              <tr>
                <th>Código</th>
                <th>Nombre</th>
                <th>Apellido</th>
              </tr>
            </thead>
            <tbody>
              {docentes.map(docente => (
                <tr key={docente.id} onClick={e => handleRowClick(e, docente)}>
                  <td>{docente.cedula}</td>
                  <td>{docente.nombre}</td>
                  <td>{docente.apellido}</td>
                </tr>
              ))}
            </tbody>
          </table>
          <div
            className="context-menu"
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
                const selectedDocente = docentes.find(
                  d => d.id === selectedRow
                );
                if (selectedDocente) {
                  setFormData({
                    id: selectedDocente.id,
                    cedula: selectedDocente.cedula,
                    nombre: selectedDocente.nombre,
                    apellido: selectedDocente.apellido,
                    telefono: selectedDocente.telefono,
                  });

                  setShowContextMenu(false); // Cierra el menú contextual
                  setIsEditing(true);
                }
              }}
            >
              Editar
            </Button>
            <Button
              variant="custom"
              id="eliminar-btn"
              onClick={() => eliminarDocentes(selectedRow)}
            >
              Eliminar
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Docentes;
