import "bootstrap/dist/css/bootstrap.min.css";
import "./Reserva.css"; // Asegúrate de tener este archivo en la misma carpeta
import React, { useState, useEffect } from "react";
import { Modal, Button } from "react-bootstrap";
import axios from "axios";
import Swal from "sweetalert2";

const LabReservations = () => {
  const [currentWeek, setCurrentWeek] = useState(0);
  const [weekRange, setWeekRange] = useState("");
  const [weekDates, setWeekDates] = useState([]);
  const [selectedCell, setSelectedCell] = useState(null);
  const [selectedDate, setSelectedDate] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [showAddModal, setShowAddModal] = useState(false);
  const [showConfirmDelete, setShowConfirmDelete] = useState(false);
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

  // Variables reactivas
  const [bloques, setBloques] = useState([]);
  const [horarios, setHorarios] = useState([]);
  const [reservations, setReservations] = useState([]);
  const [selectedBloque, setSelectedBloque] = useState(1);
  const [aulasLabs, setAulasLabs] = useState([]);
  const [selectedTipo, setSelectedTipo] = useState("Aula");
  const [selectedAulaLab, setSelectedAulaLab] = useState();

  useEffect(() => {
    updateWeekRange();
  }, [currentWeek]);

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
  }, [selectedAulaLab, weekDates]);

  const getHorarios = async () => {
    const url =
      selectedTipo === "Laboratorios"
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

  const handleBloqueChange = (event) => {
    setSelectedBloque(event.target.value);
  };

  const handleTipoChange = (event) => {
    setSelectedTipo(event.target.value);
  };

  const handleAulaLabChange = (event) => {
    setSelectedAulaLab(event.target.value);
  };

  const renderTableCell = (dia, hora) => {
    const selectedDay = weekDates[dias.indexOf(dia)];
    const formattedDate = formatDate(selectedDay);
    const reservation = reservations.find(
      (r) => r.dia === dia && r.hora === hora && r.fecha === formattedDate
    );
    const horario = horarios.find(
      (h) => h.dia === dia && h.hora === hora.split("-")[0]
    );
    if (reservation) {
      return (
        <div className="reserved" data-info={`Encargado: ${reservation.encargado}, Asunto: ${reservation.asunto}`}>
          Reservado - {reservation.descripcion || "Descripción"}
        </div>
      );
    } else if (horario) {
      return horario.materia;
    } else {
      return (
        <div
          onClick={(e) => handleCellClick(e, dia, hora)}
          className="available"
        >
          Disponible
        </div>
      );
    }
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
  const dias = ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes"];

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
    for (let i = 1; i <= 6; i++) {
      const day = new Date(monday);
      day.setDate(monday.getDate() + i);
      if (day.getDay() !== 6 && day.getDay() !== 0) {
        dates.push(day);
      }
    }
    setWeekDates(dates);
    setWeekRange(`Semana del: ${formatDate(monday)} - ${formatDate(dates[4])}`);
  };

  const changeWeek = (change) => {
    setCurrentWeek((prev) => prev + change);
  };

  const handleCellClick = (event, dia, hora) => {
    const text = event.target.textContent.trim();
    setSelectedCell({ dia, hora });

    const selectedDay = weekDates[dias.indexOf(dia)];

    // Validación para fechas pasadas
    const now = new Date();
    if (selectedDay < now.setHours(0, 0, 0, 0)) {
      Swal.fire({
        title: "Fecha inválida",
        text: "No puedes reservar una fecha que ya ha pasado.",
        icon: "error",
      });
      return;
    }

    // Validación para horas pasadas en el día de hoy
    if (selectedDay.toDateString() === new Date().toDateString()) {
      const currentTime = new Date();
      const [startHour] = hora.split("-").map(Number);
      if (currentTime.getHours() >= startHour) {
        Swal.fire({
          title: "Hora inválida",
          text: "No puedes reservar una hora que ya ha pasado.",
          icon: "error",
        });
        return;
      }
    }

    if (text === "Disponible") {
      setSelectedDate(selectedDay);
      setNewReservation((prev) => ({ ...prev, hora }));
      setShowAddModal(true);
    } else if (text.startsWith("Reservado")) {
      const info = event.target.getAttribute("data-info").split(", ");
      setReservationDetails({
        encargado: info[0].split(": ")[1],
        asunto: info[1].split(": ")[1],
        descripcion: "Descripción aquí",
        hora,
        fecha: formatDate(selectedDay),
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
    const formattedDate = formatDate(selectedDate);
    setReservations((prev) => [
      ...prev,
      {
        dia: selectedCell.dia,
        hora: selectedCell.hora,
        fecha: formattedDate,
        encargado: reservationDetails.encargado,
        asunto: reservationDetails.asunto,
        descripcion: reservationDetails.descripcion,
      },
    ]);
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
    const formattedDate = formatDate(selectedDate);
    setReservations((prev) =>
      prev.filter(
        (res) =>
          res.dia !== selectedCell.dia ||
          res.hora !== selectedCell.hora ||
          res.fecha !== formattedDate,
      ),
    );
    setShowConfirmDelete(false);
    setShowModal(false);
  };

  const handleAddReservation = () => {
    if (selectedCell) {
      setNewReservation((prev) => ({ ...prev, hora: selectedCell.hora }));
      setShowAddModal(true);
    }
  };

  const saveNewReservation = () => {
    if (selectedCell) {
      const formattedDate = formatDate(selectedDate);
      setReservations((prev) => [
        ...prev,
        {
          dia: selectedCell.dia,
          hora: selectedCell.hora,
          fecha: formattedDate,
          encargado: newReservation.encargado,
          asunto: newReservation.asunto,
          descripcion: newReservation.descripcion,
        },
      ]);
      setShowAddModal(false);
      setNewReservation({
        encargado: "",
        asunto: "",
        descripcion: "",
        hora: "",
      });
    }
  };

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
                <td
                  key={`${dia}-${hora}`}
                  data-dia={dia}
                  data-hora={hora}
                  onClick={(e) => handleCellClick(e, dia, hora)}
                >
                  {renderTableCell(dia, hora)}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>

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
