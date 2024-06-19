import "bootstrap/dist/css/bootstrap.min.css";
import "../styles/Reserva.css";
import React, { useState, useEffect } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import axios from "axios";
import { ok, oops, deleteConfirmation, info } from "../utils/Alerts";

const LabReservations = () => {
  const [currentWeek, setCurrentWeek] = useState(0);
  const [weekRange, setWeekRange] = useState("");
  const [weekDates, setWeekDates] = useState([]);
  const [selectedCell, setSelectedCell] = useState(null);
  const [selectedDate, setSelectedDate] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [showAddModal, setShowAddModal] = useState(false);
  const [noHorariosMessage, setNoHorariosMessage] = useState("");

  const [showConfirmDelete, setShowConfirmDelete] = useState(false);
  const [reservationDetails, setReservationDetails] = useState({
    encargado: "",
    asunto: "",
    descripcion: "",
    hora: "",
    fecha: "",
    tipo: "",
    editable: false,
    id: null,
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
  const [aulasLabs, setAulasLabs] = useState([]);
  const [selectedBloque, setSelectedBloque] = useState(1);
  const [selectedTipo, setSelectedTipo] = useState("Aula");
  const [selectedAulaLab, setSelectedAulaLab] = useState();

  const [horarios, setHorarios] = useState([]);
  const [reservas, setReservas] = useState([]);
  const [showContextMenu, setShowContextMenu] = useState(false);
  const [contextMenuPosition, setContextMenuPosition] = useState({
    top: 0,
    left: 0,
  });
  const [feriados, setFeriados] = useState([]);

  const [monday, setMonday] = useState();

  useEffect(() => {
    document.addEventListener("click", handleDocumentClick);
    return () => {
      document.removeEventListener("click", handleDocumentClick);
    };
  }, []);

  const handleDocumentClick = e => {
    if (!e.target.closest(".context-menu") && !e.target.closest("td")) {
      setShowContextMenu(false);
    }
  };
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
    if (aulasLabs.length > 0) {
      setSelectedAulaLab(aulasLabs[0].id);
    }
  }, [aulasLabs]);

  useEffect(() => {
    updateWeekRange();
  }, [currentWeek]);
  useEffect(() => {
    getFeriados();
  }, []);

  const getFeriados = async () => {
    const url = `http://localhost:8080/feriado`;
    try {
      const response = await axios.get(url);
      setFeriados(response.data);
    } catch (error) {
      setFeriados([]); // Limpia los datos si la petición falla
    }
  };

  //TRAE LOS HORARIOS Y LAS RESERVAS
  const getHorarios = async () => {
    let url;
    if (selectedTipo === "Laboratorio") {
      url = `http://localhost:8080/horario/lab/${selectedAulaLab}`;
    } else if (selectedTipo === "Aula") {
      url = `http://localhost:8080/horario/aula/${selectedAulaLab}`;
    } else if (selectedTipo === "Especial") {
      url = `http://localhost:8080/horario/especial/${selectedAulaLab}`;
    }

    try {
      const response = await axios.get(url);
      setHorarios(Array.isArray(response.data) ? response.data : []);
    } catch (error) {
      setHorarios([]); // Limpia los datos si la petición falla
    }

    const formattedDate = formatDate(monday); // Usa formatDate aquí
    const url2 = `http://localhost:8080/reservas?fecha=${formattedDate}&id_espacio=${selectedAulaLab}`;
    try {
      const response = await axios.get(url2);
      setReservas(response.data);
    } catch (error) {
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
    const url = `http://localhost:8080/espacio/bloque/${selectedBloque}`;

    try {
      const response = await axios.get(url);
      let filteredData = [];
      if (selectedTipo == "Aula") {
        filteredData = response.data.filter(item => item.tipo === "Aula");
      } else if (selectedTipo == "Laboratorio") {
        filteredData = response.data.filter(
          item => item.tipo === "Laboratorio"
        );
      } else if (selectedTipo == "Especial") {
        filteredData = response.data.filter(item => item.tipo === "Especial");
      }
      setAulasLabs(filteredData);
      if (filteredData.length === 0) {
        setNoHorariosMessage(
          "No hay espacios disponibles para esta selección."
        );
      } else {
        setNoHorariosMessage("");
      }
    } catch (error) {
      const { message } = error.response.data;
      if (message === "No hay espacios en este bloque") {
        oops(message);
      } else {
        oops("Error al conectar con el servidor");
      }
      setAulasLabs([]); // Limpia los datos si la petición falla
      setNoHorariosMessage(
        "No hay aulas, laboratorios o espacios especiales disponibles."
      );
    }
  };

  const handleCedulaChange = event => {
    const value = event.target.value;
    if (/^\d*$/.test(value) && value.length <= 10) {
      setResponsible(prev => ({ ...prev, cedula: value }));
    }
  };

  const handleNombreChange = event => {
    const value = event.target.value;
    if (/^[a-zA-Z\s]*$/.test(value) && value.length <= 50) {
      setResponsible(prev => ({ ...prev, nombre: value }));
    }
  };

  const handleApellidoChange = event => {
    const value = event.target.value;
    if (/^[a-zA-Z\s]*$/.test(value) && value.length <= 50) {
      setResponsible(prev => ({ ...prev, apellido: value }));
    }
  };

  const handleBloqueChange = event => {
    setSelectedBloque(event.target.value);
  };

  const handleTipoChange = event => {
    setSelectedTipo(event.target.value);
  };

  const handleAulaLabChange = event => {
    setSelectedAulaLab(event.target.value);
  };

  //RENDERIZA LA TABLA PARA PONER COLORES EN RECESOS,RESERVADOS,Y HORARIOS
  const renderTableCell = (dia, hora) => {
    let index;
    switch (dia) {
      case "Lunes":
        index = 0;
        break;
      case "Martes":
        index = 1;
        break;
      case "Miercoles":
        index = 2;
        break;
      case "Jueves":
        index = 3;
        break;
      case "Viernes":
        index = 4;
        break;
      case "Sabado":
        index = 5;
        break;
      default:
        index = -1;
    }

    const fecha = formatDate(weekDates[index]);
    const isFeriado = feriados.some(
      feriado =>
        new Date(feriado.inicio) <= new Date(fecha) &&
        new Date(feriado.fin) >= new Date(fecha)
    );

    if (isFeriado) {
      return (
        <td style={{ backgroundColor: "#d3d3d3", cursor: "not-allowed" }}>
          Feriado
        </td>
      );
    }

    if (hora === "13:00 - 14:00") {
      return <td style={{ backgroundColor: "#ffcccb" }}>Receso</td>;
    }

    const startHour = parseInt(hora.split(":")[0]);
    const horario = horarios.find(
      h => h.dia === dia && parseInt(h.hora) === startHour
    );

    const reserva = reservas.find(
      reserva => reserva.fecha === fecha && parseInt(reserva.hora) === startHour
    );

    if (reserva) {
      return (
        <td
          style={{ backgroundColor: "#ffcccc", cursor: "pointer" }}
          onClick={e => handleCellClick(e, dia, hora)}
        >
          Reservado - {reserva.asunto}
        </td>
      );
    }

    if (!horario) {
      return (
        <td
          style={{ backgroundColor: "#d3ffd3", cursor: "pointer" }}
          onClick={e => handleCellClick(e, dia, hora)}
        >
          Disponible
        </td>
      );
    }

    const colorFondo = horario.reservado ? "#ffcccc" : "#cce7ff";
    const textoCelda = horario.reservado
      ? `Reservado - ${horario.asunto}`
      : horario.nombre;

    return (
      <td
        style={{ backgroundColor: colorFondo, cursor: "pointer" }}
        onClick={e => handleCellClick(e, dia, hora)}
        data-info={`Encargado: ${horario.encargado}, Asunto: ${horario.asunto}`}
      >
        {textoCelda}
      </td>
    );
  };

  const horas = [
    "07:00 - 08:00",
    "08:00 - 09:00",
    "09:00 - 10:00",
    "10:00 - 11:00",
    "11:00 - 12:00",
    "12:00 - 13:00",
    "13:00 - 14:00",
    "14:00 - 15:00",
    "15:00 - 16:00",
    "16:00 - 17:00",
    "17:00 - 18:00",
    "18:00 - 19:00",
    "19:00 - 20:00",
  ];
  const dias = ["Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"];

  const getMonday = d => {
    d = new Date(d);
    const day = d.getDay();
    const diff = d.getDate() - day + (day === 0 ? -6 : 1);
    return new Date(d.setDate(diff));
  };

  //FORMATE LA FECHA
  const formatDate = date => {
    const d = new Date(date);
    const year = d.getFullYear();
    const month = (d.getMonth() + 1).toString().padStart(2, "0"); // Los meses en JS empiezan en 0
    const day = d.getDate().toString().padStart(2, "0");
    return `${year}-${month}-${day}`;
  };

  //OBTIENE LAS SEMANAS SEGUN LA FECHA ACTUAL
  const updateWeekRange = () => {
    const now = new Date();
    now.setDate(now.getDate() + currentWeek * 7);
    const monday = getMonday(now);
    const dates = [monday];
    for (let i = 1; i <= 6; i++) {
      const day = new Date(monday);
      day.setDate(monday.getDate() + i);
      dates.push(day);
    }
    setWeekDates(dates);
    setMonday(monday);
    setWeekRange(`Semana del: ${formatDate(monday)} - ${formatDate(dates[5])}`);
  };

  const changeWeek = change => {
    setCurrentWeek(prev => prev + change);
  };

  const handleCellClick = (event, dia, hora) => {
    const text = event.target.textContent.trim();
    setSelectedCell({ dia, hora });

    const index = dias.indexOf(dia);
    const selectedDay = weekDates[index];

    const formattedDate = formatDate(selectedDay);
    const horaInicio = hora.split("-")[0];
    const reserva = reservas.find(
      reserva =>
        reserva.fecha === formattedDate &&
        parseInt(reserva.hora) === parseInt(horaInicio)
    );

    const isFeriado = feriados.some(
      feriado =>
        new Date(feriado.inicio) <= new Date(formattedDate) &&
        new Date(feriado.fin) >= new Date(formattedDate)
    );

    if (isFeriado) {
      oops("No puedes reservar en un día de feriado.");
      return;
    }

    if (reserva) {
      const now = new Date();
      if (
        selectedDay > now.setHours(0, 0, 0, 0) ||
        (selectedDay.toDateString() === new Date().toDateString() &&
          parseInt(horaInicio) > now.getHours())
      ) {
        setReservationDetails({
          encargado: `${reserva.persona.nombre} ${reserva.persona.apellido}`,
          asunto: reserva.asunto,
          descripcion: reserva.descripcion || "Descripción aquí",
          hora: hora,
          fecha: formattedDate,
          tipo: reserva.persona.tipo || "N/A",
          editable: false,
          id: reserva.id,
        });
        setContextMenuPosition({ top: event.pageY, left: event.pageX });
        setShowContextMenu(true);
        return;
      } else {
        setReservationDetails({
          encargado: `${reserva.persona.nombre} ${reserva.persona.apellido}`,
          asunto: reserva.asunto,
          descripcion: reserva.descripcion || "Descripción aquí",
          hora: hora,
          fecha: reserva.fecha,
          tipo: reserva.persona.tipo || "N/A",
          editable: false,
          id: reserva.id,
        });
        setShowModal(true);
        return;
      }
    } else {
      const now = new Date();
      if (selectedDay < now.setHours(0, 0, 0, 0)) {
        oops("No puedes reservar en una fecha pasada.");
        return;
      }

      if (selectedDay.toDateString() === new Date().toDateString()) {
        const currentTime = new Date();
        const [startHour] = hora.split("-").map(Number);
        if (currentTime.getHours() >= startHour) {
          oops("No puedes reservar una hora pasada.");
          return;
        }
      }

      if (text === "Disponible") {
        setSelectedDate(selectedDay);
        setNewReservation({
          encargado: "",
          asunto: "",
          descripcion: "",
          hora: horaInicio,
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
        setShowAddModal(true);
      }
    }
  };

  const enableEditing = () => {
    setReservationDetails(prev => ({
      ...prev,
      editable: true,
    }));
  };

  const confirmDelete = async () => {
    deleteReservation();
  };

  const deleteReservation = () => {
    if (reservationDetails.id) {
      handleDeleteReservation(reservationDetails.id);
    }
  };

  //ELIMINAR LA RESERVA DE LA BASE
  const handleDeleteReservation = async reservationId => {
    const isConfirmed = await deleteConfirmation();
    try {
      if (isConfirmed) {
        await axios.delete(`http://localhost:8080/reservas/${reservationId}`);
        // Actualiza el estado de reservas después de eliminar
        setReservas(prev =>
          prev.filter(reserva => reserva.id !== reservationId)
        );
        ok("Registro eliminado exitosamente.");
        setShowConfirmDelete(false);
        setShowModal(false);
      }
    } catch (error) {
      oops("No se pudo eliminar el registro.Por favor, inténtelo de nuevo.");
    }
  };

  const updateReservation = async () => {
    const updatedReservation = {
      id: reservationDetails.id,
      asunto: reservationDetails.asunto,
      descripcion:
        reservationDetails.descripcion || "Sin descripción proporcionada",
    };

    try {
      await axios.put(
        `http://localhost:8080/reservas/${reservationDetails.id}`,
        updatedReservation
      );
      ok("Registro actualizado exitosamente.");

      // Actualiza la lista de reservas localmente
      setReservas(prev =>
        prev.map(reserva =>
          reserva.id === reservationDetails.id
            ? {
                ...reserva,
                asunto: reservationDetails.asunto,
                descripcion:
                  reservationDetails.descripcion ||
                  "Sin descripción proporcionada",
              }
            : reserva
        )
      );

      setShowModal(false);
    } catch (error) {
      oops("No se pudo actualizar el registro. Por favor, inténtelo de nuevo.");
    }
  };

  const getDayNameFromDate = date => {
    const dateObj = new Date(date);
    const days = [
      "Domingo",
      "Lunes",
      "Martes",
      "Miercoles",
      "Jueves",
      "Viernes",
      "Sábado",
    ];
    return days[dateObj.getDay()];
  };

  const getDayNameFromDateR = date => {
    // Añade 'T00:00:00Z' para asegurarte de que se trate como UTC
    const dateObj = new Date(date + "T00:00:00Z");
    const days = [
      "Domingo",
      "Lunes",
      "Martes",
      "Miercoles",
      "Jueves",
      "Viernes",
      "Sábado",
    ];
    return days[dateObj.getUTCDay()];
  };

  //GUARDA LA RESERVA
  const saveNewReservation = async () => {
    if (selectedCell && selectedAulaLab) {
      const formattedDate = selectedDate.toISOString();

      let savedResponsible = responsible;

      if (!isExistingResponsible) {
        try {
          const response = await axios.post(
            "http://localhost:8080/person",
            responsible
          );

          savedResponsible = response.data;
          setResponsible(savedResponsible);
          setNewReservation(prev => ({
            ...prev,
            encargado: `${savedResponsible.nombre} ${savedResponsible.apellido}`,
          }));
        } catch (error) {
          oops(
            "No se pudo guardar el registro. Por favor, inténtelo de nuevo."
          );
          return;
        }
      }

      const reserva = {
        hora: selectedCell.hora.split("-")[0],
        fecha: formattedDate,
        asunto: newReservation.asunto,
        descripcion:
          newReservation.descripcion || "Sin descripción proporcionada",
        id_persona: savedResponsible.id,
        id_espacio: selectedAulaLab,
      };
      console.log(reserva);

      try {
        const response = await axios.post(
          "http://localhost:8080/reservas",
          reserva
        );
        await getHorarios();
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
        ok("Registro guardado exitosamente.");
      } catch (error) {
        console.error("Error al guardar la reserva:", error.response);
        oops("No se pudo guardar el registro. Por favor, inténtelo de nuevo.");
      }
    }
  };

  const handleResponsibleChange = event => {
    const { name, value } = event.target;
    if (name === "telefono") {
      // Validar que solo contenga números
      if (/^\d*$/.test(value)) {
        setResponsible(prev => ({ ...prev, [name]: value }));
      }
    } else if (name === "nombre" || name === "apellido") {
      // Validar que solo contenga letras y espacios
      if (/^[a-zA-Z\s]*$/.test(value)) {
        setResponsible(prev => ({ ...prev, [name]: value }));
      }
    } else {
      setResponsible(prev => ({ ...prev, [name]: value }));
    }
  };

  //BUSCAR  EL RESPONSABLE PARA EL MODAL DE AGREGAR RESERVA
  const searchResponsible = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/person/${responsible.cedula}`
      );
      setResponsible(response.data);
      setNewReservation(prev => ({
        ...prev,
        encargado: `${response.data.nombre} ${response.data.apellido}`,
      }));
      setIsExistingResponsible(true);
      setShowAdditionalFields(false); // Asegúrate de ocultar los campos adicionales si se encuentra el responsable
    } catch (error) {
      info(
        "No se encontró el responsable con esa cédula. Puede agregarlo a continuación."
      );
      setResponsible(prev => ({
        ...prev,
        nombre: "",
        apellido: "",
        telefono: "",
        tipo: "",
      }));
      setIsExistingResponsible(false);
      setShowAdditionalFields(true); // Muestra los campos adicionales si no se encuentra el responsable
    }
  };

  return (
    <div className="container mt-3">
      <div className="header text-center">
        <h2>Reservas </h2>
      </div>
      <div className="row">
        <div className="col-md-4">
          <label>Bloque/Edificio</label>
          <Form.Select
            className="form-control"
            id="bloqueSelect"
            value={selectedBloque}
            onChange={handleBloqueChange}
          >
            {bloques.map(bloque => (
              <option key={bloque.id} value={bloque.id}>
                {bloque.nombre}
              </option>
            ))}
          </Form.Select>
        </div>
        <div className="col-md-4">
          <label>Tipo</label>
          <Form.Select
            className="form-control"
            id="tipoSelect"
            value={selectedTipo}
            onChange={handleTipoChange}
          >
            <option value="Aula">Aulas</option>
            <option value="Laboratorio">Laboratorios</option>
            <option value="Especial">Especial</option>
          </Form.Select>
        </div>
        <div className="col-md-4">
          <label>Aula/Laboratorio</label>
          <Form.Select
            className="form-control"
            id="aulaLabSelect"
            value={selectedAulaLab}
            onChange={handleAulaLabChange}
          >
            {aulasLabs.map(aulaLab => (
              <option key={aulaLab.id} value={aulaLab.id}>
                {aulaLab.nombre}
              </option>
            ))}
          </Form.Select>
        </div>
        <div className="row justify-content-center align-items-center mt-3">
          <div className="col-auto">
            <button className="btn mx-2" onClick={() => changeWeek(-1)}>
              &lt;&lt;
            </button>
          </div>
          <div className="col-auto mx-2">
            <span id="weekRange" className="week-display">
              {weekRange}
            </span>
          </div>
          <div className="col-auto">
            <button className="btn mx-2" onClick={() => changeWeek(1)}>
              &gt;&gt;
            </button>
          </div>
        </div>
      </div>

      {noHorariosMessage ? (
        <div className="alert alert-info text-center mt-3" role="alert">
          {noHorariosMessage}
        </div>
      ) : (
        <table className="table table-bordered mt-4 table-centered">
          <thead>
            <tr>
              <th>Horas</th>
              <th>Lunes</th>
              <th>Martes</th>
              <th>Miercoles</th>
              <th>Jueves</th>
              <th>Viernes</th>
              <th>Sabado</th>
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
      )}

      <div
        className="context-menu"
        id="context-menu"
        style={{
          display: showContextMenu ? "block" : "none",
          top: contextMenuPosition.top,
          left: contextMenuPosition.left,
        }}
      >
        <Button
          variant="custom"
          id="ver-btn"
          onClick={() => {
            setReservationDetails(prev => ({ ...prev, editable: false })); // Asegura que los campos no sean editables
            setShowModal(true);
            setShowContextMenu(false);
          }}
        >
          Ver
        </Button>
        <Button
          variant="custom"
          id="editar-btn"
          onClick={() => {
            enableEditing();
            setReservationDetails(prev => ({ ...prev, editable: true })); // Hace los campos editables
            setShowModal(true);
            setShowContextMenu(false);
          }}
        >
          Editar
        </Button>
        <Button
          variant="custom"
          id="eliminar-btn"
          onClick={() => {
            confirmDelete();
            setShowContextMenu(false);
          }}
        >
          Eliminar
        </Button>
      </div>

      {/* Modal para detalles de reserva */}
      <Modal show={showModal} onHide={() => setShowModal(false)} size="md">
        <Modal.Header closeButton>
          <Modal.Title>Detalles de la Reserva</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form id="reservationForm">
            <section>
              <h5>Información de la Reserva</h5>
              <div className="row">
                <div className="col-md-6">
                  <label htmlFor="dayName" className="form-label">
                    Día
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="dayName"
                    value={
                      reservationDetails.fecha
                        ? getDayNameFromDateR(reservationDetails.fecha)
                        : ""
                    }
                    disabled
                  />
                </div>
                <div className="col-md-6">
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
              <div className="row">
                <div className="col-md-6">
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
                <div className="col-md-6">
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
                  onChange={e =>
                    setReservationDetails({
                      ...reservationDetails,
                      asunto: e.target.value,
                    })
                  }
                  disabled={!reservationDetails.editable} // Asegúrate de que esto depende de `editable`
                />
              </div>
              <div className="mb-3">
                <label htmlFor="descripcion" className="form-label">
                  Descripción
                </label>
                <textarea
                  className="form-control"
                  id="descripcion"
                  value={reservationDetails.descripcion}
                  onChange={e =>
                    setReservationDetails({
                      ...reservationDetails,
                      descripcion: e.target.value,
                    })
                  }
                  disabled={!reservationDetails.editable} // Asegúrate de que esto depende de `editable`
                />
              </div>
            </section>
          </form>
        </Modal.Body>
        <Modal.Footer>
          {reservationDetails.editable ? (
            <>
              <Button variant="success" onClick={updateReservation}>
                Guardar
              </Button>
            </>
          ) : null}
          <Button variant="secondary" onClick={() => setShowModal(false)}>
            Cerrar
          </Button>
        </Modal.Footer>
      </Modal>

      <Modal
        show={showAddModal}
        onHide={() => setShowAddModal(false)}
        size="md"
      >
        <Modal.Header closeButton>
          <Modal.Title>Agregar Reserva</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form id="newReservationForm">
            <section>
              <h5>Responsable</h5>
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
                    onChange={handleCedulaChange}
                  />
                  <button
                    className="btn btn-secondary"
                    type="button"
                    onClick={searchResponsible}
                  >
                    Buscar
                  </button>
                </div>
              </div>
              {isExistingResponsible && (
                <div className="mb-3">
                  <label htmlFor="newEncargado" className="form-label">
                    Nombre
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="newEncargado"
                    value={newReservation.encargado}
                    readOnly
                  />
                </div>
              )}
              {showAdditionalFields && (
                <>
                  <div className="row">
                    <div className="col-md-6">
                      <div className="mb-3">
                        <label htmlFor="nombre" className="form-label">
                          Nombre
                        </label>
                        <input
                          type="text"
                          maxLength={30}
                          className="form-control"
                          id="nombre"
                          name="nombre"
                          value={responsible.nombre}
                          onChange={handleResponsibleChange}
                        />
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="mb-3">
                        <label htmlFor="apellido" className="form-label">
                          Apellido
                        </label>
                        <input
                          type="text"
                          maxLength={30}
                          className="form-control"
                          id="apellido"
                          name="apellido"
                          value={responsible.apellido}
                          onChange={handleResponsibleChange}
                        />
                      </div>
                    </div>
                  </div>
                  <div className="row">
                    <div className="col-md-6">
                      <div className="mb-3">
                        <label htmlFor="telefono" className="form-label">
                          Teléfono
                        </label>
                        <input
                          maxLength={10}
                          type="text"
                          className="form-control"
                          id="telefono"
                          name="telefono"
                          value={responsible.telefono}
                          onChange={handleResponsibleChange}
                        />
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="mb-3">
                        <label htmlFor="tipo" className="form-label">
                          Tipo
                        </label>
                        <Form.Select
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
                        </Form.Select>
                      </div>
                    </div>
                  </div>
                </>
              )}
            </section>
            <hr />
            <section>
              <h5>Reserva</h5>
              <div className="mb-3 row">
                <div className="col-md-6">
                  <label htmlFor="dayName" className="form-label">
                    Día
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="dayName"
                    value={selectedDate ? getDayNameFromDate(selectedDate) : ""}
                    disabled
                  />
                </div>
                <div className="col-md-6">
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
                  onChange={e =>
                    setNewReservation(prev => ({
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
                <textarea
                  className="form-control"
                  id="newDescripcion"
                  value={newReservation.descripcion}
                  onChange={e =>
                    setNewReservation(prev => ({
                      ...prev,
                      descripcion: e.target.value,
                    }))
                  }
                />
              </div>
            </section>
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
    </div>
  );
};

export default LabReservations;
