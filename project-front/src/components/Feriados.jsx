import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button, Modal } from "react-bootstrap";
import "../styles/usuarios.css";
import axios from "axios";
import { ok, oops, deleteConfirmation } from "../utils/Alerts";
import ReactPaginate from "react-paginate";

const Feriados = () => {
  const [isEditing, setIsEditing] = useState(false);
  const [selectedRow, setSelectedRow] = useState(null);
  const [showContextMenu, setShowContextMenu] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [contextMenuPosition, setContextMenuPosition] = useState({
    top: 0,
    left: 0,
  });
  const [nombre, setNombre] = useState("");
  const [inicio, setInicio] = useState();
  const [fin, setFin] = useState();
  const [idFeriado, setIdFeriado] = useState("");
  const [feriados, setFeriados] = useState([]);
  const [filtroInicio, setFiltroInicio] = useState();
  const [filtroFin, setFiltroFin] = useState();
  // Paginación
  const [paginaActual, setPaginaActual] = useState(0);
  const [itemsPorPagina, setItemsPorPagina] = useState(10);

  // useEffects
  useEffect(() => {
    document.addEventListener("click", handleDocumentClick);
    return () => {
      document.removeEventListener("click", handleDocumentClick);
    };
  }, []);

  useEffect(() => {
    getFeriados();
  }, [filtroInicio, filtroFin, paginaActual]);

  const getFeriados = async () => {
    const url = `http://localhost:8080/feriado`;
    try {
      const response = await axios.get(url);
      const data = Array.isArray(response.data) ? response.data : [];
      const filteredData = data.filter(feriado => {
        return (
          (!filtroInicio && !filtroFin) || // Ningún filtro
          (filtroInicio && !filtroFin && feriado.inicio >= filtroInicio) || // Solo filtroInicio
          (!filtroInicio && filtroFin && feriado.fin <= filtroFin) || // Solo filtroFin
          (filtroInicio &&
            filtroFin && // Ambos filtros
            ((feriado.inicio >= filtroInicio && feriado.inicio <= filtroFin) ||
              (feriado.fin >= filtroInicio && feriado.fin <= filtroFin) ||
              (feriado.inicio <= filtroInicio && feriado.fin >= filtroFin)))
        );
      });
      setFeriados(filteredData);
    } catch (error) {
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

  const offset = paginaActual * itemsPorPagina;
  const currentPageData = feriados.slice(offset, offset + itemsPorPagina);
  const pageCount = Math.ceil(feriados.length / itemsPorPagina);

  const cargarFeriados = () => {
    return currentPageData.map(feriado => (
      <tr key={feriado.id} onClick={e => handleRowClick(e, feriado)}>
        <td>{feriado.nombre}</td>
        <td>{feriado.inicio}</td>
        <td>{feriado.fin}</td>
      </tr>
    ));
  };
  const handlePageClick = data => {
    setPaginaActual(data.selected);
  };

  const limpiar = () => {
    setNombre("");
    setInicio("");
    setFin("");
  };

  const handleCloseModal = () => {
    limpiar();
    setShowModal(false);
  };

  const handleShowModal = () => {
    setShowModal(true);
  };

  const addOneDay = dateString => {
    const date = new Date(dateString);
    date.setDate(date.getDate() + 1);

    const year = date.getFullYear();
    const month = ("0" + (date.getMonth() + 1)).slice(-2);
    const day = ("0" + date.getDate()).slice(-2);

    return `${year}-${month}-${day}`;
  };

  const saveFeriado = async () => {
    try {
      const feriado = {
        nombre,
        inicio,
        fin,
      };
      await axios.post("http://localhost:8080/feriado", feriado);
      ok("Registro guardado exitosamente.");
      getFeriados();
      handleCloseModal();
      limpiar();
    } catch (error) {
      oops("Error al conectar con el servidor.");
    }
  };

  const editFeriado = async () => {
    try {
      console.log(inicio, fin);
      const feriado = {
        nombre,
        inicio: addOneDay(inicio),
        fin: addOneDay(fin),
      };
      console.log(addOneDay(inicio), addOneDay(fin));
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
          <h2>Feriados</h2>
        </div>
        <div className="row mb-0 mt-3 justify-content-between">
          <div className="col d-flex align-items-center">
            <label className="d-flex align-items-center fw-bold me-4">
              Filtros:
            </label>
            <div className="d-flex align-items-center">
              <label className="me-2">Desde:</label>
              <input
                className="form-control"
                type="date"
                value={filtroInicio}
                onChange={handleFiltroInicio}
                style={{
                  padding: "8px",
                  width: "100%",
                  boxSizing: "border-box",
                }}
              />
            </div>
            <div className="d-flex align-items-center ms-4">
              <label className="me-2">Hasta:</label>
              <input
                type="date"
                className="form-control"
                value={filtroFin}
                onChange={handleFiltroFin}
                style={{
                  padding: "8px",
                  width: "100%",
                  boxSizing: "border-box",
                }}
                min={filtroInicio}
              />
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
              Nuevo feriado
            </button>
          </div>
        </div>
        <div className="mt-0">
          <table className="table table-bordered mt-3">
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Inicio (mm/dd/yyyy)</th>
                <th>Fin (mm/dd/yyyy)</th>
              </tr>
            </thead>
            <tbody>{cargarFeriados()}</tbody>
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
            {!isEditing ? "Crear Feriado" : "Editar Feriado"}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmit}>
            <Form.Group className="mb-3">
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
            <Form.Group className="mb-3">
              <Form.Label>Fecha de inicio</Form.Label>
              <Form.Control
                type="date"
                value={inicio}
                onChange={inicioChange}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Fecha de fin</Form.Label>
              <Form.Control
                type="date"
                value={fin}
                onChange={finChange}
                min={inicio}
              />
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

export default Feriados;
