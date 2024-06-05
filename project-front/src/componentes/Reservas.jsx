import "bootstrap/dist/css/bootstrap.min.css";
import "./Reserva.css"; // Asegúrate de tener este archivo en la misma carpeta
import React, { useState, useEffect } from "react";
import { Modal, Button } from "react-bootstrap";
import axios from "axios";
import Swal from "sweetalert2";

const LabReservations = () => {
<<<<<<< HEAD
  const [bloques, setBloques] = useState([]);
  const [aulasLabs, setAulasLabs] = useState([]);
  const [selectedBloque, setSelectedBloque] = useState('');
  const [selectedTipo, setSelectedTipo] = useState('Aulas');
  const [selectedAulaLab, setSelectedAulaLab] = useState('');
  const [horarios, setHorarios] = useState([]);
=======
  const [currentWeek, setCurrentWeek] = useState(0);
  const [weekRange, setWeekRange] = useState("");
  const [weekDates, setWeekDates] = useState([]);
  const [selectedCell, setSelectedCell] = useState(null);
  const [selectedDate, setSelectedDate] = useState(null);
>>>>>>> c556afe5c55609748f4b7b17a3950ef0530ceec8
  const [showModal, setShowModal] = useState(false);
  const [showAddModal, setShowAddModal] = useState(false);
  const [showConfirmDelete, setShowConfirmDelete] = useState(false);
  const [selectedCell, setSelectedCell] = useState(null);
  const [selectedDate, setSelectedDate] = useState(null);
  const [reservationDetails, setReservationDetails] = useState({
    encargado: "",
    asunto: "",
    descripcion: "",
    hora: "",
    fecha: "",
    editable: false,
  });
  const [newReservation, setNewReservation] = useState({
    encargado: "",
    asunto: "",
    descripcion: "",
    hora: "",
  });

<<<<<<< HEAD
  useEffect(() => {
    fetchBloques();
  }, []);
=======
  // Variables reactivas
  const [bloques, setBloques] = useState([]);
  const [horarios, setHorarios] = useState([]);
  const [selectedBloque, setSelectedBloque] = useState(1);
  const [aulasLabs, setAulasLabs] = useState([]);
  const [selectedTipo, setSelectedTipo] = useState("Aula");
  const [selectedAulaLab, setSelectedAulaLab] = useState();

  useEffect(() => {
    updateWeekRange();
  }, [currentWeek]);
>>>>>>> c556afe5c55609748f4b7b17a3950ef0530ceec8

  useEffect(() => {
    if (selectedBloque && selectedTipo) {
      fetchAulasLabs();
    }
  }, [selectedBloque, selectedTipo]);

<<<<<<< HEAD
  useEffect(() => {
    if (selectedAulaLab) {
      getHorarios();
    }
  }, [selectedAulaLab]);

  const fetchBloques = async () => {
=======
  const getHorarios = async () => {
    const url =
      selectedTipo === "Laboratorios"
        ? `http://localhost:8080/horario/lab/${selectedAulaLab}`
        : `http://localhost:8080/horario/aula/${selectedAulaLab}`;
    console.log(url);
>>>>>>> c556afe5c55609748f4b7b17a3950ef0530ceec8
    try {
      const response = await axios.get(url);
      console.log(response.data);
      setHorarios(response.data);
    } catch (error) {
      const { message } = error.response.data;
      console.log(message);
      setHorarios([]); // Limpia los datos si la petición falla
    }
  };

  const getBloques = async () => {
    try {
      const resp = await axios.get("http://localhost:8080/bloque");
      setBloques(resp.data);
    } catch (error) {
      const { message } = error.response.data;
      console.log(message);
    }
  };

  const fetchAulasLabs = async () => {
    const url =
      selectedTipo === "Laboratorios"
        ? `http://localhost:8080/lab/bloque/${selectedBloque}`
        : `http://localhost:8080/aula/bloque/${selectedBloque}`;
    try {
      const response = await axios.get(url);
      setAulasLabs(response.data);
    } catch (error) {
      const { message } = error.response.data;
      if (
        message === "El bloque no tiene laboratorios" ||
        message === "El bloque no tiene aulas"
      ) {
        Swal.fire({
          title: "Oops...",
          html: `<i>${message}</i>`,
          icon: "error",
        });
      } else {
        Swal.fire({
          title: "Oops...",
          html: `<i>Error al conectar con el servidor</i>`,
          icon: "error",
        });
      }
      setAulasLabs([]); // Limpia los datos si la petición falla
    }
  };

  const getHorarios = async () => {
    const url =
      selectedTipo === 'Laboratorios'
        ? `http://localhost:8080/horario/lab/${selectedAulaLab}`
        : `http://localhost:8080/horario/aula/${selectedAulaLab}`;
    console.log(url);
    try {
      const response = await axios.get(url);
      console.log(response.data);
      setHorarios(response.data);
    } catch (error) {
      const { message } = error.response.data;
      console.log(message);
      setHorarios([]); // Limpia los datos si la petición falla
    }
  };

  const handleBloqueChange = (event) => {
    setSelectedBloque(event.target.value);
  };

  const handleTipoChange = (event) => {
    setSelectedTipo(event.target.value);
  };

  const handleAulaLabChange = (event) => {
    setSelectedAulaLab(event.target.value);
  };

<<<<<<< HEAD
=======
  useEffect(() => {
    getBloques();
  }, []);

  useEffect(() => {
    if (selectedBloque && selectedTipo) {
      fetchAulasLabs();
    }
  }, [selectedBloque, selectedTipo]);

  useEffect(() => {
    if (selectedAulaLab) {
      getHorarios();
    }
  }, [selectedAulaLab]);

  const renderTableCell = (dia, hora) => {
    const horario = horarios.find(
      (h) => h.dia === dia && h.hora === hora.split("-")[0],
    );
    return horario ? `${horario.materia}` : "";
  };

  const horas = [
    "7-8",
    "8-9",
    "9-10",
    "10-11",
    "11-12",
    "12-13",
    "13-14",
    "14-15",
    "15-16",
    "16-17",
    "17-18",
    "18-19",
    "19-20",
  ];
  const dias = ["Lunes", "Martes", "Miercoles", "Jueves", "Viernes"];

  const getMonday = (d) => {
    d = new Date(d);
    const day = d.getDay();
    const diff = d.getDate() - day + (day === 0 ? -6 : 1);
    return new Date(d.setDate(diff));
  };

  const formatDate = (date) => {
    const d = new Date(date);
    let month = "" + (d.getMonth() + 1);
    let day = "" + d.getDate();
    const year = d.getFullYear();

    if (month.length < 2) month = "0" + month;
    if (day.length < 2) day = "0" + day;

    return [day, month, year].join("-");
  };

  const updateWeekRange = () => {
    const now = new Date();
    now.setDate(now.getDate() + currentWeek * 7);
    const monday = getMonday(now);
    const dates = [monday];
    for (let i = 1; i <= 5; i++) {
      const day = new Date(monday);
      day.setDate(monday.getDate() + i);
      dates.push(day);
    }
    setWeekDates(dates);
    setWeekRange(`Semana del: ${formatDate(monday)} - ${formatDate(dates[5])}`);
  };

  const changeWeek = (change) => {
    setCurrentWeek((prev) => prev + change);
  };

>>>>>>> c556afe5c55609748f4b7b17a3950ef0530ceec8
  const handleCellClick = (event, cell, date) => {
    const text = cell.textContent.trim();
    const menu = document.getElementById("context-menu");
    setSelectedCell(cell);

<<<<<<< HEAD
    if (text.startsWith('Reservado')) {
      const info = cell.getAttribute('data-info').split(', ');
=======
    if (text === "Disponible") {
      const now = new Date();
      if (date < now.setHours(0, 0, 0, 0)) {
        alert("No se puede reservar en fechas pasadas.");
        return;
      }
      setSelectedDate(date);
      menu.style.display = "block";
      menu.style.left = `${event.pageX}px`;
      menu.style.top = `${event.pageY}px`;
      event.stopPropagation();
    } else {
      menu.style.display = "none";
    }

    if (text.startsWith("Reservado")) {
      const info = cell.getAttribute("data-info").split(", ");
>>>>>>> c556afe5c55609748f4b7b17a3950ef0530ceec8
      setReservationDetails({
        encargado: info[0].split(": ")[1],
        asunto: info[1].split(": ")[1],
        descripcion: "Descripción aquí",
        hora: cell.parentElement.firstChild.textContent,
        fecha: date,
        editable: false,
      });
      setShowModal(true);
    }
  };

  const enableEditing = () => {
    document.getElementById("encargado").disabled = false;
    document.getElementById("asunto").disabled = false;
    document.getElementById("descripcion").disabled = false;
    const saveBtn = document.createElement("button");
    saveBtn.textContent = "Guardar";
    saveBtn.classList.add("btn", "btn-success");
    saveBtn.onclick = saveReservation;
    saveBtn.id = "saveBtn";
    const footer = document.querySelector(".modal-footer");
    footer.insertBefore(saveBtn, footer.firstChild);
    document.querySelector(".btn-primary").style.display = "none";
  };

  const saveReservation = () => {
    selectedCell.setAttribute(
      "data-info",
      `Encargado: ${reservationDetails.encargado}, Asunto: ${reservationDetails.asunto}`,
    );
    selectedCell.textContent = "Reservado - Descripción";
    setShowModal(false);
    setReservationDetails({
      encargado: "",
      asunto: "",
      descripcion: "",
      hora: "",
      fecha: "",
      editable: false,
    });
  };

  const confirmDelete = () => {
    setShowConfirmDelete(true);
  };

  const deleteReservation = () => {
<<<<<<< HEAD
    selectedCell.textContent = '';
    selectedCell.removeAttribute('data-info');
=======
    selectedCell.textContent = "Disponible";
    selectedCell.removeAttribute("data-info");
>>>>>>> c556afe5c55609748f4b7b17a3950ef0530ceec8
    setShowConfirmDelete(false);
    setShowModal(false);
  };

  const handleAddReservation = () => {
    if (selectedCell) {
      const hora = selectedCell.closest("tr").firstChild.textContent;
      setNewReservation((prev) => ({ ...prev, hora }));
      setShowAddModal(true);
    }
  };

  const saveNewReservation = () => {
    if (selectedCell) {
      selectedCell.setAttribute(
        "data-info",
        `Encargado: ${newReservation.encargado}, Asunto: ${newReservation.asunto}`,
      );
      selectedCell.textContent = "Reservado - Descripción";
    }
    setShowAddModal(false);
    setNewReservation({
      encargado: "",
      asunto: "",
      descripcion: "",
      hora: "",
    });
  };

<<<<<<< HEAD
=======
  const renderTableCells = (hour) => {
    const cells = [];
    const now = new Date();
    for (let i = 0; i < 6; i++) {
      const date = weekDates[i];
      if (i === 1 && hour === "07:00-08:00") {
        cells.push(
          <td
            key={i}
            data-info="Encargado: Dr. Ana, Asunto: Física"
            onClick={(e) => handleCellClick(e, e.target, date)}
          >
            Reservado - Descripción
          </td>,
        );
      } else if (i === 2 && hour === "07:00-08:00") {
        cells.push(
          <td key={i} onClick={(e) => handleCellClick(e, e.target, date)}>
            Asunto - Encargado
          </td>,
        );
      } else {
        cells.push(
          <td
            key={i}
            data-date={formatDate(date)}
            onClick={(e) => handleCellClick(e, e.target, date)}
            className={
              date < now.setHours(0, 0, 0, 0) ? "past-date" : "available"
            }
          >
            Disponible
          </td>,
        );
      }
    }
    return cells;
  };

>>>>>>> c556afe5c55609748f4b7b17a3950ef0530ceec8
  return (
    <div className="container mt-3">
      <div className="header text-center">
        <h2>RESERVAS DE LABORATORIO</h2>
      </div>
      <div className="row">
        <div className="col-md-4">
          <label>Bloque/Edificio</label>
          <select
            className="form-control"
            id="bloqueSelect"
            value={selectedBloque}
            onChange={handleBloqueChange}
          >
            {bloques.map((bloque) => (
              <option key={bloque.id} value={bloque.id}>
                {bloque.nombre}
              </option>
            ))}
          </select>
        </div>
        <div className="col-md-4">
          <label>Tipo</label>
          <select
            className="form-control"
            id="tipoSelect"
            value={selectedTipo}
            onChange={handleTipoChange}
          >
            <option value="Aulas">Aulas</option>
            <option value="Laboratorios">Laboratorios</option>
          </select>
        </div>
        <div className="col-md-4">
          <label>Aula/Laboratorio</label>
          <select
            className="form-control"
            id="aulaLabSelect"
            value={selectedAulaLab}
            onChange={handleAulaLabChange}
          >
            {aulasLabs.map((aulaLab) => (
              <option key={aulaLab.id} value={aulaLab.id}>
                {aulaLab.nombre}
              </option>
            ))}
          </select>
        </div>
<<<<<<< HEAD
      </div>

      <div className="row mt-3">
        <div className="col-md-12">
          <table className="table table-bordered text-center">
            <thead>
              <tr>
                <th>Hora/Día</th>
                <th>Lunes</th>
                <th>Martes</th>
                <th>Miércoles</th>
                <th>Jueves</th>
                <th>Viernes</th>
                <th>Sábado</th>
              </tr>
            </thead>
            <tbody>
              {horarios.map((horario) => (
                <tr key={horario.hora}>
                  <td>{horario.hora}</td>
                  <td
                    data-info={`Encargado: ${horario.encargado}, Asunto: ${horario.asunto}`}
                    onClick={(e) => handleCellClick(e, e.target, horario.fecha)}
                  >
                    {horario.Lunes}
                  </td>
                  <td
                    data-info={`Encargado: ${horario.encargado}, Asunto: ${horario.asunto}`}
                    onClick={(e) => handleCellClick(e, e.target, horario.fecha)}
                  >
                    {horario.Martes}
                  </td>
                  <td
                    data-info={`Encargado: ${horario.encargado}, Asunto: ${horario.asunto}`}
                    onClick={(e) => handleCellClick(e, e.target, horario.fecha)}
                  >
                    {horario.Miércoles}
                  </td>
                  <td
                    data-info={`Encargado: ${horario.encargado}, Asunto: ${horario.asunto}`}
                    onClick={(e) => handleCellClick(e, e.target, horario.fecha)}
                  >
                    {horario.Jueves}
                  </td>
                  <td
                    data-info={`Encargado: ${horario.encargado}, Asunto: ${horario.asunto}`}
                    onClick={(e) => handleCellClick(e, e.target, horario.fecha)}
                  >
                    {horario.Viernes}
                  </td>
                  <td
                    data-info={`Encargado: ${horario.encargado}, Asunto: ${horario.asunto}`}
                    onClick={(e) => handleCellClick(e, e.target, horario.fecha)}
                  >
                    {horario.Sábado}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
=======
        <div className="col-md-4 nav-buttons">
          <button className="btn" onClick={() => changeWeek(-1)}>
            &lt;&lt;
          </button>
          <span id="weekRange" className="week-display">
            {weekRange}
          </span>
          <button className="btn" onClick={() => changeWeek(1)}>
            &gt;&gt;
          </button>
        </div>
      </div>

      <table className="table table-bordered mt-4">
        <thead>
          <tr>
            <th>Horas</th>
            <th>Lunes</th>
            <th>Martes</th>
            <th>Miércoles</th>
            <th>Jueves</th>
            <th>Viernes</th>
          </tr>
        </thead>
        <tbody>
          {horas.map((hora) => (
            <tr key={hora}>
              <td>{hora}</td>
              {dias.map((dia) => (
                <td key={`${dia}-${hora}`}>{renderTableCell(dia, hora)}</td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
>>>>>>> c556afe5c55609748f4b7b17a3950ef0530ceec8

      <div id="context-menu" className="context-menu">
        <button className="btn btn-sm" onClick={handleAddReservation}>
          Reservar
        </button>
      </div>

      {/* Modal para detalles de reserva */}
      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Detalles de la Reserva</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form id="reservationForm">
            <div className="mb-3">
              <label htmlFor="fecha" className="form-label">
                Fecha
              </label>
              <input
                type="text"
                className="form-control"
                id="fecha"
                value={reservationDetails.fecha}
                disabled
              />
            </div>
            <div className="mb-3">
              <label htmlFor="encargado" className="form-label">
                Responsable
              </label>
              <input
                type="text"
                className="form-control"
                id="encargado"
                value={reservationDetails.encargado}
                disabled={!reservationDetails.editable}
                onChange={(e) =>
                  setReservationDetails((prev) => ({
                    ...prev,
                    encargado: e.target.value,
                  }))
                }
              />
            </div>
            <div className="mb-3">
              <label htmlFor="asunto" className="form-label">
                Asunto
              </label>
              <input
                type="text"
                className="form-control"
                id="asunto"
                value={reservationDetails.asunto}
                disabled={!reservationDetails.editable}
                onChange={(e) =>
                  setReservationDetails((prev) => ({
                    ...prev,
                    asunto: e.target.value,
                  }))
                }
              />
            </div>
            <div className="mb-3">
              <label htmlFor="descripcion" className="form-label">
                Descripción
              </label>
              <input
                type="text"
                className="form-control"
                id="descripcion"
                value={reservationDetails.descripcion}
                disabled={!reservationDetails.editable}
                onChange={(e) =>
                  setReservationDetails((prev) => ({
                    ...prev,
                    descripcion: e.target.value,
                  }))
                }
              />
            </div>
            <div className="mb-3">
              <label htmlFor="hora" className="form-label">
                Hora
              </label>
              <input
                type="text"
                className="form-control"
                id="hora"
                value={reservationDetails.hora}
                disabled
              />
            </div>
          </form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="primary" onClick={enableEditing}>
            Modificar
          </Button>
          <Button variant="danger" onClick={confirmDelete}>
            Eliminar
          </Button>
          <Button variant="secondary" onClick={() => setShowModal(false)}>
            Cerrar
          </Button>
        </Modal.Footer>
      </Modal>

      {/* Modal para agregar reserva */}
      <Modal show={showAddModal} onHide={() => setShowAddModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Agregar Reserva</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form id="newReservationForm">
            <div className="mb-3">
<<<<<<< HEAD
              <label htmlFor="newDate" className="form-label">Fecha</label>
              <input type="text" className="form-control" id="newDate" value={selectedDate ? selectedDate : ''} disabled />
=======
              <label htmlFor="newDate" className="form-label">
                Fecha
              </label>
              <input
                type="text"
                className="form-control"
                id="newDate"
                value={selectedDate ? formatDate(selectedDate) : ""}
                disabled
              />
>>>>>>> c556afe5c55609748f4b7b17a3950ef0530ceec8
            </div>
            <div className="mb-3">
              <label htmlFor="newEncargado" className="form-label">
                Responsable
              </label>
              <input
                type="text"
                className="form-control"
                id="newEncargado"
                value={newReservation.encargado}
                onChange={(e) =>
                  setNewReservation((prev) => ({
                    ...prev,
                    encargado: e.target.value,
                  }))
                }
              />
            </div>
            <div className="mb-3">
              <label htmlFor="newAsunto" className="form-label">
                Asunto
              </label>
              <input
                type="text"
                className="form-control"
                id="newAsunto"
                value={newReservation.asunto}
                onChange={(e) =>
                  setNewReservation((prev) => ({
                    ...prev,
                    asunto: e.target.value,
                  }))
                }
              />
            </div>
            <div className="mb-3">
              <label htmlFor="newDescripcion" className="form-label">
                Descripción
              </label>
              <input
                type="text"
                className="form-control"
                id="newDescripcion"
                value={newReservation.descripcion}
                onChange={(e) =>
                  setNewReservation((prev) => ({
                    ...prev,
                    descripcion: e.target.value,
                  }))
                }
              />
            </div>
            <div className="mb-3">
              <label htmlFor="newHora" className="form-label">
                Hora
              </label>
              <input
                type="text"
                className="form-control"
                id="newHora"
                value={newReservation.hora}
                disabled
              />
            </div>
          </form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="success" onClick={saveNewReservation}>
            Agregar
          </Button>
          <Button variant="secondary" onClick={() => setShowAddModal(false)}>
            Cerrar
          </Button>
        </Modal.Footer>
      </Modal>

      {/* Modal para confirmación de eliminación */}
      <Modal
        show={showConfirmDelete}
        onHide={() => setShowConfirmDelete(false)}
      >
        <Modal.Header closeButton>
          <Modal.Title>Confirmar Eliminación</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          ¿Estás seguro de que deseas eliminar esta reserva?
        </Modal.Body>
        <Modal.Footer>
          <Button variant="danger" onClick={deleteReservation}>
            Eliminar
          </Button>
          <Button
            variant="secondary"
            onClick={() => setShowConfirmDelete(false)}
          >
            Cancelar
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default LabReservations;
