import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button, Modal } from "react-bootstrap";
import "../styles/docentes.css";
import axios from "axios";
import ReactPaginate from "react-paginate";
import { FaPlus } from "react-icons/fa";
import { ok, oops, deleteConfirmation, info } from "../utils/Alerts";

function Docentes() {
  // Variables
  const [selectedRow, setSelectedRow] = useState(null);
  const [docentes, setDocentes] = useState([]);
  const [showContextMenu, setShowContextMenu] = useState(false);
  const [filtroNombre, setFiltroNombre] = useState("");
  const [filtroCedula, setFiltroCedula] = useState("");
  const [formData, setFormData] = useState({
    id: "",
    nombre: "",
    cedula: "",
    apellido: "",
    telefono: "",
  });
  const [isEditing, setIsEditing] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [contextMenuPosition, setContextMenuPosition] = useState({
    top: 0,
    left: 0,
  });

  // Paginación
  const [paginaActual, setPaginaActual] = useState(0);
  const [itemsPorPagina] = useState(10);

  useEffect(() => {
    document.addEventListener("click", handleDocumentClick);
    return () => {
      document.removeEventListener("click", handleDocumentClick);
    };
  }, []);

  useEffect(() => {
    getDocentes();
  }, []);

  useEffect(() => {
    if (filtroNombre === "" && filtroCedula === "") {
      getDocentes();
    }
  }, [filtroNombre, filtroCedula]);

  // Funciones
  const crearDocente = async () => {
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
    let url;
    if (!filtroNombre && !filtroCedula) {
      url = `http://localhost:8080/person/docente`;
    } else if (filtroNombre && !filtroCedula) {
      url = `http://localhost:8080/person/docente-nombre/${filtroNombre}`;
    } else if (!filtroNombre && filtroCedula) {
      url = `http://localhost:8080/person/docente-cedula/${filtroCedula}`;
    } else {
      url = `http://localhost:8080/person/docente/${filtroCedula}/${filtroNombre}`;
    }

    try {
      const response = await axios.get(url);
      setDocentes(response.data);
      if (!docentes) {
        info("No hay docentes");
      }
    } catch (error) {
      oops("No se pudo cargar los docentes. Por favor, inténtelo de nuevo.");
      setDocentes([]); // Limpia los datos si la petición falla
    }
  };

  const limpiar = async () => {
    setIsEditing(false);
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

  const editarDocente = async () => {
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
  const handleCloseModal = () => {
    limpiar();
    setShowModal(false);
  };
  const handleShowModal = () => {
    setShowModal(true);
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
    return docentes.slice(offset, offset + itemsPorPagina).map(docente => (
      <tr
        key={docente.id}
        className={docente.id === selectedRow ? "table-active" : ""}
        onClick={e => handleRowClick(e, docente)}
        style={{ cursor: "pointer" }}
      >
        <td>{docente.cedula}</td>
        <td>
          {docente.apellido} {docente.nombre}
        </td>
      </tr>
    ));
  };

  const handleSearch = () => {
    getDocentes();
  };

  const handleRefresh = () => {
    setFiltroCedula("");
    setFiltroNombre("");
  };

  return (
    <>
      <div className="header">
        <h2>Docentes</h2>
      </div>
      <div className="container-sm pe-5 ps-5">
        <div className="row mb-0 mt-3 justify-content-between">
          <div className="col d-flex align-items-center">
            <label className="d-flex align-items-center fw-bold me-4">
              Filtros:
            </label>
            <div className="col-auto d-flex align-items-center">
              <label className="me-2">Cédula:</label>
              <input
                type="text"
                className="form-control"
                placeholder="Cédula"
                value={filtroCedula}
                onChange={e => setFiltroCedula(e.target.value)}
                maxLength={10}
              />
            </div>
            <div className="col-auto d-flex align-items-center ms-4">
            <label className="me-2">Nombres:</label>
              <input
                type="text"
                className="form-control"
                placeholder="Nombre o Apellido"
                value={filtroNombre}
                onChange={e => setFiltroNombre(e.target.value)}
                maxLength={30}
              />
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
              <FaPlus style={{ marginRight: "5px" }} />
              Nuevo Docente
            </button>
          </div>
        </div>
        <div className="mt-4">
          <table className="table table-bordered table-hover table-sm caption-top">
          <caption>Seleccione una fila para ver sus opciones</caption>
            <thead>
              <tr>
                <th style={{ width: "20%" }}>Cédula:</th>
                <th style={{ width: "80%" }}>Nombres</th>
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
                  setShowContextMenu(false); 
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
              onClick={() => eliminarDocentes(selectedRow)}
            >
              Eliminar
            </Button>
          </div>
        </div>
      </div>
      <Modal show={showModal} onHide={handleCloseModal} centered>
        <Modal.Header closeButton>
          <Modal.Title>
            {!isEditing ? "Crear Docente" : "Editar Docente"}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
        <Form id="form-reservas">
            <div className="row">
            <div className="col-12">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="cedula">Cédula:</Form.Label>
                  <Form.Control
                    type="number"
                    id="cedula"
                    className="form-control"
                    name="cedula"
                    value={formData.cedula}
                    placeholder="Ingrese su cédula"
                    onChange={e =>
                      setFormData({ ...formData, cedula: e.target.value })
                    }
                  />
                </Form.Group>
              </div>
              <div className="col-12">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="nombre">Nombre:</Form.Label>
                  <Form.Control
                    type="text"
                    id="nombre"
                    className="form-control"
                    name="nombre"
                    value={formData.nombre}
                    placeholder="Ingrese su nombre"
                    onChange={e =>
                      setFormData({ ...formData, nombre: e.target.value })
                    }
                  />
                </Form.Group>
              </div>
              <div className="col-12">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="apellido">Apellido:</Form.Label>
                  <Form.Control
                    type="text"
                    id="apellido"
                    className="form-control"
                    name="apellido"
                    value={formData.apellido}
                    placeholder="Ingrese su apellido"
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
                  onClick={ () => {
                    crearDocente();
                    handleCloseModal();
                  }}
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
                    onClick={()=>{limpiar();
                      handleCloseModal();
                    }}
                  >
                    Cancelar
                  </Button>
                </>
              )}
            </div>
          </Form>
        </Modal.Body>
      </Modal>
    </>
  );
}

export default Docentes;
