import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button } from "react-bootstrap";
import "../styles/docentes.css";
import axios from "axios";
import ReactPaginate from "react-paginate";
import { FaPlus } from "react-icons/fa";
import { ok, oops, deleteConfirmation } from "../utils/Alerts";

function Docentes() {
  // Variables
  const [selectedRow, setSelectedRow] = useState(null);
  const [docentes, setDocente] = useState([]);
  const [showContextMenu, setShowContextMenu] = useState(false);
  const [filtroNombre, setFiltroNombre] = useState("");
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

  // Paginación
  const [paginaActual, setPaginaActual] = useState(0);
  const [itemsPorPagina, setItemsPorPagina] = useState(10);

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
    try {
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
      ok("Registro guardado exitosamente.");
    } catch (error) {
      oops("No se pudo guardar el registro. Por favor, inténtelo de nuevo.");
    }
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

  const handlePageClick = data => {
    setPaginaActual(data.selected);
  };

  const generarCodigoCedula = () => {
    const inicialNombre = formData.nombre.charAt(0).toUpperCase();
    const inicialApellido = formData.apellido.charAt(0).toUpperCase();
    const numeroAleatorio = Math.floor(Math.random() * 100);
    return `DC${numeroAleatorio}-${inicialNombre}${inicialApellido}`;
  };

  const offset = paginaActual * itemsPorPagina;
  const currentPageData = docentes.slice(offset, offset + itemsPorPagina);
  const pageCount = Math.ceil(docentes.length / itemsPorPagina);

  const cargarDocentes = () => {
    const docentesFiltrados = docentes.filter(docente =>
      docente.nombre.toLowerCase().includes(filtroNombre.toLowerCase())
    );

    const docentesOrdenados = docentesFiltrados.sort((a, b) =>
      a.nombre.localeCompare(b.nombre)
    );

    return docentesOrdenados
      .slice(offset, offset + itemsPorPagina)
      .map(docente => (
        <tr
          key={docente.id}
          className={docente.id === selectedRow ? "table-active" : ""}
          onClick={e => handleRowClick(e, docente)}
          style={{ cursor: "pointer" }}
        >
          <td>{docente.cedula}</td>
          <td>{docente.nombre}</td>
          <td>{docente.apellido}</td>
        </tr>
      ));
  };

  return (
    <>
      <div className="header">
        <h2>Docentes</h2>
      </div>
      <div className="mt-4">
        <Button
          className="btn btn-primary d-flex align-items-center justify-content-center"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#collapseForm"
          aria-expanded="false"
          aria-controls="collapseForm"
          style={{
            fontWeight: "bold",
          }}
        >
          <FaPlus style={{ marginRight: "5px" }} />
          Agregar
        </Button>
        <div className="collapse" id="collapseForm">
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
        </div>
        <div className="row mb-3 mt-4 justify-content-center">
          <div className="col-auto d-flex align-items-center">
            <label className="me-2">Buscar:</label>
            <input
              type="text"
              className="form-control"
              placeholder="Nombre"
              value={filtroNombre}
              onChange={e => setFiltroNombre(e.target.value)}
              maxLength={25}
            />
          </div>
        </div>
        <table className="table table-bordered table-hover mt-4">
          <thead>
            <tr>
              <th>Código</th>
              <th>Nombre</th>
              <th>Apellido</th>
            </tr>
          </thead>
          <tbody>
            {currentPageData.length === 0 ? (
              <tr>
                <td colSpan="3">No hay resultados</td>
              </tr>
            ) : (
              cargarDocentes()
            )}
          </tbody>
        </table>
        <ReactPaginate
          previousLabel={"<"}
          nextLabel={">"}
          breakLabel={"..."}
          pageCount={pageCount}
          marginPagesDisplayed={2}
          pageRangeDisplayed={5}
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
          style={{
            display: showContextMenu && selectedRow !== null ? "block" : "none",
            top: contextMenuPosition.top,
            left: contextMenuPosition.left,
          }}
        >
          <Button
            variant="custom"
            id="editar-btn"
            onClick={() => {
              const selectedDocente = docentes.find(d => d.id === selectedRow);
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
    </>
  );
}

export default Docentes;
