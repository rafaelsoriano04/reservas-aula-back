import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Modal, Button, Form, Table } from "react-bootstrap";
import "../horario/horario.css";

function Horarios() {
    const [selectedCell, setSelectedCell] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [showContextMenu, setShowContextMenu] = useState(false);
    const [contextMenuPosition, setContextMenuPosition] = useState({
        top: 0,
        left: 0,
    });
    const [formData, setFormData] = useState({
        bloque: "",
        tipo: "Aula",
        aulaLab: "",
        dia: "Lunes",
        hora: "7-8",
        materia: "",
        docente: "",
    });

    const handleCellClick = (e, rowIndex, cellIndex) => {
        setSelectedCell({ rowIndex, cellIndex });
        setContextMenuPosition({ top: e.pageY, left: e.pageX });
        setShowContextMenu(true);
    };

    const handleContextMenuClick = (action) => {
        if (action === "edit") {
            const { materia } = formData;
            const newTableData = [...tableData];
            newTableData[selectedCell.rowIndex][selectedCell.cellIndex] =
                materia;
            setTableData(newTableData);
        } else if (action === "delete") {
            const newTableData = [...tableData];
            newTableData[selectedCell.rowIndex][selectedCell.cellIndex] = "";
            setTableData(newTableData);
        }
        setShowContextMenu(false);
    };

    const handleFormChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
        if (name === "tipo" && value === "Laboratorio") {
            fetchAulasLabs(formData.bloque);
        }
    };

    const fetchAulasLabs = async (bloqueId) => {
        try {
            const response = await axios.get(
                `http://localhost:8080/lab/bloque/${bloqueId}`
            );
            setAulasLabs(response.data);
        } catch (error) {
            console.error("Error fetching data: ", error);
            setAulasLabs([]); // Limpia los laboratorios si la petición falla
        }
    };
    const handleBloqueChange = (e) => {
        setFormData({ ...formData, bloque: e.target.value });
        if (formData.tipo === "Laboratorio") {
            fetchAulasLabs(e.target.value);
        }
    };

    const [tableData, setTableData] = useState(
        Array.from({ length: 13 }, () => Array.from({ length: 5 }, () => ""))
    );

    useEffect(() => {
        const handleClickOutside = (event) => {
            if (!event.target.closest(".context-menu")) {
                setShowContextMenu(false);
            }
        };
        document.addEventListener("click", handleClickOutside);
        return () => {
            document.removeEventListener("click", handleClickOutside);
        };
    }, []);

    const buscarDocente = () => {
        if (docente.cedula === "1234567890") {
            setDocente({
                cedula: "1234567890",
                nombre: "Juan",
                apellido: "Pérez",
                telefono: "0987654321",
            });
        } else {
            setDocente({
                ...docente,
                nombre: "",
                apellido: "",
                telefono: "",
            });
        }
    };
    const [horarios, setHorarios] = useState([]);
    useEffect(() => {
        const fetchHorarios = async () => {
            try {
                const response = await fetch("http://localhost:8080/reservas");
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                const data = await response.json();
                setHorarios(data);
            } catch (error) {
                console.error("Error fetching data: ", error);
            }
        };
        fetchHorarios();
    }, []);

    const asignarDocente = () => {
        if (docente.nombre && docente.apellido) {
            document.getElementById(
                "docente"
            ).value = `${docente.nombre} ${docente.apellido}`;
            handleClose();
        } else {
            alert("Docente no encontrado.");
        }
    };

    useEffect(() => {
        // Código para manejar los cambios de bloque y tipo
    }, []);

    return (
        <div className="container-fluid">
            <div className="container mt-4">
                <div className="row">
                    <div className="col-md-12">
                        <Form id="form-horarios">
                            <div className="form-container">
                                <Form.Group className="form-group">
                                    <Form.Label htmlFor="bloque">
                                        Bloque:
                                    </Form.Label>
                                    <Form.Control
                                        as="select"
                                        id="bloque"
                                        className="form-control"
                                        value={formData.bloque}
                                        onChange={handleBloqueChange}
                                    >
                                        {bloques.map((bloque) => (
                                            <option
                                                key={bloque.id}
                                                value={bloque.id}
                                            >
                                                {bloque.nombre}
                                            </option>
                                        ))}
                                    </Form.Control>
                                </Form.Group>

                                <Form.Group className="form-group">
                                    <Form.Label htmlFor="tipo">
                                        Tipo:
                                    </Form.Label>
                                    <Form.Control
                                        as="select"
                                        id="tipo"
                                        className="form-control"
                                        value={formData.tipo}
                                        onChange={handleFormChange}
                                        name="tipo"
                                    >
                                        <option selected>Aula</option>
                                        <option>Laboratorio</option>
                                    </Form.Control>
                                </Form.Group>
                                <Form.Group className="form-group">
                                    <Form.Label htmlFor="aula-lab">
                                        Aula/Laboratorio:
                                    </Form.Label>
                                    <Form.Control
                                        as="select"
                                        id="aula-lab"
                                        className="form-control"
                                        value={formData.aulaLab}
                                        onChange={handleFormChange}
                                        name="aulaLab"
                                    >
                                        {aulasLabs.map((aulaLab) => (
                                            <option
                                                key={aulaLab.id}
                                                value={aulaLab.id}
                                            >
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
                                    <Form.Control
                                        as="select"
                                        id="dia"
                                        className="form-control"
                                    >
                                        <option selected>Lunes</option>
                                        <option>Martes</option>
                                        <option>Miércoles</option>
                                        <option>Jueves</option>
                                        <option>Viernes</option>
                                        <option>Sábado</option>
                                        <option>Domingo</option>
                                    </Form.Control>
                                </Form.Group>
                                <Form.Group className="form-group">
                                    <Form.Label htmlFor="hora">
                                        Hora:
                                    </Form.Label>
                                    <Form.Control
                                        as="select"
                                        id="hora"
                                        className="form-control"
                                    >
                                        <option selected>7-8</option>
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
                                    <Form.Label htmlFor="materia">
                                        Materia:
                                    </Form.Label>
                                    <Form.Control
                                        type="text"
                                        id="materia"
                                        className="form-control"
                                    />
                                </Form.Group>
                                <div className="form-group docente-container">
                                    <Form.Label htmlFor="docente">
                                        Docente
                                    </Form.Label>
                                    <Form.Control
                                        type="text"
                                        id="docente"
                                        className="form-control"
                                    />
                                    <Button
                                        variant="custom"
                                        id="agregar-docente-btn"
                                        onClick={handleShow}
                                    >
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
                        <Table className="table table-bordered mt-4">
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
                                {tableData.map((row, rowIndex) => (
                                    <tr key={rowIndex}>
                                        <td>{`${7 + rowIndex}-${
                                            8 + rowIndex
                                        }`}</td>
                                        {row.map((cell, cellIndex) => (
                                            <td
                                                key={cellIndex}
                                                onClick={(e) =>
                                                    handleCellClick(
                                                        e,
                                                        rowIndex,
                                                        cellIndex
                                                    )
                                                }
                                                className={
                                                    selectedCell?.rowIndex ===
                                                        rowIndex &&
                                                    selectedCell?.cellIndex ===
                                                        cellIndex
                                                        ? "selected"
                                                        : ""
                                                }
                                            >
                                                {cell}
                                            </td>
                                        ))}
                                    </tr>
                                ))}
                            </tbody>
                        </Table>
                        <div
                            className="context-menu"
                            id="context-menu"
                            style={{
                                top: contextMenuPosition.top,
                                left: contextMenuPosition.left,
                                display: showContextMenu ? "block" : "none",
                            }}
                        >
                            <Button
                                variant="custom"
                                onClick={() => handleContextMenuClick("edit")}
                            >
                                Editar
                            </Button>
                            <Button
                                variant="custom"
                                onClick={() => handleContextMenuClick("delete")}
                            >
                                Eliminar
                            </Button>
                        </div>
                    </div>
                </div>
            </div>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Agregar Docente</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group>
                            <Form.Label htmlFor="cedula-docente">
                                Cédula:
                            </Form.Label>
                            <Form.Control
                                type="text"
                                id="cedula-docente"
                                value={docente.cedula}
                                onChange={handleDocenteChange}
                            />
                        </Form.Group>
                        <Button variant="custom" onClick={buscarDocente}>
                            Buscar
                        </Button>
                        <Form.Group className="mt-3">
                            <Form.Label htmlFor="nombre-docente">
                                Nombre:
                            </Form.Label>
                            <Form.Control
                                type="text"
                                id="nombre-docente"
                                value={docente.nombre}
                                onChange={handleDocenteChange}
                            />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label htmlFor="apellido-docente">
                                Apellido:
                            </Form.Label>
                            <Form.Control
                                type="text"
                                id="apellido-docente"
                                value={docente.apellido}
                                onChange={handleDocenteChange}
                            />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label htmlFor="telefono-docente">
                                Teléfono:
                            </Form.Label>
                            <Form.Control
                                type="text"
                                id="telefono-docente"
                                value={docente.telefono}
                                onChange={handleDocenteChange}
                            />
                        </Form.Group>
                        <Button variant="custom" onClick={asignarDocente}>
                            Asignar
                        </Button>
                    </Form>
                </Modal.Body>
            </Modal>
        </div>
    );
}

export default Horarios;
