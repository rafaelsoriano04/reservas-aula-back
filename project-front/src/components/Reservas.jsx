import "bootstrap/dist/css/bootstrap.min.css";
import "../styles/Reserva.css";
import React, { useState, useEffect } from "react";
import { Modal, Button, Form } from "react-bootstrap";
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
    tipo: "",
    editable: false,
  });
  const [newReservation, setNewReservation] = useState({
    encargado: "",
    asunto: "",
    descripcion: "",
    hora: "",
  });
  const [responsible, setResponsible] = useState({
    cedula: "",
    nombre: "",
    apellido: "",
    telefono: "",
    tipo: "",
  });
  const [isExistingResponsible, setIsExistingResponsible] = useState(false);
  const [showAdditionalFields, setShowAdditionalFields] = useState(false);

  const [bloques, setBloques] = useState([]);
  const [horarios, setHorarios] = useState([]);
  const [reservations, setReservations] = useState([]);
  const [selectedBloque, setSelectedBloque] = useState(1);
  const [aulasLabs, setAulasLabs] = useState([]);
  const [selectedTipo, setSelectedTipo] = useState("Aula");
  const [selectedAulaLab, setSelectedAulaLab] = useState();

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

  useEffect(() => {
    updateWeekRange();
  }, [currentWeek]);

  const getHorarios = async () => {
    const url =
      selectedTipo === "Laboratorio"
        ? `http://localhost:8080/horario/lab/${selectedAulaLab}`
        : `http://localhost:8080/horario/aula/${selectedAulaLab}`;
    console.log(url);

    try {
      const response = await axios.get(url);
      console.log(response.data);
      setHorarios(Array.isArray(response.data) ? response.data : []);
    } catch (error) {
      const { message } = error.response.data;
      console.log(message);
      setHorarios([]);
    }
  };

  const fetchHorarios = async () => {
    const formattedDate = formatDate(new Date()); // Asegúrate de enviar la fecha correcta según tu lógica de negocio
    const url = `http://localhost:8080/reservas/espacio/${selectedAulaLab}/fecha/${formattedDate}`;

    try {
        const response = await axios.get(url);
        const fetchedHorarios = response.data; // Suponiendo que la respuesta es una lista de objetos de reserva
        setHorarios(fetchedHorarios);
    } catch (error) {
        console.error("Error al recuperar horarios y reservas:", error);
        Swal.fire("Error", "No se pudieron cargar los horarios y reservas.", "error");
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
    const url = `http://localhost:8080/espacio/bloque/${selectedBloque}`;

    try {
      const response = await axios.get(url);
      let filteredData = [];
      if (selectedTipo === "Aula") {
        filteredData = response.data.filter(item => item.tipo === "Aula");
      } else {
        filteredData = response.data.filter(item => item.tipo === "Laboratorio");
      }
      setAulasLabs(filteredData);
    } catch (error) {
      const { message } = error.response.data;
      if (message === "No hay espacios en este bloque") {
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
      setAulasLabs([]);
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
    if (hora === "13-14") {
      return <td style={{ backgroundColor: "#ffcccb" }}>Receso</td>;
    }

    const horario = horarios.find(
      h => h.dia === dia && h.hora === hora.split("-")[0]
    );

    if (!horario) {
      return (
        <td
          style={{ backgroundColor: "#d3ffd3", cursor: "pointer" }}
          onClick={(e) => handleCellClick(e, dia, hora)}
        >
          Disponible
        </td>
      );
    }

    const colorFondo = horario.reservado ? "#ffcccc" : "#cce7ff";
    const textoCelda = horario.reservado ? `Reservado - ${horario.asunto}` : horario.nombre;


    return (
      <td
        style={{ backgroundColor: colorFondo, cursor: "pointer" }}
        onClick={(e) => handleCellClick(e, dia, hora)}
        data-info={`Encargado: ${horario.encargado}, Asunto: ${horario.asunto}`}
      >
        {textoCelda}
      </td>
    );
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

    const now = new Date();
    if (selectedDay < now.setHours(0, 0, 0, 0)) {
      Swal.fire({
        title: "Fecha inválida",
        text: "No puedes reservar una fecha que ya ha pasado.",
        icon: "error",
      });
      return;
    }

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
        descripcion: event.target.textContent.split(" - ")[1] || "Descripción aquí",
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
          res.fecha !== formattedDate
      )
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

  const saveNewReservation = async () => {
    if (selectedCell && selectedAulaLab) {
        const formattedDate = selectedDate.toISOString().split('T')[0];

        let savedResponsible = responsible;

        if (!isExistingResponsible) {
            try {
                const response = await axios.post("http://localhost:8080/person", responsible);
                savedResponsible = response.data;
                setResponsible(savedResponsible);
                setNewReservation((prev) => ({
                    ...prev,
                    encargado: `${savedResponsible.nombre} ${savedResponsible.apellido}`,
                }));
            } catch (error) {
                Swal.fire({
                    title: "Error",
                    text: `Hubo un error al guardar el nuevo responsable: ${error.response?.data?.message || error.message}`,
                    icon: "error",
                });
                return;
            }
        }

        const reserva = {
            hora: selectedCell.hora.split("-")[0],
            fecha: formattedDate,
            id_persona: savedResponsible.id,
            id_espacio: selectedAulaLab,
        };

        try {
            const response = await axios.post("http://localhost:8080/reservas", reserva);
            const newHorarios = [
                ...horarios,
                {
                    dia: selectedCell.dia,
                    hora: selectedCell.hora.split("-")[0],
                    encargado: `${savedResponsible.nombre} ${savedResponsible.apellido}`,
                    asunto: newReservation.asunto,
                    nombre: `Reservado - ${newReservation.asunto}`,
                    reservado: true,
                },
            ];

            setHorarios(newHorarios);
            setShowAddModal(false);
            setNewReservation({
                encargado: "",
                asunto: "",
                descripcion: "",
                hora: "",
            });
            setResponsible({
                cedula: "",
                nombre: "",
                apellido: "",
                telefono: "",
                tipo: "",
            });
            setIsExistingResponsible(false);
            setShowAdditionalFields(false);
        } catch (error) {
            console.error('Error al guardar la reserva:', error.response);
            Swal.fire({
                title: "Error",
                text: `Hubo un error al guardar la reserva: ${error.response?.data?.message || error.message}`,
                icon: "error",
            });
        }
    }
};

  
  

  const handleResponsibleChange = (event) => {
    const { name, value } = event.target;
    setResponsible((prev) => ({ ...prev, [name]: value }));
  };

  const searchResponsible = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/person/${responsible.cedula}`);
      setResponsible(response.data);
      setNewReservation((prev) => ({
        ...prev,
        encargado: `${response.data.nombre} ${response.data.apellido}`,
      }));
      setIsExistingResponsible(true);
      setShowAdditionalFields(false);
    } catch (error) {
      Swal.fire({
        title: "No encontrado",
        text: "No se encontró ningún responsable con esa cédula. Puede agregarlo a continuación.",
        icon: "info",
      });
      setIsExistingResponsible(false);
      setShowAdditionalFields(true);
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
            <option value="Aula">Aulas</option>
            <option value="Laboratorio">Laboratorios</option>
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
            <option value="">Seleccione una opción</option>
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

      <table className="table table-bordered mt-4 table-centered">
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
          {horas.map(hora => (
            <tr key={hora}>
              <td>{hora}</td>
              {dias.map(dia => (
                <React.Fragment key={`${dia}-${hora}`}>
                  {renderTableCell(dia, hora)}
                </React.Fragment>
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
                disabled
              />
            </div>
            <div className="mb-3">
              <label htmlFor="tipo" className="form-label">
                Tipo
              </label>
              <input
                type="text"
                className="form-control"
                id="tipo"
                value={reservationDetails.tipo}
                disabled
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
                disabled
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
                disabled
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
              <label htmlFor="cedula" className="form-label">
                Cédula
              </label>
              <div className="input-group">
                <input
                  type="text"
                  className="form-control"
                  id="cedula"
                  name="cedula"
                  value={responsible.cedula}
                  onChange={handleResponsibleChange}
                />
                <button className="btn btn-secondary" type="button" onClick={searchResponsible}>
                  Buscar
                </button>
              </div>
            </div>
            {isExistingResponsible ? (
              <div className="mb-3">
                <label htmlFor="newEncargado" className="form-label">
                  Responsable
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="newEncargado"
                  value={newReservation.encargado}
                  readOnly
                />
              </div>
            ) : null}
            {showAdditionalFields && (
              <>
                <div className="mb-3">
                  <label htmlFor="nombre" className="form-label">
                    Nombre
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="nombre"
                    name="nombre"
                    value={responsible.nombre}
                    onChange={handleResponsibleChange}
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="apellido" className="form-label">
                    Apellido
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="apellido"
                    name="apellido"
                    value={responsible.apellido}
                    onChange={handleResponsibleChange}
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="telefono" className="form-label">
                    Teléfono
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="telefono"
                    name="telefono"
                    value={responsible.telefono}
                    onChange={handleResponsibleChange}
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="tipo" className="form-label">
                    Tipo
                  </label>
                  <select
                    className="form-control"
                    id="tipo"
                    name="tipo"
                    value={responsible.tipo}
                    onChange={handleResponsibleChange}
                  >
                    <option value="">Seleccione un tipo</option>
                    <option value="Administrativo">Administrativo</option>
                    <option value="Estudiante">Estudiante</option>
                    <option value="Docente">Docente</option>
                    <option value="Invitado">Invitado</option>
                  </select>
                </div>
              </>
            )}
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
      <Modal show={showConfirmDelete} onHide={() => setShowConfirmDelete(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Confirmar Eliminación</Modal.Title>
        </Modal.Header>
        <Modal.Body>¿Estás seguro de que deseas eliminar esta reserva?</Modal.Body>
        <Modal.Footer>
          <Button variant="danger" onClick={deleteReservation}>
            Eliminar
          </Button>
          <Button variant="secondary" onClick={() => setShowConfirmDelete(false)}>
            Cancelar
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default LabReservations;
