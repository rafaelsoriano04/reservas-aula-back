import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button, Alert } from "react-bootstrap";
import "../styles/AulaLabs.css";
import axios from "axios";
import ReactPaginate from 'react-paginate';
import { ok, oops, deleteConfirmation } from "../utils/Alerts";

function AuLabs() {
  // Variables
  const [selectedRow, setSelectedRow] = useState(null);
  const [bloques, setBloques] = useState([]);
  const [selectedBloque, setSelectedBloque] = useState("1");
  const [selectedTipo, setSelectedTipo] = useState("Aula");
  const [aulas, setAulas] = useState([]);
  const [nombreError, setNombreError] = useState("");
  const [capacidadError, setCapacidadError] = useState("");
  const [laboratorios, setLaboratorios] = useState([]);
  const [cancel, setCancel] = useState(false);
  const [aulasLabsToShow, setAulasLabsToShow] = useState([]);
 
  const [formData, setFormData] = useState({
    id_bloque: "1",
    tipo: "Aula",
    piso: "1",
    nombre: "",
    capacidad: "",
    id: "",
  });
  const [showContextMenu, setShowContextMenu] = useState(false);
  const [contextMenuPosition, setContextMenuPosition] = useState({
    top: 0,
    left: 0,
  });
  const [isEditing, setIsEditing] = useState(false);

  // Paginación
  const [paginaActual, setPaginaActual] = useState(0);
  const [itemsPorPagina, setItemsPorPagina] = useState(10);

  // useEffects
  useEffect(() => {
    getBloques();
    fetchAulasLabs();
  }, []);

  useEffect(() => {
    setAulasLabsToShow(selectedTipo === "Aula" ? aulas : laboratorios);
  }, [selectedTipo, aulas, laboratorios]);

  useEffect(() => {
    fetchAulasLabs();
  }, [selectedBloque]);

  useEffect(() => {
    document.addEventListener("click", handleDocumentClick);
    return () => {
      document.removeEventListener("click", handleDocumentClick);
    };
  }, []);

  // Funciones
  const getBloques = async () => {
    try {
      const response = await axios.get("http://localhost:8080/bloque");
      setBloques(response.data);
    } catch (error) {
      oops("Error al cargar bloques.");
    }
  };

  const crearEspacio = async () => {
    try {
      let espacio = {
        id_bloque: selectedBloque,
        tipo: selectedTipo,
        piso: formData.piso,
        nombre: formData.nombre,
        capacidad: formData.capacidad,
      };
      if (espacio.nombre === "") {
        setNombreError("Ingrese un nombre");
        return;
      }
      if (espacio.capacidad === "") {
        setCapacidadError("Ingrese la capacidad");
        return;
      }
      await axios.post(`http://localhost:8080/espacio`, espacio);
      fetchAulasLabs();
      ok("Registro guardado exitosamente.");
    } catch (error) {
      oops("No se pudo guardar el registro. Por favor, inténtelo de nuevo.");
    }
  };

  const limpiar = async () => {
    setIsEditing(false);
    setCancel(true);
    setFormData({ nombre: "", capacidad: "", id: "" });
  };

  const editarespacio = async () => {
    let espacio = {
      id_bloque: selectedBloque,
      tipo: selectedTipo,
      piso: formData.piso,
      nombre: formData.nombre,
      capacidad: formData.capacidad,
    };

    try {
      const response = await axios.put(
        `http://localhost:8080/espacio/${formData.id}`,
        espacio
      );
      if (response.status === 200) {
        fetchAulasLabs();
        setIsEditing(false);
        setFormData({ nombre: "", capacidad: "", id: "", piso: "1" });
        ok("Registro actualizado exitosamente.");
      }
    } catch (error) {
      oops("No se pudo actualizar el registro. Por favor, inténtelo de nuevo.");
    }
  };

  const eliminarEspacio = async () => {
    if (selectedRow) {
      // Asegúrate de que hay un ID seleccionado
      const url = `http://localhost:8080/espacio/${selectedRow}`; // Uso correcto del ID seleccionado

      const isConfirmed = await deleteConfirmation();
      try {
        if (isConfirmed) {
          await axios.delete(url);
          fetchAulasLabs();
          ok("Registro eliminado exitosamente.");
        }
      } catch (error) {
        oops(
          "No se pudo eliminar el registro. Es posible que este asociado a un horario u reserva."
        );
      }
    }
  };

  const fetchAulasLabs = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/espacio/bloque/${selectedBloque}`
      );
      setAulas(response.data.filter(item => item.tipo === "Aula"));
      setLaboratorios(
        response.data.filter(item => item.tipo === "Laboratorio")
      );
    } catch (error) {
      oops("No se pudo cargar los espacios. Por favor, inténtelo de nuevo.");
      setAulas([]);
      setLaboratorios([]);
    }
  };

  const handlePageClick = (data) => {
    setPaginaActual(data.selected);
  };

  const offset = paginaActual * itemsPorPagina;
  const currentPageData = aulasLabsToShow.slice(offset, offset + itemsPorPagina);
  const pageCount = Math.ceil(aulasLabsToShow.length / itemsPorPagina);

  // Handlers
  const handleBloqueChange = event => {
    setSelectedBloque(event.target.value);
  };

  const handleTipoChange = event => {
    setSelectedTipo(event.target.value);
  };

  const handleRowClick = (e, aulasLabs) => {
    e.stopPropagation();
    setSelectedRow(aulasLabs.id);
    setContextMenuPosition({ top: e.pageY, left: e.pageX });
    setShowContextMenu(true);
  };

  const handleDocumentClick = e => {
    if (!e.target.closest(".context-menu") && !e.target.closest("td")) {
      setSelectedRow(null);
      setShowContextMenu(false);
    }
  };

  // Render
  return (
    <div className="container-fluid">
      <div className="content">
        <div className="header">
          <h2>Aulas/Laboratorios</h2>
        </div>
        <div className="mt-4">
          <h5
            className="nuevo-horario text-center"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#collapseForm"
            aria-expanded="false"
            aria-controls="collapseForm"
            style={{
              cursor: "pointer",
              textDecoration: "underline",
              fontWeight: "bold",
            }}
            onMouseOver={({ target }) =>
              (target.style.textDecoration = "underline")
            }
            onMouseOut={({ target }) =>
              (target.style.textDecoration = "none")
            }
          >
            Agregar Aulas/Laboratorios
          </h5>
          <div className="collapse" id="collapseForm">
            <Form id="form-reservas">
              <div className="row">
                <div className="col-md-6">
                  <Form.Group className="form-group">
                    <Form.Label htmlFor="bloque">Bloque:</Form.Label>
                    <Form.Select
                      id="bloque"
                      className="form-control"
                      value={selectedBloque}
                      onChange={handleBloqueChange}
                    >
                      {bloques.map(bloque => (
                        <option key={bloque.id} value={bloque.id}>
                          {bloque.nombre}
                        </option>
                      ))}
                    </Form.Select>
                  </Form.Group>
                </div>
                <div className="col-md-6">
                  <Form.Group className="form-group">
                    <Form.Label htmlFor="tipo">Tipo:</Form.Label>
                    <Form.Select
                      id="tipo"
                      className="form-control"
                      value={selectedTipo}
                      onChange={handleTipoChange}
                    >
                      <option value="Aula">Aula</option>
                      <option value="Laboratorio">Laboratorio</option>
                    </Form.Select>
                  </Form.Group>
                </div>
              </div>
              <div className="row">
                <div className="col-md-6">
                  <Form.Group className="form-group">
                    <Form.Label htmlFor="nombre">Nombre:</Form.Label>
                    <Form.Control
                      type="text"
                      id="nombre"
                      className="form-control"
                      name="nombre"
                      value={formData.nombre}
                      onChange={e => {
                        setFormData({ ...formData, nombre: e.target.value });
                        setNombreError("");
                      }}
                    />
                    {nombreError && <Alert variant="danger">{nombreError}</Alert>}
                  </Form.Group>
                </div>
                <div className="col-md-6">
                  <Form.Group className="form-group">
                    <Form.Label htmlFor="piso">Piso:</Form.Label>
                    <Form.Select
                      id="piso"
                      className="form-control"
                      name="piso"
                      value={formData.piso}
                      onChange={e =>
                        setFormData({ ...formData, piso: e.target.value })
                      }
                    >
                      <option value="1">Piso 1</option>
                      <option value="2">Piso 2</option>
                      <option value="3">Piso 3</option>
                      <option value="4">Piso 4</option>
                      <option value="5">Piso 5</option>
                    </Form.Select>
                  </Form.Group>
                </div>
                <div className="col-md-6">
                  <Form.Group className="form-group">
                    <Form.Label htmlFor="capacidad">Capacidad:</Form.Label>
                    <Form.Control
                      type="number"
                      id="capacidad"
                      className="form-control"
                      name="capacidad"
                      min="1"
                      max="100"
                      value={formData.capacidad}
                      onChange={e => {
                        setFormData({ ...formData, capacidad: e.target.value });
                        setCapacidadError("");
                      }}
                    />
                    {capacidadError && (
                      <Alert variant="danger">{capacidadError}</Alert>
                    )}
                  </Form.Group>
                </div>
              </div>
              <div className="button-group mt-4 text-center">
                {!isEditing ? (
                  <Button
                    type="button"
                    className="btn btn-custom"
                    onClick={crearEspacio}
                  >
                    Crear
                  </Button>
                ) : (
                  <>
                    <Button
                      type="button"
                      className="btn btn-custom"
                      id="guardar-btn"
                      onClick={() => editarespacio()}
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
          <table className="table table-bordered mt-4">
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Piso</th>
                <th>Capacidad</th>
                <th>Bloque</th>
                <th>Tipo</th>
              </tr>
            </thead>
            <tbody>
              {currentPageData.length === 0 ? (
                <tr>
                  <td colSpan="3">No hay resultados</td>
                </tr>
              ) : (
                currentPageData.map(aulaLab => (
                  <tr key={aulaLab.id} onClick={e => handleRowClick(e, aulaLab)}>
                    <td>{aulaLab.nombre}</td>
                    <td>{aulaLab.piso}</td>
                    <td>{aulaLab.capacidad}</td>
                  </tr>
                ))
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
                const selectedAulaLab = aulasLabsToShow.find(
                  d => d.id === selectedRow
                );
                if (selectedAulaLab) {
                  setFormData({
                    id_bloque: selectedAulaLab.id_bloque,
                    tipo: selectedAulaLab.tipo,
                    piso: selectedAulaLab.piso,
                    nombre: selectedAulaLab.nombre,
                    capacidad: selectedAulaLab.capacidad,
                    id: selectedAulaLab.id,
                  });
                  setIsEditing(true);
                  setShowContextMenu(false);
                }
              }}
            >
              Editar
            </Button>
            <Button
              variant="custom"
              id="eliminar-btn"
              onClick={() => eliminarEspacio()}
            >
              Eliminar
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AuLabs;
