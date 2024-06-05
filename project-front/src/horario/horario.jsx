import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Button, Form } from "react-bootstrap";
import "../horario/horario.css";
import axios from "axios";
import Swal from "sweetalert2";

function Horarios() {
  // Variables reactivas
  const [bloques, setBloques] = useState([]);
  const [horarios, setHorarios] = useState([]);
  const [selectedBloque, setSelectedBloque] = useState(1);
  const [aulasLabs, setAulasLabs] = useState([]);
  const [selectedTipo, setSelectedTipo] = useState("Aula");
  const [selectedAulaLab, setSelectedAulaLab] = useState();

  // Metodos
  const handleCellClick = (e, rowIndex, cellIndex) => {
    setSelectedCell({ rowIndex, cellIndex });
    setContextMenuPosition({ top: e.pageY, left: e.pageX });
    setShowContextMenu(true);
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

  const handleBloqueChange = (event) => {
    setSelectedBloque(event.target.value);
  };

  const handleTipoChange = (event) => {
    setSelectedTipo(event.target.value);
  };

  const handleAulaLabChange = (event) => {
    setSelectedAulaLab(event.target.value);
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

  return (
    <div className="container-fluid">
      <div className="container mt-4">
        <div className="row">
          <div className="col-md-12">
            <Form id="form-horarios">
              <div className="form-container">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="bloque">Bloque:</Form.Label>
                  <Form.Control
                    as="select"
                    id="bloque"
                    className="form-control"
                    value={selectedBloque}
                    onChange={handleBloqueChange}
                  >
                    {bloques.map((bloque) => (
                      <option key={bloque.id} value={bloque.id}>
                        {bloque.nombre}
                      </option>
                    ))}
                  </Form.Control>
                </Form.Group>

                <Form.Group className="form-group col-md-6">
                  <Form.Label htmlFor="aula">Tipo:</Form.Label>
                  <Form.Control
                    as="select"
                    id="aula"
                    className="form-control"
                    value={selectedTipo}
                    onChange={handleTipoChange}
                    name="aula"
                  >
                    <option value="Aulas">Aulas</option>
                    <option value="Laboratorios">Laboratorios</option>
                  </Form.Control>
                </Form.Group>

                <Form.Group className="form-group">
                  <Form.Label htmlFor="aula-lab">Aula/Laboratorio:</Form.Label>
                  <Form.Control
                    as="select"
                    id="aula-lab"
                    className="form-control"
                    value={selectedAulaLab}
                    onChange={handleAulaLabChange}
                    name="aulaLab"
                  >
                    {aulasLabs.map((aulaLab) => (
                      <option key={aulaLab.id} value={aulaLab.id}>
                        {aulaLab.nombre}
                      </option>
                    ))}
                  </Form.Control>
                </Form.Group>
              </div>
              <h5 className="nuevo-horario">Nuevo Horario</h5>
              <div className="form-container">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="dia">Día:</Form.Label>
                  <Form.Control as="select" id="dia" className="form-control">
                    <option>Lunes</option>
                    <option>Martes</option>
                    <option>Miércoles</option>
                    <option>Jueves</option>
                    <option>Viernes</option>
                    <option>Sábado</option>
                    <option>Domingo</option>
                  </Form.Control>
                </Form.Group>
                <Form.Group className="form-group">
                  <Form.Label htmlFor="hora">Hora:</Form.Label>
                  <Form.Control as="select" id="hora" className="form-control">
                    <option>7-8</option>
                    <option>8-9</option>
                    <option>9-10</option>
                    <option>10-11</option>
                    <option>11-12</option>
                    <option>12-13</option>
                    <option>13-14</option>
                    <option>14-15</option>
                    <option>15-16</option>
                    <option>16-17</option>
                    <option>17-18</option>
                    <option>18-19</option>
                    <option>19-20</option>
                  </Form.Control>
                </Form.Group>
                <Form.Group className="form-group">
                  <Form.Label htmlFor="materia">Materia:</Form.Label>
                  <Form.Control
                    type="text"
                    id="materia"
                    className="form-control"
                  />
                </Form.Group>
                <div className="form-group docente-container">
                  <Form.Label htmlFor="docente">Docente</Form.Label>
                  <Form.Control
                    type="text"
                    id="docente"
                    className="form-control"
                  />
                  <Button variant="custom" id="agregar-docente-btn">
                    Agregar Docente
                  </Button>
                </div>
              </div>
              <div className="form-container">
                <div className="form-group d-flex align-items-end">
                  <Button variant="custom" id="agregar-btn">
                    Agregar
                  </Button>
                  <Button
                    variant="custom"
                    id="guardar-btn"
                    style={{ display: "none" }}
                  >
                    Guardar
                  </Button>
                </div>
              </div>
            </Form>
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
                      <td key={`${dia}-${hora}`}>
                        {renderTableCell(dia, hora)}
                      </td>
                    ))}
                  </tr>
                ))}
              </tbody>
            </table>
            <div className="context-menu" id="context-menu">
              <Button variant="custom" id="editar-btn">
                Editar
              </Button>
              <Button variant="custom" id="eliminar-btn">
                Eliminar
              </Button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Horarios;
