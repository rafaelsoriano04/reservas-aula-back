import 'bootstrap/dist/css/bootstrap.min.css';
import './Reserva.css'; // Asegúrate de tener este archivo en la misma carpeta
import React, { useState, useEffect } from 'react';
import { Modal, Button } from 'react-bootstrap';

const LabReservations = () => {
  const [currentWeek, setCurrentWeek] = useState(0);
  const [weekRange, setWeekRange] = useState('');
  const [selectedCell, setSelectedCell] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [showAddModal, setShowAddModal] = useState(false);
  const [showConfirmDelete, setShowConfirmDelete] = useState(false);
  const [reservationDetails, setReservationDetails] = useState({
    encargado: '',
    asunto: '',
    descripcion: '',
    hora: '',
    editable: false,
  });
  const [newReservation, setNewReservation] = useState({
    encargado: '',
    asunto: '',
    descripcion: '',
    hora: '',
  });

  useEffect(() => {
    updateWeekRange();
  }, [currentWeek]);

  const getMonday = (d) => {
    d = new Date(d);
    const day = d.getDay();
    const diff = d.getDate() - day + (day === 0 ? -6 : 1);
    return new Date(d.setDate(diff));
  };

  const formatDate = (date) => {
    const d = new Date(date);
    let month = '' + (d.getMonth() + 1);
    let day = '' + d.getDate();
    const year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [day, month, year].join('-');
  };

  const updateWeekRange = (addWeeks = 0) => {
    const now = new Date();
    now.setDate(now.getDate() + currentWeek * 7);
    const monday = getMonday(now);
    const saturday = new Date(monday);
    saturday.setDate(monday.getDate() + 5);

    setWeekRange(`Semana del: ${formatDate(monday)} - ${formatDate(saturday)}`);
  };

  const changeWeek = (change) => {
    setCurrentWeek((prev) => prev + change);
  };

  const handleCellClick = (event, cell) => {
    const text = cell.textContent.trim();
    const menu = document.getElementById('context-menu');
    setSelectedCell(cell);

    if (text === 'Disponible') {
      menu.style.display = 'block';
      menu.style.left = `${event.pageX}px`;
      menu.style.top = `${event.pageY}px`;
      event.stopPropagation();
    } else {
      menu.style.display = 'none';
    }

    if (text.startsWith('Reservado')) {
      const info = cell.getAttribute('data-info').split(', ');
      setReservationDetails({
        encargado: info[0].split(': ')[1],
        asunto: info[1].split(': ')[1],
        descripcion: 'Descripción aquí',
        hora: cell.parentElement.firstChild.textContent,
        editable: false,
      });
      setShowModal(true);
    }
  };

  const enableEditing = () => {
    document.getElementById('encargado').disabled = false;
    document.getElementById('asunto').disabled = false;
    document.getElementById('descripcion').disabled = false;
    const saveBtn = document.createElement('button');
    saveBtn.textContent = 'Guardar';
    saveBtn.classList.add('btn', 'btn-success');
    saveBtn.onclick = saveReservation;
    saveBtn.id = 'saveBtn';
    const footer = document.querySelector('.modal-footer');
    footer.insertBefore(saveBtn, footer.firstChild);
    document.querySelector('.btn-primary').style.display = 'none';
  };

  const saveReservation = () => {
    selectedCell.setAttribute(
      'data-info',
      `Encargado: ${reservationDetails.encargado}, Asunto: ${reservationDetails.asunto}`
    );
    selectedCell.textContent = 'Reservado - Descripción';
    setShowModal(false);
    setReservationDetails({
      encargado: '',
      asunto: '',
      descripcion: '',
      hora: '',
      editable: false,
    });
  };

  const confirmDelete = () => {
    setShowConfirmDelete(true);
  };

  const deleteReservation = () => {
    selectedCell.textContent = 'Disponible';
    selectedCell.removeAttribute('data-info');
    setShowConfirmDelete(false);
    setShowModal(false);
  };

  const handleAddReservation = () => {
    if (selectedCell) {
      const hora = selectedCell.closest('tr').firstChild.textContent;
      setNewReservation((prev) => ({ ...prev, hora }));
      setShowAddModal(true);
    }
  };

  const saveNewReservation = () => {
    if (selectedCell) {
      selectedCell.setAttribute(
        'data-info',
        `Encargado: ${newReservation.encargado}, Asunto: ${newReservation.asunto}`
      );
      selectedCell.textContent = 'Reservado - Descripción';
    }
    setShowAddModal(false);
    setNewReservation({
      encargado: '',
      asunto: '',
      descripcion: '',
      hora: '',
    });
  };

  return (
    <div className="container mt-3">
      <div className="header text-center">
        <h2>RESERVAS DE LABORATORIO</h2>
      </div>
      <div className="row">
        <div className="col-md-4">
          <label>Bloque/Edificio</label>
          <select className="form-control" id="edificioSelect">
            <option value="Edificio A,1,30">Edificio A - Piso 1 - Capacidad 30</option>
            <option value="Edificio B,2,25">Edificio B - Piso 2 - Capacidad 25</option>
          </select>
        </div>
        <div className="col-md-4">
          <label>Laboratorio/Aula</label>
          <select className="form-control" id="aulaSelect">
            <option value="Aula 101,1,20">Aula 101 - Piso 1 - Capacidad 20</option>
            <option value="Aula 102,2,25">Aula 102 - Piso 2 - Capacidad 25</option>
          </select>
        </div>
        <div className="col-md-4 nav-buttons">
          <button className="btn" onClick={() => changeWeek(-1)}>&lt;&lt;</button>
          <span id="weekRange" className="week-display">{weekRange}</span>
          <button className="btn" onClick={() => changeWeek(1)}>&gt;&gt;</button>
        </div>
      </div>

      <table className="table table-bordered text-center mt-3">
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
          <tr>
            <td>07:00-08:00</td>
            <td data-info="Encargado: Dr. Juan, Asunto: Matemáticas" onClick={(e) => handleCellClick(e, e.target)}>Disponible</td>
            <td data-info="Encargado: Dr. Ana, Asunto: Física" onClick={(e) => handleCellClick(e, e.target)}>Reservado - Descripción</td>
            <td onClick={(e) => handleCellClick(e, e.target)}>Asunto - Encargado</td>
            <td onClick={(e) => handleCellClick(e, e.target)}>Asunto - Encargado</td>
            <td data-info="Encargado: Dr. Pedro, Asunto: Química" onClick={(e) => handleCellClick(e, e.target)}>Disponible</td>
            <td data-info="Encargado: Dra. Luisa, Asunto: Biología" onClick={(e) => handleCellClick(e, e.target)}>Reservado - Descripción</td>
          </tr>
          {/* Más filas aquí */}
        </tbody>
      </table>

      <div id="context-menu" className="context-menu">
        <button className="btn btn-sm" onClick={handleAddReservation}>Reservar</button>
      </div>

      {/* Modal para detalles de reserva */}
      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Detalles de la Reserva</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form id="reservationForm">
            <div className="mb-3">
              <label htmlFor="encargado" className="form-label">Responsable</label>
              <input type="text" className="form-control" id="encargado" value={reservationDetails.encargado} disabled={!reservationDetails.editable} onChange={(e) => setReservationDetails((prev) => ({ ...prev, encargado: e.target.value }))} />
            </div>
            <div className="mb-3">
              <label htmlFor="asunto" className="form-label">Asunto</label>
              <input type="text" className="form-control" id="asunto" value={reservationDetails.asunto} disabled={!reservationDetails.editable} onChange={(e) => setReservationDetails((prev) => ({ ...prev, asunto: e.target.value }))} />
            </div>
            <div className="mb-3">
              <label htmlFor="descripcion" className="form-label">Descripción</label>
              <input type="text" className="form-control" id="descripcion" value={reservationDetails.descripcion} disabled={!reservationDetails.editable} onChange={(e) => setReservationDetails((prev) => ({ ...prev, descripcion: e.target.value }))} />
            </div>
            <div className="mb-3">
              <label htmlFor="hora" className="form-label">Hora</label>
              <input type="text" className="form-control" id="hora" value={reservationDetails.hora} disabled />
            </div>
          </form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="primary" onClick={enableEditing}>Modificar</Button>
          <Button variant="danger" onClick={confirmDelete}>Eliminar</Button>
          <Button variant="secondary" onClick={() => setShowModal(false)}>Cerrar</Button>
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
              <label htmlFor="newEncargado" className="form-label">Responsable</label>
              <input type="text" className="form-control" id="newEncargado" value={newReservation.encargado} onChange={(e) => setNewReservation((prev) => ({ ...prev, encargado: e.target.value }))} />
            </div>
            <div className="mb-3">
              <label htmlFor="newAsunto" className="form-label">Asunto</label>
              <input type="text" className="form-control" id="newAsunto" value={newReservation.asunto} onChange={(e) => setNewReservation((prev) => ({ ...prev, asunto: e.target.value }))} />
            </div>
            <div className="mb-3">
              <label htmlFor="newDescripcion" className="form-label">Descripción</label>
              <input type="text" className="form-control" id="newDescripcion" value={newReservation.descripcion} onChange={(e) => setNewReservation((prev) => ({ ...prev, descripcion: e.target.value }))} />
            </div>
            <div className="mb-3">
              <label htmlFor="newHora" className="form-label">Hora</label>
              <input type="text" className="form-control" id="newHora" value={newReservation.hora} disabled />
            </div>
          </form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="success" onClick={saveNewReservation}>Agregar</Button>
          <Button variant="secondary" onClick={() => setShowAddModal(false)}>Cerrar</Button>
        </Modal.Footer>
      </Modal>

      {/* Modal para confirmación de eliminación */}
      <Modal show={showConfirmDelete} onHide={() => setShowConfirmDelete(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Confirmar Eliminación</Modal.Title>
        </Modal.Header>
        <Modal.Body>¿Estás seguro de que deseas eliminar esta reserva?</Modal.Body>
        <Modal.Footer>
          <Button variant="danger" onClick={deleteReservation}>Eliminar</Button>
          <Button variant="secondary" onClick={() => setShowConfirmDelete(false)}>Cancelar</Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default LabReservations;
