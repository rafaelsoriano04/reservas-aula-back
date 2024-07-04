import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button, Alert } from "react-bootstrap";
import "../styles/AulaLabs.css";
import axios from "axios";
import ReactPaginate from "react-paginate";
import { FaPlus } from "react-icons/fa";
import { ok, oops, deleteConfirmation, info } from "../utils/Alerts";

function AuLabs() {
  // Variables \
  const [selectedRow, setSelectedRow] = useState(null);
  const [bloques, setBloques] = useState([]);
  const [selectedBloque, setSelectedBloque] = useState("1");
  const [selectedTipo, setSelectedTipo] = useState("Aula");
  const [aulas, setAulas] = useState([]);
  const [nombreError, setNombreError] = useState("");
  const [capacidadError, setCapacidadError] = useState("");
  const [laboratorios, setLaboratorios] = useState([]);
  const [cancel, setCancel] = useState(false);
  const [filtroBloque, setFiltroBloque] = useState("");
  const [filtroTipo, setFiltroTipo] = useState("");
  const [filtroNombre, setFiltroNombre] = useState("");
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
  const [itemsPorPagina] = useState(10);

  // useEffects
  useEffect(() => {
    getBloques();
    fetchAulasLabs();
  }, []);

  useEffect(() => {
    if (filtroBloque === "" && filtroNombre === "" && filtroTipo === "") {
      fetchAulasLabs();
    }
  }, [filtroBloque, filtroNombre, filtroTipo]);

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
      limpiar();
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
    let url;
    if (!filtroNombre && !filtroBloque && !filtroTipo) {
      url = "http://localhost:8080/espacio";
    } else if (filtroNombre && !filtroBloque && !filtroTipo) {
      url = `http://localhost:8080/espacio/filter-nombre/${filtroNombre}`;
    } else if (!filtroNombre && filtroBloque && !filtroTipo) {
      url = `http://localhost:8080/espacio/bloque/${filtroBloque}`;
    } else if (!filtroNombre && !filtroBloque && filtroTipo) {
      url = `http://localhost:8080/espacio/filter-tipo/${filtroTipo}`;
    } else if (filtroNombre && filtroBloque && !filtroTipo) {
      url = `http://localhost:8080/espacio/filter-bloque-nombre/${filtroBloque}/${filtroNombre}`;
    } else if (!filtroNombre && filtroBloque && filtroTipo) {
      url = `http://localhost:8080/espacio/filter-tipo-bloque/${filtroTipo}/${filtroBloque}`;
    } else if (filtroNombre && !filtroBloque && filtroTipo) {
      url = `http://localhost:8080/espacio/filter-tipo-nombre/${filtroTipo}/${filtroNombre}`;
    } else {
      url = `http://localhost:8080/espacio/filter-bloque-nombre-tipo/${filtroBloque}/${filtroNombre}/${filtroTipo}`;
    }

    try {
      const response = await axios.get(url);
      if (!response.data) {
        info("No hay coincidencias");
      } else {
        setAulas(response.data);
      }
    } catch (error) {
      oops("No se pudo cargar los espacios. Por favor, inténtelo de nuevo.");
      setAulas([]);
    }
  };

  const handlePageClick = data => {
    setPaginaActual(data.selected);
  };

  const offset = paginaActual * itemsPorPagina;
  const currentPageData = aulas.slice(offset, offset + itemsPorPagina);
  const pageCount = Math.ceil(aulas.length / itemsPorPagina);

  const handleRowClick = (e, aula) => {
    e.stopPropagation();
    setSelectedRow(aula.id);
    setContextMenuPosition({ top: e.pageY, left: e.pageX });
    setShowContextMenu(true);
  };

  const handleEditClick = () => {
    const selectedAulaLab = aulasLabsToShow.find(d => d.id === selectedRow);
    if (selectedAulaLab) {
      setFormData({
        id_bloque: selectedAulaLab.id_bloque,
        tipo: selectedAulaLab.tipo,
        piso: selectedAulaLab.piso,
        nombre: selectedAulaLab.nombre,
        capacidad: selectedAulaLab.capacidad,
        id: selectedAulaLab.id,
      });
      setSelectedBloque(selectedAulaLab.id_bloque);
      setSelectedTipo(selectedAulaLab.tipo);
      setIsEditing(true);
      setShowContextMenu(false);
    }
  };

  const handleDocumentClick = e => {
    if (!e.target.closest(".context-menu") && !e.target.closest("td")) {
      setSelectedRow(null);
      setShowContextMenu(false);
    }
  };

  const cargarTabla = () => {
    return aulas.slice(offset, offset + itemsPorPagina).map(aula => (
      <tr key={aula.id} onClick={e => handleRowClick(e, aula)}>
        <td>{aula.nombre}</td>
        <td>{aula.piso}</td>
        <td>{aula.capacidad}</td>
        <td>{bloques.find(b => b.id === aula.id_bloque)?.nombre}</td>
        <td>{aula.tipo}</td>
      </tr>
    ));
  };

  const handleSearch = () => {
    fetchAulasLabs();
  };

  const handleRefresh = () => {
    setFiltroNombre("");
    setFiltroBloque("");
    setFiltroTipo("");
  };

  return (
    <div className="container">
      <div className="content">
        <div className="header">
          <h2>Aulas/Laboratorios</h2>
        </div>
        <div className="row mb-0 mt-1 justify-content-between">
          <div className="col d-flex align-items-center">
            <label className="d-flex align-items-center fw-bold me-4">
              Filtros:
            </label>
            <div className="col-auto d-flex align-items-center">
              <input
                type="text"
                className="form-control"
                placeholder="Nombre"
                value={filtroNombre}
                onChange={e => setFiltroNombre(e.target.value)}
                maxLength={30}
              />
            </div>
            <div className="col-auto d-flex align-items-center ms-4">
              <label className="me-2">Tipo:</label>
              <select
                className="form-select"
                value={filtroTipo}
                onChange={e => setFiltroTipo(e.target.value)}
              >
                <option value="">Todos</option>
                <option value="Laboratorio">Laboratorio</option>
                <option value="Aula">Aula</option>
                <option value="Especial">Especial</option>
              </select>
            </div>
            <div className="col-auto d-flex align-items-center ms-4">
              <label className="me-2">Bloque:</label>
              <select
                className="form-select"
                value={filtroBloque}
                onChange={e => setFiltroBloque(e.target.value)}
              >
                <option value="">Todos</option>
                {bloques.map(bloque => (
                  <option key={bloque.id} value={bloque.id}>
                    {bloque.nombre}
                  </option>
                ))}
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
                // handleShowModal();
              }}
            >
              Nuevo Espacio
            </button>
          </div>
        </div>
        <div className="mt-1">
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
                  <td colSpan="5">No hay resultados</td>
                </tr>
              ) : (
                cargarTabla()
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
            <Button variant="custom" id="editar-btn" onClick={handleEditClick}>
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
