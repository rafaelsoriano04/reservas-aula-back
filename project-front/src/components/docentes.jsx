import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button, Modal, FormControl } from "react-bootstrap";
import "../styles/docentes.css";
import axios from "axios";
import ReactPaginate from "react-paginate";
import { FaPlus } from "react-icons/fa";
import { ok, oops, deleteConfirmation, info, warning } from "../utils/Alerts";
import { Input } from "react-select/animated";

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
    telefono: "0999999999",
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
  function validarcedula(cedula){
    
    if(cedula.length == 10){
        
      //Obtenemos el digito de la region que sonlos dos primeros digitos
      var digito_region = cedula.substring(0,2);
      
      //Pregunto si la region existe ecuador se divide en 24 regiones
      if( digito_region >= 1 && digito_region <=24 ){
        
        // Extraigo el ultimo digito
        var ultimo_digito   = cedula.substring(9,10);

        //Agrupo todos los pares y los sumo
        var pares = parseInt(cedula.substring(1,2)) + parseInt(cedula.substring(3,4)) + parseInt(cedula.substring(5,6)) + parseInt(cedula.substring(7,8));

        //Agrupo los impares, los multiplico por un factor de 2, si la resultante es > que 9 le restamos el 9 a la resultante
        var numero1 = cedula.substring(0,1);
        var numero1 = (numero1 * 2);
        if( numero1 > 9 ){ var numero1 = (numero1 - 9); }

        var numero3 = cedula.substring(2,3);
        var numero3 = (numero3 * 2);
        if( numero3 > 9 ){ var numero3 = (numero3 - 9); }

        var numero5 = cedula.substring(4,5);
        var numero5 = (numero5 * 2);
        if( numero5 > 9 ){ var numero5 = (numero5 - 9); }

        var numero7 = cedula.substring(6,7);
        var numero7 = (numero7 * 2);
        if( numero7 > 9 ){ var numero7 = (numero7 - 9); }

        var numero9 = cedula.substring(8,9);
        var numero9 = (numero9 * 2);
        if( numero9 > 9 ){ var numero9 = (numero9 - 9); }

        var impares = numero1 + numero3 + numero5 + numero7 + numero9;

        //Suma total
        var suma_total = (pares + impares);

        //extraemos el primero digito
        var primer_digito_suma = String(suma_total).substring(0,1);

        //Obtenemos la decena inmediata
        var decena = (parseInt(primer_digito_suma) + 1)  * 10;

        //Obtenemos la resta de la decena inmediata - la suma_total esto nos da el digito validador
        var digito_validador = decena - suma_total;

        //Si el digito validador es = a 10 toma el valor de 0
        if(digito_validador == 10)
          var digito_validador = 0;

        //Validamos que el digito validador sea igual al de la cedula
        if(digito_validador == ultimo_digito){
          return true;
        }else{
          return false;
        }
        
      }
   }else{
      return false;
   }    
  }
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
      
      if (!validarcedula(docente.cedula)) {
        oops("Cédula no Valida");
        return;
      }
      
      await axios.post(`http://localhost:8080/person`, docente);
      getDocentes();
      setFormData({
        id: "",
        nombre: "",
        cedula: "",
        apellido: "",
        telefono: "",
      });
      handleCloseModal();
      ok("Registro guardado exitosamente.");
    } catch (error) {
      if (error.response) {
        const { message } = error.response.data;
        if (message == "cedula existe") {
          warning("La cédula ya existe");
        }
      } else {
        oops("No se pudo guardar el registro. Por favor, inténtelo de nuevo.");
      }

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
      cedula: "editando",
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
        handleCloseModal();
      }
    } catch (error) {

      if (error.response) {
        const { message } = error.response.data;
        if (message == "cedula existe") {
          warning("La cédula ya existe");
        }
      } else {
        oops("No se pudo actualizar el registro. Por favor, inténtelo de nuevo.");
      }

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
                  {!isEditing ? (<Form.Control
                    type="text"
                    id="cedula"
                    className="form-control"
                    name="cedula"
                    value={formData.cedula}
                    maxLength={10}
                    placeholder="Ingrese su cédula"
                    onChange={(e) => {
                      // Acepta solo valores numéricos
                      const re = /^[0-9\b]+$/;
                      if (e.target.value === '' || re.test(e.target.value)) {
                        setFormData({ ...formData, cedula: e.target.value });
                      }
                    }}
                    
                  />) : (<Form.Control
                    type="text"
                    id="cedula"
                    className="form-control"
                    name="cedula"
                    value={formData.cedula}
                    maxLength={10}
                    placeholder="Ingrese su cédula"
                    onChange={(e) => {
                      // Acepta solo valores numéricos
                      const re = /^[0-9\b]+$/;
                      if (e.target.value === '' || re.test(e.target.value)) {
                        setFormData({ ...formData, cedula: e.target.value });
                      }
                    }}
                    readOnly
                    
                  />)}
                  
                  

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
                  onClick={() => {
                    crearDocente();

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
                    onClick={() => {
                      limpiar();
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
