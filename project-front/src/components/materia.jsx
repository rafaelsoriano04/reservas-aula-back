import React, { useState, useEffect, useRef } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button } from "react-bootstrap";
import "../styles/materias.css";
import axios from "axios";
import { FaPlus } from 'react-icons/fa';
import ReactPaginate from 'react-paginate';
import { ok, oops, deleteConfirmation } from "../utils/Alerts";

function Materias() {
  const [selectedRow, setSelectedRow] = useState(null);
  const [showContextMenu, setShowContextMenu] = useState(false);
  const [materias, setMateria] = useState([]);
  const [formData, setFormData] = useState({ id: "", nombre: "", carrera: "" });
  const [isEditing, setIsEditing] = useState(false);
  const [cancel, setCancel] = useState(false);
  const [filtroNombre, setFiltroNombre] = useState("");
const [filtroCarrera, setFiltroCarrera] = useState("");

  const [contextMenuPosition, setContextMenuPosition] = useState({
    top: 0,
    left: 0,
  });
  const [carreras] = useState([
    { nombre: "Ingeniería en Software" },
    { nombre: "Ingeniería Industrial" },
    { nombre: "Ingeniería en Telecomunicaciones" },
    { nombre: "Ingeniería en Tecnologías de la Información" },
    { nombre: "Ingeniería en Automatización y Robotica" },
  ]);
  const carreraRef = useRef(null);

  const [selectedCarrera, setSelectedCarrera] = useState("");
  const handleFiltroNombreChange = (e) => {
    setFiltroNombre(e.target.value);
  };
  
  const handleFiltroCarreraChange = (e) => {
    setFiltroCarrera(e.target.value);
  };

  useEffect(() => {
    getMaterias();
  }, [filtroNombre, filtroCarrera]);
  
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
    getMaterias();
  }, []);

  const getMaterias = async () => {
    const url = `http://localhost:8080/materia/todos`;
    try {
      const response = await axios.get(url);
      const data = Array.isArray(response.data) ? response.data : [];
      const filteredData = data.filter((materia) => {
        return (
          (filtroNombre === "" || materia.nombre.toLowerCase().includes(filtroNombre.toLowerCase())) &&
          (filtroCarrera === "" || materia.carrera === filtroCarrera)
        );
      });
      setMateria(filteredData);
    } catch (error) {
      oops("Error al cargar materias.");
      setMateria([]); // Limpia los datos si la petición falla
    }
  };
  

  const eliminarMateria = async id => {
    const url = `http://localhost:8080/materia/${id}`;
    const isConfirmed = await deleteConfirmation();
    try {
      if (isConfirmed) {
        await axios.delete(url);
        getMaterias();
        ok("Registro eliminado exitosamente.");
      }
    } catch (error) {
      oops(
        "No se pudo eliminar el registro. Es posible que este asociado a un horario."
      );
    }
  };

  const limpiar = () => {
    setIsEditing(false);
    setCancel(true);
    setFormData({ id: "", nombre: "", carrera: "" });
    setSelectedCarrera(""); // Limpiar selección de carrera
  };

  const guardarMateria = async () => {
    if (selectedCarrera === "") {
      carreraRef.current.focus();
      return;
    }

    const url = `http://localhost:8080/materia`;
    try {
      let materia = {
        nombre: formData.nombre,
        carrera: selectedCarrera
      };
      await axios.post(url, materia);
      getMaterias();
      setFormData({ id: "", nombre: "", carrera: "" });
      setSelectedCarrera(""); // Limpiar selección de carrera
      ok("Registro guardado exitosamente.");
    } catch (error) {
      oops("No se pudo guardar el registro. Por favor, inténtelo de nuevo.");
    }
  };

  const editarMateria = async () => {
    const url = `http://localhost:8080/materia`;
    if (selectedCarrera === '') {
      carreraRef.current.focus();
      return;
    }
    try {
      let materia = {
        id: formData.id,
        nombre: formData.nombre,
        carrera: selectedCarrera
      };
      const response = await axios.post(url, materia);
      if (response.status === 200) {
        getMaterias();
        setIsEditing(false);
        setCancel(false);
        setFormData({ id: "", nombre: "", carrera: "" });
        setSelectedCarrera(""); // Limpiar selección de carrera
        ok("Registro actualizado exitosamente.");
      }
    } catch (error) {
      oops("No se pudo actualizar el registro. Por favor, inténtelo de nuevo.");
    }
  };

  const handleRowClick = (e, materia) => {
    e.stopPropagation();
    setSelectedRow(materia.id);
    setContextMenuPosition({ top: e.pageY, left: e.pageX });
    setShowContextMenu(true);
  };

  const handleDocumentClick = e => {
    if (!e.target.closest(".context-menu") && !e.target.closest("td")) {
      setSelectedRow(null);
      setShowContextMenu(false);
    }
  };

  const handlePageClick = (data) => {
    setPaginaActual(data.selected);
  };

  const offset = paginaActual * itemsPorPagina;
  const currentPageData = materias.slice(offset, offset + itemsPorPagina);
  const pageCount = Math.ceil(materias.length / itemsPorPagina);

  const cargarMaterias = () => {
    return currentPageData.map((materia) => (
      <tr
        key={materia.id}
        className={materia.id === selectedRow ? "table-active" : ""}
        onClick={(e) => handleRowClick(e, materia)}
        style={{ cursor: "pointer" }}
      >
        <td>{materia.nombre}</td>
        <td>{materia.carrera}</td>
      </tr>
    ));
  };

  return (
    <>
        <div className="header">
          <h2>Materias</h2>
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
            <FaPlus style={{ marginRight: '5px' }} />
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
                      onChange={(e) =>
                        setFormData({ ...formData, nombre: e.target.value })
                      }
                    />
                  </Form.Group>
                </div>
                <div className="col-md-4">
                  <Form.Group className="form-group">
                    <Form.Label htmlFor="carrera">Carrera:</Form.Label>
                    <Form.Select
                      id="carrera"
                      className="form-control"
                      value={selectedCarrera}
                      ref={carreraRef}
                      onChange={(e) => setSelectedCarrera(e.target.value)}
                    >
                      <option value="">Seleccione una opción</option>
                      {carreras.map((carrera) => (
                        <option key={carrera.nombre} value={carrera.nombre}>
                          {carrera.nombre}
                        </option>
                      ))}
                    </Form.Select>
                  </Form.Group>
                </div>
              </div>
              <div className="button-group mt-4 text-center">
                {!isEditing ? (
                  <Button
                    type="button"
                    className="btn btn-custom"
                    onClick={() => guardarMateria()}
                  >
                    Crear
                  </Button>
                ) : (
                  <>
                    <Button
                      type="button"
                      className="btn btn-custom"
                      id="guardar-btn"
                      onClick={() => editarMateria(selectedRow)}
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
                onChange={(e) => setFiltroNombre(e.target.value)}
                maxLength={30}
              />
            </div>
            <div className="col-auto d-flex align-items-center">
              <label className="me-2">Carrera:</label>
              <select
                className="form-select"
                value={filtroCarrera}
                onChange={(e) => setFiltroCarrera(e.target.value)}
              >
                <option value="">Todos</option>
                {carreras.map((carrera) => (
                  <option key={carrera.nombre} value={carrera.nombre}>
                    {carrera.nombre}
                  </option>
                ))}
              </select>
            </div>
          </div>
          <table className="table table-bordered mt-4">
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Carrera</th>
              </tr>
            </thead>
            <tbody>
              {currentPageData.length === 0 ? (
                <tr>
                  <td colSpan="2">No hay resultados</td>
                </tr>
              ) : (
                cargarMaterias()
              )}
            </tbody>
          </table>
          <ReactPaginate
            previousLabel={'<'}
            nextLabel={'>'}
            breakLabel={'...'}
            pageCount={pageCount}
            marginPagesDisplayed={2}
            pageRangeDisplayed={5}
            onPageChange={handlePageClick}
            containerClassName={'pagination'}
            activeClassName={'active'}
            pageClassName={'page-item'}
            pageLinkClassName={'page-link'}
            previousClassName={'page-item'}
            previousLinkClassName={'page-link'}
            nextClassName={'page-item'}
            nextLinkClassName={'page-link'}
            breakClassName={'page-item'}
            breakLinkClassName={'page-link'}
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
                const selectedMateria = materias.find(
                  (m) => m.id === selectedRow
                );
                if (selectedMateria) {
                  setFormData({
                    id: selectedMateria.id,
                    nombre: selectedMateria.nombre,
                    carrera: selectedMateria.carrera,
                  });
                  setSelectedCarrera(selectedMateria.carrera);
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
              onClick={() => eliminarMateria(selectedRow)}
            >
              Eliminar
            </Button>
          </div>
        </div>
        </>
  );
  
  }
  
  export default Materias;
  